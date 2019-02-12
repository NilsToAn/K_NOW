package com.studis.nife.k_now;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


/**
 * Created by nils on 13.04.17.
 */

public class ServerCommunication extends IntentService{
    String[][] befehle;
    String result, link;
    public static Handler antwortHandler;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ServerCommunication(String name) {
        super(name);
    }
    public ServerCommunication(){
        super("ServerCommunication");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try{
            Bundle b = intent.getExtras();
            befehle = (String[][])b.getSerializable("befehle");
            link = (String)b.getSerializable("link");

            //Abschnitt vorverschoben Fehler ?
            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            //war hinter der try bedingung
            try {
                String data = URLEncoder.encode(befehle[0][0], "UTF-8") + "=" + URLEncoder.encode(befehle[0][1], "UTF-8");
                ;
                int i = 1;
                while (i < befehle.length) {
                    data += "&" + URLEncoder.encode(befehle[i][0], "UTF-8") + "=" + URLEncoder.encode(befehle[i][1], "UTF-8");
                    i++;
                }
                wr.write(data);
                wr.flush();
            }catch (Exception e){

            }






            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            //Read Server Response
            while((line = reader.readLine()) != null){
                sb.append(line);
                break;
            }
            result = sb.toString();

        }catch (Exception e){
            result = new String("Exception: "+ e.getMessage());

        }

        if(antwortHandler != null) {
            Message msg = Message.obtain();
            msg.obj = result;
            antwortHandler.sendMessage(msg);
        }
    }
}
