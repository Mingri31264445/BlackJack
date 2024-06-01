//到底在難什麼我要瘋了  2024/6/2 am 01:06
//隨機抽卡搞定 2024/6/2 am 02:23
//強迫開始的迴圈完成了 2024/6/2 am 02:40
//終於搞定有戲開始抽2張牌 2024/6/2 am 03:05
//搞定要牌系統，終於可以睡了 2024/6/2 am 03:45
//打完註解 2024/6/2 am 03:55

/*明天:加上莊家和對戰績分系統 */

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

        //當點數>21時用來結束88行的do-while
        if (point > 21) {
            System.out.println("點數超過21,遊戲結束!");
            gameOver = true;
        }
    }
}

class Player extends Card{

    //開始遊戲時自動抽兩張牌
    void Licensing(){
        askForCard();
        askForCard();
        show();
    }

    void show(){
        System.out.println("總計的點數是:"+point);
    }
}

public class BlackJack {
    //主程式
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        
        Player player = new Player(); 

        //強迫開始的迴圈
        int open;
        do{
        System.out.println("是否開始21點遊戲!(要:1,不要:2)");
        open = sc.nextInt();
            if(open ==1)
                player.Licensing();
            else if(open ==2)
                System.out.println("真的不玩嗎?");
            else
                System.out.println("輸入錯誤，請重試");
        }while(open !=1);

        //重複要牌
        int get;
        do{
        System.out.println("請問要再來一張牌嗎? (要:1,不要:2)");
        get = sc.nextInt();

        if(get == 1){
            player.askForCard();
            player.show();
        }
        else if (get == 2) {
            System.out.println("遊戲結束，最終點數是: " + player.point);
        }
        else{
            System.out.println("輸入錯誤，請重試");
        }
        }while (get !=2 && !player.gameOver);

        sc.close();//聽說在主程式的最後放這行sc不會有問題，原理我不知道:)
    }
}