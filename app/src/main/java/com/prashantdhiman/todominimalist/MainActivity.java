package com.prashantdhiman.todominimalist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static List<ItemModel> listItems;
    private RecyclerView recyclerView;
    private MyAdapter myAdapterObj;
    private int numOfCols = 2;

    private CoordinatorLayout coordinatorLayout;
    private FloatingActionButton addFab;
    private ImageView noNewTaskImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPureWhite));
        }

        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        addFab = findViewById(R.id.addFab);
        noNewTaskImageView=findViewById(R.id.noNewTaskImageView);

        listItems = new ArrayList<>();

        loadData();
        int size=listItems.size();

//        Toast.makeText(getApplicationContext(),Integer.toString(size),Toast.LENGTH_SHORT).show();

        if(listItems.isEmpty()){
            noNewTaskImageView.setVisibility(View.VISIBLE);
        }else{
            noNewTaskImageView.setVisibility(View.GONE);
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(numOfCols, LinearLayout.VERTICAL));

        loadRecyclerViewData();

        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NewTaskActivity.class));
            }
        });

    }

    private void loadRecyclerViewData() {

        myAdapterObj = new MyAdapter(getApplicationContext(), listItems);
        recyclerView.setAdapter(myAdapterObj);

        myAdapterObj.setOnCardClickListener(new MyAdapter.onCardClickListener() {
            @Override
            public void onCardClick(int position) {
                editTask(position);
            }

            @Override
            public void onChkBtnClick(int position) {
                int status = listItems.get(position).getStatus();
                if (status == 0) {
                    status = 1;
                } else {
                    status = 0;
                }
                listItems.get(position).setStatus(status);
                myAdapterObj.notifyItemChanged(position);
            }
        });

    }

    private void editTask(int position) {

        String taskText = listItems.get(position).getTodoTaskText();
        int taskColor = listItems.get(position).getColor();
        int taskStatus = listItems.get(position).getStatus();

        Intent intent = new Intent(getApplicationContext(), NewTaskActivity.class);
        intent.putExtra("taskIndex", position);
        intent.putExtra("taskText", taskText);
        intent.putExtra("taskColor", taskColor);
        intent.putExtra("taskStatus", taskStatus);
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();

        myAdapterObj.notifyDataSetChanged();
        if(listItems.isEmpty()){
            noNewTaskImageView.setVisibility(View.VISIBLE);
        }else{
            noNewTaskImageView.setVisibility(View.GONE);
        }
        saveDataPermanently();
    }

    private void saveDataPermanently() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listItems);
        editor.putString("task list", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<ItemModel>>() {
        }.getType();
        listItems = gson.fromJson(json, type);

        if (listItems == null) {
            listItems = new ArrayList<>();
        }
    }
}
