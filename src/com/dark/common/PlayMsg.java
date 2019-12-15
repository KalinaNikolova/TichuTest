package com.dark.common;

import java.util.ArrayList;

import com.dark.client.Card;

public class PlayMsg extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Card> cards;//трябва да отидат в Common картите Serializable
	private int position;
	public PlayMsg(ArrayList<Card> cards, int position) {
		super(MessageType.Play);
		this.cards=cards;
		this.position=position;
	}
	
	
	public ArrayList<Card> getCards() {
		return cards;
	}

//
//	public void setCards(ArrayList<Card> cards) {
//		this.cards = cards;
//	}

	public int getPosition() {
		return position;
	}


	public void setPosition(int position) {
		this.position = position;
	}

	//comment
	@Override
	public String toString() {
		return type.toString() + '|' + cards;////see
	}

}
