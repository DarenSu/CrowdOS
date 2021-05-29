package com.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class User_Peripherals {


    private Integer user_peripheralsId;
    private Integer userId;
    private Integer taskId;
    private Integer peripheralsName;
    private String peripheralsType;
    private Integer peripheralsStatus;
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date postTime;                  // sensing begin time
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date deadLine;                   // sensing end time


    private Integer stepCout;
    private Integer heartbeat;
    private Integer bloodOxygenSaturation;


    private String image;                   //user peripheral image
    private String txtimage;                //user peripheral text





}
