package application;

import java.time.LocalDate;
import javafx.beans.property.*;

public class CallendarEntry 
{
	private StringProperty title;
    private StringProperty venue;
    private StringProperty description;
    //private final ObjectProperty<LocalDate> date;
    

	public CallendarEntry(String title, String venue, String description) 
	{
		this.title = new SimpleStringProperty (title);
		this.venue = new SimpleStringProperty (venue);
		this.description = new SimpleStringProperty (description);
	}
	
	public StringProperty getTitle() 
	{
		return title;
	}


	public StringProperty getVenue() 
	{
		return venue;
	}


	public StringProperty getDescription() 
	{
		return description;
	}


	public void setTitle(StringProperty title) 
	{
		this.title = title;
	}


	public void setVenue(StringProperty venue) 
	{
		this.venue = venue;
	}


	public void setDescription(StringProperty description) 
	{
		this.description = description;
	}
       
}

