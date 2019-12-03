package com.dark.server;

import javafx.collections.ListChangeListener;

public class Controller {
//	private Model model;
//	private View view;

	public Controller(Model model, View view) {
//		this.model = model;
//		this.view = view;

		view.btnStart.setOnAction(event -> {
			view.btnStart.setDisable(true);
			int port = Integer.parseInt(view.txtPort.getText());
			model.startServer(port);
		});

		view.stage.setOnCloseRequest(event -> model.stopServer());

		model.clients.addListener((ListChangeListener) (event -> view.updateClients()));
	}
}
