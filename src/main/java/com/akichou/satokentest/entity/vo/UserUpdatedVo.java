package com.akichou.satokentest.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record UserUpdatedVo(
        Long userId,
        String username,
        Integer age,
        String note,
        Long createdBy,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createdTime,
        Long updatedBy,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime updatedTime) {
}
