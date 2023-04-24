package Gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Gui.listeners.DataChangeListener;
import Gui.util.Utils;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListControler implements Initializable, DataChangeListener {
	
	private DepartmentService service;
	
	@FXML
	private TableView<Department> tableViewDepartment;
	
	@FXML
	private TableColumn<Department, Integer> tableColumnId;	
	// TableColumn<Department, Integer>: aqui recebe dois parametros, um a entidade
	// do qual estamos usando, e o segundo o tipo do nosso Id.
	
	@FXML
	private TableColumn<Department, String> tableColumnName;
	
	@FXML
	private Button btNew;
	
	private ObservableList<Department> obsList;
	
	@FXML
	public void onBtNewAction(javafx.event.ActionEvent event) {
		Stage parentStage = Utils.currentStage(event);
		Department obj = new Department(); 	
		createDialogForm(obj,"/gui/DepartmentForm.fxml", parentStage);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initializeNodes();
		
	}
	
	public void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		Stage stage = (Stage) Main.getScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty()); // Função para a janela se auto ajustar.
		
	}
	
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null!");
		}
		List <Department> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewDepartment.setItems(obsList);
	}
	
	private void createDialogForm(Department obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();
			
			DepartmentFormController controler = loader.getController();
			controler.setDepartment(obj);
			controler.setDepartmentService(new DepartmentService());
			controler.subsCribeDataChangeListener(this);
			controler.updateFormData();
	
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Department Data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);//nao deixa acessar a janela anterior sem fecha-la.
			dialogStage.showAndWait();
	}
		catch (IOException e) {
			e.getMessage();
		}
	}
	
	@Override
	public void onDataChanged() {
		updateTableView();
	}

}
