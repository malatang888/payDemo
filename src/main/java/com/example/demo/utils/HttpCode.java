package com.example.demo.utils;

/**
 * HTTP状态码 
 * 除权限错误/验证错误，其他错误可都传500
 */
public enum HttpCode {

	/**成功*/
    OK(200),
    /**权限不足*/
    FORBIDDEN(403),
    /**未找到处理方法/页面*/
    NOT_FOUNT(404),
    /**验证错误*/
    VALIDATE_FAIL(701),
	/**服务器错误*/
	ERROR(500);

    private int code;

    HttpCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
