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
import modelo.basedatos.Servicio;
import modelo.basedatos.Usuario;
import modelo.list_view.ListViewAdapter;
import modelo.list_view.SingleRow;
import utilerias.JSON;
import utilerias.WebService;

public class Services extends Fragment implements ComunicationWebServiceAndroid, AdapterView.OnItemLongClickListener{

    private OnFragmentInteractionListener mListener;

    private View view;
    private ListView servicesLV;

    private WebService webService;

    private static Servicio[] services;

    public Services() {

    }

    public void setInterfaceVariables(){
        servicesLV = (ListView) view.findViewById(R.id.servicesLV);
        webService = new WebService(view.getContext(), this);
    }

    public void setListeners(){
        servicesLV.setOnItemLongClickListener(this);
    }

    public static SingleRow[] toSingleRow(Servicio []services){
        SingleRow []sr = new SingleRow[0];
        int i;
        if(services != null){
            sr = new SingleRow[services.length];
            for(i=0; i<services.length; i++){
                sr[i] = new SingleRow(services[i].getImagen(), services[i].getId().getNombre());
            }
        }
        return sr;
    }

    public void setServices(String services){
        if(services.startsWith("[")){
            Services.services = (Servicio[]) JSON.aArrayObjeto(services, "Servicio");
        }else{
            Servicio ts = (Servicio) JSON.aObjeto(services, "Servicio");
            Services.services = (ts == null) ? new Servicio[0] : new Servicio[]{ts};
        }
        servicesLV.setAdapter(new ListViewAdapter(view.getContext(), toSingleRow(Services.services) ));
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_services, container, false);
        setInterfaceVariables();
        setListeners();
        CentroComercial mall = Mall.getMall();
        Usuario user = Profile.getUser();
        String  shop= "";
        if(mall != null) {
            if(user != null){
                if(user.getTipo() == 'L'){
                    LocalRentado rentedShop = RentedShop.getRentedShop();
                    if(rentedShop != null){
                        shop = "\"local\" : \"" + rentedShop.getId().getLocal() + "\", ";
                    }else{
                        Toast.makeText(view.getContext(), "Por favor seleccione un Local", Toast.LENGTH_LONG).show();
                        return view;
                    }
                }
            }
            webService.callMethodWebService("leer", WebService.pcrm, new Object[]{"{ \"id\" : {" + shop + "\"centroComercial\" : \"" + mall.getId().getNombre() + "\", \"direccionCentroComercial\" : " + mall.getId().getDireccion() + " }}", "Servicio", null});
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
        setServices(responseWebService.toString());
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        Bundle args = new Bundle();
        args.putSerializable("service", Services.services[position]);
        Fragment f = new Service();
        f.setArguments(args);
        getFragmentManager().beginTransaction().replace(R.id.navigationDrawerRL, f).commit();
        return false;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
