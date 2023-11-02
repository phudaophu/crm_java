package helloservlet;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name="helloServlet", urlPatterns = {"/hello"})
public class HelloServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		PrintWriter printWriter = resp.getWriter();
//		printWriter.write("test 01010101");
//		printWriter.close();
		
		
		
		// Buoi 02: servlet	
		String username = req.getParameter("username");
//				int age = Integer.parseInt(req.getParameter("age"));
		System.out.println("kiem tra: "+username+" - tuoi: ");
		
		
		// Buổi 04: Cookie and Session
//		HttpSession session = req.getSession();
//		session.setAttribute("cycbersoft", "Hello Session");
		
		// Buổi 05: Design Pattern (Strategy)
		// Controller: Package chứa các đường dẫn và nhận tham số. Chỉ dùng để chứa các class khai báo dường dẫn và nhận tham số (Lưu ý ko xử lý logic code)
		// Service : Sẽ dùng xử lý logic code cho Controller. Chứa các class để xử lý logic code cho các controller tương ứng
		// Repository: Dùng để kết nối tới database. Chứa các class trả ra dữ liệu của các câu querry liên quan đến các bảng trong database. Tức là chỉ thực thi câu query và trả ra kết quả của câu query (Lưu ý ko xử lý logic code)

		req.getRequestDispatcher("hello.jsp").forward(req,resp);
		
		
	}
}
