package org.example.securityapp.core;

import lombok.Data;

@Data
public class Resp<T> {
    private boolean success;
    private Integer status;
    private String msg;
    private T data;

    public Resp() {
        this.success = true;
        this.status = 200;
        this.msg = "성공";
        this.data = null;
    }

    public Resp(T data) {
        this.success = true;
        this.status = 200;
        this.msg = "성공";
        this.data = data;
    }

    public Resp(Integer status, String msg) {
        this.success = false;
        this.status = status;
        this.msg = msg;
        this.data = null;
    }
}