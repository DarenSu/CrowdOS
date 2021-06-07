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
 * @Date: 2020/4/27
 * @Time: 14:42
 */

@RestController      //To indicate the module, here is the control module
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

    ///   20200425      Get all data by id
    @RequestMapping("getLiveness/{id}")
    public Liveness GetLiveness(@PathVariable int id) {
        return livenessService.Sel(id);
    }

    ///   20200525
    // The login test is used for testing. In fact, this class does not need to be logged in.  The reason for this is
    // for testing.
    // In order to test how to perform logical operations in the service
    //   DarenSu     20200424
    //   Activity detection, mainly to detect the activity of each user
    //   Input parameter: userId, so as to feedback the user's activity，
    //   And changes will be made to the activity of each logged-in user. When there is a login response, the daily,
    //   weekly, monthly, and annual activity will be updated immediately, and the login time will be updated.，
    //     ###############          Remember    ######    The logout time has not been updated. It has been resolved
    //     and updated, see logoutUser          #####################
    //   Return parameters: daily activity, weekly activity, monthly activity, and annual activity can all be returned
    //   The return parameter of this function is daily activity, which is to return the user’s activity today
    @RequestMapping(value = "enterLiveness", method = RequestMethod.POST)
    public ResponseEntity<Liveness> EnterLiveness( @RequestBody  Liveness liveness){

        if (liveness.getUserId() == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        final ResponseEntity<Liveness> livenessResponseEntity = new ResponseEntity<>(livenessService.enterLiveness(liveness), HttpStatus.OK);
        return livenessResponseEntity;
    }

    //Front-end and back-end  2019.6.22 There is a problem with writing by myself. I don’t need
    // @Requestbody to
    // test the form by myself. The front-end and back-end docking uses json data, so requestbody is required.
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
    // Exit the logout record, the user has a registered record, and can rank the date of their login
    // However, there is no sign of logout, and the time node of its logout cannot be recorded.
    // Since token is not currently used, it is a relatively simple way to log out at the time of login
    // Idea: The front-end requests the exit request, triggers the function, then registers its launch time, and
    // marks the exit time
    // Remember: the liveness from the front end does not contain the livenessId, what is in it depends on what is
    // passed in the previous paragraph
    @RequestMapping(value = "logoutUser", method = RequestMethod.POST)
    public ResponseEntity<Liveness> logoutUser(@RequestBody Liveness liveness){  //liveness at least contains userId
        //First get the latest set of data in the database corresponding to the liveness transmitted by the front end
        System.out.println("活跃度检测系列 - 退出登录标记");
        System.out.println(liveness);
        if(livenessService.SelByUserId(liveness.getUserId()).size() != 0){   //check whether liveness exists in the
            // database
            List<Liveness> livenessListtemp = livenessService.SelByUserId(liveness.getUserId());
            Liveness livenesstemp = livenessListtemp.get(livenessListtemp.size() - 1);
            System.out.println("0");
            System.out.println(livenessListtemp.get(livenessListtemp.size() - 1));

            int id = livenesstemp.getLivenessId();
            Liveness liveness1 = livenesstemp;



            // get value, query the login time of this id from database, then query all of the data corresponding to
            // userId and onlineTime
            Date onlineDate = liveness1.getOnlineTime();
            List<Liveness> livenessesList = livenessService.SelByOnlineTime(liveness1);
            System.out.println("1");
            System.out.println(livenessesList.get(0));              // test, view output order of List data, conclusion: the data at zero index is the oldest,
            // the data at size()-1 index is the latest
            //20200702  assignment, update the logout time of these fetched data
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
            // use for test, test whether it has been updated
            System.out.println("2");
            System.out.println(livenessService.Sel(livenessesList.get(livenessesList.size() - 1).getLivenessId()));

            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {// when there is no such user, return the data of virtual user whose login
            // amount is -1
            Liveness liveness11 = new Liveness();
            liveness11 = null;
            //liveness11.setTotal(0);      ///Important to remember: 123 is a constant, 456 is a constant, +123 and
            // -456 are not constants, but an
            // expression composed of an operator followed by a constant
            // Generally, 0 is rarely used in int assignment, and 0 is null
            // how to write automatically is yet to solve
            return new ResponseEntity<>(liveness11,HttpStatus.NOT_FOUND);
        }
        //return new ResponseEntity<>(HttpStatus.OK);
    }
    @RequestMapping(value = "delLiveness", method=RequestMethod.POST)
	public ResponseEntity<Liveness> delLiveness(/*@RequestBody*/ Liveness liveness) {
        ///First check whether there is this ID, if there is, you can perform the delete operation, if not, return
        // to NOT_FOUND directly
        System.out.println("删除操作");

		livenessService.delete_Liveness(liveness);
		return new ResponseEntity<>(HttpStatus.OK);
	}

    //20201214  Since the ID is deleted, it is not reset, so this function is added
    @RequestMapping(value = "resetTheLivenessid", method=RequestMethod.POST)
    public ResponseEntity<Liveness> resetTheLivenessid() {
        livenessService.updateTheLivenessid();
        return new ResponseEntity<>(HttpStatus.OK);
    }





    // just for test
    //   Activity detection, mainly to detect the activity of each user
    //   Input parameter: userId, so as to feedback the user's activity
    //   And changes will be made to the activity of each logged-in user. When there is a login response, the daily,
    //   weekly, monthly, and annual activity will be updated immediately, and the login time will be updated.
    //     ###############          Remember    ######    Exit time not updated           #####################
    //   Return parameters: daily activity, weekly activity, monthly activity, and annual activity can all be returned
    //   The return parameter of this function is daily activity, which is to return the user’s activity today
    @RequestMapping(value="enterUser", method=RequestMethod.POST)
    public ResponseEntity<User> EnterUser(/*@RequestBody*/ User user) {
        //return userService.SelInfo(userName).toString();
        System.out.println(user);
        //System.out.println(userService.EnterUser(user));

        //////2020.04.21   password encryption
        String passWordtemp = user.getPassWord();
        ////SHA256 encryption

        if(StringUtils.isEmpty(passWordtemp)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        passWordtemp = SHA256.getSHA256StrJava(passWordtemp);

        System.out.println(passWordtemp);
        user.setPassWord(passWordtemp);
        System.out.println(user.getPassWord());



        /// No conditional statement, add the conditional judge for wrong return
        User check_userName = userService.check(user);    /// 2019.9.25 - The check function has been changed, and
        // both the user name and real name can be logged in

        if (check_userName == null) { // The case when there is no such user
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //return userService.EnterUser(user);
        else { //when there is the user
            return new ResponseEntity<>(userService.EnterUser(user), HttpStatus.OK);
        }
    }





    // just for test

    /// obtain all of the data via id
//    @RequestMapping("getLiveness/{id}")
//    public User GetUser(@PathVariable int id) {
//        return userService.Sel(id);
//    }


    /////2020.6.17 User registration is added + and check whether the name is duplicated
    @RequestMapping(value="addUser", method=RequestMethod.POST)
    public ResponseEntity<User> addUser(/*@RequestBody*/ User user) {
        User check_userName = userService.checkLogin(user);

        //////2020.04.21   password encryption
        //////2020.06.18   password encryption modification
        String passWordtemp = user.getPassWord();
        ////SHA256 encryption

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
