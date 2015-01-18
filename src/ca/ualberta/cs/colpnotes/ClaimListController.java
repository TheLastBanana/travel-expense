package ca.ualberta.cs.colpnotes;

/*
 * A singleton class which stores the global ClaimList.
 * Delegates to the ClaimList and ClaimListManager to handle events.
 */
public class ClaimListController {
	private static ClaimList claimList = null;
	
	public static ClaimList getClaimList() {
		if (claimList == null) {
			claimList = new ClaimList();
		}
		
		return claimList;
	}
}
