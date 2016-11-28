package ayan.sinacec;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.LinkedList;

import modelo.ComunicationWebServiceAndroid;
import modelo.basedatos.LocalRentado;
import modelo.basedatos.LocalRentadoId;
import modelo.basedatos.Puerta;
import modelo.basedatos.Usuario;
import modelo.list_view.ListViewAdapter2;
import modelo.list_view.SingleRow;
import modelo.list_view.SingleRow2;
import utilerias.Clock;
import utilerias.Image;
import utilerias.JSON;
import utilerias.List;
import utilerias.Mysql;
import utilerias.WebService;

public class RentedShop extends Fragment implements ComunicationWebServiceAndroid, AdapterView.OnItemLongClickListener, View.OnClickListener{

    private OnFragmentInteractionListener mListener;

    private View view;
    private TextView mallTV, shopTV, openingTV, closingTV;
    private EditText nameET, tenantET, twistET, webSideET, phoneET;
    private ImageView imageIV;
    private Button modifyB;
    private ListView doorsLV;

    private WebService webService;
    private int cwsc;//call web service code

    private static LocalRentado rentedShop;
    private Puerta[] doors;

    private LocalRentado mrs;

    private Date openingD, closingD;
    private Image imageSelector;
    private Clock timeSelector;

    public RentedShop() {
        imageSelector = new Image();
        timeSelector = new Clock();
        openingD = new Date();
        closingD = new Date();
    }

    public static LocalRentado getRentedShop() {
        return rentedShop;
    }

