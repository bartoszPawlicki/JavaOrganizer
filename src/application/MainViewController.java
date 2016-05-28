package application;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

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
	private Button addNewEventButton;
	
	@FXML
	private Button confrimAddingNewEventButton;
	
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
		
		
		
		tableColumns.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
	
		//tableColumnItemClick_onAction(null);
		tableView.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> tableColumnItem_onAction(newValue));
		
	}
	
	public void changeButtonVisibilityOnSave()
	{
		textFieldTitle.setEditable(false);
		textFieldVenue.setEditable(false);
		textAreaDescription.setEditable(false);
		saveButton.setVisible(false);
		deleteButton.setVisible(false);
		editButton.setVisible(true);
	}
	
	public void saveButton_onAction()
	{
		System.out.println("SaveButton action");
		changeButtonVisibilityOnSave();
		
		tableView.getSelectionModel().getSelectedItem().setTitle(textFieldTitle.getText());
		tableView.getSelectionModel().getSelectedItem().setVenue(textFieldVenue.getText());
		tableView.getSelectionModel().getSelectedItem().setDescription(textAreaDescription.getText());
		
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
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete entry");
		alert.setHeaderText("You are about to delete callendar entry: " + tableView.getSelectionModel().getSelectedItem().getTitle() );
		alert.setContentText("Are you sure to delete this entry?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK)
		{
			mainApp.getCallendarEntriesObservableList().remove(tableView.getSelectionModel().getSelectedIndex());
		} 
		changeButtonVisibilityOnSave();
	}
	
	public void tableColumnItem_onAction(CallendarEntry callendarEntry)
	{
		System.out.println("Item in table chosen");
		if (callendarEntry != null) {
	        // Fill the labels with info from the person object.
	        textFieldTitle.setText(callendarEntry.getTitle());
	        textFieldVenue.setText(callendarEntry.getVenue());
	        textAreaDescription.setText(callendarEntry.getDescription());

	    } 
	    
	    else
	    {
	        //TO DO: set labels to empty strings
	    	textFieldTitle.setText("");
	        textFieldVenue.setText("");
	        textAreaDescription.setText("");
	    }	
	}
	
	public void addNewEventButton_onAction()
	{
		System.out.println("AddNewEventButtonClicked");
		tableColumnItem_onAction(null);
		
		textFieldTitle.setEditable(true);
		textFieldVenue.setEditable(true);
		textAreaDescription.setEditable(true);
		saveButton.setVisible(false);
		deleteButton.setVisible(false);
		editButton.setVisible(false);
		confrimAddingNewEventButton.setVisible(true);
		
	}
	
	public void confirmAddingNewEventButton_onAction()
	{
		if (textFieldTitle.getText().length()<1 || textFieldVenue.getText().length()<1 )
		{
			Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(mainApp.getPrimaryStage());
	        alert.setTitle("Adding new event failed!");
	        alert.setHeaderText("No title and/or venue specified");
	        alert.setContentText("Please specify at least title and venue to add new event");

	        alert.showAndWait();
		}
		else
		{
			mainApp.getCallendarEntriesObservableList().add(
					new CallendarEntry(textFieldTitle.getText(), textFieldVenue.getText(), textAreaDescription.getText()));
			changeButtonVisibilityOnSave();
			confrimAddingNewEventButton.setVisible(false);
		}
		
	}

}
