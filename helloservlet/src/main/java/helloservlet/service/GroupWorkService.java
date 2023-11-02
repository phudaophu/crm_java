package helloservlet.service;
import java.util.Date;
import java.util.List;

import helloservlet.entity.JobEntity;
import helloservlet.repository.JobRepository;

//import java.time.format.DateTimeFormatter;

public class GroupWorkService {
	JobRepository jobRepository = new JobRepository();
	
	
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
