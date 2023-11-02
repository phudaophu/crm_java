package helloservlet.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import helloservlet.entity.JobEntity;
import helloservlet.config.MysqlConfig;
import java.util.ArrayList;
import java.util.Date;

public class JobRepository {

	
	public JobEntity findById(int id) {
		
		JobEntity jobEntity  = new JobEntity();
		
		String query = "SELECT * FROM jobs WHERE id = ?";
		Connection connection = MysqlConfig.getConnection();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				jobEntity.setId(resultSet.getInt("id"));
				jobEntity.setName(resultSet.getString("name"));
				jobEntity.setStartDate(resultSet.getDate("start_date"));
				jobEntity.setEndDate(resultSet.getDate("end_date"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Lỗi findById job: " + e.getLocalizedMessage());
		}
		
		
		
		return jobEntity;
	}
	
	public int update(String name, Date start_date, Date end_date, int id) {
		
		int count=0;
		
		String query = "UPDATE jobs SET name = ?, start_date = ?, end_date = ? WHERE id=? ;";
		Connection connection = MysqlConfig.getConnection();
		
		java.sql.Date sqlDateStartDate = null;
		java.sql.Date sqlDateEndDate = null;
		
		if (start_date!=null) {
			sqlDateStartDate = new java.sql.Date(start_date.getTime()); 
		}
		if (end_date!=null) {
			sqlDateEndDate =new java.sql.Date(end_date.getTime());
		}
		
		try {
			PreparedStatement preparedStatement =  connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setDate(2, sqlDateStartDate);
			preparedStatement.setDate(3, sqlDateEndDate);
			preparedStatement.setInt(4, id);
			count = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Lỗi update job: "+e.getLocalizedMessage());
		}
		
		return count;
	}
	
	
	public int deleteById (int id) {
		int count = 0;
		
		String query = "DELETE FROM jobs WHERE id = ?;";
		Connection connection = MysqlConfig.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1,id);
			count = preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Lỗi delete job: "+e.getLocalizedMessage());
		}
		return count;
	}
	
	
	public int insert(String name, Date start_date, Date end_date) {
		int count = 0;
		String query = "INSERT INTO jobs (name,start_date,end_date) VALUES(?,?,?);";
		Connection connection = MysqlConfig.getConnection();

		java.sql.Date sqlDateStartDate = null;
		java.sql.Date sqlDateEndDate = null;
		if (start_date!=null) {
			sqlDateStartDate = new java.sql.Date(start_date.getTime()); 
		}
		if (end_date!=null) {
			sqlDateEndDate =new java.sql.Date(end_date.getTime());
		}
		
		try {

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setDate(2, sqlDateStartDate);
			preparedStatement.setDate(3, sqlDateEndDate);

			count = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Lỗi insert jobs: " + e.getLocalizedMessage());
		}

		return count;
	}

	public List<JobEntity> findAllJobs() {
		List<JobEntity> jobList = new ArrayList<JobEntity>();
		String query = "SELECT * from jobs";
		Connection connection = MysqlConfig.getConnection();

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				JobEntity jobEntity = new JobEntity();

				jobEntity.setId(resultSet.getInt("id"));
				jobEntity.setName(resultSet.getString("name"));
				jobEntity.setStartDate(resultSet.getDate("start_date"));
				jobEntity.setEndDate(resultSet.getDate("end_date"));

				jobList.add(jobEntity);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Lỗi findAllJobs: " + e.getLocalizedMessage());
		}

		return jobList;
	}

}
