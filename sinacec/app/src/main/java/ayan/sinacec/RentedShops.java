package ayan.sinacec;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import modelo.ComunicationWebServiceAndroid;
import modelo.basedatos.CentroComercial;
import modelo.basedatos.LocalRentado;
import modelo.basedatos.Usuario;
import modelo.list_view.ListViewAdapter;
import modelo.list_view.SingleRow;
import utilerias.JSON;
import utilerias.WebService;

public class RentedShops extends Fragment implements ComunicationWebServiceAndroid,AdapterView.OnItemLongClickListener{

    private OnFragmentInteractionListener mListener;

    private View view;
    private ListView rentedShopsLV;

    private WebService webService;

    private static LocalRentado[] rentedShops;

    public RentedShops() {

    }

    public void setInterfaceVariables(){
        rentedShopsLV = (ListView) view.findViewById(R.id.rentedShopsLV);
        webService = new WebService(view.getContext(), this);
    }

    public void setListeners(){
        rentedShopsLV.setOnItemLongClickListener(this);
    }

    public static SingleRow[] toSingleRow(LocalRentado []rentedShops){
        SingleRow []sr = new SingleRow[0];
        int i;
        if(rentedShops != null){
            sr = new SingleRow[rentedShops.length];
            for(i = 0; i < rentedShops.length; i++){
                sr[i] = new SingleRow(rentedShops[i].getImagen(), rentedShops[i].getNombre());
            }
        }
        return sr;
    }

    public void setRentedShops(String rentedShops){
        if(rentedShops.startsWith("[")){
            RentedShops.rentedShops = (LocalRentado[]) JSON.aArrayObjeto(rentedShops, "LocalRentado");
        }else{
            LocalRentado trs = (LocalRentado) JSON.aObjeto(rentedShops, "LocalRentado");
            RentedShops.rentedShops = (trs == null) ? new LocalRentado[0] : new LocalRentado[]{trs};
        }
        this.rentedShopsLV.setAdapter(new ListViewAdapter(view.getContext(), toSingleRow(RentedShops.rentedShops)));
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_rented_shops, container, false);
        setInterfaceVariables();
        setListeners();
        CentroComercial mall = Mall.getMall();
        Usuario user = Profile.getUser();
        String  tenant = "";
        if(mall != null) {
            if(user != null){
                if(user.getTipo() == 'L'){
                    tenant = ", \"locatario\" : \"" + user.getCuenta() + "\"";
                }
            }
            webService.callMethodWebService("leer", WebService.pcrm, new Object[]{"{ \"id\" : {\"centroComercial\" : \""+ mall.getId().getNombre() +"\", \"direccionCentroComercial\" : "+ mall.getId().getDireccion() +" }" + tenant +"}","LocalRentado",null});
        }else{
            Toast.makeText(view.getContext(), "Por favor seleccione primero un Centro Comercial", Toast.LENGTH_LONG).show();
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
        setRentedShops(responseWebService.toString());
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        Bundle args = new Bundle();
        args.putSerializable("rentedShop",RentedShops.rentedShops[position]);
        Fragment f = new RentedShop();
        f.setArguments(args);
        getFragmentManager().beginTransaction().replace(R.id.navigationDrawerRL, f).commit();
        return false;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
