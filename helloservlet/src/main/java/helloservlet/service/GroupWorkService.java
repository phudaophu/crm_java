package helloservlet.service;
import java.util.Date;
import java.util.List;

import helloservlet.entity.JobEntity;
import helloservlet.entity.TaskEntity;
import helloservlet.entity.UserEntity;
import helloservlet.repository.JobRepository;
import helloservlet.repository.TaskRepository;
import helloservlet.repository.UserRepository;

//import java.time.format.DateTimeFormatter;

public class GroupWorkService {
	private JobRepository jobRepository = new JobRepository();
	private TaskRepository taskRepository = new TaskRepository();
	private UserRepository userRepositoty = new UserRepository();
	
	
	
	
	
	public int[] calculateTaskPercent(int JobId){
		int sumListSize = 0;
		float percentList[] = {0,0,0};
		int percentListExchange[] = {0,0,0};
		for (int i = 1; i<4; i++) {
			//int listSize = taskRepository.findByUserIdAndStatusId(userId, i).size();
			int listSize = taskRepository.findByJobIdAndStatusId(JobId, i).size();
			percentList[i-1] = listSize;
			sumListSize+=listSize;
		}
		if (sumListSize!=0) {
			// Calculate total size in arrayList
			for(int t=0; t< percentList.length; t++) {
				percentList[t] = Math.round(percentList[t]*100/sumListSize);
				percentListExchange[t] = (int)percentList[t];
			}
			//percentList[2] = 100 - (percentList[0]+percentList[1]);
		}
		
		return percentListExchange;
	}
	
	
	public List<TaskEntity> findTaskByUserIdAndStatusId( int userId, int statusId){
		return taskRepository.findByUserIdAndStatusId(userId, statusId);
	}
	
	public List<UserEntity> findUserById(int id){
		
		List<UserEntity> userList= userRepositoty.findByJobId(id);
		
		for(UserEntity user: userList) {
			int userId = user.getId();
			List<TaskEntity> taskList = this.findTaskByIdAndUserId(id,userId);
			user.setTaskList(taskList);
		}
		
		return userList;
	}
	
	public List<TaskEntity> findTaskByIdAndUserId(int id,int userId){
		return taskRepository.findByJobIdAndUserId(id,userId);
	}
	
	public boolean updateJobs(String name, Date start_date, Date end_date, int id) {
		return jobRepository.update(name, start_date, end_date, id)>0;
	}
	
	public boolean deleteById(int id) {
		return jobRepository.deleteById(id)>0;
	}
	
	public boolean insertJobs(String name, Date start_date, Date end_date) {
		return jobRepository.insert(name, start_date, end_date)>0;
	}
	
	public List<JobEntity> findAllJobs() {
		
		List<JobEntity> jobList = jobRepository.findAllJobs();
		//DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return jobList;
	}
}
