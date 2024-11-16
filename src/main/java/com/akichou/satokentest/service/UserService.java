package com.akichou.satokentest.service;

import cn.dev33.satoken.util.SaResult;
import com.akichou.satokentest.entity.dto.TwoFactorAuthDto;
import com.akichou.satokentest.entity.dto.UserIdAndDeviceDto;
import com.akichou.satokentest.entity.dto.UserNoteDto;

public interface UserService {

    SaResult getNoteContent();

    SaResult updateUserNote(UserNoteDto userNoteDto);

    SaResult deleteUserNote();

    SaResult doTwoFactorAuthentication(TwoFactorAuthDto twoFactorAuthDto);

    SaResult isLogin(Long userId);

    SaResult getTokenInfo(Long userId);

    SaResult getUserLoginDevice();

    SaResult getTokenByIdAndDevice(UserIdAndDeviceDto userIdAndDeviceDto);
}
