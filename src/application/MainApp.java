package application;

import java.io.IOException;
import application.model.CalendarEntry;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application 
{
    private BorderPane rootLayout;
    private Stage primaryStage;
    
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
    	callendarEntriesObservableList.add(new CalendarEntry("Wyd dzisiaj", "Radom", "Otwarcie parasola w dupie", "08.06.2016"));
    	callendarEntriesObservableList.add(new CalendarEntry("3 dni temu", "Zgierz", "Otwarcie parasola w dupie", "05.06.2016"));
    	callendarEntriesObservableList.add(new CalendarEntry("15 dni temu", "Abababa", "Otwarcie parasola w dupie", "24.05.2016"));
    	callendarEntriesObservableList.add(new CalendarEntry("2 miechy temu", "SAsdasd", "Otwarcie parasola w dupie", "08.04.2016"));
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