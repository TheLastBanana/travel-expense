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

package ca.ualberta.cs.colpnotes.viewcontroller;

import java.util.ArrayList;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ca.ualberta.cs.colpnotes.R;
import ca.ualberta.cs.colpnotes.helper.ExpenseListHelper;
import ca.ualberta.cs.colpnotes.model.Claim;
import ca.ualberta.cs.colpnotes.model.ClaimStatus;

/**
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

	/**
	 * Return a view with the custom claim layout.
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
	    	int stringID = ClaimStatus.getNameID(claim.getStatus());
	    	nameText.setText(claim.getName() + " (" + getContext().getString(stringID) + ")");
	    }
	    
	    // Set claim total
	    TextView statusText = (TextView) view.findViewById(R.id.status_textview);
	    if (statusText != null) {
	    	Context context = view.getContext();
	    	
	    	StringBuilder sb = new StringBuilder();
	    	sb.append(DateFormat.getMediumDateFormat(context).format(claim.getFrom().getTime()));
	    	sb.append(" - ");
	    	sb.append(DateFormat.getMediumDateFormat(context).format(claim.getTo().getTime()));
	    	sb.append("\n");
	    	sb.append(ExpenseListHelper.getTotalString(claim.getExpenseList(), getContext()));
	    	
	    	statusText.setText(sb.toString());
	    }
	    
	    return view;
    }
}
