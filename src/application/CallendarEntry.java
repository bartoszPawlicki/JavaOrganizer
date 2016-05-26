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
	
	public String getTitle() 
	{
        return title.get();
    }

    public void setTitle(String title) 
    {
        this.title.set(title);
    }

    public StringProperty titleProperty() 
    {
        return title;
    }
    
    public String getVenue() 
	{
        return venue.get();
    }

    public void setVenue(String venue) 
    {
        this.venue.set(venue);
    }

    public StringProperty venueProperty() 
    {
        return venue;
    }
    
    public String getDescription() 
	{
        return description.get();
    }

    public void setDescription(String description) 
    {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() 
    {
        return description;
    }
	
       
}

