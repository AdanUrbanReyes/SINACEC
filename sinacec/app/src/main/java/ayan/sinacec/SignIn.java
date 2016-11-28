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
import android.widget.TextView;
import android.widget.Toast;

import modelo.ComunicationWebServiceAndroid;
import modelo.basedatos.Usuario;
import utilerias.JSON;
import utilerias.WebService;

public class SignIn extends Fragment implements ComunicationWebServiceAndroid, View.OnClickListener {

    private OnFragmentInteractionListener mListener;

    private View view;
    private EditText accountET, keyET;
    private Button signInB;
    private TextView signUpTV;

    private WebService webService;

    public SignIn() {

    }

    public void setInterfaceVariables(){
        accountET = (EditText) view.findViewById(R.id.accountET);
        keyET = (EditText) view.findViewById(R.id.keyET);
        signInB = (Button) view.findViewById(R.id.signInB);
        signUpTV = (TextView) view.findViewById(R.id.signUpTV);
        webService = new WebService(view.getContext(), this);
    }

    public void setListeners(){
        signInB.setOnClickListener(this);
        signUpTV.setOnClickListener(this);
    }

    public void signIn(){
        if(accountET.getText().toString().trim().isEmpty() || keyET.getText().toString().trim().isEmpty()){
            Toast.makeText(view.getContext(), "Por favor ingrese "+getString(R.string.account)+ " y " + getString(R.string.key), Toast.LENGTH_LONG).show();
            return;
        }
        webService.callMethodWebService("leer", WebService.pcrm, new Object[]{"{\"cuenta\" : \"" + accountET.getText().toString().trim() + "\", \"clave\" : \"" + keyET.getText().toString().trim() +"\"}" ,"Usuario" ,null});
    }

    public void signIn(String userJSON){
        Usuario user = (Usuario) JSON.aObject(userJSON, Usuario.class, null);
        if(user != null){
            //Toast.makeText(view.getContext(), "Bienvenido "+user.getNombres()+"\nSe an activado nuevas opciones", Toast.LENGTH_LONG).show();
            Mall.closeSession();
            RentedShop.closeSession();
            ((Sinacec)getActivity()).managementSession(user, true);
            Bundle args = new Bundle();
            args.putSerializable("user",user);
            Fragment profile = new Profile();
            profile.setArguments(args);
            getFragmentManager().beginTransaction().replace(R.id.navigationDrawerRL, profile).commit();
        }else{
            Toast.makeText(view.getContext(), getString(R.string.account) + " y/o" + getString(R.string.key)+" incorrectos", Toast.LENGTH_LONG).show();
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        setInterfaceVariables();
        setListeners();
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
        signIn(responseWebService.toString());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signInB:
                signIn();
                break;
            case R.id.signUpTV:
                getFragmentManager().beginTransaction().replace(R.id.navigationDrawerRL, new SignUpTenant()).commit();
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
