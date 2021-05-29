package com.example.service;

import com.example.entity.Task;
import com.example.mapper.TaskMapper;
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
 *
 * @Date: 2019/6/13 修改
 * @Time: 14:42
 */


@Service
public class TaskService {
	@Autowired
    TaskMapper taskMapper;


	///Adding and querying data in Bean_Task database         2016.6.18
	public void add_Task(Task task) {
		taskMapper.add_Task(task);
	}

	///2019-11-6 Query all contents of task according to taskId
	public Task Sel_Task(Integer taskId) {
		return taskMapper.Sel_Task(taskId);
	}


	public void del_Task(Integer taskId) {
		taskMapper.del_Task(taskId);
	}


	////2019.7.2 Modification: Modified from a single result display to multiple result displays
	public List<Task> SelInfo(String taskName ) {
		return taskMapper.SelInfo(taskName);
	}

	//2019.7.6 Modification: Return the last ten data of the database at one time
	public List<Task> getTen() {
		return taskMapper.getTen();
	}
    //2021.1.11 Modification: Return all data in the database at once
    public List<Task> getAll() {
        return taskMapper.getAll();
    }
	////2021.1.11 Modification: Return all data in the database at once


	////2019.8.12 Modification: Return the last ten data of the database at one time
    public List<Task> getNewTen(Integer mintaskId) {
        return taskMapper.getNewTen(mintaskId);
    }
	//2019.9.11 Return a list of tasks according to the taskId of the task
	public List<Task> SelTaskFromKind(Integer taskKind) {
		return taskMapper.SelTaskFromKind(taskKind);
	}


	//2019.7.18  Function: Query all tasks published by a userId, input parameter: userId, return result: List<task>
	public List<Task> SelUserId(Integer userId ) {
		return taskMapper.SelUserId(userId);
	}



//	public Task check_Task(Integer taskId) {
//		return taskMapper.check_Task(taskId);
//	}
	///query1&0
	public Task check_Task(Task task) {		return taskMapper.checkTask(task);	}

	//20210516   The total number of people remaining in the task minus 1
    public void updateTaskTotalNum(Task task) {
		taskMapper.updateTaskTotalNum(task);
    }
	//20210516  Find the entire information of the task according to taskId
	public Task SelTaskFromTaskId(Integer taskId) {
		return taskMapper.SelTaskFromTaskId(taskId);
	}
}
