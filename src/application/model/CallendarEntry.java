package application.model;

import java.time.LocalDate;

import application.util.DateConverter;
import javafx.beans.property.*;

public class CallendarEntry 
{
	private final StringProperty title;
    private final StringProperty venue;
    private final StringProperty description;
    private final ObjectProperty<LocalDate> date;
    

	public CallendarEntry(String title, String venue, String description) 
	{
		this.title = new SimpleStringProperty (title);
		this.venue = new SimpleStringProperty (venue);
		this.description = new SimpleStringProperty (description);
		this.date = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
	}
	
	public CallendarEntry(String title, String venue, String description, String date) 
	{
		this.title = new SimpleStringProperty (title);
		this.venue = new SimpleStringProperty (venue);
		this.description = new SimpleStringProperty (description);
		
		this.date = new SimpleObjectProperty<LocalDate>(LocalDate.from(DateConverter.parse(date)));
		
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
    
    public LocalDate getDate() {
        return date.get();
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }
	
       
}

