package modelo.list_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import ayan.sinacec.R;
import utilerias.Image;

/**
 * Created by ayan on 11/14/16.
 */
public class ListViewAdapter extends ArrayAdapter<SingleRow>{
    private SingleRow []singleRows;
    private Context context;
    public ListViewAdapter(Context context,SingleRow []singleRows){
        super(context, R.layout.single_row,R.id.labelTV,singleRows);
        this.singleRows = singleRows;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        ViewHolder vh = null;
        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.single_row,parent,false);
            vh = new ViewHolder(row);
            row.setTag(vh);
        }else{
            vh = (ViewHolder) row.getTag();
        }
        Image.setImageFromBase64(singleRows[position].getImage(), vh.imageIV);
        vh.labelTV.setText(singleRows[position].getLabel());
        return row;
    }

}
