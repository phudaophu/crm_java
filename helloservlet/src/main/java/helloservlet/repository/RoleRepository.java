package helloservlet.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import helloservlet.config.MysqlConfig;
import helloservlet.entity.RoleEntity;
import helloservlet.entity.UserEntity;

public class RoleRepository {

	public RoleEntity findRoleById (int id) {
		
		String query ="SELECT * FROM roles WHERE id=?;";
		RoleEntity roleEntity = new RoleEntity();
		
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				roleEntity.setId(resultSet.getInt("id"));
				roleEntity.setName(resultSet.getString("name"));
				roleEntity.setDesc(resultSet.getString("description"));
			}

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Lỗi findRoleById "+e.getLocalizedMessage());
		}

		return roleEntity;
		
	}
	
	public int updateById(String name,String desc,int id) {
		String query ="UPDATE roles SET name=?, description=? WHERE id=?;";
		int count=0;
		Connection connection = MysqlConfig.getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, desc);
			preparedStatement.setInt(3, id);
			
			count = preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Lỗi updateById roles "+e.getMessage());
		}
		
		return count;
	}
	
	
	public int deleteById(int id) {
		int count = 0;
		String query="DELETE FROM roles r WHERE r.id = ?";
		
		try {
			
			Connection connection = MysqlConfig.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1,id);
			count = preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Lỗi "+e.getMessage());
		}
		return count;
	}
	
	public List<RoleEntity> findAll(){
		
		List<RoleEntity> listRole = new ArrayList<RoleEntity>();
		String query = "SELECT * FROM roles";
		
		try {
			Connection connection = MysqlConfig.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				RoleEntity roleEntity = new RoleEntity();
				roleEntity.setId(resultSet.getInt("id"));
				roleEntity.setName(resultSet.getString("name"));
				roleEntity.setDesc(resultSet.getString("description"));
				listRole.add(roleEntity);
			}
			
			
		}catch(Exception e) {
			System.out.println("Lỗi findAll "+e.getMessage());
		}
		
		return listRole;
		
	}
	
	public int insert(String name, String description) {
		
		int count = 0;

		 try {
			    // Tạo câu query
					String query = "INSERT INTO crm_app.roles (name, description) VALUES(?,?);";
					Connection connection =  MysqlConfig.getConnection();
				// Bước 4: Truyền câu query vào CSDL vừa mở kết nối thông qua PreparedStatement
					PreparedStatement preparedStatement = connection.prepareStatement(query);
					preparedStatement.setString(1, name);
					preparedStatement.setString(2, description);
					
				// Bước 5: Thông báo cho csdl biết và thực thi câu query
				// Các cách thực thi: 
				// executeQuery : dành cho câu truy vấn là SELECT => luôn trả ra ResultSet
				// executeUpdate : Tất cả câu truy vấn còn lại ngoài SELECT ví dụ: INSERT, UPDATE, DELETE, ... 
					count = preparedStatement.executeUpdate();
					
				
			 }catch(Exception e) {
				 System.out.println("Lỗi insert "+e.getLocalizedMessage());
			 }
		 
		return count;
	}
	
	
}
