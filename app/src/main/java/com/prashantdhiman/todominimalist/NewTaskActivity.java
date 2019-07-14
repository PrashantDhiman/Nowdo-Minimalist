package com.prashantdhiman.todominimalist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class NewTaskActivity extends AppCompatActivity {

    private Button backBtn, deleteBtn;
    private LinearLayout newTaskLnrLayout;
    private EditText taskEditText;

    //properties of each task/card that will be received through intent
    Intent intent;
    String taskText;
    int taskIndex;
    int taskColor;
    int taskStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(NewTaskActivity.this, R.color.colorPureWhite));
        }

        newTaskLnrLayout = findViewById(R.id.newTaskLnrLayout);
        backBtn = findViewById(R.id.backBtn);
        deleteBtn = findViewById(R.id.deleteBtn);
        taskEditText = findViewById(R.id.taskEditText);

        intent = getIntent();
        taskText = intent.getStringExtra("taskText");
        taskIndex = intent.getIntExtra("taskIndex", -1);
        taskColor = intent.getIntExtra("taskColor", -1);
        taskStatus = intent.getIntExtra("taskStatus", 0);
        if (taskColor == -1) {
            taskColor = (int) (Math.random() * 3) + 1;
        }

        if (taskIndex != -1 && !taskText.isEmpty()) {
            taskEditText.setText(taskText);
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDataToList();
                finish();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (taskIndex != -1) {
                    MainActivity.listItems.remove(taskIndex);
                }
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        saveDataToList();
        finish();
    }

    private void saveDataToList() {

        if (!taskEditText.getText().toString().isEmpty()) {
            ItemModel itemModelObj = new ItemModel(taskEditText.getText().toString(), taskColor, taskStatus);

            if (taskIndex != -1) {
                MainActivity.listItems.remove(taskIndex);
            }

            MainActivity.listItems.add(0, itemModelObj);

        }
    }
}
