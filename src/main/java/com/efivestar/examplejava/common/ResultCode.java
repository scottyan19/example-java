package com.efivestar.examplejava.common;

/**
 * @author Scott Yan
 * @date 2020/08/12
 */
public enum ResultCode {
    /**
     * success
     */
    SUCCESS("2000"),
    SERVER_ERROR("5000"),
    ARGUMENT_INVALID("4000"),
    DATABASE_ERROR("5002"),
    UNKNOWN_ERROR("5009");

    private String code;
    ResultCode(String codeParam) {
        code = codeParam;
    }

    public String getCode() {
        return code;
    }
}
