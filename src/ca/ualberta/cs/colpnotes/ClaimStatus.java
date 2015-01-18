package ca.ualberta.cs.colpnotes;

import java.util.HashMap;
import java.util.Map;

/*
 * Holds constants for claim status.
 * 
 * Referenced code from:
 * http://www.ajaxonomy.com/2007/java/making-the-most-of-java-50-enum-tricks
 * on 17/01/15
 */
public enum ClaimStatus {
	IN_PROGRESS,
	SUBMITTED,
	RETURNED,
	APPROVED;
	
	private static final Map<ClaimStatus, Integer> nameIDs
		= new HashMap<ClaimStatus, Integer>();
	
	static {
		nameIDs.put(IN_PROGRESS, R.string.in_progress_label);
		nameIDs.put(SUBMITTED, R.string.submitted_label);
		nameIDs.put(RETURNED, R.string.returned_label);
		nameIDs.put(APPROVED, R.string.approved_label);
	}
	
	// Returns the ID of the string resource to use
	// Throws RuntimeException!
	public static int getNameID(ClaimStatus claimStatus)
	{
		if (!nameIDs.containsKey(claimStatus)) {
			throw new RuntimeException("Missing name ID for this claim status!");
		}
		
		return nameIDs.get(claimStatus);
	}
}
