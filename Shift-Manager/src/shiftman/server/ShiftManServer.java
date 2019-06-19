package shiftman.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShiftManServer implements ShiftMan{
	private String _shopName = null;
	private List<Day> _days = new ArrayList<Day>();
	private List<Staff> _registeredStaff = new ArrayList<Staff>();

	public ShiftManServer() {

	}



	public String newRoster(String shopName){
		if ((shopName == null) || ("".equals(shopName))) {
			return "ERROR: no shop name provided";
		}

		if (_shopName != null) {
			_days.clear(); // clears all roster information ready for new store
			_registeredStaff.clear();

		}
		_shopName = shopName;
		return "";
	}



	public String setWorkingHours(String dayOfWeek, String startTime, String endTime){

		if (_shopName == null) {
			return "ERROR: Working Hours not set. No roster detected";
		}

		try {
			Day currentDay = new Day(dayOfWeek, startTime, endTime); // throws exception if day name is invalid


			if (_days.size() >= 7) {
				throw new ClientInputException("ERROR: All working hours already set");
			}


			for (Day i : _days) { //checks for duplicate days
				if (i.isDayName(dayOfWeek))	{
					throw new ClientInputException("ERROR: Working hours already set for specified day");
				}

			}

			_days.add(currentDay);

			return "";

		}
		catch (ClientInputException e) {
			return e.getMessage();
		}

	}




	public String addShift(String dayOfWeek, String startTime, String endTime, String minimumWorkers){

		if (_shopName == null) {
			return "ERROR: no shop name provided";
		}

		Day assignedDay;


		try {

			assignedDay = findDay(dayOfWeek); // throws exception if day of week is invalid or has no set working hours

			Time shiftTimes = new Time(startTime, endTime); // throws exception for invalid time
			Shift shiftToAdd = new Shift(shiftTimes, minimumWorkers); // create new shift
			assignedDay.scheduleShift(shiftToAdd); // throws exception for scheduling problems (overlap with other shifts, outside working hours)

			return "";

		}
		catch (ClientInputException e) {
			return e.getMessage();
		}



	}



	public String registerStaff(String givenname, String familyName){

		if (_shopName == null) {
			return "ERROR: no shop name provided";
		}

		try {

			Staff staffToAdd = new Staff(givenname, familyName); // throws exception if one or both inputs are empty strings

			if (!(_registeredStaff.isEmpty())) {
				for (Staff i : _registeredStaff) { // checks if the staff member is already registered
					if (i.sameNameAs(staffToAdd)) {
						throw new ClientInputException("ERROR: staff memeber specified is already registered");
					}
				}
			}

			_registeredStaff.add(staffToAdd);

			return "";

		}
		catch (ClientInputException e) {
			return e.getMessage();
		}


	}




	public String assignStaff(String dayOfWeek, String startTime, String endTime, String givenName, String familyName, boolean isManager) {

		if (_shopName == null) {
			return "ERROR: no shop name provided";
		}

		Staff staffMember = null;
		Day selectedDay;


		try {

			selectedDay = findDay(dayOfWeek); // throws exception if day of week is invalid or has no set working hours


			Staff comparisonStaff = new Staff(givenName, familyName); // allows comparison to registered staff members, sameNameAs method accepts type Staff

			if (!(_registeredStaff.isEmpty())) {
				for (Staff i : _registeredStaff) { // finds staff member specified if they are registered
					if (i.sameNameAs(comparisonStaff)) {
						staffMember = i;
					}
				}
			}
			if (staffMember == null) {
				throw new ClientInputException("ERROR: Staff member not registered");
			}

			selectedDay.assignStaffOnDay(startTime, endTime, staffMember, isManager); // assigns staff member on selected day 
																					  // (throws exception if there is no matching shift)
			staffMember.Assigned(); // sets staff member status to assigned (internal field isAssigned set to true)

		}
		catch (ClientInputException e) {
			return e.getMessage();
		}


		return "";

	}




	public List<String> getRegisteredStaff(){

		List<String> errorMessage = new ArrayList<String>();

		if (_shopName == null) {
			errorMessage.add("ERROR: no shop name provided");
			return errorMessage;
		}


		Collections.sort(_registeredStaff); // sorts _registeredStaff by family name
		
		List<String> registeredStaff = new ArrayList<String>();

		for (Staff i : _registeredStaff) {
			registeredStaff.add(i.name(false)); // obtains name of staff and adds to array
		}

		return registeredStaff;

	}



	public List<String> getUnassignedStaff(){


		List<String> unassignedStaff = new ArrayList<String>();
		List<String> errorMessage = new ArrayList<String>();

		if (_shopName == null) {
			errorMessage.add("ERROR: no shop name provided");
			return errorMessage;
		}


		Collections.sort(_registeredStaff); // sorts _registeredStaff by family name

		for (Staff i : _registeredStaff) {
			if (!(i.isAssigned())) {
				unassignedStaff.add(i.name(false)); // obtains name of staff that are not assigned to shifts and adds to array
			}

		}

		return unassignedStaff;
	}



	public List<String> shiftsWithoutManagers(){
		List<String> errorMessage = new ArrayList<String>();

		if (_shopName == null) {
			errorMessage.add("ERROR: no shop name provided");
			return errorMessage;
		}

		return shiftSearch("withoutManager" , null); // calls helper function to find shifts without managers

	}



	public List<String> understaffedShifts(){


		List<String> errorMessage = new ArrayList<String>();

		if (_shopName == null) {
			errorMessage.add("ERROR: no shop name provided");
			return errorMessage;
		}

		return shiftSearch("understaffed", null); // calls helper function to find understaffed
	}



	public List<String> overstaffedShifts(){

		List<String> errorMessage = new ArrayList<String>();

		if (_shopName == null) {
			errorMessage.add("ERROR: no shop name provided");
			return errorMessage;
		}

		return shiftSearch("overstaffed", null); // calls helper function to find overstaffed shifts
	}




	public List<String> getRosterForDay(String dayOfWeek){

		List<String> errorMessage = new ArrayList<String>();

		if (_shopName == null) {
			errorMessage.add("ERROR: no shop name provided");
			return errorMessage;
		}
		
		List<String> currentDayShifts = new ArrayList<String>();
		

		if (!(_days.size() == 0)) {

			Collections.sort(_days); // sorts days with working hours in natural order defined in the nested enum in Day class

			try {
				
				currentDayShifts.add(_shopName);
				Day dayFound = findDay(dayOfWeek);
				currentDayShifts.add(dayFound.dayInfo());
				currentDayShifts.addAll(dayFound.shiftsOnDay("rosterFormat", null)); // returns string array of shifts on the specified day in the roster format
			}
			catch (ClientInputException e) {
				return currentDayShifts; // will be empty if exception thrown as there are no days on the roster. Dont want to display thrown error message in this case, output should be empty
				// thrown error message intended for use with other non display methods
			}



		}

		return currentDayShifts;
	}

	
	

	public List<String> getRosterForWorker(String workerName){

		List<String> errorMessage = new ArrayList<String>();

		if (_shopName == null) {
			errorMessage.add("ERROR: no shop name provided");
			return errorMessage;
		}

		return shiftSearch("forWorker" , workerName); // calls helper function to find shifts worked by specified worker
	}



	public List<String> getShiftsManagedBy(String managerName){

		List<String> errorMessage = new ArrayList<String>();
		List<String> shiftsManagedBy = new ArrayList<String>();
		
		if (_shopName == null) {
			errorMessage.add("ERROR: no shop name provided");
			return errorMessage;
		}

		shiftsManagedBy.addAll(shiftSearch("forManager", managerName)); // calls helper function to find shifts managed by specified manager
		
		
		if (shiftsManagedBy.size() > 0) { // only adds manager name to start of array if they are managing at least one shift
			
			String[] splitName = managerName.split(" ");
			shiftsManagedBy.add(0, splitName[1] + ", " + splitName[0]);
		}
		
		return shiftsManagedBy;
	}



	public String reportRosterIssues() {
		String roster = "Roster issues for " + _shopName + "\n" + "  \n";


		roster = roster + "Shifts Without Managers \n";
		List<String> daySchedule = shiftsWithoutManagers();
		for (String shift : daySchedule) {
			roster = roster + shift + "\n";
		}
		roster = roster + "\n";


		roster = roster + "Understaffed Shifts \n";
		List<String> daySchedule2 = understaffedShifts();
		for (String shift : daySchedule2) {
			roster = roster + shift + "\n";
		}
		roster = roster + "\n";

		roster = roster + "Overstaffed Shifts \n";
		List<String> daySchedule3 = overstaffedShifts();
		for (String shift : daySchedule3) {
			roster = roster + shift + "\n";
		}
		roster = roster + "\n";



		roster = roster + "Unassigned Workers \n";
		List<String> daySchedule4 = getUnassignedStaff();
		for (String shift : daySchedule4) {
			roster = roster + shift + "\n";
		}
		roster = roster + "\n";


		return roster;



	}

	public String displayRoster() {
		String roster = "";
		if ((_shopName != null) & (_days.size() > 0)) {
			roster = roster + "Roster for " + _shopName +  "\n" + "  \n";
			String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
			
			for (String i: days) {
				List<String> daySchedule = getRosterForDay(i);
				for (String shift : daySchedule) {
					roster = roster + shift + "\n";
				}
			}
		}


		return roster;


	}


	// Helper functions below


	public Day findDay(String dayOfWeek) throws ClientInputException{

		Day selectedDay = null;

		for (Day i : _days) { // finds day specified if it has set working hours
			if(i.isDayName(dayOfWeek)) {
				selectedDay = i;
			}	
		}

		if (selectedDay == null) {
			throw new ClientInputException("ERROR: Specified day is invalid or has no set working hours");	
		}

		return selectedDay;
	}



	public List<String> shiftSearch(String searchCriteria, String staffMember){

		List<String> allFoundShifts = new ArrayList<String>();
		List<String> currentDayShifts = new ArrayList<String>();



		if (!(_days.size() == 0)) {

			Collections.sort(_days); // sorts days with working hours in natural order defined in the nested enum in Day class

			for (Day i : _days) {
				
				currentDayShifts = i.shiftsOnDay(searchCriteria, staffMember); // returns string array of shifts on current day that meet search requirement

				allFoundShifts.addAll(currentDayShifts); // adds found shifts to shifts found previously
			}
		}

		return allFoundShifts;
	}

}
