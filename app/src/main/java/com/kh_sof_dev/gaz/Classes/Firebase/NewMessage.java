
package com.kh_sof_dev.gaz.Classes.Firebase;


public class NewMessage {
    private String date_time, msg,sender_id,sender_name,sender_img,msgID;
private Long long_time;
    public NewMessage() {

    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getSender_img() {
        return sender_img;
    }

    public void setSender_img(String sender_img) {
        this.sender_img = sender_img;
    }

    public String getMsgID() {
        return msgID;
    }

    public void setMsgID(String msgID) {
        this.msgID = msgID;
    }

    public Long getLong_time() {
        return long_time;
    }

    public void setLong_time(Long long_time) {
        this.long_time = long_time;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public NewMessage(String date_time, String msg, String sender_id) {
        this.date_time = date_time;
        this.msg = msg;
        this.sender_id = sender_id;
    }
}