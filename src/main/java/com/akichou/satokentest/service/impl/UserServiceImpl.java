package com.akichou.satokentest.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.akichou.satokentest.entity.User;
import com.akichou.satokentest.entity.dto.TwoFactorAuthDto;
import com.akichou.satokentest.entity.dto.UserNoteDto;
import com.akichou.satokentest.entity.vo.UserNoteVo;
import com.akichou.satokentest.global.exception.UserNotFoundException;
import com.akichou.satokentest.repository.UserRepository;
import com.akichou.satokentest.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository ;

    @Override
    public SaResult getNoteContent() {

        User user = findUserByIdInternal() ;

        return SaResult.data(new UserNoteVo(user.getNote())) ;
    }

    @Override
    public SaResult updateUserNote(UserNoteDto userNoteDto) {

        User user = findUserByIdInternal() ;

        user.setNote(userNoteDto.getNoteContent()) ;

        userRepository.save(user) ;

        log.info("User ( USERNAME = {} ) note content saved successfully", user.getUsername()) ;

        return SaResult.ok("User note content saved successfully") ;
    }

    @Override
    public SaResult deleteUserNote() {

        if (!StpUtil.isSafe("deleteUserNote")) {

            return SaResult.error("Delete user note failed, please retry after passing two-factor authentication") ;
        }

        User user = findUserByIdInternal() ;

        user.setNote("") ;

        log.info("User ( USERNAME = {} ) deleted note content successfully", user.getUsername()) ;

        userRepository.save(user) ;

        return SaResult.ok("User note content deleted successfully") ;
    }

    @Override
    public SaResult doTwoFactorAuthentication(TwoFactorAuthDto twoFactorAuthDto) {

        User user = findUserByIdInternal() ;

        if (user.getPassword().equals(twoFactorAuthDto.getPassword())) {

            StpUtil.openSafe("deleteUserNote", 120) ;     // Open two-factor authentication with 2 sec

            return SaResult.ok("Two-factor authentication passed temporarily successfully") ;
        }

        return SaResult.error("Two-factor authentication failed") ;
    }

    private User findUserByIdInternal() {

        Long userId = Long.valueOf((String)StpUtil.getLoginId()) ;

        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Username: [ ID = " + userId + " ] Not Found")) ;
    }
}
