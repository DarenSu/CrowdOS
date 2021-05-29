package com.example.entity;

public class Human {



    private Integer peripheralsId;
    private Integer identificationCode; //Entity ID
    private Integer broadHeading;       //Entity types .   0-?    1- people   2-machine    3-thing
    private Integer subclass;           //Small entity class
    private Integer userId;
    private Integer relation;
    private String  position;
    private String sensing_Type;
    private Integer taskId;
    private Integer decision;        //decision-making  value 0-10
    private Integer professional;        //  value 0-10
    private Integer credit;        //credit values.  value 0-10
    private Integer sharing;        // 0或1
    private Integer computing;        //computing power   value 0-10
    private Integer communication;        //user-peripheral name
    private String Sensors;        //sensor type
    private Integer workmanner;
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
