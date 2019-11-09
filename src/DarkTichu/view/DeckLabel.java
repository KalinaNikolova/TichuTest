package DarkTichu.view;

import DarkTichu.model.DeckOfCards;
import javafx.scene.control.Label;


public class DeckLabel extends Label {
	public DeckLabel() {
		super();
		this.getStyleClass().add("deck");
	}

	// Bind the label to the CardsRemaining property of the deck
	public void setDeck(DeckOfCards deck) {
		this.textProperty().bind(deck.getCardsRemainingProperty().asString());
	}
}
