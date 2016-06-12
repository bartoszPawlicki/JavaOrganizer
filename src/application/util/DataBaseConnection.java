package application.util;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import application.model.CalendarEntry;
import javafx.collections.ObservableList;

public class DataBaseConnection
{
	private String URLConncetion;
	
	public DataBaseConnection()
	{
		this("eu-cdbr-azure-west-d.cloudapp.net",
					"bazadanychorganizer",
					"b49f86d8da8665",
					"3c8f720c");
	}
	
	public DataBaseConnection(String dataSource, String database, String user, String password)
	{
        URLConncetion = "jdbc:mysql://" 
        		+ dataSource + "/"
        		+ database
        		+ "?user=" + user 
        		+ "&password=" + password;
	}
	
	public ArrayList<CalendarEntry> LoadCallendarEntries()
	{
		ArrayList<CalendarEntry> callendarEntryArrayList = new ArrayList<CalendarEntry>();
		
        String query = "Select * FROM callendarentry";
        
        try (Connection connection = DriverManager.getConnection(URLConncetion)) 
        {
        	Class.forName("com.mysql.jdbc.Driver");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) 
            {
            	CalendarEntry tmpCallendarEntry;
            	if(resultSet.getBoolean(7))
            	{
            		tmpCallendarEntry= new CalendarEntry(
                			resultSet.getString(2),
                			resultSet.getString(3),
                			resultSet.getString(4), 
                			resultSet.getDate(5).toLocalDate(),
                			resultSet.getTime(6).toLocalTime(),
                			true,
                			resultSet.getTime(6).toLocalTime(),
                			resultSet.getString(8));
            	}
            	else
            	{
            		tmpCallendarEntry= new CalendarEntry(
                			resultSet.getString(2),
                			resultSet.getString(3),
                			resultSet.getString(4), 
                			resultSet.getDate(5).toLocalDate(),
                			resultSet.getTime(6).toLocalTime(),
                			false,
                			null,
                			null);
            	}
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
	
	public String SaveCallendarEntries(ObservableList<CalendarEntry> callendarEntryArrayList)
	{
		StringBuffer returnStatement = new StringBuffer();
		for(CalendarEntry callendarEntry : callendarEntryArrayList)
			returnStatement.append(SaveCallendarEntry(callendarEntry));
		return returnStatement.toString();
	}
	
	public String SaveCallendarEntry(CalendarEntry calendarEntry)
	{
		StringBuffer returnStatement = new StringBuffer();
		String query;
		if(calendarEntry.getIsAlarm())
		{
			query = "INSERT INTO callendarentry (title, venue, description, entry_date, entry_time, isAlarm, alarmTimeBeforeEntry, alarmStringBeforeEntry) "
					+ "VALUES (\"" +  calendarEntry.getTitle()
					+ "\", \"" + calendarEntry.getVenue()
					+ "\", \"" + calendarEntry.getDescription()
					+ "\", \"" + calendarEntry.getDate().toString()
					+ "\", \"" + calendarEntry.getTime().toString()
					+ "\", " + calendarEntry.getIsAlarm().toString()
					+ ", \"" + calendarEntry.getAlarmTimeBeforeEntry().toString()
					+ "\", \"" + calendarEntry.getAlarmStringBeforeEntry()
					+ "\")";	
		}
		else
		{
			query = "INSERT INTO callendarentry (title, venue, description, entry_date, entry_time, isAlarm) "
					+ "VALUES (\"" +  calendarEntry.getTitle()
					+ "\", \"" + calendarEntry.getVenue()
					+ "\", \"" + calendarEntry.getDescription()
					+ "\", \"" + calendarEntry.getDate().toString()
					+ "\", \"" + calendarEntry.getTime().toString()
					+ "\", " + calendarEntry.getIsAlarm().toString()
					+ ")";
		}
        try (Connection connection = DriverManager.getConnection(URLConncetion)) 
        {
                Class.forName("com.mysql.jdbc.Driver");
                Statement statement = connection.createStatement();
                returnStatement.append("\n(" + statement.executeUpdate(query) + ") row(s) affected");
        }     
	    catch(ClassNotFoundException wyjatek) 
	    {
	    	returnStatement.append("\nProblem with com.mysql.jdbc.Driver");
	    }
	    catch(SQLException wyjatek) 
	    {
	    	returnStatement.append("\n" 
	    			+ wyjatek.getMessage());
	    }
        
        return returnStatement.toString();
	}
	
	public String DeleteCallendarEntry(CalendarEntry calendarEntry)
	{
		String query = "SELECT id FROM callendarentry WHERE title = \"" 
				+ calendarEntry.getTitle() + "\" and entry_date = \"" 
				+ calendarEntry.getDate().toString() + "\"";
		
		StringBuffer returnStatement = new StringBuffer();
        try (Connection connection = DriverManager.getConnection(URLConncetion)) 
        {
        	Class.forName("com.mysql.jdbc.Driver");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if(!resultSet.next())
            	returnStatement.append("\n(0) row(s) affected");
            int id;
            while (resultSet.next()) 
            {
            	id = resultSet.getInt(1);
            	
            	String query1 = "DELETE FROM callendarentry WHERE id = " + id;
                
                try (Connection connection1 = DriverManager.getConnection(URLConncetion)) 
                {
                	Class.forName("com.mysql.jdbc.Driver");
                    Statement statement1 = connection1.createStatement();
                    returnStatement.append("\n(" + statement1.executeUpdate(query1) + ") row(s) affected");
                }
            }
        }       
        catch(ClassNotFoundException wyjatek) 
        {
        	returnStatement.append("\nProblem with com.mysql.jdbc.Driver");
        }
        catch(SQLException wyjatek) 
        {
        	returnStatement.append("\n" + wyjatek.getMessage());
        }
        
        return returnStatement.toString();
	}
}
