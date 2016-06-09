package application;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import java.util.ResourceBundle;

import application.model.CalendarEntry;
import application.util.DateConverter;
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
	private ComboBox<String> comboboxAlarms;
	private ObservableList<String> listComboboxAlarmItems;
	@FXML
	private Button addNewEventButton;
	@FXML
	private Button confrimAddingNewEventButton;
	@FXML
	private CalendarPicker calendarPicker;
	@FXML
	private ListView<CalendarEntry> listView;
	
	public void setMainApp(MainApp mainApp) 
	{
        this.mainApp = mainApp;
        listView.setItems(mainApp.getCallendarEntriesObservableList());
    }
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{		
		listComboboxAlarmItems = FXCollections.observableArrayList();
		listComboboxAlarmItems.add("Without reminder");
		listComboboxAlarmItems.add("5 min");
		listComboboxAlarmItems.add("10 min");
		listComboboxAlarmItems.add("15 min");
		listComboboxAlarmItems.add("30 min");
		listComboboxAlarmItems.add("60 min");
		listComboboxAlarmItems.add("2 hours");
		listComboboxAlarmItems.add("3 hours");
		listComboboxAlarmItems.add("6 hours");
		listComboboxAlarmItems.add("12 hours");
		listComboboxAlarmItems.add("24 hours");
		comboboxAlarms.setItems(listComboboxAlarmItems);
		
		listComboboxItems = FXCollections.observableArrayList();
		listComboboxItems.add("All events");
		listComboboxItems.add("This month");
		listComboboxItems.add("This week");
		listComboboxItems.add("Today");
		comboboxFiltre.setItems(listComboboxItems);
		
		listView.setCellFactory(new Callback<ListView<CalendarEntry>, 
	            ListCell<CalendarEntry>>() {
	                @Override 
	                public ListCell<CalendarEntry> call(ListView<CalendarEntry> listView) {
	                    return new TextFieldCell();
	                }
	            }
		);
		
		listView.getSelectionModel().selectedItemProperty().addListener(
	           (observable, oldValue, newValue) -> tableColumnItem_onAction(newValue));
		
		calendarPicker.calendarProperty().addListener((observable) -> {
			calendarPickerDayChosen_onAction();
		        });
		
	}
	
	static class TextFieldCell extends ListCell<CalendarEntry> {
        @Override
        public void updateItem(CalendarEntry item, boolean empty) {
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
	
	public void tableColumnItem_onAction(CalendarEntry callendarEntry)
	{
		System.out.println("Item in table chosen");
		if (callendarEntry != null) 
		{
	        textFieldTitle.setText(callendarEntry.getTitle());
	        textFieldVenue.setText(callendarEntry.getVenue());
	        textAreaDescription.setText(callendarEntry.getDescription());
	        
	        //TODO Selecting date in calendar when selecting entry in listView
	        
	        //Date date = Date.from(callendarEntry.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
	        //calendarPicker.getCalendar().setTime(date);
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
					new CalendarEntry(textFieldTitle.getText(), textFieldVenue.getText(), textAreaDescription.getText()));
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
			for(CalendarEntry item : mainApp.getCallendarEntriesObservableList())
			{
				if(item.getDate().isAfter(LocalDate.now().minusMonths(1)))
					mainApp.getFilteredCallendarEntreisObservableList().add(item);	
			}
			break;
			
		case "This week":
			
			mainApp.getFilteredCallendarEntreisObservableList().clear();
			for(CalendarEntry item : mainApp.getCallendarEntriesObservableList())
			{
				if(item.getDate().isAfter(LocalDate.now().minusWeeks(1)))
					mainApp.getFilteredCallendarEntreisObservableList().add(item);	
			}
			break;
			
		case "Today":
			
			mainApp.getFilteredCallendarEntreisObservableList().clear();
			for(CalendarEntry item : mainApp.getCallendarEntriesObservableList())
			{
				if(item.getDate().isAfter(LocalDate.now().minusDays(1)))
					mainApp.getFilteredCallendarEntreisObservableList().add(item);
			}
			break;
			
		case "Other day":
			mainApp.getFilteredCallendarEntreisObservableList().clear();
			for(CalendarEntry item : mainApp.getCallendarEntriesObservableList())
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
		}
				
		listComboboxItems.add(DateConverter.format(date));
		filterEvents("Other day", date);
		comboboxFiltre.getSelectionModel().select(4);
	}
}
