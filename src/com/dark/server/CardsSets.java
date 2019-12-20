/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dark.server;

import java.util.ArrayList;

/**
 *
 * @author Ralf
 */
public class CardsSets {
    
      static private ArrayList<Card>  cardsOnTable = new ArrayList<Card>();
      static private ArrayList<Card>  cards = new ArrayList<Card>();
      static private int position;
      static private int positionPlayerTabel;
      static private int round;
      static private boolean cardsEmpty=true;
      static private boolean cardsOnTableEmpty=true;
    
    
    private static CardsSets singleton = new CardsSets( ); 
    
    private CardsSets() { }
    
    
      public static CardsSets getInstance( ) {
      return singleton;
   }
      
      public static void setCards(int pos, ArrayList<Card> cd){
         

          position=pos;
          cards.addAll(cd);
          cardsEmpty=false;
          
         round++; 
      }
      
        public static void setCardsOnTable(int pos, ArrayList<Card> cd){

          positionPlayerTabel=pos;
          cardsOnTable.addAll(cd);
          cardsOnTableEmpty=false;

         round++; 
      }

    public static ArrayList<Card> getCardsOnTable() {
        return cardsOnTable;
    }

    public static ArrayList<Card> getCards() {
        return cards;
    }

    public static int getPosition() {
        return position;
    }

    public static int getPositionPlayerTabel() {
        return positionPlayerTabel;
    }

    public static int getRound() {
        return round;
    }

    public static boolean isCardsEmpty() {
        return cardsEmpty;
    }

    public static boolean isCardsOnTableEmpty() {
        return cardsOnTableEmpty;
    }
    

}
    