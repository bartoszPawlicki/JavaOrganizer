package application.util;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import application.model.CallendarEntry;
import javafx.collections.ObservableList;

public class DataBaseConnection
{
	private String URLConncetion;
	
	public DataBaseConnection(String dataSource, String database, String user, String password)
	{
        URLConncetion = "jdbc:mysql://" 
        		+ dataSource + "/"
        		+ database
        		+ "?user=" + user 
        		+ "&password=" + password;
	}
	
	public ArrayList<CallendarEntry> LoadCallendarEntries()
	{
		ArrayList<CallendarEntry> callendarEntryArrayList = new ArrayList<CallendarEntry>();
		
        String query = "Select * FROM callendarentry";
        
        try (Connection connection = DriverManager.getConnection(URLConncetion)) 
        {
                Class.forName("com.mysql.jdbc.Driver");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) 
                {
                	CallendarEntry tmpCallendarEntry = new CallendarEntry(resultSet.getString(2),resultSet.getString(3),resultSet.getString(4));
                	callendarEntryArrayList.add(tmpCallendarEntry);
                }
        }
        catch(ClassNotFoundException wyjatek) 
        {
            System.out.println("Problem with com.mysql.jdbc.Driver");
        }

        catch(SQLException wyjatek) 
        {
            System.out.println("SQLException: " + wyjatek.getMessage());
            System.out.println("SQLState: " + wyjatek.getSQLState());
            System.out.println("VendorError: " + wyjatek.getErrorCode());
        }
		return callendarEntryArrayList;
	}
	
	public void SaveCallendarEntries(ObservableList<CallendarEntry> callendarEntryArrayList)
	{
		for(CallendarEntry callendarEntry : callendarEntryArrayList)
			SaveCallendarEntry(callendarEntry);
	}
	
	public void SaveCallendarEntry(CallendarEntry callendarEntry)
	{
		String query = "INSERT INTO callendarentry (title, venue,description) "
				+ "VALUES (\"" +  callendarEntry.getTitle()
				+ "\", \"" + callendarEntry.getVenue()
				+ "\", \"" + callendarEntry.getDescription()
				+ "\")";
		
        try (Connection connection = DriverManager.getConnection(URLConncetion)) 
        {
                Class.forName("com.mysql.jdbc.Driver");
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);
        }
        catch(ClassNotFoundException wyjatek) 
        {
            System.out.println("Problem with com.mysql.jdbc.Driver");
        }

        catch(SQLException wyjatek) 
        {
            System.out.println("SQLException: " + wyjatek.getMessage());
            System.out.println("SQLState: " + wyjatek.getSQLState());
            System.out.println("VendorError: " + wyjatek.getErrorCode());
        }
	}
}
