package com.darkness.WSafety;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LawDisplayerActivity extends AppCompatActivity implements View.OnClickListener {

    TextView big,oneLine;
    String[] laws, lawsContent;
    int counter;
    Button back, next;
    View closeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law_displayer);

        big = findViewById(R.id.bigLaws);
        oneLine = findViewById(R.id.lawString);
        counter = getIntent().getIntExtra("position",0);
        laws = new String[]{"The Prohibition of Child Marriage Act, 2006","Special Marriage Act, 1954","Dowry Prohibition Act, 1961","Indian Divorce Act, 1969","Maternity Benefit Act,1861","Medical Termination of Pregnancy Act,1971","Sexual Harassment of Women at Workplace (Prevention, Prohibition and Redress) Act, 2013","Indecent Representation of Women(Prevention) Act,1986","National Commission for Women Act, 1990","Equal Remuneration Act, 1976"};
        lawsContent = this.getResources().getStringArray(R.array.lawsBig);

        closeBtn = findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(view -> {
            onBackPressed();
            LawDisplayerActivity.this.finish();
        });

        back = findViewById(R.id.backBtn);
        next = findViewById(R.id.nextBtn);
        next.setOnClickListener(this);
        back.setOnClickListener(this);
        setData();
    }

    public void setData(){
        oneLine.setText(laws[counter]);
        big.setText(lawsContent[counter]);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.nextBtn){
            if(counter<9){
                counter++;
            }else {
                counter = 0;
            }
        } else if (view.getId() == R.id.backBtn) {
            if(counter == 0){
                counter = (laws.length-1);
            }else {
                counter--;
            }
        }

        setData();
    }
}