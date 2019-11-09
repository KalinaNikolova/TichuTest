package DarkTichu.view;

import DarkTichu.model.Card;
import DarkTichu.model.HandType;
import DarkTichu.model.Player;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


public class PlayerPane extends VBox {
	private Label lblName = new Label();
	private HBox hboxCards = new HBox();
	private Label lblEvaluation = new Label("--");
	private HBox playerInfo = new HBox();
	private Label pic1 = new Label();
	private Label pic2 = new Label();
	// Link to player object
	private Player player;

	public PlayerPane() {
		super(); // Always call super-constructor first !!
		this.getStyleClass().add("player"); // CSS style class
		pic1.setGraphic(null);
		pic2.setGraphic(null);
		playerInfo.getChildren().addAll(pic1, lblEvaluation, pic2);
		// Add child nodes
		this.getChildren().addAll(lblName, hboxCards, playerInfo);

		// Center the cards
		VBox.setMargin(hboxCards, new Insets(0));

		// Add CardLabels for the cards
		for (int i = 0; i < 5; i++) {
			Label lblCard = new CardLabel();
			hboxCards.getChildren().add(lblCard);
		}
	}

	public void setPlayer(Player player) {
		this.player = player;
		updatePlayerDisplay(); // Immediately display the player information
	}

	public void highlight() {
		Image trophy = new Image(this.getClass().getClassLoader().getResourceAsStream("dark_Tichu/images/chips2.png"));
		ImageView trophyView = new ImageView(trophy);

		trophyView.setPreserveRatio(true);
		trophyView.setFitWidth(100);

		pic1.setGraphic(trophyView);
		getStyleClass().add("highlight");
		lblName.getStyleClass().add("lblhighlight");
		lblEvaluation.getStyleClass().add("lblhighlight");
		hboxCards.getChildren();

		TranslateTransition move = new TranslateTransition();
		move.setDuration(Duration.seconds(1));
		move.setFromY(150);// move.setFromX(-250);//
		move.setToY(0);// move.setToX(0);//
		move.setNode(trophyView);
		move.play();
	}

	public void updatePlayerDisplay() {
		pic1.setGraphic(null);
		getStyleClass().remove("highlight");
		lblName.getStyleClass().remove("lblhighlight");
		lblEvaluation.getStyleClass().remove("lblhighlight");
		double timeX = 0.5;
		double timeY = 0.3;
		lblName.setText(player.getPlayerName());
		player.getCards().sort(null);
		for (int i = 0; i < Player.HAND_SIZE; i++) {
			Card card = null;
			if (player.getCards().size() > i)
				card = player.getCards().get(i);
			CardLabel cl = (CardLabel) hboxCards.getChildren().get(i);
			cl.setCard(card);

			if (cl.getGraphic() != null) {
				TranslateTransition transition = new TranslateTransition();
				timeX += 0.5;
				transition.setFromX(500);
				transition.setToX(0);
				transition.setDuration(Duration.seconds(timeX));
				transition.setNode(cl);
				transition.play();
			}

			HandType evaluation = player.evaluateHand();
			if (evaluation != null)
				lblEvaluation.setText(evaluation.toString());
			else
				lblEvaluation.setText("---");

		}
	}

	public String getName() {
		return player.getPlayerName();
	}

}
