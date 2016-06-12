package application;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import application.model.CalendarEntry;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application 
{
    private BorderPane rootLayout;
    private Stage primaryStage;
    private Timer timer;
    
    class AlarmTimerTask extends TimerTask
    {
    	private Alert alert;
    	
    	AlarmTimerTask(Alert alert)
    	{
    		this.alert = alert;
    	}
		@Override
		public void run() 
		{
			Calendar calendar = Calendar.getInstance();
			for(CalendarEntry calendarEntry: callendarEntriesObservableList)
			{	
				if(calendarEntry.getDate().isEqual(calendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) && calendarEntry.getIsAlarm() == true) // 
				{
					calendar.add(Calendar.HOUR_OF_DAY, +calendarEntry.getAlarmTimeBeforeEntry().getHour());
					calendar.add(Calendar.MINUTE, +calendarEntry.getAlarmTimeBeforeEntry().getMinute());
					
					if(calendar.get(Calendar.HOUR_OF_DAY) == calendarEntry.getTime().getHour() && calendar.get(Calendar.MINUTE) == calendarEntry.getTime().getMinute())
					{
				        alert.setTitle("Alarm");
				        alert.setHeaderText("You have an event in next " + calendarEntry.getAlarmStringBeforeEntry());
				        alert.setContentText("Entry title: " + calendarEntry.toString());
				        
				        Platform.runLater(new Runnable(){public void run(){alert.show();}});
				        
					}
				}
			}
		}
    }
    //wszystkie zdarzenia
    private ObservableList<CalendarEntry> callendarEntriesObservableList = FXCollections.observableArrayList();
    //obecnie wyœwietlane
    private ObservableList<CalendarEntry> filteredCallendarEntreisObservableList = FXCollections.observableArrayList();
    
    public ObservableList<CalendarEntry> getCallendarEntriesObservableList()
	{
		return callendarEntriesObservableList;
	}
    
    public ObservableList<CalendarEntry> getFilteredCallendarEntreisObservableList()
	{
		return filteredCallendarEntreisObservableList;
	}

    public MainApp()
    {
    }
    
	public Stage getPrimaryStage() 
    {
        return primaryStage;
    }
    
	@Override
    public void start(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        this.primaryStage.setMinWidth(600);
        this.primaryStage.setMinHeight(500);
        this.primaryStage.setTitle("Organizer");
        initRootLayout();
        showMainView();
        
		Alert alert = new Alert(AlertType.WARNING);
        alert.initOwner(primaryStage);
        timer = new Timer();
//        timer.schedule(new AlarmTimerTask(), 0, 60000);
        timer.schedule(new AlarmTimerTask(alert), 0, 6000);
    }

    public void initRootLayout()
    {
        try 
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            
        } catch (IOException e) 
        {
        	System.out.println(e.getMessage());
        }
    }

    public void showMainView() 
    {
        try 
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainView.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            rootLayout.setCenter(personOverview);
            
            MainViewController controller = loader.getController();
            controller.setMainApp(this);
            
            
        } catch (IOException e) 
        {
        	System.out.println(e.getMessage());
        	e.printStackTrace();
        }
    }
    
    public static void main(String[] args) 
    {
        launch(args);
    }
}