package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;

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
	
	public void menuItemFileSaveToXml_onAction()
	{
		XStream xstream = new XStream(new DomDriver());
		File file = new File("CallendarEntries.txt");
		
		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file)))
		{
			file.createNewFile();
			String temp2 = xstream.toXML(mainApp.getCallendarEntriesObservableList());
			bufferedWriter.write(temp2);
		}
		
		catch (IOException e)
		{
			
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
			ObservableList<CallendarEntry> tempList = (ObservableList<CallendarEntry>)xstream.fromXML(file);
			mainApp.getCallendarEntriesObservableList().setAll(tempList);
		}
		
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
	}
}
