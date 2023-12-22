package projectmovie;

import java.sql.*;


public abstract class DB {
	
	 Connection conn = null;
	 ResultSet rs =null;
	 PreparedStatement ps =null;
	 Statement stmt =null;
	
	String url = "jdbc:mysql://localhost:3306/?user=root";
	String name = "root";
	String password = "1234";




		public DB() {
			conn = getConnection();
	}//end of makeDb

		public Connection getConnection() {
			Connection conn = null;
			try {
				conn = DriverManager.getConnection(url,name,password);
				System.out.println("Connnection");
				
				
			}catch(Exception e) {
				System.out.println("retry");
			}
			return conn;
	}//end of getConnection
		

			
		





	}//end of Db class

