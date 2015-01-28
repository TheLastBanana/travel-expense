package ca.ualberta.cs.colpnotes;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;

import android.content.Context;
import android.text.format.DateFormat;

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
		if (claim.getExpenseList().size() == 0) {
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
	
	/**
	 * List out the each of the claims and their expenses as a plaintext string.
	 * Based on code from: http://stackoverflow.com/a/2197841
	 * Accessed on 27/01/15
	 * @param claim The claim.
	 * @param context The context to get strings from.
	 * @return A pretty-printed string.
	 */
	static public final String prettyPrint(Claim claim, Context context) {
		StringBuilder sb = new StringBuilder();
		
		java.text.DateFormat df = DateFormat.getMediumDateFormat(context);
		int statusID = ClaimStatus.getNameID(claim.getStatus());
		
		// Print the claim name
		sb.append(context.getString(R.string.name_hint) + ": " + claim.getName() + "\n");
		sb.append(context.getString(R.string.status_hint) + ": " + context.getString(statusID) + "\n");
		sb.append(context.getString(R.string.range_hint) + ": ");
		sb.append(df.format(claim.getFrom().getTime()) + " - " + df.format(claim.getTo().getTime()) + "\n");
		sb.append(context.getString(R.string.destination_hint) + ": " + claim.getDestination() + "\n");
		sb.append(context.getString(R.string.reason_hint) + ": " + claim.getReason() + "\n");
		sb.append("\n");
		
		// Print the expenses
		for (Expense expense : claim.getExpenseList().getExpenses()) {
			sb.append("* " + df.format(expense.getDate().getTime()) + "\n");
			sb.append(expense.getAmount().toPlainString() + " ");
			sb.append(expense.getCurrency().getCurrencyCode() + " (");
			sb.append(expense.getCategory() + ")\n");
			sb.append(context.getString(R.string.description_hint) + ": " + expense.getDescription() + "\n\n");
		}
		
		// Print the total
		sb.append(context.getString(R.string.total_label) + ": ");
		sb.append(ClaimHelper.getTotalString(claim, context) + "\n");
		
		return sb.toString();
	}
}
