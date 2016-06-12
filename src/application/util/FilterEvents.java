package application.util;

import java.time.LocalDate;

import application.MainApp;
import application.model.CalendarEntry;

public class FilterEvents
{
	public static void filterEvents (String filter, LocalDate date, MainApp mainApp)
	{
		switch (filter)
		{
		case "All events":
		
			mainApp.getFilteredCallendarEntreisObservableList().setAll(mainApp.getCallendarEntriesObservableList());
			break;
			
		case "This month":
			
			mainApp.getFilteredCallendarEntreisObservableList().clear();
			for(CalendarEntry item : mainApp.getCallendarEntriesObservableList())
			{
				if(item.getDate().isAfter(LocalDate.now()) && item.getDate().isBefore(LocalDate.now().plusMonths(1)))
				{
					mainApp.getFilteredCallendarEntreisObservableList().add(item);	
				}
					
			}
			break;
			
		case "This week":
			
			mainApp.getFilteredCallendarEntreisObservableList().clear();
			for(CalendarEntry item : mainApp.getCallendarEntriesObservableList())
			{
				if(item.getDate().isAfter(LocalDate.now()) && item.getDate().isBefore(LocalDate.now().plusWeeks(1)))
				{
					mainApp.getFilteredCallendarEntreisObservableList().add(item);
					//System.out.println("Data wydarzenia: " + item.getDate().toString() + " Dzisiaj: " + LocalDate.now() + " Za 7 dni: " + LocalDate.now().plusDays(7));
				}
				
			}
			break;
			
		case "Today":
			
			mainApp.getFilteredCallendarEntreisObservableList().clear();
			for(CalendarEntry item : mainApp.getCallendarEntriesObservableList())
			{
				if(item.getDate().isEqual(LocalDate.now()))
					mainApp.getFilteredCallendarEntreisObservableList().add(item);
			}
			break;
			
		case "Other day":
			mainApp.getFilteredCallendarEntreisObservableList().clear();
			for(CalendarEntry item : mainApp.getCallendarEntriesObservableList())
			{
				if(item.getDate().isEqual(date))
					mainApp.getFilteredCallendarEntreisObservableList().add(item);
			}
			break;
			
		default:
			break;
			
		}
		mainApp.getController().getListView().setItems(mainApp.getFilteredCallendarEntreisObservableList());
		
		
	}
}
