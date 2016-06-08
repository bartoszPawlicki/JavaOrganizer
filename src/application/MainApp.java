package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MainApp extends Application 
{
    private BorderPane rootLayout;
    
    private Stage primaryStage;
    
    Callback<CallendarEntry, Observable[]> extractor = new Callback<CallendarEntry, Observable[]>() {

        @Override
        public Observable[] call(CallendarEntry c) {
            return new Observable[] {c.titleProperty(), c.venueProperty(), c.descriptionProperty()};
        }
    };
    
    private ObservableList<CallendarEntry> callendarEntriesObservableList = FXCollections.observableArrayList(extractor);
    
    private ObservableList<CallendarEntry> filteredCallendarEntreisObservableList = FXCollections.observableArrayList(extractor);
    
    public ObservableList<CallendarEntry> getCallendarEntriesObservableList()
	{
		return callendarEntriesObservableList;
	}
    
    public ObservableList<CallendarEntry> getFilteredCallendarEntreisObservableList()
	{
		return filteredCallendarEntreisObservableList;
	}

    public MainApp()
    {
    	callendarEntriesObservableList.add(new CallendarEntry("Wyd dzisiaj", "Radom", "Parasola w dupie", "08.06.2016"));
    	callendarEntriesObservableList.add(new CallendarEntry("3 dni temu", "Zgierz", "Parasola w dupie", "05.06.2016"));
    	callendarEntriesObservableList.add(new CallendarEntry("15 dni temu", "Abababa", "Parasola w dupie", "24.05.2016"));
    	callendarEntriesObservableList.add(new CallendarEntry("2 miechy temu", "SAsdasd", "Parasola w dupie", "08.04.2016"));
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