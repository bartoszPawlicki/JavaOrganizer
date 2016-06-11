package application.model;

import java.time.LocalDate;
import java.time.LocalTime;


public class SerializationCalendarEntry
{
	private String title;
	private String venue;
	private String description;
	private LocalDate date;
	private LocalTime time;
	private Boolean isAlarm;
	private LocalTime alarmTimeBeforeEntry;
	private String alarmStringBeforeEntry;
	
	public SerializationCalendarEntry (CalendarEntry calendarEntry)
    {
    	this.title = calendarEntry.getTitle();
    	this.venue = calendarEntry.getVenue();
    	this.description = calendarEntry.getDescription();
    	this.date = calendarEntry.getDate();
    	this.time = calendarEntry.getTime();
    	this.isAlarm = calendarEntry.getIsAlarm();
    	this.alarmTimeBeforeEntry = calendarEntry.getAlarmTimeBeforeEntry();
    	this.alarmStringBeforeEntry = calendarEntry.getAlarmStringBeforeEntry();
    }
	
	public String getTitle()
	{
		return title;
	}

	public String getVenue()
	{
		return venue;
	}

	public String getDescription()
	{
		return description;
	}

	public LocalDate getDate()
	{
		return date;
	}

	public LocalTime getTime()
	{
		return time;
	}

	public Boolean getIsAlarm()
	{
		return isAlarm;
	}

	public LocalTime getAlarmTimeBeforeEntry()
	{
		return alarmTimeBeforeEntry;
	}

	public String getAlarmStringBeforeEntry()
	{
		return alarmStringBeforeEntry;
	}

	
}
