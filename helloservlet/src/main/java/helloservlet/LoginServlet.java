//package helloservlet;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import helloservlet.config.MysqlConfig;
//import helloservlet.entity.UserEntity;
//
//@WebServlet(name="LoginServlet",urlPatterns= {"/login"})
//public class LoginServlet extends HttpServlet {
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		// SESSION được lưu trữ trên thanh RAM của server, và tồn tại mãi mãi nếu ko set thời gian tồn tại. Mỗi client truy cập vào server sẽ dc cấp 1 session Id và 1 cookie được tạo ra để lưu lại session Id trên máy client
//		
//		// Khi vào url: /helloservlet thì session {"cycbersoft":"Hello Session"} được khởi tạo
//		// Tạo Object session, dùng method getAttribute để hứng lấy giá trị từ session có name: cycbersoft
////		HttpSession session = req.getSession();
////		String cycberSessionValue = (String) session.getAttribute("cycbersoft");
////		System.out.println("Session: "+cycberSessionValue);
////		// 
////		
////		
////		// Tạo ra đối tượng cookie từ class Cookie.Http
////		Cookie cookie = new Cookie("username01", "nguyenvana");
////		// Set thời gian tồn tại của Cookie
////		cookie.setMaxAge(60*60*5);
////		Cookie cookiePassword = new Cookie("password01", "123456");
////		
////		
////		// Add cookie và cookiePassword vào resp và trả về client, sau đó 2 cookie này được lưu trữ tại ổ đĩa máy tính của client
////		resp.addCookie(cookie);
////		resp.addCookie(cookiePassword);
////		
////		// Lấy danh sách cookie từ request của người dùng 
////		Cookie[] listCookie = req.getCookies();
////		// Duyệt qua từng cookie bên trong list 
////		for (Cookie cookieInList : listCookie) {
////			// kiểm tra tên cookie có phải là username01 hay không
////			if (cookieInList.getName().equals("username01")) {
////				// Lấy giá trị của cookie username01
////				System.out.println("Giá trị của username01 là: "+cookieInList.getValue());
////			}
////			if(cookieInList.getName().equals("password01")) {
////				// Lấy giá trị của cookie password
////				System.out.println("Giá trị của password01 là: "+cookieInList.getValue());
////			}
////		}
//		
//		
//	
//		// AutoFill in login page
//		// Tạo 2 biến String để hứng giá trị từ danh sách Cookie đọc từ trình duyệt 
//		String email="";
//		String password="";
//		String checkbox="";
//		
//		// Lấy danh sách cookie từ request của người dùng 
//		Cookie[] listCookie = req.getCookies();
//		// Duyệt qua từng cookie bên trong list 
//		for (Cookie cookieInList : listCookie) {
//			// kiểm tra tên cookie có phải là emailCKName hay không
//			if (cookieInList.getName().equals("emailCKName")) {
//				email = cookieInList.getValue();
//			}
//			// kiểm tra tên cookie có phải là passwordCKName hay không
//			if(cookieInList.getName().equals("passwordCKName")) {
//				// Lấy giá trị của cookie password
//				password = cookieInList.getValue();
//			}
//			if(cookieInList.getName().equals("checkboxCKName")) {
//				// Lấy giá trị của cookie password
//				checkbox = cookieInList.getValue();
//			}
//		}
//		// Set Attribute({"emailReqAttribute":email,"passwordReqAttribute":password}) in request để cho các resources như jsp vào lấy. The request scope end when the associated reponse is finished
//		req.setAttribute("emailReqAttribute",email);
//		req.setAttribute("passwordReqAttribute",password);
//		req.setAttribute("checkboxReqAttribute",checkbox);
//		
//		// hiển thị nội dung file html khi người dùng gọi link/login
//		req.getRequestDispatcher("login.jsp").forward(req,resp);
//	}
//	
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		// Bước 1: Nhận tham số từ client gửi về thông qua trang login
//		String email = req.getParameter("email1"); //("email1" là name của <input type='email'> trong login.html)
//		String password = req.getParameter("password1");//("password1" là name của <input type='password'> trong login.html)
//		String isSaveLoginInfo = req.getParameter("remember");
//
//		
//		// Bước 2: Chuẩn bị câu Query 
////		String query = "SELECT * \r\n"
////				+ "FROM users u \r\n"
////				+ "where u.email ='"+email+"' AND u.password ='"+password+"'";
//		String query = "SELECT * FROM users u where u.email =? AND u.password =?";
//		
//		// Bước 3: Mở kết nối csdl và truyền câu query cho JDBC thông qua PreparedStatement
//		
//		//PreparedStatement statement = MysqlConfig.getConnection();
//		Connection connection =  MysqlConfig.getConnection();
//		
//
//		 try {
//		// Bước 4: Truyền câu query vào CSDL vừa mở kết nối thông qua PreparedStatement
//			PreparedStatement preparedStatement = connection.prepareStatement(query);
//			preparedStatement.setString(1, email);
//			preparedStatement.setString(2, password);
//			
//		// Bước 5: Thông báo cho csdl biết và thực thi câu query
//		// Các cách thực thi: 
//		// executeQuery : dành cho câu truy vấn là SELECT => luôn trả ra ResultSet
//		// executeUpdate : Tất cả câu truy vấn còn lại ngoài SELECT ví dụ: INSERT, UPDATE, DELETE, ... 
//			ResultSet resultSet = preparedStatement.executeQuery();
//			
//		// Tạo list UserEntity để lưu trữ từng dòng dữ liệu query được	
//			List<UserEntity> listUser = new ArrayList <UserEntity>();
//			
//		// Bước 6: Duyệt từng dòng dữ liệu query đươc và gán vào trong List<UserEntity>
//			while(resultSet.next()) {
//				UserEntity entity = new UserEntity();
//				entity.setId(resultSet.getInt("id")); // resultSet.getInt("id"): lấy giá trị cột Id. setId: gán vào thuộc tính Id của entity thông qua setter
//				entity.setFullname(resultSet.getString("fullname"));
//				listUser.add(entity);
//			}
//			if(listUser.size()>0) {
//				System.out.println("Đăng nhập thành công");
//				
//				// Tạo 3 cookies với tên cookie và value = null (chưa set giá trị cookie)
//				Cookie ckEmail = new Cookie("emailCKName",null);
//				Cookie ckPassword = new Cookie("passwordCKName",null);
//				Cookie ckCheckBox = new Cookie("checkboxCKName",null);
//				
//				// Kiểm tra isSaveLoginInfo để xem người dùng có click vào lưu tài khoản hay ko
//				if (isSaveLoginInfo != null){
////					Cookie ckEmail = new Cookie("emailCKName", email);
////					Cookie ckPassword = new Cookie("passwordCKName",password);
//					
//					// set value của từng cookie với giá trị tương ứng: email, password,..
//					ckEmail.setValue(email);
//					ckPassword.setValue(password);
//					ckCheckBox.setValue("checked");
//					// Set khoảng thời gian sống cho cookies. Sau thời gian này, cookie sẽ biến mất
//					ckEmail.setMaxAge(60*5);
//					ckPassword.setMaxAge(60*5);
//					ckCheckBox.setMaxAge(60*5);
//					// Cuối cùng là đưa cookies vào response và trả về cho client
//					resp.addCookie(ckEmail);
//					resp.addCookie(ckPassword);
//					resp.addCookie(ckCheckBox);
//				}
//				else {
//					// Xóa cookie, set cookie.MaxAge = 0 và sau đó add cookie vào resp
//					ckEmail.setMaxAge(0);
//					ckPassword.setMaxAge(0);
//					ckCheckBox.setMaxAge(0);
//					
//					resp.addCookie(ckEmail);
//					resp.addCookie(ckPassword);
//					resp.addCookie(ckCheckBox);
//				}
//
//			}
//			else {
//				System.out.println("Đăng nhập thất bại");
//			}
//			
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		 
//		System.out.println("email: "+email+" - "+"password: "+password);
//		req.getRequestDispatcher("login.jsp").forward(req,resp);
//		
//	}
//	
//	
//}
