package ca.ualberta.cs.colpnotes;

import java.util.ArrayList;

/*
 * Holds Claims. Wherever possible, use delegate functions implemented here
 * instead of directly calling ClaimList.getClaims().<function>.
 */
public class ClaimList {
	private ArrayList<Claim> claims = null;
	
	public ClaimList() {
		claims = new ArrayList<Claim>();
	}
	
	public ArrayList<Claim> getClaims() {
		return claims;
	}
	
	public void addClaim(Claim claim) {
		claims.add(claim);
	}
	
	public void removeClaim(Claim claim) {
		claims.remove(claim);
	}
	
	public boolean contains(Claim claim) {
		return claims.contains(claim);
	}
	
	public int size() {
		return claims.size();
	}
}
