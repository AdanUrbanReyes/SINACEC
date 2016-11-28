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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.indooratlas.android.sdk.IALocation;
import com.indooratlas.android.sdk.IALocationListener;
import com.indooratlas.android.sdk.IALocationManager;
import com.indooratlas.android.sdk.IALocationRequest;

import java.util.LinkedList;

import modelo.ComunicationWebServiceAndroid;
import modelo.basedatos.Local;
import modelo.basedatos.Puerta;
import modelo.basedatos.PuertaId;
import modelo.indoor_atlas.IndoorAtlas;
import modelo.list_view.ListViewAdapter;
import modelo.list_view.ListViewAdapter2;
import modelo.list_view.SingleRow2;
import modelo.spinner.SpinnerAdapter;
import utilerias.JSON;
import utilerias.List;
import utilerias.Mysql;
import utilerias.WebService;

public class Doors extends Fragment implements ComunicationWebServiceAndroid, View.OnClickListener, AdapterView.OnItemClickListener, CompoundButton.OnCheckedChangeListener{

    private OnFragmentInteractionListener mListener;

    private View view;
    private TextView mallTV;
    private EditText shopET, latitudET, longitudET;
    private Switch indoorAtlasS;
    private Spinner typeS;
    private Button saveB, searchB, modifyB, deleteB;
    private ListView doorsLV;

    private WebService webService;
    private int cwsc;//call web service code

    private Local shop;
    private Puerta[] doors;

    IndoorAtlas indoorAtlas;

    public Doors() {

    }

