package com.example.rcontacts30;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater m=getMenuInflater();
        m.inflate(R.menu.menu_bar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.add)
        {
            Intent i=new Intent(MainActivity.this,MainActivity2.class);
            startActivity(i);
        }
        return true;
    }

    public static Context c;
    public static CustomAdapter x;
    public static Vector<String> p;
    public static Vector<String> n;
    SearchView s;
    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        p=new Vector<String>();
        n=new Vector<String>();
        c=this;
        Toast.makeText(c, "Long Press To Delete Contact", Toast.LENGTH_LONG).show();
        rv=findViewById(R.id.rv);
        s=findViewById(R.id.s);
        SQLiteDatabase db=this.openOrCreateDatabase("contacts",MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS contacts (name VARCHAR(20),pno VARCHAR(15))");
        Cursor cur= db.rawQuery("SELECT * FROM contacts ORDER BY name ASC",null);
        cur.moveToFirst();
        int na=cur.getColumnIndex("name");
        int pn=cur.getColumnIndex("pno");
        while(!cur.isAfterLast())
        {
            n.add(cur.getString(na));
            p.add(cur.getString(pn));

            if(!cur.moveToNext())break;
        }
        x=new CustomAdapter(n,p);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(x);
        s.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String q) {
                p.clear();
                n.clear();
                Cursor cur= db.rawQuery("SELECT * FROM contacts WHERE name LIKE '%"+q+"%' ORDER BY name ASC",null);
                cur.moveToFirst();
                int na=cur.getColumnIndex("name");
                int pn=cur.getColumnIndex("pno");
                while(!cur.isAfterLast())
                {
                    n.add(cur.getString(na));
                    p.add(cur.getString(pn));

                    if(!cur.moveToNext())break;
                }
                x.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }
}