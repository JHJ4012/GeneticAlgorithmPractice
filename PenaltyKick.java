package GATest;

import java.util.ArrayList;
import java.util.Collections;

public class PenaltyKick {
   
//   골기퍼와 키커가 있음.
//   키커는 5방향으로 찰 수 있다.(왼위, 왼밑, 중앙, 오위, 오밑)
//   키커는 차기 전에 예비 동작으로 어느 방향으로 찰 것인지 먼저 경고를 함.
//   다음턴에 키커는 그 방향으로 공을 찰 곳이고 골기퍼는 랜덤으로 한 방향으로 몸을 날리게 된다.
//   최초의 골기퍼 객체는 x명이다.
//   키커는 총 y번 공을 차고 그 y개 중 가장 볼을 많이 막은 골기퍼 2명을 선발한다.
//   이 2명의 골키퍼를 교배 x-1마리. 그리고 교배한 x-1마리 중 하나의 돌연변이 1 해서 총 x명 또 선발.
//   위의 과정 z번 반복.
   
//   유전 정보
//   왼위 -> 1
//   왼밑  -> 2
//   중앙 -> 3
//   오위 -> 4
//   오밑 -> 5
   
   final int keeperNum;
   final int kickNum;
   final int gameNum;
   final int geneNum = 5;
   
   ArrayList<String> geneInfo;
   ArrayList<Integer> scoreAll;
   
   int kick;
   int defend;
   int score;
   
   int first;
   int second;
   
   boolean firsttime = true;
   
   public PenaltyKick(int kpN, int kcN, int gM) {	//기본 생성자.
      keeperNum = kpN;
      kickNum = kcN;
      gameNum = gM;
      geneInfo = new ArrayList<String>();
      scoreAll = new ArrayList<Integer>();
      for(int i = 1 ; i <= gameNum; i++) {	//세대 수만큼 반복 시킨다.
         gameControl(i);
      }
   }
   
   public void gameControl(int gM) {	//게임 진행을 책임지는 controler
      System.out.println(gM + "세대!!");
      if(firsttime) {	//1세대일 때.
         for(int i = 0; i < keeperNum; i++) {
            whereDefend();
            for(int j = 0 ; j < kickNum; j++) {
               whereShoot();
               resultKick(i);
            }
            scoreAll.add(score);
            score = 0;
         }
         for(int i = 0 ; i < keeperNum; i++) {
         	System.out.println(i+1 +"번째 골기퍼의 유전 정보 : " + geneInfo.get(i));
            System.out.println(i+1 +"번째 골기퍼의 점수는 "+ scoreAll.get(i));
         }
         firsttime = false;
      }else {	//1세대 이후의 세대들.
         rankKeeper();
         crossOver();
         for(int i = 0; i < keeperNum; i++) {
            for(int j = 0 ; j < kickNum; j++) {
               whereShoot();
               resultKick(i);
            }
            scoreAll.add(score);
            score = 0;
         }
         for(int i = 0 ; i < keeperNum; i++) {
        	System.out.println(i+1 +"번째 골기퍼의 유전 정보 : " + geneInfo.get(i));
            System.out.println(i+1 +"번째 골기퍼의 점수는 "+ scoreAll.get(i));
         }
      }
      
   }

   public void whereShoot() {	//매번 kick을 랜덤 방향으로 찬다.
      kick = (int)(Math.random()*5) + 1;
   }
   
   public void whereDefend() {	//1세대일 때 골키퍼 수만큼 유전자를 랜덤으로 만든다.
	   String[] stArray = new String[geneNum];
	   for(int i = 0; i < geneNum; i++) {
		   stArray[i] = String.valueOf((int)((Math.random()*5) + 1));
	   }
	   String str = "";
	   for(int i = 0 ; i < stArray.length; i++) {
		   str += stArray[i];
	   }
	   geneInfo.add(str);
   }
   
   public void resultKick(int keeper) {	//유전자와 kick의 방향을 비교하여 맞으면 score에 +1씩함.
	   String str = geneInfo.get(keeper);
	   String[] stArr = str.split("");
	   if(Integer.parseInt(stArr[kick-1]) == kick) {
		   score++;
	   }
   }
   
   public void rankKeeper() {	//가장 순위가 높은 둘을 선발.
      int one = 0;
      int secondth = 0;
      for(int i = 1 ; i < scoreAll.size(); i++) {
         if(scoreAll.get(i) >= scoreAll.get(one)) {
            one = i;
         }
      }
      for(int i = 1 ; i < scoreAll.size(); i++) {
         if(scoreAll.get(i) >= scoreAll.get(secondth)) {
            if(i != one) {
               secondth = i;
            }
         }
      }
      first = one;
      second = secondth;
   }
   
   public void crossOver() {	//rankKeeper로 인해 뽑힌 2개의 객체를 교배
      String parent1 = geneInfo.get(first);
      String parent2 = geneInfo.get(second);
      geneInfo.clear();
      scoreAll.clear();
      
      for(int i = 0; i < keeperNum-1; i++) {
         String str = "";
         for(int j = 0 ; j < geneNum;  j++) {
            int r = (int)((Math.random()*2) + 1);
            if(r == 1) {
               str += parent1.charAt(j);
            }else if(r == 2) {
               str += parent2.charAt(j);
            }
         }
         geneInfo.add(str);
      }
      makeMutation();
   }
   
   public void makeMutation() {	//돌연변이 생성.
      
      String str = geneInfo.get(0);
      String[] formut = str.split("");
      int int1 = (int)(Math.random()*geneNum);
      int int2 = (int)(Math.random()*geneNum);
      formut[int1] = String.valueOf((int)(Math.random()*geneNum) + 1);
      formut[int2] = String.valueOf((int)(Math.random()*geneNum) + 1);
      
      
      String mutated = "";
      for(int i = 0; i < formut.length; i++) {
         mutated += formut[i];
      }
      geneInfo.add(mutated);
   }
   
   public static void main(String[] args) {
      PenaltyKick PK = new PenaltyKick(10, 20, 100);	//게임 실행.
   }
}