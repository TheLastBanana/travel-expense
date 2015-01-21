package ca.ualberta.cs.colpnotes;

import java.io.Serializable;
import java.util.ArrayList;
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
    
	private String name = "";
	private String destination = "";
	private String reason = "";
	private ClaimStatus status = ClaimStatus.IN_PROGRESS;
	private Calendar from = Calendar.getInstance();
	private Calendar to = Calendar.getInstance();
	private ArrayList<Expense> expenses = new ArrayList<Expense>();

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

	public Calendar getFrom() {
		return from;
	}

	public void setFrom(Calendar from) {
		this.from = from;
	}

	public Calendar getTo() {
		return to;
	}

	public void setTo(Calendar to) {
		this.to = to;
	}

	public ClaimStatus getStatus() {
		return status;
	}

	public void setStatus(ClaimStatus status) {
		this.status = status;
	}

	public ArrayList<Expense> getExpenses() {
		return expenses;
	}
}
