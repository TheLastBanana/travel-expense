package ca.ualberta.cs.colpnotes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint.Join;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

/*
 * Effectively acts as a gateway to all claim-editing functionality
 * for existing claims.
 * 
 * Lists the expenses for a given claim and allows the user to
 * modify the list of expenses. Also allows the user to edit the
 * claim, change its status, or delete it.
 */
public class ListExpensesActivity extends Activity {
	public static final String CLAIM_INDEX = "CLAIM_INDEX";
	
	private Claim claim = null;
	ExpenseAdapter expenseListAdapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_expenses);
		
		// Get the claim
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	int claimIndex = extras.getInt(CLAIM_INDEX, -1);
        	
        	if (claimIndex != -1) claim = ClaimListController.getClaimList().getClaim(claimIndex);
        }
        
        // Missing a claim
        if (claim == null) {
        	throw new RuntimeException("Attempted to list expenses for null claim");
        }
        
        // Get the list of expenses
        ArrayList<Expense> expenses = claim.getExpenseList().getExpenses();
        
        // Create the adapter
        expenseListAdapter = new ExpenseAdapter(this, R.layout.expense_list_item, expenses);
        
        // Attach to the ListView 
        ListView listView = (ListView) findViewById(R.id.expense_listview);
        listView.setAdapter(expenseListAdapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
	            
				Expense expense = (Expense) expenseListAdapter.getItem(position);
				int claimIndex = ClaimListController.getClaimList().indexOf(claim);
				int expenseIndex = claim.getExpenseList().indexOf(expense);
				
		    	Intent intent = new Intent(ListExpensesActivity.this, EditExpenseActivity.class);
		    	intent.putExtra(EditExpenseActivity.CLAIM_INDEX, claimIndex);
		    	intent.putExtra(EditExpenseActivity.EXPENSE_INDEX, expenseIndex);
		    	
		    	startActivity(intent);
            }
		});
	}
	
	@Override
	protected void onResume() {
		// Update list
    	if (expenseListAdapter != null) expenseListAdapter.notifyDataSetChanged();
    	
    	// Update total
    	updateTotal();
    	
    	setTitle(claim.getName());
    	
        super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_expenses, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		switch (id) {
		case R.id.action_edit_claim:
			editClaim();
			return true;
			
		case R.id.action_add_expense:
			addExpense();
			return true;
			
		case R.id.action_delete_claim:
			deleteAlert();
			return true;
	    	
		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	// Switch to the edit claim activity
	private void editClaim() {
		int claimIndex = ClaimListController.getClaimList().indexOf(claim);
		
    	Intent intent = new Intent(ListExpensesActivity.this, EditClaimActivity.class);
    	intent.putExtra(EditClaimActivity.CLAIM_INDEX, claimIndex);
    	
    	startActivity(intent);
	}
	
	// Switch to the edit expense activity with a new expense
	private void addExpense() {
		int claimIndex = ClaimListController.getClaimList().indexOf(claim);
		
    	Intent intent = new Intent(ListExpensesActivity.this, EditExpenseActivity.class);
    	intent.putExtra(EditExpenseActivity.CLAIM_INDEX, claimIndex);
    	
    	startActivity(intent);
	}
	
	/**
	 * Ask before deleting claim.
	 */
	private void deleteAlert() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.delete_claim_message)
			   .setPositiveButton(R.string.action_delete_claim, new DialogInterface.OnClickListener() {
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
	 * Delete the claim.
	 */
	private void deleteClaim() {
		ClaimListController.getClaimList().removeClaim(claim);
		ClaimListController.save();
		
		finish();
	}
	
	// Update the total
	private void updateTotal() {
		// Update view
		TextView totalView = (TextView) findViewById(R.id.expense_total_textview);
		
		// Build the string
		StringBuilder builder = new StringBuilder();
		builder.append(getString(R.string.total_label) + " (");
		builder.append(getString(ClaimStatus.getNameID(claim.getStatus())));
		builder.append("):\n");
		builder.append(ClaimHelper.getTotalString(claim, this));
		
		totalView.setText(builder.toString());
	}
}
