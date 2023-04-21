package Gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class MainViewControler implements Initializable{
	
	@FXML
	private MenuItem menuItemSeller;
	
	@FXML
	private MenuItem menuItemDepartment;
	
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	public void onMenuSeller() {
		System.out.println("menuItemSeller");
	}
	
	@FXML
	public void onMenuDepartment() {
		System.out.println("menuItemDepartment");
	}
	
	@FXML
	public void onMenuAbout() {
		System.out.println("menuItemAbout");
	}


	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

}
