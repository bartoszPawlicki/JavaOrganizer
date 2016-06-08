package application;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Callback;
import jfxtras.scene.control.CalendarPicker;

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
	
	//@FXML
	//private TableView<CallendarEntry> tableView;
	
	//@FXML
	//private TableColumn<CallendarEntry, String> tableColumn1;
	
	@FXML
	private CalendarPicker calendarPicker;
	
	@FXML
	private ListView<CallendarEntry> listView;
	
	public void setMainApp(MainApp mainApp) 
	{
        this.mainApp = mainApp;

        // Add observable list data to the table
        //tableView.setItems(mainApp.getCallendarEntriesObservableList());
        listView.setItems(mainApp.getCallendarEntriesObservableList());
        
    }
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{		
		calendarPicker.calendarProperty().addListener((observable) -> {
			
			calendarPickerDayChosen_onAction();
		        });
		listComboboxItems = FXCollections.observableArrayList();
		listComboboxItems.add("All events");
		listComboboxItems.add("This month");
		listComboboxItems.add("This week");
		listComboboxItems.add("Today");
		comboboxFiltre.setItems(listComboboxItems);
		
		
		
		//tableColumn1.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
		
		
		

		
		//tableColumnItemClick_onAction(null);
		//tableView.getSelectionModel().selectedItemProperty().addListener(
	            //(observable, oldValue, newValue) -> tableColumnItem_onAction(newValue));
		
		listView.setCellFactory(new Callback<ListView<CallendarEntry>, 
	            ListCell<CallendarEntry>>() {
	                @Override 
	                public ListCell<CallendarEntry> call(ListView<CallendarEntry> listView) {
	                    return new TextFieldCell();
	                }
	            }
		);
		
		listView.getSelectionModel().selectedItemProperty().addListener(
	           (observable, oldValue, newValue) -> tableColumnItem_onAction(newValue));
		
	}
	
	static class TextFieldCell extends ListCell<CallendarEntry> {
        @Override
        public void updateItem(CallendarEntry item, boolean empty) {
            super.updateItem(item, empty);
            //Label field = new Label();
            if (item != null) {
                //field.setText(item.getTitle());
                setText(item.getTitle());
                System.out.println("List View Update" + item.getTitle());
                
               // setGraphic(field);
            }
            else {
            	setText("");
            }
        }
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
		
		//tableView.getSelectionModel().getSelectedItem().setTitle(textFieldTitle.getText());
		//tableView.getSelectionModel().getSelectedItem().setVenue(textFieldVenue.getText());
		//tableView.getSelectionModel().getSelectedItem().setDescription(textAreaDescription.getText());
		
		listView.getSelectionModel().getSelectedItem().setTitle(textFieldTitle.getText());
		listView.getSelectionModel().getSelectedItem().setVenue(textFieldVenue.getText());
		listView.getSelectionModel().getSelectedItem().setDescription(textAreaDescription.getText());
			
		
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
		alert.setHeaderText("You are about to delete callendar entry: " + listView.getSelectionModel().getSelectedItem().getTitle() );
		alert.setContentText("Are you sure to delete this entry?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK)
		{
			mainApp.getCallendarEntriesObservableList().remove(listView.getSelectionModel().getSelectedItem());
		} 
		changeButtonVisibilityOnSave();
		listView.refresh();
	}
	
	public void tableColumnItem_onAction(CallendarEntry callendarEntry)
	{
		System.out.println("Item in table chosen");
		if (callendarEntry != null) 
		{
	        textFieldTitle.setText(callendarEntry.getTitle());
	        textFieldVenue.setText(callendarEntry.getVenue());
	        textAreaDescription.setText(callendarEntry.getDescription());

	    } 
	    
	    else
	    {
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
	
	public void filterEvents (String filter, LocalDate date)
	{
		switch (filter)
		{
		case "All events":
		
			mainApp.getFilteredCallendarEntreisObservableList().setAll(mainApp.getCallendarEntriesObservableList());
			break;
			
		case "This month":
			
			mainApp.getFilteredCallendarEntreisObservableList().clear();
			for(CallendarEntry item : mainApp.getCallendarEntriesObservableList())
			{
				if(item.getDate().isAfter(LocalDate.now().minusMonths(1)))
					mainApp.getFilteredCallendarEntreisObservableList().add(item);	
			}
			break;
			
		case "This week":
			
			mainApp.getFilteredCallendarEntreisObservableList().clear();
			for(CallendarEntry item : mainApp.getCallendarEntriesObservableList())
			{
				if(item.getDate().isAfter(LocalDate.now().minusWeeks(1)))
					mainApp.getFilteredCallendarEntreisObservableList().add(item);	
			}
			break;
			
		case "Today":
			
			mainApp.getFilteredCallendarEntreisObservableList().clear();
			for(CallendarEntry item : mainApp.getCallendarEntriesObservableList())
			{
				if(item.getDate().isAfter(LocalDate.now().minusDays(1)))
					mainApp.getFilteredCallendarEntreisObservableList().add(item);
			}
			break;
			
		case "Other day":
			mainApp.getFilteredCallendarEntreisObservableList().clear();
			for(CallendarEntry item : mainApp.getCallendarEntriesObservableList())
			{
				if(item.getDate().isEqual(date))
					mainApp.getFilteredCallendarEntreisObservableList().add(item);
				else System.out.println(item.getDate() + "   " + date);
			}
			break;
			
		default:
			break;
			
		}
		listView.setItems(mainApp.getFilteredCallendarEntreisObservableList());
	}
	
	public void comboBoxFiltre_onAction()
	{
		String chosenFiltre = comboboxFiltre.getSelectionModel().getSelectedItem();
		filterEvents(chosenFiltre, DateConverter.parse(chosenFiltre));
	}
	
	public void calendarPickerDayChosen_onAction()
	{
		System.out.println(calendarPicker.getCalendar().getTime());
		System.out.println(DateConverter.format(calendarPicker.getCalendar().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
		
		LocalDate date = calendarPicker.getCalendar().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		if(listComboboxItems.size()>4)
		{
			listComboboxItems.remove(4);
			comboboxFiltre.setItems(listComboboxItems);
		}
				
		listComboboxItems.add(DateConverter.format(date));
		filterEvents("Other day", date);
		// TODO set selection of combobox
	}
}
