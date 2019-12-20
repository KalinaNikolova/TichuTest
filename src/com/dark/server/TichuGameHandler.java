/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dark.server;


import com.dark.client.Player;
import java.util.ArrayList;
/**
 *
 * @author Ralf
 */
public class TichuGameHandler {
    private final ArrayList<Player> players = new ArrayList<>(); 
	private DeckOfCards deck;
        private ArrayList <Card> cards = new ArrayList<>(); //get player cards played
        public ArrayList<Card> cardsOnTable = new ArrayList<>(); // palyer cards on table
        private HandType handType; // current handtype, only allowed to play this
        private ArrayList <Card> cardsPlayed = new ArrayList<>(); //card pool, who ever wins the cards
        private boolean team1; //winner of Round, if true winner team 1
        private boolean legalMove=false;
        private int player1; //player which has cards on the tabel, (cardsOnTable)
        private int player2; // player which makes the play, have to refuse play, if not legal 
        private int player;
        private boolean turnRefused=false; //if player 2 makes a invalid move
        private int passcount=0;
        private boolean roundEnd=false;
        
        
     /*   public TichuGameHandler(DeckOfCards deck, int playersCount, HandType handType, int player1, int player2) {
        this.deck = deck;
        this.handType = handType;
        this.player1 = player1;
        this.player2 = player2;
    } */
                public TichuGameHandler(int player1) {

        this.player1 = player1;

    } 
          
        //create players current cards decks for comparison

        
        public boolean getWinnerOfRound(){  //evaluate cards of current palyers
            
            //call HandType with ArrayList <cards>, need a way to compare two sets of cards
            //not possible to call players on server, using ID of player, Team A(0,2) Team B (1,3) 
            player=0;//used as boolean

            
            if(checkIfLegalMove()){
                
               HandChecker handchecker = new HandChecker(cards,cardsOnTable,HandType.evaluateHand(cards).toString());
               //players.get(i).getCards()
               player = handchecker.checkCards();
               
               if(player==1){ 
                   //winner
                   addCardsToPlayedCards();
                   
                   cards.clear();
                  
                   //get new cards
                   //...
                   
                 
               }
               
               else{
                   //cards are not higher than cards on the tabel
                   //push back not allowed
                   turnRefused=true;
                   
               }
               
               
             
                //set WinnerTeam, check if card set is higher and if all players passed or highest card round ends.
                
                
            }    
            
            
            return team1;
  
        }
     
        public boolean checkIfLegalMove(){ // only checks if same handtype
            
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
                 System.out.println("In Handler: "+card.toString());
            });  
       }
       
       
       public void putCards(Card cards){
           
           cardsOnTable.add(cards);
           
       }

}