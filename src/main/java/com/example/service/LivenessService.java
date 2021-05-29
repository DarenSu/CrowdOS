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



    //   20200712
    public Liveness enterLiveness(Liveness liveness) /*throws LivenessServiceException*/ {
        System.out.println("欢迎来到：LivenessServer的enterLiveness");

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

        //  20200527
 /*
        Calendar cal=Calendar.getInstance();


        int year  =cal.get(Calendar.YEAR);
        int month =cal.get(Calendar.MONTH)+1;
        int day   =cal.get(Calendar.DAY_OF_MONTH);
        int hour  =cal.get(Calendar.HOUR_OF_DAY);
        int minute=cal.get(Calendar.MINUTE);
        int second=cal.get(Calendar.SECOND);


        String date=year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
        System.out.println("当前时间是---->"+date);

        java.text.SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");

        Date date1 = formatter.parse(date);

        liveness.setOnlineTime(date1);
*/

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        System.out.println(date);
        liveness.setOnlineTime(date);

        // DarenSu 20200816  Lead to judge，
        // DarenSu 20200814  Add the judgment，
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

        List<Liveness> liveness2 = livenessMapper.SelByuserId(liveness.getUserId());
        Liveness liveness1 = null;
        Liveness livenesstemp = liveness;
        int temp = -1;
        System.out.println("1");
        System.out.println(liveness2);
        System.out.println(liveness2.size());
//        System.out.println(liveness2.get(0));
//        System.out.println(liveness2.get(4));
//        System.out.println(liveness2.get(5));

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

            //  20200622
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
        //liveness.setTemp(1);
        liveness.setLivenessId(livenesstemp.getLivenessId());       //

        //System.out.println("5");
        System.out.println(liveness);
        //if(liveness.getOnlineTime() == null) {

        System.out.println("添加3--------"+liveness);
        livenessMapper.add_Liveness(liveness);
        //}

        System.out.println("更新4--------"+liveness);
        livenessMapper.update_Liveness(liveness);
        //if(liveness2.get(liveness2.size()-2).getOnlineTime() ==null ){
        // DarenSu   20200824
        //livenessMapper.delete_Liveness(liveness2.get(liveness2.size()-2));
        //}


        return livenessMapper.Enter(liveness);
    }


    ///   20200425
    public Liveness Sel(int id) {
        return livenessMapper.Sel(id);
    }


    public void add_Liveness(Liveness liveness) {
        livenessMapper.add_Liveness(liveness);
    }

    public List<Liveness> SelByOnlineTime(Liveness liveness1) {
        return livenessMapper.SelByOnlineTime(liveness1);
    }

    //20200702
    public void update_deadlineTime(Liveness liveness2) {
        livenessMapper.update_deadlineTime(liveness2);
    }

    public List<Liveness> SelByUserId(Integer userId) {
        return livenessMapper.SelByuserId(userId);
    }

    // DarenSu   20200824
    public void delete_Liveness(Liveness liveness) {
        livenessMapper.delete_Liveness(liveness);
    }

    public void updateTheLivenessid() {
        List<Liveness> livenessesList = livenessMapper.SelALLFromLiveness();


        livenessMapper.updateTheLivenessid();
    }
    //20201214
    public void resetTheLivenessid() {
        livenessMapper.resetTheLivenessid();
    }



}
