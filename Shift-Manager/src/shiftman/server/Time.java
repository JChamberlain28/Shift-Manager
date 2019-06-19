package shiftman.server;

public class Time {

	private int _start;
	private int _end;
	private String _startString;
	private String _endString;

	public Time(String start, String end) throws ClientInputException {

		_startString = start;
		_endString = end;

		
		String[] times = {start, end};
		int[] timeVals = new int[2];

		for (int i = 0; i < 2; i++) { // performs conversion for start and end times
			// converts hh:mm format time it an integer of format hhmm (e.g. "18:30" -> 1830)

			if(!(times[i].matches("([0-1]\\d|2[0-3]):[0-5]\\d"))) { // regular expression to prevents strings not in hh:mm format and outside range 00:00 - 23:59
				throw new ClientInputException("ERROR: Invalid times (does not follow hh:mm format or is outside range 00:00-23:59)");
			}
			
			timeVals[i] = Integer.parseInt((times[i].substring(0,2)) + (times[i].substring(3))); // converts time into integer form


		}

		_start = timeVals[0]; // gets start and end time from array that was populated by the for loop
		_end = timeVals[1];
		

		if (_start >= _end) { // Indicates invalid time periods
			throw new ClientInputException("ERROR: Start time is on or after end time");
		}


	}





	public boolean timeRangeContains(Time time) { // checks if a time objects time period is contained within the period of the current time object
		if ((time._start >= _start) & (time._end <= _end)) {
			return true;
		}
		return false;
	}

	public boolean overlap(Time time) { // checks for overlap between time periods of time objects
		if (((_start <= time._start) & (_end > time._end)) || ((_start >= time._start) & (_start < time._end))) {
			return true;
		}
		return false;
	}

	public boolean equalTimes(Time time) { // checks if the start and end times of one Time object is the same as another
		if ((time._start == _start) && (time._end == _end)) {
			return true;
		}
		return false;
	}
	
	public boolean before(Time time){
		return (_start < time._start); // no need to indicate if start times are equal as such shifts would overlap and not co-exist
	}
	
	public String timePeriod() {
		return _startString + "-" + _endString;
	}
}
