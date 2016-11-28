package modelo.list_view;

import android.view.View;
import android.widget.TextView;

import ayan.sinacec.R;

/**
 * Created by ayan on 11/15/16.
 */
public class ViewHolder2 {
    TextView labelTV;
    TextView label1TV;
    TextView label2TV;

    public ViewHolder2(View v){
        labelTV = (TextView) v.findViewById(R.id.labelTV);
        label1TV = (TextView) v.findViewById(R.id.label1TV);
        label2TV = (TextView) v.findViewById(R.id.label2TV);
    }
}
