package com.fhflensburg.todocpp.manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.fhflensburg.libtodo.djinni.Todo;
import com.fhflensburg.todocpp.PlaceholderFragment;
import com.fhflensburg.todocpp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Todo Adapter
 *
 * Author: Maik Hansen
 *
 * This class handles the way to display the inner list layout of the todos list view
 * It extends the ArrayAdapter to pass its data from the given ArrayList (todolist) to the
 * list view
 *
 */

public class TodoAdapter extends ArrayAdapter<Todo> {

    /* declare class variables */
    Context context;
    List<Todo> todoList = new ArrayList<Todo>();
    int layoutResourceId;
    PlaceholderFragment mainFrag = null;

    /* constructor gets the view context, layout resource (inner_list), todolist */
    public TodoAdapter(Context context, int layoutResourceId, List<Todo> objects) {
        super(context, layoutResourceId, objects);
        this.layoutResourceId = layoutResourceId;
        this.todoList = objects;
        this.context = context;
        this.mainFrag = new PlaceholderFragment();
    }

    /* create view for the list */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        /* get the layout */
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.todo_list_inner_layout, parent, false);
        TextView todoTitle = (TextView) itemView.findViewById(R.id.todoTitle);
        TextView todoCategory = (TextView) itemView.findViewById(R.id.todoCategory);

        /* get todo on each position */
        final Todo todo = todoList.get(position);

        /* different states */
        CheckBox check = (CheckBox) itemView.findViewById(R.id.todo_checked);

        switch(todo.getTodoState()){
            case 0:
                check.setChecked(false);
                break;
            case 1:
                check.setChecked(true);
                break;
            default:
                check.setChecked(false);
                break;

        }

        /* add title and category to the text view */
        todoTitle.setText(todo.getTodoTitle());
        todoCategory.setText(todo.getTodoCategory());

        return itemView;
    }
}
