package com.tmt.challenge.controller;

import com.tmt.challenge.dto.UserDTO;
import com.tmt.challenge.dto.request.TokenRefreshRequestDTO;
import com.tmt.challenge.dto.request.UserLoginDTO;
import com.tmt.challenge.dto.response.JwtResponseDTO;
import com.tmt.challenge.dto.response.TokenRefreshResponseDTO;
import com.tmt.challenge.exception.TokenRefreshException;
import com.tmt.challenge.model.RefreshToken;
import com.tmt.challenge.service.security.RefreshTokenService;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tmt.challenge.service.security.CustomUserDetailsService;
import com.tmt.challenge.config.JwtUtils;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    RefreshTokenService refreshTokenService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserLoginDTO userLoginDTO) throws Exception {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userLoginDTO.getUsername(), userLoginDTO.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(userLoginDTO.getUsername());

        String jwtToken = jwtUtils.generateJwtToken(userDetails);     // Bearer Token

        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());

        return ResponseEntity.ok(new JwtResponseDTO(jwtToken, refreshToken.getToken(), userDetails.getUsername(), roles));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
        return ResponseEntity.ok(userDetailsService.save(user));
    }

    @RequestMapping(value = "/refresh-token", method = RequestMethod.POST)
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequestDTO request) throws Exception {

        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
                    String token = jwtUtils.generateJwtToken(userDetails);
                    return ResponseEntity.ok(new TokenRefreshResponseDTO(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
        Map<String, Object> expectedMap = new HashMap<>();
        for (Entry<String, Object> entry : claims.entrySet()) {
            expectedMap.put(entry.getKey(), entry.getValue());
        }
        return expectedMap;
    }

}
