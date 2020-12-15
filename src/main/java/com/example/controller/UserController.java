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

import java.util.List;
//import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Author:0xOO
 * @Date: 2018/9/26 0026
 * @Time: 14:42
 */

/**
 * @Author:DarenSu
 * @Date: 2019/6/13 修改
 * @Time: 14:42
 */

@RestController      //进行模块的注明，此处为控制模块
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

    @RequestMapping("getUser/{id}")
    public User GetUser(@PathVariable int id) {
        return userService.Sel(id);
    }

    //20201215   Server层分两层的测试
    @RequestMapping("getuser/{id}")
    public User getuser(@PathVariable int id) {
        return userServiceInterface.Sel(id);
    }



    ////2019.7.2 修改：从单个的结果展示修改为多个结果展示
    @RequestMapping("getUserInfo/{userName}")
    public List<User> GetUserInfo(@PathVariable String userName) {
        //return userService.SelInfo(userName).toString();
        System.out.println(userName);
        System.out.println(userService.SelInfo(userName));

        return userService.SelInfo(userName);
    }

    ////2019.9.16 修改：一次性返回数据库内用户信息次后面的十条数据
    @RequestMapping("getUserRank")
    public ResponseEntity<List<User>> GetUserRank() {

        if (userService.getUserRank() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<List<User>>(userService.getUserRank(), HttpStatus.OK);
        }
    }
    //20201211 测试check函数
    @RequestMapping(value = "CheckTest", method = RequestMethod.POST)
    public ResponseEntity<User> CheckTest(@RequestBody User user) {


        return new ResponseEntity<>(userService.check(user), HttpStatus.OK);
    }


    ////用户登录   2019.9.25  userName 和 realName均可以进行登录
    /// 自己测试走表单不需要requestbody，前后台对接使用的是json数据，需要使用requestbody
    ///  2019.9.25 ---待添加---
    @RequestMapping(value = "enterUser", method = RequestMethod.POST)
    public ResponseEntity<User> EnterUser(@RequestBody User user) {
        //return userService.SelInfo(userName).toString();
        Liveness liveness = new Liveness();

        System.out.println(user);
        //System.out.println(userService.EnterUser(user));

        //20201211 用户注册，之前的用户使用的账号密码没有加密，后面的加密了，导致之前注册的用户无法正常登陆
        //所以目前准备使用，两种方式，加密的可以，不加密的也可以
        User userTemp1 = user;


        ////2020.04.21   密码加密
        String passWordtemp = user.getPassWord();
        ////SHA256加密
//------------>>>>>>>>>>>20201214-由于加密涉及到活跃度判断问题，暂时注释------------------>>>>>>>>>>>>>>>>>>>>>>>------------------------------->>>>>>>>>>>
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


        ///没有判断，添加错误的返回判断    2019.7.15
        User check_userName = userService.check(userTemp2);    /// 2019.9.25 - check函数有改动，用户名和真实姓名都可以进行登录
        //20201211 用户注册，之前的用户使用的账号密码没有加密，后面的加密了，导致之前注册的用户无法正常登陆
        //所以目前准备使用，两种方式，加密的可以，不加密的也可以
        System.out.println("2");
        User check_userName1 = userService.check(userTemp1);
        System.out.println(userTemp1);
        System.out.println("1");
        System.out.println(check_userName);
        //20201211 用户注册，之前的用户使用的账号密码没有加密，后面的加密了，导致之前注册的用户无法正常登陆
        //所以目前准备使用，两种方式，加密的可以，不加密的也可以
        if (check_userName == null && check_userName1 == null) { //没有该用户的情况
            System.out.println("11");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //return userService.EnterUser(user);
        else { //有用户的情况下
            //    DarenSu     20200811
            // 有该用户的情况下，那就确定了该用户可以进行登录，也已经进行了登陆的操作
            // 那么我们这时候就需要进行登录标记，即将登陆的状态进行记录
            // 那么此时的liveness数据表里面肯定需要添加这个用户的登录记录的，并且只要添加了userId即可
            //    但是传入的参数user里面的userId是空的，需要根据user中的名字和密码从数据库中查询userId才行的
            System.out.println("有用户，进行登录准备：");
            System.out.println(user);
            System.out.println(userService.check(user));

            Integer userIdTemp = 0;
            if (userService.check(user) != null) {//使用密码未加密的查询
                userIdTemp = userService.check(user).getUserId();
                System.out.println(userService.check(user).getUserId());
            } else {//使用密码加密的查询
                userIdTemp = userService.check(check_userName).getUserId();
                System.out.println(userService.check(check_userName).getUserId());
            }

            Liveness livenessTemp = new Liveness();
            livenessTemp.setUserId(userIdTemp);
            //livenessTemp.setTotal(0);
            //System.out.println("添加1--------"+livenessTemp);
            //livenessService.add_Liveness(livenessTemp);

            //添加结束后，然后调用livenessServer的enterLiveness进行登陆时间和活跃次数的更新

            System.out.println("22");

            //int userIdTemp = user.getUserId();   //这一行代码之所以错误是因为现在还没有userId，前段传进来的只有名字和密码，具体的userId需要从数据库中提取

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
            //   DarenSu  20200825  删除没有记录的多余数据
            // DarenSu   20200824  UserController里的登录函数enterUser，由于初次登陆需要直接在其中进行liveness活跃度表的该用户记录添加（只有userId）
            //  但是她引用的添加函数，LivenessService中enterLiveness已经有了用户记录的添加操作，并且该函数还被其它函数调用
            //  所以，需要删除enterUser里的添加的记录
            //  但是，这个活跃度更新的函数属于公共函数，直接在这里面进行修改的话，造成的bug较多，所以在enterUser理进行修改
//			List<Liveness> livenessesListTemp = livenessService.SelByUserId(userIdTemp);
//			for(int i = 0; i < livenessesListTemp.size() ; i++){
//				if(livenessesListTemp.get(i).getOnlineTime() == null){
//					livenessService.delete_Liveness(livenessesListTemp.get(i));
//				}
//			}
            //20201211 用户注册，之前的用户使用的账号密码没有加密，后面的加密了，导致之前注册的用户无法正常登陆
            //所以目前准备使用，两种方式，加密的可以，不加密的也可以
            //没加密的没找到，是空，那么用加密的登录
            if (check_userName1 == null ) {
                return new ResponseEntity<>(userService.EnterUser(user), HttpStatus.OK);
            }
            //加密的没找到，是空，那么用没加密的登录
            else {
                return new ResponseEntity<>(userService.EnterUser(userTemp1), HttpStatus.OK);
            }
            //return new ResponseEntity<>(userService.EnterUser(user), HttpStatus.OK);
        }
    }

    /////2020.6.17 用户注册即添加+还有检测名字是否重复
    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    public ResponseEntity<User> addUser(@RequestBody User user) {
        //20201012  用户注册过程中数据检测，防止空数据进来
        User userTemp = user;
        if (userTemp.getUserName() == null){//传的数据是空  406
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }else if (userTemp.getUserName() != null && userTemp.getUserId() == null
                && userTemp.getPassWord() == null && userTemp.getRealName() == null ){
            if (userService.checkLogin(userTemp) == null){//未注册过  200
                return new ResponseEntity<>(HttpStatus.OK);
            }else {//已注册过  404
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }else {;}

        if (userTemp.getUserId() == null && userTemp.getUserName() != null
                && userTemp.getPassWord() != null && userTemp.getRealName() != null
                && userTemp.getCoins() != null) {
            ////20201024 数据非空的话，需要检测数据类型是否正确
            if (userTemp.getUserName() instanceof String && userTemp.getCoins() instanceof Integer
                    && userTemp.getPassWord() instanceof String && userTemp.getRealName() instanceof String) {


                User check_userName = userService.checkLogin(user);

                //////2020.04.21   密码加密
                //////2020.06.18   密码加密修改篇
                String passWordtemp = user.getPassWord();
                ////SHA256加密
//------------>>>>>>>>>>>20201214-由于加密涉及到活跃度判断问题，暂时注释------------------>>>>>>>>>>>>>>>>>>>>>>>------------------------------->>>>>>>>>>>

//                if (StringUtils.isEmpty(passWordtemp)) {//加密后的数据不为空
//                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//                }
//                passWordtemp = SHA256.getSHA256StrJava(passWordtemp);

                System.out.println(passWordtemp);
                user.setPassWord(passWordtemp);
                System.out.println(user.getPassWord());


                if (check_userName == null) {//没有这个人
                    userService.addUser(user);
                    return new ResponseEntity<>(user, HttpStatus.OK);
                } else {//已有这个人  404
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }

            } else {//数据类型不正确   400
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {//该有的数据不能为空  502
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }


//	@RequestMapping("delUser/{userId}")
//	public String DelUser(@PathVariable Integer userId) {
//		///先检查是否有这个ID，有的话可以执行删除操作，没有的话直接返回NOT_FOUND
//		userService.delUser(userId);
//		return "OK";
//	}


/////Bean_Task数据库中数据的添加和查询         2016.6.18
//
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


    ///FUNCTION---直接打出、并添加进入数据库内 --2019.6.18
    ///查询测试，仅仅只是进行本地的测试
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


    /////FUNCTION---删除功能，将数据库的某条数据从数据库内删除---2019.6.18 有错误
    ///删除一条数据，本地测试
//	@RequestMapping("delUser/{id}")
//	public String DelUser(@PathVariable int id){
//		userService.DelUser(id);
//		return"OK";
//	}


    /////FUNCTION---向前台返回数据，进行了前后台的连接--2019.6.15
    ///前后台对接，并经过测试,传输的数据格式是User
//	@RequestMapping(value="checkUser", method=RequestMethod.POST)
//	@ResponseBody
//	public ResponseEntity<User> addUser(@RequestBody User user) {
//		System.out.println(user);
//
//		/*该出逻辑验证代码*/
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


    /////添加单个的数据结构的内容
    @GetMapping(value = "addUser")
    public String addUser(@RequestParam("name") String name, @RequestParam("password") String password) {
        //User u1 = new User("a2", "b21", "c21");
        System.out.println(name + password);
        //userService.addUser(user);
        return "OK";
    }


}
