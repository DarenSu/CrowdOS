package com.example.entity;

public class Human {

//      20210416   实体识别的规则信息制定
//      首先是在实体识别码（identificationCode）中进行实体的识别
//      实体的大类主要有三个，所以其前两位主要存放 00 01 10 11这四种，分别代表未知分类，和人机物三大类
//      接着，人机物里面又可以分多个小类，如机里面有无人机、手机、智能车等等，所以需要实体识别码后面的四位对其小类进行标识
//      由于目前只分类后面

    private Integer peripheralsId;     //用户-外设ID
    private Integer identificationCode; //实体识别码
    private Integer broadHeading;       //实体大类    0-未知分类    1-人    2-机    3-物
    private Integer subclass;           //实体小类   大类里面更细致的划分
    private Integer userId;                 //所属用户的用户ID
    private Integer relation;        //关系 0或1
    private String  position;        //位置  经纬度
    private String sensing_Type;        //感知类型   0或1   固定或移动
    private Integer taskId;        //和其有关的任务
    private Integer decision;        //决策  value 0-10
    private Integer professional;        //专业程度  value 0-10
    private Integer credit;        //信用值  value 0-10
    private Integer sharing;        //是否共享 0或1
    private Integer computing;        //算力   value 0-10
    private Integer communication;        //用户-外设的名字
    private String Sensors;        //传感器种类
    private Integer workmanner;        //运作方式
    private String reserved;        //保留属性


    public Human() {
        super();
    }

    public Human(Integer peripheralsId, Integer identificationCode, Integer broadHeading, Integer subclass, Integer userId, Integer relation, String position, String sensing_Type, Integer taskId, Integer decision, Integer professional, Integer credit, Integer sharing, Integer computing, Integer communication, String sensors, Integer workmanner, String reserved) {
        this.peripheralsId = peripheralsId;
        this.identificationCode = identificationCode;
        this.broadHeading = broadHeading;
        this.subclass = subclass;
        this.userId = userId;
        this.relation = relation;
        this.position = position;
        this.sensing_Type = sensing_Type;
        this.taskId = taskId;
        this.decision = decision;
        this.professional = professional;
        this.credit = credit;
        this.sharing = sharing;
        this.computing = computing;
        this.communication = communication;
        Sensors = sensors;
        this.workmanner = workmanner;
        this.reserved = reserved;
    }

    public Integer getPeripheralsId() {
        return peripheralsId;
    }

    public void setPeripheralsId(Integer peripheralsId) {
        this.peripheralsId = peripheralsId;
    }

    public Integer getIdentificationCode() {
        return identificationCode;
    }

    public void setIdentificationCode(Integer identificationCode) {
        this.identificationCode = identificationCode;
    }

    public Integer getBroadHeading() {
        return broadHeading;
    }

    public void setBroadHeading(Integer broadHeading) {
        this.broadHeading = broadHeading;
    }

    public Integer getSubclass() {
        return subclass;
    }

    public void setSubclass(Integer subclass) {
        this.subclass = subclass;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRelation() {
        return relation;
    }

    public void setRelation(Integer relation) {
        this.relation = relation;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSensing_Type() {
        return sensing_Type;
    }

    public void setSensing_Type(String sensing_Type) {
        this.sensing_Type = sensing_Type;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getDecision() {
        return decision;
    }

    public void setDecision(Integer decision) {
        this.decision = decision;
    }

    public Integer getProfessional() {
        return professional;
    }

    public void setProfessional(Integer professional) {
        this.professional = professional;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Integer getSharing() {
        return sharing;
    }

    public void setSharing(Integer sharing) {
        this.sharing = sharing;
    }

    public Integer getComputing() {
        return computing;
    }

    public void setComputing(Integer computing) {
        this.computing = computing;
    }

    public Integer getCommunication() {
        return communication;
    }

    public void setCommunication(Integer communication) {
        this.communication = communication;
    }

    public String getSensors() {
        return Sensors;
    }

    public void setSensors(String sensors) {
        Sensors = sensors;
    }

    public Integer getWorkmanner() {
        return workmanner;
    }

    public void setWorkmanner(Integer workmanner) {
        this.workmanner = workmanner;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    @Override
    public String toString() {
        return "Human{" +
                "peripheralsId=" + peripheralsId +
                ", identificationCode=" + identificationCode +
                ", broadHeading=" + broadHeading +
                ", subclass=" + subclass +
                ", userId=" + userId +
                ", relation=" + relation +
                ", position='" + position + '\'' +
                ", sensing_Type='" + sensing_Type + '\'' +
                ", taskId=" + taskId +
                ", decision=" + decision +
                ", professional=" + professional +
                ", credit=" + credit +
                ", sharing=" + sharing +
                ", computing=" + computing +
                ", communication=" + communication +
                ", Sensors='" + Sensors + '\'' +
                ", workmanner=" + workmanner +
                ", reserved='" + reserved + '\'' +
                '}';
    }

}
