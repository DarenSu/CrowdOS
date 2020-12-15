package com.example.mapper;

import com.example.entity.Liveness;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivenessMapper {


    ///   20200425      通过id获取所有数据
    Liveness Sel(int id) ;

    List<Liveness> SelByuserId(Integer userId);


    ///   20200525
    // 测试用登录测试，其实这个类不需要进行登陆的，之所以这样是为了进行测试
    // 为了测试service里面如何进行逻辑操作
    Liveness Enter(Liveness liveness);


    void add_Liveness(Liveness liveness);

    void update_Liveness(Liveness liveness);


    List<Liveness> SelByOnlineTime(Liveness liveness1);

    //20200702  赋值， 将这些取出的数据进行退出时间的更新
    void update_deadlineTime(Liveness liveness2);


    // DarenSu   20200824  UserController里的登录函数enterUser，由于初次登陆需要直接在其中进行liveness活跃度表的该用户记录添加（只有userId）
    //  但是她引用的添加函数，LivenessService中enterLiveness已经有了用户记录的添加操作，并且该函数还被其它函数调用
    //  所以，需要删除enterUser里的添加的记录
    //  但是，这个活跃度更新的函数属于公共函数，直接在这里面进行修改的话，造成的bug较多，所以在enterUser理进行修改
    void delete_Liveness(Liveness liveness);
    //20201214 由于活跃度里面的函数有一些是空的，所以准备删除这些有很多null的数据
    void deleteNull(Liveness liveness);


//20201214 由于活跃度里面的函数有一些是空的，所以准备删除这些有很多null的数据
    List<Liveness> SelALLFromLiveness();


    //20201214  自增id删除后未进行重置，故加此功能
    void updateTheLivenessid();
    //20201214  自增id删除后未进行重置，在resetTheLivenessid中更新了自增地的顺序，这块进行自增id的指针重定义
    void resetTheLivenessid();
}
