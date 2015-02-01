package ca.ualberta.cs.colpnotes.viewcontroller;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ca.ualberta.cs.colpnotes.R;
import ca.ualberta.cs.colpnotes.model.Expense;

/**
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

	/**
	 * Return a view with the custom claim layout.
	 */
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
	    View view = convertView;
	    
	    if (convertView == null) {
	    	LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    	view = inflater.inflate(R.layout.expense_list_item, null);
	    }
	    
	    Expense expense = expenses.get(position);
	    
	    // Set expense category
	    TextView mainTextView = (TextView) view.findViewById(R.id.main_textview);
	    if (mainTextView != null) {
	    	mainTextView.setText(expense.getAmount().toString() + " " + expense.getCurrency().getCurrencyCode());
	    }
	    
	    // Set expense cost
	    TextView secondaryTextView = (TextView) view.findViewById(R.id.secondary_textview);
	    if (secondaryTextView != null) {
	    	Calendar calendar = expense.getDate();
	    	String dateString = DateFormat.getMediumDateFormat(getContext()).format(calendar.getTime());
	    	secondaryTextView.setText(dateString + " - " + expense.getCategory());
	    }
	    
	    return view;
    }
}
