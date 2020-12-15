package com.example.service;

import com.example.entity.Liveness;
import com.example.mapper.LivenessMapper;
import com.example.util.CheckLivenessIsEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class LivenessService {
    @Autowired
    LivenessMapper livenessMapper;


    ///   20200525
    // 测试用登录测试，其实这个类不需要进行登陆的，之所以这样是为了进行测试
    // 为了测试service里面如何进行逻辑操作
    //   20200712  目前已完成的功能如下：可以进行活跃度表的更新，用户登录便可以进行数据的记录
    public Liveness enterLiveness(Liveness liveness) /*throws LivenessServiceException*/ {
        System.out.println("欢迎来到：LivenessServer的enterLiveness");
        //20201214 由于活跃度里面的函数有一些是空的，所以准备删除这些有很多null的数据
        {
            System.out.println("是否进行空数据的删除开始：");
            Integer temptest = 0;
            List<Liveness> livenessesList = livenessMapper.SelALLFromLiveness();
            for (int i = 0; i < livenessesList.size() - 1; i++) {
                Liveness liveness2 = livenessesList.get(i);
                if (CheckLivenessIsEmpty.checkLivenessIsEmpty(liveness2) == 0) {//0删除，1不需要删除
                    System.out.println("进行Liveness数据表中空数据的删除：");
                    livenessMapper.deleteNull(liveness2);
                    temptest++;
                    System.out.println("删除成功后需要对Liveness数据表的自增ID进行重置");

                }
                ;
            }
            if (temptest == 0) {
                System.out.println("没有进行Liveness表空数据的删除");
            } else {
                System.out.println("对Liveness中的空数据进行删除");
            }
        }
        /*try {
              Liveness liveness1 = liveness  ;
        }
        catch (NullPointerException e){
            throw new LivenessServiceException(LivenessServiceException.PASSWORD_ERROR);
        }*/

        //  20200527         获取当前时间：
 /*       //创建Calendar对象
        Calendar cal=Calendar.getInstance();

        //用Calendar类提供的方法获取年、月、日、时、分、秒
        int year  =cal.get(Calendar.YEAR);   //年
        int month =cal.get(Calendar.MONTH)+1;  //月  默认是从0开始  即1月获取到的是0
        int day   =cal.get(Calendar.DAY_OF_MONTH);  //日，即一个月中的第几天
        int hour  =cal.get(Calendar.HOUR_OF_DAY);  //小时
        int minute=cal.get(Calendar.MINUTE);   //分
        int second=cal.get(Calendar.SECOND);  //秒

        //拼接成字符串输出
        String date=year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
        System.out.println("当前时间是---->"+date);

        java.text.SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");

        Date date1 = formatter.parse(date);

        liveness.setOnlineTime(date1);
*/
        //  20200628    Date数据类型的时间获取
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        System.out.println(date);
        liveness.setOnlineTime(date);

        // DarenSu 20200816  前置判断，需要判断传进来的userId不是空的，是空的就不要调用这个函数
        // DarenSu 20200814  添加判断，若是之前没有该用户的UserId,那么我们就需要进行判断，然后手动添加
        {
            if (livenessMapper.SelByuserId(liveness.getUserId()) == null) {
                System.out.println("没有，我自己添加了");
                Liveness liveness1 = new Liveness();
                liveness1.setUserId(liveness.getUserId());
                System.out.println("添加2--------"+liveness1);
                livenessMapper.add_Liveness(liveness1);
                System.out.println("添加了！！！");
            }
        }


        //           20200628         DarenSu
        // 获得现在传入的liveness对应的用户的userId，然后通过userId获取该用户的最新的数据
        //然后用着最新的数据，进行活跃度的更新，更新完成后，将更新的数据进行存储
        List<Liveness> liveness2 = livenessMapper.SelByuserId(liveness.getUserId());
        Liveness liveness1 = null;
        Liveness livenesstemp = liveness;  //后面由于从数据库中取值主键有值，输入的话数据库重复造成bug
        int temp = -1;
        System.out.println("1");
        System.out.println(liveness2);
        System.out.println(liveness2.size());
//        System.out.println(liveness2.get(0));
//        System.out.println(liveness2.get(4));
//        System.out.println(liveness2.get(5));
        // 寻找temp最大的值，那么这个最大的temp对应的liveness便是最新的数据
        /*for (int i=0 ; i<liveness2.size()  ;i++  ){
            System.out.println("进来+1");
            if(temp <= liveness2.get(i).getTotal()){
                System.out.println("进来+2");
                temp = liveness2.get(i).getTotal();
            }
            System.out.println("3");
        }
        System.out.println("2");
        System.out.println(temp);
        System.out.println(liveness2);
        for (int i = 0; i<liveness2.size()  ;i++  ){
            if (temp == liveness2.get(i).getTotal()){
                liveness1 = liveness2.get(i);
            }
        }*/
        if (liveness2.size() == 0) {
            liveness1 = liveness;
            liveness.setTemp(1);
            liveness.setTotalMouth(1);
            liveness.setTotalWeek(1);
            liveness.setTotalYear(1);
            liveness.setTotal(1);

        } else {
            liveness1 = liveness2.get(liveness2.size() - 1);
            //liveness.setTotal(liveness1.getTotal() + 1);
            System.out.println("3");
            System.out.println(liveness1);

            //  20200622    做活跃度更新用
            //  每次用户登录后，进行更新操作
            //  如果这个用户第一次记录，那么其除了userId之外都是null，容易报错
            //  分三种情况，第一种是总活跃度为null，并且只有这一个记录，那么其活跃度记1
            //  第二种情况，最新的数据的总活跃度为null，但是其还有其他的数据，那么取次新的数据的活跃度+1
            //  第三种情况，直接在最新的数据基础上+1即可
            if (liveness1.getTotal() == null && liveness2.size() == 1) {
                liveness.setTemp(1);
                liveness.setTotalMouth(1);
                liveness.setTotalWeek(1);
                liveness.setTotalYear(1);
                liveness.setTotal(1);
            } else if (liveness1.getTotal() == null) {
                Liveness livenessTemptest = liveness2.get(liveness2.size() - 2);
                liveness.setTemp(livenessTemptest.getTemp() + 1);
                liveness.setTotalMouth(livenessTemptest.getTotalMouth() + 1);
                liveness.setTotalWeek(livenessTemptest.getTotalWeek() + 1);
                liveness.setTotalYear(livenessTemptest.getTotalYear() + 1);
                liveness.setTotal(livenessTemptest.getTotal() + 1);
            } else {

                liveness.setTemp(liveness1.getTemp() + 1);
                liveness.setTotalMouth(liveness1.getTotalMouth() + 1);
                liveness.setTotalWeek(liveness1.getTotalWeek() + 1);
                liveness.setTotalYear(liveness1.getTotalYear() + 1);
                liveness.setTotal(liveness1.getTotal() + 1);
            }
        }

        System.out.println("4");
        //System.out.println(liveness1);

        //liveness = liveness1;
        //liveness.setTemp(1);           //测试用，用作测试是否可以进行数据的重置，测试成功
        liveness.setLivenessId(livenesstemp.getLivenessId());       //将之前定义的livenesstemp的livenessId赋值到参数liveness里面，

        //System.out.println("5");                                                            // 修正livenessId，使其可以在数据库中正常自增
        System.out.println(liveness);
        //if(liveness.getOnlineTime() == null) {

        System.out.println("添加3--------"+liveness);
        livenessMapper.add_Liveness(liveness);
        //}

        System.out.println("更新4--------"+liveness);
        livenessMapper.update_Liveness(liveness);
        //if(liveness2.get(liveness2.size()-2).getOnlineTime() ==null ){
        // DarenSu   20200824  UserController里的登录函数enterUser，由于初次登陆需要直接在其中进行liveness活跃度表的该用户记录添加（只有userId）
        //  但是她引用的添加函数，LivenessService中enterLiveness已经有了用户记录的添加操作，并且该函数还被其它函数调用
        //  所以，需要删除enterUser里的添加的记录
        //  但是，这个活跃度更新的函数属于公共函数，直接在这里面进行修改的话，造成的bug较多，所以在enterUser理进行修改
        //livenessMapper.delete_Liveness(liveness2.get(liveness2.size()-2));
        //}


        return livenessMapper.Enter(liveness);
    }


    ///   20200425      通过id获取所有数据
    public Liveness Sel(int id) {
        return livenessMapper.Sel(id);
    }


    public void add_Liveness(Liveness liveness) {
        livenessMapper.add_Liveness(liveness);
    }

    public List<Liveness> SelByOnlineTime(Liveness liveness1) {
        return livenessMapper.SelByOnlineTime(liveness1);
    }

    //20200702  赋值， 将这些取出的数据进行退出时间的更新
    public void update_deadlineTime(Liveness liveness2) {
        livenessMapper.update_deadlineTime(liveness2);
    }

    public List<Liveness> SelByUserId(Integer userId) {
        return livenessMapper.SelByuserId(userId);
    }

    // DarenSu   20200824  UserController里的登录函数enterUser，由于初次登陆需要直接在其中进行liveness活跃度表的该用户记录添加（只有userId）
    //  但是她引用的添加函数，LivenessService中enterLiveness已经有了用户记录的添加操作，并且该函数还被其它函数调用
    //  所以，需要删除enterUser里的添加的记录
    //  但是，这个活跃度更新的函数属于公共函数，直接在这里面进行修改的话，造成的bug较多，所以在enterUser理进行修改
    public void delete_Liveness(Liveness liveness) {
        livenessMapper.delete_Liveness(liveness);
    }
    //20201214  自增id删除后未进行重置，故加此功能,对所有的id进行重置，并且进行自增id的指针重定义
    public void updateTheLivenessid() {
        List<Liveness> livenessesList = livenessMapper.SelALLFromLiveness();
        //思路便是，找到所有的数据，然后找出size，根据size()大小，对id重新自增，然后一个个更新，最后对自增的id指针进行充值到size()+1

        livenessMapper.updateTheLivenessid();
    }
    //20201214  自增id删除后未进行重置，在resetTheLivenessid中更新了自增地的顺序，这块进行自增id的指针重定义
    public void resetTheLivenessid() {
        livenessMapper.resetTheLivenessid();
    }



}
