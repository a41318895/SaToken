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


    // Account Disable
    public static final long DISABLE_TIME_SECOND = 172800 ;     // 2 days

    public static final String ACCOUNT_DISABLED_MESSAGE = "Account Disabled - ID = " ;

    public static final String DISABLE_INFO_RETRIEVAL_MESSAGE = "Disable INFO (ID = {0}) - [ Status: {1}, Time: {2} ]" ;

    public static final String DISABLE_TIME_INFINITE_DESCRIPTION = "Infinite" ;

    public static final String DISABLE_TIME_ZERO = "0 sec" ;

    public static final String UNTIE_DISABLE_MESSAGE = "United the account (ID = {0})" ;

    public static final String ACCOUNT_STATUS_HEALTHY = "HEALTHY" ;

    public static final String ACCOUNT_STATUS_DISABLED = "DISABLED" ;


    // Encryption
    public static final String ENCRYPT_SUCCESS_MESSAGE = "Encrypted text: [ {0} ] - [ With {1} Algorithm ]" ;

    public static final String DECRYPT_SUCCESS_MESSAGE = "Decrypted text: [ {0} ] - [ With {1} Algorithm ]" ;

    public static final String MAP_KEY_SIGN_PUBLIC = "public" ;

    public static final String MAP_KEY_SIGN_PRIVATE = "private" ;

    public static final int HASH_SALT_LOG_ROUNDS = 12 ;

    public static final String ALGORITHM_NAME_IN_RSA_UTILS = "RSA" ;

    public static final int KEY_PAIR_GENERATE_SIZE = 2048 ;


    // Admin
    public static final String ADMIN_LOGIN_SUCCESS_MSG_WITH_ID = "(ADMIN ID={}) Login Successfully" ;

    public static final String ACCOUNT_SESSION_KEY_ADMIN = "admin" ;

    public static final String ADMIN_LOGIN_SUCCESS_MSG = "Admin Login Successfully" ;

    public static final String ADMIN_LOGIN_STATUS_CHECK_FAIL_MSG_WITH_ID = "Admin Login status check failed: Admin not found (ID={})" ;

    public static final String ADMIN_LOGIN_STATUS_CHECK_MSG_WITH_ID = "Admin Login status checked for admin (ID={}): {}" ;

    public static final String ADMIN_LOGIN_MESSAGE = "logged in" ;

    public static final String ADMIN_NO_LOGIN_MESSAGE = "not logged in" ;

    public static final String ADMIN_TOKEN_INFO_RETRIEVAL_FAIL_MSG_WITH_ID = "Admin Token info retrieval failed: Admin not found (ID={})" ;

    public static final String ADMIN_TOKEN_INFO_RETRIEVAL_SUCCESS_MSG_WITH_ID = "Admin Token info successfully retrieved for admin (ID={})" ;

    public static final String ADMIN_LOGOUT_SUCCESS_MSG = "Admin Logout Successfully" ;

    public static final String ADMIN_LOGOUT_SUCCESS_MSG_WITH_ID = "(ID={}) Admin Logout Successfully" ;

    public static final String ADMIN_CREATE_USER_MSG = "User (ID = {0}) created by Admin (ID = {1}) successfully" ;

    public static final String ADMIN_UPDATE_USER_MSG = "User (ID = {0}) updated by Admin (ID = {1}) successfully" ;

    public static final String ADMIN_DELETE_USER_MSG = "User (ID = {0}) deleted by Admin (ID = {1}) successfully" ;


    private Constant() {}
}
