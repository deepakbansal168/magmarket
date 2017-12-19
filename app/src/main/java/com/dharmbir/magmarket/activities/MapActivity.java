package com.dharmbir.magmarket.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.dharmbir.magmarket.R;
import com.dharmbir.magmarket.support.ChangeDateFormat;
import com.dharmbir.magmarket.support.Constant;
import com.dharmbir.magmarket.support.LocationService;
import com.dharmbir.magmarket.support.MultipartUtility;
import com.dharmbir.magmarket.support.Utils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import im.delight.android.location.SimpleLocation;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {


    String deviceID;
    SupportMapFragment supportMapFragment;
    double Loc_Lat, Loc_Lang;
    GoogleMap gMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;


    Button back;

    ProgressDialog progressDialog;
    private double currentLatitude;
    private double currentLongitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(MapActivity.this);
        //progressBar.setVisibility(View.VISIBLE);


        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();


        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Now lets connect to the API
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(this.getClass().getSimpleName(), "onPause()");

        //Disconnect from API onPause()
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }


    }

    /**
     * If connected get lat and long
     *
     */
    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        if (location != null) {

            //If everything went fine lets get latitude and longitude
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
            Log.i("location", currentLatitude + " WORKS " + currentLongitude + "");
            displaylocation();
            // Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();

        }
    }


    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
            /*
             * Google Play services can resolve some errors it detects.
             * If the error has a resolution, try sending an Intent to
             * start a Google Play services activity that can resolve
             * error.
             */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                    /*
                     * Thrown if Google Play services canceled the original
                     * PendingIntent
                     */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
                /*
                 * If no resolution is available, display a dialog to the
                 * user with the error.
                 */
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    /**
     * If locationChanges change lat and long
     *
     *
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
        displaylocation();
        //Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
    }


    //region Action

    public void actionLog(View v) {
        uploadLogsOnServer();
    }

    //endregion


    public void uploadLogsOnServer() {

        if (progressDialog == null)
            progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Uploading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        new UploadImage().execute();
        //  new MyAsyncTask().execute();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        gMap = googleMap;
        displaylocation();
        //progressBar.setVisibility(View.GONE);
    }

    void displaylocation() {
        LatLng location = new LatLng(currentLatitude, currentLongitude);
        gMap.clear();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        gMap.setMyLocationEnabled(true);
        gMap.getUiSettings().setZoomControlsEnabled(true);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,13));

//        gMap.addMarker(new MarkerOptions()
//                .title("User")
//                .position(location));

    }

    private class UploadImage extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // progressBar.setVisibility(View.VISIBLE);
//            pd=new ProgressDialog(Addphotovideo.this);
//            pd.setMessage("Please Wait...");
//            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String response = uploadImage();
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
//            Log.e("server response", response);
        //    progressBar.setVisibility(View.GONE);
            //pd.dismiss();
            progressDialog.dismiss();
            Toast.makeText(MapActivity.this, "File Uploaded!", Toast.LENGTH_LONG).show();
            File myDirectory = new File(Environment.getExternalStorageDirectory(), "MagMarket");
            File file=new File(myDirectory.getAbsolutePath()+"/"+"locationfile.txt");
            if(myDirectory.exists()){
              //  Toast.makeText(MapActivity.this, "Exist1", Toast.LENGTH_SHORT).show();
                if (myDirectory.delete()) {
               //     Toast.makeText(MapActivity.this, "File Deleted", Toast.LENGTH_SHORT).show();
                } else {
               //     Toast.makeText(MapActivity.this, "File Not Deleted", Toast.LENGTH_SHORT).show();                }
                }
            } else {
               // Toast.makeText(MapActivity.this, "File Does not Exist", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }
    private String uploadImage() {
        Looper.prepare();

         //String url = AppConstants.addtrendz_url;
        File myDirectory = new File(Environment.getExternalStorageDirectory(), "MagMarket");
        File sourceFile=new File(myDirectory.getAbsolutePath()+"/"+"locationfile.txt");
        Log.e("filepath", "not a file"+sourceFile.getAbsolutePath().trim());

        Log.d("tag", sourceFile.getAbsolutePath().toString());
        if(sourceFile.isFile()){
          //  chosefile ="yes";
            Log.e("tag", "is file");
        }else {
            Log.e("tag", "not a file");
        }
        MultipartEntity multiPart = new MultipartEntity();


        try {
            // sending parameters
            //   multiPart.addPart("tag", new StringBody("add_content"));
//            multiPart.addPart("user_id", new StringBody(user_id));
//            Log.e("tag", "user_id: "+user_id);
//            multiPart.addPart("access_token", new StringBody(acces_token));
//            Log.e("tag", "acces_token: "+acces_token);
//            multiPart.addPart("caption", new StringBody(txtcaption.getText().toString().trim()));
//            Log.e("tag", "caption: "+txtcaption.getText().toString().trim());

                multiPart.addPart("userfile", new FileBody(sourceFile));
              //  multiPart.addPart("thumbnail", new FileBody(convertBitmapIntoFile(thumbnail, null)));

            deviceID    = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID) ;

            String url  = Constant.LOG_URL + deviceID;

            String response = multipost(url, multiPart);
            return response;
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Looper.loop();

        return null;
    }

    private static String multipost(String urlString, MultipartEntity reqEntity) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestProperty("Connection", "keep-alive");
            conn.addRequestProperty("Content-length", reqEntity.getContentLength()+"");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.addRequestProperty(reqEntity.getContentType().getName(), reqEntity.getContentType().getValue());

            OutputStream os = conn.getOutputStream();
            reqEntity.writeTo(conn.getOutputStream());
            os.close();
            conn.connect();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return readStream(conn.getInputStream());
            }
            else{
                Log.e("tag", "cannot read stream");
            }
        }
        catch (Exception e) {
            Log.e("Uploading", "multipart post error " + e + "(" + urlString + ")");
        }
        return null;
    }

    private static String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }


//    class MyAsyncTask extends AsyncTask<Void, Integer, String> {
//        @Override
//        protected String doInBackground(Void... params) {
//
//            String result = null;
//
//            try {
//                result = fetchDataFromServer();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            return result;
//        }
//
//        protected void onPostExecute(String result) {
//            progressDialog.hide();
//            if (result != null && result.length() != 0) {
//                try {
//
//                    if (result.contains("Uploaded!")) {
//
//                        Utils.writeToFile("", MapActivity.this);
//
//                        String successMsg = "Log updated successfully";
//
//                        Toast.makeText(MapActivity.this, successMsg, Toast.LENGTH_LONG).show();
//                        finish();
//
//                    }else{
//                        Toast.makeText(MapActivity.this, "Sorry! Image not uploaded.", Toast.LENGTH_LONG).show();
//                    }
//
//                } catch (Exception var3) {
//                    Toast.makeText(MapActivity.this, "Server not responding, Please trg again.", Toast.LENGTH_LONG).show();
//                    var3.printStackTrace();
//                }
//            }else{
//                Toast.makeText(MapActivity.this, "Sorry! Image not uploaded.", Toast.LENGTH_LONG).show();
//            }
//            File myDirectory = new File(Environment.getExternalStorageDirectory(), "MagMarket");
//            File file=new File(myDirectory.getAbsolutePath()+"/"+"locationfile.txt");
//            if(file.exists()){
//                Toast.makeText(MapActivity.this, "Exist1", Toast.LENGTH_SHORT).show();
//                if (file.delete()) {
//                    Toast.makeText(MapActivity.this, "File Deleted", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(MapActivity.this, "File Not Deleted", Toast.LENGTH_SHORT).show();                }
//            } else {
//                Toast.makeText(MapActivity.this, "File Does not Exist", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        protected void onProgressUpdate(Integer... progress){
//        }
//
//        public String fetchDataFromServer() throws IOException, JSONException {
//
//            String url = Constant.LOG_URL+deviceID;
//
//            String text = Utils.readFromFile(MapActivity.this);
//            byte[] bytes = text.getBytes();
//
//            MultipartUtility ws   = new MultipartUtility(url, "UTF-8", "");
//            ws.addFilePart("userfile", "location.txt", bytes);
//            List<String> responseString = ws.finish();
//
//            return responseString.toString();
//        }
//    }

    //endregion





}
