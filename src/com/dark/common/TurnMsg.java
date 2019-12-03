package com.dark.common;
/**
 * 
 * @author Kalina
 * message for taking turn
 */
public class TurnMsg extends Message {
	private int position;
	private String content;
	
	public TurnMsg(int position, String content) {
		super(MessageType.Turn);
		this.position = position;
		this.content = content;
	}

	public int getPosition() {
		return position;
	}

	public String getContent() {
		return content;
	}
	
	@Override
	public String toString() {
		return type.toString() + '|' + position + '|' + sanitize(content);
	}
	
	private String sanitize(String in) {
		return in.replace('|', '/');
	}
}