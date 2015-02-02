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

package ca.ualberta.cs.colpnotes.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.util.Base64;
import ca.ualberta.cs.colpnotes.model.ClaimList;

/**
 * A static class with helper functions for handling ClaimLists.
 *
 */
public class ClaimListHelper {
	// This is a static class; no instances
	private ClaimListHelper() {}
	
	/**
	 * Serialize a ClaimList serialized to a Base64 string.
	 * @param cl The ClaimList.
	 * @return A Base64-encoded string.
	 * @throws IOException
	 */
	static public String claimListToString(ClaimList cl) throws IOException {
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
	static public ClaimList claimListFromString(String claimListData) throws ClassNotFoundException, IOException {
		ByteArrayInputStream bi = new ByteArrayInputStream(Base64.decode(claimListData, Base64.DEFAULT));
		ObjectInputStream oi = new ObjectInputStream(bi);
		
		return (ClaimList)oi.readObject();	
	}
}
