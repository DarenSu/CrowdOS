package com.example.controller;

import com.example.entity.Task;
import com.example.entity.User_Task;
import com.example.service.TaskService;
import com.example.service.User_TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
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
@RequestMapping("/task")
public class TaskController {


	@Autowired
	private TaskService taskService;
	//private Object setuser1;
	@Autowired
	private User_TaskService user_taskService;


	
///Task数据库中数据的添加和查询         2016.6.18
	
//	@PostMapping(value="add_Task")
//	@ResponseBody
//	public String add_Task(/*@RequestBody*/  Task task) {
////		Task task1 = new Task();
////		System.out.println(task1);
//		taskService.add_Task(task);
//
//		//System.out.println(task);
//		//return new ResponseEntity<>(task, HttpStatus.OK);
//		return "OK";
//	}
	
	@GetMapping("get_Task")
	public String Get_Task(@RequestParam(value = "taskId") Integer taskId) {
		return taskService.Sel_Task(taskId).toString();
//		return "11111111";
	}

	@RequestMapping("del_Task/{taskId}")
	public String del_Task(@PathVariable Integer taskId) {
		taskService.del_Task(taskId);
		return "OK";
	}


	//前后台对接    2019.6.22  自己写的有点问题  自己测试走表单不需要requestbody，前后台对接使用的是json数据，需要使用requestbody
	@PostMapping(value="add_Task")
	@ResponseBody
	public ResponseEntity<Task> add_Task(@RequestBody Task task) {
//		Task task1 = new Task();
//		System.out.println(task1);
		System.out.println("欢迎来到任务添加功能：task的add_Task");
		//taskService.add_Task(task);

		System.out.println(task);
		System.out.println(task.toString());
		//20201012  任务发布过程中数据检测，防止空数据进来
		Task taskTemp = task;
		if (taskTemp.getTaskId() == null && taskTemp.getTaskName() != null &&
				taskTemp.getPostTime() != null && taskTemp.getDeadLine() != null &&
				taskTemp.getUserId() != null && taskTemp.getUserName() != null &&
				taskTemp.getCoin() != null && taskTemp.getDescribe_task() != null &&
				taskTemp.getTotalNum() != null && taskTemp.getTaskStatus() != null &&
				taskTemp.getTaskKind() != null /*&& taskTemp.getTemp() != null &&
				taskTemp.getLongitude() != null && taskTemp.getLatitude() != null &&
				taskTemp.getSensorTypes() != null*/) {
			//20201024 数据非空的话，需要检测数据类型是否正确
			if (taskTemp.getTaskName() instanceof String  &&
					taskTemp.getPostTime() instanceof Date && taskTemp.getDeadLine() instanceof Date &&
					taskTemp.getUserId() instanceof Integer && taskTemp.getUserName() instanceof String &&
					taskTemp.getCoin() instanceof Float && taskTemp.getDescribe_task() instanceof String &&
					taskTemp.getTotalNum() instanceof Integer && taskTemp.getTaskStatus() instanceof Integer &&
					taskTemp.getTaskKind() instanceof Integer /*&& taskTemp.getTemp() instanceof Integer &&
					taskTemp.getLongitude() instanceof Float && taskTemp.getLatitude() instanceof Float	&&
					taskTemp.getSensorTypes() instanceof String	*/) {
				//20201102   添加
				taskService.add_Task(task);
				System.out.println(task);
				return new ResponseEntity<>(task, HttpStatus.OK);
			}
			//return "OK";
			else {//数据类型不正确
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
		} else {//空数据
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	////2019.7.5 修改：从单个的结果展示修改为多个结果展示
	@RequestMapping("getUserInfo/{taskName}")
	public List<Task> GetUserInfo(@PathVariable String taskName) {
		//return userService.SelInfo(id).toString();
		System.out.println(taskName);
		System.out.println(taskService.SelInfo(taskName));

		return taskService.SelInfo(taskName);
	}

	////2019.7.6 修改：一次性返回数据库最后面的十条数据
	@RequestMapping("getTen")
	public ResponseEntity<List<Task>> GetTen() {
		//return userService.SelInfo().toString();
		//System.out.println();
		//System.out.println(taskService.getTen());

		if (taskService.getTen() == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<Task>>(taskService.getTen(), HttpStatus.OK);
		}
	}


	//2019.7.18  功能：查询某userId所发布的所有的任务  ，输入的参数：userId，返回的结果：List<task>
	@RequestMapping("getUserAllTask/{userId}")
	public List<Task> GetUserAllTask(@PathVariable Integer userId) {
		System.out.println("被访问的函数的名称：getUserAllTask");
		System.out.println("被访问的函数的功能：查询某userId所发布的所有的任务");
		System.out.println("输入参数userId：" + userId);

		//20201024  测试输出数据
		System.out.println(taskService.SelUserId(userId).get(0));



		return taskService.SelUserId(userId);

	}

	private static String getType(Object a) {
		return a.getClass().toString();

	}

	////2019.7.6 修改：一次性返回数据库次后面的十条数据
	@RequestMapping("getNewTen/{mintaskId}")
	public ResponseEntity<List<Task>> GetNewTen(@PathVariable Integer mintaskId) {
		//return userService.SelInfo(id).toString();
		//System.out.println();
		//System.out.println(taskService.getTen());
		System.out.println("被访问的函数的名称：getNewTen");
		System.out.println("被访问的函数的功能：一次性返回数据库次后面的十条数据");
		System.out.println("输入参数mintaskId：" + mintaskId);

		if (taskService.getNewTen(mintaskId) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<Task>>(taskService.getNewTen(mintaskId), HttpStatus.OK);
		}
	}
	///2019.09.11 根据任务的taskId返回任务的List
	@RequestMapping("getOrderTaskFromKind/{taskKind}")
	public ResponseEntity<List<Task>> GetOrderTaskFromKind(@PathVariable Integer taskKind) {
		System.out.println("被访问的函数的名称：getOrderTaskFromKind");
		System.out.println("被访问的函数的功能：根据任务的taskId返回任务的List");
		System.out.println("输入参数taskKind：" + taskKind);


		System.out.println(taskKind);
		return new ResponseEntity<List<Task>>(taskService.SelTaskFromKind(taskKind), HttpStatus.OK);
	}

	///2019-11-03 功能：根据前端传输的用户的ID，返回此用户已经接收的所有任务的详情
	///------此处的操作为跨数据库操作，使用UT里面的函数getUserAllAcceptTaskId里的调用函数seluserIdForTaskId查出来的List<INTEGER>
	///------进行操作，根据List<INTEGER>存储的查询出来的taskId的List<>搜索出其对应的Task的List
	@RequestMapping("getUserAllAcceptTaskFromUT/{userId}")
	public ResponseEntity<List<Task>> getUserAllAcceptTaskFromUT(@PathVariable int userId) {
		///跨结构引用，引用UTService里的函数，seluserIdForTaskId(userId)功能为：返回此用户已经接收的所有任务的taskId
		List<Integer> taskIdListtemp = user_taskService.seluserIdForTaskId(userId);
		///新建一个List<Task>类型的数据，目的是为了存储查询的结果
		List<Task> taskList1 = new ArrayList<Task>();
		for (int taskIdTemp : taskIdListtemp){  ///利用循环遍历Integer类型的taskIdListtemp，即遍历在UT中查询的taskId
			///存储每一次遍历的结果于临时值
			Task taskTemp = taskService.Sel_Task(taskIdTemp);
			if (taskTemp == null ){
				continue;
			}
			///将查询结果添加到taskList1
			taskList1.add(taskTemp);
		}
		if (taskList1 == null ){
			return new ResponseEntity<List<Task>>( HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<List<Task>>(taskList1, HttpStatus.OK);
		}
	}
	///2019-11-06 功能：根据前端传输的用户的ID，返回此用户已经接收并已经完成所有任务的详情
	///------此处的操作为跨数据库操作，使用UT里面的函数getUserAllFinishTaskId里的
	///------调用函数seluserIdFinish查出来的List<User_Task>
	///------进行操作，根据从遍历List<User_Task>存储的每一条数据中查询出来的taskId搜索出其对应的Task的List
	@RequestMapping("getUserAllFinishTaskFromUT/{userId}")
	public ResponseEntity<List<Task>> getUserAllFinishTaskFromUT(@PathVariable int userId){
		///根据userId查找UT中符合条件的List<User_Task>
		List<User_Task> tempList = user_taskService.seluserIdFinish(userId);
		///新建一个List<Task>类型的数据，目的是为了存储List<User_Task>在T中的查询结果
		List<Task> taskList1 = new ArrayList<Task>();
		for (int i = 0 ;i < tempList.size() ; i++){
			System.out.println(i);	///	测试i专用
			///取出List<User_Task> tempList中的一条数据，并且继续取出其taskId
			Integer taskIdListTemp = user_taskService.seluserIdFinish(userId).get(i).getTaskId();
			Task taskTemp = taskService.Sel_Task(taskIdListTemp);
			if (taskTemp == null ){
				continue;
			}
			taskList1.add(taskTemp);
		}
		if (taskList1 == null ){
			return new ResponseEntity<List<Task>>( HttpStatus.NOT_FOUND);
		}
		else{
			return new ResponseEntity<List<Task>>(taskList1, HttpStatus.OK);
		}
	}

	///2019-11-06  根据前端传输的用户的ID，返回此用户已经接收的但是未完成完成的所有任务
	///------此处的操作为跨数据库操作，使用UT里面的函数getUserUnfinishTask里的
	///------调用函数seluserIdUnfinish查出来的List<User_Task>
	///------进行操作，根据从遍历List<User_Task>存储的每一条数据中查询出来的taskId搜索出其对应的Task的List
	@RequestMapping("getUserAllUnfinishTaskFromUT/{userId}")
	public ResponseEntity<List<Task>> getUserAllUnfinishTaskFromUT(@PathVariable int userId){
		///根据userId查找UT中符合条件的List<User_Task>
		List<User_Task> tempList = user_taskService.seluserIdUnfinish(userId);
		///新建一个List<Task>类型的数据，目的是为了存储List<User_Task>在T中的查询结果
		List<Task> taskList1 = new ArrayList<Task>();
		for (int i = 0 ;i < tempList.size() ; i++){
			System.out.println(i);	///	测试i专用
			///取出List<User_Task> tempList中的一条数据，并且继续取出其taskId
			Integer taskIdListTemp = user_taskService.seluserIdUnfinish(userId).get(i).getTaskId();
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

	///2019-11-06  根据前端传输的用户的ID，根据推荐系统的策略返回此用户最合适接受的未执行的任务（目前的本质是随机返回）
	///------此处的操作为跨数据库操作，使用UT里面的函数getUserRecommendTask里的
	///------调用函数seluserIdRandom查出来的List<User_Task>
	///------进行操作，根据从遍历List<User_Task>存储的每一条数据中查询出来的taskId搜索出其对应的Task的List
	@RequestMapping("getUserAllRecommendFromUT/{userId}")
	public ResponseEntity<List<Task>> getUserAllRecommendTaskFromUT(@PathVariable int userId){
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






//	////2019.7.6 修改：一次性返回数据库最后面的十条数据   仅供参考
//	@RequestMapping("getNewTen/{MaxtaskId&MintaskId}")
//	public ResponseEntity<List<Task>> GetNewTen(@PathVariable Integer MaxtaskId,Integer MintaskId) {
//		//return userService.SelInfo(id).toString();
//		//System.out.println();
//		//System.out.println(taskService.getTen());
//
//		if (taskService.getTen() == null) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		} else {
//			return new ResponseEntity<List<Task>>(taskService.getTen(), HttpStatus.OK);
//		}
//	}


//	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
//	@ResponseBody
//	public String add_Task_include_pic(Task task, @RequestParam("file") MultipartFile file) {
//
//		System.out.println(task);
//
//
//
//		// 获取文件名
//		String fileName = file.getOriginalFilename();
//		// 获取文件的后缀名
//		String suffixName = fileName.substring(fileName.lastIndexOf("."));
//		// 文件上传后的路径
//		String filePath = "E:\\springboot-upload\\image\\";
//		// 解决中文问题，liunx下中文路径，图片显示问题
//		// fileName = UUID.randomUUID() + suffixName;
//		File dest = new File(filePath + fileName);
//		// 检测是否存在目录
//		if (!dest.getParentFile().exists()) {
//			dest.getParentFile().mkdirs();
//		}
//		try {
//			file.transferTo(dest);
//			return "OK";
//		} catch (IllegalStateException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "文件上传失败";
//
//	}

	/////FUNCTION---向前台返回数据，进行了前后台的连接--2019.6.15
	///前后台对接，并经过测试,传输的数据格式是Task
//	@RequestMapping(value="check_Task", method=RequestMethod.POST)
//	@ResponseBody
//	public ResponseEntity<Task> add_Task(/*@RequestBody*/  Task task) {
//		System.out.println(task);
//
//		/*该出逻辑验证代码*/
//
//		Task u = taskService.check_Task(task);
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

}
