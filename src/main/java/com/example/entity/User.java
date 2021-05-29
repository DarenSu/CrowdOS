package com.example.entity;


/**
 * @Author:DarenSu
 * @Date: 2019/6/13
 * @Time: 14:42
 */



public class User {
    private Integer userId;
    private String userName;
    private String passWord;
    private String realName;
    private Integer coins;                  // credit points

    //  20200723    test
    //  20200812
    //private String Nickname;
    //private String QQNumber;
    //private String Phone;
    //private String UserIcon;


    public User() {
        super();
    }

    public User(Integer userId, String userName, String passWord, String realName, Integer coins) {
        this.userId = userId;
        this.userName = userName;
        this.passWord = passWord;
        this.realName = realName;
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
                ", coins=" + coins +
                '}';
    }
}
