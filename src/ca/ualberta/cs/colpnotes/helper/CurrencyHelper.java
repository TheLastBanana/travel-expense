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
