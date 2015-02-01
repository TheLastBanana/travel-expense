package ca.ualberta.cs.colpnotes.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

final public class CurrencyHelper {
	// This is a static class; no instances
	private CurrencyHelper() {}
	
	private static ArrayList<Currency> currencies = null;
	
	/*
	 * Get the list of all currencies, sorted alphabetically by ISO code
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
