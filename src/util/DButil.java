package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ���ݿ�Ĺ�����
 * 
 * @author
 *
 */
public final class DButil {

	
	/**
	 *  ���ݿ�����Ӻ���
	 *  ���޸ĵ�
	 * 
	 * @return ���ݿ������
	 */
	public  Connection getConn()
	{
		Connection connection =null; 
		// user Ϊ���ݿ�����  �� ����ӵ�������
		String db_url="jdbc:mysql://localhost:3306/wuhan_virus?serverTimezone=UTC";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(db_url, "root", "123456");
			System.out.println("Success connect MySql server!");  
			
		} catch (Exception e) {
			
		}
		
		
		
		return connection;
	}
	
	public void close(Connection conn)
	{
		if(conn!=null)
		{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public  void close(Statement state) {
		if (state != null) {
			try {
				state.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	
	}

	public  void close(PreparedStatement ps) {
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	
	}
	public  void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
}