package com.pyg.vo;

import java.io.Serializable;

/**
 * Description: 结果包装类
 *
 * @author AK
 * @date 2018/8/6 21:15
 * @since 1.0.0
 */
public class InfoResult implements Serializable {
    private boolean success;
    private String message;

    public InfoResult() {
    }

    public InfoResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}