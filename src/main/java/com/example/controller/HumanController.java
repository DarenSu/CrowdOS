package com.example.controller;


import com.example.entity.Human;
import com.example.entity.Liveness;
import com.example.entity.User;
import com.example.service.HumanService;
import com.example.service.LivenessService;
import com.example.service.TaskService;
import com.example.service.UserService;
import com.example.service.serviceInterface.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//import org.apache.commons.codec.digest.DigestUtils;



/**
 * @Author:DarenSu
 * @Date: 2019/6/13
 * @Time: 14:42
 */

@RestController      //To indicate the module, here is the control module
@RequestMapping("/human")

public class HumanController {

    @Autowired
    private HumanService humanService;
    @Autowired
    private UserService userService;
    @Autowired
    private TaskService bean_TaskService;
    @Autowired
    private LivenessService livenessService;
    @Autowired
    private UserServiceInterface userServiceInterface;


    private Object setuser1;



    @RequestMapping("getHuman/{peripheralsId}")
    public User GetHuman(@PathVariable int peripheralsId) {
        int peripheralsId_temp = peripheralsId;
        // the three most significant bits are used to identify device types, 000 - human 001 - machine 010 - object
        // id has 32 bits in total, if the most significant bit is 0, the next two bits are compared, which
        // represents human, machine and object as 0, 1, 2 in decimal form.
        int temp_two = peripheralsId_temp;
        temp_two = temp_two>>29;

        int state = 0;

        switch(temp_two){
            case 0 :// this case means human, more detailed classification and parse are implemented
                // by the following statements
                // the implementation statement
                return humanService.Sel1(peripheralsId);
            case 1 :// this case means the device is machine, more detailed classification and parse are implemented
                // by the following statements
                // the implementation statement
                return humanService.Sel2(peripheralsId); // optional
            // any number of case clauses is allowed
            case 2 :// this case means the device is object, more detailed classification and parse are
                // implemented
                // by the following statements
                // the implementation statement
                return humanService.Sel3(peripheralsId);  // optional;
            default : // the input device type is wrong, do some mistake handling in the default clause
                return null;// the implementation statement
        }
    }


    @RequestMapping("getUser/{id}")
    public User GetUser(@PathVariable int id) {
        return userService.Sel(id);
    }

    //20201215   the test of Server layer is a two-layer test
    @RequestMapping("getuser/{id}")
    public User getuser(@PathVariable int id) {
        return userServiceInterface.Sel(id);
    }



    //2019.7.2 modification: single result display is adapted to multiple results display
    @RequestMapping("getUserInfo/{userName}")
    public List<User> GetUserInfo(@PathVariable String userName) {
        //return userService.SelInfo(userName).toString();
        System.out.println(userName);
        System.out.println(userService.SelInfo(userName));

        return userService.SelInfo(userName);
    }

    // 2019.9.16 modification: return the last ten records of user info one time from database
    @RequestMapping("getUserRank")
    public ResponseEntity<List<User>> GetUserRank() {

        if (userService.getUserRank() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<List<User>>(userService.getUserRank(), HttpStatus.OK);
        }
    }
    //20201211 test check function
    @RequestMapping(value = "CheckTest", method = RequestMethod.POST)
    public ResponseEntity<User> CheckTest(@RequestBody User user) {


        return new ResponseEntity<>(userService.check(user), HttpStatus.OK);
    }


