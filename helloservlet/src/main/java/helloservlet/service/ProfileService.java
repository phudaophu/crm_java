package helloservlet.service;
import helloservlet.repository.UserRepository;

import java.util.Date;
import java.util.List;

import helloservlet.entity.JobEntity;
import helloservlet.entity.StatusEntity;
import helloservlet.entity.TaskEntity;
import helloservlet.entity.UserEntity;
import helloservlet.repository.TaskRepository;
import helloservlet.repository.JobRepository;
import helloservlet.repository.StatusRepository;


public class ProfileService {
	private UserRepository userRepository = new UserRepository();
	private TaskRepository taskRepository = new TaskRepository();
	private JobRepository  jobRepository  = new JobRepository();
	private StatusRepository statusRepository = new StatusRepository();
	
	
	
	public boolean updateById(int jobId, String name, int userId, Date startDate, Date endDate, int statusId, int id) {
		return taskRepository.updateById(jobId, name, userId, startDate, endDate, statusId, id)>0;
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
	
	
	public int[] calculateTaskPercent(int userId){
		int sumListSize = 0;
		float percentList[] = {0,0,0};
		int percentListExchange[] = {0,0,0};
		for (int i = 1; i<4; i++) {
			int listSize = taskRepository.findByUserIdAndStatusId(userId, i).size();
			percentList[i-1] = listSize;
			sumListSize+=listSize;
		}
		if (sumListSize!=0) {
			// Calculate total size in arrayList
			for(int t=0; t< (percentList.length); t++) {
				percentList[t] = Math.round(percentList[t]*100/sumListSize) ;
				percentListExchange[t] = (int)percentList[t];
			}
			//percentList[2] = 100 - (percentList[0]+percentList[1]);
		}
		
		return percentListExchange;
	}
	
	public List<UserEntity> findTaskByEmailAndPassword(String email, String password){
		
		List<UserEntity> userList = userRepository.findByEmailAndPassword(email, password);
		
		for (UserEntity user : userList) {
			int userId = user.getId();
			String fullname = user.getFullname();
			List<TaskEntity> taskList= taskRepository.findByUserId(userId);
			
			for (TaskEntity taskEntity : taskList) {
				taskEntity.setUserName(fullname);
				
				int jobId = taskEntity.getIdJob();
				JobEntity jobEntity = jobRepository.findById(jobId);
				taskEntity.setJobName(jobEntity.getName());
				
				int statusId = taskEntity.getIdStatus();
				StatusEntity statusEntity = statusRepository.findById(statusId);
				taskEntity.setStatusName(statusEntity.getName());
			}
			user.setTaskList(taskList);
		}
		
		return userList;
	}
	
}
