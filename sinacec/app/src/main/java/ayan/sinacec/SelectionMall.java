package ayan.sinacec;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import modelo.ComunicationWebServiceAndroid;
import modelo.basedatos.CentroComercial;
import modelo.basedatos.Usuario;
import modelo.list_view.ListViewAdapter;
import modelo.list_view.SingleRow;
import utilerias.JSON;
import utilerias.WebService;

public class SelectionMall extends Fragment implements AdapterView.OnItemLongClickListener, ComunicationWebServiceAndroid{

    private OnFragmentInteractionListener mListener;

    private View view;
    private ListView mallsLV;

    private static CentroComercial[] malls;

    private WebService webService;

    public SelectionMall() {

    }

    public static CentroComercial[] getMalls() {
        return malls;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void setInterfaceVariables(){
        mallsLV = (ListView) view.findViewById(R.id.mallsLV);
        webService = new WebService(view.getContext(), this);
    }

    public void setListeners(){
        mallsLV.setOnItemLongClickListener(this);
    }

    public static SingleRow[] toSingleRow(CentroComercial[] malls){
        SingleRow[] sr = new SingleRow[0];
        int i;
        if(malls != null){
            sr = new SingleRow[malls.length];
            for(i=0; i < malls.length; i++){
                sr[i] = new SingleRow(malls[i].getImagen(), malls[i].getId().getNombre());
            }
        }
        return sr;
    }

    public void setMalls(String malls)  {
        if(malls.startsWith("[")){
            SelectionMall.malls = (CentroComercial[]) JSON.aArrayObjeto(malls, "CentroComercial");
        }else{
            CentroComercial tm = (CentroComercial) JSON.aObjeto(malls, "CentroComercial");
            SelectionMall.malls = (tm == null) ? new CentroComercial[0] : new CentroComercial[]{tm};
        }
        mallsLV.setAdapter(new ListViewAdapter(view.getContext(), toSingleRow(SelectionMall.malls)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_selection_mall, container, false);
        setInterfaceVariables();
        setListeners();
        Usuario user = Profile.getUser();
        if(user != null){
            switch (user.getTipo()){
                case 'C':
                    webService.callMethodWebService("leer",WebService.pcrm, new Object[]{"{\"administrador\" : \"" + user.getCuenta() + "\"}","CentroComercial",null});
                    break;
                case 'L':
                    webService.callMethodWebService("llamarProcedimientoSelect",WebService.pclpsm, new Object[]{"malls_tenant", user.getCuenta(), "CentroComercial"});
                    break;
            }
        }else{
            webService.callMethodWebService("leer",WebService.pcrm, new Object[]{"","CentroComercial",null});
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
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        Bundle args = new Bundle();
        args.putSerializable("mall",SelectionMall.malls[position]);
        Fragment  mall= new Mall();
        mall.setArguments(args);
        ((Sinacec)getActivity()).simulateNavigationItemSelected(R.id.nav_mall, mall);
        return false;
    }

    @Override
    public void manageResponse(Object responseWebService) {
        setMalls(responseWebService.toString());
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
