package utilerias;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by ayan on 11/21/16.
 */
public class Calendar {

    public void showDatePickerDialog(Context context, String title, final Date date, final TextView textView) {
        java.util.Calendar currentTime = java.util.Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    java.util.Calendar st = java.util.Calendar.getInstance();
                    st.set(year,month,day);
                    date.setTime(st.getTimeInMillis());
                    textView.setText(Mysql.formatoFecha.format(date));
                }
            },
            currentTime.get(java.util.Calendar.YEAR),
            currentTime.get(java.util.Calendar.MONTH),
            currentTime.get(java.util.Calendar.DAY_OF_MONTH)
        );
        dpd.setTitle(title);
        dpd.show();
    }
}
