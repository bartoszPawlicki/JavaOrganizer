package application.util;

import application.model.CalendarEntry;
import javafx.collections.ObservableList;

public class CalendarEntryConverter
{
	public static String ConvertToiCalendarFormat(ObservableList<CalendarEntry> callendarEntryArrayList)
	{
		String documentPrefix = "BEGIN:VCALENDAR\n";
		String documentSufix = "END:VCALENDAR\n";
		
		String eventPrefix = "BEGIN:VEVENT\n";
		String eventSufix = "END:VEVENT\n";
		
		String alarmPrefix = "BEGIN:VALARM\n";
		String alarmSufix = "END:VALARM\n";
		
		StringBuffer returnStatement = new StringBuffer();
		
		returnStatement.append(documentPrefix);
		for(CalendarEntry calendarEntry : callendarEntryArrayList)
		{
			returnStatement.append(eventPrefix);
			returnStatement.append("DESCRIPTION:" + calendarEntry.getDescription()+ "\n");
			returnStatement.append("DTSTART:" + calendarEntry.getDate().toString().replace("-", "") + "T" +
					calendarEntry.getTime().toString().replace(":", "").substring(0, 4) + "00\n");
			returnStatement.append("LOCATION:" + calendarEntry.getVenue() + "\n");
			returnStatement.append("SEQUENCE:0\n");
			returnStatement.append("SUMMARY:" + calendarEntry.getTitle()+ "\n");
			if(calendarEntry.getIsAlarm())
			{ //toString().replace(":", "").substring(0, 6)
				returnStatement.append(alarmPrefix);
				returnStatement.append("SoundPath:\n");
				returnStatement.append("ACTION:DISPLAY\n");
				returnStatement.append("SUMMARY:" + calendarEntry.getTitle() + "\n");
				
				String time = "";
				if(calendarEntry.getAlarmTimeBeforeEntry().getMinute() == 0)
				{
					time = calendarEntry.getAlarmTimeBeforeEntry().getHour() + "H";
				}
				else if(calendarEntry.getAlarmTimeBeforeEntry().getHour() == 0)
				{
					time = calendarEntry.getAlarmTimeBeforeEntry().getMinute() + "M";
				}
				else
					throw new IllegalArgumentException();
				
				returnStatement.append("TRIGGER:-PT" + time + "\n");
				returnStatement.append("AlarmTony:alert\n");
				returnStatement.append(alarmSufix);
			}
			returnStatement.append(eventSufix);
		}
		returnStatement.append(documentSufix);
		
		return returnStatement.toString();
	}
}
