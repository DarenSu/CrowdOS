package com.example.entity;

/**
 * @Author:0xOO
 * @Date: 2018/9/26 0026
 * @Time: 14:39
 */
/**
 * @Author:DarenSu
 * @Date: 2019/6/13 修改
 * @Time: 14:42
 */



public class User_Task {
    private Integer user_taskId;                //用户-任务ID
    private Integer userId;                      //用户ID
    private Integer taskId;                      //任务ID
    private Integer user_taskStatus;                //用户-任务执行状态  0-接受未执行  &1-接收并执行
    private String content;                     //内容
    private String image;                       //图片信息   （暂时并不需要使用）
    private Integer type;                        //文件的格式，0-文字，1-图片，2-视频，3-语音

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
