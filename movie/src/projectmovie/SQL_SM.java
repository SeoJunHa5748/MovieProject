package projectmovie;

import java.util.HashMap;

public class SQL_SM extends DB{
	
	//Update
	
	public boolean CreatetimeTB(String datecode,String movietitle,String time,String hall) {  
		 String use = "USE cinemamanagement";
		 String sql1 = "CREATE TABLE IF NOT EXISTS TimetableT1" + datecode + "("
	    			+"    FilmOrder int NOT NULL AUTO_INCREMENT PRIMARY KEY,"
	    			+"    Hall1Timetable int(5),"
	                + "    Hall1Movietitle varchar(15),"
	                + "CONSTRAINT FOREIGN KEY(Hall1Movietitle) REFERENCES moviecode(MovieTitle) ON DELETE CASCADE"
	                + ");";
		 String sql2 = "CREATE TABLE IF NOT EXISTS TimetableT2" + datecode + "("
	    			+"    FilmOrder int NOT NULL AUTO_INCREMENT PRIMARY KEY,"	   	    			
	                + "    Hall2Timetable int(5),"
	                + "    Hall2Movietitle varchar(15),"
	                + "CONSTRAINT FOREIGN KEY(Hall2Movietitle) REFERENCES moviecode(MovieTitle) ON DELETE CASCADE"	   	             
	                + ");";
		 String sql3 = "CREATE TABLE IF NOT EXISTS TimetableT3" + datecode + "("
	    			+"    FilmOrder int NOT NULL AUTO_INCREMENT PRIMARY KEY,"
	                + "    Hall3Timetable int(5),"
	                + "    Hall3Movietitle varchar(15),"
	                + "CONSTRAINT FOREIGN KEY(Hall3Movietitle) REFERENCES moviecode(MovieTitle) ON DELETE CASCADE"
	                + ");";
		 try {
	    	  conn.setAutoCommit(false);
	    	  ps = conn.prepareStatement(use);
	    	  ps.executeUpdate();
		      ps = conn.prepareStatement(sql1);
		      ps.executeUpdate();
	   	      ps = conn.prepareStatement(sql2);
	   	      ps.executeUpdate();
	   		  ps = conn.prepareStatement(sql3);
	   		  ps.executeUpdate();      
	   		  conn.commit();
		 }catch(Exception e) {
	         e.printStackTrace();
	         
	         if(conn != null) {
	        	 try {
	        		 conn.rollback();
	        	 }catch(Exception e1) {
	        		 e1.printStackTrace();
	        	 }
	         }
	         return false;
	      }
	   return true;
	   }
//C
	public boolean SaveTimeTB(String datecode,String movietitle,String time,String hall) {
	   String sql1 =  "INSERT INTO CinemaManagement.TimetableT"+ hall + datecode + "(Hall" +hall+ "Timetable,Hall"+hall+"Movietitle)"
            +"VALUES(?,?)";  

	   try {
 	  conn.setAutoCommit(false);
 	  ps = conn.prepareStatement(sql1);
       ps.setString(1,time);
       ps.setString(2,movietitle);
       ps.executeUpdate();
       conn.commit();
   }catch(Exception e) {
      e.printStackTrace();
      
      if(conn != null) {
     	 try {
     		 conn.rollback();
     	 }catch(Exception e1) {
     		 e1.printStackTrace();
     	 }
      }
      return false;
   }
return true;
}
//U
	public boolean CreateSeatTB(String movietitle,String hall,String time,String timecode)  {
		   String use = "USE " + movietitle;
		   String sql = "CREATE TABLE IF NOT EXISTS T"+hall+timecode+time+"("
	               + "   SeatNumber varchar(10),"
	               + "   SerialNumber varchar(20),"
	               + "CONSTRAINT FOREIGN KEY(SerialNumber) REFERENCES consumerinfo.bookingstatus(SerialNumber) ON DELETE CASCADE"
	               + ");";
		   try {
	    	 conn.setAutoCommit(false);
	    	 ps = conn.prepareStatement(use);
	    	 ps.executeUpdate();
	    	 ps = conn.prepareStatement(sql);
	         ps.executeUpdate();
	         
	         conn.commit();
	     
		   }catch(Exception e) {
	        e.printStackTrace();
	        
	        if(conn!=null) {
	        	 try {
	        		 conn.rollback();
	        	 }catch(Exception e1) {
	        		 e1.printStackTrace();
	        	 }
	         }
	         return false;
	      }
	   return true;
	   }
//C
	public void copy(String movietitle,String hall,String time,String timecode) {
		   
		   String sql1 ="INSERT INTO " + movietitle+".T"+hall+timecode+time
	               + " SELECT * FROM hall.hall"+hall;
		   
		   try {
			   ps = conn.prepareStatement(sql1);
		       ps.executeUpdate();
		       conn.commit();		
		   }catch(Exception e) {
			   e.printStackTrace();
		   }
	   }
//U
	public void deleteTimeTB(String hall,String datecode,Integer time,String movietitle) {
		try {
		String sql = "DELETE FROM cinemamanagement.timetablet"+hall+"2023"+datecode
					+" WHERE Hall"+hall+"Timetable = ?";
		String sql2 = "DROP TABLE "+movietitle+".t"+hall+datecode+time+";";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, time);
		ps.executeUpdate();
		ps = conn.prepareStatement(sql2);
		ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}//end of delete
//D
	public HashMap<Integer,String> timeTablecheck(String datecode,String hall) {
		String sql ="SELECT * FROM cinemamanagement.timetablet"+hall+"2023"+ datecode; 		
		HashMap<Integer,String> arr = new HashMap<>();
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				int a = rs.getInt("Hall"+hall+"Timetable");
				String b = rs.getString("Hall"+hall+"Movietitle");
			arr.put(a, b);
			}
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return arr;
	}
//R
	
}//end of ScheduleManager class