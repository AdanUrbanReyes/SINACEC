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
import modelo.basedatos.Producto;
import modelo.basedatos.ProductoId;
import modelo.list_view.ListViewAdapter;
import modelo.list_view.SingleRow;
import utilerias.Image;
import utilerias.JSON;
import utilerias.List;
import utilerias.Mysql;
import utilerias.WebService;

public class ProductsManagement extends Fragment implements ComunicationWebServiceAndroid, View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    private OnFragmentInteractionListener mListener;

    private View view;
    private TextView mallTV, shopTV;
    private ImageView imageIV;
    private EditText nameET, descriptionET;
    private Button saveB, searchB, modifyB, deleteB;
    private ListView productsLV;

    private WebService webService;
    private int cwsc;//call web service code

    private LocalRentado rentedShop;
    private Producto []products;

    private Image imageSelector;

    public ProductsManagement() {
        imageSelector = new Image();
    }

    public static SingleRow[] toSingleRow(Producto[]products){
        SingleRow []sr = new SingleRow[0];
        int i;
        if(products != null){
            sr = new SingleRow[products.length];
            for(i=0; i < products.length;i++){
                sr[i] = new SingleRow(products[i].getImagen(), products[i].getId().getNombre());
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
        shopTV = (TextView) view.findViewById(R.id.shopTV);
        descriptionET = (EditText) view.findViewById(R.id.descriptionET);
        saveB = (Button) view.findViewById(R.id.saveB);
        searchB = (Button) view.findViewById(R.id.searchB);
        modifyB = (Button) view.findViewById(R.id.modifyB);
        deleteB = (Button) view.findViewById(R.id.deleteB);
        productsLV = (ListView) view.findViewById(R.id.productsLV);
        webService = new WebService(view.getContext(), this);
    }

    public void setListeners(){
        imageIV.setOnClickListener(this);
        saveB.setOnClickListener(this);
        searchB.setOnClickListener(this);
        modifyB.setOnClickListener(this);
        deleteB.setOnClickListener(this);
        productsLV.setOnItemClickListener(this);
        productsLV.setOnItemLongClickListener(this);
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

    public Producto readProduct(){
        Producto p = new Producto();
        ProductoId ip = new ProductoId();
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
            webService.callMethodWebService("crear", WebService.pccm, new Object[]{JSON.aJSON(readProduct(), "Producto"), "Producto"});
        }else{
            Toast.makeText(getContext(),"Ingrese los siguientes campos:\n"+ List.joinItems(mf,"\n"), Toast.LENGTH_LONG).show();
        }
    }

    public void search(){
        Producto tp = readProduct();
        tp.setImagen(new byte[0]);
        this.cwsc = 2;
        webService.callMethodWebService("leer", WebService.pcrm, new Object[]{JSON.aJSON(tp, "Producto"), "Producto",null});
    }

    public void modify(){
        LinkedList<String> mf = missingFields();
        if(mf.size() == 0){
            this.cwsc = 3;
            webService.callMethodWebService("actualizar", WebService.pcum, new Object[]{JSON.aJSON(readProduct(), "Producto"), "Producto"});
        }else{
            Toast.makeText(getContext(),"Ingrese los siguientes campos:\n"+ List.joinItems(mf,"\n"), Toast.LENGTH_LONG).show();
        }
    }

    public void delete(){
        LinkedList<String> mf = missingFields();
        if(mf.size() == 0){
            this.cwsc = 4;
            webService.callMethodWebService("eliminar", WebService.pcdm, new Object[]{JSON.aJSON(readProduct(), "Producto"), "Producto"});
        }else{
            Toast.makeText(getContext(),"Ingrese los siguientes campos:\n"+ List.joinItems(mf,"\n"), Toast.LENGTH_LONG).show();
        }
    }

    public void statusSave(boolean status){
        if(status){
            Toast.makeText(view.getContext(), "Producto guardado", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(view.getContext(), "No se pudo guardar el Producto\nPara guardar un Producto este NO debe existir\nRevise su conexion a internet", Toast.LENGTH_LONG).show();
        }
    }

    public void setProducts(String products){
        if(products.startsWith("[")){
            this.products = (Producto[]) JSON.aArrayObjeto(products, "Producto");
        }else{
            Producto t = (Producto) JSON.aObjeto(products, "Producto");
            this.products = (t == null) ? new Producto[0] : new Producto[]{t};
        }
        if(this.products == null || this.products.length == 0){
            Toast.makeText(view.getContext(), "No se encontraron registros con los datos ingresados", Toast.LENGTH_LONG).show();
        }
        productsLV.setAdapter(new ListViewAdapter(view.getContext(), ProductsManagement.toSingleRow(this.products)));
    }

    public void statusModify(boolean status){
        if(status){
            Toast.makeText(view.getContext(), "Producto modificado", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(view.getContext(), "No se pudo modificar el Producto\nPara modificar un Producto este DEBE existir\nRevise su conexion a internet", Toast.LENGTH_LONG).show();
        }
    }

    public void statusDelete(boolean status){
        if(status){
            Toast.makeText(view.getContext(), "Producto eliminado", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(view.getContext(), "No se pudo eliminar el Producto\nPara eliminar un Producto este DEBE existir\nRevise su conexion a internet", Toast.LENGTH_LONG).show();
        }
    }

    public void setOnInterface(Producto p){
        if(p != null){
            mallTV.setText(p.getId().getCentroComercial());
            Image.setImageFromBase64(p.getImagen(), imageIV);
            nameET.setText(p.getId().getNombre());
            shopTV.setText(p.getId().getLocal());
            descriptionET.setText(p.getDescripcion());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_products_management, container, false);
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
                setProducts(responseWebService.toString());
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
        if(this.products != null && position < this.products.length){
            setOnInterface(this.products[position]);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        if(this.products != null && position < this.products.length){
            Bundle args = new Bundle();
            args.putSerializable("product", this.products[position]);
            Fragment product = new Product();
            product.setArguments(args);
            getFragmentManager().beginTransaction().replace(R.id.navigationDrawerRL, product).commit();
        }
        return false;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
