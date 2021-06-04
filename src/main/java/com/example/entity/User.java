package com.example.entity;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Author:DarenSu
 * @Date: 2019/6/13
 * @Time: 14:42
 */



public class User {
    private Integer userId;                 //用户ID
    private String userName;                //用户账号
    private String passWord;                //登录密码
    private String realName;                //真实姓名
    private String mail;                    // 邮箱地址
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date onlineTime;
    private String token;
    private Integer Phone;
    private String imagePath;
    private Integer coins;                  //积分

    //  20200723    测试用，暂时屏蔽该部分的信息，
    //  20200812     修改比较麻烦，太繁琐，就没有进行恢复，有时间再说吧 ， 也要记得
    //private String Nickname;                //用户昵称
    //private String QQNumber;                //QQ
    //private String Phone;                   //电话
    //private String UserIcon;                //头像存储路径


    public User() {
        super();
    }

//    public User(Integer userId, String userName, String passWord, String realName, Integer coins) {
//        this.userId = userId;
//        this.userName = userName;
//        this.passWord = passWord;
//        this.realName = realName;
//        this.coins = coins;
//    }
//
//    public Integer getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Integer userId) {
//        this.userId = userId;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public String getPassWord() {
//        return passWord;
//    }
//
//    public void setPassWord(String passWord) {
//        this.passWord = passWord;
//    }
//
//    public String getRealName() {
//        return realName;
//    }
//
//    public void setRealName(String realName) {
//        this.realName = realName;
//    }
//
//    public Integer getCoins() {
//        return coins;
//    }
//
//    public void setCoins(Integer coins) {
//        this.coins = coins;
//    }
//
//    @Override
//    public String toString() {
//        return "User{" +
//                "userId=" + userId +
//                ", userName='" + userName + '\'' +
//                ", passWord='" + passWord + '\'' +
//                ", realName='" + realName + '\'' +
//                ", coins=" + coins +
//                '}';
//    }


    public User(Integer userId, String userName, String passWord, String realName, String mail, Date onlineTime, String token, Integer phone, String imagePath, Integer coins) {
        this.userId = userId;
        this.userName = userName;
        this.passWord = passWord;
        this.realName = realName;
        this.mail = mail;
        this.onlineTime = onlineTime;
        this.token = token;
        Phone = phone;
        this.imagePath = imagePath;
        this.coins = coins;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Date getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Date onlineTime) {
        this.onlineTime = onlineTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getPhone() {
        return Phone;
    }

    public void setPhone(Integer phone) {
        Phone = phone;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getCoins() {
        return coins;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", realName='" + realName + '\'' +
                ", mail='" + mail + '\'' +
                ", onlineTime=" + onlineTime +
                ", token='" + token + '\'' +
                ", Phone=" + Phone +
                ", imagePath='" + imagePath + '\'' +
                ", coins=" + coins +
                '}';
    }
}
