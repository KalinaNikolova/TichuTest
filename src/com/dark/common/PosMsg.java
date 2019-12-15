package com.dark.common;

public class PosMsg extends Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
