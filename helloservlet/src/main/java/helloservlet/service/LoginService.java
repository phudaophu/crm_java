package helloservlet.service;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import helloservlet.entity.UserEntity;
import helloservlet.repository.UserRepository;


public class LoginService {
	// tạo đối tượng của class UserRepository
	private UserRepository userRepository = new UserRepository();
	
	public boolean checkLogin(String email, String password, String remember, HttpServletResponse resp) {
		// Tạo list để hứng giá trị trả về của method: findByEmailAndPassword
		List<UserEntity> list = userRepository.findByEmailAndPassword(email, password);
		
		boolean isLoginSuccess = list.size()>0;
		
		// Tạo 3 cookies với tên cookie và value = null (chưa set giá trị cookie)
		Cookie cookieEmail = new Cookie("emailCKName",null);
		Cookie cookiePassword = new Cookie("passwordCKName",null);
		Cookie cookieRemember = new Cookie("checkboxCKName",null);
		
		if(isLoginSuccess && remember !=null){
			System.out.println("Lưu cookie");
			cookieEmail.setValue(email);
			cookiePassword.setValue(password);
			cookieRemember.setValue("checked");
			
			cookieEmail.setMaxAge(60*60);
			cookiePassword.setMaxAge(60*60);
			cookieRemember.setMaxAge(60*60);
			

		}else if (isLoginSuccess && remember ==null) {
			System.out.println("Lưu cookie tạm 10p");
			cookieEmail.setValue(email);
			cookiePassword.setValue(password);
			
			cookieEmail.setMaxAge(60*10);
			cookiePassword.setMaxAge(60*10);
			cookieRemember.setMaxAge(0);
			

		}else {
			System.out.println("Xóa cookie");
			
			cookieEmail.setMaxAge(0);
			cookiePassword.setMaxAge(0);
			cookieRemember.setMaxAge(0);

		}
		resp.addCookie(cookieEmail);
		resp.addCookie(cookiePassword);
		resp.addCookie(cookieRemember);

		
		return isLoginSuccess;
	}
	
	
	
	
}
