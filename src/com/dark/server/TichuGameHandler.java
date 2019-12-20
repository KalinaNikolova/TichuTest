/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dark.server;

import com.dark.client.Player;
import static com.dark.server.Teams.cardsTeam1;
import static com.dark.server.Teams.cardsTeam2;

import java.util.ArrayList;
import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
/**
 *
 * @author Ralf
 */
public class TichuGameHandler {
        static private ArrayList <Card> cards = new ArrayList<>(); //get player cards played
        static public ArrayList<Card> cardsOnTable = new ArrayList<>(); // palyer cards on table
        static private HandType handType; // current handtype, only allowed to play this
        static private ArrayList <Card> cardsPlayed = new ArrayList<>(); //card pool, who ever wins the cards
        static private boolean team1; //winner of Round, if true winner team 1
        static private boolean legalMove=false;
        static private int player1; //player which has cards on the tabel, (cardsOnTable)
        static private int player2; // player which makes the play, have to refuse play, if not legal 
        static private int player;
        static private boolean turnRefused=false; //if player 2 makes a invalid move
        static private int passcount=0;
        static private boolean roundEnd=false;
        
    public static ArrayList <Card> p1 = new ArrayList<>();
    public static ArrayList <Card> p2 = new ArrayList<>();
    public static ArrayList <Card> p3 = new ArrayList<>();
    public static ArrayList <Card> p4 = new ArrayList<>();
    public static int round;
    public static ArrayList <Card> cardsTeam1 = new ArrayList<>(); 
    public static ArrayList <Card> cardsTeam2 = new ArrayList<>();

          
        //create players current cards decks for comparison

        
        public boolean getWinnerOfRound(){  //evaluate cards of current palyers
            
            //call HandType with ArrayList <cards>, need a way to compare two sets of cards
            //not possible to call players on server, using ID of player, Team A(0,2) Team B (1,3) 
            player=0;//used as boolean
                System.out.println("getWinner started");
            
            if(checkIfLegalMove()){
                
                
               HandChecker handchecker = new HandChecker(cards,cardsOnTable,HandType.evaluateHand(cards).toString());
               //players.get(i).getCards()
               player = handchecker.checkCards();  
               
               
               if(player==1){ //if player 2  =1, allowed to play, this means Handtype is equal
                   //winner
                   addCardsToPlayedCards();
                   
                   System.out.println("Player 2 won turn");
                   team1=false;
                   cards.clear();
                  
                   //get new cards
                   //...
                   
                 
               }
               
               else{
                   //cards are not higher than cards on the tabel
                   //push back not allowed
                   turnRefused=true; // not used yet
                   
                System.out.println("Player 2 turn refused");
                team1=true;
                   
               }
        
                //set WinnerTeam, check if card set is higher and if all players passed or highest card round ends.
              
            }  
            else{   
                
                System.out.println("This play is not allowed");
                team1=true;
                
            }  
            
            
            return team1;
  
        }
     
        public boolean checkIfLegalMove(){ // only checks if same handtype
            System.out.println("chekifLegalMove is called");
            System.out.println(HandType.evaluateHand(cards).toString());
                    
            
           if( HandType.evaluateHand(cards)==HandType.evaluateHand(cardsOnTable)){
               legalMove=true;
           }
           return legalMove;        
           
        }
        
        public void playerPass(){
            passcount++;
        }
        
       
        
       public void addCardsToPlayedCards(){  //add cards to played deck, current round
           
      
           cardsOnTable.forEach((card) -> {   //not relevant for evulation anymore, add to current round played deck
               cardsPlayed.add(card);
        });      
    
                cards.forEach((card) -> {      //replace cards on the tabel
               cardsOnTable.add(card);
        });           
           
           
           
        }
       
       
       public void addToWinnerTeam(Boolean team1){ //add cards to winner team deck, after the round
           
           if(team1){
           for(int i=0;i<cardsPlayed.size();i++){
               
               Teams.cardsTeam1.add(cardsPlayed.get(i));
           
            }
       }
           else{
           for(int i=0;i<cardsPlayed.size();i++){
         
                      Teams.cardsTeam2.add(cardsPlayed.get(i));
                      
                }         
           }
           
           cardsPlayed=null;  //remove cards
           
       }
       
