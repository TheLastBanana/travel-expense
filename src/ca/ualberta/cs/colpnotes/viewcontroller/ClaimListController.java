/*
 * This file is part of travel-expense by Elliot Colp.
 *
 *  travel-expense is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  travel-expense is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with travel-expense.  If not, see <http://www.gnu.org/licenses/>.
 */

package ca.ualberta.cs.colpnotes.viewcontroller;

import java.io.IOException;

import android.util.Log;
import ca.ualberta.cs.colpnotes.model.ClaimList;

/**
 * A controller class which stores a single global ClaimList.
 * Delegates to ClaimList and ClaimListManager to handle events.
 * 
 * Based on code from:
 * https://github.com/abramhindle/student-picker/blob/master/src/ca/softwareprocess/studentpicker/StudentListManager.java
 * Accessed 25/01/24
 */
public class ClaimListController {
	private static ClaimList claimList = null;
	
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
