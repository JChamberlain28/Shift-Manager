package shiftman.server;

import java.util.List;


public interface ShiftMan {

	/**
	 * Request that a new roster be started for the shop with the supplied name.
	 * <span style="color:green">Any other methods called after a call to this method will apply to the
	 * roster created by this method, until the next time this method is called.</span>
	 * @param shopName The name of the shop the roster is for.
	 * @return The status of the request.
	 */
	public String newRoster(String shopName);
	
	/**
	 * Request that the hours for the specified day be as specified by the times supplied. 
	 * @param dayOfWeek The day the request applies to.
	 * @param startTime The start of the working day.
	 * @param endTime The end of the working day.
	 * @return The status of the request.
	 */
	public String setWorkingHours(String dayOfWeek, String startTime, String endTime);
	
	/**
	 * Request that the shift specified by the supplied times be added as a shift that 
	 * staff can be assigned to for the specified day. 
	 * @param dayOfWeek The day the request applies to.
	 * @param startTime The start of the shift.
	 * @param endTime The end of the shift.
	 * @param minimumWorkers The number of workers needed for the shift.
	 * @return The status of the request.
	 */
	public String addShift(String dayOfWeek, String startTime, String endTime, String minimumWorkers);
	
	/**
	 * Request that a staff member with the supplied name be registered. 
	 * @param givenname The given name of the staff member
	 * @param familyName The family name of the staff member
	 * @return The status of the request as described in the notes.
	 */
	public String registerStaff(String givenname, String familyName);
	
	/**
	 * Request that the staff member specified by the supplied names be assigned to the shift specified by the supplied start and end times 
	 * @param dayOfWeek The day the request applies to.
	 * @param startTime The start of the shift.
	 * @param endTime The end of the shift.
	 * @param givenName The given name of the staff member
	 * @param familyName The family name of the staff member
	 * @param isManager true if the staff member is to be the manager for the shift (and not a worker).
	 * @return The status of the request.
	 */
	public String assignStaff(String dayOfWeek, String startTime, String endTime, String givenName, String familyName, boolean isManager);
	
	/**
	 * Request a list of the names of all registered staff. 
	 * 
	 * @return A list of the names of all registered staff.
	 */
	public List<String> getRegisteredStaff();

	/**
	 * Request a list of the names of all staff that have not be assigned as either
	 * a shift manager or as a worker on any shift.
	 * 
	 * @return A list of the names of all unassigned staff.
	 */
	public List<String> getUnassignedStaff();

	/**
	 * Request the shifts that do not have a manager assigned.
	 * @return A list of strings describing shifts that do not have a manager assigned,
	 * one string describing one shift.
	 */
	public List<String> shiftsWithoutManagers();

	/**
	 * Request the shifts that do not have enough workers assigned.
	 * @return A list of strings describing shifts that have fewer workers than the minimum required,
	 * one string describing one shift.
	 */
	public List<String> understaffedShifts();

	/**
	 * Request the shifts that have more workers than are needed.
	 * @return A list of strings describing shifts that have more workers than the minimum required,
	 * one string describing one shift.
	 */
	public List<String> overstaffedShifts();

	/**
	 * Request the roster (who is working when and in what role) for the specified day.
	 * @param dayOfWeek The day of the week to provide the roster for.
	 * @return A list of strings that describe who is working on what shifts for
	 * the specified day and other relevant information.
	 */
	public List<String> getRosterForDay(String dayOfWeek);

	/**
	 * Request the shifts that the staff member with the supplied name is assigned to (i.e not as manager).
	 * @param workerName The name of the staff member.
	 * @return a list of strings describing the shifts the staff member is a worker for.
	 */
	public List<String> getRosterForWorker(String workerName);
	
	/**
	 * Request the shifts that the staff member with the supplied name is the manager for.
	 * @param managerName The name of the staff member.
	 * @return a list of strings describing the shifts the staff member is the manager for.
	 */
	public List<String> getShiftsManagedBy(String managerName);
	
	/**
	 * Request the current issues with the current roster in a suitably formatted form.
	 * @return A string describing the issues.
	 * 
	 */
	public String reportRosterIssues();
	
	/**
	 * Request the current roster in a suitably formatted form.
	 * @return A string describing the roster.
	 */
	public String displayRoster();
}
