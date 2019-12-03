package com.dark.common;
/**
 * 
 * @author Kalina
 * message for putting the players in the table
 *
 */
public class PosMsg extends Message {
	private int position;
	
	public PosMsg(int position) {
		super(MessageType.Pos);
		this.position = position;
	}

	public int getPosition() {
		return position;
	}
	
	public void setPosition(int position) {
		this.position=position;
	}
	
	@Override
	public String toString() {
		return type.toString() + '|' + position;
	}
}
