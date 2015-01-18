package ca.ualberta.cs.colpnotes;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

public class EditClaimActivity extends Activity {
	private View actionBarView;
	private Claim claim = null;
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        actionBarView = getLayoutInflater().inflate(R.layout.menu_edit_claim, null);
        ActionBar actionBar = getActionBar();
        actionBar.setCustomView(actionBarView);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //actionBar.setDisplayHomeAsUpEnabled(true);
        getMenuInflater().inflate(R.menu.edit_claim, menu);
        
        return true;
    }
    
    /** Called when the user wants to set the "from" date. */
    public void pickFromDate(View v) {
    	DialogFragment picker = DatePickerFragment.newInstance(claim.getStart(), new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int month, int day) {
				Calendar cal = Calendar.getInstance();
				cal.set(year, month, day);
				claim.setStart(cal);
				updateFromDate();
			}
		});
    	
    	picker.show(getFragmentManager(), "datePicker");
    }
    
    /** Called when the user wants to set the "to" date. */
    public void pickToDate(View v) {
    	
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
	}

	/** Update the "from" date view. */
	//TODO: this should probably be its own class
	private void updateFromDate() {
		TextView fromText = (TextView) findViewById(R.id.claim_from_date_edittext);
		Calendar cal = claim.getStart();
		
		String dateString = DateFormat.getMediumDateFormat(this).format(cal.getTime());
		fromText.setText(dateString);
	}

	/** Update the "to" date. */
	private void updateToDate() {
		
	}
}
