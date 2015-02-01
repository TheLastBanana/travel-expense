package ca.ualberta.cs.colpnotes.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Currency;
import java.util.Locale;

/**
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
		
		try {
			setCurrency(Currency.getInstance(Locale.getDefault()));
		}
		catch (IllegalArgumentException e) {
			// Not a supported locale, so fall back to USD
			setCurrency(Currency.getInstance("USD"));
		}
	}
	
	public Expense(Expense e) {
		copyFrom(e);
	}
	
	/**
	 * Copy all attributes from the given expense.
	 */
	public void copyFrom(Expense e) {
		setDate((Calendar) e.date.clone());
		setCategory(new String(e.category));
		setDescription(new String(e.description));
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
	
	/**
	 *  Update the scale of the amount based on the currency fraction digits.
	 */
	private void updateAmountScale() {
		if (amount == null) return;
		if (currency == null) return;
		
		amount = amount.setScale(currency.getDefaultFractionDigits(), BigDecimal.ROUND_DOWN);
	}
}
