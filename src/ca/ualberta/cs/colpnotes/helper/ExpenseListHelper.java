package ca.ualberta.cs.colpnotes.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ca.ualberta.cs.colpnotes.R;
import ca.ualberta.cs.colpnotes.R.string;
import ca.ualberta.cs.colpnotes.model.Expense;
import ca.ualberta.cs.colpnotes.model.ExpenseList;
import android.content.Context;

public class ExpenseListHelper {
	/**
	 * Get the totals for each currency type in an ExpenseList.
	 * @param list The ExpenseList.
	 * @return A map from Currency to total amount 
	 */
	static public HashMap<Currency, BigDecimal> getTotals(ExpenseList list) {
		HashMap<Currency, BigDecimal> totals = new HashMap<Currency, BigDecimal>();
		
		// Find the amount for each currency and tally
		for (Expense expense : list.getExpenses()) {
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
	
	/**
	 * Creates a comma-separated string listing the totals of all the claim's expenses by currency type.
	 * @param list The ExpenseList holding the expenses.
	 * @param context The context to get strings from.
	 * @return The resulting string.
	 */
	static public final String getTotalString(ExpenseList list, Context context) {
		// Map from currency to amount
		HashMap<Currency, BigDecimal> totals = getTotals(list);
		
		// Build the string
		StringBuilder builder = new StringBuilder();
		
		// Nothing to list
		if (totals.size() == 0) {
			builder.append(context.getString(R.string.na_label));
			
		// Build the comma-separated list
		} else {
			// Sort by amount first, then alphabetically by currency code
			ArrayList<Map.Entry<Currency, BigDecimal>> sorted =
					new ArrayList<Map.Entry<Currency,BigDecimal>>(totals.entrySet());
			
			Collections.sort(sorted, new Comparator<Map.Entry<Currency, BigDecimal>>() {
				@Override
	            public int compare(Entry<Currency, BigDecimal> lhs,
	                    Entry<Currency, BigDecimal> rhs) {
		            
					BigDecimal lhsAmount = lhs.getValue();
					BigDecimal rhsAmount = rhs.getValue();
					
					// Equal, so sort by currency code
					if (lhsAmount.equals(rhsAmount)) {
						String lhsCode = lhs.getKey().getCurrencyCode();
						String rhsCode = rhs.getKey().getCurrencyCode();
						
						return lhsCode.compareTo(rhsCode);
					}
					
					// Largest to smallest
					return -lhsAmount.compareTo(rhsAmount);
	            }
			});
			
			// Create strings for each amount and separate with commas
			for (Map.Entry<Currency, BigDecimal> entry : sorted) {
				Currency currency = entry.getKey();
				BigDecimal amount = entry.getValue();
				
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
