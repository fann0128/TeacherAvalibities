package com.example.yangf.teacheravalibities.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.yangf.teacheravalibities.BasicClasses.Department;
import com.example.yangf.teacheravalibities.BasicClasses.OfficeHour;
import com.example.yangf.teacheravalibities.BasicClasses.Program;
import com.example.yangf.teacheravalibities.BasicClasses.Teacher;
import com.example.yangf.teacheravalibities.DataManagement.DataManager;
import com.example.yangf.teacheravalibities.DataManagement.MyListViewAdapter;
import com.example.yangf.teacheravalibities.R;

import java.util.ArrayList;

public class Fragment1 extends Fragment {

    private Spinner spDep;
    private Spinner spPro;

    private ArrayAdapter<String> adapterDep;
    private ArrayAdapter<String> adapterPro;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_layout1, container, false);

        DataManager.reLoadAllData(getContext());

        spDep = (Spinner)v.findViewById(R.id.spinnerDepName);
        spPro = (Spinner)v.findViewById(R.id.spinnerProgarm);

        adapterDep = new ArrayAdapter<String>(v.getContext(),android.R.layout.simple_spinner_dropdown_item);
        adapterPro = new ArrayAdapter<String>(v.getContext(),android.R.layout.simple_spinner_dropdown_item);
        setSpinnerDepItems();

        ListView ListOfResult = (ListView)v.findViewById(R.id.listView);
        ListOfResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent("com.example.yangf.teacheravalibities.CourseInTableActivity");

                Bundle officeHours = new Bundle();
                ArrayList<OfficeHour> listOfHours = new ArrayList<OfficeHour>();



                officeHours.putSerializable("listOfHours", listOfHours);
                intent.putExtras(officeHours);


                startActivity(intent);


            }
        });

        spDep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String str = parent.getItemAtPosition(position).toString();
                setSpinnerPrograms(str);
                updateListViewTeacher(str);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spPro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String strPro = parent.getItemAtPosition(position).toString();
                String strDep = spDep.getSelectedItem().toString();

                if (strPro != "-") {
                    updateListViewTeacher(strDep,strPro);
                } else {
                    updateListViewTeacher(strDep);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return v;
    }

    public void setSpinnerDepItems()
    {

        for (Department dp :DataManager.listOfDepartments)
        {
            adapterDep.add(dp.getName().toString());
        }
        spDep.setAdapter(adapterDep);

    }
    public void updateListViewTeacher(String depName)
    {

        ArrayList<Teacher>listOfTea = new ArrayList<>();
        for(Department dep : DataManager.listOfDepartments)
        {
            if(dep.getName()==depName)
            {
                for(Teacher t : dep.getListOfTeachers())
                {
                    listOfTea.add(t);
                }

            }

        }

        updateListView(listOfTea);

    }
    public void setSpinnerPrograms(String depName)
    {
        adapterPro.clear();
        adapterPro.add("-");
        for(Department dep : DataManager.listOfDepartments)
        {
            if(dep.getName()==depName)
            {
                for(Program p : dep.getListOfPrograms())
                {
                    adapterPro.add(p.getTitle());
                }

            }

        }

        spPro.setAdapter(adapterPro);

    }
    public void updateListViewTeacher(String depName,String proName)
    {

        ArrayList<Teacher>listOfTea = new ArrayList<>();
        for(Department dep : DataManager.listOfDepartments)
        {
            if(dep.getName()==depName)
            {
                for(Program p:dep.getListOfPrograms())
                {

                    if(p.getTitle() == proName)
                    {
                        listOfTea = p.getListOfTeachers();

                    }


                }


            }

        }

        updateListView(listOfTea);
    }

    private void updateListView( ArrayList<Teacher> listOfMatchedTeachers)
    {

        //instantiate custom adapter
        MyListViewAdapter adapter = new MyListViewAdapter(listOfMatchedTeachers, getContext());

        //handle listview and assign adapter
        ListView lView = (ListView) getActivity().findViewById(R.id.listView);
        lView.setAdapter(adapter);
    }


}
