package ca.ualberta.cs.colpnotes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.HashMap;
import java.util.Observable;

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
	private Calendar from;
	private Calendar to;
	private ExpenseList expenses;
	
	Claim() {
		setName("");
		setDestination("");
		setReason("");
		setStatus(ClaimStatus.IN_PROGRESS);
		setFrom(Calendar.getInstance());
		setTo(Calendar.getInstance());
		expenses = new ExpenseList();
	}
	
	Claim (Claim c) {
		copyFrom(c);
	}
	
	/**
	 * Copies all attributes from the given claim.
	 * @param c The claim to copy from.
	 */
	public void copyFrom(Claim c) {
		setName(new String(c.getName()));
		setDestination(new String(c.getDestination()));
		setReason(new String(c.getReason()));
		setStatus(c.getStatus());
		setFrom((Calendar) c.getFrom().clone());
		setTo((Calendar) c.getTo().clone());
		expenses = new ExpenseList(c.getExpenseList());
	}
	
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

	public ExpenseList getExpenseList() {
		return expenses;
	}
	
	/**
	 * Get the totals for each currency type in this claim's expenses.
	 * @return A map from Currency to total amount 
	 */
	public HashMap<Currency, BigDecimal> getTotals() {
		HashMap<Currency, BigDecimal> totals = new HashMap<Currency, BigDecimal>();
		
		// Find the amount for each currency and tally
		for (Expense expense : getExpenseList().getExpenses()) {
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
