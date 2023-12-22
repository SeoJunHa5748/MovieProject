package projectmovie;
import java.util.Scanner;

public class UI {
   
   public void Run() {
      Scanner scan = new Scanner(System.in);
      Prompt prompt = new Prompt();
      MovieMethod mm = new MovieMethod();
   
      
      String input = "";
      int num = 0;
      
main :   while(true) {
         prompt.Prompt_main();
         num = Integer.parseInt(scan.nextLine());
         
         
         switch(num) {
         case 1:  // 영화 예매
             mm.BuyTicket();           
             break;
             
          case 2: // 예매 확인
            prompt.Prompt_main_CheckReserv();
             mm.check();
             break;
             
          case 3: // 예매 취소
             prompt.Prompt_main_cancelTicket();
             mm.cancleTicket();
             break;
             
          case 4: // 회원 가입
             prompt.Prompt_main_RegiMember();
             mm.RegiMember();
             break;
             
          case 5: // 죵료하기
             break main;      // 완전히 종료
            
         case 999:
admin :   while(true) {
        	prompt.Prompt_manager();
            num = Integer.parseInt(scan.nextLine());


            
        	 switch(num) {
            case 1: // 영화등록
            	prompt.Prompt_manager_RegiMovie();
            	mm.RegiMovie();
               break;
            case 2: //영화삭제
            	prompt.Prompt_manager_Del();
            	mm.DelMovieList();
               break;
            case 3: //영화 상영등록
            	prompt.Prompt_manager_NMT();
            	mm.NMT();
               break;
            case 4: //상영등록한 영화 수정
            	prompt.Prompt_manager_delNMT();
            	mm.timeTable_check_and_Delete();
               break;
            case 5:   // 영화목록보기
                prompt.Prompt_manager_MovieList( );
                mm.MoviePrint();      // toString 바꿔야함
                break;
                
             case 6:   // 회원목록보기
                prompt.Prompt_manager_ConsumerList( );
                mm.allMemberList();   
                break;
                
             case 7:   // 예매율 확인
                prompt.Prompt_manager_RRTotal( );
                mm.GetRating();
                break;

            
            case 8:
                break admin;
            case 9:
               default :
                  System.out.println("잘못 누르셨습니다.");
                  break;
            }
        
		}		
         
      }
      
   }
   }
}
