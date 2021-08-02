package com.tmt.challenge.mapper;

import com.tmt.challenge.dto.UserDTO;
import com.tmt.challenge.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toUserDTO(User user);
    User toUserEntity(UserDTO userDTO);

}
