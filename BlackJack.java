//我好累我真的好累我超想睡覺
//為甚麼我的VsCode沒辦法用F5??
//一直找主程式的Run我快瘋了
//格式有可能會跑，我已經裝了自動調格式的套件了，但他很久沒生效了:(
import java.util.Scanner;

//牌組
class Card{
    int point = 0;
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
        point += (card % 13) +1;

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

        //當點數>21時用來結束174行的do-while迴圈
        if (point > 21) {
            System.out.println("點數超過21,遊戲結束!");
            gameOver = true;
        }
    }

    //用來重製第二輪莊家、玩家的點數(point)和是否爆牌(gameOver)
    void reset() {
        point = 0;
        gameOver = false;
    }
}
//玩家
class Player extends Card{
    int score = 10;

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

    //莊家抽牌邏輯
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
        
        System.out.println("歡迎遊玩21點遊戲!");
        System.out.println("請輸入遊玩人數:");//將使用者輸入的值放進numOfPlayers

        //這東西出現很多次，很重要，邏輯我不知道
        //每一次需要使用者輸入就會用來排除數字以外的輸入
        while (!sc.hasNextInt()) {
            System.out.println("輸入錯誤,請輸入玩家人數才能開始遊玩!");
            sc.next(); //這可以清除無效的輸入
        }
        int numOfPlayers = sc.nextInt();


        //建立物件陣列
        Player[] player = new Player[numOfPlayers];
            //用for迴圈建立玩家物件
            for (int i = 0; i < numOfPlayers; i++) {
                player[i] = new Player();
            }
        Dealer dealer = new Dealer();//莊家物件

        //強迫開始的迴圈
        int open;
        int rounds = 0;
        boolean playGame = true;

        do{
            System.out.println("開始遊戲!:1");
            System.out.println("退出遊戲:2");//結果輸出放到open
            //使用 while 迴圈檢查輸入是否為整數
            while (!sc.hasNextInt()) {
                System.out.println("輸入錯誤,請輸入1或2:");
                sc.next(); //這可以清除無效的輸入
            } 
            open = sc.nextInt();
                
            if(open == 1){//當open = 1開始遊戲
            
            //用來重製第二輪莊家、玩家的點數(point)和是否爆牌(gameOver)
            dealer.reset();
            for (int i=0;i<player.length;i++) {
                player[i].reset();
            }

            dealer.showOneCard();//莊家顯示其中一張牌

            //玩家回合開始
            for(int i=0;i<player.length;i++){
                System.out.println();
                System.out.println("玩家"+(i+1)+"的回合:");
                player[i].Licensing();//呼叫開局方法


                int get;//在do-while外宣告while中才可以使用
                do{
                    System.out.println("");
                    System.out.println("請問要再來一張牌嗎?");
                    System.out.println("要:1");
                    System.out.println("不要:2");
                    while (!sc.hasNextInt()) {
                        System.out.println("輸入錯誤,請輸入1或2:");
                        sc.next();
                    }
                    get = sc.nextInt();
                
                    if(get == 1){//玩家要牌
                        System.out.print("玩家"+(i+1));
                        player[i].askForCard();
                        player[i].show();
                    }
                    else if (get == 2) {//玩家不要牌
                        System.out.println("遊戲結束，最終點數是: " + player[i].point);
                    }
                    else{
                        System.out.println("輸入錯誤,請輸入1或2:");
                    }

                }while (get !=2 && !player[i].gameOver);//爆牌自動結束

            }
            
            //玩家都結束之後，莊家開始
            System.out.println("");
            System.out.println("莊家的回合:");
            dealer.play();

            for(int j=0;j<player.length;j++){
                //判斷輸贏
                if (player[j].gameOver) {
                    System.out.println("");
                    System.out.println("玩家"+(j+1)+"爆牌，莊家勝利!");
                    player[j].score -= 2;
                }
                else if (dealer.gameOver) {
                    System.out.println("");
                    System.out.println("莊家爆牌，玩家"+(j+1)+"勝利!");
                    player[j].score += 2;
                }
                else {
                    if (player[j].point > dealer.point) {
                        System.out.println("");
                        System.out.println("玩家"+(j+1)+"勝利!");
                        player[j].score += 2;
                    }
                    else if (player[j].point < dealer.point) {
                        System.out.println("");
                        System.out.println("莊家勝利!");
                        player[j].score -= 2;
                    }
                    else{
                        System.out.println("");
                        System.out.println("平局!");
                        player[j].score -= 1;//平局莊家大很正常吧:)//-1分
                    }
                }
            }

            //每一輪結束後顯示玩家當前積分
            for (int k=0;k<player.length;k++) {
                System.out.println("玩家"+ (k+1) +"的當前積分: " + player[k].score);
            }

            rounds++;//輪次+1

            //為了不要讓遊戲無限輪迴，5局強制結束
            if (rounds >= 5) {
                System.out.println("遊戲結束!");
                open = 2; //強制結束遊戲
            }

            //如果有玩家的積分歸0直接結束
            //just like日麻被轟飛的概念
            for (Player p : player) {
                if (p.score <= 0) {
                    playGame = false;
                    break;
                }
            }
            
            //詢問使用者是否開始第二輪遊戲
            if (playGame && rounds < 5) {
                System.out.println("是否繼續下一輪遊戲?");
                System.out.println("要:1");
                System.out.println("不要:1以外的數字");
                while (!sc.hasNextInt()) {
                    System.out.println("輸入錯誤,請輸入數字:");
                    sc.next();
                }
                int continueGame = sc.nextInt();

                    if(continueGame == 1){
                        playGame = true;
                    }
                    else {
                        playGame = false;
                        //playGame = false會結束遊戲喔注意剛開始的do-while是有條件會結束執行的
                        //264行
                    }
            }

            }
            else if(open ==2){
                System.out.println("真的不玩嗎?");//情緒勒索
            }
            else{
                System.out.println("輸入錯誤,請輸入1或2:");
            }
        }while(open !=1 || playGame == true);

        System.out.println("最終積分如下:");
            for (int k = 0; k < player.length; k++) {
                System.out.println("玩家" + (k + 1) + "的最終積分: " + player[k].score);
            }

        //找出最高分的玩家顯示
        int highestScore = player[0].score;
        int winnerIndex = 0;
        boolean tie = false; //記錄是否有平局

            for (int k = 1; k < player.length; k++) {
                if (player[k].score > highestScore) {
                    highestScore = player[k].score;
                    winnerIndex = k;
                    tie = false; //重置平局
                } else if (player[k].score == highestScore) {
                    tie = true; // 有平局
                }
            }

        if (tie) {//tie = true
            System.out.println("遊戲結束! 平局，無獲勝者!");
        } else {
            System.out.println("遊戲結束! 玩家" + (winnerIndex + 1) + "獲勝，最終得分是: " + highestScore);
        }

        sc.close();//聽說在主程式的最後放這行sc不會有問題，原理我不知道:)
    }
}