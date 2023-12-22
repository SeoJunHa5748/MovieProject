package projectmovie;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SQL_CM extends DB {

	// 1-2. 현재 상영중인 영화 리스트 반환
	   
	public ArrayList<String> AllMovie() { 
  	  ArrayList<String> trueMovieList = new ArrayList<String>();
  	  try {
	         conn.setAutoCommit(false);
	         
	         String use = "USE cinemamanagement;";
	          ps = conn.prepareStatement(use);
	          ps.executeUpdate();
	            
             String sql1 = "select * from movies where ScreeningStatus = true;";
             ps = conn.prepareStatement(sql1);
             ResultSet resultSet = ps.executeQuery();

             while (resultSet.next()) { 
           	  String a = resultSet.getString("MovieTitle");
           	 trueMovieList.add(a);
             }
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
	         }
		return trueMovieList;
    }
    
 // 1-3. 상영중인 영화의 상영 시간 출력
    public ArrayList<String> timeList(String TBname, String Movie, String hallNum2, String todayDate) { 
  	  ArrayList<String> timeList = new ArrayList<String>();
  	  try {
	         conn.setAutoCommit(false);
	         
	         String use = "USE CinemaManagement;";
	          ps = conn.prepareStatement(use);
	          ps.executeUpdate();
	          
           String sql1 = "select Hall"+ hallNum2 + "Timetable from " + TBname + hallNum2+ todayDate+ 
        		   " where hall"+hallNum2+"MovieTitle = " + "'"+Movie+"'" ;
           //테이블이 3개로 나누어져서 sql문 변경
           ps = conn.prepareStatement(sql1);
           ResultSet resultSet = ps.executeQuery();

             while (resultSet.next()) { 
           	  String a = resultSet.getString("hall" + hallNum2 + "Timetable");
          
           	int b = Integer.parseInt(a);
           	int c = (int)b/60;
           	int d = b%60;
           	String f = String.format("%02d", c);
           	String g = String.format("%02d", d); // 두자리로 포맷팅
           	String e = f+"시"+g+"분";
            // 시 / 분 형태로 숫자변형
           	timeList.add(e);
             }
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
	         }
  	 Collections.sort(timeList); //정렬추가
		return timeList;
    }
    
    
  // 1-4. 회원예매 시 아이디 비번 올바른지 확인
    public Sign isMember(String inputId, String inputPass) {
  	  Sign member = null;
  	  try {
 	         conn.setAutoCommit(false);
 	         
 	         String use = "USE ConsumerInfo;";
 	          ps = conn.prepareStatement(use);
 	          ps.executeUpdate();
 	          
            String sql1 = "select * from sign where ID = ? and Pass = ? ;";
            ps = conn.prepareStatement(sql1);
            ps.setString(1, inputId);
            ps.setString(2, inputPass);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                String id = resultSet.getString("Id");
                String password = resultSet.getString("Pass");
                String name = resultSet.getString("InfoName");
                String phoneNumber = resultSet.getString("PhoneNumber");

                member = new Sign(id, password, name, phoneNumber);
            }

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
 	         }
		return member;

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
 
    // 4-2. 회원가입 정보 DB에 추가
    public boolean RegiMember(String Id, String Pass, String InfoName, String PhoneNumber) {
  	  try {
  	         conn.setAutoCommit(false);
  	         String use = "USE consumerinfo";
  	          ps = conn.prepareStatement(use);
  	          ps.executeUpdate();
  	            
  	         String sql = "insert into consumerinfo.sign (Id, Pass, InfoName, PhoneNumber) "
  	                     + "values (?, ?, ?, ?);";
  	         ps = conn.prepareStatement(sql);
  	         ps.setString(1, Id);
  	         ps.setString(2, Pass);
  	         ps.setString(3, InfoName);
  	         ps.setString(4, PhoneNumber);
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
    
    
    // 4-3. 아이디 중복확인
    public ArrayList<String> CheckId(String Id) {
  	  ArrayList<String> idArr = new ArrayList<String>();
  	  try {
	         conn.setAutoCommit(false);
	         String use = "USE consumerinfo";
	          ps = conn.prepareStatement(use);
	          ps.executeUpdate();
	            
            String sql1 = "SELECT Id FROM sign;";
            ps = conn.prepareStatement(sql1);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) { 
          	  String a = resultSet.getString("Id");
          	  idArr.add(a);
            }
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
	         }
		return idArr;
    }

    public ArrayList<BookingStatus> nonfind(String name) {
        ArrayList<BookingStatus> Addr = new ArrayList<>();
     String sql1 = "select a.* from consumerinfo.bookingstatus a where a.SerialNumber = ? or a.PhoneNumber = ?";
     try {
       
        stmt = conn.createStatement();
        String sql = "use consumerinfo";
        stmt.executeUpdate(sql);
        
        ps = conn.prepareStatement(sql1);
        ps.setString(1, name);
        ps.setString(2, name);
        
        rs = ps.executeQuery();
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
                 String oT = rs.getString("OrderTime");
                 int pM = rs.getInt("PayMent");
                 String ts = rs.getString("TicketStatus");

                 BookingStatus addr = new BookingStatus(Id, infoname, Pnum, sn, mt, hall,Recode,seatn, nOp, oT, pM, ts);
                 Addr.add(addr);
        }
        
     }catch(Exception e) {
     e.printStackTrace();
  }
     if(Addr.isEmpty()) {
        System.out.println("없는 정보 입니다");
     }else {
     for(BookingStatus list : Addr) {
  	   int i = 1;
  	   System.out.print(i+". ");
  	   System.out.println(list);
  	   i++;
     }
  }
     return Addr;
  }
    
    public  ArrayList<BookingStatus> find(String name) {
        ArrayList<BookingStatus> Addr = new ArrayList<>();
        String sql1 = "select a.* from bookingstatus a join sign b on a.id = b.id where a.id = ?";
        int i = 1;
        try {
     
        stmt = conn.createStatement();
        String sql = "use consumerinfo";
        stmt.executeUpdate(sql);
        
        ps = conn.prepareStatement(sql1);
        ps.setString(1, name);
        ps.setString(1, name);

        rs = ps.executeQuery();
        while(rs.next()) {
               String Id = rs.getString("Id");
                 String infoname = rs.getString("infoname");
                 String Pnum = rs.getString("PhoneNUMBER");
                 String sn = rs.getString("SerialNumber");
                 String mt = rs.getString("MovieTitle");
                 String hall = rs.getString("Hall");
                 String Recode = rs.getString("ReservationTimeCode");
                 String seatn = rs.getString("SeatNumber");
                 String nOp = rs.getString("NOP");
                 String oT = rs.getString("OrderTime");
                 int pM = rs.getInt("PayMent");
                 String ts = rs.getString("TicketStatus");

                 BookingStatus addr = new BookingStatus(Id, infoname, Pnum, sn, mt, hall,Recode, seatn, nOp, oT, pM, ts);
                 Addr.add(addr);
        }
        
     }catch(Exception e) {
     e.printStackTrace();
  }
     if(Addr.isEmpty()) {
        System.out.println("없는 정보 입니다");
     }else {
     for(BookingStatus list : Addr) {
       
  	   System.out.print(i+". ");
  	   System.out.println(list);
  	   i++;
     }}
  return Addr;
    }
    
    public void MemberCancle(BookingStatus addr) {
		// 예매현황 취소(=0) 상태로 만듦
		String sql = "UPDATE consumerinfo.bookingstatus SET TicketStatus = false WHERE Id = ? and SerialNumber = ? and PhoneNumber = ?";
		try {
			ps = conn.prepareStatement(sql);
			System.out.println(addr.getId());
			System.out.println(addr.getSerialNumber());
			System.out.println(addr.getPhoneNUMBER());
			
			ps.setString(1, addr.getId());
			ps.setString(2, addr.getSerialNumber());
			ps.setString(3, addr.getPhoneNUMBER());
			ps.executeUpdate();
			conn.commit();
			ps.close();
			SeatUpdate(addr.getMovieTitle(),addr.getSerialNumber(),addr.getHall(),addr.getReservationTimeCode());
			System.out.println("취소되었습니다.");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	} // end of method 

	public void Guestcancle(BookingStatus addr) {
		// 예매현황 취소(=0) 상태로 만듦
		String sql = "UPDATE consumerinfo.bookingstatus SET TicketStatus = false WHERE SerialNumber = ? or PhoneNumber = ? and Id = '0000'";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, addr.getSerialNumber());
			ps.setString(2, addr.getPhoneNUMBER());
			ps.executeUpdate();
			ps.close();
			SeatUpdate(addr.getMovieTitle(),addr.getSerialNumber(),addr.getHall(),addr.getReservationTimeCode());
			System.out.println("취소되었습니다.");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	} // end of method 
	
	public void SeatUpdate(String movieTitle,String SerialNumber,String hall,String ReservationTimeCode) {
		String sql = "UPDATE "+ movieTitle+".t"+hall+ReservationTimeCode+" SET SerialNumber = NULL WHERE SerialNumber = ?";
		try {
			ps= conn.prepareStatement(sql);
			ps.setString(1,SerialNumber);
			ps.executeUpdate();
			ps.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Sign> allMember() {
	       ArrayList<Sign> allMemberList = new ArrayList<>();
	         try {
	               conn.setAutoCommit(false);
	               
	               String use = "USE ConsumerInfo;";
	                ps = conn.prepareStatement(use);
	                ps.executeUpdate();
	                
	              String sql1 = "select * from sign;";
	              ps = conn.prepareStatement(sql1);
	              ResultSet resultSet = ps.executeQuery();
	              
	              while (resultSet.next()) {
	                  String id = resultSet.getString("Id");
	                  String password = resultSet.getString("Pass");
	                  String name = resultSet.getString("InfoName");
	                  String phoneNumber = resultSet.getString("PhoneNumber");

	                  allMemberList.add(new Sign(id, password, name, phoneNumber));
	              }
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
	               }
	        return allMemberList;

	      }  

}// end of SQLMETHOD

