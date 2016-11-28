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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

import modelo.ComunicationWebServiceAndroid;
import modelo.basedatos.Area;
import modelo.basedatos.AreaId;
import modelo.basedatos.CentroComercial;
import modelo.list_view.ListViewAdapter;
import modelo.list_view.SingleRow;
import utilerias.Image;
import utilerias.JSON;
import utilerias.List;
import utilerias.Mysql;
import utilerias.WebService;

public class AreasManagement extends Fragment implements ComunicationWebServiceAndroid, View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, CompoundButton.OnCheckedChangeListener{

    private OnFragmentInteractionListener mListener;

    private View view;
    private TextView mallTV;
    private ImageView imageIV;
    private EditText nameET, latitudET, longitudET;
    private Switch indoorAtlasS;
    private Button saveB, searchB, modifyB, deleteB;
    private ListView areasLV;

    private WebService webService;
    private int cwsc;//call web service code

    private CentroComercial mall;
    private Area []areas;

    private Image imageSelector;

    public AreasManagement() {
        imageSelector = new Image();
    }

    public static SingleRow[] toSingleRow(Area []a){
        SingleRow []sr = new SingleRow[0];
        int i;
        if(a != null){
            sr = new SingleRow[a.length];
            for(i = 0; i < a.length; i++){
                sr[i] = new SingleRow(a[i].getImagen(), a[i].getId().getNombre());
            }
        }
        return sr;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void setInterfaceVariables(){
        mallTV = (TextView) view.findViewById(R.id.mallTV);
        imageIV = (ImageView) view.findViewById(R.id.imageIV);
        nameET = (EditText) view.findViewById(R.id.nameET);
        indoorAtlasS = (Switch) view.findViewById(R.id.indoorAtlasS);
        latitudET = (EditText) view.findViewById(R.id.latitudET);
        longitudET = (EditText) view.findViewById(R.id.longitudET);
        saveB = (Button) view.findViewById(R.id.saveB);
        searchB = (Button) view.findViewById(R.id.searchB);
        modifyB = (Button) view.findViewById(R.id.modifyB);
        deleteB = (Button) view.findViewById(R.id.deleteB);
        areasLV = (ListView) view.findViewById(R.id.areasLV);
        webService = new WebService(view.getContext(), this);
    }

    public void setListeners(){
        imageIV.setOnClickListener(this);
        indoorAtlasS.setOnCheckedChangeListener(this);
        saveB.setOnClickListener(this);
        searchB.setOnClickListener(this);
        modifyB.setOnClickListener(this);
        deleteB.setOnClickListener(this);
        areasLV.setOnItemClickListener(this);
        areasLV.setOnItemLongClickListener(this);
    }

    public LinkedList<String> missingFields(){
        LinkedList<String> mf = new LinkedList<String>();
        if(nameET.getText().toString().trim().isEmpty()){
            mf.add(getString(R.string.name));
        }
        if(latitudET.getText().toString().trim().isEmpty()){
            mf.add(getString(R.string.latitud));
        }
        if(longitudET.getText().toString().trim().isEmpty()){
            mf.add(getString(R.string.longitud));
        }
        return mf;
    }

    public Area readArea(){
        Area a = new Area();
        a.setId(new AreaId((nameET.getText().toString().trim().isEmpty()) ? Mysql.valorNoSeteadoString : nameET.getText().toString().trim(), this.mall.getId().getNombre(), this.mall.getId().getDireccion()));
        a.setLatitud((latitudET.getText().toString().trim().isEmpty()) ? Mysql.valorNoSeteadoDouble : Double.parseDouble(latitudET.getText().toString().trim()));
        a.setLongitud((longitudET.getText().toString().trim().isEmpty()) ? Mysql.valorNoSeteadoDouble : Double.parseDouble(longitudET.getText().toString().trim()));
        a.setImagen(Image.imageToBase64(imageIV));
        return a;
    }

    public void save(){
        LinkedList<String> mf = missingFields();
        if(mf.size() == 0){
            this.cwsc = 1;
            webService.callMethodWebService("crear", WebService.pccm, new Object[]{JSON.aJSON(readArea(), "Area"), "Area"});
        }else{
            Toast.makeText(getContext(),"Ingrese los siguientes campos:\n"+ List.joinItems(mf,"\n"), Toast.LENGTH_LONG).show();
        }
    }

    public void search(){
        Area ta = readArea();
        ta.setImagen(new byte[0]);
        this.cwsc = 2;
        webService.callMethodWebService("leer", WebService.pcrm, new Object[]{JSON.aJSON(ta, "Area"), "Area", null});
    }

    public void modify(){
        LinkedList<String> mf = missingFields();
        if(mf.size() == 0){
            this.cwsc = 3;
            webService.callMethodWebService("actualizar", WebService.pcum, new Object[]{JSON.aJSON(readArea(), "Area"), "Area"});
        }else{
            Toast.makeText(getContext(),"Ingrese los siguientes campos:\n"+ List.joinItems(mf,"\n"), Toast.LENGTH_LONG).show();
        }
    }

    public void delete(){
        LinkedList<String> mf = missingFields();
        if(mf.size() == 0){
            this.cwsc = 4;
            webService.callMethodWebService("eliminar", WebService.pcdm, new Object[]{JSON.aJSON(readArea(), "Area"), "Area"});
        }else{
            Toast.makeText(getContext(),"Ingrese los siguientes campos:\n"+ List.joinItems(mf,"\n"), Toast.LENGTH_LONG).show();
        }
    }

    public void statusSave(boolean status){
        if(status){
            Toast.makeText(view.getContext(), "Area guardada", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(view.getContext(), "No se pudo guardar el Area\nPara guardar un Area esta NO debe existir\nRevise su conexion a internet", Toast.LENGTH_LONG).show();
        }
    }

    public void setAreas(String areas){
        if(areas.startsWith("[")){
            this.areas = (Area[]) JSON.aArrayObjeto(areas, "Area");
        }else{
            Area t = (Area) JSON.aObjeto(areas, "Area");
            this.areas = (t == null) ? new Area[0] : new Area[]{t};
        }
        if(this.areas == null || this.areas.length == 0){
            Toast.makeText(view.getContext(), "No se encontraron registros con los datos ingresados", Toast.LENGTH_LONG).show();
        }
        areasLV.setAdapter(new ListViewAdapter(view.getContext(), AreasManagement.toSingleRow(this.areas)));
    }

    public void statusModify(boolean status){
        if(status){
            Toast.makeText(view.getContext(), "Area modificada", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(view.getContext(), "No se pudo modificar el Area\nPara modificar un Area esta DEBE existir\nRevise su conexion a internet", Toast.LENGTH_LONG).show();
        }
    }

    public void statusDelete(boolean status){
        if(status){
            Toast.makeText(view.getContext(), "Area eliminada", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(view.getContext(), "No se pudo eliminar el Area\nPara eliminar un Area esta DEBE existir\nRevise su conexion a internet", Toast.LENGTH_LONG).show();
        }
    }

    public void setOnInterface(Area a){
        if(a != null){
            mallTV.setText(a.getId().getCentroComercial());
            Image.setImageFromBase64(a.getImagen(), imageIV);
            nameET.setText(a.getId().getNombre());
            latitudET.setText(""+a.getLatitud());
            longitudET.setText(""+a.getLongitud());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_areas_management, container, false);
        setInterfaceVariables();
        this.mall = Mall.getMall();
        if(this.mall != null){
            mallTV.setText(this.mall.getId().getNombre());
            setListeners();
        }else{
            Toast.makeText(view.getContext(), "Por favor seleccione primero un Centro Comercial", Toast.LENGTH_LONG).show();
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
                statusSave(Boolean.parseBoolean(responseWebService.toString()));
                break;
            case 2:
                setAreas(responseWebService.toString());
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
        switch(view.getId()){
            case R.id.imageIV:
                imageSelector.selectImage(this, imageIV, getString(R.string.loadAreaImage));
                break;
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
        if(this.areas != null && position < this.areas.length){
            setOnInterface(this.areas[position]);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        if(this.areas != null && position < this.areas.length){
            Bundle args = new Bundle();
            args.putSerializable("area", this.areas[position]);
            Fragment area = new ayan.sinacec.Area();
            area.setArguments(args);
            getFragmentManager().beginTransaction().replace(R.id.navigationDrawerRL, area).commit();
        }
        return false;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
