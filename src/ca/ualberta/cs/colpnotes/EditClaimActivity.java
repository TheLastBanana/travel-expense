package ca.ualberta.cs.colpnotes;

import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

public class EditClaimActivity extends Activity {
	private View actionBarView;
	private Claim claim = null;
	private boolean newClaim = false;

	private DatePickerController fromPicker = null;
	private DatePickerController toPicker = null;
	private Calendar fromDate;
	private Calendar toDate;
	
	@Override
	public void onBackPressed() {
		discardAlert();
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        actionBarView = getLayoutInflater().inflate(R.layout.menu_edit_claim, null);
        ActionBar actionBar = getActionBar();
        actionBar.setCustomView(actionBarView);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getMenuInflater().inflate(R.menu.edit_claim, menu);
        
        // Handle onClick for custom accept button
        actionBar.getCustomView().findViewById(R.id.accept_claim_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
	        	saveChanges();
	        	finish();
			}
		});
        
        return true;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        
        switch (id) {
        case R.id.action_discard_claim_changes:
        	discardAlert();
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
        setContentView(R.layout.activity_edit_claim);
        
        // Get the claim
        if (savedInstanceState != null) {
        	int claimIndex = savedInstanceState.getInt("claimIndex", -1);
        	if (claimIndex != -1) claim = ClaimListController.getClaimList().getClaim(claimIndex);
        }
        
        // Making a new claim
        if (claim == null) {
        	claim = new Claim();
        	ClaimListController.getClaimList().addClaim(claim);
        	
        	newClaim = true;
        	
    	// Editing an old claim, so populate values
        } else {
        	newClaim = false;
        }
        	
    	((TextView) findViewById(R.id.claim_name_edittext)).setText(claim.getName());
    	((TextView) findViewById(R.id.claim_destination_edittext)).setText(claim.getDestination());
    	((TextView) findViewById(R.id.claim_reason_edittext)).setText(claim.getReason());
    	fromDate = (Calendar) claim.getFrom().clone();
    	toDate = (Calendar) claim.getTo().clone();
        
        // Set up the date fields
        TextView fromText = (TextView) findViewById(R.id.claim_from_date_edittext);
        fromPicker = new DatePickerController(fromText, fromDate,
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
        
        TextView toText = (TextView) findViewById(R.id.claim_to_date_edittext);
        toPicker = new DatePickerController(toText, toDate,
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
	}
	
	// Ask before discarding changes
	private void discardAlert() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.discard_message)
			   .setPositiveButton(R.string.action_discard, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						discardChanges();
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
	
	// Discard the changes to the current claim
	private void discardChanges() {
    	// Delete the new claim
    	if (newClaim) {
    		ClaimListController.getClaimList().removeClaim(claim);
    	}
	}
	
	// Save the changes to the current claim
	private void saveChanges() {
    	claim.setName(((TextView) findViewById(R.id.claim_name_edittext)).getText().toString());
    	claim.setDestination(((TextView) findViewById(R.id.claim_destination_edittext)).getText().toString());
    	claim.setReason(((TextView) findViewById(R.id.claim_reason_edittext)).getText().toString());
    	claim.setFrom(fromDate);
    	claim.setTo(toDate);
	}
}
