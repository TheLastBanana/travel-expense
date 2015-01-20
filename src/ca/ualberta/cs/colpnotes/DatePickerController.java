package ca.ualberta.cs.colpnotes;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
	private boolean cancelled = false;
	
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
	    
	    updateTextView();
    }
	
	public void setDate(int year, int month, int day) {
		calendar.set(year, month, day);
		updateTextView();
	}
	
	public void updateTextView() {
		String dateString = DateFormat.getMediumDateFormat(textView.getContext()).format(calendar.getTime());
		textView.setText(dateString);
	}
	
	private void showDialog() {
		cancelled = true;
		
		// Create the dialog
		DatePickerDialog dialog = new DatePickerDialog(textView.getContext(),
				new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,
					        int dayOfMonth) {
						
						if (!cancelled) {
							setDate(year, monthOfYear, dayOfMonth);
							listener.onDateSet(view, year, monthOfYear, dayOfMonth);
						}
					}
				},
				this.calendar.get(Calendar.YEAR),
				this.calendar.get(Calendar.MONTH),
				this.calendar.get(Calendar.DAY_OF_MONTH));
		
		// Multi-button setup based on code from:
		// http://stackoverflow.com/a/21529892
		// Accessed on 19/01/15
		Context ctxt = textView.getContext();
		
		dialog.setButton(Dialog.BUTTON_POSITIVE, ctxt.getString(R.string.action_set), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				cancelled = false;
			}
		});
		
		dialog.setButton(Dialog.BUTTON_NEGATIVE, ctxt.getString(R.string.action_cancel), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				cancelled = true;
			}
		});
		
		dialog.show();
	}
}
