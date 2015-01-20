package ca.ualberta.cs.colpnotes;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
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
        final DatePickerController fromPicker = new DatePickerController(fromText, claim.getStart());
        
        TextView toText = (TextView) findViewById(R.id.claim_to_date_edittext);
        final DatePickerController toPicker = new DatePickerController(toText, claim.getEnd());
	}
}
