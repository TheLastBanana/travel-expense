package ca.ualberta.cs.colpnotes.viewcontroller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ca.ualberta.cs.colpnotes.helper.ClaimListHelper;
import ca.ualberta.cs.colpnotes.model.ClaimList;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

/**
 * A singleton class which handles operations on ClaimLists
 * that require an Android context such as saving and loading.
 * 
 * Based on code from StudentPicker:
 * https://github.com/abramhindle/student-picker/blob/master/src/ca/softwareprocess/studentpicker/StudentListManager.java
 * Accessed on 26/01/15
 */
public class ClaimListManager {
	private static final String prefFile = "ClaimList";
	private static final String clKey = "claimList";
	private static ClaimListManager claimListManager = null;
	
	private Context context;
	
	/**
	 * Initializes the ClaimListManager.
	 * @param context The context to work in.
	 * @throws RuntimeException
	 */
	public static void initManager(Context context) {
		if (context == null) {
			throw new RuntimeException("Context required for ClaimListManager");
		}
		
		claimListManager = new ClaimListManager(context);
	}
	
	/**
	 * Get the ClaimListManager instance.
	 * @return The manager.
	 * @throws RuntimeException
	 */
	public static ClaimListManager getManager() {
		if (claimListManager == null) {
			throw new RuntimeException("ClaimListManager not initialized");
		}
		
		return claimListManager;
	}
	
	/**
	 * Save a ClaimList to the preferences file.
	 * @param cl The ClaimList to save.
	 * @throws IOException
	 */
	public void saveClaimList(ClaimList cl) throws IOException {
		SharedPreferences settings = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
		Editor editor = settings.edit();
		editor.putString(clKey, ClaimListHelper.claimListToString(cl));
		editor.commit();
	}

	/**
	 * Load a ClaimList from the preferences file.
	 * @return The loaded ClaimList.
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public ClaimList loadClaimList() throws ClassNotFoundException, IOException {
		SharedPreferences settings = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
		
		String claimListData = settings.getString(clKey, "");
		if (claimListData.equals("")) {
			return new ClaimList();
		} else {
			return ClaimListHelper.claimListFromString(claimListData);
		}
	}
	
	/**
	 * Construct the ClaimListManager.
	 * @param context The context to use.
	 */
	private ClaimListManager(Context context) {
		this.context = context;
	}
}
