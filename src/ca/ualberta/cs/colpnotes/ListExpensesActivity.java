package ca.ualberta.cs.colpnotes;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

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
	ListAdapter expenseListAdapter = null;

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
        
        setTitle(claim.getName());
        
        // Get the list of expenses
        ArrayList<Expense> expenses = claim.getExpenses();
        
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
				int expenseIndex = claim.getExpenses().indexOf(expense);
				
		    	Intent intent = new Intent(ListExpensesActivity.this, EditExpenseActivity.class);
		    	intent.putExtra(EditExpenseActivity.CLAIM_INDEX, claimIndex);
		    	intent.putExtra(EditExpenseActivity.EXPENSE_INDEX, expenseIndex);
		    	
		    	startActivity(intent);
            }
		});
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
}
