package DarkTichu;
//test azure kjn

import DarkTichu.controller.DarkTichuController;
import DarkTichu.model.DarkTichuModel;
import DarkTichu.view.DarkTichuView;
import javafx.application.Application;
import javafx.stage.Stage;

public class DarkTichu extends Application {

	DarkTichuModel model;
	DarkTichuView view;
	DarkTichuController controller;
	
// this is a test for the develop branch
	
	
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Create and initialize the MVC components
		model = new DarkTichuModel();
		view = new DarkTichuView(primaryStage, model);
		controller = new DarkTichuController(model, view);
		
		 }
}
