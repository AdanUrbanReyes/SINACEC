package utilerias;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;


/**
 * Created by ayan on 11/6/16.
 */
public class Clock {
    private TextView selectedTimeTV;

    public void showTimePickerDialog(Context context, String title,final Date date, TextView textView){
        this.selectedTimeTV = textView;
        Calendar currentTime = Calendar.getInstance();
        TimePickerDialog tpd = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                date.setHours(selectedHour);
                date.setMinutes(selectedMinute);
                date.setSeconds(0);
                selectedTimeTV.setText(Mysql.formatoTiempo.format(date));
            }
        }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE), false);
        tpd.setTitle(title);
        tpd.show();
    }
}