package ca.ualberta.cs.colpnotes;

import java.io.Serializable;
import java.util.ArrayList;

/*
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
