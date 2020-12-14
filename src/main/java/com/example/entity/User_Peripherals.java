package com.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class User_Peripherals {


    private Integer user_peripheralsId;     //用户-外设ID
    private Integer userId;                 //用户ID
    private Integer taskId;                 //任务ID
    private Integer peripheralsName;        //用户-外设的名字
    private String peripheralsType;         //用户-外设的型号
    private Integer peripheralsStatus;      //用户-外设的健康状态
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date postTime;                  //感知开始日期
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date deadLine;                  //感知结束日期


    private Integer stepCout;               //步数
    private Integer heartbeat;              //心跳
    private Integer bloodOxygenSaturation;  //血氧饱和度
    /*
    * 这部分需要添加外设的运动数据，需要具体的外设接进来后才可以进行属性的确认
    * */

    private String image;                   //用户-外设的图片信息
    private String txtimage;                //用户-外设的文本信息





}
