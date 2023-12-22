package projectmovie;

import java.util.ArrayList;
import java.util.List;

public class SQL_RM extends DB {
	
	public void Consumerinfo() { // 예약자 전체 조회 
         ArrayList<BookingStatus> Addr = new ArrayList<>();

         try {
             conn = getConnection();
             
             stmt = conn.createStatement();
            
             String sql1 = "use consumerinfo";
               stmt.executeUpdate(sql1);

            String sql = "select * from Bookingstatus";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
               String Id = rs.getString("Id");
               String infoname = rs.getString("infoname");
               String Pnum = rs.getString("PhoneNUMBER");
               String sn = rs.getString("SerialNumber");
               String mt = rs.getString("MovieTitle");
               String hall = rs.getString("Hall");
               String Recode = rs.getString("ReservationTimeCode");
               String seatn = rs.getString("SeatNumber");
               String nOp = rs.getString("NOP");
               String oT =rs.getString("OrderTime");
               int pM =rs.getInt("PayMent");
               String ts =rs.getString("TicketStatus");

               BookingStatus addr = new BookingStatus(Id, infoname, Pnum, sn, mt, hall,Recode, seatn, nOp, oT, pM, ts);
               Addr.add(addr);
            }

         } catch (Exception e) {
            e.printStackTrace();

         }
         for (BookingStatus list : Addr) {
            System.out.println(list);
         }
      }
	
	public void Reservationinfo() {
	      ArrayList<BookingStatus> Addr = new ArrayList<>();

	      try {
	          conn = getConnection();
	          
	          stmt = conn.createStatement();
	         
	          String sql1 = "use consumerinfo";
	         	stmt.executeUpdate(sql1);

	         String sql = "select * from Bookingstatus";
	         rs = stmt.executeQuery(sql);

	         while (rs.next()) {
	            String Id = rs.getString("Id");
	            String infoname = rs.getString("infoname");
	            String Pnum = rs.getString("PhoneNUMBER");
	            String sn = rs.getString("SerialNumber");
	            String mt = rs.getString("MovieTitle");
	            String hall = rs.getString("Hall");
	            String Recode = rs.getString("ReservationTimeCode");
	            String seatn = rs.getString("SeatNumber");
	            String nOp = rs.getString("NOP");
	            String oT =rs.getString("OrderTime");
	            int pM =rs.getInt("PayMent");
	            String ts =rs.getString("TicketStatus");

	            BookingStatus addr = new BookingStatus(Id, infoname, Pnum, sn, mt, hall,Recode, seatn, nOp, oT, pM, ts);
	            Addr.add(addr);
	         }

	      } catch (Exception e) {
	         e.printStackTrace();

	      }
	      for (BookingStatus list : Addr) {
	         System.out.println(list);
	      }
	   }
	
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
	
	public boolean bookingSeatTable(String MovieTitle,List<String> SeatNumber,String SerialNumber,int Hall,String datetimecode) {
        
        String safeon = "SET SQL_SAFE_UPDATES = 0";
        String sql = "UPDATE "+MovieTitle+".t"+Hall+datetimecode
              +" SET SerialNumber = ?"
              +" WHERE SeatNumber = ?";
        String safeoff = "SET SQL_SAFE_UPDATES = 1";
        int affectedRows = 0;
        try {
           ps = conn.prepareStatement(safeon);
           ps.executeUpdate();
           ps = conn.prepareStatement(sql);
           for(int i=0;i<SeatNumber.size();i++) {
              ps = conn.prepareStatement(sql);
           ps.setString(1, SerialNumber);
           ps.setString(2,(String) SeatNumber.get(i));
           
           
           
           affectedRows = ps.executeUpdate();
           conn.commit();
           }
           ps = conn.prepareStatement(safeoff);
           ps.executeUpdate();
           
           if (affectedRows == 0) {
                 return false;
             }
      	}catch(Exception e) {
        	e.printStackTrace();
        	return false;
        	}
        return true;
        }

    public boolean saveReservation(String Id, String InfoName, String PhoneNumber, String SerialNumber, String MovieTitle, int Hall, String ReservationTimeCode, List<String> seatList, int NOP, String OrderTime, int Payment, boolean TicketStatus) {
        try {
             conn.setAutoCommit(false);
             String use = "USE consumerinfo";
              ps = conn.prepareStatement(use);
              ps.executeUpdate();
              String seat = "";
              for(int i = 0 ; i<seatList.size();i++) {
                seat += (String) seatList.get(i);
              }
             String sql = "insert into consumerinfo.bookingstatus (Id, InfoName, PhoneNumber, SerialNumber, MovieTitle, Hall,ReservationTimeCode, SeatNumber, NOP, OrderTime, Payment, TicketStatus) "
                         + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
             ps = conn.prepareStatement(sql);
             ps.setString(1, Id);
             ps.setString(2, InfoName);
             ps.setString(3, PhoneNumber);
             ps.setString(4, SerialNumber);
             ps.setString(5, MovieTitle);
             ps.setInt(6, Hall);
             ps.setString(7, ReservationTimeCode);
             ps.setString(8, seat);
             ps.setInt(9, NOP);
             ps.setString(10, OrderTime);
             ps.setInt(11, Payment);
             ps.setBoolean(12, TicketStatus);
             ps.executeUpdate();
             
             conn.commit();
             return true;
             
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
    }
    public boolean saveReservation(String InfoName, String PhoneNumber, String SerialNumber, String MovieTitle, int Hall,String ReservationTimeCode, List<String> seatList, int NOP, String OrderTime, int Payment, boolean TicketStatus) {
        try {
             conn.setAutoCommit(false);
             String use = "USE consumerinfo";
              ps = conn.prepareStatement(use);
              ps.executeUpdate();
              String seat = "";
              for(int i = 0 ; i<seatList.size();i++) {
                seat += (String) seatList.get(i);
              }
             String sql = "insert into consumerinfo.bookingstatus (InfoName, PhoneNumber, SerialNumber, MovieTitle, Hall,ReservationTimeCode ,SeatNumber, NOP, OrderTime, Payment, TicketStatus) "
                         + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
             ps = conn.prepareStatement(sql);
            
             ps.setString(1, InfoName);
             ps.setString(2, PhoneNumber);
             ps.setString(3, SerialNumber);
             ps.setString(4, MovieTitle);
             ps.setInt(5, Hall);
             ps.setString(6, ReservationTimeCode);
             ps.setString(7, seat);
             ps.setInt(8, NOP);
             ps.setString(9, OrderTime);
             ps.setInt(10, Payment);
             ps.setBoolean(11, TicketStatus);
             ps.executeUpdate();
             
             conn.commit();
             return true;
             
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

    }
	
    public int runningtime(String MovieTitle) {
    	  String sql = "SELECT RunningTime FROM "+MovieTitle+".info";
    	  try {
    		  ps = conn.prepareStatement(sql);
    		  rs =  ps.executeQuery();
    		 
    		 int a = 0;
    		 
    		 while(rs.next()) {
    		  a = rs.getInt("RunningTime");
    		 }
    		 
    		 return a;
    	
    	  }catch(Exception e) {
    		  e.printStackTrace();
    		  return -1;
    	  }
      
      }
  }

