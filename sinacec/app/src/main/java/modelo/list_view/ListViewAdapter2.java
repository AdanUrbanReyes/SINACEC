package modelo.list_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import ayan.sinacec.R;
import utilerias.Image;

/**
 * Created by ayan on 11/15/16.
 */
public class ListViewAdapter2 extends ArrayAdapter<SingleRow2> {
    private SingleRow2 []singleRows2;
    private Context context;
    public ListViewAdapter2(Context context,SingleRow2 []singleRows2){
        super(context, R.layout.single_row2,R.id.labelTV,singleRows2);
        this.singleRows2 = singleRows2;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        ViewHolder2 vh2 = null;
        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.single_row2,parent,false);
            vh2 = new ViewHolder2(row);
            row.setTag(vh2);
        }else{
            vh2 = (ViewHolder2) row.getTag();
        }
        vh2.labelTV.setText(singleRows2[position].getLabel());
        vh2.label1TV.setText(singleRows2[position].getLabel1());
        vh2.label2TV.setText(singleRows2[position].getLabel2());
        return row;
    }
}
