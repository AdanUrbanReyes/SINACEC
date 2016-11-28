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

import java.util.Date;
import java.util.LinkedList;

import modelo.ComunicationWebServiceAndroid;
import modelo.basedatos.LocalRentado;
import modelo.basedatos.Oferta;
import modelo.basedatos.OfertaId;
import modelo.basedatos.Usuario;
import modelo.list_view.ListViewAdapter;
import modelo.list_view.SingleRow;
import utilerias.Calendar;
import utilerias.Image;
import utilerias.JSON;
import utilerias.List;
import utilerias.Mysql;
import utilerias.WebService;

public class OffersManagement extends Fragment implements ComunicationWebServiceAndroid, View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    private OnFragmentInteractionListener mListener;

    private View view;
    private TextView mallTV, shopTV, tenantTV, startDateTV, endDateTV;
    private ImageView imageIV;
    private EditText descriptionET;
    private Button saveB, searchB, modifyB, deleteB;
    private ListView offersLV;

    private WebService webService;
    private int cwsc;//call web service code

    private LocalRentado rentedShop;
    private Usuario user;
    private Oferta []offers;

    private Image imageSelector;
    private Calendar dateSelector;
    private Date startDateD, endDateD;
    
    public OffersManagement() {
        imageSelector = new Image();
        dateSelector = new Calendar();
        startDateD = new Date();
        endDateD = new Date();
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public static SingleRow[] toSingleRow(Oferta[] offers){
        SingleRow []sr = new SingleRow[0];
        int i;
        if(offers != null){
            sr = new SingleRow[offers.length];
            for(i=0; i < offers.length;i++){
                sr[i] = new SingleRow(offers[i].getImagen(), offers[i].getId().getDescripcion());
            }
        }
        return sr;
    }
    
    public void setInterfaceVariables(){
        mallTV = (TextView) view.findViewById(R.id.mallTV);
        imageIV = (ImageView) view.findViewById(R.id.imageIV);
        tenantTV = (TextView) view.findViewById(R.id.tenantTV);
        shopTV = (TextView) view.findViewById(R.id.shopTV);
        descriptionET = (EditText) view.findViewById(R.id.descriptionET);
        startDateTV = (TextView) view.findViewById(R.id.startDateTV);
        endDateTV = (TextView) view.findViewById(R.id.endDateTV);
        saveB = (Button) view.findViewById(R.id.saveB);
        searchB = (Button) view.findViewById(R.id.searchB);
        modifyB = (Button) view.findViewById(R.id.modifyB);
        deleteB = (Button) view.findViewById(R.id.deleteB);
        offersLV = (ListView) view.findViewById(R.id.offersLV);
        webService = new WebService(view.getContext(), this);
    }

    public void setListeners(){
        imageIV.setOnClickListener(this);
        startDateTV.setOnClickListener(this);
        endDateTV.setOnClickListener(this);
        saveB.setOnClickListener(this);
        searchB.setOnClickListener(this);
        modifyB.setOnClickListener(this);
        deleteB.setOnClickListener(this);
        offersLV.setOnItemClickListener(this);
        offersLV.setOnItemLongClickListener(this);
    }

    public LinkedList<String> missingFields(){
        LinkedList<String> mf = new LinkedList<String>();
        if(descriptionET.getText().toString().trim().isEmpty()){
            mf.add(getString(R.string.description));
        }
        if(startDateTV.getText().toString().trim().isEmpty()){
            mf.add(getString(R.string.startDate));
        }
        if(endDateTV.getText().toString().trim().isEmpty()){
            mf.add(getString(R.string.endDate));
        }
        return mf;
    }

    public Oferta readOffer(){
        Oferta o = new Oferta();
        OfertaId io = new OfertaId();
        io.setLocatario(this.user.getCuenta());
        io.setLocal(this.rentedShop.getId().getLocal());
        io.setCentroComercial(this.rentedShop.getId().getCentroComercial());
        io.setDireccionCentroComercial(this.rentedShop.getId().getDireccionCentroComercial());
        io.setDescripcion((descriptionET.getText().toString().trim().isEmpty()) ? Mysql.valorNoSeteadoString : descriptionET.getText().toString().trim());
        o.setId(io);
        o.setImagen(Image.imageToBase64(imageIV));
        o.setInicio(startDateD);
        o.setExpiracion(endDateD);
        return o;
    }

    public void save(){
        LinkedList<String> mf = missingFields();
        if(mf.size() == 0){
            this.cwsc = 1;
            webService.callMethodWebService("crear", WebService.pccm, new Object[]{JSON.aJSON(readOffer(), "Oferta"), "Oferta"});
        }else{
            Toast.makeText(getContext(),"Ingrese los siguientes campos:\n"+ List.joinItems(mf,"\n"), Toast.LENGTH_LONG).show();
        }
    }

    public void search(){
        Oferta to = readOffer();
        to.setImagen(new byte[0]);
        to.setInicio(Mysql.valorNoSeteadoDate);
        to.setExpiracion(Mysql.valorNoSeteadoDate);
        this.cwsc = 2;
        webService.callMethodWebService("leer", WebService.pcrm, new Object[]{JSON.aJSON(to, "Oferta"), "Oferta", null});
    }

    public void modify(){
        LinkedList<String> mf = missingFields();
        if(mf.size() == 0){
            this.cwsc = 3;
            webService.callMethodWebService("actualizar", WebService.pcum, new Object[]{JSON.aJSON(readOffer(), "Oferta"), "Oferta"});
        }else{
            Toast.makeText(getContext(),"Ingrese los siguientes campos:\n"+ List.joinItems(mf,"\n"), Toast.LENGTH_LONG).show();
        }
    }

    public void delete(){
        LinkedList<String> mf = missingFields();
        if(mf.size() == 0){
            this.cwsc = 4;
            webService.callMethodWebService("eliminar", WebService.pcdm, new Object[]{JSON.aJSON(readOffer(), "Oferta"), "Oferta"});
        }else{
            Toast.makeText(getContext(),"Ingrese los siguientes campos:\n"+ List.joinItems(mf,"\n"), Toast.LENGTH_LONG).show();
        }
    }

    public void statusSave(boolean status){
        if(status){
            Toast.makeText(view.getContext(), "Oferta guardada", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(view.getContext(), "No se pudo guardar la Oferta\nPara guardar una Oferta esta NO debe existir\nRevise su conexion a internet", Toast.LENGTH_LONG).show();
        }
    }

    public void setOfferts(String offers){
        if(offers.startsWith("[")){
            this.offers = (Oferta[]) JSON.aArrayObjeto(offers, "Oferta");
        }else{
            Oferta t = (Oferta) JSON.aObjeto(offers, "Oferta");
            this.offers = (t == null) ? new Oferta[0] : new Oferta[]{t};
        }
        if(this.offers == null || this.offers.length == 0){
            Toast.makeText(view.getContext(), "No se encontraron registros con los datos ingresados", Toast.LENGTH_LONG).show();
        }
        offersLV.setAdapter(new ListViewAdapter(view.getContext(), OffersManagement.toSingleRow(this.offers)));
    }

    public void statusModify(boolean status){
        if(status){
            Toast.makeText(view.getContext(), "Oferta modificada", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(view.getContext(), "No se pudo modificar la Oferta\nPara modificar una Oferta esta DEBE existir\nRevise su conexion a internet", Toast.LENGTH_LONG).show();
        }
    }

    public void statusDelete(boolean status){
        if(status){
            Toast.makeText(view.getContext(), "Oferta eliminada", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(view.getContext(), "No se pudo eliminar la Oferta\nPara eliminar una Oferta esta DEBE existir\nRevise su conexion a internet", Toast.LENGTH_LONG).show();
        }
    }

    public void setOnInterface(Oferta o){
        if(o != null){
            mallTV.setText(o.getId().getCentroComercial());
            Image.setImageFromBase64(o.getImagen(), imageIV);
            tenantTV.setText(o.getId().getLocatario());
            shopTV.setText(o.getId().getLocal());
            descriptionET.setText(o.getId().getDescripcion());
            startDateTV.setText(Mysql.formatoFecha.format(o.getInicio()));
            endDateTV.setText(Mysql.formatoFecha.format(o.getExpiracion()));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_offers_management, container, false);
        this.rentedShop = RentedShop.getRentedShop();
        this.user = Profile.getUser();
        if(this.rentedShop != null && this.user != null){
            setInterfaceVariables();
            setListeners();
            mallTV.setText(this.rentedShop.getId().getCentroComercial());
            shopTV.setText(this.rentedShop.getId().getLocal());
            tenantTV.setText(this.user.getCuenta());
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
                setOfferts(responseWebService.toString());
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
            case R.id.startDateTV:
                dateSelector.showDatePickerDialog(view.getContext(), getString(R.string.startDate), startDateD, startDateTV);
                break;
            case R.id.endDateTV:
                dateSelector.showDatePickerDialog(view.getContext(), getString(R.string.endDate), endDateD, endDateTV);
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
        if(this.offers != null && position < this.offers.length){
            setOnInterface(this.offers[position]);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        if(this.offers != null && position < this.offers.length){
            Bundle args = new Bundle();
            args.putSerializable("offer", this.offers[position]);
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
