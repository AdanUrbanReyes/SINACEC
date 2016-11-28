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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.LinkedList;

import modelo.ComunicationWebServiceAndroid;
import modelo.basedatos.CentroComercial;
import modelo.basedatos.Local;
import modelo.basedatos.RegistroLocatario;
import modelo.spinner.SpinnerAdapter;
import utilerias.Clock;
import utilerias.Image;
import utilerias.JSON;
import utilerias.List;
import utilerias.WebService;

public class SignUpTenant extends Fragment implements ComunicationWebServiceAndroid, View.OnClickListener, AdapterView.OnItemSelectedListener{

    private OnFragmentInteractionListener mListener;

    private View view;
    private ImageView profileImageIV, shopImageIV;
    private EditText accountET, keyET, namesET, firstNameET, secondNameET, addressMallET, shopNameET, twistET, webSideET, phoneET;
    private Spinner mallS, shopS;
    private TextView openingTV, closingTV;
    private Button sendB;

    private WebService webService;
    private int cwsc;//call web service code

    private Date openingD, closingD;
    private Image imageSelector;
    private Clock timeSelector;

    private static CentroComercial[] malls;
    private static Local[] shops;

    public SignUpTenant() {
        imageSelector = new Image();
        timeSelector = new Clock();
        openingD = new Date();
        closingD = new Date();
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
        sendB = (Button) view.findViewById(R.id.sendB);
        webService = new WebService(view.getContext(), this);
    }

    public void setListeners(){
        profileImageIV.setOnClickListener(this);
        shopImageIV.setOnClickListener(this);
        mallS.setOnItemSelectedListener(this);
        openingTV.setOnClickListener(this);
        closingTV.setOnClickListener(this);
        sendB.setOnClickListener(this);
    }

    public void setMalls(String malls){
        if(malls.startsWith("[")){
            SignUpTenant.malls = (CentroComercial[]) JSON.aArrayObjeto(malls, "CentroComercial");
        }else{
            CentroComercial ct = (CentroComercial) JSON.aObjeto(malls, "CentroComercial");
            SignUpTenant.malls = (ct == null) ? new CentroComercial[0] : new CentroComercial[]{ct};
        }
        mallS.setAdapter(new SpinnerAdapter(view.getContext(),R.layout.fragment_sign_up_tenant, SignUpTenant.malls));
    }

    public void setShops(String shops){
        if(shops.startsWith("[")){
            SignUpTenant.shops = (Local[]) JSON.aArrayObjeto(shops, "Local");
        }else{
            Local st = (Local) JSON.aObjeto(shops, "Local");
            SignUpTenant.shops = (st == null) ? new Local[0] : new Local[]{st};
        }
        shopS.setAdapter(new SpinnerAdapter(view.getContext(), R.layout.fragment_sign_up_tenant, SignUpTenant.shops));
    }

