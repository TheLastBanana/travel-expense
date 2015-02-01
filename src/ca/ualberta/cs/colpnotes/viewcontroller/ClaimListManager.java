package ca.ualberta.cs.colpnotes.viewcontroller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ca.ualberta.cs.colpnotes.model.ClaimList;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

/**
 * A singleton class which handles serialization, deserialization,
 * saving and loading of ClaimLists.
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
		editor.putString(clKey, claimListToString(cl));
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
			return claimListFromString(claimListData);
		}
	}
	
	/**
	 * Serialize a ClaimList serialized to a Base64 string.
	 * @param cl The ClaimList.
	 * @return A Base64-encoded string.
	 * @throws IOException
	 */
	public String claimListToString(ClaimList cl) throws IOException {
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo = new ObjectOutputStream(bo);
		oo.writeObject(cl);
		oo.close();
		
		byte bytes[] = bo.toByteArray();
		return Base64.encodeToString(bytes, Base64.DEFAULT);
	}

	/**
	 * Deserialize a ClaimList from a Base64 string.
	 * @param claimListData The String to deserialize.
	 * @return The deserialized ClaimList.
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public ClaimList claimListFromString(String claimListData) throws ClassNotFoundException, IOException {
		ByteArrayInputStream bi = new ByteArrayInputStream(Base64.decode(claimListData, Base64.DEFAULT));
		ObjectInputStream oi = new ObjectInputStream(bi);
		
		return (ClaimList)oi.readObject();	
	}
	
	/**
	 * Construct the ClaimListManager.
	 * @param context The context to use.
	 */
	private ClaimListManager(Context context) {
		this.context = context;
	}
}
