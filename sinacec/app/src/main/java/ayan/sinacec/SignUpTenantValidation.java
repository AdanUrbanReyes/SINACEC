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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.LinkedList;

import modelo.ComunicationWebServiceAndroid;
import modelo.basedatos.CentroComercial;
import modelo.basedatos.RegistroLocatario;
import modelo.spinner.SpinnerAdapter;
import utilerias.Image;
import utilerias.JSON;
import utilerias.Mysql;
import utilerias.WebService;

public class SignUpTenantValidation extends Fragment implements ComunicationWebServiceAndroid, View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    private View view;
    private ImageView profileImageIV, shopImageIV;
    private EditText accountET, keyET, namesET, firstNameET, secondNameET, addressMallET, shopNameET, twistET, webSideET, phoneET;
    private Spinner mallS, shopS;
    private TextView openingTV, closingTV;
    private Button acceptB, rejectB, beforeB, nextB;

    private WebService webService;
    private int cwsc;//call web service code

    private CentroComercial mall;

    private LinkedList<RegistroLocatario> signUpsTenant;
    private int isut;

    public SignUpTenantValidation() {
        signUpsTenant = new LinkedList<RegistroLocatario>();
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void setInterfaceVariables(){
        profileImageIV = (ImageView) view.findViewById(R.id.profileImageIV);
        accountET = (EditText) view.findViewById(R.id.accountET);
        keyET = (EditText) view.findViewById(R.id.keyET);
        namesET = (EditText) view.findViewById(R.id.namesET);
        firstNameET = (EditText) view.findViewById(R.id.firstNameET);
        secondNameET = (EditText) view.findViewById(R.id.secondNameET);
        shopImageIV = (ImageView) view.findViewById(R.id.shopImageIV);
        mallS = (Spinner) view.findViewById(R.id.mallS);
        shopS = (Spinner) view.findViewById(R.id.shopS);
        addressMallET = (EditText) view.findViewById(R.id.addressMallET);
        shopNameET = (EditText) view.findViewById(R.id.shopNameET);
        twistET = (EditText) view.findViewById(R.id.twistET);
        openingTV = (TextView) view.findViewById(R.id.openingTV);
        closingTV = (TextView) view.findViewById(R.id.closingTV);
        webSideET = (EditText) view.findViewById(R.id.webSideET);
        phoneET = (EditText) view.findViewById(R.id.phoneET);
        acceptB = (Button) view.findViewById(R.id.acceptB);
        rejectB = (Button) view.findViewById(R.id.rejectB);
        beforeB = (Button) view.findViewById(R.id.beforeB);
        nextB = (Button) view.findViewById(R.id.nextB);
        webService = new WebService(view.getContext(), this);
    }

    public void setListeners(View.OnClickListener ocl){
        acceptB.setOnClickListener(ocl);
        rejectB.setOnClickListener(ocl);
        beforeB.setOnClickListener(ocl);
        nextB.setOnClickListener(ocl);
    }

    public void setOnInterface(RegistroLocatario sut){
        if(sut != null){
            Image.setImageFromBase64(sut.getImagen(), profileImageIV);
            accountET.setText(sut.getCuenta());
            keyET.setText(sut.getClave());
            namesET.setText(sut.getNombres());
            firstNameET.setText(sut.getPrimerApellido());
            secondNameET.setText(sut.getSegundoApellido());

            Image.setImageFromBase64(sut.getImagenLocal(), shopImageIV);
            mallS.setAdapter(new SpinnerAdapter(view.getContext(), R.layout.fragment_sign_up_tenant_validation, new Object[]{sut.getCentroComercial()}));
            shopS.setAdapter(new SpinnerAdapter(view.getContext(), R.layout.fragment_sign_up_tenant_validation, new Object[]{sut.getLocal()}));
            addressMallET.setText(""+sut.getDireccionCentroComercial());
            shopNameET.setText(sut.getNombreLocal());
            twistET.setText(sut.getGiro());
            openingTV.setText(Mysql.formatoTiempo.format(sut.getHorarioApertura()));
            closingTV.setText(Mysql.formatoTiempo.format(sut.getHorarioCierre()));
            webSideET.setText(sut.getSitioWeb());
            phoneET.setText(sut.getTelefono());
        }
    }

    public void setSignUpsTenant(String signUpsTenant){
        if(signUpsTenant.startsWith("[")){
            this.signUpsTenant = new LinkedList<RegistroLocatario>(Arrays.asList(  (RegistroLocatario[]) JSON.aObjectArray(signUpsTenant, RegistroLocatario[].class, JSON.formatoTiempo) ) );
        }else{
            RegistroLocatario tsut = (RegistroLocatario) JSON.aObject(signUpsTenant, RegistroLocatario.class, JSON.formatoTiempo);
            if(tsut != null){
                this.signUpsTenant.add(tsut);
            }
        }
        if(this.signUpsTenant.isEmpty()){
            Toast.makeText(view.getContext(), "No hay registros por validar para el Centro Comercial seleccionado", Toast.LENGTH_LONG).show();
        }else{
            setListeners(this);
            setOnInterface(this.signUpsTenant.get(0));
        }
    }

    public void setBeforeSignUpTenant(){
        this.isut = (this.isut == 0) ?  this.signUpsTenant.size()-1 : this.isut - 1;
        setOnInterface(this.signUpsTenant.get(this.isut));
    }

    public void setNextSignUpTenant(){
        this.isut = (this.isut == this.signUpsTenant.size()-1) ? 0 : this.isut + 1;
        setOnInterface(this.signUpsTenant.get(this.isut));
    }

    public void statusAccept(int records){
        if(records != -1){
            this.signUpsTenant.remove(this.isut);
            if(this.signUpsTenant.isEmpty()){
                setListeners(null);
            }else{
                setNextSignUpTenant();
            }
            Toast.makeText(view.getContext(), "Registro aceptado", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(view.getContext(), "No se pudo aceptar el Registro\nRevise su conexion a internet", Toast.LENGTH_LONG).show();
        }
    }

    public void statusReject(int records){
        if(records != -1){
            this.signUpsTenant.remove(this.isut);
            if(this.signUpsTenant.isEmpty()){
                setListeners(null);
            }else{
                setNextSignUpTenant();
            }
            Toast.makeText(view.getContext(), "Registro rechazado", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(view.getContext(), "No se pudo rechazar el Registro\nRevise su conexion a internet", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign_up_tenant_validation, container, false);
        setInterfaceVariables();
        this.mall = Mall.getMall();
        if(this.mall != null){
            this.cwsc = 1;
            webService.callMethodWebService("leer", WebService.pcrm, new Object[]{"{\"centroComercial\" : \"" + this.mall.getId().getNombre() + "\", \"direccionCentroComercial\" : " + this.mall.getId().getDireccion() + "}","RegistroLocatario",null});
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
        switch (this.cwsc){
            case 1:
                setSignUpsTenant(responseWebService.toString());
                break;
            case 2:
                statusAccept(Integer.parseInt(responseWebService.toString()));
                break;
            case 3:
                statusReject(Integer.parseInt(responseWebService.toString()));
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.acceptB:
                this.cwsc = 2;
                webService.callMethodWebService("llamarProcedimientoUpdate", WebService.pclpum, new Object[]{"sign_up_validator",accountET.getText().toString().trim()+",A"});
                break;
            case R.id.rejectB:
                this.cwsc = 3;
                webService.callMethodWebService("llamarProcedimientoUpdate", WebService.pclpum, new Object[]{"sign_up_validator",accountET.getText().toString().trim()+",R"});
                break;
            case R.id.beforeB:
                setBeforeSignUpTenant();
                break;
            case R.id.nextB:
                setNextSignUpTenant();
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
