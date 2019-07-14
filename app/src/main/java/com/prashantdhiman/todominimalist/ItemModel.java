package com.prashantdhiman.todominimalist;

public class ItemModel {

    private String todoTaskText;
    private int color;
    private int status;  //0=not done   1=done

    public ItemModel(String todoTask, int color, int statusd) {
        this.todoTaskText = todoTask;
        this.color = color;
        this.status = status;
    }

    public String getTodoTaskText() {
        return todoTaskText;
    }

    public int getColor() {
        return color;
    }

    public int getStatus() {
        return status;
    }

    public void setTodoTaskText(String todoTaskText) {
        this.todoTaskText = todoTaskText;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
