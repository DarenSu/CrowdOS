package com.example.mapper;

import com.example.entity.Liveness;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivenessMapper {


    ///   20200425
    Liveness Sel(int id) ;

    List<Liveness> SelByuserId(Integer userId);


    ///   20200525

    Liveness Enter(Liveness liveness);


    void add_Liveness(Liveness liveness);

    void update_Liveness(Liveness liveness);


    List<Liveness> SelByOnlineTime(Liveness liveness1);

    //20200702
    void update_deadlineTime(Liveness liveness2);


    // DarenSu   20200824
    void delete_Liveness(Liveness liveness);
    //20201214
    void deleteNull(Liveness liveness);


    //20201214 Delete empty data
    List<Liveness> SelALLFromLiveness();


    //20201214  自增id删除后未进行重置，故加此功能
    void updateTheLivenessid();
    //20201214  自增id删除后未进行重置，在resetTheLivenessid中更新了自增地的顺序，这块进行自增id的指针重定义
    void resetTheLivenessid();
}
