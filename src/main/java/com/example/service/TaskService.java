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


///Bean_Task数据库中数据的添加和查询         2016.6.18	
	public void add_Task(Task task) {
		taskMapper.add_Task(task);
	}
	
	///2019-11-6 根据taskId查询task的所有内容
	public Task Sel_Task(Integer taskId) {
		return taskMapper.Sel_Task(taskId);
	}


	public void del_Task(Integer taskId) {
		taskMapper.del_Task(taskId);
	}


	////2019.7.2 修改：从单个的结果展示修改为多个结果展示
	public List<Task> SelInfo(String taskName ) {
		return taskMapper.SelInfo(taskName);
	}

	////2019.7.6 修改：一次性返回数据库最后面的十条数据
	public List<Task> getTen() {
		return taskMapper.getTen();
	}
    ////2021.1.11 修改：一次性返回数据库所有数据
    public List<Task> getAll() {
        return taskMapper.getAll();
    }
    ////2021.1.11 修改：一次性返回数据库所有数据


	////2019.8.12 修改：一次性返回数据库次后面的十条数据
    public List<Task> getNewTen(Integer mintaskId) {
        return taskMapper.getNewTen(mintaskId);
    }
	///2019.9.11 根据任务的taskId返回任务的List
	public List<Task> SelTaskFromKind(Integer taskKind) {
		return taskMapper.SelTaskFromKind(taskKind);
	}


	////2019.7.18  功能：查询某userId所发布的所有的任务  ，输入的参数：userId，返回的结果：List<task>
	public List<Task> SelUserId(Integer userId ) {
		return taskMapper.SelUserId(userId);
	}



//	public Task check_Task(Integer taskId) {
//		return taskMapper.check_Task(taskId);
//	}
	///查询1&0
	public Task check_Task(Task task) {		return taskMapper.checkTask(task);	}

	//20210516   任务剩余的总人数-1
    public void updateTaskTotalNum(Task task) {
		taskMapper.updateTaskTotalNum(task);
    }
	//20210516  根据taskId查找该条task的整个信息
	public Task SelTaskFromTaskId(Integer taskId) {
		return taskMapper.SelTaskFromTaskId(taskId);
	}
}
