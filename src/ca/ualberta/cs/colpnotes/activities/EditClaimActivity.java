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

package ca.ualberta.cs.colpnotes.activities;

import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import ca.ualberta.cs.colpnotes.R;
import ca.ualberta.cs.colpnotes.model.Claim;
import ca.ualberta.cs.colpnotes.model.ClaimStatus;
import ca.ualberta.cs.colpnotes.viewcontroller.ClaimListController;
import ca.ualberta.cs.colpnotes.viewcontroller.DatePickerController;

/**
 * Lets the user modify the settings for a claim.
 * 
 * If CLAIM_INDEX is passed as an integer extra, then the claim in
 * ClaimListController at index CLAIM_INDEX will be edited rather
 * than creating a new one.
 */
public class EditClaimActivity extends Activity {
	public static final String CLAIM_INDEX = "CLAIM_INDEX";
	
	private View actionBarView;
	private Claim claim = null;
	private Claim tempClaim = null;
	
	private EditText nameEditText = null;
	private DatePickerController fromPicker = null;
	private DatePickerController toPicker = null;
	private EditText destinationEditText = null;
	private EditText reasonEditText = null;
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_claim, menu);
        
        if (ClaimStatus.getEditable(tempClaim.getStatus())) {
        	// Create custom menu with accept button
	        actionBarView = getLayoutInflater().inflate(R.layout.menu_edit_claim, null);
	        ActionBar actionBar = getActionBar();
	        actionBar.setCustomView(actionBarView);
	        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
	        
	        // Handle onClick for custom accept button
	        actionBar.getCustomView().findViewById(R.id.accept_claim_button).setOnClickListener(new View.OnClickListener() {
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
    	    menu.findItem(R.id.action_discard_claim_changes).setEnabled(false);
    	    menu.findItem(R.id.action_discard_claim_changes).setVisible(false);
    	    
    	    // Disable editable views
    	    ((TextView) findViewById(R.id.claim_from_date_textview)).setClickable(false);
    	    ((TextView) findViewById(R.id.claim_to_date_textview)).setClickable(false);
    	    ((EditText) findViewById(R.id.claim_name_edittext)).setKeyListener(null);
    	    ((EditText) findViewById(R.id.claim_destination_edittext)).setKeyListener(null);
    	    ((EditText) findViewById(R.id.claim_reason_edittext)).setKeyListener(null);
        }
        
        return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        
        switch (id) {
        case R.id.action_discard_claim_changes:
        	discardAlert();
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
	
	@Override
	public void onBackPressed() {
		if (ClaimStatus.getEditable(tempClaim.getStatus())) {
			discardAlert();
		} else {
			finish();
		}
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_claim);
        
        // Get the claim
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	int claimIndex = extras.getInt(CLAIM_INDEX, -1);
        	
        	if (claimIndex != -1) claim = ClaimListController.getClaimList().getClaim(claimIndex);
        }
        
        // Making a new claim
        if (claim == null) {
        	tempClaim = new Claim();
        	
    	// Editing an old claim, so populate values
        } else {
        	tempClaim = new Claim(claim);
        }
        	
    	nameEditText = (EditText) findViewById(R.id.claim_name_edittext);
    	destinationEditText = (EditText) findViewById(R.id.claim_destination_edittext);
    	reasonEditText = (EditText) findViewById(R.id.claim_reason_edittext);
    	final Calendar fromDate = tempClaim.getFrom();
    	final Calendar toDate = tempClaim.getTo();
        
        // Set up the date fields
        TextView fromText = (TextView) findViewById(R.id.claim_from_date_textview);
        fromPicker = new DatePickerController(fromText, fromDate, null, null,
        		new android.app.DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,
					        int dayOfMonth) {
						
		        		// Make sure start date comes before end date
						if (fromDate.after(toDate)) {
							toPicker.setDate(fromDate.get(Calendar.YEAR), fromDate.get(Calendar.MONTH), fromDate.get(Calendar.DAY_OF_MONTH));
						}
					}
				});
        
        TextView toText = (TextView) findViewById(R.id.claim_to_date_textview);
        toPicker = new DatePickerController(toText, toDate, null, null,
        		new android.app.DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,
					        int dayOfMonth) {

		        		// Make sure start date comes before end date
						if (fromDate.after(toDate)) {
							fromPicker.setDate(toDate.get(Calendar.YEAR), toDate.get(Calendar.MONTH), toDate.get(Calendar.DAY_OF_MONTH));
						}
					}
				});
        
        nameEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) { }
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
			
			@Override
			public void afterTextChanged(Editable s) {
				tempClaim.setName(s.toString());
			}
		});
        
        destinationEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) { }
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
			
			@Override
			public void afterTextChanged(Editable s) {
				tempClaim.setDestination(s.toString());
			}
		});
        
        reasonEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) { }
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
			
			@Override
			public void afterTextChanged(Editable s) {
				tempClaim.setReason(s.toString());
			}
		});
        
        nameEditText.setText(tempClaim.getName());
        destinationEditText.setText(tempClaim.getDestination());
        reasonEditText.setText(tempClaim.getReason());
	}
	
	/**
	 *  Ask before discarding changes
	 */
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
	
	/**
	 * Save the changes to the current claim.
	 * If necessary, create a new one in the static list.
	 */
	private void saveChanges() {
		// Create the new claim
		if (claim == null) {
			claim = new Claim(tempClaim);
			ClaimListController.getClaimList().addClaim(claim);
		
		// Expense already exists
		} else {
			claim.copyFrom(tempClaim);
		}
		
    	ClaimListController.save();
	}
	
	/**
	 * Check each of the input fields for valid input.
	 * @return True if all fields validated, false if at least one is invalid.
	 */
	private boolean validate() {
		String name = nameEditText.getText().toString();
		
		// Need a name
		if (name.length() == 0) {
			Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
			return false;
		}
		
		return true;
	}
}
