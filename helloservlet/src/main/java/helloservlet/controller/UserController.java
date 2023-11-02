package helloservlet.controller;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helloservlet.entity.RoleEntity;
import helloservlet.entity.UserEntity;
import helloservlet.service.UserService;

@WebServlet(name="userController",urlPatterns = {"/user-add","/user","/user-delete","/user-update"})
public class UserController extends HttpServlet{
	
	UserService userService = new UserService();
	int tempUserId = -1;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doGet(req, resp);
		String path = req.getServletPath();
		System.out.println("Kiểm tra Do Get: "+path);
		if (path.equals("/user")){
			doGetUser(req,resp);
		}else if(path.equals("/user-add")){
			doGetUserAdd(req,resp);
		}else if(path.equals("/user-delete")){
			doGetUserDelete(req,resp);
		}else if(path.equals("/user-update")){
			doGetUserUpdate(req,resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doPost(req, resp);
		String path = req.getServletPath();
		System.out.println("Kiểm tra Do Post: "+path);
		if (path.equals("/user-add")){
			doPostUserAdd(req,resp);
		}else if(path.equals("/user-update")){
			doPostUserUpdate(req,resp);
		}
	}
	
	private void doGetUserAdd (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<RoleEntity> roleList = userService.findAllRoles();
		req.setAttribute("reqAttributeRoleList", roleList);
		
		req.getRequestDispatcher("user-add.jsp").forward(req, resp);
		
	}
	private void doPostUserAdd (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Load all roles in select section
		List<RoleEntity> roleList = userService.findAllRoles();
		req.setAttribute("reqAttributeRoleList", roleList);
		// ghi đè tên của encoding đang sử dụng thành encoding mà mình muốn, dùng trước khi get parameter   
		req.setCharacterEncoding("UTF-8");
		
		String fullname = req.getParameter("fullname01");
		String email = req.getParameter("email01");
		String password = req.getParameter("password01");
		String phonenumber = req.getParameter("phonenumber01");
		int roleId = Integer.parseInt( req.getParameter("rolename01"));
		
//		System.out.println(fullname+" "+email+" "+password+" "+phonenumber+" "+roleId);
//		System.out.println("Nguyễn văn tèo");
		boolean isInsertSuccess = userService.insert(email, password, fullname, phonenumber, roleId);
		
		System.out.println("Is User Inserting Success: "+isInsertSuccess);
		req.getRequestDispatcher("user-add.jsp").forward(req, resp);
		
	}
	private void doGetUser (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<UserEntity> userList = userService.findAllUsers();
		
		System.out.println("Total number user: " + userList.size());
		
		List<String> firstNameList = userService.getFirstNameList();
		List<String>  lastNameList = userService.getLastNameList();
		
		req.setAttribute("reqAttributeUserList", userList);
		req.setAttribute("reqAttributeFirstNameList", firstNameList);
		req.setAttribute("reqAttributeLastNameList", lastNameList);

		req.getRequestDispatcher("user-table.jsp").forward(req, resp);
		
	}
	
	private void doGetUserDelete (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		int id = Integer.parseInt(req.getParameter("id"));
		
		boolean isDeleted = userService.deleteById(id);
		System.out.println("Is user deleted: "+isDeleted);
		resp.sendRedirect(req.getContextPath()+"/user");
	}
	
	private void doGetUserUpdate (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Load all roles in select section
		List<RoleEntity> roleList = userService.findAllRoles();
		req.setAttribute("reqAttributeRoleList", roleList);
		
		String fullname = req.getParameter("fullname");
		String email = req.getParameter("email");
		//String password = req.getParameter("pwd");
		String phonenumber = req.getParameter("phoneno");
		int roleId = Integer.parseInt(req.getParameter("roleid"));
		tempUserId = Integer.parseInt(req.getParameter("id"));
		
		req.setAttribute("reqAttrributeFullName", fullname);
		req.setAttribute("reqAttrributeEmail", email);
		//req.setAttribute("reqAttrributePassword", password);
		req.setAttribute("reqAttrributePhoneNumber", phonenumber);
		req.setAttribute("reqAttrributeRoleId", roleId);
		//req.setAttribute("reqAttributeId",id);
		
		
		req.getRequestDispatcher("user-update.jsp").forward(req, resp);
	}
	
	private void doPostUserUpdate (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Load all roles in select section
		List<RoleEntity> roleList = userService.findAllRoles();
		req.setAttribute("reqAttributeRoleList", roleList);
		
		// ghi đè tên của encoding đang sử dụng thành encoding mà mình muốn, dùng trước khi get parameter   
		req.setCharacterEncoding("UTF-8");
		
		
		String email = req.getParameter("email02");
		String newPassword = req.getParameter("newpassword02");
		String phonenumber = req.getParameter("phonenumber02");
		int roleId = Integer.parseInt( req.getParameter("rolename02"));
		
		//System.out.println(fullname+"-"+email+"-"+newPassword+"-"+phonenumber+"-"+roleId+"-"+tempUserId);
		boolean isUpdatedSuccess = false;
		
		
		if(!newPassword.equals("")) {
			isUpdatedSuccess = userService.updateById(email, newPassword, phonenumber, roleId, tempUserId);
			
		}else {
			
			isUpdatedSuccess = userService.updateInfoById(email, phonenumber, roleId, tempUserId);
		}
		
		System.out.println("Update User: "+ isUpdatedSuccess);
		resp.sendRedirect(req.getContextPath()+"/user");
	}
	
}
