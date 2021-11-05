package com.example.lab11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.validator.routines.InetAddressValidator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button button;

    EditText e1;
    EditText e2;
    EditText e3;
    EditText e4;

    InetAddressValidator inetAddressValidator = new InetAddressValidator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);

        e1 = findViewById(R.id.ip1);
        e2 = findViewById(R.id.ip2);
        e3 = findViewById(R.id.ip3);
        e4 = findViewById(R.id.ip4);

        View view = getCurrentFocus();

        button.setOnClickListener(v -> {
            buttonPress(view);
        });
    }

    public void buttonPress(View view){  getIPInfo(); }

    private String getIP() {

        String ip = e1.getText().toString() + "." + e2.getText().toString() + "." +
                e3.getText().toString() + "." + e4.getText().toString();
        checkIfAddressIPIsCorrect(ip);
        Log.i("-->",ip);
        return ip;
    }
    private void printInfo(IPInfo info) {
        TextView textView = findViewById(R.id.textView);
        String s;
        if(info == null) s = "Faild";
        else {
            s = "ip: " + info.getIp() + "\n" +
                    "hostname: " + info.getHostname() + "\n" +
                    "city: " + info.getCity() + "\n" +
                    "region: " + info.getRegion() + "\n" +
                    "loc: " + info.getLoc() + "\n" +
                    "org: " + info.getOrg() + "\n" +
                    "postal: " + info.getPostal() + "\n" +
                    "timezone: " + info.getTimezone();
        }
        textView.setText(s);
    }
    private void getIPInfo(){
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class, getIP());
        Call<IPInfo> call = apiInterface.getIPInfo();
        call.enqueue(new Callback<IPInfo>() {
            @Override
            public void onResponse(Call<IPInfo> call, Response<IPInfo> response) {
                printInfo(response.body());
            }
            @Override
            public void onFailure(Call<IPInfo> call, Throwable t) {
                printInfo(null);
            }
        });
    }

    private void checkIfAddressIPIsCorrect(String ip) {

        if(!inetAddressValidator.isValid(ip)) {
            Toast.makeText(this, "IP address is incorrect!", Toast.LENGTH_LONG).show();
            e4.setError("Wrong ip address");
            return;
        }
    }
}