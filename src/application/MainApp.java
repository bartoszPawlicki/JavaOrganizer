package application;

import java.io.IOException;

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
    
    private ObservableList<CallendarEntry> callendarEntriesObservableList = FXCollections.observableArrayList();
    
    public ObservableList<CallendarEntry> getCallendarEntriesObservableList()
	{
		return callendarEntriesObservableList;
	}

    
    public MainApp()
    {
    	callendarEntriesObservableList.add(new CallendarEntry("nazwa wydarzenia 1", "zadupie", "Wydarzenie na zadupiu"));
    	callendarEntriesObservableList.add(new CallendarEntry("Otwarcie parasola w dupie", "Palac prezydencki", "Wielkie otwarcie"));
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
            loader.setLocation(MainApp.class.getResource("RootLayout.fxml"));
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
            loader.setLocation(MainApp.class.getResource("MainView.fxml"));
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
    public static void main(String[] args) {
        launch(args);
    }
}