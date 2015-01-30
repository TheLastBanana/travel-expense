package ca.ualberta.cs.colpnotes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.text.format.DateFormat;

public class ClaimHelper {
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
		sb.append(ExpenseListHelper.getTotalString(claim.getExpenseList(), context) + "\n");
		
		return sb.toString();
	}
}
