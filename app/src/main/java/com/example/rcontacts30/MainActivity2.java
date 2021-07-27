package com.example.rcontacts30;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    TextView name,pno;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent i=getIntent();
        name=findViewById(R.id.name);
        pno=findViewById(R.id.pno);
        b=findViewById(R.id.add1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a,b;
                a=name.getText().toString();
                b=pno.getText().toString();
                SQLiteDatabase db=MainActivity.c.openOrCreateDatabase("contacts",MODE_PRIVATE,null);
                db.execSQL("CREATE TABLE IF NOT EXISTS contacts (name VARCHAR(20), pno VARCHAR(15))");
                db.execSQL("INSERT INTO contacts VALUES('"+a+"','"+b+"')");
                MainActivity.n.add(a);
                MainActivity.p.add(b);
                MainActivity.x.notifyDataSetChanged();
                finish();

            }
        });


    }
}