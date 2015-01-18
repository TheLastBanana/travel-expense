package ca.ualberta.cs.colpnotes;

import java.util.Calendar;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.widget.DatePicker;

/*
 * Creates a date picker. Based on code from:
 * http://stackoverflow.com/a/23667053
 * accessed on 17/01/15
 */
public class DatePickerFragment extends DialogFragment
{
	private OnDateSetListener listener;
	
	/*
	 * Create a DatePickerFragment with the given initial date and listener for when the date is set
	 */
	public static DatePickerFragment newInstance(Calendar initialDate, OnDateSetListener listener) {
		DatePickerFragment fragment = new DatePickerFragment();
		fragment.listener = listener;
		
		Bundle bundle = new Bundle();
		bundle.putSerializable("initialDate", initialDate);
		fragment.setArguments(bundle);
		
		return fragment;
	}
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		super.onCreateDialog(savedInstanceState);
		
		Calendar calendar = (Calendar) getArguments().getSerializable("initialDate");
		
		// Fall back to today's date
		if (calendar == null) {
			calendar = Calendar.getInstance();
		}
		
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), listener, year, month, day);
        dialog.setCancelable(true);
        
        return dialog;
    }
}
