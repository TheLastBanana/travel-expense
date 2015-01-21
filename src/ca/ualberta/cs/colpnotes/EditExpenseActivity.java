package ca.ualberta.cs.colpnotes;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class EditExpenseActivity extends Activity {
	public static final String CLAIM_INDEX = "CLAIM_INDEX";
	public static final String EXPENSE_INDEX = "EXPENSE_INDEX";
	
	private View actionBarView;
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        actionBarView = getLayoutInflater().inflate(R.layout.menu_edit_expense, null);
        ActionBar actionBar = getActionBar();
        actionBar.setCustomView(actionBarView);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //actionBar.setDisplayHomeAsUpEnabled(true);
        getMenuInflater().inflate(R.menu.edit_expense, menu);
        
        return true;
    }

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);
	}

}
