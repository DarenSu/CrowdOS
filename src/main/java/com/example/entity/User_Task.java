package com.example.entity;


/**
 * @Author:DarenSu
 * @Date: 2019/6/13
 * @Time: 14:42
 */



public class User_Task {
    private Integer user_taskId;
    private Integer userId;
    private Integer taskId;
    private Integer user_taskStatus;                // 0-received but not executed  &1-received and executed
    private String content;                     //context
    private String image;                       //picture   （Temporarily not applicable）
    private Integer type;                        //file type，0-text，1-image，2-video，3-audio

    public User_Task() {
		super();
	}

    public User_Task(Integer user_taskId, Integer userId, Integer taskId, Integer user_taskStatus, String content, String image, Integer type) {
        this.user_taskId = user_taskId;
        this.userId = userId;
        this.taskId = taskId;
        this.user_taskStatus = user_taskStatus;
        this.content = content;
        this.image = image;
        this.type = type;
    }

    public Integer getUser_taskId() {
        return user_taskId;
    }

    public void setUser_taskId(Integer user_taskId) {
        this.user_taskId = user_taskId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getUser_taskStatus() {
        return user_taskStatus;
    }

    public void setUser_taskStatus(Integer user_taskStatus) {
        this.user_taskStatus = user_taskStatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User_Task{" +
                "user_taskId=" + user_taskId +
                ", userId=" + userId +
                ", taskId=" + taskId +
                ", user_taskStatus=" + user_taskStatus +
                ", content='" + content + '\'' +
                ", image='" + image + '\'' +
                ", type=" + type +
                '}';
    }
}
