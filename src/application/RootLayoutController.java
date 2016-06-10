package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import application.model.CalendarEntry;
import application.util.CalendarEntryConverter;
import application.util.DataBaseConnection;
import javafx.application.Platform;
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
	
	public void menuItemFileSaveToXml_onAction() // TODO refactor serializing
	{
		XStream xstream = new XStream(new DomDriver());
		File file = new File("CallendarEntries.txt");
		
		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file)))
		{
			file.createNewFile();
			List<CalendarEntry> list = new ArrayList<CalendarEntry>();
			for (CalendarEntry callendarEntry : mainApp.getCallendarEntriesObservableList())
			{
				list.add(callendarEntry);
			}
			String temp2 = xstream.toXML(list);
			bufferedWriter.write(temp2);
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
		
		TextInputDialog dialog = new TextInputDialog("CallendarEntries.txt");
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
			ObservableList<CalendarEntry> tempList = (ObservableList<CalendarEntry>)xstream.fromXML(file);
			mainApp.getCallendarEntriesObservableList().setAll(tempList);
		}
		
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void menuItemFileSaveToDataBase_onAction()
	{
		DataBaseConnection dataBaseConncetion = new DataBaseConnection(
				"eu-cdbr-azure-west-d.cloudapp.net",
				"bazadanychorganizer",
				"b49f86d8da8665",
				"3c8f720c");
		dataBaseConncetion.SaveCallendarEntries(mainApp.getCallendarEntriesObservableList());
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Database");
		alert.setHeaderText("Data successfully saved to database");
		alert.showAndWait();
	}
	
	public void menuItemFileLoadFromDataBase_onAction()
	{
		DataBaseConnection dataBaseConncetion = new DataBaseConnection(
				"eu-cdbr-azure-west-d.cloudapp.net",
				"bazadanychorganizer",
				"b49f86d8da8665",
				"3c8f720c");
		mainApp.getCallendarEntriesObservableList().setAll(dataBaseConncetion.LoadCallendarEntries());
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Database");
		alert.setHeaderText("Data successfully loaded from database");
		alert.showAndWait();
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
		
		System.out.println("aaaaaaa");
	}
}
