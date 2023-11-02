package helloservlet.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import helloservlet.service.LoginService;

@WebServlet(name="loginController",urlPatterns = {"/login"})
public class LoginController extends HttpServlet{
	
	private LoginService loginService = new LoginService();
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = ""; 
		String password = "";
		String remember = ""; 
		// lấy danh sách cookie từ req
		Cookie[] listCookie = req.getCookies();
		
		for(Cookie cookie : listCookie) {
			if(cookie.getName().equalsIgnoreCase("emailCKName")) {
				email = cookie.getValue();
			}
			if(cookie.getName().equalsIgnoreCase("passwordCKName")) {
				password = cookie.getValue();
			}
			if(cookie.getName().equalsIgnoreCase("checkboxCKName")) {
				remember = cookie.getValue();
			}
		}
		
		req.setAttribute("ReqAttributeEmail", email);
		req.setAttribute("ReqAttributePassword", password);
		req.setAttribute("ReqAttributeRemember", remember);
		
		req.getRequestDispatcher("login.jsp").forward(req,resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doPost(req, resp);
		String email = req.getParameter("email01"); //("email1" là name của <input type='email'> trong login.html)
		String password = req.getParameter("password01");//("password1" là name của <input type='password'> trong login.html)
		String isSaveLoginInfo = req.getParameter("remember01");
		
		boolean isSuccess = loginService.checkLogin(email, password, isSaveLoginInfo, resp);
		System.out.println("Kiem tra login "+isSuccess);
		
		if(isSuccess) {
			resp.sendRedirect(req.getContextPath()+"/home");
		}else {
			req.getRequestDispatcher("login.jsp").forward(req,resp);
		}
		
	}
}
