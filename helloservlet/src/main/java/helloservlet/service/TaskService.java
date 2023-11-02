package helloservlet.service;
import java.util.Date;
import java.util.List;

import helloservlet.entity.TaskEntity;
import helloservlet.entity.UserEntity;
import helloservlet.entity.JobEntity;
import helloservlet.entity.StatusEntity;
import helloservlet.repository.TaskRepository;
import helloservlet.repository.UserRepository;
import helloservlet.repository.JobRepository;
import helloservlet.repository.StatusRepository;

public class TaskService {
	
		TaskRepository taskRepository = new TaskRepository();
		UserRepository userRepository = new UserRepository();
		JobRepository  jobRepository = new JobRepository();
		StatusRepository statusRepository = new StatusRepository();
		
		
		public boolean updateById(int jobId, String name, int userId, Date startDate, Date endDate, int statusId, int id) {
			return taskRepository.updateById(jobId, name, userId, startDate, endDate, statusId, id)>0;
		}
		
		public boolean deleteById(int id) {
			
			return taskRepository.deleteById(id) >0;
		}
		
		
		public boolean insert(int jobId, String name, int userId, Date startDate, Date endDate, int statusId) {
			return taskRepository.insert(jobId, name, userId, startDate, endDate, statusId) >0;
		}
		
		public List<StatusEntity> findAllStatus(){
			List<StatusEntity> statusList = statusRepository.findAllStatus();
			return statusList;
		}
		
		public List<UserEntity> findAllUsers(){
			List<UserEntity> userList = userRepository.findAllUsers();
			return userList;
			
		}
		
		public List<JobEntity> findAllJobs(){
			List<JobEntity> jobList = jobRepository.findAllJobs();
			return jobList;	
		}
		
		
		public List<TaskEntity> findAllTasks() {
			List<TaskEntity> taskList = taskRepository.findAllTasks();
			
			for (TaskEntity taskEntity : taskList) {
				
				int userId = taskEntity.getIdUser();
				UserEntity userEntity = userRepository.findById(userId);
				taskEntity.setUserName(userEntity.getFullname());
				
				int jobId = taskEntity.getIdJob();
				JobEntity jobEntity = jobRepository.findById(jobId);
				taskEntity.setJobName(jobEntity.getName());
				
				int statusId = taskEntity.getIdStatus();
				StatusEntity statusEntity = statusRepository.findById(statusId);
				taskEntity.setStatusName(statusEntity.getName());
				
			}
			
			return taskList;
		}
		
}
