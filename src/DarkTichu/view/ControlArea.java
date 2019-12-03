package DarkTichu.view;

import DarkTichu.model.DeckOfCards;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;


public class ControlArea extends HBox {
	private DeckLabel lblDeck = new DeckLabel();
	private Region spacer = new Region(); // Empty spacer
	Button btnShuffle = new Button("Shuffle");
	Button btnDeal = new Button("Start");
	Button btnPass = new Button("Pass");
	Button somethingElse= new Button ("Something else");
	
	
	Label lblWinner = new Label("");

	public ControlArea() {
		super(); // Always call super-constructor first !!

		this.getChildren().addAll(lblDeck, spacer, btnShuffle, btnDeal, btnPass,somethingElse, lblWinner);

		HBox.setHgrow(spacer, Priority.ALWAYS); // Use region to absorb resizing
		this.setId("controlArea"); // Unique ID in the CSS
	}

	public void linkDeck(DeckOfCards deck) {
		lblDeck.setDeck(deck);
	}
}
