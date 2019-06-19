package shiftman.client;

import shiftman.server.ShiftMan;
import shiftman.server.ShiftManServer;
import java.util.List;

/**
 * This shows another use of the ShiftMap API, in particular showing the
 * effect of {@link ShiftMan#newRoster(String)}.
 */
public class MyTestRoster {
	public static void main(String[] args) {
		System.out.println(">>Starting new roster demo");
		ShiftMan scheduler = new ShiftManServer();
		System.out.println(">>Setting working hours without having created new roster");
		String status = scheduler.setWorkingHours("Monday", "09:00", "17:00");
		// Put status in {} so can tell when get empty string.
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
		
		System.out.println(">>Create new roster 'eScooters R Us' (status of {} means no error)");
		status = scheduler.newRoster("eScooters R Us");
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
	
		System.out.println(">>Set working hours for Monday to 98:00-99:00 (testing regex)");
		status = scheduler.setWorkingHours("Monday", "98:00", "99:00");
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
		
		System.out.println(">>Set working hours for Monday to 12:00--01:00 (testing regex)");
		status = scheduler.setWorkingHours("Monday", "12:00", "-01:00");
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
		
		System.out.println(">>Set working hours for Monday to Over-NineThousand (testing regex)");
		status = scheduler.setWorkingHours("Monday", "Over", "NineThousand");
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
		
		
		System.out.println(">>Set working hours for Monday to 21:30-09:00");
		status = scheduler.setWorkingHours("Monday", "21:30", "09:00");
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");

		System.out.println(">>Set working hours for Dday to 10:30-14:00 (should be invalid day)");
		status = scheduler.setWorkingHours("Dday", "10:30", "14:00");
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
		
		System.out.println(">>Add shift 12:00-13:00 to Tuesday with minimum 1 worker");
		status = scheduler.addShift("Tuesday", "12:00", "13:00", "1");
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
		
		System.out.println(">>Set working hours for Tuesday to 09:00-19:00");
		status = scheduler.setWorkingHours("Tuesday", "09:00", "19:00");
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
		
		System.out.println(">>Add shift 12:00-13:00 to Tuesday with minimum 1 worker");
		status = scheduler.addShift("Tuesday", "12:00", "13:00", "1");
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
		
		System.out.println(">>Set working hours for Monday to 09:00-19:00");
		status = scheduler.setWorkingHours("Monday", "09:00", "19:00");
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
		
		System.out.println(">>Add shift 09:00-12:00 to Monday");
		status = scheduler.addShift("Monday", "09:00", "12:00", "0");
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");

		System.out.println(">>Add shift 09:00-12:30 to Monday (overlap)");
		status = scheduler.addShift("Monday", "09:00", "12:30", "0");
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
		
		System.out.println(">>Add shift 14:00-18:00 to Monday");
		status = scheduler.addShift("Monday", "14:00", "18:00", "0");
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
		
		System.out.println(">>Add shift 18:00-19:00 to Monday");
		status = scheduler.addShift("Monday", "18:00", "19:00", "0");
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
		
		System.out.println(">>Add shift 12:00-13:00 to Monday with minimum 1 worker");
		status = scheduler.addShift("Monday", "12:00", "13:00", "1");
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
		
		System.out.println(">>Add shift 09:00-12:00 to Monday (same time and day as another)");
		status = scheduler.addShift("Monday", "09:00", "12:00", "0");
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
		
		System.out.println(">>Add shift 09:00-14:00 to Monday (overlap)");
		status = scheduler.addShift("Monday", "09:00", "14:00", "0");
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
		
		System.out.println(">>Add shift 08:00-12:00 to Monday (overlap and outside working hours)");
		status = scheduler.addShift("Monday", "08:00", "12:00", "0");
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
		
		System.out.println(">>Add shift 10:00-20:00 to Monday (overlap and outside working hours)");
		status = scheduler.addShift("Monday", "10:00", "20:00", "0");
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
		
		System.out.println(">>Add shift 18:00-20:00 to Monday (outside working hours)");
		status = scheduler.addShift("Monday", "18:00", "20:00", "0");
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
		
		System.out.println(">>Register Darell Bayta as a staff member");
		status = scheduler.registerStaff("Darell", "Bayta");
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");

		System.out.println(">>Register Hari Sheldon as a staff member");
		status = scheduler.registerStaff("Hari", "Sheldon");
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
		
		System.out.println(">>Register Joe Sheldon as a staff member");
		status = scheduler.registerStaff("Joe", "Sheldon");
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
		
		System.out.println(">>Register Joe Chan as a staff member");
		status = scheduler.registerStaff("Joe", "Chan");
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
		
		System.out.println(">>Register John Burton as a staff member");
		status = scheduler.registerStaff("John", "Burton");
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
		
		System.out.println(">>Register Jake burton as a staff member");
		status = scheduler.registerStaff("Jake", "burton");
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
		
		System.out.println(">>Register Jake Durton as a staff member");
		status = scheduler.registerStaff("Jake", "Durton");
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
		
		System.out.println(">>Register Jake durton as a staff member");
		status = scheduler.registerStaff("Jake", "durton");
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
		
		System.out.println(">>Register jake Durton as a staff member");
		status = scheduler.registerStaff("jake", "Durton");
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
		
		System.out.println(">>Register jake durton as a staff member");
		status = scheduler.registerStaff("jake", "durton");
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
		
		System.out.println(">>Show registered staff");
		List<String> statusList = scheduler.getRegisteredStaff();
		System.out.println("\tGot status: ");
		for (String i : statusList) {
			System.out.println(i);
		}
		System.out.println(" ");
		
		System.out.println(">>Schedule Darell Bayta as manager to Monday 09:00-12:00");
		status = scheduler.assignStaff("Monday", "09:00", "12:00", "Darell", "Bayta", true);
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");

		System.out.println(">>Schedule Hari Sheldon as worker to Monday 12:00-13:00");
		status = scheduler.assignStaff("Monday", "12:00", "13:00", "Hari", "Sheldon", false);
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
		
		System.out.println(">>Schedule Jeremy Corbyn as worker to Monday 12:00-13:00");
		status = scheduler.assignStaff("Monday", "12:00", "13:00", "Jeremy", "Corbyn", false);
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
		
		System.out.println(">>Schedule Hari Sheldon as Manager to Monday 12:00-13:00 (already on shift (not manager))");
		status = scheduler.assignStaff("Monday", "12:00", "13:00", "Hari", "Sheldon", true);
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
		
		System.out.println(">>Schedule Joe Chan as Manager to Monday 12:00-13:00");
		status = scheduler.assignStaff("Monday", "12:00", "13:00", "Joe", "Chan", true);
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
		
		System.out.println(">>Schedule John Burton as Manager to Monday 12:00-13:00");
		status = scheduler.assignStaff("Monday", "12:00", "13:00", "John", "Burton", true);
		System.out.println("\tGot status {" + status + "}");
		System.out.println(" ");
		
		System.out.println(">>Show unassigned staff");
		List<String> statusList2 = scheduler.getUnassignedStaff();
		System.out.println("\tGot status: ");
		for (String i : statusList2) {
			System.out.println(i);
		}
		System.out.println(" ");
		
		System.out.println(">>Show shifts with no manager");
		List<String> statusList3 = scheduler.shiftsWithoutManagers();
		System.out.println("\tGot status: ");
		for (String i : statusList3) {
			System.out.println(i);
		}
		System.out.println(" ");
		
		System.out.println(">>Show understaffed shifts");
		List<String> statusList4 = scheduler.understaffedShifts();
		System.out.println("\tGot status: ");
		for (String i : statusList4) {
			System.out.println(i);
		}
		System.out.println(" ");
		
		System.out.println(">>Show overstaffed");
		List<String> statusList5 = scheduler.overstaffedShifts();
		System.out.println("\tGot status: ");
		for (String i : statusList5) {
			System.out.println(i);
		}
		System.out.println(" ");
		
		
		
		System.out.println(">>Getting roster for Monday");
		List<String> statusList6 = scheduler.getRosterForDay("Monday");
		System.out.println("\tGot status: ");
		for (String i : statusList6) {
			System.out.println(i);
		}
		System.out.println(" ");
		
		System.out.println(">>Display roster issues");
		System.out.println(scheduler.reportRosterIssues());
		System.out.println(" ");
		
		System.out.println(">>Display current roster");
		System.out.println(scheduler.displayRoster());
		System.out.println(" ");
		
		System.out.println(">>Create a new Roster for 'Socks for Everyone'");
		status = scheduler.newRoster("Socks for Everyone");
		System.out.println("\tGot status {" + status + "}");
		System.out.println(">>Display current roster. Should be empty due to call to newRoster()");
		System.out.println(scheduler.displayRoster());
		System.out.println(" ");
		System.out.println(">>End new roster demo");
	}
}
