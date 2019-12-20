
package com.dark.server;

import java.util.ArrayList;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.CloseAction;


/**
 *
 * @author Ralf
 */
public class Teams {
    
    private static int scoreTeam1;
    private static int scoreTeam2;
    private static String winner;
    public static ArrayList <Card> cardsTeam1 = new ArrayList<>(); 
    public static ArrayList <Card> cardsTeam2 = new ArrayList<>(); 
    public static ArrayList <String> cardsT1 = new ArrayList<>(); //test array
    
       private ArrayList<com.dark.server.Card> cards = new ArrayList<>();////// temporar testing    
    
    public void calculatePoints(){
        //calulate value of stored cards
        //store points to team
    //    for (Card cardsTeam11 : cardsTeam1) {
              for (String cardsTeam11 : cardsT1) {
         //   scoreTeam1=+checkValue(cardsTeam11.get());
         
                  System.out.println(cardsTeam11);
            scoreTeam1+=checkValue(cardsTeam11);
                  System.out.println("Score of Team 1 is: "+scoreTeam1);
        }
              
            for (Card cardsTeam2 : cardsTeam2) {
            scoreTeam2+=checkValue(cardsTeam2.toString()); // depenting how we will get cards     
        }
        
    }
    
    public int checkValue(String a){ //checks Value of a card
        int points=0;
        
        if(a.contains("5")){
            points=5;
        }
        
        else if(a.contains("K")||a.contains("T")){
            points=10;
        }
        
        return points;
    }
    
        /*
     Kings and tens are worth ten points each, 
    fives are worth five points,
    the Dragon is worth 25 points, and the Phoenix is worth negative 25. All other cards score zero. 
    0 points: "2C", "3C", "4C", "6C", "7C", "8C","9C","JC", "QC", "AC"
    5 points: "5C"
    10 pints  "KC","TC"
    */
    
    public String getWinner(){
      
        if(scoreTeam1>scoreTeam2){
            
        }
        
        return winner;
    }

    
    public static int getScoreTeam1() {
        return scoreTeam1;
    }

    public static void setScoreTeam1(int scoreTeam1) {
        Teams.scoreTeam1 = scoreTeam1;
    }

    public static int getScoreTeam2() {
        return scoreTeam2;
    }

    public static void setScoreTeam2(int scoreTeam2) {
        Teams.scoreTeam2 = scoreTeam2;
    }
    
    

}