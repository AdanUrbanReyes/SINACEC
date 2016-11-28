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
import modelo.basedatos.Producto;
import utilerias.Image;
import utilerias.JSON;
import utilerias.WebService;

public class Product extends Fragment implements ComunicationWebServiceAndroid, View.OnClickListener{

    private OnFragmentInteractionListener mListener;

    private View view;
    private TextView nameTV, shopTV, mallTV, descriptionTV;
    private ImageView imageIV;

    private WebService webService;

    private Producto product;

    public Product() {

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

    public void setProductOnInterface(){
        if(this.product != null){
            nameTV.setText(this.product.getId().getNombre());
            shopTV.setText(this.product.getId().getLocal());
            mallTV.setText(this.product.getId().getCentroComercial());
            Image.setImageFromBase64(this.product.getImagen(), imageIV);
            descriptionTV.setText(this.product.getDescripcion());
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_product, container, false);
        Bundle args = getArguments();
        if(args != null) {
            this.product = (Producto) args.getSerializable("product");
            if(this.product != null) {
                setInterfaceVariables();
                setListeners();
                setProductOnInterface();
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
                webService.callMethodWebService("leer", WebService.pcrm, new Object[]{"{ \"id\" : {\"local\" : \""+this.product.getId().getLocal()+"\", \"centroComercial\" : \""+this.product.getId().getCentroComercial()+"\", \"direccionCentroComercial\" : "+ this.product.getId().getDireccionCentroComercial()+"}}", "LocalRentado", null});
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
