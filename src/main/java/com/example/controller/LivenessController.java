package com.example.controller;


import com.example.entity.Liveness;
import com.example.entity.User;
import com.example.service.*;
import com.example.util.SHA256;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author:DarenSu
 * @Date: 2020/4/27 修改
 * @Time: 14:42
 */

@RestController      //进行模块的注明，此处为控制模块
@RequestMapping("/liveness")
public class LivenessController {

    @Autowired
    private User_TaskService user_taskService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    @Autowired
    private LivenessService livenessService;

    @Autowired
    private Version_UpdatingService version_updatingService;


    private Object setuser1;

    ///   20200425      通过id获取所有数据
    @RequestMapping("getLiveness/{id}")
    public Liveness GetLiveness(@PathVariable int id) {
        return livenessService.Sel(id);
    }

    ///   20200525
    // 测试用登录测试，其实这个类不需要进行登陆的，之所以这样是为了进行测试
    // 为了测试service里面如何进行逻辑操作
    //   DarenSu     20200424
    //   活跃度检测，主要进行对每个用户的活跃度进行检测
    //   输入参数：userId，从而反馈回来该用户的活跃度，
    //   并且会针对每个登陆的用户的活跃度进行变化，有登陆响应时候，会立即进行日、周、月、年活跃度的更新，登录时间的更新，
    //     ###############          切记    ######    退出时间未更新   已解决已更新，见logoutUser          #####################
    //   返回参数：日活跃度、周活跃度、月活跃度、年活跃度都可以进行返回
    //   该函数的返回参数为日活跃度，就是返回该用户今天的活跃度
    @RequestMapping(value = "enterLiveness", method = RequestMethod.POST)
    public ResponseEntity<Liveness> EnterLiveness( @RequestBody  Liveness liveness){

        if (liveness.getUserId() == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        final ResponseEntity<Liveness> livenessResponseEntity = new ResponseEntity<>(livenessService.enterLiveness(liveness), HttpStatus.OK);
        return livenessResponseEntity;
    }

    //前后台对接    2019.6.22  自己写的有点问题  自己测试走表单不需要requestbody，前后台对接使用的是json数据，需要使用requestbody
    @RequestMapping(value="addLiveness",method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Liveness> add_Task(@RequestBody Liveness liveness) {
//		Task task1 = new Task();
//		System.out.println(task1);
        System.out.println(liveness);
        livenessService.add_Liveness(liveness);
        System.out.println("asdfsdfwef");

        System.out.println(liveness);
        return new ResponseEntity<>(liveness, HttpStatus.OK);
        /*ResponseEntity<Liveness> tResponseEntity = new ResponseEntity<>(liveness, HttpStatus.OK);
        return tResponseEntity;*/
        //return "OK";
    }
    //           20200624
    // 退出登出记录，用户已有登记记录，并且可以等级其登陆的日期
    // 然而目前还没有退出登录的标记，无法记录其退出的时间节点
    // 由于目前没有使用token，那么目前就是使用比较简单的方式进行退出的时间登录
    // 思路：前端请求退出的要求，触发该功能，然后登记其推出的时间，并将其退出的时间进行标记
    // 切记：前端传来的liveness里面不含有livenessId的，里面具体有什么是看前段传入什么的
    @RequestMapping(value = "logoutUser", method = RequestMethod.POST)
    public ResponseEntity<Liveness> logoutUser(/*@RequestBody*/ Liveness liveness){  //liveness里面最少包括userId
        //首先获取前端传送的liveness在数据库中真正对应的那一组最新的数据
        System.out.println("活跃度检测系列 - 退出登录标记");
        System.out.println(liveness);
        if(livenessService.SelByUserId(liveness.getUserId()).size() != 0){  //判断此liveness是否在数据库中存在
            List<Liveness> livenessListtemp = livenessService.SelByUserId(liveness.getUserId());
            Liveness livenesstemp = livenessListtemp.get(livenessListtemp.size() - 1);
            System.out.println("0");
            System.out.println(livenessListtemp.get(livenessListtemp.size() - 1));

            int id = livenesstemp.getLivenessId();
            Liveness liveness1 = livenesstemp;



            //取值，从数据库中查询此id对应的登陆时间，然后针对userId和onlineTime查询所有的数据
            Date onlineDate = liveness1.getOnlineTime();
            List<Liveness> livenessesList = livenessService.SelByOnlineTime(liveness1);
            System.out.println("1");
            System.out.println(livenessesList.get(0));  //测试，查看List数据的输出顺序，结论：o对应最久的数据，size()-1对应最新的数据
            //20200702  赋值， 将这些取出的数据进行退出时间的更新
            Calendar calendar = Calendar.getInstance();
            Date deadlineDate = calendar.getTime();
            System.out.println(deadlineDate);
//            livenessesList.get(livenessesList.size() - 1).setDeadlineTime(deadlineDate);
//            livenessService.update_deadlineTime(livenessesList.get(livenessesList.size() - 1));
            for (int i =0 ; i < livenessesList.size() ; i++){
                Liveness liveness2 = livenessesList.get(i);
                liveness2.setDeadlineTime(deadlineDate);
                System.out.println(liveness2.getDeadlineTime());
                livenessService.update_deadlineTime(liveness2);
                System.out.println("##############");
                System.out.println(livenessService.SelByOnlineTime(liveness2).get(livenessService.SelByOnlineTime(liveness2).size()-1).getDeadlineTime());
            }
            //测试用，测试其是否进行了更新
            System.out.println("2");
            System.out.println(livenessService.Sel(livenessesList.get(livenessesList.size() - 1).getLivenessId()));

            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {//没有该用户的时候，返回登陆总数为-1的虚拟用户数据
            Liveness liveness11 = new Liveness();
            liveness11 = null;
            //liveness11.setTotal(0);    ///重点切记：123是常量，456是常量，+123和 -456 都不是常量，而是一个运算符后面跟一个常量组成的表达式
                                         ///int中一般很少赋值使用0，其0就是null
            //  暂时没有找到可以进行自己写东西返回的方法
            return new ResponseEntity<>(liveness11,HttpStatus.NOT_FOUND);
        }
        //return new ResponseEntity<>(HttpStatus.OK);
    }
    @RequestMapping(value = "delLiveness", method=RequestMethod.POST)
	public ResponseEntity<Liveness> delLiveness(/*@RequestBody*/ Liveness liveness) {
		///先检查是否有这个ID，有的话可以执行删除操作，没有的话直接返回NOT_FOUND
        System.out.println("删除操作");

		livenessService.delete_Liveness(liveness);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	//20201214  自增id删除后未进行重置，故加此功能
    @RequestMapping(value = "resetTheLivenessid", method=RequestMethod.POST)
    public ResponseEntity<Liveness> resetTheLivenessid() {
        livenessService.updateTheLivenessid();
        return new ResponseEntity<>(HttpStatus.OK);
    }





    //   DarenSu     20200424
    //   活跃度检测，主要进行对每个用户的活跃度进行检测
    //   输入参数：userId，从而反馈回来该用户的活跃度，
    //   并且会针对每个登陆的用户的活跃度进行变化，有登陆响应时候，会立即进行日、周、月、年活跃度的更新，登录时间的更新，
    //     ###############          切记    ######    退出时间未更新           #####################
    //   返回参数：日活跃度、周活跃度、月活跃度、年活跃度都可以进行返回
    //   该函数的返回参数为日活跃度，就是返回该用户今天的活跃度
    @RequestMapping(value="enterUser", method=RequestMethod.POST)
    public ResponseEntity<User> EnterUser(/*@RequestBody*/ User user) {
        //return userService.SelInfo(userName).toString();
        System.out.println(user);
        //System.out.println(userService.EnterUser(user));

        //////2020.04.21   密码加密
        String passWordtemp = user.getPassWord();
        ////SHA256加密

        if(StringUtils.isEmpty(passWordtemp)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        passWordtemp = SHA256.getSHA256StrJava(passWordtemp);

        System.out.println(passWordtemp);
        user.setPassWord(passWordtemp);
        System.out.println(user.getPassWord());



        ///没有判断，添加错误的返回判断    2019.7.15
        User check_userName = userService.check(user);    /// 2019.9.25 - check函数有改动，用户名和真实姓名都可以进行登录

        if (check_userName == null) { //没有该用户的情况
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //return userService.EnterUser(user);
        else { //有用户的情况下
            return new ResponseEntity<>(userService.EnterUser(user), HttpStatus.OK);
        }
    }





    ///通过id获取所有数据
//    @RequestMapping("getLiveness/{id}")
//    public User GetUser(@PathVariable int id) {
//        return userService.Sel(id);
//    }


    /////2020.6.17 用户注册即添加+还有检测名字是否重复
    @RequestMapping(value="addUser", method=RequestMethod.POST)
    public ResponseEntity<User> addUser(/*@RequestBody*/ User user) {
        User check_userName = userService.checkLogin(user);

        //////2020.04.21   密码加密
        //////2020.06.18   密码加密修改篇
        String passWordtemp = user.getPassWord();
        ////SHA256加密

        if(StringUtils.isEmpty(passWordtemp)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        passWordtemp = SHA256.getSHA256StrJava(passWordtemp);

        System.out.println(passWordtemp);
        user.setPassWord(passWordtemp);
        System.out.println(user.getPassWord());


        if (check_userName == null){
            userService.addUser(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}
