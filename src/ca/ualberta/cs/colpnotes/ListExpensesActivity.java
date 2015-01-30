package ca.ualberta.cs.colpnotes;

import java.util.ArrayList;
import java.util.Comparator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
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
	
	private Menu menu = null;
	private Claim claim = null;
	private ExpenseAdapter expenseListAdapter = null;

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
    	if (expenseListAdapter != null) {
    		expenseListAdapter.notifyDataSetChanged();
        	expenseListAdapter.sort(new Comparator<Expense>() {
    			@Override
                public int compare(Expense lhs, Expense rhs) {
    	            return lhs.getDate().before(rhs.getDate()) ? -1 : 1;
                }
    		});
    	}
    	
    	setTitle(claim.getName());
    	
        super.onResume();
    	
    	// Update status and total
    	if (menu != null) updateStatus();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_expenses, menu);
		this.menu = menu;
		
		updateStatus();
		
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
			
		case R.id.action_submit_claim:
			submitClaim();
			return true;
			
		case R.id.action_return_claim:
			returnClaim();
			return true;
			
		case R.id.action_approve_claim:
			approveAlert();
			return true;
			
		case R.id.action_mail_claim:
			mailClaim();
			return true;
	    	
		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Switch to the edit claim activity for this claim.
	 */
	private void editClaim() {
		int claimIndex = ClaimListController.getClaimList().indexOf(claim);
		
    	Intent intent = new Intent(ListExpensesActivity.this, EditClaimActivity.class);
    	intent.putExtra(EditClaimActivity.CLAIM_INDEX, claimIndex);
    	
    	startActivity(intent);
	}
	
	/**
	 * Switch to the edit expense activity with a new expense.
	 */
	private void addExpense() {
		int claimIndex = ClaimListController.getClaimList().indexOf(claim);
		
    	Intent intent = new Intent(ListExpensesActivity.this, EditExpenseActivity.class);
    	intent.putExtra(EditExpenseActivity.CLAIM_INDEX, claimIndex);
    	
    	startActivity(intent);
	}
	
	/**
	 * Change the claim status to submitted.
	 */
	private void submitClaim() {
		claim.setStatus(ClaimStatus.SUBMITTED);
		ClaimListController.save();
		updateStatus();
	}
	
	/**
	 * Change to the claim status to returned.
	 */
	private void returnClaim() {
		claim.setStatus(ClaimStatus.RETURNED);
		ClaimListController.save();
		updateStatus();
	}
	
	/**
	 * Change to the claim status to approved.
	 */
	private void approveClaim() {
		claim.setStatus(ClaimStatus.APPROVED);
		ClaimListController.save();
		updateStatus();
	}
	
	/**
	 * Ask if the user wants to approve the claim.
	 */
	private void approveAlert() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.approve_claim_message)
			   .setPositiveButton(R.string.action_approve_claim, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						approveClaim();
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
	
	/**
	 * Updates the total text at the bottom of the list.
	 */
	private void updateTotal() {
		// Update view
		TextView totalView = (TextView) findViewById(R.id.expense_total_textview);
		
		// Build the string
		StringBuilder builder = new StringBuilder();
		builder.append(getString(R.string.total_label) + " (");
		builder.append(getString(ClaimStatus.getNameID(claim.getStatus())));
		builder.append("):\n");
		builder.append(ExpenseListHelper.getTotalString(claim.getExpenseList(), this));
		
		totalView.setText(builder.toString());
	}
	
	/**
	 * Mail the claim.
	 * 
	 * Based on code from:
	 * http://stackoverflow.com/a/2197841
	 * Accessed on 27/01/15
	 */
	private void mailClaim() {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_SUBJECT, "Travel expense claim (" + claim.getName() + ")");
		i.putExtra(Intent.EXTRA_TEXT, ClaimHelper.prettyPrint(claim, this));
		
		try {
			startActivity(i);
		}
		catch (ActivityNotFoundException e) {
			Toast.makeText(this, "No email client installed!", Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * Updates button states and text based on new claim status.
	 * Throws an exception if the menu is null.
	 * @throws RuntimeException
	 */
	private void updateStatus() {
		if (menu == null) {
			throw new RuntimeException("Null menu can't be updated");
		}
		
		updateTotal();
		
		ClaimStatus claimStatus = claim.getStatus();
		
		switch (claimStatus) {
		// Can submit; can edit
		case IN_PROGRESS:
		case RETURNED:
			menu.findItem(R.id.action_submit_claim).setVisible(true);
			menu.findItem(R.id.action_submit_claim).setEnabled(true);
			menu.findItem(R.id.action_return_claim).setVisible(false);
			menu.findItem(R.id.action_return_claim).setEnabled(false);
			menu.findItem(R.id.action_approve_claim).setVisible(false);
			menu.findItem(R.id.action_approve_claim).setEnabled(false);
			break;
		
		// Can return or approve; can't edit
		case SUBMITTED:
			menu.findItem(R.id.action_submit_claim).setVisible(false);
			menu.findItem(R.id.action_submit_claim).setEnabled(false);
			menu.findItem(R.id.action_return_claim).setVisible(true);
			menu.findItem(R.id.action_return_claim).setEnabled(true);
			menu.findItem(R.id.action_approve_claim).setVisible(true);
			menu.findItem(R.id.action_approve_claim).setEnabled(true);
			break;
		
		// Can't change status; can't edit
		case APPROVED:
			menu.findItem(R.id.action_submit_claim).setVisible(false);
			menu.findItem(R.id.action_submit_claim).setEnabled(false);
			menu.findItem(R.id.action_return_claim).setVisible(false);
			menu.findItem(R.id.action_return_claim).setEnabled(false);
			menu.findItem(R.id.action_approve_claim).setVisible(false);
			menu.findItem(R.id.action_approve_claim).setEnabled(false);
			break;
		}
		
		// Enable/disable edit buttons
		boolean editable = ClaimStatus.getEditable(claimStatus);
		menu.findItem(R.id.action_add_expense).setVisible(editable);
	}
}
