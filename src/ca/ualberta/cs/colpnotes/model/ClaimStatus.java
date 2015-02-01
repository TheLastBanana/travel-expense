package ca.ualberta.cs.colpnotes.model;

import java.util.EnumMap;
import java.util.Map;

import ca.ualberta.cs.colpnotes.R;

/**
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
		= new EnumMap<ClaimStatus, Integer>(ClaimStatus.class);
	
	private static final Map<ClaimStatus, Boolean> editable
	= new EnumMap<ClaimStatus, Boolean>(ClaimStatus.class);
	
	// Static data
	static {
		nameIDs.put(IN_PROGRESS, R.string.in_progress_label);
		nameIDs.put(SUBMITTED, R.string.submitted_label);
		nameIDs.put(RETURNED, R.string.returned_label);
		nameIDs.put(APPROVED, R.string.approved_label);
		
		editable.put(IN_PROGRESS, true);
		editable.put(SUBMITTED, false);
		editable.put(RETURNED, true);
		editable.put(APPROVED, false);
	}

	/**
	 * Get the ID of the string resource to use for a claimStatus' name.
	 * @param claimStatus The claimStatus to check.
	 * @return The ID of the name string.
	 * @throws RuntimeException
	 */
	public static int getNameID(ClaimStatus claimStatus)
	{
		if (!nameIDs.containsKey(claimStatus)) {
			throw new RuntimeException("Missing name ID for this claim status!");
		}
		
		return nameIDs.get(claimStatus);
	}

	/**
	 * Get whether a claimStatus allows the claim to be edited.
	 * @param claimStatus The claimStatus to check.
	 * @return Whether the claim should be editable.
	 * @throws RuntimeException
	 */
	public static Boolean getEditable(ClaimStatus claimStatus)
	{
		if (!editable.containsKey(claimStatus)) {
			throw new RuntimeException("Missing editable value for this claim status!");
		}
		
		return editable.get(claimStatus);
	}
}
