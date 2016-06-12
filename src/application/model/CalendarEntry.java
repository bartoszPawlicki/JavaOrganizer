package application.model;

import java.time.LocalDate;
import java.time.LocalTime;

import application.util.DateConverter;
import javafx.beans.property.*;

public class CalendarEntry 
{
	private final StringProperty title;
    private final StringProperty venue;
    private final StringProperty description;
    private final ObjectProperty<LocalDate> date;
    private final ObjectProperty<LocalTime> time;
    private final ObjectProperty<Boolean> isAlarm;
    private final ObjectProperty<LocalTime> alarmTimeBeforeEntry;
    private final StringProperty alarmStringBeforeEntry;
   
    
	public CalendarEntry(String title, String venue, String description) 
	{
		this.title = new SimpleStringProperty (title);
		this.venue = new SimpleStringProperty (venue);
		this.description = new SimpleStringProperty (description);
		this.date = new SimpleObjectProperty<LocalDate>(LocalDate.now());
		this.time = new SimpleObjectProperty<LocalTime>(LocalTime.now());
		this.isAlarm = new SimpleObjectProperty<Boolean>(false);
		this.alarmTimeBeforeEntry = new SimpleObjectProperty<LocalTime>(null);
		this.alarmStringBeforeEntry = new SimpleStringProperty(null);
	}
	
	public CalendarEntry(String title, String venue, String description, LocalDate date, LocalTime time, Boolean isAlarm, LocalTime alarmTimeBeforeEntry, String alarmStringBeforeEntry) 
	{
		this.title = new SimpleStringProperty (title);
		this.venue = new SimpleStringProperty (venue);
		this.description = new SimpleStringProperty (description);
		this.date = new SimpleObjectProperty<LocalDate>(date);
		this.time = new SimpleObjectProperty<LocalTime>(time);
		this.isAlarm = new SimpleObjectProperty<Boolean>(isAlarm);
		this.alarmTimeBeforeEntry = new SimpleObjectProperty<LocalTime>(alarmTimeBeforeEntry);
		this.alarmStringBeforeEntry = new SimpleStringProperty(alarmStringBeforeEntry);
	}
	
	public CalendarEntry(String title, String venue, String description, String date)
	{
		this.title = new SimpleStringProperty (title);
		this.venue = new SimpleStringProperty (venue);
		this.description = new SimpleStringProperty (description);
		this.date = new SimpleObjectProperty<LocalDate>(LocalDate.from(DateConverter.parse(date)));
		//temporary
		this.time = new SimpleObjectProperty<LocalTime>(LocalTime.now());
		this.isAlarm = new SimpleObjectProperty<Boolean>(false);
		this.alarmTimeBeforeEntry = new SimpleObjectProperty<LocalTime>(null);
		this.alarmStringBeforeEntry = new SimpleStringProperty(null);
	}
	
	public CalendarEntry(SerializationCalendarEntry serialziationCalendarEntry)
	{
		this.title = new SimpleStringProperty(serialziationCalendarEntry.getTitle());
    	this.venue = new SimpleStringProperty(serialziationCalendarEntry.getVenue());
    	this.description = new SimpleStringProperty(serialziationCalendarEntry.getDescription());
    	this.date = new SimpleObjectProperty<LocalDate>(serialziationCalendarEntry.getDate());
    	this.time = new SimpleObjectProperty<LocalTime>(serialziationCalendarEntry.getTime());
    	this.isAlarm = new SimpleObjectProperty<Boolean>(serialziationCalendarEntry.getIsAlarm());
    	this.alarmTimeBeforeEntry = new SimpleObjectProperty<LocalTime>(serialziationCalendarEntry.getAlarmTimeBeforeEntry());
    	this.alarmStringBeforeEntry = new SimpleStringProperty(serialziationCalendarEntry.getAlarmStringBeforeEntry());
	}
	
	@Override
	public String toString()
	{
		return getTitle();
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
    
    public LocalDate getDate() 
    {
        return date.get();
    }

    public void setDate(LocalDate date) 
    {
        this.date.set(date);
    }

    public ObjectProperty<LocalDate> dateProperty() 
    {
        return date;
    }

	public LocalTime getTime()
	{
		return time.get();
	}	
	
    public void setTime(LocalTime time) 
    {
        this.time.set(time);
    }

    public ObjectProperty<LocalTime> timeProperty() 
    {
        return time;
    }

	
	public Boolean getIsAlarm()
	{
		return isAlarm.get();
	}	
	
    public void setIsAlarm(Boolean isAlarm) 
    {
        this.isAlarm.set(isAlarm);
    }

    public ObjectProperty<Boolean> isAlarmProperty() 
    {
        return isAlarm;
    }

	public LocalTime getAlarmTimeBeforeEntry()
	{
		return alarmTimeBeforeEntry.get();
	}
	
	public void setAlarmTimeBeforeEntry(LocalTime alarmTimeBeforeEntry)
	{
		this.alarmTimeBeforeEntry.set(alarmTimeBeforeEntry);
	}
	
	public ObjectProperty<LocalTime> alarmTimeBeforeEntryProperty()
	{
		return alarmTimeBeforeEntry;
	}
	
	public String getAlarmStringBeforeEntry()
	{
		return alarmStringBeforeEntry.get();
	}
	
	public void setAlarmStringBeforeEntry(String alarmStringBeforeEntry)
	{
		this.alarmStringBeforeEntry.set(alarmStringBeforeEntry);
	}
	
	public StringProperty alarmStringBeforeEntryProperty()
	{
		return alarmStringBeforeEntry;
	}
}