    public static void closeSession(){
         rentedShop = null;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void setInterfaceVariables(){
        nameET = (EditText) view.findViewById(R.id.nameET);
        mallTV = (TextView) view.findViewById(R.id.mallTV);
        imageIV = (ImageView) view.findViewById(R.id.imageIV);
        tenantET = (EditText) view.findViewById(R.id.tenantET);
        shopTV = (TextView) view.findViewById(R.id.shopTV);
        twistET = (EditText) view.findViewById(R.id.twistET);
        openingTV = (TextView) view.findViewById(R.id.openingTV);
        closingTV = (TextView) view.findViewById(R.id.closingTV);
        webSideET = (EditText) view.findViewById(R.id.webSideET);
        phoneET = (EditText) view.findViewById(R.id.phoneET);
        doorsLV = (ListView) view.findViewById(R.id.doorsLV);
        modifyB = (Button) view.findViewById(R.id.modifyB);
        webService = new WebService(view.getContext(), this);
    }

    public void setListeners(){
        doorsLV.setOnItemLongClickListener(this);
    }

    public void setDoors(String doors){
        if(doors.startsWith("[")){
            this.doors = (Puerta[]) JSON.aArrayObjeto(doors,"Puerta");
        }else{
            Puerta td = (Puerta) JSON.aObjeto(doors, "Puerta");
            this.doors = (td == null) ? new Puerta[0] : new Puerta[]{td};
        }
        doorsLV.setAdapter(new ListViewAdapter2(view.getContext(), Doors.toSingleRow2(this.doors)));
    }

    public void setUserInterface(){
        Usuario user = Profile.getUser();
        if(user != null && user.getTipo() == 'L'){
            nameET.setEnabled(true);
            tenantET.setEnabled(true);
            twistET.setEnabled(true);
            webSideET.setEnabled(true);
            phoneET.setEnabled(true);
            modifyB.setVisibility(View.VISIBLE);

            imageIV.setOnClickListener(this);
            openingTV.setOnClickListener(this);
            closingTV.setOnClickListener(this);
            modifyB.setOnClickListener(this);
        }
    }

    public void setRentedShopOnInterface(){
        if(RentedShop.rentedShop != null){
            nameET.setText(RentedShop.rentedShop.getNombre());
            mallTV.setText(RentedShop.rentedShop.getId().getCentroComercial());
            Image.setImageFromBase64(RentedShop.rentedShop.getImagen(), imageIV);
            tenantET.setText(RentedShop.rentedShop.getLocatario());
            shopTV.setText(RentedShop.rentedShop.getId().getLocal());
            twistET.setText(RentedShop.rentedShop.getGiro());
            openingTV.setText(Mysql.formatoTiempo.format(RentedShop.rentedShop.getHorarioApertura()));
            closingTV.setText(Mysql.formatoTiempo.format(RentedShop.rentedShop.getHorarioCierre()));
            webSideET.setText(RentedShop.rentedShop.getSitioWeb());
            phoneET.setText(RentedShop.rentedShop.getTelefono());
            setUserInterface();
        }
    }

    public LinkedList<String> missingFields(){
        LinkedList<String> mf = new LinkedList<String>();
        if(tenantET.getText().toString().trim().isEmpty()){
            mf.add(getString(R.string.tenant));
        }
        if(nameET.getText().toString().trim().isEmpty()){
            mf.add(getString(R.string.name));
        }
        if(twistET.getText().toString().trim().isEmpty()){
            mf.add(getString(R.string.twist));
        }
        if(openingTV.getText().toString().trim().isEmpty()){
            mf.add(getString(R.string.opening));
        }
        if(closingTV.getText().toString().trim().isEmpty()){
            mf.add(getString(R.string.closing));
        }
        return mf;
    }

    public LocalRentado readRentedShop(){
        LocalRentado rs = new LocalRentado();
        rs.setId(new LocalRentadoId(RentedShop.rentedShop.getId().getLocal(), RentedShop.rentedShop.getId().getCentroComercial(), RentedShop.rentedShop.getId().getDireccionCentroComercial()));
        rs.setImagen(Image.imageToBase64(imageIV));
        rs.setLocatario(tenantET.getText().toString().trim());
        rs.setNombre(nameET.getText().toString().trim());
        rs.setGiro(twistET.getText().toString().trim());
        rs.setHorarioApertura(openingD);
        rs.setHorarioCierre(closingD);
        rs.setSitioWeb((webSideET.getText().toString().trim().isEmpty()) ? Mysql.valorNoSeteadoString : webSideET.getText().toString().trim());
        rs.setTelefono((phoneET.getText().toString().trim().isEmpty()) ? Mysql.valorNoSeteadoString : phoneET.getText().toString().trim());
        return rs;
    }

    public void modify(){
        LinkedList<String> mf = missingFields();
        if(mf.size() == 0){
            mrs = readRentedShop();
            this.cwsc = 3;
            webService.callMethodWebService("actualizar", WebService.pcum, new Object[]{JSON.aJSON(mrs, "LocalRentado"), "LocalRentado"});
        }else{
            Toast.makeText(getContext(),"Ingrese los siguientes campos:\n"+ List.joinItems(mf,"\n"), Toast.LENGTH_LONG).show();
        }
    }

    public void statusModify(boolean status){
        if(status){
            RentedShop.rentedShop = mrs;
            Toast.makeText(view.getContext(), "Local modificado", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(view.getContext(), "No se pudo modificar el Local\nRevise su conexion a internet", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_rented_shop, container, false);
        setInterfaceVariables();
        setListeners();
        Bundle args = getArguments();
        if(args != null){
            RentedShop.rentedShop = (LocalRentado) args.getSerializable("rentedShop");
            if(RentedShop.rentedShop != null){
                setRentedShopOnInterface();
                this.cwsc = 1;
                webService.callMethodWebService("leer", WebService.pcrm, new Object[]{"{ \"id\" : { \"local\" : \"" + RentedShop.rentedShop.getId().getLocal() + "\", \"centroComercial\" : \""+ RentedShop.rentedShop.getId().getCentroComercial() +"\", \"direccionCentroComercial\" : \""+ this.rentedShop.getId().getDireccionCentroComercial() +"\"}}","Puerta",null});
            }
        }else{
            if(RentedShop.rentedShop != null){
                setRentedShopOnInterface();
            }else{
                Toast.makeText(view.getContext(), "Por favor seleccione un Local", Toast.LENGTH_LONG).show();
            }
        }
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageSelector.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        imageSelector.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
        switch (this.cwsc){
            case 1:
                setDoors(responseWebService.toString());
                break;
            case 2:
                statusModify(Boolean.parseBoolean(responseWebService.toString()));
                break;
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        //trazar ruta
        return false;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.imageIV:
                imageSelector.selectImage(this, imageIV, getString(R.string.loadMallImage));
                break;
            case R.id.openingTV:
                timeSelector.showTimePickerDialog(view.getContext(), getString(R.string.opening), openingD, openingTV);
                break;
            case R.id.closingTV:
                timeSelector.showTimePickerDialog(view.getContext(), getString(R.string.closing), closingD, closingTV);
                break;
            case R.id.modifyB:
                modify();
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
