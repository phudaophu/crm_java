package helloservlet.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helloservlet.entity.JobEntity;
import helloservlet.entity.StatusEntity;
import helloservlet.entity.UserEntity;
import helloservlet.service.ProfileService;

@WebServlet(name="profileController", urlPatterns = {"/profile","/profile-edit"})
public class ProfileController extends HttpServlet{
	
	private ProfileService profileService = new ProfileService();
	int tempTaskId = -1;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doGet(req, resp);
		// Display fullname 

		String path = req.getServletPath();
		if (path.equals("/profile")) {
			doGetProfile(req,resp);
		}else if (path.equals("/profile-edit")) {
			doGetProfileEdit(req,resp);
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doPost(req, resp);
		String path = req.getServletPath();
		if (path.equals("/profile")) {
			
		}else if (path.equals("/profile-edit")) {
			doPostProfileEdit(req,resp);
		}
	}
	
	/*
	 * protected void getLoginUser(HttpServletRequest req, HttpServletResponse resp)
	 * throws ServletException, IOException { //
	 * 
	 * 
	 * }
	 */
	
	protected void doGetProfileEdit (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Create selecting option for job and user
		List<JobEntity> jobList = profileService.findAllJobs();
		List<UserEntity> userList = profileService.findAllUsers();
		List<StatusEntity> statusList = profileService.findAllStatus();
		
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
				
		req.getRequestDispatcher("profile-edit.jsp").forward(req, resp);
	}

	protected void doPostProfileEdit (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
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
		
		boolean isUpdated = profileService.updateById(jobId, taskName, userId, startDate, endDate, statusId, tempTaskId);
		System.out.println("Update Task: "+isUpdated);
		
		resp.sendRedirect(req.getContextPath()+"/profile");
	}
	
	protected void doGetProfile (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = ""; 
		String password = "";
		Cookie[] listCookie = req.getCookies();
		
		for(Cookie cookie : listCookie) {
			if(cookie.getName().equalsIgnoreCase("emailCKName")) {
				email = cookie.getValue();
			}
			if(cookie.getName().equalsIgnoreCase("passwordCKName")) {
				password = cookie.getValue();
			}
		}
		
		List<UserEntity> userList =profileService.findTaskByEmailAndPassword(email, password);
		UserEntity user = userList.get(0);
		
		int[] percentListExchange = profileService.calculateTaskPercent(user.getId());
		
		req.setAttribute("reqAttributeFullName", user.getFullname());
		req.setAttribute("reqAttributeEmail", user.getEmail());
		req.setAttribute("reqAttributeUserId", user.getId());
		
		req.setAttribute("reqAttributeTaskList", user.getTaskList());
		req.setAttribute("reqAttributePercentageList", percentListExchange);
		
		req.getRequestDispatcher("profile.jsp").forward(req, resp);

	}
}
