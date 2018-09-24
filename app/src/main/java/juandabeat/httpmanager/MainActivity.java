package juandabeat.httpmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button boton;
    TextView textView;
    ProgressBar progressBar;
    List<MyTask> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boton=(Button) findViewById(R.id.boton);
        textView=(TextView) findViewById(R.id.textView);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        textView.setMovementMethod(new ScrollingMovementMethod());

        taskList= new ArrayList<>();

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*for (int i=1; i<=100; i++){
                    cargarDatos("Numero :"+i);
                }
                MyTask tarea=new MyTask();
                tarea.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);*/
                if(isOnLine()){
                    pedirDatos("http://maloschistes.com/maloschistes.com/jose/usuarios.xml");
                }else {
                    Toast.makeText(getApplicationContext(),"Sin conexion",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void cargarDatos(String datos){
        textView.append(datos +"\n");
    }
    public boolean isOnLine(){
        ConnectivityManager connectivityManager=(ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnectedOrConnecting()){
            return true;
        }
        return false;
    }
    public void pedirDatos(String uri){
        MyTask tarea=new MyTask();
        tarea.execute(uri);
    }

    private class MyTask extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cargarDatos("Inicio de Carga");
            if (taskList.size()==0){
                progressBar.setVisibility(View.VISIBLE);
            }
            taskList.add(this);
        }
        @Override
        protected String doInBackground(String... strings) {
            for (int i=1; i<=10; i++){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress("Numero :"+i);
            }
            return "Terminamos";
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            cargarDatos(s);
            taskList.remove(this);
            if (taskList.size()==0){
                progressBar.setVisibility(View.INVISIBLE);
            }

        }
        @Override
        protected void onProgressUpdate(String... values) {
            cargarDatos(values[0]);

        }
    }
}