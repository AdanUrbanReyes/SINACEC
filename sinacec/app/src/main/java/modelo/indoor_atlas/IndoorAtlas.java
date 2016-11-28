package modelo.indoor_atlas;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.indooratlas.android.sdk.IALocation;
import com.indooratlas.android.sdk.IALocationListener;
import com.indooratlas.android.sdk.IALocationManager;
import com.indooratlas.android.sdk.IALocationRequest;

/**
 * Created by ayan on 11/23/16.
 */
public class IndoorAtlas implements IALocationListener{
    private IALocationManager iaLocationManager;
    private Context context;
    private EditText latitudET, longitudET;

    public IndoorAtlas(Context context, EditText latitudET, EditText longitudET){
        this.context = context;
        this.latitudET = latitudET;
        this.longitudET = longitudET;
        this.iaLocationManager = IALocationManager.create(this.context);
    }

    public void location(boolean turn){
        if(turn){
            this.iaLocationManager.requestLocationUpdates(IALocationRequest.create(), this);
        }else{
            iaLocationManager.removeLocationUpdates(this);
        }
    }

    public void onDestroy() {
        iaLocationManager.destroy();
    }

    @Override
    public void onLocationChanged(IALocation iaLocation) {
        this.latitudET.setText(""+iaLocation.getLatitude());
        this.longitudET.setText(""+iaLocation.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch (status) {
            case IALocationManager.STATUS_CALIBRATION_CHANGED:
                String quality = "unknown";
                switch (extras.getInt("quality")) {
                    case IALocationManager.CALIBRATION_POOR:
                        quality = "Poor";
                        break;
                    case IALocationManager.CALIBRATION_GOOD:
                        quality = "Good";
                        break;
                    case IALocationManager.CALIBRATION_EXCELLENT:
                        quality = "Excellent";
                        break;
                }
                Toast.makeText(this.context,"Calibration change. Quality: " + quality, Toast.LENGTH_LONG).show();
                break;
            case IALocationManager.STATUS_AVAILABLE:
                Toast.makeText(this.context,"onStatusChanged: Available", Toast.LENGTH_LONG).show();
                break;
            case IALocationManager.STATUS_LIMITED:
                Toast.makeText(this.context,"onStatusChanged: Limited", Toast.LENGTH_LONG).show();
                break;
            case IALocationManager.STATUS_OUT_OF_SERVICE:
                Toast.makeText(this.context,"onStatusChanged: Out of service", Toast.LENGTH_LONG).show();
                break;
            case IALocationManager.STATUS_TEMPORARILY_UNAVAILABLE:
                Toast.makeText(this.context,"onStatusChanged: Temporarily unavailable",Toast.LENGTH_LONG).show();
                break;
        }
    }
}
