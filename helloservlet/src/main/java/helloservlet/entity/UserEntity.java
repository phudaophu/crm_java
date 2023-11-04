package helloservlet.entity;

import java.util.List;

public class UserEntity {
	
	private int id; 
	private String email;
	private String password; 
	private String fullname; 
	private String phonenumber; 
	private int idRole;
	private String rolename;
	
	private List<TaskEntity> taskList;
	
	
	
	public List<TaskEntity> getTaskList() {
		return taskList;
	}
	public void setTaskList(List<TaskEntity> taskList) {
		this.taskList = taskList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public int getIdRole() {
		return idRole;
	}
	public void setIdRole(int idRole) {
		this.idRole = idRole;
	}
	public String getRoleName() {
		return rolename;
	}
	public void setRoleName(String rolename) {
		this.rolename = rolename;
	}
	
}
