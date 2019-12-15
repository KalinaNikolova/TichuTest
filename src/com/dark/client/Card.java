package com.dark.client;


//this class is taken from PokerGame2019 project provided by prof. Bradley Richards
//It has been changed by Kalina slightly to fit the new cards names
public class Card implements Comparable<Card> {
	public enum Suit {
		Jade, Pagodas,Stars,Swords;
		@Override
		public String toString() {
			String suit = "";
			switch (this) {
			case Jade:
				suit = "J";
				break;
			case Pagodas:
				suit = "P";
				break;
			case Stars:
				suit = "S";
				break;
			case Swords:
				suit = "W";
				break;
			}
			return suit;
		}
	};
    
    public enum Rank { Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace;
        @Override
        public String toString() {
            String str = "Ace";  // Assume we have an ace, then cover all other cases
            // Get ordinal value, which ranges from 0 to 12
            int ordinal = this.ordinal();
            if (ordinal <= 7) {
				str ="0"+Integer.toString(ordinal + 2);// 03,04,05....
			} else if (ordinal == 8) { // 10
				str = Integer.toString(ordinal + 2);
			}
			else if (ordinal == 9) { // Jack
				str = "Jack";
			} else if (ordinal == 10) { // Queen
				str = "Queen";
			} else if (ordinal == 11) { // King
				str = "King";
			}
			return str;
		}
    };
    private int ordinalint;
    private Suit suit;
    private Rank rank;
//    private final Suit suit;
//    private final Rank rank;
    
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }
    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }
    //ADD
    public int getOrdinal() {

		ordinalint = rank.ordinal() + 2;
		return ordinalint;
	}
    
    @Override
    public String toString() {
        return rank.toString() + suit.toString();
    }
    //ADD
    @Override
	public int compareTo(Card that) {
		if(this.rank.ordinal()>that.rank.ordinal())return 1;
		else if(this.rank.ordinal()<that.rank.ordinal())return -1;
		else return 0;
	}
    
  //ne savpada s compare// drugi structuri ot danni
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		return result;
	}
    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (rank != other.rank)
			return false;
		if (suit != other.suit)
			return false;
		return true;
	}
    
}