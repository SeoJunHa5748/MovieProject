package projectmovie;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class SQL_MM extends DB {
	
	public ArrayList<Movies> MovieList(){
		ArrayList<Movies> arr = new ArrayList<>();
		
		try {
			String sql = "SELECT * FROM cinemamanagement.movies";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
			String a = rs.getString("MovieTitle");
			String b = rs.getString("MovieSerialNumber");
			String c = rs.getString("Genre");
			String d = rs.getString("ReleaseDate");
			String e = rs.getString("WithdrawDate");
			String f = rs.getString("ScreeningStatus");
			arr.add(new Movies(a, b, c, d, e, f));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	return arr;
	}
	
	public boolean RegiMovie(String MovieTitle, String MovieSerialNumber, String Genre, String ReleaseDate, String WithdrawDate, boolean ScreeningStatus) {
        
        try {
        conn.setAutoCommit(false);
        String use = "USE cinemamanagement";
         ps = conn.prepareStatement(use);
         ps.executeUpdate();
           
        String sql = "insert into cinemamanagement.movies (MovieTitle, MovieSerialNumber, Genre, ReleaseDate, WithdrawDate, ScreeningStatus)"
                    + "values (?, ?, ?, ?, ?, ?);";
        ps = conn.prepareStatement(sql);
        ps.setString(1, MovieTitle);
        ps.setString(2, MovieSerialNumber);
        ps.setString(3, Genre);
        ps.setString(4, ReleaseDate);
        ps.setString(5, WithdrawDate);
        ps.setBoolean(6, ScreeningStatus);
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
	
	public boolean RegiMoviecode(String MovieTitle, String MovieSerialNumber) {
        try {
        conn.setAutoCommit(false);
        String use = "USE cinemamanagement";
         ps = conn.prepareStatement(use);
         ps.executeUpdate();
           
        String sql = "insert into cinemamanagement.moviecode (MovieTitle, MovieSerialNumber)"
                    + "values (?, ?);";
        ps = conn.prepareStatement(sql);
        ps.setString(1, MovieTitle);
        ps.setString(2, MovieSerialNumber);
        
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
	
	public void CreateMovieDB(String MovieTitle) {
        try {
            conn.setAutoCommit(false);
            String sql1 = "CREATE DATABASE " + MovieTitle + " CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci;";
            ps = conn.prepareStatement(sql1);
            ps.executeUpdate();
            conn.commit();
        } catch(Exception e) {
             e.printStackTrace();
             if(conn!=null) {
                try {
                   conn.rollback();
                }catch(Exception e1) {
                   e1.printStackTrace();
                }
             }
          }
    }
	
	public void CreateInfoTB(String MovieTitle, String MovieSerialNumber, int RunningTime, String MovieDescription, double MovieRating) {
        try {
            conn.setAutoCommit(false);
            String use = "USE " + MovieTitle;
            ps = conn.prepareStatement(use);
            ps.executeUpdate();
            String sql1 = "CREATE TABLE info("
                   + " MovieTitle varchar(15),"
                   + " MovieSerialNumber varchar(10),"
                   + " RunningTime int,"
                   + " MovieDescription varchar(30),"
                   + " MovieRating double,"
                   + " ReservationRate double"
                   + ");";
                   ps = conn.prepareStatement(sql1);
                   ps.executeUpdate();
            String sql2 = "insert into " + MovieTitle + ".info (MovieTitle, MovieSerialNumber, RunningTime, MovieDescription, MovieRating, ReservationRate) "
                   + "values (?, ?, ?, ?, ?, ?);";
            ps = conn.prepareStatement(sql2);
            ps.setString(1, MovieTitle);
            ps.setString(2, MovieSerialNumber);
            ps.setInt(3, RunningTime);
            ps.setString(4, MovieDescription);
            ps.setDouble(5, MovieRating);
            ps.setDouble(6, 0.0);	// 예매율은 기본 0.0으로 설정
            ps.executeUpdate();
          conn.commit();
        } catch(Exception e) {
             e.printStackTrace();
             if(conn!=null) {
                try {
                   conn.rollback();
                }catch(Exception e1) {
                   e1.printStackTrace();
                }
             }
          }
    }
	
	public String SearchLastSerialNumber() {   
        String lastSerialNumber = "";
        try {
            conn.setAutoCommit(false);
            String sql1 = "use cinemamanagement;";
            ps = conn.prepareStatement(sql1);
            ps.execute();
            String sql2 = "SELECT MovieSerialNumber FROM movies ORDER BY MovieSerialNumber DESC LIMIT 1";
            ps = conn.prepareStatement(sql2);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {   // 결과 집합의 마지막 요소까지 받으면 = 가장 큰 시리얼넘버 구해짐
                lastSerialNumber = resultSet.getString("MovieSerialNumber");
           }
            conn.commit();
        } catch(Exception e) {
             e.printStackTrace();
             if(conn!=null) {
                try {
                   conn.rollback();
                }catch(Exception e1) {
                   e1.printStackTrace();
                }
             }
          }

        return lastSerialNumber;
    }
	
	public void MovieList1(String mvname) {

        String sql = "delete FROM MOVIES WHERE  movieSerialNumber = ?";
        try {
           conn = getConnection();
           stmt = conn.createStatement();
           String sql1 = "use CINEMAMANAGEMENT";
           stmt.executeUpdate(sql1); // insert delete update

           // select resultSet
           ps = conn.prepareStatement(sql);
           ps.setString(1, mvname);
           ps.executeUpdate();

        } catch (Exception e) {
           System.out.println("DB작업중 문제 발생: " + e.getMessage());
           e.printStackTrace(); // 예외 상세 정보 출력
        }
        System.out.println("목록에서 삭제 완료");
     }
	
    public void MovieList2(String mvname) {

        String sql = "delete FROM MOVIEcode WHERE  movieSerialNumber = ?";
        try {
           conn = getConnection();
           stmt = conn.createStatement();
           String sql1 = "use CINEMAMANAGEMENT";
           stmt.executeUpdate(sql1); // insert delete update

           // select resultSet
           ps = conn.prepareStatement(sql);
           ps.setString(1, mvname);
           ps.executeUpdate();

        } catch (Exception e) {
           System.out.println("DB작업중 문제 발생: " + e.getMessage());
           e.printStackTrace(); // 예외 상세 정보 출력
        }
        System.out.println("영화코드정보에서 삭제 완료");
     }
    
    public void MovieList3(String mvname) {

        String sql = "drop database "+ mvname;
        try {
         
           // select resultSet
           ps = conn.prepareStatement(sql);
           ps.executeUpdate();

        } catch (Exception e) {
           System.out.println("DB작업중 문제 발생: " + e.getMessage());
           e.printStackTrace(); // 예외 상세 정보 출력
        }
        System.out.println("영화데이터베이스 삭제 완료");
     }
    
    public void DelMovieList() {
        ArrayList<Movies> Addr = new ArrayList<>();
        ArrayList<Movies> All = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        try {
           conn = getConnection();
           stmt = conn.createStatement();
           String sql1 = "use cinemamanagement";
           stmt.executeUpdate(sql1);

           String sql = "select * from movies";
           rs = stmt.executeQuery(sql);

           while (rs.next()) {
              String name = rs.getString("MovieTitle");
              String serial = rs.getString("MovieSerialNumber");
              String gerne = rs.getString("Genre");
              String re = rs.getString("ReleaseDate");
              String wd = rs.getString("WithdrawDate");
              String ss = rs.getString("ScreeningStatus");

              Movies mov = new Movies(name, serial, re, wd, gerne, ss);
              Addr.add(mov);

           }

        } catch (Exception e) {
        }

        for (Movies list : Addr) {
           System.out.println(list);
        }
        System.out.println("삭제 메뉴는 1번 돌아가기는 2번");
        int a = Integer.parseInt(sc.nextLine());
        if (a == 1) {
           System.out.println("삭제 하실 영화명 입력해 주세요");
           String ss = sc.nextLine();
           for (Movies list : Addr)
              if (list.getMovieTitle().toLowerCase().equals(ss.toLowerCase())) { 
              String ss1 = list.getMovieSerialNumber();
                 MovieList1(ss1);
                 MovieList2(ss1);
                 MovieList3(ss);
                 
                 break;
              } else {
                 System.out.println("잘못된 영화명 입니다");
                 break;
              }
        } else if (a == 2) {
        } else {
           System.out.println("잘못된 입력 입니다");
        }
     }
	
    public int GetTotalHallSeat(String name) {

        // 해당 영화의 상영기간 중 전체 예매 좌석 수
        
        int i=0;
        try {
           // DB 접속
           String dbinit = "USE cinemamanagement";
           stmt = conn.createStatement();
           stmt.executeUpdate(dbinit);
           
           LocalDate currentDate = LocalDate.now();
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
           String todayDate = currentDate.format(formatter);
           
           // 총 좌석 수 가져오기
           String sql = "SELECT a.t1, b.t2, c.t3 FROM (SELECT COUNT(*)*81 t1 FROM timetablet1" + todayDate +" WHERE Hall1Movietitle = '"+name+"') a,"
                 + "(SELECT COUNT(*)*54 t2 FROM timetablet2" + todayDate + " WHERE Hall2Movietitle = '"+name+"') b,"
                       + "(SELECT COUNT(*)*36 t3 FROM timetablet3" + todayDate + " WHERE Hall3Movietitle = '"+name+"') c";
           ResultSet rs = stmt.executeQuery(sql);         

           
           // 각 관별로 가져온 좌석 수 배열에 넣기
           while(rs.next()) {
              i = rs.getInt("t1") + rs.getInt("t2") + rs.getInt("t3");
           }
           return i;
              
        } catch(Exception e) {
           System.out.println("Error: " + e.getMessage());
           e.printStackTrace();
        }
        return i;
     }
	
    public int GetReservSeat(String a) {
		// ==== DB 작업 ====
		// 해당 영화의 예매 좌석 수 가져오기
		int num =0;
		try {
			stmt = conn.createStatement();
			
			//SQL 문 입력
			//DB 접속
			String sql1 = "USE consumerinfo";
			stmt.executeUpdate(sql1);
			// 좌석 수 가져오기
			String sql = "SELECT COUNT(SeatNumber) AS num FROM bookingstatus WHERE MovieTitle = '"+ a + "'";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			num = rs.getInt("num");
			rs.close();
		} catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		return num;
	}
}
