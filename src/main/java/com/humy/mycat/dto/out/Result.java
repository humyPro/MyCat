package com.humy.mycat.dto.out;

import lombok.Data;

/**
 * @Author: Milo Hu
 * @Date: 10/16/2019 17:55
 * @Description:
 */
@Data
public class Result<T> {

    public static final int SUCCESS = 200;

    public static final int BAD_REQUEST = 400;

    public static final int SERVER_ERROR = 500;

    public static final int FAILED = 0;

    public static final int UNAUTHORIZED = 401;

    private int code;

    private String errMsg;

    private T data;

    public static <T> Result<T> success(T data) {
        return newInstance(SUCCESS, data, null);
    }

    public static <T> Result<T> serverError() {
        return newInstance(SERVER_ERROR, null, null);
    }

    public static <T> Result<T> badRequest(String msg) {
        return newInstance(BAD_REQUEST, null, msg);
    }

    public static <T> Result<T> unauthorized(String msg) {
        return newInstance(UNAUTHORIZED, null, null);
    }

    public static <T> Result<T> newInstance(int code, T data, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setData(data);
        result.setErrMsg(msg);
        return result;
    }
}
