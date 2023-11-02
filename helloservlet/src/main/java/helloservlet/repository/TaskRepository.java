package helloservlet.repository;

import java.util.List;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import helloservlet.config.MysqlConfig;
import helloservlet.entity.TaskEntity;

public class TaskRepository {
	
	

	
	public int updateById(int jobId, String name, int userId, Date startDate, Date endDate, int statusId, int id) {
		int count = 0;
		String query = "UPDATE tasks SET name= ?, start_date= ?, end_date= ?, user_id= ?, job_id= ?, status_id= ? WHERE id= ?;";
		Connection connection = MysqlConfig.getConnection();
		
		java.sql.Date sqlDateStartDate = null;
		java.sql.Date sqlDateEndDate = null;
		if (startDate!=null) {
			sqlDateStartDate = new java.sql.Date(startDate.getTime()); 
		}
		if (endDate!=null) {
			sqlDateEndDate =new java.sql.Date(endDate.getTime());
		}
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setDate(2, sqlDateStartDate);
			preparedStatement.setDate(3, sqlDateEndDate);
			preparedStatement.setInt(4, userId);
			preparedStatement.setInt(5,jobId);
			preparedStatement.setInt(6, statusId);
			preparedStatement.setInt(7,id);
			
			count = preparedStatement.executeUpdate();
			//close connection
			connection.close();
		} catch (SQLException e) {
			System.out.println("Lỗi updateById task: "+e.getLocalizedMessage());
		}
		
		return count;
	}
	
	
	public int deleteById(int id) {
		int count = 0;
		String query = "DELETE FROM tasks WHERE id =?;";
		Connection connection = MysqlConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			
			count = preparedStatement.executeUpdate();
			//close connection
			connection.close();
		} catch (SQLException e) {
			System.out.println("Lỗi deleteById task: "+e.getLocalizedMessage());
		}
		
		return count;
	}
	
	
	public int insert(int jobId, String name, int userId, Date startDate, Date endDate, int statusId) {
		int count = 0;
		String query = "INSERT INTO tasks (name,start_date,end_date,user_id,job_id,status_id) VALUES(?,?,?,?,?,?)";
		Connection connection = MysqlConfig.getConnection();
		
		java.sql.Date sqlDateStartDate = null;
		java.sql.Date sqlDateEndDate = null;
		if (startDate!=null) {
			sqlDateStartDate = new java.sql.Date(startDate.getTime()); 
		}
		if (endDate!=null) {
			sqlDateEndDate =new java.sql.Date(endDate.getTime());
		}
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setDate(2, sqlDateStartDate);
			preparedStatement.setDate(3, sqlDateEndDate);
			preparedStatement.setInt(4, userId);
			preparedStatement.setInt(5,jobId);
			preparedStatement.setInt(6, statusId);
			count = preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Lỗi insert task: "+e.getLocalizedMessage());
		}

		return count;
	}
	
	public List<TaskEntity> findAllTasks () {
		List<TaskEntity> taskList = new ArrayList<TaskEntity>();
		
		String query = "SELECT * FROM tasks;";
		
		Connection connection = MysqlConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				
				TaskEntity taskEntity = new TaskEntity();
				taskEntity.setId(resultSet.getInt("id"));
				taskEntity.setName(resultSet.getString("name"));
				taskEntity.setStartDate(resultSet.getDate("start_date"));
				taskEntity.setEndDate(resultSet.getDate("end_date"));
				taskEntity.setIdUser(resultSet.getInt("user_id"));
				taskEntity.setIdJob(resultSet.getInt("job_id"));
				taskEntity.setIdStatus(resultSet.getInt("status_id"));
				
				taskList.add(taskEntity);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Lỗi findAllTasks: "+e.getLocalizedMessage());
		}
		
		return taskList;
	}
}
