package ca.ualberta.cs.colpnotes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Currency;

/*
 * Holds information about a single expense.
 */
public class Expense implements Serializable {
	/**
	 * Generated ID
	 */
    private static final long serialVersionUID = -1959800553757616009L;
    
	private Calendar date;
	private ExpenseCategory category;
	private String description;
	private BigDecimal amount;
	private Currency currency;
	
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}
	public ExpenseCategory getCategory() {
		return category;
	}
	public void setCategory(ExpenseCategory category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
}
