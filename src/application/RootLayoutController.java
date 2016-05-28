package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class RootLayoutController implements Initializable
{
	private MainApp mainApp;
	
	@FXML
	private MenuItem menuItemFileClose;
	
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
}
