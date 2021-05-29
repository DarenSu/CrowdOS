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
import java.util.Calendar;
import java.util.Date;
import java.util.List;




/**
 * @Author:DarenSu
 * @Date: 2019/6/13
 * @Time: 14:42
 */

@RestController      //To indicate the module, here is the control module
@RequestMapping("/user_task")
public class User_TaskController {

	@Autowired
	private User_TaskService user_taskService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private UserService userService;

	private Object setuser1;

	//2019.7.11  User task table query function
	@RequestMapping("getUser_Task/{user_taskId}")
	public String GetUser_Task(@PathVariable int user_taskId) {
		return user_taskService.Sel(user_taskId).toString();
	}

	//2019.7.16  The status of the user task table is returned, the input parameters are userId and taskId, and
	// User_taskStatus is returned
	// -1：No tasks  0：there exists tasks but haven't been executed  1：there exists tasks and have been executed
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
	//2019.7.11  Function: upload task data, upload user_task data from the front end to the backend database
	@RequestMapping(value="addUser_Task", method=RequestMethod.POST)
	public ResponseEntity<User_Task> addUser_Task( @RequestBody User_Task user_task) {
//		User_Task check_userName = user_taskService.check(user_task);
//		if (check_userName == null){
//			user_taskService.addUser_Task(user_task);
//			return new ResponseEntity<>(user_task, HttpStatus.OK);
//		}else{
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}

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

