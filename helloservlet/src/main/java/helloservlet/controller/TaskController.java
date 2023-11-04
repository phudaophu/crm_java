package helloservlet.controller;

import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import helloservlet.entity.JobEntity;
import helloservlet.entity.StatusEntity;
import helloservlet.entity.TaskEntity;
import helloservlet.entity.UserEntity;
import helloservlet.service.TaskService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="taskController",urlPatterns= {"/task","/task-add","/task-update","/task-delete"})

public class TaskController extends HttpServlet{
	private TaskService taskService = new TaskService();
	int tempTaskId = -1; 
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String path = req.getServletPath();
		if(path.equals("/task")) {
			doGetTask(req,resp);
		}else if (path.equals("/task-add")) {
			doGetTaskAdd(req,resp);
		}else if (path.equals("/task-delete")) {
			doGetTaskDelete(req,resp);
		}else if (path.equals("/task-update")) {
			doGetTaskUpdate(req,resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String path = req.getServletPath();
		if(path.equals("/task")) {
			
		}else if (path.equals("/task-add")) {
			doPostTaskAdd(req,resp);
		}else if (path.equals("/task-update")) {
			doPostTaskUpdate(req,resp);
		}
	}
	
	
	protected void doGetTaskUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Create selecting option for job and user
		List<JobEntity> jobList = taskService.findAllJobs();
		List<UserEntity> userList = taskService.findAllUsers();
		List<StatusEntity> statusList = taskService.findAllStatus();
		
		req.setAttribute("reqAttributeJobList", jobList);
		req.setAttribute("reqAttributeUserList", userList);
		req.setAttribute("reqAttributeStatusList", statusList);
		
		
		String name = req.getParameter("name");
		tempTaskId  = Integer.parseInt(req.getParameter("id"));
		
		String startDate = req.getParameter("startdate");
		String endDate = req.getParameter("enddate");
		int userId = Integer.parseInt(req.getParameter("userid"));
		int jobId  = Integer.parseInt(req.getParameter("jobid"));
		int statusId  = Integer.parseInt(req.getParameter("statusid"));
		
		req.setAttribute("reqAttributeName", name);
		req.setAttribute("reqAttributeStartDate", startDate);
		req.setAttribute("reqAttributeEndDate", endDate);
		req.setAttribute("reqAttributeUserId", userId);
		req.setAttribute("reqAttributeJobId", jobId);
		req.setAttribute("reqAttributeStatusId", statusId);
		
		req.getRequestDispatcher("task-update.jsp").forward(req, resp);
	}
	
	protected void doPostTaskUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// job_id02
		// ghi đè tên của encoding đang sử dụng thành encoding mà mình muốn, dùng trước khi get parameter   
		req.setCharacterEncoding("UTF-8");
		
		int jobId = Integer.parseInt(req.getParameter("job_id02"));
		String taskName = req.getParameter("taskname02");
		int userId = Integer.parseInt(req.getParameter("user_id02"));
		int statusId = Integer.parseInt(req.getParameter("status_id02"));
		
		String startDateString = req.getParameter("startdate02");
		String endDateString = req.getParameter("enddate02");
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		Date startDate = null;
		Date endDate=null;
		
		try {
			startDate = dateFormat.parse(startDateString);
		}catch(Exception e) {}
		
		try {
			endDate   = dateFormat.parse(endDateString);
		}catch(Exception e) {}
		
		boolean isUpdated = taskService.updateById(jobId, taskName, userId, startDate, endDate, statusId, tempTaskId);
		System.out.println("Update Task: "+isUpdated);
		//req.getRequestDispatcher("task-update.jsp").forward(req, resp);
		resp.sendRedirect(req.getContextPath()+"/task");
	}

	
	protected void doGetTaskDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int id = Integer.parseInt(req.getParameter("id")); 
		
		boolean isDeleted =  taskService.deleteById(id);
		System.out.println("Delete Task: "+isDeleted);
		resp.sendRedirect(req.getContextPath()+"/task");
	}
	
	protected void doGetTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<TaskEntity> taskList = taskService.findAllTasks();
		
		req.setAttribute("reqAttributeTaskList", taskList);
		
		System.out.println("Number of task: "+taskList.size());
		req.getRequestDispatcher("task.jsp").forward(req, resp);
	}
	
	protected void doGetTaskAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// load jobs and users
		List<JobEntity> jobList = taskService.findAllJobs();
		List<UserEntity> userList = taskService.findAllUsers();
		
		req.setAttribute("reqAttributeJobList", jobList);
		req.setAttribute("reqAttributeUserList", userList);
		
		System.out.println("Job List: "+jobList.size());
		System.out.println("User List: "+userList.size());
		
		req.getRequestDispatcher("task-add.jsp").forward(req, resp);
	}
	protected void doPostTaskAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// load jobs and users
		List<JobEntity> jobList = taskService.findAllJobs();
		List<UserEntity> userList = taskService.findAllUsers();
		
		req.setAttribute("reqAttributeJobList", jobList);
		req.setAttribute("reqAttributeUserList", userList);
		
		
		// ghi đè tên của encoding đang sử dụng thành encoding mà mình muốn, dùng trước khi get parameter   
		req.setCharacterEncoding("UTF-8");
		
		int jobId = Integer.parseInt(req.getParameter("job_id01"));
		String taskName = req.getParameter("taskname01");
		int userId = Integer.parseInt(req.getParameter("user_id01"));
		// initialize job so its status is 1;
		int statusId =1 ;
		
		String startDateString = req.getParameter("startdate01");
		String endDateString = req.getParameter("enddate01");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		Date startDate = null;
		Date endDate=null;
		
		try {
			startDate = dateFormat.parse(startDateString);
		}catch(Exception e) {}
		
		try {
			endDate   = dateFormat.parse(endDateString);
		}catch(Exception e) {}
		
		
		boolean isInserted = taskService.insert(jobId,taskName ,userId ,startDate ,endDate , statusId);
		System.out.println("Insert task: "+isInserted);
		
		req.getRequestDispatcher("task-add.jsp").forward(req, resp);
	}
	
}
