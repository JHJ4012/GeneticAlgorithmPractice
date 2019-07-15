package GATest;

import java.util.ArrayList;
import java.util.Collections;

public class PenaltyKick {
   
//   ����ۿ� ŰĿ�� ����.
//   ŰĿ�� 5�������� �� �� �ִ�.(����, �޹�, �߾�, ����, ����)
//   ŰĿ�� ���� ���� ���� �������� ��� �������� �� ������ ���� ��� ��.
//   �����Ͽ� ŰĿ�� �� �������� ���� �� ���̰� ����۴� �������� �� �������� ���� ������ �ȴ�.
//   ������ ����� ��ü�� x���̴�.
//   ŰĿ�� �� y�� ���� ���� �� y�� �� ���� ���� ���� ���� ����� 2���� �����Ѵ�.
//   �� 2���� ��Ű�۸� ���� x-1����. �׸��� ������ x-1���� �� �ϳ��� �������� 1 �ؼ� �� x�� �� ����.
//   ���� ���� z�� �ݺ�.
   
//   ���� ����
//   ���� -> 1
//   �޹�  -> 2
//   �߾� -> 3
//   ���� -> 4
//   ���� -> 5
   
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
   
   public PenaltyKick(int kpN, int kcN, int gM) {	//�⺻ ������.
      keeperNum = kpN;
      kickNum = kcN;
      gameNum = gM;
      geneInfo = new ArrayList<String>();
      scoreAll = new ArrayList<Integer>();
      for(int i = 1 ; i <= gameNum; i++) {	//���� ����ŭ �ݺ� ��Ų��.
         gameControl(i);
      }
   }
   
   public void gameControl(int gM) {	//���� ������ å������ controler
      System.out.println(gM + "����!!");
      if(firsttime) {	//1������ ��.
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
         	System.out.println(i+1 +"��° ������� ���� ���� : " + geneInfo.get(i));
            System.out.println(i+1 +"��° ������� ������ "+ scoreAll.get(i));
         }
         firsttime = false;
      }else {	//1���� ������ �����.
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
        	System.out.println(i+1 +"��° ������� ���� ���� : " + geneInfo.get(i));
            System.out.println(i+1 +"��° ������� ������ "+ scoreAll.get(i));
         }
      }
      
   }

   public void whereShoot() {	//�Ź� kick�� ���� �������� ����.
      kick = (int)(Math.random()*5) + 1;
   }
   
   public void whereDefend() {	//1������ �� ��Ű�� ����ŭ �����ڸ� �������� �����.
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
   
   public void resultKick(int keeper) {	//�����ڿ� kick�� ������ ���Ͽ� ������ score�� +1����.
	   String str = geneInfo.get(keeper);
	   String[] stArr = str.split("");
	   if(Integer.parseInt(stArr[kick-1]) == kick) {
		   score++;
	   }
   }
   
   public void rankKeeper() {	//���� ������ ���� ���� ����.
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
   
   public void crossOver() {	//rankKeeper�� ���� ���� 2���� ��ü�� ����
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
   
   public void makeMutation() {	//�������� ����.
      
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
      PenaltyKick PK = new PenaltyKick(10, 20, 100);	//���� ����.
   }
}