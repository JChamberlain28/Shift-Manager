package shiftman.server;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day implements Comparable<Day>{

	private String _dayOfWeek;
	private Time _workingHours;
	private List<Shift> _shifts = new ArrayList<Shift>();

	public enum daysOfWeek { // defines valid day representations and natural order for sorting
		MONDAY("Monday"), TUESDAY("Tuesday"), WEDNESDAY("Wednesday"),
		THURSDAY("Thursday"), FRIDAY("Friday"), SATURDAY("Saturday"), SUNDAY("Sunday");

		private final String _day;

		private daysOfWeek(String day) {
			_day = day;
		}


	}


	public Day(String dayOfWeek, String startTime, String endTime) throws ClientInputException {
		_dayOfWeek = dayOfWeek;

		boolean dayIsValid = false;
		for (daysOfWeek i : daysOfWeek.values()) { // checks if supplied day is a valid date in the daysOfWeek nested enum

			if (i._day.equals(_dayOfWeek)) { 
				dayIsValid = true;
			}

		}
		if (dayIsValid == false) {
			throw new ClientInputException("ERROR: Invalid day of week");
		}

		_workingHours = new Time(startTime, endTime); // time object stores working hours

	}





	public boolean isDayName(String name) { // allows any string to be compared to the _dayOfWeek field without direct access
		if (_dayOfWeek.equals(name)) {
			return true;
		}
		return false;
	}



	public void scheduleShift(Shift shift) throws ClientInputException { 


		if (!(_shifts.isEmpty())) {

			for (Shift i : _shifts) { // checks for duplicate shift and overlaps (2 loops to ensure in case when both overlap and duplicate exists, 
									  // duplicate error is thrown)
				if (i.sameTime(shift)) {
					throw new ClientInputException("ERROR: Shift already exists with same day and time"); 
				}
			}
			for (Shift i: _shifts) {
				if (i.shiftOverlap(shift)) {
					throw new ClientInputException("ERROR: Shift overlaps another scheduled shift");
				}
			}
		}


		if (!(shift.fitsInWorkingHours(_workingHours))) { // prevents shift from being scheduled outside working hours
			throw new ClientInputException("ERROR: Shift is outside of " + _dayOfWeek + " working hours of " + _workingHours.timePeriod());
		}


		_shifts.add(shift);

	}


	public void assignStaffOnDay(String startTime, String endTime, Staff staffMember, boolean isManager) throws ClientInputException{

		Time comparisonTime = new Time(startTime, endTime); // throws exception if times are invalid
		Shift comparisonShift = new Shift(comparisonTime, "0"); // creates a requested shift to compare it to existing shifts 
		Shift assignedShift = null;

		for (Shift i : _shifts) { // finds specified shift
			if(i.sameTime(comparisonShift)) {
				assignedShift = i;
			}

		}


		if (assignedShift == null) {
			throw new ClientInputException("ERROR: Shift specified doesnt exist");
		}

		assignedShift.addStaff(staffMember, isManager); // adds staff member to shift
		// throws exception if they are already rostered on the shift, or the shift is already managed (when they are a manager)
	}


	public List<String> shiftsOnDay(String searchCriteria, String staffMember){
		// returns list of shift time strings formatted [startTime - endTime] unless rosterFormat is the searchCriteria
		// roster format includes manager name and workers
		// String parameter is null unless finding shifts a staff member or manager is working on

		Collections.sort(_shifts);
		List<String> shiftTimes = new ArrayList<String>();

		for (Shift i: _shifts) { // adds formatted time string for shift to shiftTImes array
			if ("withoutManager".equals(searchCriteria)) {
				if (!(i.isManaged())) { // only adds shifts with no manager
					shiftTimes.add(i.shiftTime());	
				}
			}

			if ("understaffed".equals(searchCriteria)) {
				if (i.understaffed()) { // only adds shifts that are understaffed
					shiftTimes.add(i.shiftTime());	
				}	
			}

			if ("overstaffed".equals(searchCriteria)) {
				if (i.overstaffed()) { // only adds shifts that are overstaffed
					shiftTimes.add(i.shiftTime());	
				}	
			}

			if ("rosterFormat".equals(searchCriteria)) { // adds all shifts including manager name and workers
				String currentShiftTime = i.shiftTime() + " " + i.managerDescription() + " " + i.workersList();

				shiftTimes.add(currentShiftTime);	
			}

			if ("forManager".equals(searchCriteria)) {
				if (i.isManager(staffMember)) {
					shiftTimes.add(i.shiftTime());
				}
			}
			
			if ("forWorker".equals(searchCriteria)) {
				if (i.isWorking(staffMember)) {
					shiftTimes.add(i.shiftTime());
				}
			}

		}



		for (int i = 0; i < shiftTimes.size() ; i++) { // Add day to formatted time string
			shiftTimes.set(i, (_dayOfWeek + shiftTimes.get(i)));
		}


		return shiftTimes;
	}
	
	public String dayInfo() {
		return _dayOfWeek + " " + _workingHours.timePeriod();
	}


	public int compareTo(Day day) { // specifies sorting behavior for comparable

		int thisDayPos = 0;
		int comparisonDayPos = 0;

		for (daysOfWeek i : daysOfWeek.values()) {

			if (i._day.equals(_dayOfWeek)) { // finds position of current day in enum
				thisDayPos = i.ordinal();
			}

			if (i._day.equals(day._dayOfWeek)) { // finds position of comparison day in enum
				comparisonDayPos = i.ordinal();
			}
		}

		if (thisDayPos < comparisonDayPos) {
			return -1;
		}
		else if (thisDayPos > comparisonDayPos) {
			return 1;
		}
		else {
			return 0;
		}
	}

}


