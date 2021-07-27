package com.example.rcontacts30;


import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Vector;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private Vector<String> a=new Vector<String>();
    private Vector<String> b=new Vector<String>();

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView na,pn;

        public ViewHolder(View view) {
            super(view);

            // Define click listener for the ViewHolder's View


            na = (TextView) view.findViewById(R.id.na);
            pn = (TextView) view.findViewById(R.id.pn);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    String e,f;
                    e=na.getText().toString();
                    f=pn.getText().toString();
                    SQLiteDatabase db =MainActivity.c.openOrCreateDatabase("contacts", Context.MODE_PRIVATE,null);
                    db.execSQL("CREATE TABLE IF NOT EXISTS contacts (name VARCHAR(20),pno VARCHAR(15)) ");
                    db.execSQL("DELETE FROM contacts WHERE name='"+e+"'");
                    for(int i=0;i<MainActivity.n.size();i++)
                    {
                        if(MainActivity.n.get(i).equals(e))
                        {
                            MainActivity.n.remove(i);
                            MainActivity.p.remove(i);
                            break;
                        }
                    }
                    MainActivity.x.notifyDataSetChanged();
                    return true;
                }
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" +pn.getText().toString()));
                    v.getContext().startActivity(intent);

                }
            });


        }



        public TextView getTextViewna() {
            return na;
        }
        public TextView getTextViewpn() {
            return pn;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param x,y String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public CustomAdapter(Vector<String> x,Vector<String> y) {
        a=x;
        b=y;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rc, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextViewna().setText(a.get(position));
        viewHolder.getTextViewpn().setText(b.get(position));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return a.size();
    }
}

