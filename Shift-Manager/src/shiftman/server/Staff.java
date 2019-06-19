package shiftman.server;

public class Staff implements Comparable<Staff>{

	private String _givenName;
	private String _familyName;
	private boolean _isAssigned = false;
	
	public Staff(String givenName, String familyName) throws ClientInputException{
		
		if (("".equals(givenName) || ("".equals(familyName)))){
			throw new ClientInputException("ERROR: givenName and/or familyName are empty strings");
		}
		_givenName = givenName;
		_familyName = familyName;
	}
	
	public boolean sameNameAs(Staff staff) {
		if ((_givenName.equalsIgnoreCase(staff._givenName)) & (_familyName.equalsIgnoreCase(staff._familyName))) {
			return true;
		}
		return false;
	}
	
	public int compareTo(Staff staff) { // specifies sorting behavior for comparable
		 // determines if staff familyName is before "this" _familyName in the alphabet
		// compareTo method puts upper case "Z" before lower case "a" in the alphabet, therefore compareToIgnoreCase is used
		return _familyName.compareToIgnoreCase(staff._familyName); // returns
		
	}
	
	public String name(boolean managerFormat) { // allows the format of the name to be changed to manager format (last, first) without another method
		if (managerFormat) {
			return _familyName + ", " + _givenName;
		}
		return _givenName + " " + _familyName;
	}
	
	public void Assigned(){ // sets assigned to shift status to true
		_isAssigned = true;
	}
	
	public boolean isAssigned() { // returns if the staff member is assigned to a shift or not
		if (_isAssigned) {
			return true;
		}
		return false;
	}
}
