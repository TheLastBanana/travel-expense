package ca.ualberta.cs.colpnotes;

import java.util.EnumMap;
import java.util.Map;

/*
 * Holds constants for expense categories.
 * 
 * Referenced code from:
 * http://www.ajaxonomy.com/2007/java/making-the-most-of-java-50-enum-tricks
 * on 17/01/15
 */
public enum ExpenseCategory {
	AIR_FARE,
	GROUND_TRANSPORT,
	VEHICLE_RENTAL,
	FUEL,
	PARKING,
	REGISTRATION,
	ACCOMMODATION,
	MEAL;
	
	private static final Map<ExpenseCategory, Integer> nameIDs
		= new EnumMap<ExpenseCategory, Integer>(ExpenseCategory.class);
	
	static {
		nameIDs.put(AIR_FARE, R.string.air_fare_label);
		nameIDs.put(GROUND_TRANSPORT, R.string.ground_transport_label);
		nameIDs.put(VEHICLE_RENTAL, R.string.vehicle_rental_label);
		nameIDs.put(FUEL, R.string.fuel_label);
		nameIDs.put(PARKING, R.string.parking_label);
		nameIDs.put(REGISTRATION, R.string.registration_label);
		nameIDs.put(ACCOMMODATION, R.string.accommodation_label);
		nameIDs.put(MEAL, R.string.meal_label);
	}
	
	// Returns the ID of the string resource to use
	// Throws RuntimeException!
	public static int getNameID(ClaimStatus claimStatus)
	{
		if (!nameIDs.containsKey(claimStatus)) {
			throw new RuntimeException("Missing name ID for this expense category!");
		}
		
		return nameIDs.get(claimStatus);
	}
}
