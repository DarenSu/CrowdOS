package com.example.service.serviceInterface;

import com.example.entity.Liveness;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LivenessServiceInterface {



    ///   20200525
    // 测试用登录测试，其实这个类不需要进行登陆的，之所以这样是为了进行测试
    // 为了测试service里面如何进行逻辑操作
    //   20200712  目前已完成的功能如下：可以进行活跃度表的更新，用户登录便可以进行数据的记录
    public Liveness enterLiveness(Liveness liveness) ;


    ///   20200425      通过id获取所有数据
    public Liveness Sel(int id) ;


    public void add_Liveness(Liveness liveness) ;

    public List<Liveness> SelByOnlineTime(Liveness liveness1);

    //20200702  赋值， 将这些取出的数据进行退出时间的更新
    public void update_deadlineTime(Liveness liveness2) ;

    public List<Liveness> SelByUserId(Integer userId);

    // DarenSu   20200824  UserController里的登录函数enterUser，由于初次登陆需要直接在其中进行liveness活跃度表的该用户记录添加（只有userId）
    //  但是她引用的添加函数，LivenessService中enterLiveness已经有了用户记录的添加操作，并且该函数还被其它函数调用
    //  所以，需要删除enterUser里的添加的记录
    //  但是，这个活跃度更新的函数属于公共函数，直接在这里面进行修改的话，造成的bug较多，所以在enterUser理进行修改
    public void delete_Liveness(Liveness liveness) ;


    //20201214  自增id删除后未进行重置，故加此功能,对所有的id进行重置，并且进行自增id的指针重定义
    //思路便是，找到所有的数据，然后找出size，根据size()大小，对id重新自增，然后一个个更新，最后对自增的id指针进行充值到size()+1
    public void updateTheLivenessid();



    //20201214  自增id删除后未进行重置，在resetTheLivenessid中更新了自增地的顺序，这块进行自增id的指针重定义
    public void resetTheLivenessid() ;
}
