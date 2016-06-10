package application.util;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import application.model.CalendarEntry;
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
                	CalendarEntry tmpCallendarEntry = new CalendarEntry(
                			resultSet.getString(2),
                			resultSet.getString(3),
                			resultSet.getString(4), 
                			resultSet.getDate(5).toLocalDate(),
                			resultSet.getTime(6).toLocalTime(),
                			resultSet.getBoolean(7),
                			resultSet.getTime(6).toLocalTime(),
                			resultSet.getString(8));
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
	
	public void SaveCallendarEntries(ObservableList<CalendarEntry> callendarEntryArrayList)
	{
		for(CalendarEntry callendarEntry : callendarEntryArrayList)
			SaveCallendarEntry(callendarEntry);
	}
	
	public void SaveCallendarEntry(CalendarEntry callendarEntry)
	{
		LocalDate localDate = callendarEntry.getDate();
		String date = localDate.getYear() + "-" + localDate.getMonthValue() + "-" + localDate.getDayOfMonth();
		
		String query = "INSERT INTO callendarentry (title, venue, description, entry_date) "
				+ "VALUES (\"" +  callendarEntry.getTitle()
				+ "\", \"" + callendarEntry.getVenue()
				+ "\", \"" + callendarEntry.getDescription()
				+ "\", \"" + localDate.toString()
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
