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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

import modelo.ComunicationWebServiceAndroid;
import modelo.basedatos.LocalRentado;
import modelo.basedatos.Servicio;
import modelo.basedatos.ServicioId;
import modelo.list_view.ListViewAdapter;
import utilerias.Image;
import utilerias.JSON;
import utilerias.List;
import utilerias.Mysql;
import utilerias.WebService;

public class ServicesManagement extends Fragment implements ComunicationWebServiceAndroid, View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    private OnFragmentInteractionListener mListener;

    private View view;
    private TextView mallTV, shopTV;
    private ImageView imageIV;
    private EditText nameET, descriptionET;
    private Button saveB, searchB, modifyB, deleteB;
    private ListView servicesLV;

    private WebService webService;
    private int cwsc;//call web service code

    private LocalRentado rentedShop;
    private Servicio []services;

    private Image imageSelector;

    public ServicesManagement() {
        imageSelector = new Image();
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
        shopTV = (TextView) view.findViewById(R.id.shopTV);
        descriptionET = (EditText) view.findViewById(R.id.descriptionET);
        saveB = (Button) view.findViewById(R.id.saveB);
        searchB = (Button) view.findViewById(R.id.searchB);
        modifyB = (Button) view.findViewById(R.id.modifyB);
        deleteB = (Button) view.findViewById(R.id.deleteB);
        servicesLV = (ListView) view.findViewById(R.id.servicesLV);
        webService = new WebService(view.getContext(), this);
    }

    public void setListeners(){
        imageIV.setOnClickListener(this);
        saveB.setOnClickListener(this);
        searchB.setOnClickListener(this);
        modifyB.setOnClickListener(this);
        deleteB.setOnClickListener(this);
        servicesLV.setOnItemClickListener(this);
        servicesLV.setOnItemLongClickListener(this);
    }

    public LinkedList<String> missingFields(){
        LinkedList<String> mf = new LinkedList<String>();
        if(nameET.getText().toString().trim().isEmpty()){
            mf.add(getString(R.string.name));
        }
        if(descriptionET.getText().toString().trim().isEmpty()){
            mf.add(getString(R.string.description));
        }
        return mf;
    }

    public Servicio readService(){
        Servicio p = new Servicio();
        ServicioId ip = new ServicioId();
        ip.setLocal(this.rentedShop.getId().getLocal());
        ip.setCentroComercial(this.rentedShop.getId().getCentroComercial());
        ip.setDireccionCentroComercial(this.rentedShop.getId().getDireccionCentroComercial());
        ip.setNombre((nameET.getText().toString().trim().isEmpty()) ? Mysql.valorNoSeteadoString : nameET.getText().toString().trim());
        p.setId(ip);
        p.setDescripcion((descriptionET.getText().toString().trim().isEmpty()) ? Mysql.valorNoSeteadoString : descriptionET.getText().toString().trim());
        p.setImagen(Image.imageToBase64(imageIV));
        return p;
    }

    public void save(){
        LinkedList<String> mf = missingFields();
        if(mf.size() == 0){
            this.cwsc = 1;
            webService.callMethodWebService("crear", WebService.pccm, new Object[]{JSON.aJSON(readService(), "Servicio"), "Servicio"});
        }else{
            Toast.makeText(getContext(),"Ingrese los siguientes campos:\n"+ List.joinItems(mf,"\n"), Toast.LENGTH_LONG).show();
        }
    }

    public void search(){
        Servicio ts = readService();
        ts.setImagen(new byte[0]);
        this.cwsc = 2;
        webService.callMethodWebService("leer", WebService.pcrm, new Object[]{JSON.aJSON(ts, "Servicio"), "Servicio", null});
    }

    public void modify(){
        LinkedList<String> mf = missingFields();
        if(mf.size() == 0){
            this.cwsc = 3;
            webService.callMethodWebService("actualizar", WebService.pcum, new Object[]{JSON.aJSON(readService(), "Servicio"), "Servicio"});
        }else{
            Toast.makeText(getContext(),"Ingrese los siguientes campos:\n"+ List.joinItems(mf,"\n"), Toast.LENGTH_LONG).show();
        }
    }

    public void delete(){
        LinkedList<String> mf = missingFields();
        if(mf.size() == 0){
            this.cwsc = 4;
            webService.callMethodWebService("eliminar", WebService.pcdm, new Object[]{JSON.aJSON(readService(), "Servicio"), "Servicio"});
        }else{
            Toast.makeText(getContext(),"Ingrese los siguientes campos:\n"+ List.joinItems(mf,"\n"), Toast.LENGTH_LONG).show();
        }
    }

    public void statusSave(boolean status){
        if(status){
            Toast.makeText(view.getContext(), "Servicio guardado", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(view.getContext(), "No se pudo guardar el Servicio\nPara guardar un Servicio este NO debe existir\nRevise su conexion a internet", Toast.LENGTH_LONG).show();
        }
    }

    public void setServices(String services){
        if(services.startsWith("[")){
            this.services = (Servicio[]) JSON.aArrayObjeto(services, "Servicio");
        }else{
            Servicio t = (Servicio) JSON.aObjeto(services, "Servicio");
            this.services = (t == null) ? new Servicio[0] : new Servicio[]{t};
        }
        if(this.services == null || this.services.length == 0){
            Toast.makeText(view.getContext(), "No se encontraron registros con los datos ingresados", Toast.LENGTH_LONG).show();
        }
        servicesLV.setAdapter(new ListViewAdapter(view.getContext(), Services.toSingleRow(this.services)));
    }

    public void statusModify(boolean status){
        if(status){
            Toast.makeText(view.getContext(), "Servicio modificado", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(view.getContext(), "No se pudo modificar el Servicio\nPara modificar un Servicio este DEBE existir\nRevise su conexion a internet", Toast.LENGTH_LONG).show();
        }
    }

    public void statusDelete(boolean status){
        if(status){
            Toast.makeText(view.getContext(), "Servicio eliminado", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(view.getContext(), "No se pudo eliminar el Servicio\nPara eliminar un Servicio este DEBE existir\nRevise su conexion a internet", Toast.LENGTH_LONG).show();
        }
    }

    public void setOnInterface(Servicio s){
        if(s != null){
            mallTV.setText(s.getId().getCentroComercial());
            Image.setImageFromBase64(s.getImagen(), imageIV);
            nameET.setText(s.getId().getNombre());
            shopTV.setText(s.getId().getLocal());
            descriptionET.setText(s.getDescripcion());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_services_management, container, false);
        this.rentedShop = RentedShop.getRentedShop();
        if(this.rentedShop != null){
            setInterfaceVariables();
            setListeners();
            mallTV.setText(this.rentedShop.getId().getCentroComercial());
            shopTV.setText(this.rentedShop.getId().getLocal());
        }else{
            Toast.makeText(view.getContext(), "Por favor seleccione primero un Local", Toast.LENGTH_LONG).show();
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
                setServices(responseWebService.toString());
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
                imageSelector.selectImage(this, imageIV, getString(R.string.loadProductImage));
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
        if(this.services != null && position < this.services.length){
            setOnInterface(this.services[position]);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        if(this.services != null && position < this.services.length){
            Bundle args = new Bundle();
            args.putSerializable("service", this.services[position]);
            Fragment service = new Service();
            service.setArguments(args);
            getFragmentManager().beginTransaction().replace(R.id.navigationDrawerRL, service).commit();
        }
        return false;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
