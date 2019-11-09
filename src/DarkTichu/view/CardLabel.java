package DarkTichu.view;

import com.sun.javafx.geom.Shape;
import com.sun.media.jfxmedia.logging.Logger;

import DarkTichu.model.Card;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

// this class is taken from PokerGame2019 project provided by prof. Bradley Richards
//
public class CardLabel extends Label{
	public CardLabel() {
		super();
		this.getStyleClass().add("card");
	}

	public void setCard(Card card) {
//		if (card != null) {
//			String fileName = cardToFileName(card);
//			Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("dark_Tichu/images/" + fileName));
//			
//			ImageView imv = new ImageView(image);
//			imv.fitWidthProperty().bind(this.widthProperty());
//			imv.fitHeightProperty().bind(this.heightProperty());
//			//imv.setPreserveRatio(true);
//			
//			this.setGraphic(imv);
//		}
		if (card != null) {
			String fileName = cardToFileName(card);
			Image image = new Image(this.getClass().getClassLoader().getResourceAsStream("dark_Tichu/images/" + fileName));
			ImageView imv = new ImageView(image);
			imv.fitWidthProperty().bind(this.widthProperty());
			imv.fitHeightProperty().bind(this.heightProperty());
			imv.setPreserveRatio(true);
			this.setGraphic(imv);
		} else {
			// load back cover of the cards
			Image image = new Image(
					getClass().getClassLoader().getResourceAsStream("dark_Tichu/images/tichu12.png"));
			ImageView imv = new ImageView(image);
			imv.fitWidthProperty().bind(this.widthProperty());
			imv.fitHeightProperty().bind(this.heightProperty());
			this.setGraphic(imv);

		}
	}

	private String cardToFileName(Card card) {
		String rank = card.getRank().toString();
		String suit = card.getSuit().toString();
		return suit + "_"+ rank+".png";
	}

}
