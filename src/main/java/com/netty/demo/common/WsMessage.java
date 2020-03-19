package com.netty.demo.common;

import lombok.Data;

/**
 * @Description: WsMessage
 * @Author: shizhimin
 * @Date: 2020/3/18
 * @Version: 1.0
 */
@Data
public class WsMessage {

    private int t; // 消息类型
    private String n; // 用户名称
    private long room_id; // 房间 ID
    private String body; // 消息主体
    private int err; //错误码

    @Override
    public String toString() {
        return "WsMessage{" +
                "t=" + t +
                ", n='" + n + '\'' +
                ", room_id=" + room_id +
                ", body='" + body + '\'' +
                ", err=" + err +
                '}';
    }

    public WsMessage(int t, String n, int err) {
        this.t = t;
        this.n = n;
        this.err = err;
    }

    public WsMessage(int t, String n) {
        this.t = t;
        this.n = n;
        this.err = 0;
    }

    public WsMessage(int t, String n, String body, int err) {
        this.t = t;
        this.n = n;
        this.body = body;
        this.err = err;
    }

    public WsMessage(int t, String n, String body) {
        this.t = t;
        this.n = n;
        this.body = body;
        this.err = 0;
    }
}
