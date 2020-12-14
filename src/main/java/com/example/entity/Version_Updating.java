package com.example.entity;

/**
 * @Author:0xOO
 * @Date: 2019/10/28 0026
 * @Time: 14:39
 */
/**
 * @Author:DarenSu
 * @Date: 2019/10/28 修改
 * @Time: 14:42
 */



public class Version_Updating {

    private Integer versionId;                          //序号
    private Integer versionCode;                        //版本代码
    private String versionName;                         //新版本号
    private String versionAddress;                      //旧版本
    private String apkName;                             //apk名字

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
