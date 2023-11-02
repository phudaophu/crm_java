package helloservlet.controller;

import java.io.IOException;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helloservlet.entity.RoleEntity;
import helloservlet.service.*;
import helloservlet.service.LoginService;



@WebServlet(name="roleServlet",urlPatterns= {"/role-add","/role","/role-delete","/role-update"})
public class RoleController extends HttpServlet {
	
	private RoleService roleService = new RoleService();
	int tempRoleId;
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doGet(req, resp);
		String path = req.getServletPath();
		System.out.println("Kiểm tra doGet: "+path);
		if(path.equals("/role-add")) {
			doGetRoleAdd(req,resp);
		}
		else if(path.equals("/role")) {
			doGetRole(req,resp);
		}
		else if(path.equals("/role-delete")) {
			doGetRoleDelete(req,resp);
		}
		else if(path.equals("/role-update")) {
			doGetRoleUpdate(req,resp);
		}
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String path = req.getServletPath();
		System.out.println("Kiểm tra doPost: "+path);
		if(path.equals("/role-add")) {
			doPostRoleAdd(req,resp);
		}
		else if(path.equals("/role-update")) {
			doPostRoleUpdate(req,resp);
		}
	}
	
	private void doGetRoleAdd (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.getRequestDispatcher("role-add.jsp").forward(req, resp);
	}
	
	private void doPostRoleAdd (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		// ghi đè tên của encoding đang sử dụng thành encoding mà mình muốn, dùng trước khi get parameter 
		req.setCharacterEncoding("UTF-8");
		
		String roleName = req.getParameter("role-name"); 
		String roleDesc = req.getParameter("role-desc");
		
		boolean isSuccess = roleService.insert(roleName, roleDesc);
		System.out.println("Insert "+ isSuccess);
		req.getRequestDispatcher("role-add.jsp").forward(req,resp);
	}
	
	private void doGetRole (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		List<RoleEntity> list = roleService.getAllRoles();
		req.setAttribute("listRole01", list);
		req.getRequestDispatcher("role-table.jsp").forward(req,resp);
	}
	
	private void doGetRoleDelete (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		int id = Integer.parseInt(req.getParameter("id"));
		roleService.delete(id);
		System.out.println("Kiểm tra xóa: "+roleService.delete(id));
		resp.sendRedirect( req.getContextPath() + "/role");
		
	}
	
	private void doGetRoleUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		String previousName = req.getParameter("name");
		String previousDesc = req.getParameter("desc");
		tempRoleId = Integer.parseInt(req.getParameter("id"));
		
		
		req.setAttribute("reqAttributePreviousRoleName", previousName);
		req.setAttribute("reqAttributePreviousRoleDesc",previousDesc);
		
		req.getRequestDispatcher("role-update.jsp").forward(req, resp);
	}
	
	private void doPostRoleUpdate (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		// ghi đè tên của encoding đang sử dụng thành encoding mà mình muốn, dùng trước khi get parameter   
		req.setCharacterEncoding("UTF-8");
		
		String newName = req.getParameter("roleNameUpdate");
		String newDesc = req.getParameter("roleDescUpdate");
		
		boolean isUpdate = roleService.update(newName, newDesc, tempRoleId);
		System.out.println("Kiểm tra update: "+isUpdate);
		resp.sendRedirect(req.getContextPath()+"/role");
		

	}
	
//	private void doPostRoleTable (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
//		
//	}
	
}