    public static SingleRow2[] toSingleRow2(Puerta []doors){
        SingleRow2 []sr2 = new SingleRow2[0];
        int i;
        if(doors != null){
            sr2 = new SingleRow2[doors.length];
            for(i = 0; i < doors.length ; i++){
                sr2[i] = new SingleRow2(doors[i].getMeaningTipo(), ""+doors[i].getId().getLatitud(), ""+doors[i].getId().getLongitud());
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
        indoorAtlasS = (Switch) view.findViewById(R.id.indoorAtlasS);
        latitudET = (EditText) view.findViewById(R.id.latitudET);
        longitudET = (EditText) view.findViewById(R.id.longitudET);
        typeS = (Spinner) view.findViewById(R.id.typeS);
        saveB = (Button) view.findViewById(R.id.saveB);
        searchB = (Button) view.findViewById(R.id.searchB);
        modifyB = (Button) view.findViewById(R.id.modifyB);
        deleteB = (Button) view.findViewById(R.id.deleteB);
        doorsLV = (ListView) view.findViewById(R.id.doorsLV);
        webService = new WebService(view.getContext(), this);
        indoorAtlas = new IndoorAtlas(view.getContext(), latitudET, longitudET);
    }

    public void setListeners(){
        indoorAtlasS.setOnCheckedChangeListener(this);
        saveB.setOnClickListener(this);
        searchB.setOnClickListener(this);
        modifyB.setOnClickListener(this);
        deleteB.setOnClickListener(this);
        doorsLV.setOnItemClickListener(this);
    }

    public void setTypes(){
        typeS.setAdapter(new SpinnerAdapter(view.getContext(), R.layout.fragment_doors, new String[]{"Entrada","Salida","Virtual","Entrada/Salida"}));
    }

    public void setShopOnInterface(){
        if(this.shop != null){
            mallTV.setText(this.shop.getId().getCentroComercial());
            shopET.setText(this.shop.getId().getLocal());
        }
    }

    public LinkedList<String> missingFields(){
        LinkedList<String> mf = new LinkedList<String>();
        if(latitudET.getText().toString().trim().isEmpty()){
            mf.add(getString(R.string.latitud));
        }
        if(longitudET.getText().toString().trim().isEmpty()){
            mf.add(getString(R.string.longitud));
        }
        if(typeS.getSelectedItem() == null){
            mf.add(getString(R.string.type));
        }
        return mf;
    }

    public Puerta readDoor(){
        Puerta d = new Puerta();
        PuertaId id = new PuertaId();
        id.setLatitud((latitudET.getText().toString().trim().isEmpty()) ? Mysql.valorNoSeteadoDouble : Double.parseDouble(latitudET.getText().toString().trim()));
        id.setLongitud((longitudET.getText().toString().trim().isEmpty()) ? Mysql.valorNoSeteadoDouble : Double.parseDouble(longitudET.getText().toString().trim()));
        id.setLocal(shop.getId().getLocal());
        id.setCentroComercial(shop.getId().getCentroComercial());
        id.setDireccionCentroComercial(shop.getId().getDireccionCentroComercial());
        d.setId(id);
        d.setTipo(d.getTypeFromMeaning(typeS.getSelectedItem().toString()));
        return d;
    }

    public void save(){
        LinkedList<String> mf = missingFields();
        if(mf.size() == 0){
            this.cwsc = 1;
            webService.callMethodWebService("crear", WebService.pccm, new Object[]{JSON.aJSON(readDoor(), "Puerta"), "Puerta"});
        }else{
            Toast.makeText(getContext(),"Ingrese los siguientes campos:\n"+ List.joinItems(mf,"\n"), Toast.LENGTH_LONG).show();
        }
    }

    public void search(){
        this.cwsc = 2;
        webService.callMethodWebService("leer", WebService.pcrm, new Object[]{JSON.aJSON(readDoor(), "Puerta"), "Puerta", null});
    }

    public void modify(){
        LinkedList<String> mf = missingFields();
        if(mf.size() == 0){
            this.cwsc = 3;
            webService.callMethodWebService("actualizar", WebService.pcum, new Object[]{JSON.aJSON(readDoor(), "Puerta"), "Puerta"});
        }else{
            Toast.makeText(getContext(),"Ingrese los siguientes campos:\n"+ List.joinItems(mf,"\n"), Toast.LENGTH_LONG).show();
        }
    }

    public void delete(){
        LinkedList<String> mf = missingFields();
        if(mf.size() == 0){
            this.cwsc = 4;
            webService.callMethodWebService("eliminar", WebService.pcdm, new Object[]{JSON.aJSON(readDoor(), "Puerta"), "Puerta"});
        }else{
            Toast.makeText(getContext(),"Ingrese los siguientes campos:\n"+ List.joinItems(mf,"\n"), Toast.LENGTH_LONG).show();
        }
    }

    public void statusSave(boolean status){
        if(status){
            Toast.makeText(view.getContext(), "Puerta guardada", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(view.getContext(), "No se pudo guardar la Puerta\nPara guardar una Puerta esta NO debe existir\nRevise su conexion a internet", Toast.LENGTH_LONG).show();
        }
    }

    public void setDoors(String doors){
        if(doors.startsWith("[")){
            this.doors = (Puerta[]) JSON.aArrayObjeto(doors, "Puerta");
        }else{
            Puerta td = (Puerta) JSON.aObjeto(doors, "Puerta");
            this.doors = (td == null) ? new Puerta[0] : new Puerta[]{td};
        }
        if(this.doors == null || this.doors.length == 0){
            Toast.makeText(view.getContext(), "No se encontraron registros con los datos ingresados", Toast.LENGTH_LONG).show();
        }
        doorsLV.setAdapter(new ListViewAdapter2(view.getContext(), Doors.toSingleRow2(this.doors)));
    }

    public void statusModify(boolean status){
        if(status){
            Toast.makeText(view.getContext(), "Puerta modificada", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(view.getContext(), "No se pudo modificar la Puerta\nPara modificar una Puerta esta DEBE existir\nRevise su conexion a internet", Toast.LENGTH_LONG).show();
        }
    }

    public void statusDelete(boolean status){
        if(status){
            Toast.makeText(view.getContext(), "Puerta eliminada", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(view.getContext(), "No se pudo eliminar la Puerta\nPara eliminar una Puerta esta DEBE existir\nRevise su conexion a internet", Toast.LENGTH_LONG).show();
        }
    }

    public void setOnInterface(Puerta door){
        if(door != null){
            mallTV.setText(door.getId().getCentroComercial());
            shopET.setText(door.getId().getLocal());
            latitudET.setText(""+door.getId().getLatitud());
            longitudET.setText(""+door.getId().getLongitud());
            typeS.setSelection(((SpinnerAdapter)typeS.getAdapter()).getPosition(door.getMeaningTipo()));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_doors, container, false);
        setInterfaceVariables();
        setTypes();
        Bundle args = getArguments();
        if(args != null){
            this.shop = (Local) args.getSerializable("shop");
            if(this.shop != null){
                setListeners();
                setShopOnInterface();
            }
        }
        return view;
    }

    @Override
    public void onDestroy(){
        indoorAtlas.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onPause(){
        indoorAtlas.location(false);
        super.onPause();
    }

    @Override
    public void onResume(){
        indoorAtlas.location((indoorAtlasS.isChecked()));
        super.onResume();
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
                statusSave(Boolean.parseBoolean(responseWebService.toString()));
                break;
            case 2:
                setDoors(responseWebService.toString());
                break;
            case 3:
                statusModify(Boolean.parseBoolean(responseWebService.toString()));
                break;
            case 4:
                statusDelete(Boolean.parseBoolean(responseWebService.toString()));
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
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
        if(this.doors != null && position < this.doors.length){
            setOnInterface(this.doors[position]);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        indoorAtlas.location(isChecked);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