    ///2019.11.30 Accept the task, change the task status to 0 (accept the unfinished status)
    @RequestMapping(value="acceptUser_Task", method=RequestMethod.POST)
    public ResponseEntity<User_Task> acceptUser_Task(@RequestBody User_Task user_task){
        ///Add a judgment.
		// 		If the judgment is missed, the judgment will be made at this time.
		// 		If the status is only 1, the task cannot be accepted
        if (user_taskService.SelUser_Task(user_task) != null ) {
            User_Task ut = user_taskService.getStatus(user_task);

            //System.out.println(ut.getUser_taskStatus());
            if (ut.getUser_taskStatus() != null) {
                if (ut.getUser_taskStatus() == 1) {///  400
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                if (ut.getUser_taskStatus() == 0) {///  202
                    return new ResponseEntity<>(HttpStatus.ACCEPTED);
                }
            }
            ///If this data is not available, it needs to be added separately
            System.out.println(user_task);
        }
        if ( user_taskService.SelUser_Task(user_task) == null ){
			Calendar calendar = Calendar.getInstance();
			Date date = calendar.getTime();
			System.out.println(date);

			Task task= new Task();
			task.setTaskId(user_task.getTaskId());
			Task tasktest= taskService.SelTaskFromTaskId(task.getTaskId());
			System.out.println("查找出来的tasktest"+tasktest);
			int residueTotalNum= tasktest.getTotalNum()- 1;//The current remaining strength of the mission was reduced by one

			System.out.println("date="+date);
			System.out.println("tasktest.getDeadLine()="+tasktest.getDeadLine());
			System.out.println(date.compareTo(tasktest.getDeadLine()));
			System.out.println(date.compareTo(tasktest.getPostTime()));
			System.out.println("residueTotalNum="+ residueTotalNum);
			//20210515
			if( tasktest != null && date.compareTo(tasktest.getDeadLine()) <= 0  && date.compareTo(tasktest.getPostTime()) >= 0 && residueTotalNum >= 0){
				//System.out.println(user_task);
				// 20210515
				tasktest.setTotalNum(residueTotalNum);
				// 20210515		Update the remaining numbers
				taskService.updateTaskTotalNum(tasktest);
				System.out.println("剩余人数更新完成，已完成-1操作");
				user_taskService.addUser_Task(user_task);
			}else{// 20210515	The task has expired, 403
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}


        }
	    user_task.setUser_taskStatus(0);
        //System.out.println(user_task);
		//Change task state from 0 to 1
        user_taskService.acceptUser_Task(user_task);
	    return new ResponseEntity<User_Task>(HttpStatus.OK);
    }

//    @RequestMapping(value = "updateTotalNum", method = RequestMethod.POST)
//	public ResponseEntity<Task> updateTotalNum(@RequestParam Task task){
//		task.
//		return new ResponseEntity<Task>(taskService.SelTaskFromTaskId(task),HttpStatus.OK);
//	}
	// 20210516  Enter UT, including taskID, to query the entire data
    @RequestMapping(value="SelUser_Task",method = RequestMethod.POST)
    public ResponseEntity<User_Task> SelUser_Task(@RequestBody User_Task user_task) {
        return new ResponseEntity<User_Task>(user_taskService.SelUser_Task(user_task),HttpStatus.OK);
    }

	//2019.7.16  User-task table update, previously only userId and taskId, now update, that is to fill the content
	// and image behind
	@RequestMapping(value="updateUser_Task", method=RequestMethod.POST)
	public ResponseEntity<User_Task> updateUser_Task( @RequestBody User_Task user_task){
		System.out.println("欢迎来到用户-任务表更新--接受任务后：user_task/updateUser_Task");

		System.out.println(user_task);
		if (user_taskService.SelUser_Task(user_task).getUser_taskStatus() == 0 ) {

		user_task.setUser_taskStatus(1);

		user_taskService.updateUser_Task(user_task);

		return new ResponseEntity<>(HttpStatus.OK);
		}else {
			System.out.println("已有该数据，该任务已执行");
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	////2019.7.2 Modification: Modified from a single result display to multiple result displays
	@RequestMapping("getUserInfo/{userName}")
	public List<User> GetUserInfo(@PathVariable String userName) {
		//return userService.SelInfo(userName).toString();
		System.out.println(userName);
		System.out.println(userService.SelInfo(userName));

		return userService.SelInfo(userName);
	}


	////2019.7.18 View the completion of the task, that is, see how many people are performing the task, as long as
	// the task is to be counted
	////Input parameter taskId, return List<user_task> and return List<user>
	////Query the corresponding user_task class by taskId
	////Then query the corresponding user class according to the userId in the queried user_task class (mainly the userName in the user)
	@RequestMapping ("getTaskAllUser/{taskId}")
	public List<User_Task> GetTaskAllUser(@PathVariable  Integer taskId){
		System.out.println(taskId);
		return user_taskService.SelTaskId(taskId);
	}

	////2019.7.18 According to the taskId, first look up the userId in the user_task class, and then query the
	// corresponding userName in the user class according to this userId
	@RequestMapping ("getTaskIdToUserName/{taskId}")
	public ResponseEntity<List<Combine_u_ut>> GetTaskIdToUserName(@PathVariable  Integer taskId){
        System.out.println("被访问的函数的名称：getTaskIdToUserName");
        System.out.println("被访问的函数的功能：根据taskId，在user_task类里查找userId，根据userId查询user类对应的userName");
	    System.out.println("输入的参数taskId：" + taskId );

		///SelTaskId--To heavy query
		List<User_Task> ut = user_taskService.SelTaskIdForUT(taskId);
		List<Combine_u_ut> combine_u_utList = new ArrayList<Combine_u_ut>();
		for(User_Task user_task : ut) {
			Integer userID= user_task.getUserId();
			System.out.println(userID);
//			String[] spilts = user_task.getImage().split(File.separator + File.separator);
//			String fileName = spilts[spilts.length - 1];
//			User_Task tem_ut = new User_Task(user_task.getUser_taskId(),user_task.getUserId(),user_task.getTaskId(),user_task.getUser_taskStatus(),user_task.getContent(),fileName,user_task.getType());
			///2019-11-29
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


	///According to taskId, return the user_task class, and there is no duplicate data




	///2019.7.25 Upload the text message and picture of the task, two parameters, user_task text message + single picture
	///Currently there is no realization of uploading multiple pictures, but the realization of uploading a single picture
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	@ResponseBody

	public ResponseEntity<User_Task> add_Task_include_pic(@RequestPart("utask") User_Task utask, @RequestPart("file") MultipartFile file) {

        System.out.println(utask);

		String fileName_temp = file.getOriginalFilename();
		System.out.println("fileName_temp="+fileName_temp);

		String str_uid= utask.getUserId()+ "";
		String str_tid= utask.getTaskId()+ "";
		StringBuilder stringBuilder= new StringBuilder(str_uid);
		stringBuilder.append("-");
		stringBuilder.append(str_tid);
		stringBuilder.append("-");
		stringBuilder.append(fileName_temp);

		String fileName= stringBuilder.toString();
		System.out.println("修改后的fileName="+fileName);


		//2019.9.17  Read the size of the uploaded file, cannot exceed the maximum supported by SSM  ,unit:B
		long fileLength = 0L;
		fileLength = file.getSize()/1024;
		System.out.println(fileLength+"KB"+"    "+"单张照片小于20480KB，可以传输");
		if (fileLength > 20480){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// Gets the suffix name of the file
		String suffixName = fileName.substring(fileName.lastIndexOf("."));
		// The path to the file after uploading. Test yourself
//		String filePath = "E:" + File.separator + "springboot-upload" + File.separator + "image" + File.separator ;
		// Server test
		String filePath =  "/E/springboot-upload/image/";

		// Solving Chinese Language Problems.
		// Liunx Chinese path.
		// picture display problem
		// fileName = UUID.randomUUID() + suffixName;
		File dest = new File(filePath + fileName);
		// Checks for the existence of a directory
		if (!dest.getParentFile().exists()) {
			//Generate the path itself if it doesn't exist
			dest.getParentFile().mkdirs();
		}
		try {
		    if(!dest.exists()) {
                file.transferTo(dest);
            }
			//Upload the image address to the relevant location in the database
			utask.setImage(filePath + fileName);
			//At the same time, the specific content of the task will also be uploaded.
			// At this point, the utask already has the location information of the image
			user_taskService.addUser_Task(utask);

			return new ResponseEntity<>(utask, HttpStatus.OK);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	///2019.9.10  Input parameter utask, return a single picture
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

	///2019.9.15  Input parameter image, return a single image
	@RequestMapping("downImageFromImage/{image}")
	public ResponseEntity<byte[]> downImageFromImage(@PathVariable String image) {


//		String suffixName = image.substring(image.lastIndexOf(File.separator));
//		image = suffixName;
		//2019.9.17
		boolean status1 = image.contains("/");
		boolean status2 = image.contains("\\");
		if( status1 || status2  ){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		//2019.9.15
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

//	///2019.9.15  test
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


	///2019.7.25 Download task text information and pictures, two parameters, user_task text information + single picture
	///Currently, there is no realization of downloading multiple pictures, only the download of a single picture is realized
	@RequestMapping(value = "/downloadImage", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<User_Task> look_Task_include_pic(User_Task utask, @RequestParam("file") MultipartFile file) {

		String fileName = file.getOriginalFilename();

		String suffixName = fileName.substring(fileName.lastIndexOf("."));

		String filePath = "E:\\springboot-upload\\image\\";

		// fileName = UUID.randomUUID() + suffixName;
		File dest = new File(filePath + fileName);

		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}
		try {
			file.transferTo(dest);

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

	///2019-11-1   According to the user ID transmitted by the front end, return all tasks that this user has received
	@RequestMapping("getUserAllAcceptTask/{userId}")
	public ResponseEntity<List<User_Task>> getUserAllAcceptTask(@PathVariable int userId){
		System.out.println(userId);
		System.out.println(user_taskService.seluserId(userId));

		return new ResponseEntity<List<User_Task>>(user_taskService.seluserId(userId),HttpStatus.OK);
	}

	///2019-11-1   According to the ID of the user transmitted by the front-end, return that the user has received
	// and completed all tasks
	@RequestMapping("getUserAllFinishTask/{userId}")
	public ResponseEntity<List<User_Task>> getUserAllFinishTask(@PathVariable int userId){
		System.out.println(userId);
		System.out.println(user_taskService.seluserIdFinish(userId));

		return new ResponseEntity<List<User_Task>>(user_taskService.seluserIdFinish(userId),HttpStatus.OK);
	}


	///2019-11-3   According to the user ID transmitted by the front-end,
	// return all tasks that the user has received but not completed
	@RequestMapping("getUserAllUnfinishTask/{userId}")
	public ResponseEntity<List<User_Task>> getUserAllUnfinishTask(@PathVariable int userId){
		System.out.println(userId);
		List<User_Task> user_tasks = user_taskService.seluserIdUnfinish(userId);
		System.out.println(user_tasks);

		List<User_Task> user_tasks1 = new ArrayList<>();
		for (int i = 0; i<user_tasks.size();i++){
			if (user_tasks.get(i).getUser_taskStatus()==0){
				user_tasks1.add(user_tasks.get(i));
			}
		}

		return new ResponseEntity<List<User_Task>>(user_tasks1,HttpStatus.OK);
		//return new ResponseEntity<List<User_Task>>(user_taskService.seluserIdUnfinish(userId),HttpStatus.OK);
	}

	///2019-11-1   According to the user ID transmitted by the front-end, the unexecuted tasks that the user is most
	// suitable to accept according to the strategy of the recommendation system are returned (the current nature is random return)
	@RequestMapping("getUserAllRecommendTask/{userId}")
//	public ResponseEntity<List<User_Task>> getUserAllRecommendTask(@PathVariable int userId){
//		System.out.println(userId);
//		System.out.println(user_taskService.seluserIdRandom(userId));
//
//		return new ResponseEntity<List<User_Task>>(user_taskService.seluserIdRandom(userId),HttpStatus.OK);
//	}
	public ResponseEntity<List<Task>> getUserAllRecommendTask(@PathVariable int userId){

		List<User_Task> tempList = user_taskService.seluserIdRandom(userId);

		List<Task> taskList1 = new ArrayList<Task>();
		for (int i = 0 ;i < tempList.size() ; i++){
			System.out.println(i);

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

	///2019-11-3   According to the user ID transmitted by the front end, return the taskId of all tasks that this
	// user has received
	@RequestMapping("getUserAllAcceptTaskId/{userId}")
	public ResponseEntity<List<Integer>> getUserAllAcceptTaskId(@PathVariable int userId){
		System.out.println(userId);
		System.out.println(user_taskService.seluserIdForTaskId(userId));

		return new ResponseEntity<List<Integer>>(user_taskService.seluserIdForTaskId(userId),HttpStatus.OK);
	}

	
}
