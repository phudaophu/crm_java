package helloservlet.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import helloservlet.config.MysqlConfig;
import helloservlet.entity.StatusEntity;

public class StatusRepository {
	
	
	
	public List<StatusEntity> findAllStatus(){
		List<StatusEntity> statusList = new ArrayList<StatusEntity>();
		
		String query="SELECT * FROM status";
		Connection connection = MysqlConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement= connection.prepareStatement(query);
			ResultSet resultSet =preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				StatusEntity statusEntity = new StatusEntity();
				statusEntity.setId(resultSet.getInt("id"));
				statusEntity.setName(resultSet.getString("name"));
				statusList.add(statusEntity);
			}
			connection.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Lỗi findAllStatus: "+e.getLocalizedMessage());
		}
		
		return statusList;
	}
	
	
	public StatusEntity findById(int id) {
		StatusEntity statusEntity= new StatusEntity();
		String query="SELECT * FROM status WHERE id = ?;";
		Connection connection = MysqlConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement= connection.prepareStatement(query);
			preparedStatement.setInt(1,id);
			
			ResultSet resultSet =preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				
				statusEntity.setId(resultSet.getInt("id"));
				statusEntity.setName(resultSet.getString("name"));
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Lỗi findById Status: "+e.getLocalizedMessage());
		}
			
		return statusEntity;
	}
	
	
}
