package com.dark.common;
/**
 * 
 * This class has been taken from ch.fhnw.richards.lecture14_chatLab.v3_commons;
 * No changes made
 *
 */
public class ChatMsg extends Message {
	private String name;
	private String content;
	
	public ChatMsg(String name, String content) {
		super(MessageType.Chat);
		this.name = name;
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public String getContent() {
		return content;
	}
	
	@Override
	public String toString() {
		return type.toString() + '|' + name + '|' + sanitize(content);
	}
	
	private String sanitize(String in) {
		return in.replace('|', '/');
	}
}
