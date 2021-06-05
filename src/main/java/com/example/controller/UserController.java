package com.example.controller;


import com.example.entity.Liveness;
import com.example.entity.User;
import com.example.service.LivenessService;
import com.example.service.TaskService;
import com.example.service.UserService;
import com.example.service.serviceInterface.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
//import org.apache.commons.codec.digest.DigestUtils;



/**
 * @Author:DarenSu
 * @Date: 2019/6/13
 * @Time: 14:42
 */

@RestController      //To indicate the module, here is the control module
@RequestMapping("/user")

public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private TaskService bean_TaskService;
    @Autowired
    private LivenessService livenessService;
    @Autowired
    private UserServiceInterface userServiceInterface;


    private Object setuser1;

    @RequestMapping("/getUser1")
    public Map<String,Object> getUser(@PathVariable Integer id)
    {
        User resultUser = userService.getUser(id);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("user",resultUser);
        return result;
    }
    // 20201215     User query
    @RequestMapping("getUser/{id}")
    public User GetUser(@PathVariable int id) {
        return userService.Sel(id);
    }

    //  20201215   Server layer is divided into two layers of testing
    @RequestMapping("getuser/{id}")
    public User getuser(@PathVariable int id) {
        return userServiceInterface.Sel(id);
    }



    ////2019.7.2 Modification: Modified from a single result display to multiple result displays
    @RequestMapping("getUserInfo/{userName}")
    public List<User> GetUserInfo(@PathVariable String userName) {
        //return userService.SelInfo(userName).toString();
        System.out.println(userName);
        System.out.println(userService.SelInfo(userName));

        return userService.SelInfo(userName);
    }

    ////2019.9.16 Modification: Return the ten data after the user information in the database at one time
    @RequestMapping("getUserRank")
    public ResponseEntity<List<User>> GetUserRank() {

        if (userService.getUserRank() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<List<User>>(userService.getUserRank(), HttpStatus.OK);
        }
    }
    //20201211 Test the check function
    @RequestMapping(value = "CheckTest", method = RequestMethod.POST)
    public ResponseEntity<User> CheckTest(@RequestBody User user) {


        return new ResponseEntity<>(userService.check(user), HttpStatus.OK);
    }


    ////User login 2019.9.25 Both userName and realName can log in
    /// You don’t need requestbody to test the form by yourself. The front-end and back-end docking uses json data,
    // so requestbody is required
    ///  2019.9.25 ---To be added---
    @RequestMapping(value = "enterUser", method = RequestMethod.POST)
    public ResponseEntity<User> EnterUser(@RequestBody User user) {
        //return userService.SelInfo(userName).toString();


        Liveness liveness = new Liveness();

        System.out.println(user);
        //System.out.println(userService.EnterUser(user));


        User userTemp1 = user;



        String passWordtemp = user.getPassWord();
        ////SHA256
//------------>>>>>>>>>>>20201214------------------->>>>>>>>>>>>>>>>>>>>>>>------------------------------->>>>>>>>>>>
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



        User check_userName = userService.check(userTemp2);
        System.out.println("2");
        User check_userName1 = userService.check(userTemp1);
        System.out.println(userTemp1);
        System.out.println("1");
        System.out.println(check_userName);

        if (check_userName == null && check_userName1 == null) {
            System.out.println("11");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //return userService.EnterUser(user);
        else {
            System.out.println("有用户，进行登录准备：");
            System.out.println(user);
            System.out.println(userService.check(user));

            Integer userIdTemp = 0;
            if (userService.check(user) != null) {
                userIdTemp = userService.check(user).getUserId();
                System.out.println(userService.check(user).getUserId());
            } else {
                userIdTemp = userService.check(check_userName).getUserId();
                System.out.println(userService.check(check_userName).getUserId());
            }

            Liveness livenessTemp = new Liveness();
            livenessTemp.setUserId(userIdTemp);
            //livenessTemp.setTotal(0);
            //System.out.println("添加1--------"+livenessTemp);
            //livenessService.add_Liveness(livenessTemp);



            System.out.println("22");

            //int userIdTemp = user.getUserId();

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

//			List<Liveness> livenessesListTemp = livenessService.SelByUserId(userIdTemp);
//			for(int i = 0; i < livenessesListTemp.size() ; i++){
//				if(livenessesListTemp.get(i).getOnlineTime() == null){
//					livenessService.delete_Liveness(livenessesListTemp.get(i));
//				}
//			}
            // 20210405 Can be used in the token
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            user.setOnlineTime(date);
            userTemp1.setOnlineTime(date);
            if (check_userName1 == null ) {
                return new ResponseEntity<>(userService.EnterUser(user), HttpStatus.OK);
            }

            else {
                return new ResponseEntity<>(userService.EnterUser(userTemp1), HttpStatus.OK);
            }
            //return new ResponseEntity<>(userService.EnterUser(user), HttpStatus.OK);
        }
    }

    // user.getUserName != null
    // Check to see if the account has been registered
    @RequestMapping(value = "checkLogin", method = RequestMethod.POST)
    public ResponseEntity<String> checkLogin(@RequestBody User user){
        String str = new String();
        if(user.getUserName() == null ){
            str = "NOT_ACCEPTABLE:空数据";
            System.out.println(str);
            return new ResponseEntity<>(str, HttpStatus.NOT_ACCEPTABLE);
        }
        if( userService.checkLogin(user) != null){
            str = "NOT_FOUND:已有该账号";
            System.out.println(str);
            System.out.println(userService.checkLogin(user));
            return new ResponseEntity<>(str, HttpStatus.NOT_FOUND);
        }
        str = "OK:可以进行注册";
        System.out.println(str);
        return new ResponseEntity<>(str, HttpStatus.OK);
    }


    /////2020.6.17 User registration is added and check whether the name is duplicated
    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    public ResponseEntity<User> addUser(@RequestBody User user) {
        // 20201012 Data detection during user registration to prevent empty data from coming in
        User userTemp = user;
        // Incentives are initialized
        userTemp.setCoins(1000);
        if (userTemp.getUserName() == null){//passed data is empty  406
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }else if (userTemp.getUserName() != null && userTemp.getUserId() == null
                && userTemp.getPassWord() == null /*&& userTemp.getRealName() == null*/ ){
            if (userService.checkLogin(userTemp) == null){//never register  200
                return new ResponseEntity<>(HttpStatus.OK);
            }else {//already registered  404
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else {;}

        if (userTemp.getUserId() == null && userTemp.getUserName() != null
                && userTemp.getPassWord() != null && userTemp.getRealName() != null
                && userTemp.getCoins() != null) {
            //  20201024 If the data is not empty, you need to check whether the data type is correct
            if (userTemp.getUserName() instanceof String && userTemp.getCoins() instanceof Integer
                    && userTemp.getPassWord() instanceof String /*&& userTemp.getRealName() instanceof String*/) {


                User check_userName = userService.checkLogin(user);

                //////2020.04.21   encrypted by password
                //////2020.06.18   Password encryption modification
                String passWordtemp = user.getPassWord();
                ////SHA256 encryption
//------------>>>>>>>>>>>20201214-As encryption involves the problem of judging the degree of activity, temporarily
// comment------------------>>>>>>>>>>>>>>>>>>>>>>>------------------------------->>>>>>>>>>>

//                if (StringUtils.isEmpty(passWordtemp)) {//The encrypted data is not empty
//                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//                }
//                passWordtemp = SHA256.getSHA256StrJava(passWordtemp);

                System.out.println(passWordtemp);
                user.setPassWord(passWordtemp);
                System.out.println(user.getPassWord());


                if (check_userName == null) {//there's no such person
                    if(user.getUserName().indexOf(".com")!=-1 && user.getUserName().indexOf("@")!=-1){
                        user.setMail(user.getUserName());
                    }
                    userService.addUser(user);
                    return new ResponseEntity<>(user, HttpStatus.OK);
                } else {//Already has this person  404
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }

            } else {//Incorrect data type   400
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {//Some data cannot be empty  502
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }


//	@RequestMapping("delUser/{userId}")
//	public String DelUser(@PathVariable Integer userId) {
//		userService.delUser(userId);
//		return "OK";
//	}

//	@PostMapping(value="addBean_Task")
//	public String addBean_Task(Bean_Task bean) {
////		Bean_Task bean1 = new Bean_Task();
////		System.out.println(bean1);
//		bean_TaskService.addBean_Task(bean);
//
//		return "OK";
//	}
//
//	@GetMapping("getBean_Task")
//	public String GetBean_Task(@RequestParam(value = "id") Integer id) {
//		return bean_TaskService.Sel_BeanTask(id).toString();
////		return "11111111";
//	}



//	@RequestMapping(value="checkUser", method=RequestMethod.POST)
//	public String checkUser(User user) {
//		System.out.println(user);
//
//		User user1 = new User();
//		user1.setUserName("a2");
//		user1.setPassWord("b21");
//
//		userService.checkUser(user1);
//
//		userService.addUser(user);
//		return "OK";
//	}



//	@RequestMapping("delUser/{id}")
//	public String DelUser(@PathVariable int id){
//		userService.DelUser(id);
//		return"OK";
//	}



//	@RequestMapping(value="checkUser", method=RequestMethod.POST)
//	@ResponseBody
//	public ResponseEntity<User> addUser(@RequestBody User user) {
//		System.out.println(user);
//
//
//
//		User u = userService.checkUser(user);
//
//		if (u != null){
//			System.out.println(u);
//			return new ResponseEntity<>(u, HttpStatus.OK);
//		}else{
//			System.out.println(u);
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//
//	}



    /////Add the content of a single data structure
    @GetMapping(value = "addUser")
    public String addUser(@RequestParam("name") String name, @RequestParam("password") String password) {
        //User u1 = new User("a2", "b21", "c21");
        System.out.println(name + password);
        //userService.addUser(user);
        return "OK";
    }


}
