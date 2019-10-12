package DarkTichu;


import DarkTichu.controller.DarkTichuController;
import DarkTichu.model.DarkTichuModel;
import DarkTichu.view.DarkTichuView;
import javafx.application.Application;
import javafx.stage.Stage;

public class DarkTichu extends Application {

	DarkTichuModel model;
	DarkTichuView view;
	DarkTichuController controller;
// this is a new test for azure
	
	int azure = 3;
	int a=2;
	int z=0;
	//commentsfffwdwdddccfghgh
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Create and initialise the MVC components
		model = new DarkTichuModel();
		view = new DarkTichuView(primaryStage, model);
		controller = new DarkTichuController(model, view);
		
		 }
}
