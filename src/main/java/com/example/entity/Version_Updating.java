package com.example.entity;


/**
 * @Author:DarenSu
 * @Date: 2019/10/28
 * @Time: 14:42
 */



public class Version_Updating {

    private Integer versionId;
    private Integer versionCode;                        //Version of the code
    private String versionName;                         //The new version number
    private String versionAddress;                      //The old version number
    private String apkName;

    public Version_Updating() {
        super();
    }

    public Version_Updating(Integer versionId, Integer versionCode, String versionName, String versionAddress, String apkName) {
        this.versionId = versionId;
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.versionAddress = versionAddress;
        this.apkName = apkName;
    }

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionAddress() {
        return versionAddress;
    }

    public void setVersionAddress(String versionAddress) {
        this.versionAddress = versionAddress;
    }

    public String getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    @Override
    public String toString() {
        return "Version_Updating{" +
                "versionId=" + versionId +
                ", versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                ", versionAddress='" + versionAddress + '\'' +
                ", apkName='" + apkName + '\'' +
                '}';
    }
}
