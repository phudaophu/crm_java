package helloservlet.service;
import java.util.List;

import helloservlet.entity.RoleEntity;
import helloservlet.repository.RoleRepository;
public class RoleService {

	
	private RoleRepository roleRepository = new RoleRepository();	
	
	public boolean insert(String roleName, String roleDesc) {
		
		int count = roleRepository.insert(roleName,roleDesc);
		return count>0;
	}
	
	public List<RoleEntity> getAllRoles(){
		
		return roleRepository.findAll();
		
	}
	
	public boolean delete(int id) {
		return roleRepository.deleteById(id) >0;
	}
	
	public boolean update(String name, String desc, int id) {
		
		return roleRepository.updateById(name, desc, id) >0;
	}
	
	
}
