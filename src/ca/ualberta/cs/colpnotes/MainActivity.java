package ca.ualberta.cs.colpnotes;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends Activity {
	private ClaimAdapter claimListAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClaimListManager.initManager(this.getApplicationContext());
        
        // Get the global claims from the singleton
        ArrayList<Claim> claims = ClaimListController.getClaimList().getClaims();
        
        // Create the adapter
        claimListAdapter = new ClaimAdapter(this, R.layout.claim_list_item, claims);
        
        // Attach to the ListView 
        ListView listView = (ListView) findViewById(R.id.claim_list_view);
        listView.setAdapter(claimListAdapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
	            
				Claim claim = claimListAdapter.getItem(position);
				int claimIndex = ClaimListController.getClaimList().indexOf(claim);
				
		    	Intent intent = new Intent(MainActivity.this, ListExpensesActivity.class);
		    	intent.putExtra(ListExpensesActivity.CLAIM_INDEX, claimIndex);
		    	
		    	startActivity(intent);
            }
		});
    }
    
    @Override
    protected void onResume() {
    	if (claimListAdapter != null) claimListAdapter.notifyDataSetChanged();
    	
        super.onResume();
    }

    public void addClaim() {
    	Intent intent = new Intent(MainActivity.this, EditClaimActivity.class);
    	startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add_claim) {
        	addClaim();
        	return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
