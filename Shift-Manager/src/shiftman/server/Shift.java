package shiftman.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Shift implements Comparable<Shift>{

	private Time _shiftTime;
	private int _minWorkers;
	private List<Staff> _staff = new ArrayList<Staff>();
	private Staff _manager = null;


	public Shift(Time times, String minimumWorkers) {
		_shiftTime = times;
		_minWorkers = Integer.parseInt(minimumWorkers);
	}
	
	

	public boolean sameTime(Shift shift) { // checks if shifts start and end times are the same
		if(_shiftTime.equalTimes(shift._shiftTime)) {
			return true;
		}
		return false;
	}
	
	

	public boolean fitsInWorkingHours(Time workingHours) { // checks if shift will fit into specified times
		return workingHours.timeRangeContains(_shiftTime);
	}

	
	
	public String shiftTime() { // returns a formatted version of the shift start and end times
		return ("[" + _shiftTime.timePeriod() + "]");
	}
	
	

	public boolean shiftOverlap(Shift shift) { // checks if the shift overlaps a supplied shift
		return _shiftTime.overlap(shift._shiftTime);
	}
	
	


	public void addStaff(Staff staff, boolean isManager) throws ClientInputException{


		if (_manager != null) { // checks if they are already a manager on the shift
			if (_manager.sameNameAs(staff)) {
				throw new ClientInputException("ERROR: staff member is already a manager on the specified shift");
			}
		}

		for (Staff i : _staff) { // checks if they are already a staff member on the shift
			if (i.sameNameAs(staff)) {
				throw new ClientInputException("ERROR: staff member is already working on the specified shift");
			}
		}

		if (isManager) { // adds the staff member to the correct field depending on the isManager boolean
			if (_manager == null) {
				_manager = staff;
			}
			else {
				throw new ClientInputException("ERROR: Another manager is already rostered on for the specified shift");
			}
		}
		else {
			_staff.add(staff);
		}

	}
	
	

	public boolean overstaffed() {
		if (_staff.size() > _minWorkers) {
			return true;
		}
		return false;
	}
	
	

	public boolean understaffed() {
		if (_staff.size() < _minWorkers) {
			return true;
		}
		return false;
	}

	
	
	public boolean isManaged() { // helper function to check if shift has manager
		if(_manager != null) {
			return true;
		}
		return false;
	}
	
	

	public boolean isManager(String managerName) { // checks if specified name is the name of the manager
		if (_manager.name(false).equals(managerName)) {
			return true;
		}
		return false;
	}
	
	

	public boolean isWorking(String managerName) { // checks if specified name is the name of a worker
		for (Staff i : _staff) {
			if (i.name(false).equals(managerName)) {
				return true;
			}
		}
		return false;
	}
	
	

	public String managerDescription() { // returns a formatted description of the manager name for the shift
		if (_manager != null) {
			return "Manager: " + _manager.name(true);
		}
		return "[No manager assigned]";
	}
	
	

	public String workersList() { // returns a formatted list of staff working on the shift
		if (_staff.isEmpty()) {
			return "[No workers assigned]";
		}

		Collections.sort(_staff);
		String workerList = "[";

		for (Staff i : _staff) { // populates worker list
			if (_staff.indexOf(i) == (_staff.size() - 1)) { // if at the last element of _staff list add closing bracket for end of staff list
				workerList = workerList + i.name(false) + "]";
			}
			else {
				workerList = workerList + i.name(false) + ", "; // separate staff names by commas	
			}

		}

		return workerList;
	}

	
	
	public int compareTo(Shift shift) { // specifies sorting behavior (there is no case where start times are equal, prevented by duplicate shift checking)
		if (_shiftTime.before(shift._shiftTime)) {
			return -1;
		}
		else {
			return 1;
		}
	}

}
// 