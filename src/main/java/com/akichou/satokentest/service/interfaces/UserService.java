package com.akichou.satokentest.service.interfaces;

import cn.dev33.satoken.util.SaResult;
import com.akichou.satokentest.entity.dto.TwoFactorAuthDto;
import com.akichou.satokentest.entity.dto.UserNoteDto;

public interface UserService {

    SaResult getNoteContent();

    SaResult updateUserNote(UserNoteDto userNoteDto);

    SaResult deleteUserNote();

    SaResult doTwoFactorAuthentication(TwoFactorAuthDto twoFactorAuthDto);
}
