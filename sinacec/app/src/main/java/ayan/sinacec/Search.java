package ayan.sinacec;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import modelo.ComunicationWebServiceAndroid;
import modelo.basedatos.CentroComercial;
import modelo.basedatos.LocalRentado;
import modelo.basedatos.Producto;
import modelo.basedatos.Servicio;
import modelo.basedatos.Usuario;
import modelo.list_view.ListViewAdapter;
import modelo.list_view.SingleRow;
import utilerias.JSON;
import utilerias.WebService;

public class Search extends Fragment implements ComunicationWebServiceAndroid, View.OnClickListener, AdapterView.OnItemLongClickListener{

    private OnFragmentInteractionListener mListener;

    private View view;
    private EditText keyWordET;
    private Button searchB;
    private RadioButton rentedShopRB, serviceRB, productRB;
    private ListView replayLV;

    private WebService webService;
    private int cwsc;//call web service code

    private LocalRentado []rentedShops;
    private Servicio []services;
    private Producto []products;

    private CentroComercial mall;

    public Search() {

    }

    public void setInterfaceVariables(){
        keyWordET = (EditText) view.findViewById(R.id.keyWordET);
        searchB = (Button) view.findViewById(R.id.searchB);
        rentedShopRB = (RadioButton) view.findViewById(R.id.rentedShopRB);
        serviceRB = (RadioButton) view.findViewById(R.id.serviceRB);
        productRB = (RadioButton) view.findViewById(R.id.productRB);
        replayLV = (ListView) view.findViewById(R.id.replayLV);
        webService = new WebService(view.getContext(),this);
    }

    public void setListeners(){
        searchB.setOnClickListener(this);
        replayLV.setOnItemLongClickListener(this);
    }

    public void search(){
        if(keyWordET.getText().toString().trim().isEmpty()){
            Toast.makeText(getContext(),"Ingrese una palabra clave para realizar la busqueda",Toast.LENGTH_LONG).show();
            return ;
        }
        Usuario user = Profile.getUser();
        String tenant= "NULL", shop= "NULL";
        if(user != null){
            if(user.getTipo() == 'L'){
                tenant = user.getCuenta();
                LocalRentado rentedShop = RentedShop.getRentedShop();
                if(rentedShop != null){
                    shop = rentedShop.getId().getLocal();
                }else{
                    Toast.makeText(view.getContext(), "Por favor seleccione un Local", Toast.LENGTH_LONG).show();
                    return ;
                }
            }
        }
        if(rentedShopRB.isChecked()){
            cwsc = 1;
            webService.callMethodWebService("llamarProcedimientoSelect", WebService.pclpsm, new Object[]{"search_rented_shops", keyWordET.getText().toString() + "," + tenant + "," + mall.getId().getNombre() + "," + mall.getId().getDireccion(), "LocalRentado"});
        }else if(serviceRB.isChecked()){
            cwsc = 2;
            webService.callMethodWebService("llamarProcedimientoSelect", WebService.pclpsm, new Object[]{"search_services", keyWordET.getText().toString() +  "," + shop + ","  + mall.getId().getNombre() + "," + mall.getId().getDireccion(), "Servicio"});
        }else if(productRB.isChecked()){
            cwsc = 3;
            webService.callMethodWebService("llamarProcedimientoSelect", WebService.pclpsm, new Object[]{"search_products", keyWordET.getText().toString() +  "," + shop + ","  + mall.getId().getNombre() + "," + mall.getId().getDireccion(), "Producto"});
        }else{
            Toast.makeText(getContext(),"Seleccione un metodo de busqueda (por Local, Producto o Servico)",Toast.LENGTH_LONG).show();
        }
    }

    public void setRentedShops(String rentedShops){
        if(rentedShops.startsWith("[")){
            this.rentedShops = (LocalRentado[]) JSON.aArrayObjeto(rentedShops, "LocalRentado");
        }else{
            LocalRentado trs = (LocalRentado) JSON.aObjeto(rentedShops, "LocalRentado");
            this.rentedShops = (trs == null) ? new LocalRentado[0] : new LocalRentado[]{trs};
        }
        replayLV.setAdapter(new ListViewAdapter(view.getContext(), RentedShops.toSingleRow(this.rentedShops)));
    }

    public void setServices(String services){
        if(services.startsWith("[")){
            this.services = (Servicio[]) JSON.aArrayObjeto(services, "Servicio");
        }else{
            Servicio ts = (Servicio) JSON.aObjeto(services, "Servicio");
            this.services = (ts == null) ? new Servicio[0] : new Servicio[]{ts};
        }
        replayLV.setAdapter(new ListViewAdapter(view.getContext(), Services.toSingleRow(this.services)));
    }

    public void setProducts(String products){
        if(products.startsWith("[")){
            this.products = (Producto[]) JSON.aArrayObjeto(products, "Producto");
        }else{
            Producto tp = (Producto) JSON.aObjeto(products, "Producto");
            this.products = (tp == null) ? new Producto[0] : new Producto[]{tp};
        }
        replayLV.setAdapter(new ListViewAdapter(view.getContext(), ProductsManagement.toSingleRow(this.products)));
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        setInterfaceVariables();
        mall = Mall.getMall();
        if(mall != null){
            setListeners();
        }else{
            Toast.makeText(view.getContext(), "Por favor seleccione un Centro Comercial", Toast.LENGTH_LONG).show();
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
        switch (cwsc){
            case 1:
                setRentedShops(responseWebService.toString());
                break;
            case 2:
                setServices(responseWebService.toString());
                break;
            case 3:
                setProducts(responseWebService.toString());
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.searchB:
                search();
                break;
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        Bundle args = new Bundle();
        Fragment f = null;
        if(rentedShopRB.isChecked()){
            args.putSerializable("rentedShop", this.rentedShops[position]);
            f = new RentedShop();
        }else if(serviceRB.isChecked()){
            args.putSerializable("service", this.services[position]);
            f = new Service();
        }else if(productRB.isChecked()){
            args.putSerializable("product", this.products[position]);
            f = new Product();
        }
        f.setArguments(args);
        //getFragmentManager().beginTransaction().addToBackStack("RentedPlaceToRentedPlaces");
        getFragmentManager().beginTransaction().replace(R.id.navigationDrawerRL, f).commit();
        return false;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
