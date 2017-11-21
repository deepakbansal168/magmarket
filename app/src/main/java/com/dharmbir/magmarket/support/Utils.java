package com.dharmbir.magmarket.support;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.dharmbir.magmarket.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.logging.Logger;

/**
 * Created by arthonsystechnologiesllp on 22/10/16.
 */
public class Utils {

    private static ProgressDialog pDialog;
    private static ConnectionDetector cd;
    private static Context mContext;

    public static void hideTransparentProgressDialog() {

        try {
            if (pDialog.isShowing())
                pDialog.dismiss();
        } catch (Exception exception) {

        }
    }

    public static void showTransparentDialog(Context mContext) {

        if (pDialog != null)
            pDialog.dismiss();

        pDialog = new ProgressDialog(mContext);
        try {

            if (!pDialog.isShowing()) {
                pDialog.show();
            }

        } catch (WindowManager.BadTokenException e) {

        }
        pDialog.setCancelable(false);
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        pDialog.setContentView(R.layout.progress_dialog);

    }


    //endregion

    //region Internet Connection
    public static boolean isInternetAvaliable(){

        cd = new ConnectionDetector(getCurrentActivityContext());

        Boolean isInternetPresent = cd.isConnectingToInternet();

        if (isInternetPresent)
            return true;

        else
            Toast.makeText(getCurrentActivityContext(), "You don't have internet connection", Toast.LENGTH_SHORT).show();

            return false;

    }
    //endregion

    //region Set current Activity

    public static void setCurrentActvityContext(Context context) {
//        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(context));
        mContext = context;
    }

    public static Context getCurrentActivityContext() {
        return mContext;
    }
    //endregion

    //region Hide Keyboard

    public static void hideSoftKeyboard(Context context) {

        Activity activity = (Activity) context;
        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    //endregion

    //region Post Exception
    public static void postExceptionOnServer(JSONObject request){



    }
    //endregion

    //region Show Toast

    public static void showToast(String msg) {
        Toast.makeText(getCurrentActivityContext(), msg, Toast.LENGTH_SHORT).show();
    }

    //endregion


    public static void writeStringAsFile(final String fileContents, Context context) {

        try {
            FileWriter out = new FileWriter(new File(context.getFilesDir(), "location.txt"));
            out.write(fileContents);
            out.append("\n\r");
            out.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public static String readFileAsString(Context context) {

        String fileName = "location.txt";

        StringBuilder stringBuilder = new StringBuilder();
        String line;
        BufferedReader in = null;

        try {
            in = new BufferedReader(new FileReader(new File(context.getFilesDir(), fileName)));
            while ((line = in.readLine()) != null){
                stringBuilder.append(line);
            }

        } catch (FileNotFoundException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }

        return stringBuilder.toString();
    }

    //region Read & Write File
    public static void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("location.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
//            outputStreamWriter.append("\n\r");
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public static String readFromFile(Context context) {

        String ret = "";
        File myDirectory = new File(Environment.getExternalStorageDirectory(), "MagMarket");
        File newfile=new File(myDirectory.getAbsolutePath()+"/"+"locationfile.txt");

        try {
            InputStream inputStream = context.openFileInput(newfile.getAbsolutePath());

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader       = new BufferedReader(inputStreamReader);
                String receiveString                = "";
                StringBuilder stringBuilder         = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString + " \n");
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
    //endregion

    //region Home Content
    public static void writeHomeContent(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("home.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    public static String readHomeContent(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("home.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader       = new BufferedReader(inputStreamReader);
                String receiveString                = "";
                StringBuilder stringBuilder         = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
    //endregion

}
