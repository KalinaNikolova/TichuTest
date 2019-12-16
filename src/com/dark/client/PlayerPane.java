package com.dark.client;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;



public class PlayerPane extends VBox {

	private Button playButton = new Button("Play");
	private Button passButton = new Button("Pass");
    private Label lblName = new Label();

    private Pane hboxCards = new Pane();
    private HBox bottom = new HBox();
    //private Label lblEvaluation = new Label("--");
    private Label picture = new Label();
    // Link to player object
    private Player player;
    private int x=0;
    BorderPane topPlayer;//ne mnogo dobre...
    public PlayerPane() {
        super(); // Always call super-constructor first !!
        this.getStyleClass().add("player"); // CSS style class
    	
    	picture.setGraphic(null);
       
    	topPlayer=new BorderPane();
    
    	topPlayer.setPrefWidth(435);
    	topPlayer.setMaxWidth(435);
    	topPlayer.setMinWidth(435);
    	topPlayer.setLeft(playButton);
    	topPlayer.setCenter(lblName);
    	topPlayer.setRight(passButton);
    
    	
        // Add child nodes
        this.getChildren().addAll(topPlayer, hboxCards, bottom);
      
        
        // Add CardLabels for the cards
        for (int i = 0; i < Player.HAND_SIZE; i++) {
            Label lblCard = new CardLabel();
            hboxCards.getChildren().add(lblCard);
            lblCard.setVisible(false);
        }
    }

	public void setPlayer(Player player) {
    	this.player = player;
    	updatePlayerDisplay(); // Immediately display the player information
    }
	
	public String getName() {
		return lblName.getText();
	}
	//not ok
	public void setName(String name) {
		lblName.setText(name);
		player.setPlayerName(name);//
	}

    public void updatePlayerDisplay() {
    	picture.setGraphic(null);
    	lblName.setText(player.getPlayerName());
    	x=0;
    	player.getCards().sort(null);//
    	for (int i = 0; i < Player.HAND_SIZE; i++) {

    		CardLabel cl = (CardLabel) hboxCards.getChildren().get(i);
    		
    			cl.setVisible(false);
    	}
    	for (int i = 0; i < player.getCards().size(); i++) {
    		Card card = null;
    		if (player.getCards().size() > i) card = player.getCards().get(i);
    		CardLabel cl = (CardLabel) hboxCards.getChildren().get(i);
    		cl.setLayoutX(x);
    		
    			cl.setVisible(true);
    		x+=25;
    		cl.setCard(card);

    	}
    }

	public Pane getHboxCards() {
		return hboxCards;
	}

	public Button getPlayButton() {
		return playButton;
	}

	public Button getPassButton() {
		return passButton;
	}
    
	
}
