package Gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import Gui.util.Alerts;
import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;
import model.services.SellerService;

public class MainViewControler implements Initializable{
	
	@FXML
	private MenuItem menuItemSeller;
	
	@FXML
	private MenuItem menuItemDepartment;
	
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	URL url = getClass().getResource("path/to/your/file.fxml");
	FXMLLoader loader = new FXMLLoader(url);
	
	@FXML
	public void onMenuDepartment() {
		loadView("/gui/DepartmentList.fxml", (DepartmentListControler controler) -> { 
			controler.setDepartmentService(new DepartmentService());
			controler.updateTableView();
		});
	}
	
	@FXML
	public void onMenuSeller() {
		loadView("/gui/SellerList.fxml", (SellerListControler controler) -> { 
			controler.setSellerService(new SellerService());
			controler.updateTableView();
		});
	}
	
	@FXML
	public void onMenuAbout() {
		loadView("/gui/About.fxml", x -> {});
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}
	
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			
			Scene mainScene = Main.getScene();
			VBox mainVBox = (VBox)((ScrollPane)mainScene.getRoot()).getContent(); //Acessando o conteudo do ScrollPane
			
			Node mainMenu = mainVBox.getChildren().get(0); //Acessando meu mainMenu no vBox
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			T controler = loader.getController();
			initializingAction.accept(controler);
			
		}
		catch(IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}

}
