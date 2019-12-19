/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dark.server;


import com.dark.client.Player;
import java.util.ArrayList;
import java.util.Collections;
/**
 *
 * @author Ralf
 */
public class TichuGameModel {
    private final ArrayList<Player> players = new ArrayList<>(); 
	private DeckOfCards deck;
	private int playersCount;
        private ArrayList <Card> cards = new ArrayList<>(); //get player cards played
        private ArrayList<Card> cardsOnTable = new ArrayList<>(); // palyer cards on table
        private HandType handType; // current handtype, only allowed to play this
        private ArrayList <Card> cardsPlayed = new ArrayList<>(); //card pool, who ever wins the cards
        private boolean team1; //winner of Round, if true winner team 1
        private boolean legalMove=false;
        int player;
      

        
        //create players current cards decks for comparison

        
        public boolean getWinnerOfRound(){  //evaluate cards of current palyers
            
            //call HandType with ArrayList <cards>, need a way to compare two sets of cards
            //not possible to call players on server, using ID of player, Team A(1,3) Team B (2,4) 
            player=0;

            
            if(checkIfLegalMove()){
                
               HandChecker handchecker = new HandChecker(cards,cardsOnTable,HandType.evaluateHand(cards).toString());
               //players.get(i).getCards()
               player = handchecker.checkCards();
               
               if(player==1){
                   //winner
                   addCardsToPlayedCards();
                   
               }
               
               else{
                   //cards are not higher than cards on the tabel
    
                   
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
}