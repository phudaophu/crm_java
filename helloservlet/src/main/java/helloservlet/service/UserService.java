package helloservlet.service;
import java.util.ArrayList;
import java.util.List;

import helloservlet.entity.UserEntity;
import helloservlet.entity.RoleEntity;
import helloservlet.entity.TaskEntity;
import helloservlet.repository.UserRepository;
import helloservlet.repository.RoleRepository;
import helloservlet.repository.TaskRepository;
public class UserService {

	UserRepository userRepository = new UserRepository();
	RoleRepository roleRepository = new RoleRepository();
	TaskRepository taskRepository = new TaskRepository();

	
	public int[] calculateTaskPercent(int userId){
		int sumListSize = 0;
		int percentList[] = {0,0,0};
		for (int i = 1; i<4; i++) {
			int listSize = taskRepository.findByUserIdAndStatusId(userId, i).size();
			percentList[i-1] = listSize;
			sumListSize+=listSize;
		}
		if (sumListSize!=0) {
			// Calculate total size in arrayList
			for(int t=0; t< percentList.length; t++) {
				percentList[t] = percentList[t]*100/sumListSize;
			}
		}
		
		return percentList;
	}
	
	public UserEntity findById(int id) {
		return userRepository.findById(id);
	}
	
	public List<TaskEntity> findByUserIdAndStatusId (int userId, int statusId) {
		return taskRepository.findByUserIdAndStatusId(userId, statusId);
	}
	
	public boolean updateInfoById(String email, String phonenumber, int idRole, int id) {
		return userRepository.updateInfoById(email,phonenumber, idRole, id) >0;
	}
	
	public boolean checkEmailPassword(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password).size()>0;
	}
	
	public boolean updateById(String email, String password, String phonenumber, int idRole, int id) {
		return userRepository.updateById(email, password, phonenumber, idRole, id) >0;
	}
	
	
	public boolean deleteById (int id) {
		return userRepository.deleteById(id) > 0;
	}
	
	public List<RoleEntity> findAllRoles(){
		List<RoleEntity> roleList = roleRepository.findAll();
		return roleList;
	}
	
	public List<String> getLastNameList(){
		
		 List<String> lastNameList = new ArrayList<String>();
		 List<UserEntity> userList = this.findAllUsers();
		
		 if(userList.size()>0) {
			 for(UserEntity userEntity : userList) {
				 String fullName = userEntity.getFullname();
				 String lastName ="";
				 int lastSpaceIndex = fullName.lastIndexOf(" ");
				 if (lastSpaceIndex != -1) {
					 lastName = fullName.substring(lastSpaceIndex); 
				 }
				 lastNameList.add(lastName);
			 }
		 }
		return  lastNameList;
	}
	
	public List<String> getFirstNameList(){
		
		 List<String> firstNameList = new ArrayList<String>();
		 List<UserEntity> userList = this.findAllUsers();
		
		 if(userList.size()>0) {
			 for(UserEntity userEntity : userList) {
				 String fullName = userEntity.getFullname();
				 String firstName =  fullName;
				 int firstSpaceIndex = fullName.indexOf(" ");
				 if(firstSpaceIndex!=-1) {
					 firstName = fullName.substring(0, firstSpaceIndex);
				 }
				 firstNameList.add(firstName);
			 }
		 }
		 
		return  firstNameList;
	}
	
	public List<UserEntity> findAllUsers(){
		
		List<UserEntity> userList = userRepository.findAllUsers(); 
		
		for(UserEntity user : userList ) {
			int idRole = user.getIdRole();
			RoleEntity roleEntity = roleRepository.findRoleById(idRole);
			user.setRoleName(roleEntity.getName());
		}
		return userList;
	}
	
	public boolean insert(String email, String password, String fullname, String phonenumber, int idRole) {
		return userRepository.insert(email, password, fullname, phonenumber, idRole)>0;
	}
	
}
