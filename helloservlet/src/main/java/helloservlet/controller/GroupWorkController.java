package helloservlet.controller;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helloservlet.entity.JobEntity;
import helloservlet.service.GroupWorkService;

@WebServlet(name="groupWorkController", urlPatterns = {"/groupwork","/groupwork-add","/groupwork-delete","/groupwork-update"})
public class GroupWorkController extends HttpServlet{
	GroupWorkService groupWorkService = new GroupWorkService();
	int temp_jobId = -1;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String path = req.getServletPath();
		if(path.equals("/groupwork")) {
			doGetGroupWork(req,resp);
		}else if(path.equals("/groupwork-add")) {
			doGetGroupWorkAdd(req,resp);
		}else if(path.equals("/groupwork-delete")) {
			doGetGroupWorkDelete(req,resp);
		}else if (path.equals("/groupwork-update")) {
			doGetGroupWorkUpdate(req,resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String path = req.getServletPath();
		if(path.equals("/groupwork")) {
	
		}else if(path.equals("/groupwork-add")) {
			doPostGroupWorkAdd(req,resp);
		}else if (path.equals("/groupwork-update")) {
			doPostGroupWorkUpdate(req,resp);
		}
	}
	
	protected void doGetGroupWork (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<JobEntity> jobList = groupWorkService.findAllJobs();
		
		req.setAttribute("reqAttributeJobList", jobList);
		
		req.getRequestDispatcher("groupwork.jsp").forward(req, resp);
	}
	
	protected void doGetGroupWorkAdd (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		
		req.getRequestDispatcher("groupwork-add.jsp").forward(req, resp);
	}
	protected void doPostGroupWorkAdd (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ghi đè tên của encoding đang sử dụng thành encoding mà mình muốn, dùng trước khi get parameter   
		req.setCharacterEncoding("UTF-8");
		
		String name = req.getParameter("jobname01");
		String startDateString = req.getParameter("startdate01");
		String endDateString = req.getParameter("enddate01");
		Date startDate = null;
		Date endDate = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		boolean isSuccess = false;
		
		try {
			startDate = dateFormat.parse(startDateString);
		}catch(ParseException e) {}
		
		try {
			endDate   = dateFormat.parse(endDateString);
		} catch (ParseException e) {}
		
		
		isSuccess = groupWorkService.insertJobs(name, startDate, endDate);
		System.out.println("Insert job: "+isSuccess);
		
		req.getRequestDispatcher("groupwork-add.jsp").forward(req, resp);
	}
	
	protected void doGetGroupWorkDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int id = Integer.parseInt(req.getParameter("id"));
		boolean isDeleted = groupWorkService.deleteById(id);
		System.out.println("Delete job: "+isDeleted);
		resp.sendRedirect(req.getContextPath()+"/groupwork");
	}
	
	protected void doGetGroupWorkUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		String name = req.getParameter("name");
		String startDate = req.getParameter("startdate");
		String endDate = req.getParameter("enddate");
		temp_jobId = Integer.parseInt(req.getParameter("id"));
		
		
		req.setAttribute("reqAttributeName", name);
		req.setAttribute("reqAttributeStartDate", startDate);
		req.setAttribute("reqAttributeEndDate", endDate);
		req.getRequestDispatcher("groupwork-update.jsp").forward(req, resp);
	}
	
	protected void doPostGroupWorkUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// ghi đè tên của encoding đang sử dụng thành encoding mà mình muốn, dùng trước khi get parameter   
		req.setCharacterEncoding("UTF-8");
		String name = req.getParameter("jobname02");
		String startDateString = req.getParameter("startdate02");
		String endDateString = req.getParameter("enddate02");
		Date startDate = null;
		Date endDate = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		boolean isUpdated = false;
		
		try {
			startDate = dateFormat.parse(startDateString);
			endDate   = dateFormat.parse(endDateString);
		}catch(Exception e) {
			//System.out.println("Lỗi parse string to date: "+e.getLocalizedMessage());
		}
		isUpdated = groupWorkService.updateJobs(name, startDate, endDate, temp_jobId);
		System.out.println("Update job: "+isUpdated);
		//req.getRequestDispatcher("groupwork-update.jsp").forward(req, resp);
		resp.sendRedirect(req.getContextPath()+"/groupwork");
	}
	
	
}
