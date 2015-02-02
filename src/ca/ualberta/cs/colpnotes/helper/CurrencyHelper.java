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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * A static class which contains functions to help working with Currency.
 */
final public class CurrencyHelper {
	// This is a static class; no instances
	private CurrencyHelper() {}
	
	// Holds the list of all currencies
	private static ArrayList<Currency> currencies = null;
	
	/**
	 * Get the list of all currencies, sorted alphabetically by ISO 4217 code.
	 * 
	 * @return A list of all currencies, sorted alphabetically by ISO 4217 code.
	 */
	public static ArrayList<Currency> getAllCurrencies() {
		if (currencies == null) {
	        // Get the set of currencies
	        Set<Currency> allCurrencies = new HashSet<Currency>();
	        for (Locale locale : Locale.getAvailableLocales())
	        {
	        	try {
	        		Currency currency = Currency.getInstance(locale);
	        		allCurrencies.add(currency);
	        	}
	        	catch (IllegalArgumentException e) {
	        		// No currency for this locale
	        	}
	        }
	        
	        // Alphabetize
	        currencies = new ArrayList<Currency>(allCurrencies);
	        Collections.sort(currencies, new Comparator<Currency>() {
				@Override
	            public int compare(Currency lhs, Currency rhs) {
		            return lhs.getCurrencyCode().compareTo(rhs.getCurrencyCode());
	            }
			});
		}
        
        return currencies;
	}
}
