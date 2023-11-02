package helloservlet.config;
/*
 * 
 * 
 *  Tạo package: helloservlet.config (dùng để chứa các class kết nối tới database)
 *  class dùng để khai báo thông tin cấu hình tạo kết nối tới CSDL
 * 
 * 
 * */

import java.sql.Connection;
import java.sql.DriverManager;

public class MysqlConfig {
	public static Connection getConnection() {
		
		try {
			// Khai báo driver sử dụng cho JDBC kết nối tới Mysql (từ khóa search: class.forname + [cơ sở dữ liệu])
			Class.forName("com.mysql.cj.jdbc.Driver");
			// khai báo thông tin csdl mà JDBC kết nối tới [url]/[tên database]
			return DriverManager.getConnection("jdbc:mysql://localhost:3307/crm_app", "root", "admin123");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Lỗi kết nối tới CSDL "+e.getMessage());
		}
		
		return null;
	}
}
