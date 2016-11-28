package utilerias;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ayan on 10/4/16.
 */
public class Image implements DialogInterface.OnClickListener{
    private static final CharSequence []optionsLoadImage = {"Tomar foto", "Elejir existente", "Cancelar"};
    private static final int SELECT_FILE = 1;
    private static final int REQUEST_CAMERA = 0;

    private String optionSelectedToLoadImage;
    private Fragment fragment;
    private ImageView imageIV;

    public static byte[] imageToBase64(ImageView image){
        image.buildDrawingCache();
        Bitmap bm = image.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        return ("data:image/JPEG;base64," + Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)).getBytes();
    }

    public static void setImageFromBase64(byte []base64, ImageView imageIV){
        if(base64 == null){
            return ;
        }
        String base64S = new String(base64);
        String []information = base64S.split(",");
        if(information != null && information.length == 2){
            byte[] image = Base64.decode(information[1],Base64.DEFAULT);
            imageIV.setImageBitmap(BitmapFactory.decodeByteArray(image,0,image.length));
        }else{
            ;//set default image
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(this.fragment.getContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        imageIV.setImageBitmap(bm);
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageIV.setImageBitmap(thumbnail);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fragment.startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        fragment.startActivityForResult(Intent.createChooser(intent, "Selecciona Imagen"),SELECT_FILE);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Storage.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    switch(optionSelectedToLoadImage){
                        case "Tomar Foto":
                            cameraIntent();
                            break;
                        case "Elejir existente":
                            galleryIntent();
                            break;
                    }
                } else {
                    Toast.makeText(fragment.getContext(),"Acceso denegado para cargar imagen",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    public void selectImage(Fragment fragment, ImageView imageIV, String title){
        this.imageIV = imageIV;
        this.fragment = fragment;
        AlertDialog.Builder builder = new AlertDialog.Builder(this.fragment.getContext());
        builder.setTitle(title);
        builder.setItems(optionsLoadImage,this);
        builder.show();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int item) {
        boolean result= Storage.checkPermission(this.fragment.getContext());
        optionSelectedToLoadImage = optionsLoadImage[item].toString();
        switch (optionSelectedToLoadImage) {
            case "Tomar foto":
                if(result){
                    cameraIntent();
                }
                break;
            case "Elejir existente":
                if(result){
                    galleryIntent();
                }
                break;
            case "Cancelar":
                dialogInterface.dismiss();
                break;
        }
    }

}
