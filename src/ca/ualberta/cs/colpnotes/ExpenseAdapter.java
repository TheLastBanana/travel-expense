package ca.ualberta.cs.colpnotes;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/*
 * An adapter used to display an ArrayList of Expenses.
 * 
 * Used the following code for reference:
 * https://devtut.wordpress.com/2011/06/09/custom-arrayadapter-for-a-listview-android/
 * Accessed on 17/01/15
 */
public class ExpenseAdapter extends ArrayAdapter<Expense> {
	private ArrayList<Expense> expenses;

	public ExpenseAdapter(Context context, int textViewResourceId, ArrayList<Expense> expenses) {
	    super(context, textViewResourceId, expenses);
	    this.expenses = expenses;
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
	    
	    Expense expense = expenses.get(position);
	    
	    // Set expense description
	    TextView descText = (TextView) view.findViewById(R.id.description_textview);
	    if (descText != null) {
	    	descText.setText(expense.getDescription());
	    }
	    
	    // Set claim status
	    //
	    // Referenced for title case:
	    // http://stackoverflow.com/questions/12656941/format-name-in-title-case-java-help-please
	    // on 17/01/15
	    TextView statusText = (TextView) view.findViewById(R.id.amount_textview);
	    if (statusText != null) {
	    	statusText.setText(expense.getAmount().toString());
	    }
	    
	    
	    return view;
    }
	
	
}
