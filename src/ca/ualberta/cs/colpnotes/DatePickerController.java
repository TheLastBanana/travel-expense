package ca.ualberta.cs.colpnotes;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

/*
 * A controller between a TextView and a Calendar which allows the user
 * to set the Calendar date in a dialog and displays the date formatted
 * nicely in the TextView.
 */
public class DatePickerController {
	private TextView textView;
	private Calendar calendar;
	private DatePickerDialog.OnDateSetListener listener;
	
	/**
	 * Construct the DatePickerController.
	 * @param textView	The TextView to show the date in.
	 * @param calendar	The Calendar to store the selected date in.
	 * @param listener	An additional listener for when the date is set (or null for no listener).
	 */
	public DatePickerController(TextView textView, Calendar calendar, DatePickerDialog.OnDateSetListener listener) {
	    this.textView = textView;
	    this.calendar = calendar;
	    this.listener = listener;
	    
	    // Show dialog on click
	    textView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog();
			}
		});
    }
	
	private void showDialog() {
		DatePickerDialog dialog = new DatePickerDialog(textView.getContext(),
				new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,
					        int dayOfMonth) {
						setDate(year, monthOfYear, dayOfMonth);
						listener.onDateSet(view, year, monthOfYear, dayOfMonth);
					}
				},
				this.calendar.get(Calendar.YEAR),
				this.calendar.get(Calendar.MONTH),
				this.calendar.get(Calendar.DAY_OF_MONTH));
		
		dialog.show();
	}
	
	public void setDate(int year, int month, int day) {
		calendar.set(year, month, day);
		
		String dateString = DateFormat.getMediumDateFormat(textView.getContext()).format(calendar.getTime());
		textView.setText(dateString);
	}
}
