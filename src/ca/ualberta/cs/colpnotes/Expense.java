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
	private String category;
	private String description;
	private BigDecimal amount;
	private Currency currency;
	
	public Expense() {
		setDate(Calendar.getInstance());
		setCategory(category);
		setDescription("");
		setAmount(new BigDecimal(0));
		setCurrency(Currency.getInstance("USD"));
	}
	
	public Expense(Expense e) {
		setDate((Calendar) e.date.clone());
		setCategory(new String(e.category));
		setDescription(new String(e.category));
		setAmount(new BigDecimal(e.amount.toString()));
		
		// Don't need to deep copy as only one exists per currency type
		setCurrency(e.currency);
	}
	
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
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
		
		updateAmountScale();
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
		
		updateAmountScale();
	}
	
	private void updateAmountScale() {
		if (amount == null) return;
		if (currency == null) return;
		
		amount = amount.setScale(currency.getDefaultFractionDigits());
	}
}
