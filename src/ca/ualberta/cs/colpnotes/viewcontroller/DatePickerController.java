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

package ca.ualberta.cs.colpnotes.viewcontroller;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import ca.ualberta.cs.colpnotes.R;

/**
 * A controller between a TextView and a Calendar which allows the user
 * to set the Calendar date in a dialog and displays the date formatted
 * nicely in the TextView.
 * 
 * This class helps to avoid boilerplate code by automatically updating
 * the given Calendar and setting up the textView to show the dialog.
 */
public class DatePickerController {
	private TextView textView;
	private Calendar calendar;
	private Calendar minDate;
	private Calendar maxDate;
	private DatePickerDialog.OnDateSetListener listener;
	private boolean cancelled = false;
	
	/**
	 * Construct the DatePickerController.
	 * @param textView	The TextView to show the date in.
	 * @param calendar	The Calendar to store the selected date in. It will be updated automatically.
	 * @param minDate	The minimum date to be selected, or null.
	 * @param maxDate	The maximum date to be selected, or null.
	 * @param listener	An additional listener for when the date is set (or null for no listener).
	 */
	public DatePickerController(TextView textView, Calendar calendar, Calendar minDate, Calendar maxDate,
			DatePickerDialog.OnDateSetListener listener) {
	    this.textView = textView;
	    this.calendar = calendar;
	    this.listener = listener;
	    this.minDate = minDate;
	    this.maxDate = maxDate;
	    
	    // Potential crash bug workaround
	    // http://stackoverflow.com/a/15698792
	    // Referenced on 24/01/15
	    if (this.minDate != null) {
	    	this.minDate.set(Calendar.MILLISECOND, this.minDate.getMinimum(Calendar.MILLISECOND));
	    }
	    if (this.maxDate != null) {
	    	this.maxDate.set(Calendar.MILLISECOND, this.minDate.getMaximum(Calendar.MILLISECOND));
	    }
	    
	    // Show dialog on click
	    textView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog();
			}
		});
	    
	    updateTextView();
    }
	
	/**
	 * Get the controlled TextView.
	 * @return The TextView.
	 */
	public TextView getTextView() {
		return textView;
	}

	/**
	 * Get the Calendar storing the date.
	 * @return The Calendar.
	 */
	public Calendar getCalendar() {
		return calendar;
	}

	/**
	 * Set the date.
	 * @param year The year.
	 * @param month The month of the year.
	 * @param day The day of the month.
	 */
	public void setDate(int year, int month, int day) {
		calendar.set(year, month, day);
		updateTextView();
	}
	
	/**
	 * Update the controlled TextView.
	 */
	public void updateTextView() {
		String dateString = DateFormat.getMediumDateFormat(textView.getContext()).format(calendar.getTime());
		textView.setText(dateString);
	}
	
	/**
	 * Pop up the date picker dialog.
	 */
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
							if (listener != null) listener.onDateSet(view, year, monthOfYear, dayOfMonth);
						}
					}
				},
				this.calendar.get(Calendar.YEAR),
				this.calendar.get(Calendar.MONTH),
				this.calendar.get(Calendar.DAY_OF_MONTH));
		
		DatePicker picker = dialog.getDatePicker();
		if (minDate != null) picker.setMinDate(minDate.getTimeInMillis());
		if (maxDate != null) picker.setMaxDate(maxDate.getTimeInMillis());
		
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
