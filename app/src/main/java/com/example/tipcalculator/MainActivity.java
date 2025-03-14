package com.example.tipcalculator;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tipcalculator.databinding.ActivityMainBinding;

import java.text.NumberFormat;


public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener, View.OnClickListener {
ActivityMainBinding binding;
    private EditText billAmount;
    private TextView percentTextView;
    private Button percentUp, percentDown;
    private  TextView  tipTextView;
    private TextView totalTextView;

    private String billAmountString = "";
    private float tipPercent = 0.15f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        billAmount = binding.editTextNumberDecimal;
        percentTextView = binding.percentTextView;
        tipTextView = binding.tipTextView;
        totalTextView = binding.totalTextView;
        percentUp = binding.buttonUp;
        percentDown = binding.buttonDown;

        billAmount.setOnEditorActionListener(this);
        percentDown.setOnClickListener(this);
        percentUp.setOnClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("billAmountString", billAmountString);
        outState.putFloat("tipPercent", tipPercent);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null){
            billAmountString = savedInstanceState.getString(billAmountString, "");
            tipPercent = savedInstanceState.getFloat(tipPercent, 0.15f);
        }
    }
    //    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        outState.putString("billAmountString", billAmountString);
//        outState.putFloat("tipPercent", tipPercent);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        if(savedInstanceState != null){
//            billAmountString= savedInstanceState.getString("billAmountString", "");
//            tipPercent = savedInstanceState.getFloat("tipPercent", 0.15f);
//
//            billAmount.setText(billAmountString);
//            calculateAndDisplay();
//        }
//    }


    public  void calculateAndDisplay(){
        billAmountString = billAmount.getText().toString().trim();
        float billAmount;
        if(billAmountString.equals("")){
            billAmount = 0f;
        }else{
            billAmount = Float.parseFloat(billAmountString);
        }

        //calculate tip and total

        float tipAmount = billAmount * tipPercent;
        float totalAmount = billAmount +tipAmount;

        //display the result after formatting
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        tipTextView.setText(currency.format(tipAmount));
        totalTextView.setText(currency.format(totalAmount));

        NumberFormat percent = NumberFormat.getPercentInstance();
        percentTextView.setText(percent.format(tipPercent));

    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.buttonUp:
//                tipPercent+= 0.1f;
//                calculateAndDisplay();
//                break;
//            case  binding.buttonDown:
//                tipPercent -= 0.1f;
//                calculateAndDisplay();
//                break;
//        }

        if(v== binding.buttonUp){
            tipPercent += 0.01f;
            calculateAndDisplay();

        }else if(v== binding.buttonDown){
            tipPercent -= 0.01f;
            calculateAndDisplay();

        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        calculateAndDisplay();
        return false;
    }
}