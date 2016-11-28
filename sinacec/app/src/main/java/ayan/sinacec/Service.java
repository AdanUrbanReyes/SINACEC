package ayan.sinacec;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import modelo.ComunicationWebServiceAndroid;
import modelo.basedatos.LocalRentado;
import modelo.basedatos.Servicio;
import utilerias.Image;
import utilerias.JSON;
import utilerias.WebService;

public class Service extends Fragment implements ComunicationWebServiceAndroid, View.OnClickListener{

    private OnFragmentInteractionListener mListener;

    private View view;
    private TextView nameTV, shopTV, mallTV, descriptionTV;
    private ImageView imageIV;

    private WebService webService;
    
    private Servicio service;

    public Service() {

    }

    public void setInterfaceVariables(){
        nameTV = (TextView) view.findViewById(R.id.nameTV);
        shopTV = (TextView) view.findViewById(R.id.shopTV);
        mallTV = (TextView) view.findViewById(R.id.mallTV);
        imageIV = (ImageView) view.findViewById(R.id.imageIV);
        descriptionTV = (TextView) view.findViewById(R.id.descriptionTV);
        webService = new WebService(view.getContext(), this);
    }

    public void setListeners(){
        shopTV.setOnClickListener(this);
    }
    
    public void setServiceOnInterface(){
        if(this.service != null){
            nameTV.setText(this.service.getId().getNombre());
            shopTV.setText(this.service.getId().getLocal());
            mallTV.setText(this.service.getId().getCentroComercial());
            Image.setImageFromBase64(this.service.getImagen(), imageIV);
            descriptionTV.setText(this.service.getDescripcion());
        }
    }
    
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_service, container, false);
        Bundle args = getArguments();
        if(args != null) {
            this.service = (Servicio) args.getSerializable("service");
            if(this.service != null){
                setInterfaceVariables();
                setListeners();
                setServiceOnInterface();
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
        LocalRentado rs = (LocalRentado) JSON.aObjeto(responseWebService.toString(), "LocalRentado");
        if(rs != null) {
            Bundle args = new Bundle();
            args.putSerializable("rentedShop",rs);
            Fragment f = new RentedShop();
            f.setArguments(args);
            //getFragmentManager().beginTransaction().addToBackStack("RentedPlaceToRentedPlaces");
            getFragmentManager().beginTransaction().replace(R.id.navigationDrawerRL, f).commit();
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.shopTV:
                webService.callMethodWebService("leer", WebService.pcrm, new Object[]{"{ \"id\" : {\"local\" : \""+service.getId().getLocal()+"\", \"centroComercial\" : \""+service.getId().getCentroComercial()+"\", \"direccionCentroComercial\" : "+service.getId().getDireccionCentroComercial()+"}}", "LocalRentado", null});
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
