package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import application.model.CalendarEntry;
import application.model.SerializationCalendarEntry;
import application.util.CalendarEntryConverter;
import application.util.DataBaseConnection;
import application.util.DateConverter;
import application.util.FilterEvents;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;

public class RootLayoutController implements Initializable
{
	private MainApp mainApp;
	
	@FXML
	private MenuItem menuItemFileClose;
	
	@FXML
	private MenuItem menuItemFileSaveToXml;
	
	@FXML
	private MenuItem menuItemFileLoadFromXml;
	
	@FXML
	private MenuItem menuItemDeletOlderThan;
	
	@FXML
	private MenuItem menuItemHelpAbout;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		
	}
	
	public void setMainApp(MainApp mainApp) 
	{
        this.mainApp = mainApp;

    }
	
	public void menuItemFileClose_onAction() throws Exception
	{
		Platform.exit();
	}
	
	public void menuItemFileSaveToXml_onAction() 
	{
		XStream xstream = new XStream(new DomDriver());
		File file = new File("CallendarEntries.xml");
		
		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file)))
		{
			file.createNewFile();
			ObservableList<SerializationCalendarEntry> list = FXCollections.observableArrayList();
			for (CalendarEntry calendarEntry : mainApp.getCallendarEntriesObservableList())
			{
				list.add(new SerializationCalendarEntry(calendarEntry));
			}
			String temp2 = xstream.toXML(list);
			bufferedWriter.write(temp2);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Xml");
			alert.setHeaderText("Data saved to file");
			alert.showAndWait();
		}
		
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
	public void menuItemFileLoadFromXml_onAction()
	{
		XStream xstream = new XStream(new DomDriver());
		String fileName ="";
		
		TextInputDialog dialog = new TextInputDialog("CallendarEntries.xml");
		dialog.setTitle("Load from XML");
		dialog.setContentText("Please enter path to file:");
		
		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    fileName = result.get();
		}

		// The Java 8 way to get the response value (with lambda expression).
		//result.ifPresent(name -> System.out.println("Your name: " + name));
		
		File file = new File(fileName);
		
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file)))
		{
			@SuppressWarnings("unchecked")
			ObservableList<SerializationCalendarEntry> tempList = (ObservableList<SerializationCalendarEntry>)xstream.fromXML(file);
			mainApp.getCallendarEntriesObservableList().clear();
			for (SerializationCalendarEntry calendarEntry : tempList)
			{
				mainApp.getCallendarEntriesObservableList().add(new CalendarEntry(calendarEntry));
			}
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Xml");
			alert.setHeaderText("Data loaded from file");
			alert.showAndWait();
		}
		
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
		FilterEvents.filterEvents("All Events", LocalDate.now(), mainApp);
		
	}
	
	public void menuItemFileSaveToDataBase_onAction()
	{
		DataBaseConnection dataBaseConncetion = new DataBaseConnection();
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Database");
		alert.setHeaderText(dataBaseConncetion.SaveCallendarEntries(mainApp.getCallendarEntriesObservableList()));
		alert.showAndWait();
	}
	
	public void menuItemFileLoadFromDataBase_onAction()
	{
		DataBaseConnection dataBaseConncetion = new DataBaseConnection();
		mainApp.getCallendarEntriesObservableList().setAll(dataBaseConncetion.LoadCallendarEntries());
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Database");
		alert.setHeaderText("Data loaded from database");
		alert.showAndWait();
		FilterEvents.filterEvents("All Events", LocalDate.now(), mainApp);
	}
	
	public void convertToOutlook_onAction()
	{	
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File("mojplik.ics"))))
		{
			bw.write(CalendarEntryConverter.ConvertToiCalendarFormat(mainApp.getCallendarEntriesObservableList()));
		}
		catch (IOException io)
		{
			System.out.println(io.getMessage());
		}
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Convert");
		alert.setHeaderText("Data converter to iClandar format");
		alert.showAndWait();
	}
	
	public void menuItemDeleteOlderThan_onAction()
	{
		TextInputDialog dialog = new TextInputDialog("DD.MM.YYYY");
		dialog.setTitle("Delete calendar entreis");
		dialog.setHeaderText("Delete entries older than");
		dialog.setContentText("Please specify date");
		
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent())
		{
			System.out.println(result.get());
			LocalDate date = DateConverter.parse(result.get());
			
			ObservableList<CalendarEntry> tempList = FXCollections.observableArrayList();
			tempList.setAll(mainApp.getCallendarEntriesObservableList());
			
			for(CalendarEntry item : tempList)
			{
				if(item.getDate().isBefore(date))
					mainApp.getCallendarEntriesObservableList().remove(item);
					
				else System.out.println(item.getDate() + "   " + date);
			}
		}

	}
	
	public void menuItemHelpAbout_onAction()
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About Java Organizer");
		alert.setHeaderText("Java Organizer created by £ukasz Zimoñ and Bartosz Pawlicki ");
		alert.setContentText("Fantastic orgranizer which capabilities are infinite! Add, edit, delete events"
				+ "\nconnect with database, load your events from xml file or open them in Outlook via our converter!"
				+ "\n\nApplication uses: javafx libraries, jfxtras for graphical calendar, Azure MySql database with JDBC Driver for MySQL and XStream for serialization");
		alert.showAndWait();
	}
}
