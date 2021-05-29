package com.example.service;

import com.example.entity.User;
import com.example.entity.User_Task;
import com.example.mapper.UserMapper;
import com.example.mapper.User_TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:0xOO
 * @Date: 2018/9/26 0026
 * @Time: 15:23
 */

/**
 * @Author:DarenSu
 * @Date: 2019/6/13 修改
 * @Time: 14:42
 */

@Service
public class User_TaskService {
	@Autowired
    User_TaskMapper user_taskMapper;
	@Autowired
    UserMapper userMapper;


	//2019.7.11 User-task table query function
	public User_Task Sel(int user_taskId) { return user_taskMapper.Sel(user_taskId); }

	//2019.7.13 Upload task data to user-task table
	public void addUser_Task(User_Task user_task) {
		user_taskMapper.add(user_task);
	}

	public User_Task getStatus (User_Task user_task) {
		return user_taskMapper.getStatus(user_task);
	}

	//2019.11.30 Accept the task and change the task status to 1
	public void acceptUser_Task (User_Task user_task){
		 user_taskMapper.acceptUser_Task(user_task);
	}
	//2019-11-30 For testing, if the data is not available, it needs to be added separately
	public User_Task SelUser_Task (User_Task user_task) {
		return user_taskMapper.SelUser_Task(user_task);
	}

	//2019.7.16  The user-task table is updated. Before, only userId and taskId were only used. Now it is updated,
	// that is, fill in the content and image behind.
	public void updateUser_Task (User_Task user_task) {
		user_taskMapper.update(user_task);
	}

	////2019.7.18 View the completion of the task, that is, see how many people are performing the task, as long as
	// the task is to be counted
	////Input parameter taskId, return List<user_task> and return List<user>
	////Query the corresponding user_task class by taskId
	////Then query the corresponding user class according to the userId in the queried user_task class (mainly the
	// userName in the user)
	public List<User_Task> SelTaskId (Integer taskId){
		return user_taskMapper.SelTaskId(taskId);
	}
	public List<User_Task> SelTaskIdForUT (Integer taskId){
		return user_taskMapper.SelTaskIdForUT(taskId);
	}

	//2019.9.10  Input parameter utask, return a single picture.
	public User_Task selcetUser_Task (User_Task utask){
		return user_taskMapper.selcetUser_Task(utask);
	}




	//2019.7.2 Modification: Modified from a single result display to multiple result displays
	public List<User> SelInfo(String userName ) {
		return userMapper.SelInfo(userName);
	}



	public User EnterUser(User user ) {
		return userMapper.Enter(user);
	}

	public User check(User user ) {
		return userMapper.check(user);
	}

	//2019-11-1   According to the user ID transmitted by the front end, return all tasks that this user has received
	public List<User_Task> seluserId (int userId){
		return user_taskMapper.seluserId(userId);
	}
	//2019-11-1   According to the ID of the user transmitted by the front-end, return that the user has received
	// and completed all tasks
	public List<User_Task> seluserIdFinish (int userId){
		return user_taskMapper.seluserIdFinish(userId);
	}

	// 2019-11-3   According to the user ID transmitted by the front-end, return all tasks that the user has received
	// but not completed
	public List<User_Task> seluserIdUnfinish (int userId){
		return user_taskMapper.seluserIdUnfinish(userId);
	}

	// 2019-11-1   According to the user ID transmitted by the front-end, the unexecuted tasks that the user is most
	// suitable to accept according to the strategy of the recommendation system are returned (the current nature is
	// random return)
	public List<User_Task> seluserIdRandom (int userId){
		return user_taskMapper.seluserIdRandom(userId);
	}


	// 2019-11-3   According to the user ID transmitted by the front end, return the taskId of all tasks that this
	// user has received
	public List<Integer> seluserIdForTaskId (int userId){
		return user_taskMapper.seluserIdForTaskId(userId);
	}








	// test
	public void addUser(User user) {
		userMapper.add(user);
	}

	// test
	public User checkUser(User user) {
		return userMapper.checkUser(user);
	}

}
