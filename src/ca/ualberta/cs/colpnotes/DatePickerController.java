package ca.ualberta.cs.colpnotes;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

public class DatePickerController {
	private TextView textView;
	private Calendar calendar;
	
	public DatePickerController(TextView textView, Calendar calendar) {
	    this.textView = textView;
	    this.calendar = calendar;
	    
	    // Show dialog on click
	    textView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog();
			}
		});
    }
	
	private void showDialog() {
		DatePickerDialog picker = new DatePickerDialog(textView.getContext(),
				new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,
					        int dayOfMonth) {
						setDate(year, monthOfYear, dayOfMonth);
					}
				},
				this.calendar.get(Calendar.YEAR),
				this.calendar.get(Calendar.MONTH),
				this.calendar.get(Calendar.DAY_OF_MONTH));
		
		picker.show();
	}
	
	private void setDate(int year, int month, int day) {
		calendar.set(year, month, day);
		
		String dateString = DateFormat.getMediumDateFormat(textView.getContext()).format(calendar.getTime());
		textView.setText(dateString);
	}
}
