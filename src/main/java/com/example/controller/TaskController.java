package com.example.controller;

import com.example.entity.Task;
import com.example.entity.User_Task;
import com.example.service.TaskService;
import com.example.service.User_TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;



/**
 * @Author:DarenSu
 * @Date: 2019/6/13
 * @Time: 14:42
 */


@RestController      //To indicate the module, here is the control module
@RequestMapping("/task")
public class TaskController {


	@Autowired
	private TaskService taskService;
	//private Object setuser1;
	@Autowired
	private User_TaskService user_taskService;
	//20200110
	Calendar calendar = Calendar.getInstance();
	Date dateone = calendar.getTime();

	
///         2016.6.18
	
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


	//2019.6.22
	//Front-end and back-end docking 2019.6.22 There is a problem with writing by myself.  I don’t need requestbody
	// to test the form by myself. The front-end and back-end docking uses json data, so requestbody is required.
	@PostMapping(value="add_Task")
	@ResponseBody
	public ResponseEntity<Task> add_Task(@RequestBody Task task) {
		//20210110  Gets only the number of milliseconds
		Long time1 = System.currentTimeMillis();

		System.out.println(getType(System.currentTimeMillis()));
		//20210110
		Calendar calendar1 = Calendar.getInstance();
		Date dateonetemp = calendar1.getTime();
		System.out.println("进入函数--时分秒"+dateonetemp);
		System.out.println("进入函数--毫秒"+System.currentTimeMillis());

//		Task task1 = new Task();
//		System.out.println(task1);
		System.out.println("欢迎来到任务添加功能：task的add_Task");
		//taskService.add_Task(task);

		System.out.println(task);
		System.out.println(task.toString());
		//20201012  Prevent empty data from coming in
		Task taskTemp = task;
		if (taskTemp.getTaskId() == null && taskTemp.getTaskName() != null &&
				//taskTemp.getPostTime() != null && taskTemp.getDeadLine() != null &&
				taskTemp.getUserId() != null && taskTemp.getUserName() != null &&
				taskTemp.getCoin() != null && taskTemp.getDescribe_task() != null &&
				taskTemp.getTotalNum() != null && taskTemp.getTaskStatus() != null &&
				taskTemp.getTaskKind() != null /*&& taskTemp.getTemp() != null &&
				taskTemp.getLongitude() != null && taskTemp.getLatitude() != null &&
				taskTemp.getSensorTypes() != null*/) {
			//20201024 If the data is not empty, you need to check that the data type is correct
			if (taskTemp.getTaskName() instanceof String  &&
					//taskTemp.getPostTime() instanceof Date && taskTemp.getDeadLine() instanceof Date &&
					taskTemp.getUserId() instanceof Integer && taskTemp.getUserName() instanceof String &&
					taskTemp.getCoin() instanceof Float && taskTemp.getDescribe_task() instanceof String &&
					taskTemp.getTotalNum() instanceof Integer && taskTemp.getTaskStatus() instanceof Integer &&
					taskTemp.getTaskKind() instanceof Integer /*&& taskTemp.getTemp() instanceof Integer &&
					taskTemp.getLongitude() instanceof Float && taskTemp.getLatitude() instanceof Float	&&
					taskTemp.getSensorTypes() instanceof String	*/) {
				//20201102   add
				taskService.add_Task(task);
				System.out.println(task);

				//20200110
				//20200110  Gets only the number of milliseconds
				Long time2 = System.currentTimeMillis()-time1;
				Calendar calendar = Calendar.getInstance();
				Date dateone1 = calendar.getTime();
				System.out.println(dateone);
				System.out.println(dateonetemp);
				System.out.println(dateone1);
				System.out.println(time2);

				System.out.println("离开函数--时分秒"+dateonetemp);
				System.out.println("离开函数--毫秒"+System.currentTimeMillis());

				return new ResponseEntity<>(task, HttpStatus.OK);
			}
			//return "OK";
			else {//The data type is incorrect,  return 406

				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
		} else {//Empty data  400
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	////2019.7.5 Modification: Modified from a single result display to multiple result displays
	@RequestMapping("getUserInfo/{taskName}")
	public List<Task> GetUserInfo(@PathVariable String taskName) {
		//return userService.SelInfo(id).toString();
		System.out.println(taskName);
		System.out.println(taskService.SelInfo(taskName));

		return taskService.SelInfo(taskName);
	}

	////2019.7.6 Modification: Return the last ten data of the database at one time
	@RequestMapping("getTen")
	public ResponseEntity<List<Task>> GetTen() {
		//return userService.SelInfo().toString();
		//System.out.println();
		//System.out.println(taskService.getTen());
		//20200110  Gets only the number of milliseconds
		Long time1 = System.currentTimeMillis();
		System.out.println(getType(System.currentTimeMillis()));
		//20200110
		Calendar calendar1 = Calendar.getInstance();
		Date dateonetemp = calendar1.getTime();
		System.out.println("进入函数--时分秒"+dateonetemp);
		System.out.println("进入函数--毫秒"+System.currentTimeMillis());

		if (taskService.getTen() == null) {
			//20200110
			//20200110  Gets only the number of milliseconds
			Long time2 = System.currentTimeMillis()-time1;
			Calendar calendar = Calendar.getInstance();
			Date dateone1 = calendar.getTime();
			System.out.println(dateone);
			System.out.println(dateonetemp);
			System.out.println(dateone1);
			System.out.println(time2);
			System.out.println("离开函数--时分秒"+dateonetemp);
			System.out.println("离开函数--毫秒"+System.currentTimeMillis());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			//20200110
			//20200110  Gets only the number of milliseconds
			Long time2 = System.currentTimeMillis()-time1;
			Calendar calendar = Calendar.getInstance();
			Date dateone1 = calendar.getTime();
			System.out.println(dateone);
			System.out.println(dateonetemp);
			System.out.println(dateone1);
			System.out.println(time2);
			System.out.println("离开函数--时分秒"+dateonetemp);
			System.out.println("离开函数--毫秒"+System.currentTimeMillis());

			//In reverse order, the newest one comes first in chronological order
			List<Task> taskTemp = taskService.getTen();
//            System.out.println("正序 ");
//			for (Task n : taskTemp) {
//                System.out.println(n + " ");
//            }

//            System.out.println("逆序 ");
            Collections.reverse(taskTemp);
//            for (Task n : taskTemp) {
//                System.out.println(n + " ");
//            }

            return new ResponseEntity<List<Task>>(taskService.getTen(), HttpStatus.OK);
//            return new ResponseEntity<List<Task>>(taskTemp, HttpStatus.OK);
		}
	}


	//2019.7.18  Function: Query all tasks published by a userId, input parameter: userId, return result: List<task>
	@RequestMapping("getUserAllTask/{userId}")
	public List<Task> GetUserAllTask(@PathVariable Integer userId) {
		System.out.println("被访问的函数的名称：getUserAllTask");
		System.out.println("被访问的函数的功能：查询某userId所发布的所有的任务");
		System.out.println("输入参数userId：" + userId);

		//20201024  Test output data
		System.out.println(taskService.SelUserId(userId).get(0));


        List<Task> taskTemp = taskService.SelUserId(userId);
//            System.out.println("正序 ");
//			for (Task n : taskTemp) {
//                System.out.println(n + " ");
//            }

//            System.out.println("逆序 ");
        Collections.reverse(taskTemp);
		return taskTemp;

	}

	private static String getType(Object a) {
		return a.getClass().toString();

	}

	////2019.7.6 Modification: Return the last ten data of the database one time
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
	///2019.09.11 Return the list of tasks according to the taskId of the task
	@RequestMapping("getOrderTaskFromKind/{taskKind}")
	public ResponseEntity<List<Task>> GetOrderTaskFromKind(@PathVariable Integer taskKind) {
		System.out.println("被访问的函数的名称：getOrderTaskFromKind");
		System.out.println("被访问的函数的功能：根据任务的taskId返回任务的List");
		System.out.println("输入参数taskKind：" + taskKind);


		System.out.println(taskKind);
		return new ResponseEntity<List<Task>>(taskService.SelTaskFromKind(taskKind), HttpStatus.OK);
	}

	///2019-11-03 Function: According to the user ID transmitted by the front end, return the details of all tasks
	// that the user has received
	///------The operation here is a cross-database operation, using the call function seluserIdForTaskId in the
	// function getUserAllAcceptTaskId in UT to find out List<INTEGER>
	///------Perform the operation and search for the corresponding Task List according to the List<> of taskId stored in List<INTEGER>.
	@RequestMapping("getUserAllAcceptTaskFromUT/{userId}")
	public ResponseEntity<List<Task>> getUserAllAcceptTaskFromUT(@PathVariable int userId) {
		///Cross-structure references:
		// References to functions in UTService  ;
		// seluserIdForTaskId(userId)：Returns the TASKID of all tasks that this user has received
		List<Integer> taskIdListtemp = user_taskService.seluserIdForTaskId(userId);

		List<Task> taskList1 = new ArrayList<Task>();
		for (int taskIdTemp : taskIdListtemp){
			Task taskTemp = taskService.Sel_Task(taskIdTemp);
			if (taskTemp == null ){
				continue;
			}

			taskList1.add(taskTemp);
		}
		if (taskList1 == null ){
			return new ResponseEntity<List<Task>>( HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<List<Task>>(taskList1, HttpStatus.OK);
		}
	}
	///2019-11-06 Function: According to the user ID transmitted by the front-end, return the details of all tasks
	// that the user has received and completed
	///------The operation here is a cross-database operation, using the function getUserAllFinishTaskId in UT
	///------Call the function seluserIdFinish to find out List<User_Task>
	///------Perform the operation and search for the corresponding Task List according to the taskId queried from
	// each piece of data stored in the traversal List<User_Task>
	@RequestMapping("getUserAllFinishTaskFromUT/{userId}")
	public ResponseEntity<List<Task>> getUserAllFinishTaskFromUT(@PathVariable int userId){
		///Find the qualified ones in the UT by the userId .  List<User_Task>
		List<User_Task> tempList = user_taskService.seluserIdFinish(userId);

		List<Task> taskList1 = new ArrayList<Task>();
		for (int i = 0 ;i < tempList.size() ; i++){
			System.out.println(i);

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

	///2019-11-06  According to the user ID transmitted by the front-end, return all tasks that the user has
	// received but not completed
	///------The operation here is a cross-database operation, using the function getUserUnfinishTask in UT
	///------Call the function seluserIdUnfinish to find out List<User_Task>
	///------Perform the operation and search for the corresponding Task List according to the taskId queried from
	// each piece of data stored in the traversal List<User_Task>
	@RequestMapping("getUserAllUnfinishTaskFromUT/{userId}")
	public ResponseEntity<List<Task>> getUserAllUnfinishTaskFromUT(@PathVariable int userId){

		List<User_Task> tempList = user_taskService.seluserIdUnfinish(userId);

		List<Task> taskList1 = new ArrayList<Task>();
		for (int i = 0 ;i < tempList.size() ; i++){
			System.out.println(i);
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

	///2019-11-06  According to the user ID transmitted by the front-end, the unexecuted tasks that the user is most
	// suitable to accept according to the strategy of the recommendation system are returned (the current nature is
	// random return)
	///------The operation here is a cross-database operation, using the function getUserRecommendTask in UT
	///------List<User_Task> detected by calling function seluserIdRandom
	///------Perform the operation and search for the corresponding Task List according to the taskId queried from
	// each piece of data stored in the traversal List<User_Task>
	@RequestMapping("getUserAllRecommendFromUT/{userId}")
	public ResponseEntity<List<Task>> getUserAllRecommendTaskFromUT(@PathVariable int userId){

		List<User_Task> tempList = user_taskService.seluserIdRandom(userId);

		List<Task> taskList1 = new ArrayList<Task>();
		//20210111
//		List<Task> taskList2 = new ArrayList<>();
//		taskList2 = taskService.getAll();
//		int num = 0;
//		for (int i = 0 ;i < taskList2.size() ; i++){
//			System.out.println(i);	///	测试i专用
//			///20210111 判断该任务没有与该用户关联
//			if (taskList2.get(i)){}
//			///取出List<User_Task> tempList中的一条数据，并且继续取出其taskId
//			Integer taskIdListTemp = user_taskService.seluserIdRandom(userId).get(i).getTaskId();
//			Task taskTemp = taskService.Sel_Task(taskIdListTemp);
//			taskList1.add(taskTemp);
//		}


		for (int i = 0 ;i < tempList.size() ; i++){
			System.out.println(i);
			Integer taskIdListTemp = user_taskService.seluserIdRandom(userId).get(i).getTaskId();
			Task taskTemp = taskService.Sel_Task(taskIdListTemp);
			if (taskTemp != null) {
				taskList1.add(taskTemp);
			}
//			if (taskIdListTemp < 50 ){
//				taskIdListTemp+=2;taskTemp = taskService.Sel_Task(taskIdListTemp);taskList1.add(taskTemp);
//				taskIdListTemp+=4;taskTemp = taskService.Sel_Task(taskIdListTemp);taskList1.add(taskTemp);
//				taskIdListTemp+=6;taskTemp = taskService.Sel_Task(taskIdListTemp);taskList1.add(taskTemp);
//				taskIdListTemp+=8;taskTemp = taskService.Sel_Task(taskIdListTemp);taskList1.add(taskTemp);
//				taskIdListTemp+=10;taskTemp = taskService.Sel_Task(taskIdListTemp);taskList1.add(taskTemp);
//			}else {
//				taskIdListTemp-=2;taskTemp = taskService.Sel_Task(taskIdListTemp);taskList1.add(taskTemp);
//				taskIdListTemp-=4;taskTemp = taskService.Sel_Task(taskIdListTemp);taskList1.add(taskTemp);
//				taskIdListTemp-=6;taskTemp = taskService.Sel_Task(taskIdListTemp);taskList1.add(taskTemp);
//				taskIdListTemp-=8;taskTemp = taskService.Sel_Task(taskIdListTemp);taskList1.add(taskTemp);
//				taskIdListTemp-=10;taskTemp = taskService.Sel_Task(taskIdListTemp);taskList1.add(taskTemp);
//			}

		}
		if (taskList1 == null ){
			return new ResponseEntity<List<Task>>( HttpStatus.NOT_FOUND);
		}
		else{
			return new ResponseEntity<List<Task>>(taskList1, HttpStatus.OK);
		}
	}






//	////2019.7.6 Returns the last ten data points in the database at once
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



//	@RequestMapping(value="check_Task", method=RequestMethod.POST)
//	@ResponseBody
//	public ResponseEntity<Task> add_Task(/*@RequestBody*/  Task task) {
//		System.out.println(task);
//
//
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
