package helloservlet.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import helloservlet.config.MysqlConfig;
import helloservlet.entity.UserEntity;
// Chứa tất cả các câu query liên quan tới bảng User

public class UserRepository {
	
	/*
	 * 	Repository có quy tắc đặt tên hàm.
	 * 	Nếu câu query có liên quan đến:
	 * 		select: đại diện cho chữ find 
	 * 		where: đại diện bởi chữ by
	 * 
	 * */
	
	public List<UserEntity> findByJobId(int jobId){
		
		String query = "SELECT * FROM users WHERE id in (SELECT user_id FROM tasks WHERE job_id = ?) ORDER BY fullname;";
		
		Connection connection =  MysqlConfig.getConnection();

		List<UserEntity> userList = new ArrayList <UserEntity>();

		 try {

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, jobId);
			 
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				UserEntity entity = new UserEntity();
				entity.setId(resultSet.getInt("id")); // resultSet.getInt("id"): lấy giá trị cột Id. setId: gán vào thuộc tính Id của entity thông qua setter
				entity.setFullname(resultSet.getString("fullname"));
				userList.add(entity);
			}
			connection.close();
			
		 }catch(Exception e) {
			 System.out.println("Lỗi findByJobId "+e.getLocalizedMessage());
		 }
		
		return userList;
		
	}
	
	
	
	public UserEntity findById(int id) {
		UserEntity userEntity = new UserEntity();
		String query = "SELECT * FROM users WHERE id=? ;";
		
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement preparedStatement= connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				userEntity.setId(resultSet.getInt("id")); // resultSet.getInt("id"): lấy giá trị cột Id. setId: gán vào thuộc tính Id của entity thông qua setter
				userEntity.setFullname(resultSet.getString("fullname"));
				userEntity.setEmail(resultSet.getString("email"));
				userEntity.setPhonenumber(resultSet.getString("phonenumber"));
				userEntity.setIdRole(resultSet.getInt("role_id"));
			}
			connection.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Lỗi findById user: "+e.getLocalizedMessage());
		}
		
		return userEntity;
	}
	
	
	public int updateInfoById(String email, String phonenumber, int idRole, int id) {
		int count=0;
		String query = "UPDATE users SET email=?, phonenumber = ?, role_id = ? Where id = ?";
		Connection connection = MysqlConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1,email);
			preparedStatement.setString(2,phonenumber);
			preparedStatement.setInt(3,idRole);
			preparedStatement.setInt(4,id);
			count = preparedStatement.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Lỗi updatePhoneNoIdRoleById user: "+e.getLocalizedMessage());
		}
		
		return count;
	}
	
	public int updateById(String email, String password, String phonenumber, int idRole, int id) {
		int count=0;
		String query = "UPDATE users SET email = ?, password = ?, phonenumber = ?, role_id = ? Where id = ?";
		Connection connection = MysqlConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1,email);
			preparedStatement.setString(2,password);
			preparedStatement.setString(3,phonenumber);
			
			preparedStatement.setInt(4,idRole);
			preparedStatement.setInt(5,id);
			count = preparedStatement.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Lỗi updateById user: "+e.getLocalizedMessage());
		}
		
		return count;
	}
	
	public int deleteById(int id) {
		int count=0;
		String query = "DELETE FROM users WHERE id= ?;";
		Connection connection = MysqlConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement= connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			count = preparedStatement.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Lỗi delete user: "+e.getLocalizedMessage());
		}
		return count;
	}
	
	
	public List<UserEntity> findAllUsers(){
		
		// Bước 2: Chuẩn bị câu Query 
//		String query = "SELECT * \r\n"
//				+ "FROM users u \r\n"
//				+ "where u.email ='"+email+"' AND u.password ='"+password+"'";
		String query = "SELECT * FROM users ORDER BY fullname";
		
		// Bước 3: Mở kết nối csdl và truyền câu query cho JDBC thông qua PreparedStatement
		
		//PreparedStatement statement = MysqlConfig.getConnection();
		Connection connection =  MysqlConfig.getConnection();
		// Tạo list UserEntity để lưu trữ từng dòng dữ liệu query được	
		List<UserEntity> listUser = new ArrayList <UserEntity>();

		 try {
		// Bước 4: Truyền câu query vào CSDL vừa mở kết nối thông qua PreparedStatement
			PreparedStatement preparedStatement = connection.prepareStatement(query);

			
		// Bước 5: Thông báo cho csdl biết và thực thi câu query
		// Các cách thực thi: 
		// executeQuery : dành cho câu truy vấn là SELECT => luôn trả ra ResultSet
		// executeUpdate : Tất cả câu truy vấn còn lại ngoài SELECT ví dụ: INSERT, UPDATE, DELETE, ... 
			ResultSet resultSet = preparedStatement.executeQuery();
			
		// Bước 6: Duyệt từng dòng dữ liệu query đươc và gán vào trong List<UserEntity>
			while(resultSet.next()) {
				UserEntity userEntity = new UserEntity();
				userEntity.setId(resultSet.getInt("id")); // resultSet.getInt("id"): lấy giá trị cột Id. setId: gán vào thuộc tính Id của entity thông qua setter
				userEntity.setFullname(resultSet.getString("fullname"));
				userEntity.setEmail(resultSet.getString("email"));
				userEntity.setPhonenumber(resultSet.getString("phonenumber"));
				userEntity.setIdRole(resultSet.getInt("role_id"));
				listUser.add(userEntity);
			}
			
			connection.close();
			
		 }catch(Exception e) {
			 System.out.println("Lỗi findAllUser "+e.getLocalizedMessage());
		 }
		
		return listUser;
	}
	
	public int insert(String email, String password, String fullname, String phonenumber, int idRole) {
		int count=0;
		String query = "INSERT INTO crm_app.users ( email, password, fullname, phonenumber, role_id) VALUES(?,?,?,?,?);";
		Connection connection = MysqlConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement= connection.prepareStatement(query);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, fullname);
			preparedStatement.setString(4, phonenumber);
			preparedStatement.setInt(5, idRole);
			
			count = preparedStatement.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Lỗi insert user: "+e.getMessage());
		}
		return count;
	}
	
	public List<UserEntity> findByEmailAndPassword(String email, String password){
		
		// Bước 2: Chuẩn bị câu Query 
//		String query = "SELECT * \r\n"
//				+ "FROM users u \r\n"
//				+ "where u.email ='"+email+"' AND u.password ='"+password+"'";
		String query = "SELECT * FROM users u where u.email =? AND u.password =? ORDER BY id LIMIT 1;";
		
		// Bước 3: Mở kết nối csdl và truyền câu query cho JDBC thông qua PreparedStatement
		
		//PreparedStatement statement = MysqlConfig.getConnection();
		Connection connection =  MysqlConfig.getConnection();
		// Tạo list UserEntity để lưu trữ từng dòng dữ liệu query được	
		List<UserEntity> listUser = new ArrayList <UserEntity>();

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
			

			
		// Bước 6: Duyệt từng dòng dữ liệu query đươc và gán vào trong List<UserEntity>
			while(resultSet.next()) {
				UserEntity entity = new UserEntity();
				entity.setId(resultSet.getInt("id")); // resultSet.getInt("id"): lấy giá trị cột Id. setId: gán vào thuộc tính Id của entity thông qua setter
				entity.setFullname(resultSet.getString("fullname"));
				entity.setEmail(resultSet.getString("email"));
				listUser.add(entity);
			}
		
			connection.close();
			
		 }catch(Exception e) {
			 System.out.println("Lỗi findByEmailAndPassword "+e.getLocalizedMessage());
		 }
		
		return listUser;
	}

	
}
