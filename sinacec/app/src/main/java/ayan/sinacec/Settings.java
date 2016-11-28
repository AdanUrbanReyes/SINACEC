package ayan.sinacec;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import utilerias.WebService;

public class Settings extends Fragment implements View.OnClickListener {

    public static final String ipHost = "host", ipPort="port", ipPath = "path", ipNamespace = "namespace" ;//ip = id preferences

    private OnFragmentInteractionListener mListener;

    private View view;
    private EditText hostET, portET, pathET, namespaceET;
    private Button saveB;

    private SharedPreferences preferences;

    public Settings() {

    }

    public void loadSettings(){
        hostET.setText(preferences.getString(ipHost,"deisacv.dlinkddns.com"));
        portET.setText(""+preferences.getInt(ipPort,8080));
        pathET.setText(preferences.getString(ipPath,"/webService/Sinacec?wsdl"));
        namespaceET.setText(preferences.getString(ipNamespace,"http://web_service/"));
    }

    public void saveSettings(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ipHost, hostET.getText().toString());
        editor.putInt(ipPort, Integer.parseInt(portET.getText().toString()));
        editor.putString(ipPath, pathET.getText().toString());
        editor.putString(ipNamespace, namespaceET.getText().toString());
        editor.commit();

        ((Sinacec)getActivity()).setWebServiceSettings();
        Toast.makeText(getContext(),"Se establecio la configuracion correctamente", Toast.LENGTH_LONG).show();
    }

    public void setInterfaceVariables(){
        hostET = (EditText) view.findViewById(R.id.hostET);
        portET = (EditText) view.findViewById(R.id.portET);
        pathET = (EditText) view.findViewById(R.id.pathET);
        namespaceET = (EditText) view.findViewById(R.id.namespaceET);
        saveB = (Button) view.findViewById(R.id.saveB);
        preferences = Sinacec.getSpws();
    }

    public void setListeners(){
        saveB.setOnClickListener(this);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        setInterfaceVariables();
        setListeners();
        loadSettings();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.saveB:
                saveSettings();
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
