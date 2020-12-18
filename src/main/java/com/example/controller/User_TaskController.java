package com.example.controller;

import com.example.entity.Combine_u_ut;
import com.example.entity.Task;
import com.example.entity.User;
import com.example.entity.User_Task;
import com.example.service.TaskService;
import com.example.service.UserService;
import com.example.service.User_TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


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
@RequestMapping("/user_task")
public class User_TaskController {

	@Autowired
	private User_TaskService user_taskService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private UserService userService;

	private Object setuser1;

	//2019.7.11  用户任务表查询功能
	@RequestMapping("getUser_Task/{user_taskId}")
	public String GetUser_Task(@PathVariable int user_taskId) {
		return user_taskService.Sel(user_taskId).toString();
	}

	//2019.7.16  用户任务表状态返回，输入参数为userId和taskId,返回User_taskStatus
	// -1：没有  0：有未执行  1：有已执行
	@RequestMapping(value="getStatus",method = RequestMethod.POST)
	public ResponseEntity<Integer> getStatus(@RequestBody User_Task user_task){

		System.out.println(user_task);
		User_Task ut = user_taskService.getStatus(user_task);
		System.out.println(user_task);

		if(ut == null){
			return new ResponseEntity<>(-1, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(ut.getUser_taskStatus(), HttpStatus.OK);
		}

	}
	//2019.7.11  功能：任务数据上传    前端的user_task类数据上传至后台数据库
	@RequestMapping(value="addUser_Task", method=RequestMethod.POST)
	public ResponseEntity<User_Task> addUser_Task( @RequestBody User_Task user_task) {
//		User_Task check_userName = user_taskService.check(user_task);
//		if (check_userName == null){
//			user_taskService.addUser_Task(user_task);
//			return new ResponseEntity<>(user_task, HttpStatus.OK);
//		}else{
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
		//这里需要搜索出task里面的taskId，然后根据其将task的totalNum-1
		//需要调用taskService里的函数，update
		//若是此人已经执行过任务，则返回不可执行操作
		User_Task ut = user_taskService.getStatus(user_task);
        System.out.println(ut.getUser_taskStatus());
		if (ut.getUser_taskStatus() == 1 ){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		System.out.println(user_task);
		user_taskService.addUser_Task(user_task);

		System.out.println(user_task);
		return new ResponseEntity<>(ut, HttpStatus.OK);
	}
    ///2019.11.30 接受任务，将任务状态变为0(接受未完成状态)
    @RequestMapping(value="acceptUser_Task", method=RequestMethod.POST)
    public ResponseEntity<User_Task> acceptUser_Task(@RequestBody User_Task user_task){
        ///添加判断，若是漏判，则此时进行判断，要是状态只为1 则无法接受任务
        if (user_taskService.SelUser_Task(user_task) != null ) {
            User_Task ut = user_taskService.getStatus(user_task);

            //System.out.println(ut.getUser_taskStatus());
            if (ut.getUser_taskStatus() != null) {
                if (ut.getUser_taskStatus() == 1) {///接受并已执行
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                if (ut.getUser_taskStatus() == 0) {///接受未执行
                    return new ResponseEntity<>(HttpStatus.ACCEPTED);
                }
            }
            ///未接受未执行的情况,进行检测，若是该数据没有的话，则需要另行添加
            System.out.println(user_task);
        }
        if ( user_taskService.SelUser_Task(user_task) == null ){///若是没有该用户执行该任务的数据的话，则需要进行添加该条数据
            //System.out.println(user_task);
            user_taskService.addUser_Task(user_task);
        }
	    user_task.setUser_taskStatus(0);
        //System.out.println(user_task);
        user_taskService.acceptUser_Task(user_task);
	    return new ResponseEntity<User_Task>(HttpStatus.OK);
    }

    @RequestMapping(value="SelUser_Task",method = RequestMethod.POST)
    public ResponseEntity<User_Task> SelUser_Task(@RequestBody User_Task user_task) {
        return new ResponseEntity<User_Task>(user_taskService.SelUser_Task(user_task),HttpStatus.OK);
    }

	//2019.7.16  用户-任务表更新，之前只有userId和taskId,现在是更新，也就是填充后面的content和image
	@RequestMapping(value="updateUser_Task", method=RequestMethod.POST)
	public ResponseEntity<User_Task> updateUser_Task( @RequestBody User_Task user_task){
		System.out.println("欢迎来到用户-任务表更新--接受任务后：user_task/updateUser_Task");

		System.out.println(user_task);

		user_taskService.updateUser_Task(user_task);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	////2019.7.2 修改：从单个的结果展示修改为多个结果展示
	@RequestMapping("getUserInfo/{userName}")
	public List<User> GetUserInfo(@PathVariable String userName) {
		//return userService.SelInfo(userName).toString();
		System.out.println(userName);
		System.out.println(userService.SelInfo(userName));

		return userService.SelInfo(userName);
	}

	////用户登录   自己测试走表单不需要requestbody，前后台对接使用的是json数据，需要使用requestbody
	@RequestMapping(value="enterUser", method=RequestMethod.POST)
	public ResponseEntity<User> EnterUser(@RequestBody User user) {
		//return userService.SelInfo(userName).toString();
		System.out.println(user);
		System.out.println(userService.EnterUser(user));

		//return userService.EnterUser(user);
		return new ResponseEntity<>(userService.EnterUser(user), HttpStatus.OK);

	}
	/////2019.7.5 用户注册即添加+还有检测名字是否重复
	@RequestMapping(value="addUser", method=RequestMethod.POST)
	public ResponseEntity<User> addUser(@RequestBody User user) {
		User check_userName = userService.check(user);
		if (check_userName == null){
			userService.addUser(user);
			return new ResponseEntity<>(user, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	////2019.7.18 查看task的完成情况，即看有多少人正在执行该任务，只要是接受任务的都要统计
	////输入参数taskId，返回List<user_task>和返回List<user>
	////通过taskId查询出对应的多有的user_task类
	////然后根据查询到的user_task类里的userId查询出其对应的user类（主要是要user里的userName）
	@RequestMapping ("getTaskAllUser/{taskId}")
	public List<User_Task> GetTaskAllUser(@PathVariable  Integer taskId){
		System.out.println(taskId);
		return user_taskService.SelTaskId(taskId);
	}

	////2019.7.18 根据taskId，先在user_task类里查找userId，然后根据这个userId查询出user类里对应的userName
	@RequestMapping ("getTaskIdToUserName/{taskId}")
	public ResponseEntity<List<Combine_u_ut>> GetTaskIdToUserName(@PathVariable  Integer taskId){
        System.out.println("被访问的函数的名称：getTaskIdToUserName");
        System.out.println("被访问的函数的功能：根据taskId，在user_task类里查找userId，根据userId查询user类对应的userName");
	    System.out.println("输入的参数taskId：" + taskId );

		///提示：SelTaskId--去重查询
		List<User_Task> ut = user_taskService.SelTaskIdForUT(taskId);
		List<Combine_u_ut> combine_u_utList = new ArrayList<Combine_u_ut>();
		for(User_Task user_task : ut) {
			Integer userID= user_task.getUserId();
			System.out.println(userID);
//			String[] spilts = user_task.getImage().split(File.separator + File.separator);
//			String fileName = spilts[spilts.length - 1];
//			User_Task tem_ut = new User_Task(user_task.getUser_taskId(),user_task.getUserId(),user_task.getTaskId(),user_task.getUser_taskStatus(),user_task.getContent(),fileName,user_task.getType());
			///2019-11-29    空指针容易出错，所以分类讨论
            File tep_file = new File(user_task.getImage());
			User_Task tem_ut;
            if(tep_file != null) {
				tem_ut = new User_Task(user_task.getUser_taskId(), user_task.getUserId(), user_task.getTaskId(), user_task.getUser_taskStatus(), user_task.getContent(), tep_file.getName(), user_task.getType());
			}
            else{
				tem_ut = new User_Task(user_task.getUser_taskId(), user_task.getUserId(), user_task.getTaskId(), user_task.getUser_taskStatus(), user_task.getContent(), "kong", user_task.getType());
			}
			combine_u_utList.add(new Combine_u_ut(userService.Sel(userID),tem_ut));
			System.out.println(combine_u_utList);
		}
		System.out.println(combine_u_utList);

		///
		return new ResponseEntity<>(combine_u_utList, HttpStatus.OK);
	}


	///根据taskId,返回user_task类，并且没有重复的数据




	///2019.7.25 上传任务的文字信息和图片，两个参数，user_task文字信息+单张图片
	///目前没有实现多张图片上传，实现的是单图片的上传
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	@ResponseBody
														// User类           文件类型的参数（可以是文件、视频、图片均可）
	public ResponseEntity<User_Task> add_Task_include_pic(@RequestPart("utask") User_Task utask, @RequestPart("file") MultipartFile file) {
	    //查看文件是否有重复
	    // 获取文件名
        System.out.println(utask);

		String fileName = file.getOriginalFilename();
		System.out.println(fileName);

		//2019.9.17  读取上传的文件的大小，不能超过SSM所支持的最大值,单位为B
		//2019.9.17  前端那边设置最多可以传输十个文件
		long fileLength = 0L;
		fileLength = file.getSize()/1024;
		System.out.println(fileLength+"KB"+"    "+"单张照片小于20480KB，可以传输");
		if (fileLength > 20480){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// 获取文件的后缀名
		String suffixName = fileName.substring(fileName.lastIndexOf("."));
		// 文件上传后的路径   自己测试
		//String filePath = "E:" + File.separator + "springboot-upload" + File.separator + "image" + File.separator ;
		// 服务器测试
		String filePath =  "/E/springboot-upload/image/";

		// 解决中文问题，liunx下中文路径，图片显示问题
		// fileName = UUID.randomUUID() + suffixName;
		File dest = new File(filePath + fileName);
		// 检测是否存在目录
		if (!dest.getParentFile().exists()) {
			//如果不存在的话则自己生成路径
			dest.getParentFile().mkdirs();
		}
		try {
		    if(!dest.exists()) {
                file.transferTo(dest);
            }
			//上传图片的地址到数据库的相关位置
			utask.setImage(filePath + fileName);
			//同时将任务的具体内容也上传，此时的utask里面已经有了图片的位置信息
			user_taskService.addUser_Task(utask);
			//将这个结果返回，并且返回状态为OK
			return new ResponseEntity<>(utask, HttpStatus.OK);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	///2019.9.10  输入参数utask,返回单个图片，
	@RequestMapping(value="downImage", method=RequestMethod.POST)
	public ResponseEntity<File> downImage(@RequestBody User_Task utask) {
		User_Task findUT = user_taskService.selcetUser_Task(utask);
		String pathtemp = findUT.getImage();
		//System.out.println(pathtemp);
		String path = pathtemp;
		//System.out.println(path);
		File file = new File(path);
		//return file;
		return new ResponseEntity<>(file,HttpStatus.OK);
	}

	///2019.9.15  输入参数image,返回单个图片
	@RequestMapping("downImageFromImage/{image}")
	public ResponseEntity<byte[]> downImageFromImage(@PathVariable String image) {


//		String suffixName = image.substring(image.lastIndexOf(File.separator));
//		image = suffixName;
		//2019.9.17   若是传输的参数中带有自字符  \或者/   的时候终止返回
		boolean status1 = image.contains("/");
		boolean status2 = image.contains("\\");
		if( status1 || status2  ){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		//2019.9.15   数据流转换
		InputStream in;
		ResponseEntity<byte[]> response=null ;
		String filePath = "E" + File.separator + "springboot-upload" + File.separator + "image" + File.separator ;
//		System.out.println(image);
		File file = new File(filePath + image);
		try {
			in = new FileInputStream(file);
			byte[] b=new byte[in.available()];
			in.read(b);
			HttpHeaders headers = new HttpHeaders();
			String filename = new String(image.getBytes("gbk"),"iso8859-1");
			headers.add("Content-Disposition", "attachment;filename="+filename);
			response = new ResponseEntity<byte[]>(b, headers, HttpStatus.OK);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

//	///2019.9.15  输入参数image,返回单个图片
//	@RequestMapping("downImageFromImage/{image}")
//	public ResponseEntity<byte[]> downImageFromImage(@PathVariable String image) {
//
//
//		String suffixName = image.substring(image.lastIndexOf(File.separator));
//		image = suffixName;
//
//
//
//		InputStream in;
//		ResponseEntity<byte[]> response=null ;
//		String filePath = "E:" + File.separator + "springboot-upload" + File.separator + "image" + File.separator ;
//		System.out.println(image);
//		File file = new File(filePath + image);
//		try {
//			in = new FileInputStream(file);
//			byte[] b=new byte[in.available()];
//			in.read(b);
//			HttpHeaders headers = new HttpHeaders();
//			String filename = new String(image.getBytes("gbk"),"iso8859-1");
//			headers.add("Content-Disposition", "attachment;filename="+filename);
//			response = new ResponseEntity<byte[]>(b, headers, HttpStatus.OK);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return response;
//	}


	///2019.7.25 下载任务的文字信息和图片，两个参数，user_task文字信息+单张图片
	///目前没有实现多张图片下载，仅仅实现单张图片的下载
	@RequestMapping(value = "/downloadImage", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<User_Task> look_Task_include_pic(User_Task utask, @RequestParam("file") MultipartFile file) {
		// 获取文件名
		String fileName = file.getOriginalFilename();
		// 获取文件的后缀名
		String suffixName = fileName.substring(fileName.lastIndexOf("."));
		// 文件上传后的路径
		String filePath = "E:\\springboot-upload\\image\\";
		// 解决中文问题，liunx下中文路径，图片显示问题
		// fileName = UUID.randomUUID() + suffixName;
		File dest = new File(filePath + fileName);
		// 检测是否存在目录
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}
		try {
			file.transferTo(dest);
			//上传图片的地址到数据库
			utask.setImage(filePath + fileName);

			user_taskService.addUser_Task(utask);

			return new ResponseEntity<>(utask, HttpStatus.OK);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	///2019-11-1   根据前端传输的用户的ID，返回此用户已经接收的所有任务
	@RequestMapping("getUserAllAcceptTask/{userId}")
	public ResponseEntity<List<User_Task>> getUserAllAcceptTask(@PathVariable int userId){
		System.out.println(userId);
		System.out.println(user_taskService.seluserId(userId));

		return new ResponseEntity<List<User_Task>>(user_taskService.seluserId(userId),HttpStatus.OK);
	}

	///2019-11-1   根据前端传输的用户的ID，返回此用户已经接收并已经完成所有任务
	@RequestMapping("getUserAllFinishTask/{userId}")
	public ResponseEntity<List<User_Task>> getUserAllFinishTask(@PathVariable int userId){
		System.out.println(userId);
		System.out.println(user_taskService.seluserIdFinish(userId));

		return new ResponseEntity<List<User_Task>>(user_taskService.seluserIdFinish(userId),HttpStatus.OK);
	}

	///2019-11-3   根据前端传输的用户的ID，返回此用户已经接收的但是未完成完成的所有任务
	@RequestMapping("getUserAllUnfinishTask/{userId}")
	public ResponseEntity<List<User_Task>> getUserAllUnfinishTask(@PathVariable int userId){
		System.out.println(userId);
		System.out.println(user_taskService.seluserIdUnfinish(userId));

		return new ResponseEntity<List<User_Task>>(user_taskService.seluserIdUnfinish(userId),HttpStatus.OK);
	}

	///2019-11-1   根据前端传输的用户的ID，根据推荐系统的策略返回此用户最合适接受的未执行的任务（目前的本质是随机返回）
	@RequestMapping("getUserAllRecommendTask/{userId}")
//	public ResponseEntity<List<User_Task>> getUserAllRecommendTask(@PathVariable int userId){
//		System.out.println(userId);
//		System.out.println(user_taskService.seluserIdRandom(userId));
//
//		return new ResponseEntity<List<User_Task>>(user_taskService.seluserIdRandom(userId),HttpStatus.OK);
//	}
	public ResponseEntity<List<Task>> getUserAllRecommendTask(@PathVariable int userId){
		///根据userId查找UT中符合条件的List<User_Task>
		List<User_Task> tempList = user_taskService.seluserIdRandom(userId);
		///新建一个List<Task>类型的数据，目的是为了存储List<User_Task>在T中的查询结果
		List<Task> taskList1 = new ArrayList<Task>();
		for (int i = 0 ;i < tempList.size() ; i++){
			System.out.println(i);	///	测试i专用
			///取出List<User_Task> tempList中的一条数据，并且继续取出其taskId
			Integer taskIdListTemp = user_taskService.seluserIdRandom(userId).get(i).getTaskId();
			Task taskTemp = taskService.Sel_Task(taskIdListTemp);
			taskList1.add(taskTemp);
		}
		if (taskList1 == null ){
			return new ResponseEntity<List<Task>>( HttpStatus.NOT_FOUND);
		}
		else{
			return new ResponseEntity<List<Task>>(taskList1, HttpStatus.OK);
		}
	}

	///2019-11-3   根据前端传输的用户的ID，返回此用户已经接收的所有任务的taskId
	@RequestMapping("getUserAllAcceptTaskId/{userId}")
	public ResponseEntity<List<Integer>> getUserAllAcceptTaskId(@PathVariable int userId){
		System.out.println(userId);
		System.out.println(user_taskService.seluserIdForTaskId(userId));

		return new ResponseEntity<List<Integer>>(user_taskService.seluserIdForTaskId(userId),HttpStatus.OK);
	}


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
/*	@RequestMapping(value="checkUser", method=RequestMethod.POST)
	public String checkUser(User user) {
		System.out.println(user);
		
		User user1 = new User();
		user1.setUserName("a2");
		user1.setPassWord("b21");
		
		userService.checkUser(user1);
		
		userService.addUser(user);
		return "OK";
	}*/



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
	
	

	
	
//	/////添加单个的数据结构的内容
//	@GetMapping(value="addUser")
//	public String addUser(@RequestParam("name") String name,@RequestParam("password") String password) {
//		//User u1 = new User("a2", "b21", "c21");
//		System.out.println(name+password);
//		//userService.addUser(user);
//		return "OK";
//	}
	
	
//	@RequestMapping("delUser/{id}")
//	public String delUser(@PathVariable int id) {
//		userService.delUser(id);
//		return "OK";
//	}
	
}
