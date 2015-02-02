/*
 * This file is part of travel-expense by Elliot Colp.
 *
 *  travel-expense is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  travel-expense is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with travel-expense.  If not, see <http://www.gnu.org/licenses/>.
 */

package ca.ualberta.cs.colpnotes.helper;

import android.content.Context;
import android.text.format.DateFormat;
import ca.ualberta.cs.colpnotes.R;
import ca.ualberta.cs.colpnotes.model.Claim;
import ca.ualberta.cs.colpnotes.model.ClaimStatus;
import ca.ualberta.cs.colpnotes.model.Expense;

/**
 * A static class which contains functions to help working with Claim.
 */
public class ClaimHelper {
	// This is a static class; no instances
	private ClaimHelper() {}
	
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
