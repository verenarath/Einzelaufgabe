package com.example.einzelaufgabe;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    Button berechnen;
    Button ServerAnfrage;
    EditText Matrikelnummer;
    TextView Ausgabefeld;
    String Antwort;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        berechnen = findViewById(R.id.calculatebutton);
        ServerAnfrage = findViewById(R.id.ServerButton);
        Matrikelnummer = findViewById(R.id.MartrikelnummerEingabe);
        Ausgabefeld = findViewById(R.id.Ausgabe);
        ServerAnfrage.setOnClickListener(view -> {
            serverConnection();

        });


    }


    public void serverConnection (){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket serverSocket = new Socket("se2-isys.aau.at", 53212);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
                    DataOutputStream outputStream = new DataOutputStream(serverSocket.getOutputStream());

                    outputStream.writeBytes(Matrikelnummer.getText().toString()+ '\n');
                    Antwort = bufferedReader.readLine();

                    serverSocket.close();
                    Ausgabefeld.setText(Antwort);

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
