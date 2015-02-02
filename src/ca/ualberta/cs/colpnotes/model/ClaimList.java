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

package ca.ualberta.cs.colpnotes.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Holds Claims. Wherever possible, use delegate functions implemented here
 * instead of directly calling ClaimList.getClaims().<function>.
 */
public class ClaimList implements Serializable {
	/**
	 * Generated ID
	 */
    private static final long serialVersionUID = -1455114587291688119L;
    
	private ArrayList<Claim> claims = null;
	
	public ClaimList() {
		claims = new ArrayList<Claim>();
	}
	
	/**
	 * Get the underlying ArrayList of Claims.
	 * @return The ArrayList.
	 */
	public ArrayList<Claim> getClaims() {
		return claims;
	}
	
	/**
	 * Add a Claim to the list.
	 * @param claim The Claim to add.
	 */
	public void addClaim(Claim claim) {
		claims.add(claim);
	}
	
	/**
	 * Remove a Claim from the list.
	 * @param claim The Claim to remove.
	 * @return Whether the list was modified by this operation.
	 */
	public boolean removeClaim(Claim claim) {
		return claims.remove(claim);
	}
	
	/**
	 * Get the claim at a given index in the list.
	 * @param index The index in the list.
	 * @return The claim at that index.
	 */
	public Claim getClaim(int index) {
		return claims.get(index);
	}
	
	/**
	 * Determine whether a Claim is in the list.
	 * @param claim The Claim to check.
	 * @return Whether the Claim is in the list.
	 */
	public boolean contains(Claim claim) {
		return claims.contains(claim);
	}
	
	/**
	 * Get the size of the list.
	 * @return The size of the list.
	 */
	public int size() {
		return claims.size();
	}

	/**
	 * Get the index of a Claim in the list.
	 * @param claim The Claim to check.
	 * @return Its index in the list.
	 */
	public int indexOf(Claim claim) {
	    return claims.indexOf(claim);
    }
}
