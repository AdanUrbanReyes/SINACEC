package ayan.sinacec;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import modelo.ComunicationWebServiceAndroid;
import modelo.basedatos.Local;
import modelo.list_view.ListViewAdapter2;
import utilerias.Image;
import utilerias.JSON;
import utilerias.WebService;

public class Area extends Fragment implements ComunicationWebServiceAndroid, AdapterView.OnItemLongClickListener {

    private OnFragmentInteractionListener mListener;

    private View view;
    private TextView mallTV;
    private ImageView imageIV;
    private EditText nameET, latitudET, longitudET;
    private ListView shopsLV;

    private WebService webService;

    private modelo.basedatos.Area area;
    private Local []shops;

    public Area() {

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void setInterfaceVariables(){
        mallTV = (TextView) view.findViewById(R.id.mallTV);
        imageIV = (ImageView) view.findViewById(R.id.imageIV);
        nameET = (EditText) view.findViewById(R.id.nameET);
        latitudET = (EditText) view.findViewById(R.id.latitudET);
        longitudET = (EditText) view.findViewById(R.id.longitudET);
        shopsLV = (ListView) view.findViewById(R.id.shopsLV);
        webService = new WebService(view.getContext(), this);
    }

    public void setListeners(){
        shopsLV.setOnItemLongClickListener(this);
    }

    public void setAreaOnInterface(){
        if(this.area != null){
            mallTV.setText(this.area.getId().getCentroComercial());
            Image.setImageFromBase64(this.area.getImagen(), imageIV);
            nameET.setText(this.area.getId().getNombre());
            latitudET.setText("" + this.area.getLatitud());
            longitudET.setText("" + this.area.getLongitud());
        }
    }

    public void setShops(String shops){
        if(shops.equals("[")){
            this.shops = (Local[]) JSON.aArrayObjeto(shops, "Local");
        }else{
            Local st = (Local) JSON.aObjeto(shops, "Local");
            this.shops = (st == null) ? new Local[0] : new Local[]{st};
        }
        shopsLV.setAdapter(new ListViewAdapter2(view.getContext(), ShopsManagement.toSingleRow2(this.shops)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_area, container, false);
        Bundle args = getArguments();
        if(args != null){
            this.area = (modelo.basedatos.Area) args.getSerializable("area");
            if(this.area != null){
                setInterfaceVariables();
                setListeners();
                setAreaOnInterface();
                webService.callMethodWebService("leer", WebService.pcrm, new Object[]{"{\"id\" : {\"centroComercial\" : \"" + this.area.getId().getCentroComercial() + "\", \"direccionCentroComercial\" : " + this.area.getId().getDireccionCentroComercial() + "}, \"area\" : \"" + this.area.getId().getNombre() + "\"}", "Local", null});
            }
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void manageResponse(Object responseWebService) {
        setShops(responseWebService.toString());
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        if(this.shops != null && position < this.shops.length){
            Bundle args = new Bundle();
            args.putSerializable("shop", this.shops[position]);
            Fragment shop = new Shop();
            shop.setArguments(args);
            getFragmentManager().beginTransaction().replace(R.id.navigationDrawerRL, shop).commit();
        }
        return false;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
