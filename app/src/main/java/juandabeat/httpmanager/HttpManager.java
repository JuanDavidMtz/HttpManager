package juandabeat.httpmanager;

import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpManager {
    public static String getData(String uri){
        BufferedReader reader=null;
        try {
            URL url=new URL(uri);
            HttpURLConnection conexxion=(HttpURLConnection)url.openConnection();
            StringBuilder stringBuilder=new StringBuilder();
            reader=new BufferedReader(new InputStreamReader(conexxion.getInputStream()));
            String linea;
            while ((linea=reader.readLine())!=null){
                stringBuilder.append(linea+"\n");
            }
            return stringBuilder.toString();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("ERROR URL :",e.toString());
            return null;
        }
        catch (IOException e) {
            e.printStackTrace();
            Log.e("ERROR IOECEPTION :",e.toString());
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
            Log.e("ERROR EXCEPTION :",  e.toString());
            return null;
        }
        finally {
            if(reader != null){
                try {
                    reader.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

    }
}