    //// user login 2019.9.25 userName and realName can be both used to
    // log in user account
    /// requestbody is not necessarily required if you test form transfer by yourself, however, if the front and back
    // end employ json format to transfer data, requestbody is required
    /// 2019.9.25 ---pending insersion---
    @RequestMapping(value = "addHuman", method = RequestMethod.POST)
    public ResponseEntity<User> AddHuman(@RequestBody Human human) {
        //return userService.SelInfo(userName).toString();
        Liveness liveness = new Liveness();

        System.out.println(human);
        Human humanTemp1= human;

        User user = new User();
        System.out.println(user);
        //System.out.println(userService.EnterUser(user));

        // 20201211 User registration, the account password used by the previous user is not encrypted,
        // and the latter is encrypted, causing the previously registered user to be unable to log in normally
        //So it is currently ready to use, there are two ways, either encrypted or unencrypted

        User userTemp1 = user;


        ////2020.04.21   encrypted by password
        String passWordtemp = user.getPassWord();
        ////SHA256
//------------>>>>>>>>>>>20201214-As encryption involves the problem of judging the degree of activity, temporarily comment------------------>>>>>>>>>>>>>>>>>>>>>>>------------------------------->>>>>>>>>>>
//        if (StringUtils.isEmpty(passWordtemp)) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        passWordtemp = SHA256.getSHA256StrJava(passWordtemp);

        System.out.println(passWordtemp);
        User userTemp2 = new User();
        userTemp2.setPassWord(passWordtemp);
        userTemp2.setUserName(user.getUserName());

        //user.setPassWord(passWordtemp);
        System.out.println(userTemp2.getPassWord());


        ///No conditional statement, add condition judge for the wrong result case    2019.7.15
        User check_userName = userService.check(userTemp2);    /// 2019.9.25 - The check function has been changed,
        // and both the user name and real name can be logged in
        //20201211 User registration, the account password used by the previous user is not encrypted,
        // and the latter is encrypted, causing the previously registered user to be unable to log in normally
        //So it is currently ready to use, there are two ways, either encrypted or unencrypted
        System.out.println("2");
        User check_userName1 = userService.check(userTemp1);
        System.out.println(userTemp1);
        System.out.println("1");
        System.out.println(check_userName);
        //20201211 User registration, the account password used by the previous user is not encrypted, and the latter is encrypted,
        // causing the previously registered user to be unable to log in normally
        //So it is currently ready to use, there are two ways, either encrypted or unencrypted
        if (check_userName == null && check_userName1 == null) { //No user
            System.out.println("11");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //return userService.EnterUser(user);
        else { //There is a user

            System.out.println("有用户，进行登录准备：");
            System.out.println(user);
            System.out.println(userService.check(user));

            Integer userIdTemp = 0;
            if (userService.check(user) != null) {//Queries with unencrypted passwords
                userIdTemp = userService.check(user).getUserId();
                System.out.println(userService.check(user).getUserId());
            } else {//Encrypted query with password
                userIdTemp = userService.check(check_userName).getUserId();
                System.out.println(userService.check(check_userName).getUserId());
            }

            Liveness livenessTemp = new Liveness();
            livenessTemp.setUserId(userIdTemp);
            //livenessTemp.setTotal(0);
            //System.out.println("添加1--------"+livenessTemp);
            //livenessService.add_Liveness(livenessTemp);

            //After the addition is over, then call enterLiveness of livenessServer to update the login time and active times

            System.out.println("22");

            //int userIdTemp = user.getUserId();   //The reason why this line of code is wrong is that there is no
            //            // userId yet. Only the name and password are passed in the previous paragraph. The specific userId
            //            // needs to be extracted from the database.


            //Liveness liveness = null;
            //int userIdTemp = userService.check(user).getUserId();
            //Integer userIdTemp = userService.check(user).getUserId();

            System.out.println("2");
            System.out.println(userIdTemp);
            //Liveness liveness2 = new Liveness();
            //liveness2.setUserId(userIdTemp);
            liveness.setUserId(userIdTemp);
            System.out.println("3");
            System.out.println(liveness);
            Liveness liveness1 = livenessService.enterLiveness(liveness);
            // DarenSu  20200825 Delete unnecessary data that is not recorded
            // The login function enterUser in UserController, because the first login needs to directly add the
            // user record in the liveness activity table (only userId)
            // But the add function it invoked, enterLiveness in LivenessService already has the add operation of the
            // user record, and this function is also called by other functions
            // However, this liveness update function is public. If you modify it directly here, it will
            // cause more bugs, so modify it in enterUser.
//			List<Liveness> livenessesListTemp = livenessService.SelByUserId(userIdTemp);
//			for(int i = 0; i < livenessesListTemp.size() ; i++){
//				if(livenessesListTemp.get(i).getOnlineTime() == null){
//					livenessService.delete_Liveness(livenessesListTemp.get(i));
//				}
//			}
            //20201211 user signup，The account password used by the previous user is not encrypted,  and the latter is
            // encrypted, causing the previously registered user to fail to log in normally
            //So currently ready, there are two ways, either encrypted or unencrypted
            //If the unencrypted one is not found, it is empty, then use encrypted login
            if (check_userName1 == null ) {
                return new ResponseEntity<>(userService.EnterUser(user), HttpStatus.OK);
            }
            // If the encrypted one is not found, it is empty, then use the unencrypted login
            else {
                return new ResponseEntity<>(userService.EnterUser(userTemp1), HttpStatus.OK);
            }
            //return new ResponseEntity<>(userService.EnterUser(user), HttpStatus.OK);
        }
    }


}
