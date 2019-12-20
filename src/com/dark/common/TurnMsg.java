package com.dark.common;

public class TurnMsg extends Message {
	/**
	 *Body taken from WI_SoftwareEngineering-master.zip Bradley Richards chatLab
	 */
	private static final long serialVersionUID = 1L;
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