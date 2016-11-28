package ayan.sinacec;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.LinkedList;

import modelo.ComunicationWebServiceAndroid;
import modelo.basedatos.CentroComercial;
import modelo.basedatos.CodigoPostal;
import modelo.basedatos.Direccion;
import modelo.basedatos.Usuario;
import utilerias.Clock;
import utilerias.Image;
import utilerias.JSON;
import utilerias.List;
import utilerias.Mysql;
import utilerias.WebService;

public class Mall extends Fragment implements ComunicationWebServiceAndroid, View.OnClickListener{

    private OnFragmentInteractionListener mListener;

    private View view;
    private TextView nameTV, addressTV, managerET, openingTV, closingTV, webSideET, phoneET;
    private ImageView imageIV;
    private Button modifyB;

    private static CentroComercial mall;
    private static Direccion address;
    private static CodigoPostal postalCode;

    private WebService webService;
    private int cwsc;//call web service code

    private CentroComercial mm; //modified mall
    private Date openingD, closingD;
    private Image imageSelector;
    private Clock timeSelector;

    public Mall() {
        imageSelector = new Image();
        timeSelector = new Clock();
        openingD = new Date();
        closingD = new Date();
    }

    public static CentroComercial getMall() {
        return mall;
    }

    public static void closeSession(){
        mall = null;
        address = null;
        postalCode = null;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void setInterfaceVariables(){
        nameTV = (TextView) view.findViewById(R.id.nameTV);
        addressTV = (TextView) view.findViewById(R.id.addressTV);
        imageIV = (ImageView) view.findViewById(R.id.imageIV);
        managerET = (EditText) view.findViewById(R.id.managerET);
        openingTV = (TextView) view.findViewById(R.id.openingTV);
        closingTV = (TextView) view.findViewById(R.id.closingTV);
        webSideET = (EditText) view.findViewById(R.id.webSideET);
        phoneET = (EditText) view.findViewById(R.id.phoneET);
        modifyB = (Button) view.findViewById(R.id.modifyB);
        webService = new WebService(view.getContext(),this);
    }

    public void setListeners(){
        imageIV.setOnClickListener(this);
        openingTV.setOnClickListener(this);
        closingTV.setOnClickListener(this);
        modifyB.setOnClickListener(this);
    }

    public void setUserInterface(){
        Usuario user = Profile.getUser();
        if(user != null && user.getTipo() == 'C'){
            managerET.setEnabled(true);
            webSideET.setEnabled(true);
            phoneET.setEnabled(true);
            modifyB.setVisibility(View.VISIBLE);
            setListeners();
        }
    }

    public void setMallOnInterface(){
        if(Mall.mall != null){
            nameTV.setText(Mall.mall.getId().getNombre());
            Image.setImageFromBase64(Mall.mall.getImagen(), imageIV);
            if(Mall.address != null && Mall.postalCode != null){
                addressTV.setText(Mall.postalCode.getEstado()+" "+Mall.postalCode.getMunicipio()+" "+Mall.postalCode.getAsentamiento()+" "+Mall.postalCode.getCodigo()+" "+Mall.address.getCalle()+" "+Mall.address.getNumeroExterior());
            }
            managerET.setText(Mall.mall.getAdministrador());
            openingTV.setText(Mysql.formatoTiempo.format(Mall.mall.getHorarioApertura()));
            closingTV.setText(Mysql.formatoTiempo.format(Mall.mall.getHorarioCierre()));
            webSideET.setText(Mall.mall.getSitioWeb());
            phoneET.setText(Mall.mall.getTelefono());
            setUserInterface();
        }
    }

    public void setAddress(String address){
        this.address = (Direccion) JSON.aObjeto(address,"Direccion");
        if(this.address != null){
            cwsc = 2;
            webService.callMethodWebService("leer",WebService.pcrm,new Object[]{"{\"id\" : "+this.address.getCodigoPostal()+"}","CodigoPostal",null});
        }
    }

    public void setPostalCode(String postalCode){
        this.postalCode = (CodigoPostal) JSON.aObjeto(postalCode, "CodigoPostal");
        setMallOnInterface();
    }

    public LinkedList<String> missingFields(){
        LinkedList<String> mf = new LinkedList<String>();
        if(managerET.getText().toString().trim().isEmpty()){
            mf.add(getString(R.string.manager));
        }
        if(openingTV.getText().toString().trim().isEmpty()){
            mf.add(getString(R.string.opening));
        }
        if(closingTV.getText().toString().trim().isEmpty()){
            mf.add(getString(R.string.closing));
        }
        return mf;
    }

    public CentroComercial readMall(){
        CentroComercial m = Mall.mall;
        m.setImagen(Image.imageToBase64(imageIV));
        m.setAdministrador(managerET.getText().toString().trim());
        m.setHorarioApertura(openingD);
        m.setHorarioCierre(closingD);
        m.setSitioWeb((webSideET.getText().toString().trim().isEmpty()) ? null : webSideET.getText().toString().trim());
        m.setTelefono((phoneET.getText().toString().trim().isEmpty()) ? null : phoneET.getText().toString().trim());
        return m;
    }

    public void modify(){
        LinkedList<String> mf = missingFields();
        if(mf.size() == 0){
            mm = readMall();
            this.cwsc = 3;
            webService.callMethodWebService("actualizar", WebService.pcum, new Object[]{JSON.aJSON(mm, "CentroComercial"), "CentroComercial"});
        }else{
            Toast.makeText(getContext(),"Ingrese los siguientes campos:\n"+ List.joinItems(mf,"\n"), Toast.LENGTH_LONG).show();
        }
    }

    public void statusModify(boolean status){
        if(status){
            Mall.mall = mm;
            Toast.makeText(view.getContext(), "Centro Comercial modificado", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(view.getContext(), "No se pudo modificar el Centro Comerical\nRevise su conexion a internet", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mall, container, false);
        setInterfaceVariables();
        Bundle args = getArguments();
        if(args != null){
            Mall.mall = (CentroComercial) args.getSerializable("mall");
            if(Mall.mall != null) {
                cwsc = 1;
                webService.callMethodWebService("leer",WebService.pcrm,new Object[]{"{\"id\" : "+Mall.mall.getId().getDireccion()+"}","Direccion",null});
            }
        }else{
            if(Mall.mall != null){
                setMallOnInterface();
            }else{
                Toast.makeText(view.getContext(), "Por favor seleccione un Centro Comercial", Toast.LENGTH_LONG).show();
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
        switch (cwsc){
            case 1:
                setAddress(responseWebService.toString());
                break;
            case 2:
                setPostalCode(responseWebService.toString());
                break;
            case 3 :
                statusModify(Boolean.parseBoolean(responseWebService.toString()));
                break;
        }
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