       public void printCardOnTable(){
           
             cardsOnTable.forEach((card) -> {   
                 System.out.println("CardsOnTable in Handler: "+card.toString());
            });  
       }
       
       
       public static void putCards(ArrayList<Card> cards){ //set cards
           
           cards.addAll(cards);
           
       }
       
      public static void putCardOnTable(ArrayList<Card> cards){
           
           cardsOnTable.addAll(cards);
           
       }

    public static void setCards(ArrayList<Card> cards) {
        TichuGameHandler.cards = cards;
    }

    public static void setCardsOnTable(ArrayList<Card> cardsOnTable) {
        TichuGameHandler.cardsOnTable = cardsOnTable;
    }

    public static void setCardsPlayed(ArrayList<Card> cardsPlayed) {
        TichuGameHandler.cardsPlayed = cardsPlayed;
    }

    public static void setPlayer1(int player1) {
        TichuGameHandler.player1 = player1;
    }

    public static void setPlayer2(int player2) {
        TichuGameHandler.player2 = player2;
    }
      
 
    public static void playerCardsOnTable(int pos, ArrayList<Card> cards) {
	 ArrayList<Card>  cardsForEvaluation = new ArrayList<Card>();

    if(cards.isEmpty()==false){
	switch (pos) {		

    case 0: {
    	p1.addAll(cards);
    	break;	
    }
    case 1:	p2.addAll(cards);	break;	
    case 2:	p3.addAll(cards);	break;	
    case 3:	{
    	p4.addAll(cards);	
    	
    	round++;
    	System.out.println("Round "+round);
    	break;	
    }
    	
	}
	cardsTeam1.addAll(p1);
	cardsTeam1.addAll(p2);
	cardsTeam2.addAll(p3);
	cardsTeam2.addAll(p4);


    	takePlayedCards(p1);
    	takePlayedCards(p2);
    	takePlayedCards(p3);
    	takePlayedCards(p4);
    	takePlayedCards(cardsTeam1);
    	takePlayedCards(cardsTeam2);
		
        if(CardsSets.getCardsOnTable().equals(p1)==false){
               CardsSets.setCardsOnTable(pos, p1);

             System.out.println("Cards on the table: "+ CardsSets.getCardsOnTable());

               //check if same cards
        }
        //1 set singelton
        //2 set 
        
        //else if(CardsSets.isCardsEmpty()){//check if we have two sets, evaluate
        else if(CardsSets.getCards().equals(p2)==false){
            CardsSets.setCards(pos, p2); //cardsForEvaluation
        
      //  System.out.println("Cards: "+ CardsSets.getCards());
        player2=CardsSets.getPosition();
	TichuGameHandler tgh = new TichuGameHandler();	
        TichuGameHandler.setCardsOnTable(CardsSets.getCardsOnTable());
        TichuGameHandler.setCards(CardsSets.getCards());
        TichuGameHandler.setPlayer1(CardsSets.getPositionPlayerTabel());
        TichuGameHandler.setPlayer2(CardsSets.getPosition());
        
        team1  =  tgh.getWinnerOfRound(); //asign the winner to team
        if(team1){
            System.out.println("Team 1 won");
        }
        else if(team1==false){
             System.out.println("Team 2 won");
        }
        
            }
        
        
        //add player 3 and 4
        
        else{
           //
           }
        }
    }

    public static void takePlayedCards(ArrayList<Card>  cardsForEvaluation) {
    for (int i = 0; i <  cardsForEvaluation.size(); i++) {
        for (int j = i + 1; j <  cardsForEvaluation.size(); j++) {
            if ( cardsForEvaluation.get(i).equals(cardsForEvaluation.get(j))) {
            	 cardsForEvaluation.remove(j);
                j--;
            }
        }
    }
  //  System.out.println("FINALLY WE HAVE CARDS TEAMS:"+ cardsForEvaluation);
    }

}

