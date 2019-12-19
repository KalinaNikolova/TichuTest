package com.dark.common;

import java.util.ArrayList;

import com.dark.server.Card;


public class WinMsg  extends Message {

	public WinMsg(MessageType type) {
		super(type.Win);
		// TODO Auto-generated constructor stub
	}
	
//	private static final long serialVersionUID = 1L;
//	private int position;
//	private ArrayList<Card> cards;
//	
//	public WinMsg(int position, ArrayList<Card> cards) {
//		super(MessageType.Win);
//		this.position = position;
//		this.cards = cards;
//	}
//
//	public int getPosition() {
//		return position;
//	}
//
//	public ArrayList<Card> getCards() {
//		return cards;
//	}
//	
//	@Override
//	public String toString() {
//		return type.toString() + '|' + cards+ '|' + position;
//	}
//	
//	private String sanitize(String in) {
//		return in.replace('|', '/');
//	}
}