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

import java.util.LinkedList;

import modelo.ComunicationWebServiceAndroid;
import modelo.basedatos.Usuario;
import utilerias.Image;
import utilerias.JSON;
import utilerias.List;
import utilerias.WebService;

public class Profile extends Fragment implements ComunicationWebServiceAndroid, View.OnClickListener{

    private OnFragmentInteractionListener mListener;

    private View view;
    private TextView accountTV;
    private ImageView imageIV;
    private EditText keyET, namesET, firstNameET, secondNameET;
    private Button modifyB;

    private WebService webService;

    private Image imageSelector;
    private Usuario um;//user modified (auxiliary)

    private static Usuario user;

    public Profile() {
        imageSelector = new Image();
    }

    public static Usuario getUser() {
        return user;
    }

    public static void closeSession(){
        Profile.user = null;
    }

    public void setInterfaceVariables(){
        accountTV = (TextView) view.findViewById(R.id.accountTV);
        imageIV = (ImageView) view.findViewById(R.id.imageIV);
        keyET = (EditText) view.findViewById(R.id.keyET);
        namesET = (EditText) view.findViewById(R.id.namesET);
        firstNameET = (EditText) view.findViewById(R.id.firstNameET);
        secondNameET = (EditText) view.findViewById(R.id.secondNameET);
        modifyB = (Button) view.findViewById(R.id.modifyB);
        webService = new WebService(view.getContext(), this);
    }

    public void setListeners(){
        imageIV.setOnClickListener(this);
        modifyB.setOnClickListener(this);
    }

    public void setUser(){
        if(Profile.user != null){
            accountTV.setText(Profile.user.getCuenta());
            Image.setImageFromBase64(Profile.user.getImagen(), imageIV);
            keyET.setText(Profile.user.getClave());
            namesET.setText(Profile.user.getNombres());
            firstNameET.setText(Profile.user.getPrimerApellido());
            secondNameET.setText(Profile.user.getSegundoApellido());
        }
    }

    public LinkedList<String> missingFields(){
        LinkedList<String> mf = new LinkedList<String>();
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
        return mf;
    }

    public Usuario readUser(){
        Usuario u = Profile.user;
        u.setImagen(Image.imageToBase64(imageIV));
        u.setClave(keyET.getText().toString());
        u.setNombres(namesET.getText().toString().trim());
        u.setPrimerApellido(firstNameET.getText().toString().trim());
        u.setSegundoApellido(secondNameET.getText().toString().trim());
        return u;
    }

    public void modify(){
        LinkedList<String> mf = missingFields();
        if(mf.size() == 0){
            um = readUser();
            webService.callMethodWebService("actualizar", WebService.pcum, new Object[]{JSON.aJSON(um, "Usuario"),"Usuario"});
        }else{
            Toast.makeText(getContext(),"Ingrese los siguientes campos:\n"+ List.joinItems(mf,"\n"), Toast.LENGTH_LONG).show();
        }
    }

    public void statusModify(boolean status){
        if(status){
            Profile.user = um;
            Toast.makeText(view.getContext(), "Perfil modificado", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(view.getContext(), "No se pudo modificar su perfi\nRevise su conexion a internet", Toast.LENGTH_LONG).show();
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        Bundle args = getArguments();
        if(args != null){
            Profile.user = (Usuario) args.getSerializable("user");
        }
        if(Profile.user != null){
            setInterfaceVariables();
            setListeners();
            setUser();
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
        statusModify(Boolean.parseBoolean(responseWebService.toString()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageIV:
                imageSelector.selectImage(this, imageIV, getString(R.string.loadProfileImage));
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
