/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dark.server;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Ralf
 */
public enum HandType {
	OnePair, SequenceOfPair, Trio, Sequence, FullHouse, FourOfAKind, StraightFlush, SingleCard;

	/**
	 * Determine the value of this hand. Note that this does not account for any
	 * tie-breaking
	 */
	public static HandType evaluateHand(ArrayList<Card> cards) {
		HandType currentEval = SingleCard;
		 if (isStraightFlush(cards))
			currentEval = StraightFlush; //Bomb(atleast 5)
		else if (isFourOfAKind(cards))
			currentEval = FourOfAKind; //4erBomb
	/*	else if (isFullHouse(cards))
			currentEval = FullHouse; */
		else if (isSequence(cards))
			currentEval = Sequence;
		else if (isTrio(cards))
			currentEval = Trio;
		else if (isSequenceOfPair(cards))
			currentEval = SequenceOfPair;
		else if (isOnePair(cards))
			currentEval = OnePair;
                else if (isSingleCard(cards))
                        currentEval=SingleCard;
                
		return currentEval;
	}
        
       public static boolean isSingleCard(ArrayList<Card> cards) {
           
           boolean isSingleCard=false;

            if(cards.size()==1){
		
             isSingleCard=true;
                  
            }
            
                return isSingleCard;
	} 
            

	public static boolean isOnePair(ArrayList<Card> cards) {
            int[] valueCards = HandType.getValueList(cards);
            
            boolean isOnePair=false;
            
                  if(cards.size()==2 && (valueCards[0]==valueCards[1])){
		
             isOnePair=true;        
            }
                  return isOnePair;
	}
        
        
        	public static boolean isTrio(ArrayList<Card> cards) {
                    
                     int[] valueCards = HandType.getValueList(cards);
            
            boolean isTrio=false;
                    
                 if(cards.size()==3 && (valueCards[0]==valueCards[1]) && (valueCards[1]==valueCards[2])){
                        
                     isTrio=true;
                        
                    }

		return isTrio;

	}
                
                	public static boolean isFullHouse(ArrayList<Card> cards) {

		// suit doesn't matter only rank, 3 of the same rank and 2 of the same rank

		int[] valueCards = HandType.getValueList(cards);

		for (int i = 0; i < cards.size(); i++) {
	//		valueCards[i] = cards.get(i).getOrdinal();
		}
		Arrays.sort(valueCards);

		boolean isFullHouse = false;

		if (valueCards[0] == valueCards[1]) {
			// check if third card is the same or the next 3 are the same
			if (valueCards[1] == valueCards[2]) {
				if (valueCards[3] == valueCards[4])
					isFullHouse = true;
			} else if (valueCards[2] == valueCards[3] && valueCards[3] == valueCards[4])
				isFullHouse = true;
		}

		return isFullHouse;
	}
                
                

	public static boolean isSequenceOfPair(ArrayList<Card> cards) {
		// Clone the cards, because we will be altering the list
		ArrayList<Card> clonedCards = (ArrayList<Card>) cards.clone();

		// Find the first pair; if found, remove the cards from the list
		boolean firstPairFound = false;
		for (int i = 0; i < clonedCards.size() - 1 && !firstPairFound; i++) {
			for (int j = i + 1; j < clonedCards.size() && !firstPairFound; j++) {
				if (clonedCards.get(i).getRank() == clonedCards.get(j).getRank()) {
					firstPairFound = true;
					clonedCards.remove(j); // Remove the later card
					clonedCards.remove(i); // Before the earlier one
				}
			}
		}
		// If a first pair was found, see if there is a second pair
		return firstPairFound && isOnePair(clonedCards);
	}
        
        public static boolean isSequence(ArrayList<Card> cards) {
            boolean isSequence=false;
            int[] valueCards = HandType.getValueList(cards);

                    
                 if(cards.size()>=5&&isStraight(cards)){
                     
                        isSequence=true;
                        
                    }

		return isSequence;
            
        }


	public static boolean isStraight(ArrayList<Card> cards) { //check if is sequence 

		boolean isStraight = false;
		if (!HandType.checkFlush(cards) && HandType.checkStraight(cards))
			isStraight = true;

		return isStraight;

	}

	public static boolean isFlush(ArrayList<Card> cards) {

		boolean isFlush = false;

		if (HandType.checkFlush(cards) && !HandType.checkStraight(cards))
			isFlush = true;

		return isFlush;

	}

	public static boolean isFourOfAKind(ArrayList<Card> cards) {
		return findSameOfAKind(cards, 4);
	}

	public static boolean isStraightFlush(ArrayList<Card> cards) {
		boolean isStraightFlush = false;
                
                if(cards.size()>=5){

		if (HandType.checkFlush(cards) && HandType.checkStraight(cards))
			isStraightFlush = true;
                
                }

		return isStraightFlush;
	}


	private static boolean checkFlush(ArrayList<Card> cards) {

		boolean isFlush = false;
		boolean allSuit = true;

		for (int i = 0; i < cards.size() - 1; i++) {
			for (int j = i + 1; j < cards.size(); j++) {

				if (cards.get(i).getSuit() == cards.get(j).getSuit() && allSuit) {
					isFlush = true;

				} else {
					allSuit = false;
					isFlush = false;
				}
			}
		}

		return isFlush;
	}

	private static boolean checkStraight(ArrayList<Card> cards) {

		int[] valueCards = HandType.getValueList(cards);

		boolean isStraight = false;

		for (int i = 0; i < valueCards.length - 1; i++) {
			if (valueCards[i] + 1 == valueCards[i + 1]) {
				isStraight = true;
			} else {
				isStraight = false;
				break;
			}
		}
		return isStraight;
	}
        

	public static int[] getValueList(ArrayList<Card> cards) { // used by TieBreak as well
		int[] valueCards = new int[cards.size()];
		for (int i = 0; i < cards.size(); i++) {
		//	valueCards[i] = cards.get(i).getOrdinal();
		}
		Arrays.sort(valueCards);

		return valueCards;
	}

	public static boolean findSameOfAKind(ArrayList<Card> cards, int num) {
		int count = 1;
		boolean found = false;
		for (int i = 0; i < cards.size() - 1 && !found; i++) {
			count = 1;
			for (int j = i + 1; j < cards.size() && !found; j++) {
				if (cards.get(i).getRank() == cards.get(j).getRank())
					count++;
			}
			if (count >= num) {
				found = true;
			}
		}
		return found;
	}
}
