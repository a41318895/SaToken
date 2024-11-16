package com.akichou.satokentest.constant;

public final class Constant {

    // Mocking Data
    public static final Long MOCK_USER_ID = 1L ;

    public static final String MOCK_USER_USERNAME = "aki" ;

    public static final String MOCK_USER_PASSWORD = "akiChou" ;

    public static final String MOCK_USER_SEX = "Man" ;

    public static final Integer MOCK_USER_AGE = 25 ;


    // Login Status
    public static final String LOGIN_STATUS = "[STATUS : LOGIN]" ;

    public static final String NO_LOGIN_STATUS = "[STATUS : NO LOGIN]" ;

    public static final String LOGIN_DEVICE = "PC" ;

    public static final String LOGIN_DEVICE_MESSAGE = "Current User Login Device: " ;

    // User Status Message
    public static final String LOGIN_MESSAGE = "logged in" ;

    public static final String LOGIN_SUCCESS_MESSAGE = "Login Successfully" ;

    public static final String LOGIN_SUCCESS_MESSAGE_WITH_ID = "(ID={}) Login Successfully" ;

    public static final String LOGOUT_SUCCESS_MESSAGE = "Logout Successfully" ;

    public static final String LOGOUT_SUCCESS_MESSAGE_WITH_ID = "(ID={}) Logout Successfully" ;

    public static final String LOGOUT_SUCCESS_MESSAGE_WITH_ID_AND_DEVICE = "Logout Successfully (ID = {0}, DEVICE = {1})" ;

    public static final String NO_LOGIN_MESSAGE = "not logged in" ;

    public static final String USER_NOT_FOUND_MESSAGE = "User not found" ;

    public static final String LOGIN_STATUS_CHECK_MESSAGE_WITH_ID = "Login status checked for user (ID={}): {}" ;

    public static final String LOGIN_STATUS_CHECK_FAIL_MESSAGE_WITH_ID = "Login status check failed: User not found (ID={})" ;

    public static final String NOTE_SAVE_SUCCESS_MESSAGE = "User note content saved successfully" ;

    public static final String NOTE_SAVE_SUCCESS_MESSAGE_WITH_ID = "User ( ID = {} ) note content saved successfully" ;

    public static final String NOTE_DELETE_SUCCESS_MESSAGE = "User note content deleted successfully" ;

    public static final String NOTE_DELETE_SUCCESS_MESSAGE_WITH_ID = "(ID = {} ) note content deleted successfully" ;

    public static final String NOTE_DELETE_FAIL_MESSAGE = "Delete user note failed, please retry after passing two-factor authentication" ;

    public static final String TOKEN_INFO_RETRIEVAL_FAIL_MESSAGE_WITH_ID = "Token info retrieval failed: User not found (ID={})" ;

    public static final String TOKEN_INFO_RETRIEVAL_SUCCESS_MESSAGE_WITH_ID = "Token info successfully retrieved for user (ID={})" ;

    public static final long TOKEN_TIMEOUT = 60 * 60 * 24 * 7 ;

    public static final String TOKEN_RETRIEVAL_WITH_ID_AND_DEVICE_MESSAGE = "Token Retrieved (ID = {0}): {1}" ;

    // Session
    public static final String ACCOUNT_SESSION_KEY_USER = "user" ;

    public static final String SESSION_SWITCH_FAIL_MESSAGE = "Failed to switch the session" ;

    public static final String SESSION_SWITCH_FAIL_MESSAGE_WITH_ID_AND_EXCEPTION_MSG = "Error while switching the session for ID={}: {}" ;


    // Two-Factor Authentication
    public static final String DELETE_SIGN = "deleteUserNote" ;

    public static final String AUTH_PASS_MESSAGE = "Two-factor authentication passed temporarily successfully" ;

    public static final String AUTH_FAIL_MESSAGE = "Two-factor authentication failed" ;

    public static final long SAFE_TIME_SECOND = 120 ;


    private Constant() {}
}
