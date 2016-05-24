package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application 
{
    private BorderPane rootLayout;
    
    private Stage primaryStage;
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
            
        } catch (IOException e) 
        {
        	System.out.println(e.getMessage());
        	e.printStackTrace();
        }
    }
}