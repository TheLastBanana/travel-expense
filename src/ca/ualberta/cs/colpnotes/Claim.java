package ca.ualberta.cs.colpnotes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.HashMap;

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
	
	/**
	 * Get the totals for each currency type in this claim's expenses.
	 * @return A map from Currency to total amount 
	 */
	public HashMap<Currency, BigDecimal> getTotals() {
		HashMap<Currency, BigDecimal> totals = new HashMap<Currency, BigDecimal>();
		
		// Find the amount for each currency and tally
		for (Expense expense : getExpenses()) {
			Currency currency = expense.getCurrency();
			BigDecimal amount = totals.get(currency);
			
			// New currency
			if (amount == null) {
				amount = new BigDecimal(expense.getAmount().toString());
				
			// We already have a total
			} else {
				amount = amount.add(expense.getAmount());
			}
			
			totals.put(currency, amount);
		}
		
		return totals;
	}
}
