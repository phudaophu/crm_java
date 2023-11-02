package helloservlet.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import helloservlet.config.MysqlConfig;
import helloservlet.entity.UserEntity;



@WebFilter(filterName = "authenFilter",urlPatterns = {"/role-add"})
public class AuthenticationFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("Filter activated");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		String email = "";
		String password ="";
		String contextPath = req.getContextPath();
		
		try {
			// Lấy danh sách cookies người dùng gửi lên thông qua request
			Cookie[] cookies = req.getCookies();
			for( Cookie cookie : cookies) {
				if (cookie.getName().equals("emailCKName")) {
					email = cookie.getValue();
				}
				if (cookie.getName().equals("passwordCKName")) {
					password = cookie.getValue();
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Lỗi"+e.getMessage());
			resp.sendRedirect(contextPath+"/login");
		}

		if(email.trim().length() > 0 && password.trim().length()>0) {
			// Đã đăng nhập rồi
			// Xác thực lại: Thông tin email và password đã lưu ở cookie xác thực lại ở database
			String query = "SELECT * FROM users u where u.email =? AND u.password =?";
			
			// Mở connection tới database 
			Connection connection =  MysqlConfig.getConnection();
			
			
			try {
				// Bước 4: Truyền câu query vào CSDL vừa mở kết nối thông qua PreparedStatement
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, email);
				preparedStatement.setString(2, password);
				
				// Bước 5: Thông báo cho csdl biết và thực thi câu query
				// Các cách thực thi: 
				// executeQuery : dành cho câu truy vấn là SELECT => luôn trả ra ResultSet
				// executeUpdate : Tất cả câu truy vấn còn lại ngoài SELECT ví dụ: INSERT, UPDATE, DELETE, ... 
				ResultSet resultSet = preparedStatement.executeQuery();
				List<UserEntity> userList = new ArrayList <UserEntity>();
				
				while(resultSet.next()) {
					UserEntity entity = new UserEntity();
					entity.setId(resultSet.getInt("id")); // lấy giá trị cột Id gán vào Id của entity thông qua setter
					entity.setFullname(resultSet.getString("fullname"));
					userList.add(entity);
				}
				
				if(userList.size()>0) {
					System.out.println("Xác thực thành công");
					// Cho phép đi vào Link mà người dùng đang gọi mà kích hoạt filter
					chain.doFilter(req,resp);
				}
				else {
					System.out.println("Xác thực không thành công");
					resp.sendRedirect(contextPath+"/login");
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resp.sendRedirect(contextPath+"/login");
			}

		}else {
			// Chưa đăng nhập 
			System.out.println("Chưa login, bị đẩy về trang login");
			resp.sendRedirect(contextPath+"/login");
		}

	}

}
