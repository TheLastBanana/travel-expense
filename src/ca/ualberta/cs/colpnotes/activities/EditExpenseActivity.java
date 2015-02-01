package ca.ualberta.cs.colpnotes.activities;

import java.math.BigDecimal;
import java.util.Currency;

import ca.ualberta.cs.colpnotes.R;
import ca.ualberta.cs.colpnotes.R.array;
import ca.ualberta.cs.colpnotes.R.id;
import ca.ualberta.cs.colpnotes.R.layout;
import ca.ualberta.cs.colpnotes.R.menu;
import ca.ualberta.cs.colpnotes.R.string;
import ca.ualberta.cs.colpnotes.helper.CurrencyHelper;
import ca.ualberta.cs.colpnotes.model.Claim;
import ca.ualberta.cs.colpnotes.model.ClaimStatus;
import ca.ualberta.cs.colpnotes.model.Expense;
import ca.ualberta.cs.colpnotes.viewcontroller.ClaimListController;
import ca.ualberta.cs.colpnotes.viewcontroller.DatePickerController;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
	private Spinner categorySpinner = null;
	private Spinner currencySpinner = null;
	private EditText amountEditText = null;
	private TextView descriptionEditText = null;
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_expense, menu);
    	
    	if (ClaimStatus.getEditable(claim.getStatus())) {
    		// Create custom menu with accept button
            actionBarView = getLayoutInflater().inflate(R.layout.menu_edit_expense, null);
	        ActionBar actionBar = getActionBar();
	        actionBar.setCustomView(actionBarView);
	        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        
	        // Handle onClick for custom accept button
	        actionBar.getCustomView().findViewById(R.id.accept_expense_button).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (!validate()) return;
					
		        	saveChanges();
		        	finish();
				}
			});
	        
        // Not editable
    	} else {
    	    ActionBar actionBar = getActionBar();
    	    actionBar.setDisplayHomeAsUpEnabled(true);
    	    
    	    // Disable deletion
    	    menu.findItem(R.id.action_discard_expense_changes).setEnabled(false);
    	    menu.findItem(R.id.action_discard_expense_changes).setVisible(false);
    	    menu.findItem(R.id.action_delete_expense).setEnabled(false);
    	    menu.findItem(R.id.action_delete_expense).setVisible(false);
    	    
    	    // Disable editable views
    	    ((TextView) findViewById(R.id.expense_date_textview)).setClickable(false);
    	    ((Spinner) findViewById(R.id.expense_category_spinner)).setClickable(false);
    	    ((Spinner) findViewById(R.id.expense_currency_spinner)).setClickable(false);
    	    ((EditText) findViewById(R.id.expense_amount_edittext)).setKeyListener(null);
    	    ((EditText) findViewById(R.id.expense_description_edittext)).setKeyListener(null);
    	}
        
        return true;
    }
	
	@Override
	public void onBackPressed() {
		if (ClaimStatus.getEditable(claim.getStatus())) {
			discardAlert();
		} else {
			finish();
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        
        switch (id) {
        case R.id.action_discard_expense_changes:
        	discardAlert();
        	return true;
        	
        case R.id.action_delete_expense:
        	deleteAlert();
        	return true;
        	
        case android.R.id.home:
        	// We're not editable if this is visible, so just go back
        	finish();
        	return true;
        	
    	default:
    		break;
        }
        
	    return super.onOptionsItemSelected(item);
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
        categorySpinner = (Spinner) findViewById(R.id.expense_category_spinner);
        categorySpinner.setAdapter(categoryAdapter);
        
        // Populate currency spinner
        ArrayAdapter<Currency> currencyAdapter = new ArrayAdapter<Currency>(this,
        		android.R.layout.simple_spinner_item);
        currencyAdapter.addAll(CurrencyHelper.getAllCurrencies());
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        currencySpinner = (Spinner) findViewById(R.id.expense_currency_spinner);
        currencySpinner.setAdapter(currencyAdapter);
		
		// Get the claim
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	int claimIndex = extras.getInt(CLAIM_INDEX, -1);
        	int expenseIndex = extras.getInt(EXPENSE_INDEX, -1);
        	
        	if (claimIndex != -1) {
        		claim = ClaimListController.getClaimList().getClaim(claimIndex);
            	if (expenseIndex != -1) expense = claim.getExpenseList().get(expenseIndex);
        	}
        }
        
        // Missing claim
        if (claim == null) {
        	throw new RuntimeException("Attempted to edit expense for nonexistent claim");
        }
        
        // Need to make a new expense
        if (expense == null) {
        	tempExpense = new Expense();
        	
    	// Existing expense so make a copy of it
        } else {
        	tempExpense = new Expense(expense);
        }
        
        // Set up the date field as it needs a special controller
        // We don't need a special listener to update tempExpense.getDate, as the controller handles it
        TextView dateText = (TextView) findViewById(R.id.expense_date_textview);
        datePicker = new DatePickerController(dateText, tempExpense.getDate(), null, null, null);
        
    	// Get other fields
        amountEditText = (EditText) findViewById(R.id.expense_amount_edittext);
        descriptionEditText = (TextView) findViewById(R.id.expense_description_edittext);
        
        // Set listener
        categorySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
            public void onItemSelected(AdapterView<?> parent, View view,
                    int position, long id) {
				
	            tempExpense.setCategory(categorySpinner.getSelectedItem().toString());
            }

			@Override
            public void onNothingSelected(AdapterView<?> parent) {}
		});
        
        currencySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
            public void onItemSelected(AdapterView<?> parent, View view,
                    int position, long id) {
	            
				tempExpense.setCurrency((Currency) currencySpinner.getSelectedItem());
            }

			@Override
            public void onNothingSelected(AdapterView<?> parent) {}
		});
        
        amountEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				try {
					tempExpense.setAmount(new BigDecimal(s.toString()));
				}
				catch (NumberFormatException e) {
					amountFormatError();
				}
			}
		});
        
        descriptionEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				tempExpense.setDescription(s.toString());
			}
		});
        
        // Populate fields
        categorySpinner.setSelection(categoryAdapter.getPosition(tempExpense.getCategory()));
        currencySpinner.setSelection(currencyAdapter.getPosition(tempExpense.getCurrency()));
		descriptionEditText.setText(tempExpense.getDescription());
		amountEditText.setText(tempExpense.getAmount().toPlainString());
	}
	
	/*
	 * Displays a format error for the amount field
	 */
	private void amountFormatError() {
		amountEditText.setError(getString(R.string.amount_format_error));
	}
	
	/**
	 * Ask before deleting expense.
	 */
	private void deleteAlert() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.delete_expense_message)
			   .setPositiveButton(R.string.action_delete_expense, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						deleteClaim();
						finish();
					}
			   })
			   .setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
			   });
		builder.create().show();
	}
	
	/**
	 * Delete the expense.
	 */
	private void deleteClaim() {
		claim.getExpenseList().removeExpense(expense);
		ClaimListController.save();
		
		finish();
	}
	
	// Ask before discarding changes
	private void discardAlert() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.discard_message)
			   .setPositiveButton(R.string.action_discard, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
			   })
			   .setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
			   });
		builder.create().show();
	}
	
	// Save changes from the temporary expense to the actual expense
	// If necessary, create a new expense in the claim
	private void saveChanges() {
		// Create the new expense to the claim
		if (expense == null) {
			expense = new Expense(tempExpense);
			claim.getExpenseList().add(expense);
		
		// Expense already exists
		} else {
			expense.copyFrom(tempExpense);
		}
		
		ClaimListController.save();
	}
	
	/**
	 * Check each of the input fields for valid input.
	 * @return True if all fields validated, false if at least one is invalid.
	 */
	private boolean validate() {
		// Need a valid amount
		if (amountEditText.getText().length() == 0) {
			Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_LONG).show();
			return false;
		}
		
		return true;
	}
}
