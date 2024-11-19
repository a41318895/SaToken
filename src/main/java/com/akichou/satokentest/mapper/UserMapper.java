package com.akichou.satokentest.mapper;

import com.akichou.satokentest.entity.User;
import com.akichou.satokentest.entity.dto.UserDto;
import com.akichou.satokentest.entity.dto.UserUpdateDto;
import com.akichou.satokentest.entity.vo.UserUpdatedVo;

import java.time.LocalDateTime;

public class UserMapper {

    public static User mapUserDtoToUser(UserDto userDto, Object adminId) {

        return User.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .age(userDto.getAge())
                .note(userDto.getNote())
                .createdBy((Long)adminId)
                .createdTime(LocalDateTime.now())
                .build() ;
    }

    public static User mapUserUpdateDtoToUser(User user, UserUpdateDto dto, Object adminId) {

        return User.builder()
                .id(dto.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .age(dto.getAge())
                .note(dto.getNote())
                .createdBy(user.getCreatedBy())
                .createdTime(user.getCreatedTime())
                .updatedBy((Long)adminId)
                .updatedTime(LocalDateTime.now())
                .build() ;
    }

    public static UserUpdatedVo mapUserToUserUpdatedVo(User user) {

        return new UserUpdatedVo(user.getId(), user.getUsername(), user.getAge(), user.getNote(),
                user.getCreatedBy(), user.getCreatedTime(), user.getUpdatedBy(), user.getUpdatedTime()) ;
    }
}
