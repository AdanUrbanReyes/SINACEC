package modelo.spinner;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import modelo.basedatos.CentroComercial;

/**
 * Created by ayan on 11/16/16.
 */
public class SpinnerAdapter extends ArrayAdapter<Object> {
    private static float fontSize = 21;
    private Context context;
    private Object[] labels;

    public SpinnerAdapter(Context context,int resource, Object[]labels){
        super(context,resource,labels);
        this.context = context;
        this.labels = labels;
    }

    public int size(){
        return (this.labels != null) ? this.labels.length : 0;
    }

    public Object getItem(int position){
        return (this.labels != null && position > -1 && position < this.labels.length) ? this.labels[position] : null;
    }

    public int getPosition(String label){
        if(this.labels == null){
            return -1;
        }
        int i;
        for(i=0; i < this.labels.length; i++){
            if(this.labels[i].toString() == label){
                return i;
            }
        }
        return -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setTextSize(fontSize);
        label.setText(this.labels[position].toString());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setTextSize(fontSize);
        label.setText(this.labels[position].toString());
        return label;
    }

}
