package projectmovie;
import java.util.*;

import com.mysql.cj.util.PerVmServerConfigCacheFactory;


import java.time.*;
import java.time.format.DateTimeFormatter;

public class MovieMethod {
	
	Scanner sc = new Scanner(System.in);
	LocalDate currentDate = LocalDate.now();
	LocalTime currenttime = LocalTime.now();
	
	SQL_CM cm = new SQL_CM();
    SQL_MM mm = new SQL_MM();
    SQL_RM rm = new SQL_RM();
    SQL_SM sm = new SQL_SM();
	
	
	public ArrayList<Movies> MoviePrint() {
		ArrayList<Movies> arr = mm.MovieList();
		System.out.println(arr.toString());
		return arr;
	}
	
	
	public void NMT() {
		   ArrayList<Movies> arr =MoviePrint();	
			//영화목록을 출력하는 자바메서드.
		   
			ArrayList<String> arr1 = new ArrayList<>();
		  //ArrayList<Movies> arr에서 영화이름을 get 해 리스트에 담아 사용자 입력과 대조하여
			// 잘못된 입력을 방지.
		 
		  printCalendar(currentDate.getYear(),currentDate.getMonthValue());
		  //달력을 출력하는 메서드 사용자의 편의를 위해 출력하였다.
			 
				System.out.println("상영하실 날짜를 두자리로 입력하세요");
				String day = sc.nextLine();
			// 상영등록 할 날짜를 입력받는다.(오류사전검증)
				
				
				System.out.print("상영관을 입력하세요 : ");
				   	String hall = sc.nextLine();
				    // 상영등록할 상영관을 입력받는다(오류사후검증)
				     
				      String year = String.valueOf(currentDate.getYear());
				      String month = String.format("%02d", currentDate.getMonthValue()); // 월을 항상 두 자리 숫자로 포맷
				      String datecode = year+month+day;
				      String timecode = month+day;
				      //테이블 생성 및 구분을 위해 (현재)년도, 월, (사용자입력)일 을 조합하여
							//날짜 코드를 생성한다
//				      HashMap<Integer,Integer> arr2 =SQ.timeTable_pre(datecode,hall);
//				      Set s = arr2.entrySet();
//				      System.out.println(s);
		   
		   String movietitle ;
		   	  while(true) {
		   		  System.out.println("영화제목 입력하세요");
		   		 
		   		  movietitle = sc.nextLine();
		   		 
		   		  for(int i = 0 ; i<arr.size(); i++) {
		   			arr1.add(arr.get(i).getMovieTitle());
		   		  }
		   		
		   		  if(arr1.contains(movietitle)) {
		   			break;  
		   		  }else {
		   			 System.out.println("올바른 영화제목입력하세요");
		   		  }  
		   	  }
		   //상영등록 할 영화를 입력받는다(오류사전검증)
		   	
		
	 String time;
		   while(true) {	   
			   try {
				  System.out.print("상영시작시간을 입력하세요 : ");
				 time = sc.nextLine();
		   		 int timecheck = Integer.parseInt(time);
		         
		         if (timecheck >= 0 && timecheck <= 1440) {
		        	 break;    
		         } else {
		             System.out.println("시작 시간은 0에서 1440 사이의 숫자여야 합니다.");
		             
		         }
		     } catch (NumberFormatException e) {
		         
		    	 System.out.println("유효한 숫자를 입력해주세요.");
		     }
		   }//end of while
		   //상영등록 할 시간을 입력받는다(오류사전검증)
		   
		   
		  
					sm.CreatetimeTB(datecode, movietitle, timecode, hall);
		      //cinemamanagement<database>
					//TimeTablet1(datecode)<table>
					//TimeTablet2(datecode)<table>
					//TimeTablet3(datecode)<table>
					//3개의 테이블을 생성한다.
					sm.SaveTimeTB(datecode,movietitle, time, hall);
		      //SQ.CreatetimeTB로 생성한 테이블 중에서 hall코드(상영관을 입력받음)를 
					//이용하여 이름이 일치하는 테이블에 time(사용자 입력)과 ,movietitl(사용자 입력) 
					//정보를 저장함  
					sm.CreateSeatTB(movietitle,hall, time,timecode);
		      //영화등록하기 단계에서 생성된 영화이름<database>
					//T(hall)(timecode)(time)<table>
					//1개의 테이블을 생성한다(해당영화 상영시점의 좌석테이블)
					sm.copy(movietitle,hall, time, timecode);
					//hall<database>.hall(hall)<table>
					//좌석 기준 정보테이블 내용을 상영관을 기준으로 
					//T(hall)(timecode)(time)<table>에 복사한다.
					System.out.println("상영시간표 등록 완료되었습니다.");
		   }//end of NMT
	
	
	public void printCalendar(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);

        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);

        // Print the headings of the calendar
        System.out.println("Sun Mon Tue Wed Thu Fri Sat");

        // Print the spaces before the first day of the month
        for (int i = 1; i < firstDayOfMonth; i++) {
            System.out.print("    ");
        }

        // Print the days of the month starting from 1
        for (int i = 1, dayOfWeek = firstDayOfMonth; i <= daysInMonth; i++, dayOfWeek++) {
            System.out.printf("%3d ", i);

            // Break the line at the end of the week
            if (dayOfWeek % 7 == 0) {
                System.out.println();
            }
        }

        // Print a newline at the end if the last day is not Saturday
        if ((firstDayOfMonth - 1 + daysInMonth) % 7 != 0) {
            System.out.println();
        }
    }//end of printCalendar
	
	
	public void timeTablecheck() {
		
    	System.out.println("조회하실 월과 날짜를 네자리로 입력해주세요");
    	String year = String.valueOf(currentDate.getYear());
    	String datecode = sc.nextLine();
    	
    	
    	System.out.println("조회하실 상영관을 선택하세요");
    	String hall = sc.nextLine();
	    HashMap<Integer,String>	arr = sm.timeTablecheck(datecode,hall);
    	
    	Set set =arr.entrySet();
    	System.out.println(set.toString());
    }//end of timeTablecheck
	
	
	public void timeTable_check_and_Delete() {

    	System.out.println("수정하실 월과 날짜를 네자리로 입력해주세요");
    	
    	String datecode = sc.nextLine();
    	
    	
    	System.out.println("수정하실 상영관을 선택하세요");
    	String hall = sc.nextLine();
    	HashMap<Integer,String> arr = sm.timeTablecheck(datecode,hall);
    	Set set =arr.entrySet();
    	
    	System.out.println(set.toString());
    	
    	System.out.println("삭제하실 영화의 시간을 입력해주세요");
    	int time = sc.nextInt();
    	
    	String movietitle = arr.get(time);
    	
    	sm.deleteTimeTB(hall,datecode,time,movietitle);
	}

	
    public void RegiMovie() {
        StringBuffer SB = new StringBuffer();
        
        System.out.println("새로 등록할 영화의 이름 : ");
        String MovieTitle = sc.nextLine();
        
        String str1 = mm.SearchLastSerialNumber();// 가장 최근 시리얼넘버 구하기
        if(str1 == "") {
        	str1 ="0";
        }
        String str2 = str1.replace("A", "");
        int int1 = Integer.parseInt(str2);
        int1++;
        String MovieSerialNumber = String.format("A%03d", int1);
        System.out.printf("해당 영화의 시리얼넘버는 [%s]입니다.\n", MovieSerialNumber);
        
        System.out.println("영화의 장르 : ");
        String Genre = sc.nextLine();
        
        System.out.println("상영 시작날짜 (Ex. 20230101) : ");
        String var1 = sc.nextLine();
        SB.append(var1);
        SB.insert(6, "-");
        SB.insert(4, "-");
        String ReleaseDate = SB.toString();
        SB.setLength(0);
        
        System.out.println("상영 종료날짜 (Ex. 20230101) : ");
        String var2 = sc.nextLine();
        SB.append(var2);
        SB.insert(6, "-");
        SB.insert(4, "-");
        String WithdrawDate = SB.toString();
        
        System.out.println("상영상태 (true / false) : ");
        boolean ScreeningStatus = Boolean.parseBoolean(sc.nextLine());

        // (5-2 호출) cinemamanagement > movies에 새로운 영화 행 추가
        mm.RegiMoviecode(MovieTitle, MovieSerialNumber);
        mm.RegiMovie(MovieTitle, MovieSerialNumber, Genre, ReleaseDate, WithdrawDate, ScreeningStatus);
        
        // (5-3 호출) 새로운 영화 DB 생성
        mm.CreateMovieDB(MovieTitle);
        
        System.out.println("러닝타임 (분 단위로 숫자만 입력) : ");
        int RunningTime = sc.nextInt();
        sc.nextLine();
        
        System.out.println("영화 설명 : ");
        String MovieDescription = sc.nextLine();
        
        System.out.println("영화 평점 (ex. 5.0) : ");
        Double MovieRating = sc.nextDouble();
        sc.nextLine();
        
        // (5-4 호출) 새로 만든 DB에 info 테이블 만들고 영화 세부정보 추가
       
        mm.CreateInfoTB(MovieTitle, MovieSerialNumber, RunningTime, MovieDescription, MovieRating);
        System.out.printf("[%s] 영화가 신규 등록되었습니다.\n", MovieTitle);
       
     }

    public void DelMovieList() {
    	mm.DelMovieList();
    }
   
 
    public void Reservationinfo() {
    	rm.Reservationinfo();
    }
    
    public void check() {
        System.out.println("회원은 1번 비회원은 2번");
        int num = Integer.parseInt(sc.nextLine());
        if (num == 1) {
           member();
        } else if (num == 2) {
           nonMb();
        } else {
           System.out.println("잘못 입력 하셧습니다");
        }

     }

     public void member() {
        System.out.println("ID를 입력해 주세요");
        String name = sc.nextLine();
        cm.find(name);
     }

     public void nonMb() {
        System.out.println("예매 번호 또는 전화번호를 입력해 주세요\nex).010-1234-5678");
        String name = sc.nextLine();
        cm.nonfind(name);
     }
     
     public int runningtime() {
    	 System.out.println("영화이름");
    	 String a = sc.nextLine();
    	 int b = rm.runningtime(a);
    	 return b;
     }
 
     public  void GetRating() {
         ArrayList<String> arr = cm.AllMovie();
         Iterator it = arr.iterator();
         int i=0;
         while(it.hasNext()) {
            i++;
            System.out.println(i+ ". "+it.next());
         }
         System.out.println("====================================================");
          System.out.println("예매율을 확인하실 영화를 선택해주세요");
          String name = sc.nextLine();
           int reservseat = mm.GetReservSeat(name);
           int total = mm.GetTotalHallSeat(name);
           
           double rating = reservseat/total;
          System.out.printf("[%s]의 현재 예매율은 %.5f입니다.\n", name, rating);
       }
     
     public void cancleTicket() {
    	 
    	 
    	 System.out.println("회원 예매취소 : 1 \n 비회원 예몌취소 : 2");
    	 int a = sc.nextInt();
    	 switch(a) {
    	 
    	 case 1:
    	System.out.println("아이디를 입력해 주세요");
    	sc.nextLine();
    	String Id = sc.nextLine();
    	ArrayList<BookingStatus> arr = cm.find(Id);
    	System.out.println("예매 취소하실 정보의 번호를 입력해주세요");
    	int b = sc.nextInt();
    	cm.MemberCancle(arr.get(b-1));
    	 break;
    	 
    	 case 2:
    	System.out.println("시리얼넘버 또는 예매하신 전화번호를 입력해주세여");
    	sc.nextLine();
    	String NonID = sc.nextLine();
    	ArrayList<BookingStatus> arr1 =cm.nonfind(NonID);
    	System.out.println("예매 취소하실 정보의 번호를 입력해주세요");
    	int c = sc.nextInt();
    	cm.Guestcancle(arr1.get(c-1));
    	 break;
    	 
    	 }
    	 
    	 
    	 
     }
     
     public ArrayList<Sign> allMemberList() {
         ArrayList<Sign> arr = cm.allMember();
         	
         for(Sign a : arr) {
         		System.out.println(a.toString());
         	}
         System.out.println(arr.toString());
         return arr;
      }
     
     
     
     ////////////////////////////////사용자 메서드///////////////////////////////////// 
 
    
    // 1-1. 영화 예매하기
    public void BuyTicket() {
  	  LocalDate today = LocalDate.now();
  	  String todayDate = today.toString().replace("-", "");	
    	
  	printReservation(today,todayDate);
    	
//======= 예매정보 입력받기
  	  System.out.println("예매할 영화를 입력하세요. : ");
  	  String selectedMovie = sc.nextLine();
  	  System.out.println("예매할 날짜를 4자리로 입력하세요 ex)1215 -> 12월15일");
  	  String selecteddate = sc.nextLine();
  	  System.out.println("예매할 시간을 입력하세요. (ex. 9시 = '0900' 입력) : ");
  	  String selectedTime = sc.nextLine();
  	  
  	  String x = selectedTime.substring(0,2);
  	  String y = selectedTime.substring(2,4);
  	  int x1 = Integer.parseInt(x);
  	  int y1 =Integer.parseInt(y);
  	  int xy = (x1*60) + y1;
  	  
  	  String timecode = String.valueOf(xy);
  	  String datetimecode = selecteddate+timecode;
  	  String ReservationTimeCode =selecteddate + timecode;
  	  
  	  System.out.println("상영관을 숫자로 입력하세요. : ");
  	  int selectedHall = sc.nextInt();
  	  
  	  System.out.println("예매할 인원수를 숫자로 입력하세요. : ");
  	  int selectedNum = sc.nextInt();
  	  sc.nextLine();
  	  
  	  // 좌석도 출력 필요!
  	  
  	 System.out.println("예매할 좌석을 입력해 주세요. : ");
     ArrayList seatList = new ArrayList();
     for (int i =0;i<selectedNum;i++) {
     String selectedSeat = sc.nextLine();
     seatList.add(selectedSeat);
     }
			
     // "A1 A2 A3"
//  	  String[] selectedSeatArr = selectedSeat.split(" ");	// {A1, A2, A3}
  	  
  	  
  	  
//======== 예매방법 선택
  	  System.out.println("1. 기존회원예매 / 2. 신규 회원가입 후 예매 / 3. 비회원예매 \n 번호 입력 : ");
  	  int reservationMethod = sc.nextInt();
  	  sc.nextLine();
  	  
  	  switch(reservationMethod) {
//1. 기존회원예매
  	  	case (1): 
  	  		
  	  		Sign member = null;
  	  		String inputId = "";
  	  		String inputPass = "";
  	  		
  	  		while(true) {
	    	  		System.out.println("회원 아이디 입력 : ");
	    	  		inputId = sc.nextLine();
	    	  		
	    	  		System.out.println("비밀번호 입력 : ");
	    	  		inputPass = sc.nextLine();
	    	  		
	    	  		member = cm.isMember(inputId, inputPass);		// (1-4) 기존 회원이 아이디 비번 입력 시, 올바른지 확인
	    	  		if (member != null) {
	    	  			break;
	    	  		} else {
	    	  			System.out.println("아이디 또는 비밀번호가 틀렸습니다. 다시 입력하세요.");
	    	  			
	    	  		}
  	  		}
  	  		
  	  		String PhoneNumber = member.getPhoneNumber();
  	  		
  	  		LocalDateTime now = LocalDateTime.now();
		        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"); // 시리얼넘버
		        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");	//  OrderTime
		        String formattedDateTime1 = now.format(formatter1);
		        String formattedDateTime2 = now.format(formatter2);
		        
  	  		String SerialNumber = formattedDateTime1;
  	  		String OrderTime = formattedDateTime2;
  	  		
  	  		int Payment = selectedNum * 10000;			// 한명당 1만원으로 계산했습니다.
  	  		
  	  		boolean TicketStatus = true;		
  	  	// (1-5) saveReservation() : 입력받은 정보를 BookingStatus 테이블에 저장
  	  		rm.saveReservation(inputId, inputPass, PhoneNumber, SerialNumber, selectedMovie, selectedHall,ReservationTimeCode, seatList, selectedNum, OrderTime, Payment, TicketStatus);
  	  		cm.bookingSeatTable(selectedMovie, seatList, SerialNumber, selectedHall, datetimecode);
  	  		System.out.println();
  	  		System.out.println("회원 예매가 완료되었습니다.");
  	  		
  	  		break;
  	  		
//2. 신규회원가입 후 예매     	  	
  	  	case (2) : 
  	  		  System.out.println("========= 회원가입 =========");
		      	  String Id = "";
		      	  boolean duplicate = true;
		      	  
		      	  while (duplicate == true) { // 아이디 중복체크
		  	          System.out.println("아이디 (10글자 이하) : ");
		  	          Id = sc.nextLine();
		  	          ArrayList<String> idArr = cm.CheckId(Id);		// (4-3) 아이디 중복확인
		  	              	  
		  	          for (String data : idArr) { 
		  	        	  if (Id.equals(data)) {
		  	        		  System.out.println("이미 존재하는 아이디입니다.");
		  	        		  duplicate = true;
		  	        		  break;
		  	        	  } else {
		  	        		  duplicate = false;
		  	        	  }
		  	          }
		      	  }
		            
		            System.out.println("비밀번호 (10글자 이하) : ");
		            String Pass = sc.nextLine();
		            
		            System.out.println("이름 : ");
		            String InfoName = sc.nextLine();
		            
		            System.out.println("전화번호 (숫자만 입력) : ");	//  sql에서 테이블 만들 때 전화번호 10자리 => 11자리로 바꿨습니다.
		            PhoneNumber = sc.nextLine();
		            
		            boolean b = cm.RegiMember(Id, Pass, InfoName, PhoneNumber);		// (4-2) 회원가입 정보 DB에 추가
		            if (b = true) {
		          	  System.out.println("회원가입이 완료되었습니다.");
		            }
		            
	    	  		member = cm.isMember(Id, Pass);		// (1-4) 기존 회원이 아이디 비번 입력 시, 올바른지 확인
	    	  		if (member == null) {
	    	  			System.out.println("아이디 또는 비밀번호가 틀렸습니다. 다시 입력하세요.");
	    	  			
	    	  		}
		            
	    	  		System.out.println("신용카드로 계산중 ...");
	    	  		
	    	  		PhoneNumber = member.getPhoneNumber();
	    	  		
	    	  		now = LocalDateTime.now();
	  		        formatter1 = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"); // 시리얼넘버는 일단 이렇게 저장했습니다. 
	  		        formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");	//  OrderTime
	  		        formattedDateTime1 = now.format(formatter1);
	  		        formattedDateTime2 = now.format(formatter2);
	  		        
	    	  		SerialNumber = formattedDateTime1;
	    	  		OrderTime = formattedDateTime2;

	    	  		Payment = selectedNum * 10000;			// 한명당 1만원으로 계산했습니다.
	    	  		
	    	  		TicketStatus = true;	
	    	  	// (1-5) saveReservation() : 입력받은 정보를 BookingStatus 테이블에 저장
	    	  		rm.saveReservation(Id, InfoName, PhoneNumber, SerialNumber, selectedMovie, selectedHall,ReservationTimeCode, seatList, selectedNum, OrderTime, Payment, TicketStatus);
	    	  		cm.bookingSeatTable(selectedMovie, seatList, SerialNumber, selectedHall, datetimecode);
	    	  		System.out.println("신규가입 후 회원예매가 완료되었습니다.");
	    	  		break;
	    	  		
	    	  		
//3. 비회원예매	    	  		
				case (3) :		
					System.out.println("====== 비회원예매 ======");
					System.out.println("예매 고객 이름 : ");
					String nonMemberName = sc.nextLine();
					
					System.out.println("예매 고객 전화번호 : ");
					String nonMemberPhoneNumber = sc.nextLine();
	    	  		
					
	    	  		
					now = LocalDateTime.now();
	  		        formatter1 = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"); // 시리얼넘버는 일단 이렇게 저장했습니다. 
	  		        formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");	//  OrderTime
	  		        formattedDateTime1 = now.format(formatter1);
	  		        formattedDateTime2 = now.format(formatter2);
	  		        
	    	  		SerialNumber = formattedDateTime1;
	    	  		OrderTime = formattedDateTime2;

	    	  		Payment = selectedNum * 10000;			// 한명당 1만원으로 계산했습니다.
	    	  		
//	    	  		Id = SerialNumber.substring(9,15);  // id 가 프라이머리 키이기 때문에 난수로 설정해 둬야 오류 발생 x - >12/20 메서드 오버로딩으로 수정
	    	  		
	    	  		TicketStatus = true;	
	    	  		
	    	  		// (1-5) saveReservation() : 입력받은 정보를 BookingStatus 테이블에 저장
	    	  		rm.saveReservation(nonMemberName, nonMemberPhoneNumber, SerialNumber, selectedMovie, selectedHall,ReservationTimeCode, seatList, selectedNum, OrderTime, Payment, TicketStatus);
	    	  		cm.bookingSeatTable(selectedMovie, seatList, SerialNumber, selectedHall, datetimecode);
	    	  		System.out.println("비회원예매가 완료되었습니다.");
	    	  		break;
	    	  		
  	  }

     
  	  // (1-6) saveSeatTable( ) : 좌석도 테이블에도 예매번호 등록하기.
  	  
    }
    

   
    // 4-1. 회원가입 시 필요한 정보 입력받기
    public void RegiMember() {
  	  System.out.println("========= 회원가입 =========");
  	  String Id = "";
  	  boolean duplicate = true;
  	  
  	  while (duplicate == true) { // 아이디 중복체크
	          System.out.println("아이디 (10글자 이하) : ");
	          Id = sc.nextLine();
	          ArrayList<String> idArr = cm.CheckId(Id);	
	              	  
	          for (String data : idArr) { 
	        	  if (Id.equals(data)) {
	        		  System.out.println("이미 존재하는 아이디입니다.");
	        		  duplicate = true;
	        		  break;
	        	  } else {
	        		  duplicate = false;
	        	  }
	          }
  	  }
        
        System.out.println("비밀번호 (10글자 이하) : ");
        String Pass = sc.nextLine();
        
        System.out.println("이름 : ");
        String InfoName = sc.nextLine();
        
        System.out.println("전화번호 (숫자만 입력) : ");	//  sql에서 테이블 만들 때 전화번호 10자리 => 11자리로 바꿨습니다.
        String PhoneNumber = sc.nextLine();
        
        boolean b = cm.RegiMember(Id, Pass, InfoName, PhoneNumber);
        if (b = true) {
      	  System.out.println("회원가입이 완료되었습니다.");
        }
    }
    

    
    public void printReservation(LocalDate tomorrow,String tomorrowDate) {
  	  ArrayList<String> trueMovieList = cm.AllMovie(); // (1-2) AllMovie() : 현재 상영중인 영화 리스트 반환
    	  
//	  printCalendar(currentDate.getYear(),currentDate.getMonthValue());
	  //달력을 출력하는 메서드 사용자의 편의를 위해 출력하였다.

	 
	 // 2023-01-01 형식을 20230101 형식으로 바꿈
//	  System.out.println("예매하실 날짜를 선택하세요");
//	  String day = sc.nextLine();
	  
	  String TBName = "Timetablet";				// Timetable20230101 -> Timetable"t1"20230101 .홀정보를 나타내는 테이블 명이 빠짐.
	  											//홀 넘버를 뒤에서 받기때문에 todayDate를 매개변수로 추가. -> 12월 내에서 예매 가능하도록 수정 예정.
	  
	  int hallNum = 1;
	 
//======= 상영중인 모든 영화, 모든 관, 모든 시간을 출력   (잔여좌석 출력하는 메서드 추가로 필요)
	  System.out.println();
	  System.out.println("============"+tomorrow+"============");
	  for (String Movie : trueMovieList) {
		  System.out.printf("\n ====== 영화명 : [%s] ======\n", Movie);

		  while (hallNum < 4) {
			  String hallNum2 = Integer.toString(hallNum);
			  ArrayList<String> timeList = cm.timeList(TBName, Movie, hallNum2,tomorrowDate);	// (1-3) timeList() : 해당 영화의 상영관별 상영시간 출력	
    		  System.out.printf("%d관 : ", hallNum);
    		  for (String time : timeList) {
    			  
    			  if (time == null) {
    				  continue;
    			  } else {
    				 System.out.print(" "+time +" |");
    			  }
    			  
    		  }
    		  System.out.println();
    		  hallNum ++;
		
		  }
		
		  hallNum = 1;	// 다음 for문 돌 때 초기화되어야 함
	  }
	System.out.println();
	  System.out.println("다음날 상영정보 보기 : 1 \n 예매하기 : 2");
	  int a = sc.nextInt();
	  
	  if(a == 1) { 
		  LocalDate tomorrow1 =tomorrow.plusDays(1);
		  String tomorrowDate1 = tomorrow1.toString().replace("-", "");
		  printReservation(tomorrow1,tomorrowDate1);
	  }else {
		  sc.nextLine();
	  }
  
  
  }
      
    
}// end of MovieMethod

