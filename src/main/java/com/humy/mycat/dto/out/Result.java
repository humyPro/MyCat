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

    private int code;

    private String errMsg;

    private T data;

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setData(data);
        result.setCode(SUCCESS);
        return result;
    }

    public static <T> Result<T> serverError() {
        Result<T> result = new Result<>();
        result.setData(null);
        result.setCode(SERVER_ERROR);
        return result;
    }

    public static <T> Result<T> failed(String msg) {
        Result<T> result = new Result<>();
        result.setCode(BAD_REQUEST);
        result.setErrMsg(msg);
        return result;
    }
}
