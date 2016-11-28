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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

import modelo.ComunicationWebServiceAndroid;
import modelo.basedatos.Area;
import modelo.basedatos.CentroComercial;
import modelo.basedatos.Local;
import modelo.basedatos.LocalId;
import modelo.list_view.ListViewAdapter2;
import modelo.list_view.SingleRow2;
import modelo.spinner.SpinnerAdapter;
import utilerias.JSON;
import utilerias.List;
import utilerias.Mysql;
import utilerias.WebService;

public class ShopsManagement extends Fragment implements ComunicationWebServiceAndroid, View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    private OnFragmentInteractionListener mListener;

    private View view;
    private TextView mallTV;
    private EditText shopET;
    private Spinner statusS, areaS;
    private Button saveB, searchB, modifyB, deleteB;
    private ListView shopsLV;

    private WebService webService;
    private int cwsc;//call web service code

    private CentroComercial mall;
    private Local []shops;

    public ShopsManagement() {

    }

    public static SingleRow2[] toSingleRow2(Local []shops){
        SingleRow2 []sr2 = new SingleRow2[0];
        int i;
        if(shops != null){
            sr2 = new SingleRow2[shops.length];
            for(i = 0; i < shops.length; i++){
                sr2[i] = new SingleRow2(shops[i].getId().getLocal(), shops[i].getArea(), shops[i].getStatusMeaning());
            }
        }
        return sr2;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void setInterfaceVariables(){
        mallTV = (TextView) view.findViewById(R.id.mallTV);
        shopET = (EditText) view.findViewById(R.id.shopET);
        statusS = (Spinner) view.findViewById(R.id.statusS);
        areaS = (Spinner) view.findViewById(R.id.areaS);
        saveB = (Button) view.findViewById(R.id.saveB);
        searchB = (Button) view.findViewById(R.id.searchB);
        modifyB = (Button) view.findViewById(R.id.modifyB);
        deleteB = (Button) view.findViewById(R.id.deleteB);
        shopsLV = (ListView) view.findViewById(R.id.shopsLV);
        webService = new WebService(view.getContext(), this);
    }

    public void setListeners(){
        saveB.setOnClickListener(this);
        searchB.setOnClickListener(this);
        modifyB.setOnClickListener(this);
        deleteB.setOnClickListener(this);
        shopsLV.setOnItemClickListener(this);
        shopsLV.setOnItemLongClickListener(this);
    }

    public void setStatus(){
        statusS.setAdapter(new SpinnerAdapter(view.getContext(), R.layout.fragment_shops_management, new String[]{"Libre","Ocupado"}));
    }

    public void setAreas(String areasJSON){
        Area []areas;
        if(areasJSON.startsWith("[")){
            areas = (Area[]) JSON.aArrayObjeto(areasJSON, "Area");
        }else{
            Area ta = (Area) JSON.aObjeto(areasJSON, "Area");
            areas = (ta == null) ? new Area[0] : new Area[]{ta};
        }
        if(areas == null || areas.length == 0){
            Toast.makeText(view.getContext(), "Al parecer no existen areas para este Centro Comercial\nPara poder continuar primero cree alguna(s)", Toast.LENGTH_LONG).show();
            return;
        }
        mallTV.setText(this.mall.getId().getNombre());
        setListeners();
        setStatus();
        areaS.setAdapter(new SpinnerAdapter(view.getContext(), R.layout.fragment_shops_management, areas));
    }

    public LinkedList<String> missingFields(){
        LinkedList<String> mf = new LinkedList<String>();
        if(shopET.getText().toString().trim() == ""){
            mf.add(getString(R.string.shop));
        }
        if(statusS.getSelectedItem() == null){
            mf.add(getString(R.string.status));
        }
        if(areaS.getSelectedItem() == null){
            mf.add(getString(R.string.area));
        }
        return mf;
    }

    public Local readShop(){
        Local s = new Local();
        LocalId is = new LocalId();
        is.setLocal((shopET.getText().toString().trim().isEmpty()) ? Mysql.valorNoSeteadoString : shopET.getText().toString().trim());
        is.setCentroComercial(this.mall.getId().getNombre());
        is.setDireccionCentroComercial(this.mall.getId().getDireccion());
        s.setId(is);
        s.setArea(areaS.getSelectedItem().toString().trim());
        s.setStatusFromMeaning(statusS.getSelectedItem().toString().trim());
        return s;
    }

    public void save(){
        LinkedList<String> mf = missingFields();
        if(mf.size() == 0){
            this.cwsc = 2;
            this.webService.callMethodWebService("crear", WebService.pccm, new Object[]{JSON.aJSON(readShop(), "Local"), "Local"});
        }else{
            Toast.makeText(getContext(),"Ingrese los siguientes campos:\n"+ List.joinItems(mf,"\n"), Toast.LENGTH_LONG).show();
        }
    }

    public void search(){
        this.cwsc = 3;
        this.webService.callMethodWebService("leer", WebService.pcrm, new Object[]{JSON.aJSON(readShop(), "Local"), "Local", null});
    }

    public void modify(){
        LinkedList<String> mf = missingFields();
        if(mf.size() == 0){
            this.cwsc = 4;
            this.webService.callMethodWebService("actualizar", WebService.pcum, new Object[]{JSON.aJSON(readShop(), "Local"), "Local"});
        }else{
            Toast.makeText(getContext(),"Ingrese los siguientes campos:\n"+ List.joinItems(mf,"\n"), Toast.LENGTH_LONG).show();
        }
    }

    public void delete(){
        LinkedList<String> mf = missingFields();
        if(mf.size() == 0){
            this.cwsc = 5;
            this.webService.callMethodWebService("eliminar", WebService.pcdm, new Object[]{JSON.aJSON(readShop(), "Local"), "Local"});
        }else{
            Toast.makeText(getContext(),"Ingrese los siguientes campos:\n"+ List.joinItems(mf,"\n"), Toast.LENGTH_LONG).show();
        }
    }

    public void statusSave(boolean status){
        if(status){
            Toast.makeText(view.getContext(), "Local guardado", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(view.getContext(), "No se pudo guardar el Local\nPara guardar un Local este NO debe existir\nRevise su conexion a internet", Toast.LENGTH_LONG).show();
        }
    }

    public void setShops(String shops){
        if(shops.startsWith("[")){
            this.shops = (Local[]) JSON.aArrayObjeto(shops, "Local");
        }else{
            Local ts = (Local) JSON.aObjeto(shops, "Local");
            this.shops = (ts == null) ? new Local[0] : new Local[]{ts};
        }
        if(this.shops == null || this.shops.length == 0){
            Toast.makeText(view.getContext(), "No se encontraron registros con los datos ingresados", Toast.LENGTH_LONG).show();
        }
        shopsLV.setAdapter(new ListViewAdapter2(view.getContext(), ShopsManagement.toSingleRow2(this.shops)));
    }

    public void statusModify(boolean status){
        if(status){
            Toast.makeText(view.getContext(), "Local modificado", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(view.getContext(), "No se pudo modificar el Local\nPara modificar un Local este DEBE existir\nRevise su conexion a internet", Toast.LENGTH_LONG).show();
        }
    }

    public void statusDelete(boolean status){
        if(status){
            Toast.makeText(view.getContext(), "Local eliminado", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(view.getContext(), "No se pudo eliminar el Local\nPara eliminar un Local este DEBE existir\nRevise su conexion a internet", Toast.LENGTH_LONG).show();
        }
    }

    public void setOnInterface(Local s){
        if(s != null){
            mallTV.setText(s.getId().getCentroComercial());
            shopET.setText(s.getId().getLocal());
            statusS.setSelection(((SpinnerAdapter)statusS.getAdapter()).getPosition(s.getStatusMeaning()));
            areaS.setSelection(((SpinnerAdapter)statusS.getAdapter()).getPosition(s.getArea()));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shops_management, container, false);
        setInterfaceVariables();
        this.mall = Mall.getMall();
        if(this.mall != null){
            this.cwsc = 1;
            webService.callMethodWebService("leer", WebService.pcrm, new Object[]{"{ \"id\" : {\"centroComercial\" : \"" + this.mall.getId().getNombre() + "\", \"direccionCentroComercial\" : \"" + this.mall.getId().getDireccion() + "\"}}", "Area", null});
        }else{
            Toast.makeText(view.getContext(), "Por favor seleccione primero un Centro Comercial", Toast.LENGTH_LONG).show();
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
                setAreas(responseWebService.toString());
                break;
            case 2:
                statusSave(Boolean.parseBoolean(responseWebService.toString()));
                break;
            case 3:
                setShops(responseWebService.toString());
                break;
            case 4:
                statusModify(Boolean.parseBoolean(responseWebService.toString()));
                break;
            case 5:
                statusDelete(Boolean.parseBoolean(responseWebService.toString()));
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.saveB:
                save();
                break;
            case R.id.searchB:
                search();
                break;
            case R.id.modifyB:
                modify();
                break;
            case R.id.deleteB:
                delete();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if(this.shops != null && position < this.shops.length){
            setOnInterface(this.shops[position]);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        if(this.shops != null && position < this.shops.length){
            Bundle args = new Bundle();
            args.putSerializable("shop", this.shops[position]);
            Fragment shop = new Shop();
            shop.setArguments(args);
            getFragmentManager().beginTransaction().replace(R.id.navigationDrawerRL, shop).commit();
        }
        return false;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
