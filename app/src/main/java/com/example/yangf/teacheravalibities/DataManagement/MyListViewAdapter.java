package com.example.yangf.teacheravalibities.DataManagement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.yangf.teacheravalibities.BasicClasses.OfficeHour;
import com.example.yangf.teacheravalibities.BasicClasses.Teacher;
import com.example.yangf.teacheravalibities.R;

import java.util.ArrayList;

/**
 * Created by Admin on 2016-01-20.
 */
public class MyListViewAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Teacher> list = new ArrayList<Teacher>();
    private Context context;



    public MyListViewAdapter(ArrayList<Teacher> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;

    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_list_item, null);
        }
        //Sign TextView to parameter from custom_list_item
        TextView tv_dep = (TextView)view.findViewById(R.id.tv_Dep);


        tv_dep.setText(String .valueOf(list.get(position).getDepId()));

        TextView program = (TextView)view.findViewById(R.id.tv_prgs);


        String strPrg = "TBA";
        program.setText(strPrg);

        TextView tv_TeacherName = (TextView)view.findViewById(R.id.tv_name);
        tv_TeacherName.setText(list.get(position).getfName()+" "+list.get(position).getlName());








        //Handle buttons and add onClickListeners
        ImageButton btnFav = (ImageButton)view.findViewById(R.id.imageButton);
        ImageButton btnEmail = (ImageButton)view.findViewById(R.id.imageButton2);





        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{list.get(position).getEmail()});
                i.putExtra(Intent.EXTRA_SUBJECT, "Office Hour");
                i.putExtra(Intent.EXTRA_TEXT   , "");
                try {
                    context.startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something
                Toast toast = Toast.makeText(context,"you clicked Fav",Toast.LENGTH_LONG);
                toast.show();
            }
        });

        return view;
    }

}
