package com.dark.common;
/**
 * This class has been taken from ch.fhnw.richards.lecture14_chatLab.v3_commons;
 * No changes made
 *
 */
public class JoinMsg extends Message {
	private String name;
	
	public JoinMsg(String name) {
		super(MessageType.Join);
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	@Override
	public String toString() {
		return type.toString() + '|' + name;
	}
}
