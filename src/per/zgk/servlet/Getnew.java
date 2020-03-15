package per.zgk.servlet;

import java.io.IOException;
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

import org.codehaus.jackson.map.ObjectMapper;

import per.zgk.dao.PageProcess;
import per.zgk.virus.bean.Province;
import us.codecraft.webmagic.Spider;
import util.DButil;

/**
 * Servlet implementation class Getnew
 */
@WebServlet("/Getnew")
public class Getnew extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Getnew() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Spider.create(new PageProcess()).addUrl("https://ncov.dxy.cn/ncovh5/view/pneumonia").run();
		
		List<Province> list =new ArrayList<Province>();
		  DButil DB = new DButil();
			Connection conn= null;
			PreparedStatement ps = null;
			conn = DB.getConn();
			
			
			
		 String sql="select name,sum from real_time";
		
		
		  try {
			  ps = conn.prepareStatement(sql);

				ResultSet rs = ps.executeQuery();    
	            while(rs.next())
	            {
	                String Province=rs.getString("name");     
	                String Confirmed_num=rs.getString("sum");
	          
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
