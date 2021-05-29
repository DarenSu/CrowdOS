package com.example.util;


import com.example.DemoApplication;

/**
 * author: Created by lsw on 2020/06/24 11:25
 * description:Abnormal interrupt handling
  * 2020/07/02 modify
  * 2020/07/14 modify again
 */


public class MYApplication extends DemoApplication {

    private static MYApplication app;
 /*   public static SysCfgInfo config = new SysCfgInfo();
    private long onTouchTime;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        BugHandler.getInstance().init(this,null,null);
        Thread.setDefaultUncaughtExceptionHandler(BugHandler.getInstance());

        CrashReport.initCrashReport(getApplicationContext(), "665fa6736b", true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

    }

    public static MYApplication getApp() {
        return app;
    }

    public void setTouchTime(long time){
        LogUtil.e( time +"   点击屏幕时间 --- " );
        this.onTouchTime = time;
    }

    public long getOnTouchTime() {
        return onTouchTime;
    }*/
}
