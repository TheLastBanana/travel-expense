package ca.ualberta.cs.colpnotes;

import java.io.Serializable;
import java.util.Calendar;

/*
 * Holds information about a claim, including its corresponding Expenses.
 * Also responsible for validating this data in setter functions.
 */
public class Claim implements Serializable {
	/**
	 * Generated ID
	 */
    private static final long serialVersionUID = -5147034485394091896L;
    
	private String name;
	private String destination;
	private String reason;
	private ClaimStatus status;
	private Calendar start;
	private Calendar end;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Calendar getStart() {
		return start;
	}

	public void setStart(Calendar start) {
		this.start = start;
	}

	public Calendar getEnd() {
		return end;
	}

	public void setEnd(Calendar end) {
		this.end = end;
	}

	public ClaimStatus getStatus() {
		return status;
	}

	public void setStatus(ClaimStatus status) {
		this.status = status;
	}
}
