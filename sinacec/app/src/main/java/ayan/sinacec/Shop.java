package ayan.sinacec;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import modelo.ComunicationWebServiceAndroid;
import modelo.basedatos.Local;
import modelo.basedatos.Puerta;
import modelo.list_view.ListViewAdapter2;
import modelo.list_view.SingleRow2;
import modelo.spinner.SpinnerAdapter;
import utilerias.JSON;
import utilerias.WebService;

public class Shop extends Fragment implements ComunicationWebServiceAndroid, View.OnClickListener{

    private OnFragmentInteractionListener mListener;

    private View view;
    private TextView mallTV;
    private EditText shopET;
    private Spinner statusS, areaS;
    private ListView doorsLV;
    private Button doorsManagementB;

    private WebService webService;

    private Local shop;
    private Puerta []doors;

    public Shop() {

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void setInterfaceVariables(){
        mallTV = (TextView) view.findViewById(R.id.mallTV);
        shopET = (EditText) view.findViewById(R.id.shopET);
        statusS = (Spinner) view.findViewById(R.id.statusS);
        areaS = (Spinner) view.findViewById(R.id.areaS);
        doorsLV = (ListView) view.findViewById(R.id.doorsLV);
        doorsManagementB = (Button) view.findViewById(R.id.doorsManagementB);
        webService = new WebService(view.getContext(), this);
    }

    public void setListeners(){
        doorsManagementB.setOnClickListener(this);
    }

    public void setShopOnInterface(){
        if(this.shop != null){
            mallTV.setText(this.shop.getId().getCentroComercial());
            shopET.setText(this.shop.getId().getLocal());
            statusS.setAdapter(new SpinnerAdapter(view.getContext(), R.layout.fragment_shop, new String[]{this.shop.getStatusMeaning()}));
            areaS.setAdapter(new SpinnerAdapter(view.getContext(), R.layout.fragment_shop, new String[]{this.shop.getArea()}));
        }
    }

    public void setDoors(String doors){
        if(doors.equals("[")){
            this.doors = (Puerta[]) JSON.aArrayObjeto(doors, "Puerta");
        }else{
            Puerta td = (Puerta) JSON.aObjeto(doors, "Puerta");
            this.doors = (td == null) ? new Puerta[0] : new Puerta[]{td};
        }
        doorsLV.setAdapter(new ListViewAdapter2(view.getContext(), Doors.toSingleRow2(this.doors)));
    }

    public void doorsManagement(){
        if(this.shop != null){
            Bundle args = new Bundle();
            args.putSerializable("shop", this.shop);
            Fragment doors = new Doors();
            doors.setArguments(args);
            getFragmentManager().beginTransaction().replace(R.id.navigationDrawerRL, doors).commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shop, container, false);
        Bundle args = getArguments();
        if(args != null) {
            this.shop = (Local) args.getSerializable("shop");
            if(this.shop != null){
                setInterfaceVariables();
                setListeners();
                setShopOnInterface();
                webService.callMethodWebService("leer", WebService.pcrm, new Object[]{"{\"id\" : {\"local\" : \"" + this.shop.getId().getLocal() + "\", \"centroComercial\" : \"" + this.shop.getId().getCentroComercial() + "\", \"direccionCentroComercial\" : " + this.shop.getId().getDireccionCentroComercial() + "} }", "Puerta", null});
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
        setDoors(responseWebService.toString());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.doorsManagementB:
                doorsManagement();
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
