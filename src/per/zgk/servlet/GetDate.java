package per.zgk.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.DButil;
import org.codehaus.jackson.map.ObjectMapper;

import per.zgk.virus.bean.Province;


/**
 * Servlet implementation class GetDate
 */
@WebServlet("/GetDate")
public class GetDate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	
    	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		String time1=request.getParameter("time1");
    	String time2=request.getParameter("time2");
		 List<Province> list =new ArrayList<Province>();
		  DButil DB = new DButil();
			Connection conn= null;
			PreparedStatement ps = null;
			conn = DB.getConn();
			
			
			
		 String sql="select Province,Confirmed_num from info where Date between ? and ?";
		System.out.println(time1);
		
		  try {
			  ps = conn.prepareStatement(sql);
			  ps.setString(1, time1);
			  ps.setString(2, time2);
				ResultSet rs = ps.executeQuery();    
	            while(rs.next())
	            {
	                String Province=rs.getString("Province");     
	                String Confirmed_num=rs.getString("Confirmed_num");
	          
	              Province p=new Province(Confirmed_num, Province);
	                list.add(p);
	                }
	                
	           
	        } catch (Exception e) {
				
				
				e.printStackTrace();
			} finally {
				DB.close(conn);
				DB.close(ps);
			}
		
		
		
		
		/*request.setAttribute("json", json);

		//这里是转发，从Servlet跳转到showinfo.jsp页面，并且带上request和response对象中原有的参数
		  response.setContentType("text/html;charset=utf-8");
		request.getRequestDispatcher("/viewclass.jsp").forward(request, response);
		*/
		  
		  ObjectMapper mapper = new ObjectMapper(); // 提供java-json相互转换功能的类
			String json = mapper.writeValueAsString(list); // 将list中的对象转换为Json字符串

			System.out.println(json);

			// 将json字符串数据返回给前端
			response.setContentType("text/html; charset=utf-8");
			response.getWriter().write(json);
	
	}

}