    public LinkedList<String> missingFields(){
        LinkedList<String> mf = new LinkedList<String>();
        if(accountET.getText().toString().trim().isEmpty()){
            mf.add(getString(R.string.account));
        }
        if(keyET.getText().toString().trim().isEmpty()){
            mf.add(getString(R.string.key));
        }
        if(namesET.getText().toString().trim().isEmpty()){
            mf.add(getString(R.string.names));
        }
        if(firstNameET.getText().toString().trim().isEmpty()){
            mf.add(getString(R.string.firstName));
        }
        if(secondNameET.getText().toString().trim().isEmpty()){
            mf.add(getString(R.string.secondName));
        }
        if(mallS.getSelectedItem() == null){
            mf.add(getString(R.string.mall));
        }
        if(shopS.getSelectedItem() == null){
            mf.add(getString(R.string.shop));
        }
        if(shopNameET.getText().toString().trim().isEmpty()){
            mf.add(getString(R.string.shopName));
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

    public RegistroLocatario readTenantRecord(){
        RegistroLocatario tr = new RegistroLocatario();
        tr.setImagen(Image.imageToBase64(profileImageIV));
        tr.setCuenta(accountET.getText().toString().trim());
        tr.setClave(keyET.getText().toString());
        tr.setNombres(namesET.getText().toString().trim());
        tr.setPrimerApellido(firstNameET.getText().toString().trim());
        tr.setSegundoApellido(secondNameET.getText().toString().trim());
        tr.setImagenLocal(Image.imageToBase64(shopImageIV));
        tr.setCentroComercial(mallS.getSelectedItem().toString().trim());
        tr.setLocal(shopS.getSelectedItem().toString().trim());
        tr.setDireccionCentroComercial(Integer.parseInt(addressMallET.getText().toString().trim()));
        tr.setNombreLocal(shopNameET.getText().toString().trim());
        tr.setGiro(twistET.getText().toString().trim());
        tr.setHorarioApertura(openingD);
        tr.setHorarioCierre(closingD);
        tr.setSitioWeb((webSideET.getText().toString().trim().isEmpty()) ? null : webSideET.getText().toString().trim());
        tr.setTelefono((phoneET.getText().toString().trim().isEmpty()) ? null : phoneET.getText().toString().trim());
        return tr;
    }

    public void send(){
        LinkedList<String> mf = missingFields();
        if(mf.size() == 0){
            this.cwsc = 3;
            webService.callMethodWebService("crear", WebService.pccm, new Object[]{JSON.aJSON(readTenantRecord(), "RegistroLocatario"),"RegistroLocatario"});
        }else{
            Toast.makeText(getContext(),"Ingrese los siguientes campos:\n"+ List.joinItems(mf,"\n"), Toast.LENGTH_LONG).show();
        }
    }

    public void statusSend(boolean status){
        if(status){
            Toast.makeText(view.getContext(), "Registro enviado\nEn espera de ser validado", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(view.getContext(), "No se pudo enviar el registro\nRevise su conexion a internet", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign_up_tenant, container, false);
        setInterfaceVariables();
        setListeners();
        SignUpTenant.malls = SelectionMall.getMalls();
        if(SignUpTenant.malls == null || SignUpTenant.malls.length == 0){
            this.cwsc = 1;
            webService.callMethodWebService("leer", WebService.pcrm, new Object[]{"", "CentroComercial", null});
        }else{
            mallS.setAdapter(new SpinnerAdapter(view.getContext(),R.layout.fragment_sign_up_tenant, SignUpTenant.malls));
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
        switch(this.cwsc){
            case 1 :
                setMalls(responseWebService.toString());
                break;
            case 2:
                setShops(responseWebService.toString());
                break;
            case 3:
                statusSend(Boolean.parseBoolean(responseWebService.toString()));
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.profileImageIV:
                imageSelector.selectImage(this, profileImageIV, getString(R.string.loadProfileImage));
                break;
            case R.id.shopImageIV:
                imageSelector.selectImage(this, shopImageIV, getString(R.string.loadShopImage));
                break;
            case R.id.openingTV:
                timeSelector.showTimePickerDialog(view.getContext(), getString(R.string.opening), openingD, openingTV);
                break;
            case R.id.closingTV:
                timeSelector.showTimePickerDialog(view.getContext(), getString(R.string.closing), closingD, closingTV);
                break;
            case R.id.sendB:
                send();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        AdapterView<SpinnerAdapter> sa = (AdapterView<SpinnerAdapter>) adapterView;
        CentroComercial mall = (CentroComercial) sa.getItemAtPosition(position);
        addressMallET.setText(""+mall.getId().getDireccion());
        this.cwsc = 2;
        webService.callMethodWebService("leer", WebService.pcrm, new Object[]{"{ \"id\" : {\"centroComercial\" : \"" + mall.getId().getNombre() + "\", \"direccionCentroComercial\" : " + mall.getId().getDireccion() + " }, \"estatus\" : \"L\"}", "Local", null});
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}