package modelo.list_view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ayan.sinacec.R;

/**
 * Created by ayan on 11/14/16.
 */
public class ViewHolder {
    ImageView imageIV;
    TextView labelTV;

    public ViewHolder(View v){
        imageIV = (ImageView) v.findViewById(R.id.imageIV);
        labelTV = (TextView) v.findViewById(R.id.labelTV);
    }

}
