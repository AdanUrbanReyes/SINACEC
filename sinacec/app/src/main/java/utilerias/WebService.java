package utilerias;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import modelo.ComunicationWebServiceAndroid;

/**
 * Created by ayan on 9/29/16.
 */
public class WebService implements Runnable{
    public static final String[] pccm = new String []{"json","clase"};//parameters call create method
    public static final String[] pcrm = new String []{"json","clase","proyeccion"};//parameters call read method
    public static final String[] pcum = new String []{"json","clase"};//parameters call update method
    public static final String[] pcdm = new String []{"json","clase"};//parameters call delete method
    public static final String[] pclpsm = new String []{"procedimiento","parametros","clase"};//parameters call llamar procedimiento select method
    public static final String[] pclpum = new String []{"procedimiento","parametros"};//parameters call llamar procedimiento update method

    private static String HOST_WEB_SERVICE;
    private static int PORT_WEB_SERVICE;
    private static String PATH_WEB_SERVICE;
    private static String URL_WEB_SERVICE;
    private static String TARGET_NAMESPACE;

    public static void setSettings(String host, int port, String path, String namespace){
        WebService.HOST_WEB_SERVICE = host;
        WebService.PORT_WEB_SERVICE = port;
        WebService.PATH_WEB_SERVICE = path;
        WebService.URL_WEB_SERVICE = "http://"+HOST_WEB_SERVICE+":"+PORT_WEB_SERVICE+PATH_WEB_SERVICE;
        WebService.TARGET_NAMESPACE = namespace;
    }

    private ComunicationWebServiceAndroid cwsa;
    private Handler handler;
    private ProgressDialog progressDialog;
    private String method;
    private String[] parameters;
    private Object[] values;

    public static ProgressDialog getProgressDialog(String message, String title,Context context){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setTitle(title);
        return progressDialog;
    }

    public WebService(Context context, ComunicationWebServiceAndroid cwsa){
        handler = new Handler();
        progressDialog = WebService.getProgressDialog("Obteniendo informacion","Web Service",context);
        this.cwsa = cwsa;
    }

    public void callMethodWebService(String method,String []parameters, Object []values){
        this.method = method;
        this.parameters = parameters;
        this.values = values;
        progressDialog.show();
        new Thread(this).start();
    }

    public Object callMethodWebService() throws IOException, XmlPullParserException {
        if(method == null || method == "" || parameters ==  values  || parameters.length != values.length){
            return null;
        }
        int i;
        SoapObject soapObject = new SoapObject(TARGET_NAMESPACE,method);
        for(i=0; i < values.length; i++){
            soapObject.addProperty(parameters[i],values[i]);
        }
        SoapSerializationEnvelope soapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapSerializationEnvelope.setOutputSoapObject(soapObject);
        HttpTransportSE httpTransportSE = new HttpTransportSE(URL_WEB_SERVICE);
        httpTransportSE.call(method,soapSerializationEnvelope);
        return soapSerializationEnvelope.getResponse();
    }

    @Override
    public void run() {
        final Object response;
        try {
            response = this.callMethodWebService();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    cwsa.manageResponse(response);
                }
            });
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }finally {
            progressDialog.dismiss();
        }
    }
}
