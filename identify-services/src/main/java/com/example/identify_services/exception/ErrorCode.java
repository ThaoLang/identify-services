package com.example.identify_services.exception;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(999,"Uncategorized exception"),
    USER_EXISTED(1001,"User existed"),
    USERNAME_INVALID(1002,"Username must be at least 4 characters"),
    PASSWORD_INVALID(1003,"Password must be at least 8 characters"),
    USER_NOT_EXISTED(1005,"User not existed"),
    UNAUTHENTICATED(1006,"Unauthenticated"),
    ;

    ErrorCode(int code,String message) {
        this.message = message;
        this.code = code;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
