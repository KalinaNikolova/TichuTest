package com.dark.common;

import java.util.ArrayList;

import com.dark.server.Card;

public class DealMsg extends Message{
	/**
	 * The body of this class was taken from WI_SoftwareEngineering-master.zip from Bradley Richards
	 * chatLab example
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Card> cards;
	private int position;
	public DealMsg(ArrayList<Card> cards, int position) {
		super(MessageType.Deal);
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
