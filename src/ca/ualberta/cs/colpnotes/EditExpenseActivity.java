package ca.ualberta.cs.colpnotes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

/*
 * Lets the user modify the settings for an expense.
 * 
 * CLAIM_INDEX must be passed as an integer extra. The claim in
 * ClaimListController at index CLAIM_INDEX will be edited.
 * 
 * If EXPENSE_INDEX is passed as an integer extra, then the expense
 * in the claim's list at index EXPENSE_INDEX will be edited rather
 * than creating a new one.
 */
public class EditExpenseActivity extends Activity {
	public static final String CLAIM_INDEX = "CLAIM_INDEX";
	public static final String EXPENSE_INDEX = "EXPENSE_INDEX";
	
	private View actionBarView;
	private Claim claim = null;
	private Expense expense = null;		// This is the original expense
	private Expense tempExpense = null;	// This will be copied back to expense when changes are saved
	private DatePickerController datePicker = null;
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        actionBarView = getLayoutInflater().inflate(R.layout.menu_edit_expense, null);
        ActionBar actionBar = getActionBar();
        actionBar.setCustomView(actionBarView);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //actionBar.setDisplayHomeAsUpEnabled(true);
        getMenuInflater().inflate(R.menu.edit_expense, menu);
        
        return true;
    }

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);
        
        // Populate category spinner
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
        		R.array.expense_categories,
        		android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner categorySpinner = (Spinner) findViewById(R.id.expense_category_spinner);
        categorySpinner.setAdapter(categoryAdapter);
        
        // Populate currency spinner
        ArrayAdapter<Currency> currencyAdapter = new ArrayAdapter<Currency>(this,
        		android.R.layout.simple_spinner_item);
        currencyAdapter.addAll(CurrencyHelper.getAllCurrencies());
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        Spinner currencySpinner = (Spinner) findViewById(R.id.expense_currency_spinner);
        currencySpinner.setAdapter(currencyAdapter);
		
		// Get the claim
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	int claimIndex = extras.getInt(CLAIM_INDEX, -1);
        	int expenseIndex = extras.getInt(EXPENSE_INDEX, -1);
        	
        	if (claimIndex != -1) {
        		claim = ClaimListController.getClaimList().getClaim(claimIndex);
            	if (expenseIndex != -1) expense = claim.getExpenses().get(expenseIndex);
        	}
        }
        
        // Missing claim
        if (claim == null) {
        	throw new RuntimeException("Attempted to edit expense for nonexistent claim");
        }
        
        // Need to make a new expense
        if (expense == null) {
        	tempExpense = new Expense();
        	
    	// Existing expense, populate fields
        } else {
        	tempExpense = new Expense(expense);
        }
        
        // Set up the date field
        TextView fromText = (TextView) findViewById(R.id.expense_date_edittext);
        datePicker = new DatePickerController(fromText, tempExpense.getDate(), null);
        
    	// Populate fields
        updateAmountView();
        categorySpinner.setSelection(categoryAdapter.getPosition(tempExpense.getCategory()));
        currencySpinner.setSelection(currencyAdapter.getPosition(tempExpense.getCurrency()));
		((TextView) findViewById(R.id.expense_description_edittext)).setText(tempExpense.getDescription());
	}

	/*
	 * If the currency changes, the amount scale might as well, so this needs to be called.
	 */
	private void updateAmountView() {
		((TextView) findViewById(R.id.expense_amount_edittext)).setText(tempExpense.getAmount().toString());
	}
}
