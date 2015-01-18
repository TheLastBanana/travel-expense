package ca.ualberta.cs.colpnotes;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/*
 * An adapter used to display an ArrayList of Claims.
 * 
 * Used the following code for reference:
 * https://devtut.wordpress.com/2011/06/09/custom-arrayadapter-for-a-listview-android/
 * Accessed on 17/01/15
 */
public class ClaimAdapter extends ArrayAdapter<Claim> {
	private ArrayList<Claim> claims;

	public ClaimAdapter(Context context, int textViewResourceId, ArrayList<Claim> claims) {
	    super(context, textViewResourceId, claims);
	    this.claims = claims;
    }

	/*
	 * Returns a view with the custom claim layout.
	 */
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
	    View view = convertView;
	    
	    if (convertView == null) {
	    	LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    	view = inflater.inflate(R.layout.claim_list_item, null);
	    }
	    
	    Claim claim = claims.get(position);
	    
	    // Set claim name
	    TextView nameText = (TextView) view.findViewById(R.id.name_textview);
	    if (nameText != null) {
	    	nameText.setText(claim.getName());
	    }
	    
	    // Set claim status
	    //
	    // Referenced for title case:
	    // http://stackoverflow.com/questions/12656941/format-name-in-title-case-java-help-please
	    // on 17/01/15
	    TextView statusText = (TextView) view.findViewById(R.id.status_textview);
	    if (statusText != null) {
	    	int stringID = ClaimStatus.getNameID(claim.getStatus());
	    	
	    	// Set first character as capital
	    	StringBuilder statusString = new StringBuilder(getContext().getString(stringID));
	    	statusString.setCharAt(0, Character.toTitleCase(statusString.charAt(0)));
	    	
	    	statusText.setText(statusString);
	    }
	    
	    
	    return view;
    }
	
	
}
