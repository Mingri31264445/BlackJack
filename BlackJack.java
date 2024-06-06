//我好累我真的好累
import java.util.Scanner;

//牌組
class Card{
    int point=0;
    boolean gameOver = false;
    String[] suits = {"愛心", "黑桃", "菱形", "梅花"};
    String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

    //要牌的方法
    void askForCard(){

        //從0到52抽一個，除13找花色，除13的餘數是代表1到13其中一個數
        int card = (int) (Math.random() * 52);
        String suit = suits[card / 13];
        String rank = ranks[card % 13];
        
        System.out.println("抽到的卡片是: " + suit + rank);

        //計算總點數
        point+=(card % 13)+1;

        //排除A可以是1或11和JQK是10的情況
        if (rank.equals("A")) {
            point += 10;
            if (point > 21) {
                point -= 9;
            }
        } 
        else if (rank.equals("J")) {
            point -= 1;
        } 
        else if (rank.equals("Q")) {
            point -= 2;
        } 
        else if (rank.equals("K")) {
            point -= 3;
        }

        //當點數>21時用來結束121行的do-while迴圈
        if (point > 21) {
            System.out.println("點數超過21,遊戲結束!");
            gameOver = true;
        }
    }
}
//玩家
class Player extends Card{

    //開始遊戲時自動抽兩張牌
    void Licensing(){
        askForCard();
        askForCard();
        show();
    }

    void show(){
        System.out.println("總計的點數是: "+point);
    }
}

//莊家
class Dealer extends Player {//莊家後來加的，我懶得把show移上去Card，直接繼承Player

    // 顯示第一張牌的方法
    void showOneCard() {
        System.out.print("莊家");
        askForCard(); // 抽取第一張牌
        System.out.print("目前已知莊家");
        show();
    }

    void play() {
        while (point < 17 && !gameOver) {//<17無腦抽
            askForCard();
        }
        show();
    }
}

public class BlackJack {
    //主程式
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        
        //建立物件

        System.out.println("請輸入遊玩人數:");
        while (!sc.hasNextInt()) {
            System.out.println("輸入錯誤,請輸入數字:");
            sc.next(); //這可以清除無效的輸入
        }
        int numOfPlayers = sc.nextInt();
        Player[] player = new Player[numOfPlayers];
        Dealer dealer = new Dealer();


        //強迫開始的迴圈
        int open;
        do{
        System.out.println("是否開始21點遊戲!(要:1,不要:2)");
        //使用 while 迴圈檢查輸入是否為整數
            while (!sc.hasNextInt()) {
                System.out.println("輸入錯誤,請輸入1或2:");
                sc.next(); //這可以清除無效的輸入
            }

            open = sc.nextInt();

            if(open ==1){
                dealer.showOneCard();//莊家顯示其中一張牌
            for(int i=0;i<player.length;i++){
                System.out.println();
                System.out.println("玩家"+(i+1)+"的回合:");
                player[i] = new Player();
                player[i].Licensing();//呼叫開局方法


                int get;//在do-while外宣告while中才可以使用
                do{
                System.out.println("");
                System.out.println("請問要再來一張牌嗎? (要:1,不要:2)");
                    //同87行
                    while (!sc.hasNextInt()) {
                        System.out.println("輸入錯誤,請輸入1或2:");
                        sc.next();
                    }

                get = sc.nextInt();
        
                if(get == 1){
                    System.out.print("玩家"+(i+1));
                    player[i].askForCard();
                    player[i].show();
                }
                else if (get == 2) {
                    System.out.println("遊戲結束，最終點數是: " + player[i].point);
                }
                else{
                    System.out.println("輸入錯誤,請輸入1或2:");
                }
                }while (get !=2 && !player[i].gameOver);//爆牌自動結束

                }

                        System.out.println("");
                        System.out.println("莊家的回合:");
                        dealer.play();

                for(int j=0;j<player.length;j++){
                //判斷輸贏
                if (player[j].gameOver) {
                    System.out.println("");
                    System.out.println("玩家"+(j+1)+"爆牌，莊家勝利!");
                }
                else if (dealer.gameOver) {
                    System.out.println("");
                    System.out.println("莊家爆牌，玩家"+(j+1)+"勝利!");
                }
                else {
                    if (player[j].point > dealer.point) {
                        System.out.println("");
                        System.out.println("玩家"+(j+1)+"勝利!");
                    }
                    else if (player[j].point < dealer.point) {
                        System.out.println("");
                        System.out.println("莊家勝利!");
                    }
                    else{
                        System.out.println("");
                        System.out.println("平局!");
                    }
                }
            }
            }
            else if(open ==2){
                System.out.println("真的不玩嗎?");
            }
            else{
                System.out.println("輸入錯誤,請輸入1或2:");
            }
        }while(open !=1);


        sc.close();//聽說在主程式的最後放這行sc不會有問題，原理我不知道:)
    }
}