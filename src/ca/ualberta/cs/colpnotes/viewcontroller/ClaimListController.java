package ca.ualberta.cs.colpnotes.viewcontroller;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import ca.ualberta.cs.colpnotes.model.ClaimList;
import android.content.Context;
import android.util.Log;

/**
 * A singleton controller class which stores the global ClaimList.
 * Delegates to the ClaimList and ClaimListManager to handle events.
 * 
 * Based on code from:
 * https://github.com/abramhindle/student-picker/blob/master/src/ca/softwareprocess/studentpicker/StudentListManager.java
 * Accessed 25/01/24
 */
public class ClaimListController {
	private static ClaimList claimList = null;
	
	Context context = null;
	
	/**
	 * Get the global ClaimList.
	 * @return The ClaimList.
	 */
	public static ClaimList getClaimList() {
		if (claimList == null) {
			load();
		}
		
		return claimList;
	}
	
	/**
	 * Save the global ClaimList, serialized, to the preferences file.
	 * @throws RuntimeException.
	 */
	public static void save() {
		try {
			ClaimListManager.getManager().saveClaimList(claimList);
		}
		catch (IOException e) {
			throw new RuntimeException("Failed to save claim list");
		}
	}
	
	/**
	 * Load the global ClaimList from the preferences file.
	 * @throws RuntimeException.
	 */
	public static void load() {
		try {
			claimList = ClaimListManager.getManager().loadClaimList();
		}
		catch (IOException e) {
			Log.e("CLC", "IOException");
			throw new RuntimeException("Failed to load claim list");
		}
		catch (ClassNotFoundException e) {
			Log.e("CLC", "ClassNotFoundException");
			throw new RuntimeException("Failed to load claim list");
		}
		
		// No claim list, so create a new one
		if (claimList == null) claimList = new ClaimList();
	}
}
