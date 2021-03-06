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

package ca.ualberta.cs.colpnotes.activities;

import java.util.ArrayList;
import java.util.Comparator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import ca.ualberta.cs.colpnotes.R;
import ca.ualberta.cs.colpnotes.model.Claim;
import ca.ualberta.cs.colpnotes.viewcontroller.ClaimAdapter;
import ca.ualberta.cs.colpnotes.viewcontroller.ClaimListController;
import ca.ualberta.cs.colpnotes.viewcontroller.ClaimListManager;

/**
 * The main activity which lists all existing claims so they can be edited.
 */
public class MainActivity extends Activity {
	private ClaimAdapter claimListAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClaimListManager.initManager(this.getApplicationContext());
        
        // Create the adapter
        ArrayList<Claim> claims = ClaimListController.getClaimList().getClaims();
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
    	claimListAdapter.notifyDataSetChanged();
    	claimListAdapter.sort(new Comparator<Claim>() {
			@Override
            public int compare(Claim lhs, Claim rhs) {
	            return lhs.getFrom().before(rhs.getFrom()) ? -1 : 1;
            }
		});
    	
        super.onResume();
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

    /**
     * Go to the activity to add a new claim.
     */
    private void addClaim() {
    	Intent intent = new Intent(MainActivity.this, EditClaimActivity.class);
    	startActivity(intent);
    }
}
