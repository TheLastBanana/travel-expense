package ca.ualberta.cs.colpnotes;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;

import android.content.Context;

public class ClaimHelper {
	/**
	 * Creates a comma-separated string listing the totals of all the claim's expenses by currency type.
	 * @param claim The claim holding the expenses
	 * @param context The context to get strings from.
	 * @return The resulting string.
	 */
	static public final String getTotalString(Claim claim, Context context) {
		// Map from currency to amount
		HashMap<Currency, BigDecimal> totals = claim.getTotals();
		
		// Build the string
		StringBuilder builder = new StringBuilder();
		
		// Nothing to list
		if (claim.getExpenses().size() == 0) {
			builder.append(context.getString(R.string.na_label));
			
		// Build the comma-separated list
		} else {
			// Create strings for each amount and separate with commas
			for (Currency currency : totals.keySet()) {
				BigDecimal amount = totals.get(currency);
				
				builder.append(amount.toPlainString() +
							   " " +
							   currency.getCurrencyCode() +
							   ", ");
			}
			
			// Remove last comma
			builder.delete(builder.length() - 2, builder.length());
		}
		
		return builder.toString();
	}
}
