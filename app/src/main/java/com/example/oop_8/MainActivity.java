package com.example.oop_8;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    BottleDispenser bd;
    Context context;
    SeekBar seekBar;
    Spinner bottleSpinner, sizeSpinner;
    String lastBought = "";
    TextView text, money, moneyToAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        text = findViewById(R.id.textField);
        money = findViewById(R.id.currentMoney);
        moneyToAdd = findViewById(R.id.moneyToAdd);
        seekBar = findViewById(R.id.seekBar);
        bottleSpinner = findViewById(R.id.bottleSpinner);
        sizeSpinner = findViewById(R.id.sizeSpinner);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float amount = ((float) seekBar.getProgress()) / 10;
                String moneyStr = String.format("%.2f", amount);
                moneyToAdd.setText("Money to add: " + moneyStr);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        ArrayAdapter<String> bottleAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item);
        bottleAdapter.addAll("Pepsi", "Pepsi Max", "Coca-Cola", "Coca-Cola Zero", "Fanta", "Fanta Zero");
        bottleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bottleSpinner.setAdapter(bottleAdapter);
        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item);
        sizeAdapter.addAll("0.33 l", "0.5 l", "1.0 l", "1.5 l");
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeAdapter);
        bd = BottleDispenser.getInstance();
    }

    public void addMoney(View v) {
        int progress;
        float amount;

        if ((progress = seekBar.getProgress()) > 0) {
            amount = (float) progress;
            bd.addMoney(amount/10, text, money);
            seekBar.setProgress(0);
        }
    }

    public void buyBottle(View v) {
        String name = (String) bottleSpinner.getItemAtPosition(bottleSpinner.getSelectedItemPosition());
        String sizeStr = ((String) sizeSpinner.getItemAtPosition(sizeSpinner.getSelectedItemPosition())).split(" ")[0];
        float size = Float.parseFloat(sizeStr);
        int idx;
        String info;

        if ((idx = bd.getBottleIdx(name, size)) < 0) {
            text.setText("No such bottle in dispenser!");
        } else {
            info = bd.buyBottle(idx, text, money);
            if (info != null) lastBought = info;
        }
    }

    public void printReceipt(View v) {
        try {
            OutputStreamWriter ows = new OutputStreamWriter(context.openFileOutput("receipt.txt", Context.MODE_PRIVATE));
            ows.write("RECEIPT\n\n" + lastBought);
            ows.close();
        } catch (IOException e) {
            Log.e("IOException", "Couldn't save receipt");
            new AlertDialog.Builder(context)
                    .setTitle("Error")
                    .setMessage("Couldn't save receipt")
                    .setPositiveButton(android.R.string.yes, null)
                    .show();
        }
    }

    public void showReceipt(View v) {
        try {
            InputStream in = context.openFileInput("receipt.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String s;
            StringBuilder sb = new StringBuilder();

            while ((s = br.readLine()) != null) {
                sb.append(s);
                sb.append("\n");
            }

            sb.setLength(sb.length() - 1); // remove the last (unneeded) newline
            text.setText(sb.toString());
            br.close();
            in.close();
        } catch (IOException e) {
            Log.e("IOException", "Couldn't load receipt");
            new AlertDialog.Builder(context)
                    .setTitle("Error")
                    .setMessage("Couldn't load receipt")
                    .setPositiveButton(android.R.string.yes, null)
                    .show();
        }
    }

    public void returnMoney(View v) {
        bd.returnMoney(text, money);
    }

    public void listBottles(View v) {
        bd.listBottles(text);
    }
}
