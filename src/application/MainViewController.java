package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class MainViewController implements Initializable
{
	private MainApp mainApp;
	
	@FXML
	private Button saveButton;
	
	@FXML
	private Button editButton;
	
	@FXML
	private Button deleteButton;
	
	@FXML
	private TextField textFieldTitle;
	
	@FXML
	private TextField textFieldVenue;
	
	@FXML
	private TextArea textAreaDescription;
	
	@FXML
	private ComboBox<String> comboboxFiltre;
	
	private ObservableList<String> listComboboxItems;
	
	@FXML
	private TableView<CallendarEntry> tableView;
	
	@FXML
	private TableColumn<CallendarEntry, String> tableColumns;
	
	public void setMainApp(MainApp mainApp) 
	{
        this.mainApp = mainApp;

        // Add observable list data to the table
        tableView.setItems(mainApp.getCallendarEntriesObservableList());
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		listComboboxItems = FXCollections.observableArrayList();
		listComboboxItems.add("All events");
		listComboboxItems.add("This month");
		listComboboxItems.add("This week");
		listComboboxItems.add("Today");
		comboboxFiltre.setItems(listComboboxItems);
		
		tableColumns.setCellValueFactory(cellData -> cellData.getValue().getTitle());
		
	}
	
	public void saveButton_onAction()
	{
		System.out.println("SaveButton action");
		textFieldTitle.setEditable(false);
		textFieldVenue.setEditable(false);
		textAreaDescription.setEditable(false);
		saveButton.setVisible(false);
		deleteButton.setVisible(false);
		editButton.setVisible(true);
	}
	
	public void editButton_onAction()
	{
		System.out.println("EditButton action");
		textFieldTitle.setEditable(true);
		textFieldVenue.setEditable(true);
		textAreaDescription.setEditable(true);
		saveButton.setVisible(true);
		deleteButton.setVisible(true);
		editButton.setVisible(false);
	}
	
	public void deleteButton_onAction()
	{
		System.out.println("DeleteButton action");
	}

}
