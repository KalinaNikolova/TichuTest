package com.dark.client;

import java.util.Observable;
/**
 * 
 * @author Kalina
 *
 */
public class Turn extends Observable{
	private int turn;

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
		setChanged();
		notifyObservers(Integer.valueOf(turn));
	}
	
}
