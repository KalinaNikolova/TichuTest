package com.dark.server;
/**
 *Class taken from WI_SoftwareEngineering-master.zip Bradley Richards chatLab
 */
import javafx.application.Platform;
import javafx.collections.ListChangeListener;

public class Controller {
	private Model model;
//	private View view;

	public Controller(Model model, View view) {
		this.model = model;
//		this.view = view;

		view.btnStart.setOnAction(event -> {
			view.btnStart.setDisable(true);
			int port = Integer.parseInt(view.txtPort.getText());
			model.startServer(port);
		});

		view.stage.setOnCloseRequest(event -> {     
//			view.stop();     // closes the GUI
//        Platform.exit(); // ends any JavaFX activities
//        System.exit(0);  // end all activities (our server task) - not good code
        model.stopServer();});

		model.clients.addListener((ListChangeListener) (event -> view.updateClients()));
	}
}
