package com.dark.client;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardLabel extends Label {
	public CardLabel() {
		super();
		this.getStyleClass().add("card");
	}
	private Card card;

	public void setCard(Card card) {
		this.card = card;//
		if (card != null) {
			String fileName = cardToFileName(card);
			Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("tichu/images/" + fileName));
			ImageView imv = new ImageView(image);
			imv.fitWidthProperty().bind(this.widthProperty());
			imv.fitHeightProperty().bind(this.heightProperty());
			imv.setPreserveRatio(true);
			this.setGraphic(imv);
		} else {
			this.setGraphic(null);
			//ADD?
			// load back cover of the cards
//			Image image = new Image(
//					getClass().getClassLoader().getResourceAsStream("dark_tichu_files/images/tichu12.png"));
//			ImageView imv = new ImageView(image);
//			imv.fitWidthProperty().bind(this.widthProperty());
//			imv.fitHeightProperty().bind(this.heightProperty());
//			this.setGraphic(imv);
		}
	}

	private String cardToFileName(Card card) {
		String rank = card.getRank().toString();
		String suit = card.getSuit().toString();
		return suit + "_"+ rank+".png";//ADD
	}
	//
	public Card getCard() {
		return card;
	}
	

}
