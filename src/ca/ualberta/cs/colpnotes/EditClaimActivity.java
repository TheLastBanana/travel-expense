package ca.ualberta.cs.colpnotes;

import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

public class EditClaimActivity extends Activity {
	private View actionBarView;
	private Claim claim = null;
	DatePickerController fromPicker = null;
	DatePickerController toPicker = null;
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        actionBarView = getLayoutInflater().inflate(R.layout.menu_edit_claim, null);
        ActionBar actionBar = getActionBar();
        actionBar.setCustomView(actionBarView);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getMenuInflater().inflate(R.menu.edit_claim, menu);
        
        return true;
    }

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_claim);
        
        // Get the claim
        if (savedInstanceState != null) {
        	claim = (Claim) savedInstanceState.getSerializable("claim");
        }
        
        // Making a new claim
        if (claim == null) {
        	claim = new Claim();
        	
    	// Editing an old claim, so populate values
        } else {
        	((TextView) findViewById(R.id.claim_name_edittext)).setText(claim.getName());
        	((TextView) findViewById(R.id.claim_destination_edittext)).setText(claim.getDestination());
        	((TextView) findViewById(R.id.claim_reason_edittext)).setText(claim.getReason());
        }
        
        // Set up the date fields
        TextView fromText = (TextView) findViewById(R.id.claim_from_date_edittext);
        fromPicker = new DatePickerController(fromText, claim.getFrom(),
        		new android.app.DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,
					        int dayOfMonth) {
						
						Calendar from = claim.getFrom();
						Calendar to = claim.getTo();
						
		        		// Make sure start date comes before end date
						if (from.after(to)) {
							toPicker.setDate(from.get(Calendar.YEAR), from.get(Calendar.MONTH), from.get(Calendar.DAY_OF_MONTH));
						}
					}
				});
        
        TextView toText = (TextView) findViewById(R.id.claim_to_date_edittext);
        toPicker = new DatePickerController(toText, claim.getTo(),
        		new android.app.DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,
					        int dayOfMonth) {

						Calendar from = claim.getFrom();
						Calendar to = claim.getTo();
						
		        		// Make sure start date comes before end date
						if (from.after(to)) {
							fromPicker.setDate(to.get(Calendar.YEAR), to.get(Calendar.MONTH), to.get(Calendar.DAY_OF_MONTH));
						}
					}
				});
	}
}
