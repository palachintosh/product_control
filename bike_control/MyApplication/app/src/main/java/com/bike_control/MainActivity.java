package com.bike_control;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    String[] warehouses = {"", "SHOP", "X", "Y"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner fromSpinner = (Spinner) findViewById(R.id.fromSpinner);
        Spinner toSpinner = (Spinner) findViewById(R.id.toSpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String> (
                this, android.R.layout.simple_spinner_item, warehouses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);
    }

    public void scanCode(View view) {
        Intent intent = new Intent(MainActivity.this, Scan.class);
        Spinner get_w_from = (Spinner) findViewById(R.id.fromSpinner);
        Spinner get_w_to = (Spinner) findViewById(R.id.toSpinner);
        EditText editText = (EditText) findViewById(R.id.prodctQuantity);
        String quantity_tt = editText.getText().toString();

        RequestData requestData = new RequestData(
                get_w_from.getSelectedItem().toString(),
                get_w_to.getSelectedItem().toString(),
                quantity_tt);
        intent.putExtra(RequestData.class.getSimpleName(), requestData);
        startActivity(intent);

    }

    public void  enterCode(View view) {
        Intent intent = new Intent(MainActivity.this, EnterCode.class);
        Spinner get_w_from = (Spinner) findViewById(R.id.fromSpinner);
        Spinner get_w_to = (Spinner) findViewById(R.id.toSpinner);
        EditText editText = (EditText) findViewById(R.id.prodctQuantity);
        String quantity_tt = editText.getText().toString();

        RequestData requestData = new RequestData(
                                                get_w_from.getSelectedItem().toString(),
                                                get_w_to.getSelectedItem().toString(),
                                                quantity_tt);


//        get_w_from.getSelectedItem().toString();
//        get_w_to.getSelectedItem().toString();
//        intent.putExtra("w_from", get_w_from.getSelectedItem().toString());
//        intent.putExtra("w_to", get_w_to.getSelectedItem().toString());
        intent.putExtra(RequestData.class.getSimpleName(), requestData);
        startActivity(intent);
    }
}