package com.akichou.satokentest.entity;

import java.text.MessageFormat;

import static com.akichou.satokentest.constant.Constant.*;

public final class SpecificUser {

    @Override
    public String toString() {

        return MessageFormat.format("ID = {0}, USERNAME = {1}, SEX = {2}, AGE = {3}",
                MOCK_USER_ID, MOCK_USER_USERNAME, MOCK_USER_SEX, MOCK_USER_AGE) ;
    }
}
