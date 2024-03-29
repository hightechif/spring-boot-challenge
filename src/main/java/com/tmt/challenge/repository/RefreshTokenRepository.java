package com.tmt.challenge.repository;

import com.tmt.challenge.model.RefreshToken;
import com.tmt.challenge.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @NotNull
    @Override
    Optional<RefreshToken> findById(@NotNull Long id);

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);

}
