package com.dharmbir.magmarket.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.dharmbir.magmarket.R;

import com.dharmbir.magmarket.support.Constant;
import com.dharmbir.magmarket.support.LocationService;
import com.dharmbir.magmarket.support.MultipartUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    ProgressDialog progressDialog;
    String deviceID;
    String imgName;
    String imageFilePath;
    int cameraCaptures=0;

    Uri imageUri;
    boolean fromTwoCaptures=false;
    Bitmap picBitmap;
    public static int MY_PERMISSIONS_REQUEST_LOCATION=1000;
    public static int MY_PERMISSIONS_REQUEST_STORAGE=2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadWebView();

       checkpremission("location");




    }

    void checkpremission(String which){
        // Here, thisActivity is the current activity
        if(which.equals("location")){
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION);
                }
            }
        }else if(which.equals("storage")){
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                } else {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_STORAGE);
                }
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        if(requestCode==MY_PERMISSIONS_REQUEST_LOCATION){
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkpremission("storage");
            } else {
                checkpremission("storage");
            }
            return;
        }else if(requestCode==MY_PERMISSIONS_REQUEST_STORAGE){
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent serviceIntent = new Intent(this, LocationService.class);
            startService(serviceIntent);
            File myDirectory = new File(Environment.getExternalStorageDirectory(), "MagMarket");

            if(!myDirectory.exists()) {
                myDirectory.mkdirs();
            }
            } else {
                checkpremission("location");
            }
            return;
        }
    }

    public void loadWebView(){

        imgName     = String.valueOf(System.currentTimeMillis());
        deviceID    = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID) ;

        String url  = Constant.URL + deviceID;
        WebView wv  = (WebView) findViewById(R.id.web_view);

        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
       // wv.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        wv.loadUrl(url);

        wv.setWebViewClient(new WebViewClient() {

            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                if (progressDialog == null) {
                    // in standard case YourActivity.this
                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }
            }

            /*
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                final Uri uri = request.getUrl();
                return true;
            }*/

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                final Uri uri = Uri.parse(url);
                String scheme = uri.getScheme();

                if (scheme.equalsIgnoreCase("http")){

                    if (uri.getHost().equalsIgnoreCase("magmarket.mobi")){

                        http://magmarket.mobi/index.php?Camera=1&deviceid=
//                        if (url.equalsIgnoreCase("http://magmarket.mobi/index.php#Camera=1&deviceid="+deviceID)) {
                        if (url.equalsIgnoreCase("http://magmarket.mobi/index.php?Camera=1&deviceid="+deviceID)) {
                            hideProgressDialog();

//                            imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "fname_" +
//                                    String.valueOf(System.currentTimeMillis()) + ".jpg"));

                            imgName = "file"+String.valueOf(System.currentTimeMillis())+ ".jpg";

//                            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                                    new ContentValues());

//                            imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"tmp_avatar_" + imgName + ".jpg"));


                            Intent it = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(it, 1);

//                            imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + imgName;
//                            File imageFile       = new File(imageFilePath);
//                            imageUri             = Uri.fromFile(imageFile);
//
//
//                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                            startActivityForResult(intent, 1);

                        }else if (url.equalsIgnoreCase("http://magmarket.mobi/index.php?Camera=2&deviceid="+deviceID)) {

//                            hideProgressDialog();
//
//                            imgName = "file"+ imgName + ".jpg";
//
//                            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                            startActivityForResult(intent, 2);
                            Intent it = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(it, 1);
                            fromTwoCaptures=true;

                        }
                    }else{
                        //Toast.makeText(MainActivity.this, "ELSE magmarket.mobi", Toast.LENGTH_LONG).show();
                    }
                }

                return true;
            }


            public void onPageFinished(WebView view, String url) {
                hideProgressDialog();
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                hideProgressDialog();
            }
        });
    }

    public void hideProgressDialog(){
        try {

            if (progressDialog .isShowing()) {

                progressDialog .dismiss();

                progressDialog = null;

            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public boolean hasImageCaptureBug() {

        // list of known devices that have the bug
        ArrayList<String> devices = new ArrayList<String>();
        devices.add("android-devphone1/dream_devphone/dream");
        devices.add("generic/sdk/generic");
        devices.add("vodafone/vfpioneer/sapphire");
        devices.add("tmobile/kila/dream");
        devices.add("verizon/voles/sholes");
        devices.add("google_ion/google_ion/sapphire");

        return devices.contains(android.os.Build.BRAND + "/" + android.os.Build.PRODUCT + "/"
                + android.os.Build.DEVICE);

    }

    //TODO--
    //region ActivityResult
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            try {
                if (requestCode == 1) { // Take Photo

                    Bundle extras = data.getExtras();
                    // Get the returned image from extra
                    Bitmap bmp = (Bitmap) extras.get("data");

                    picBitmap = bmp;

                    if (picBitmap != null){
                        String successMsg = "By uploading this image and proceeding you certify that you have place the magnet"
                                + "on the vehicle in accordance with the Mag Markets terms of service. Do you agree?";
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        //Yes button clicked
                                        uploadImageOnServer();
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //No button clicked
                                        Toast.makeText(MainActivity.this, "You need to accept our terms first. Please" +
                                                "try again.", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage(successMsg).setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener).show();


//                        new Timer().schedule(new TimerTask() {
//                            @Override
//                            public void run() {
//                                callMethodAfterDelay();
//                            }
//                        }, 1000);
                    }else{
                        Toast.makeText(MainActivity.this, "Bit map is null", Toast.LENGTH_LONG).show();
                    }

                } else if (requestCode == 2) {// Choose Photo



//                    Uri selectedImage = data.getData();
//                    String[] filePath = {MediaStore.Images.Media.DATA};
//
//
//                    Bitmap thumbnail = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
//
//                    picBitmap = thumbnail;
//
//                    if (picBitmap != null){
//
//                        new Timer().schedule(new TimerTask() {
//                            @Override
//                            public void run() {
//                                callMethodAfterDelay();
//                            }
//                        }, 1000);
//                    }
//
                }else{
                }
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "EXCEPTION -> " + e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }else{
//            Toast.makeText(MainActivity.this, "ELSE RESULT OK", Toast.LENGTH_LONG).show();
        }
    }
    //endregion

    //region Upload Images

    public void moveToNextActivity(){

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        startActivity(new Intent(MainActivity.this, MapActivity.class));

                    }
                });
            }
        }, 1000);
    }

    public void callMethodAfterDelay(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                uploadImageOnServer();
            }
        });
    }

    public void uploadImageOnServer(){

        if (progressDialog == null)
            progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

//        Toast.makeText(MainActivity.this, "Start Uploading", Toast.LENGTH_SHORT).show();

        new MyAsyncTask().execute();
    }

    class MyAsyncTask extends AsyncTask<Void, Integer, String> {
        @Override
        protected String doInBackground(Void... params) {

            String result = null;

            try {
                result = fetchDataFromServer();
            }catch (Exception e){
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String result) {

//            Toast.makeText(MainActivity.this, "onPostExecute -> "+ result, Toast.LENGTH_LONG).show();

            progressDialog.hide();
            if (result != null && result.length() != 0) {
                try {
                    if (result.contains("Uploaded!")) {


                        cameraCaptures++;

                        if(!fromTwoCaptures){
                            moveToNextActivity();
                        } else {
                            if(cameraCaptures==2){
                                moveToNextActivity();
                                Toast.makeText(MainActivity.this, "cap: "+cameraCaptures, Toast.LENGTH_SHORT).show();
                                cameraCaptures=0;
                            } else {
                                Intent it = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(it, 1);
                            }
                        }



                    }else{
                        Toast.makeText(MainActivity.this, "Sorry! Image not uploaded.", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception var3) {
                    Toast.makeText(MainActivity.this, "Server not responding, Please trg again.", Toast.LENGTH_LONG).show();
                    var3.printStackTrace();
                }
            }else{
                Toast.makeText(MainActivity.this, "Sorry! Image not uploaded.", Toast.LENGTH_LONG).show();
            }
        }

        protected void onProgressUpdate(Integer... progress){
        }

        public String fetchDataFromServer() throws IOException, JSONException {

//            Toast.makeText(MainActivity.this, "FetchResponse", Toast.LENGTH_LONG).show();

            String url = Constant.IMAGE_URL+deviceID;

            byte[] byteArray = new byte[0];
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            picBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            byteArray = stream.toByteArray();

            MultipartUtility ws   = new MultipartUtility(url, "UTF-8", "");
            ws.addFilePart("userfile", imgName, byteArray);
            List<String> responseString = ws.finish();

            final String str = responseString.toString();

            return responseString.toString();
        }
    }

    //endregion
}




