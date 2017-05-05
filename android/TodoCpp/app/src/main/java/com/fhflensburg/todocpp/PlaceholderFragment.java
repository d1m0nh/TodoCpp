package com.fhflensburg.todocpp;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.fhflensburg.libtodo.djinni.Todo;
import com.fhflensburg.libtodo.djinni.TodoAppCore;
import com.fhflensburg.libtodo.djinni.TodoManager;
import com.fhflensburg.todocpp.manager.TodoAdapter;

import java.util.List;

/**
 * Placeholder Fragment
 *
 * Author: Maik Hansen
 *
 * This class handles the different fragment states and views of the application. It extends
 * the fragment class.
 *
 */

public class PlaceholderFragment extends Fragment {

    /* declare class variables */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private TodoManager todoManager = null;
    private TodoAdapter adapter = null;
    private List<Todo> todoList = null;

    public Context appContext = null;

    Button addNewTodo;
    EditText newTodoTitle;
    Spinner newTodoCategory;

    /* empty constructor */
    public PlaceholderFragment() {}

    /* fragment instance */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);

        return fragment;
    }

    /* create fragment view */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /* variables to handle the todos */
        TodoAppCore todoApp = ((TodoApplication)getActivity().getApplication()).getTodoApp();
        todoManager = todoApp.getTodoManager();

        todoList = todoManager.getTodos();
        adapter = new TodoAdapter(getContext(), R.layout.todo_list_inner_layout, todoList);

        /* set menu to true, so it is possible to access menu actions in fragment */
        setHasOptionsMenu(true);

        /* create fragment view variable */
        View fragment = null;

        /* switch through the different fragment sections from tabs */
        switch(getArguments().getInt(ARG_SECTION_NUMBER)) {

            /* case 1 is the list view of todos */
            case 1:
                fragment = inflater.inflate(R.layout.todo_list_layout, container, false);
                final ListView todoListView = (ListView) fragment.findViewById(R.id.todoListView);

                /* set click listener, for dynamic propose with methods */
                setSingleClickListener(todoListView);

                /* set the adapter to the list view, to get the todos */
                todoListView.setAdapter(adapter);
                break;

            /* is the create new view */
            case 2:
                fragment = inflater.inflate(R.layout.todo_new_layout, container, false);

                addNewTodo = (Button) fragment.findViewById(R.id.addTodo);
                newTodoTitle = (EditText) fragment.findViewById(R.id.newTodoTitle);
                newTodoCategory = (Spinner) fragment.findViewById(R.id.newTodoCategory);

                /* set on click to the new add button */
                addNewTodo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /* for toasts */
                        Context context = getContext();
                        int duration = Toast.LENGTH_LONG;

                        /* get the data */
                        String title = newTodoTitle.getText().toString();
                        String category = newTodoCategory.getSelectedItem().toString();

                        /* no values, toast message - else add new */
                        if(!title.equals("") && !category.equals("")){
                            add_todo(title, category, 0);
                            newTodoTitle.setText("");
                        } else {

                            if(title.equals("") && !category.equals("")){
                                String noTitle = "Please enter a title";
                                Toast toast = Toast.makeText(context, noTitle, duration);
                                toast.show();
                            }

                            if(!title.equals("") && category.equals("")){
                                String noCategory = "Please choose a category";
                                Toast toast = Toast.makeText(context, noCategory, duration);
                                toast.show();
                            }

                            if(!title.equals("") && !category.equals("")){
                                String noEntries = "Please enter title and category";
                                Toast toast = Toast.makeText(context, noEntries, duration);
                                toast.show();
                            }
                        }
                    }
                });
                break;
            default:
                Log.d("DEFAULT", "DEFAULT SWITCH CALLED");
                break;
        }

        return fragment;
    }

    /* options menu to filter the todos */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.All:
                handle_category_filter("All");
                break;
            case R.id.Other:
                handle_category_filter("Other");
                break;
            case R.id.Shopping:
                handle_category_filter("Shopping");
                break;
            case R.id.Study:
                handle_category_filter("Study");
                break;
            case R.id.Work:
                handle_category_filter("Work");
                break;
            case R.id.Reminder:
                handle_category_filter("Reminder");
                break;
        }

        return false;
    }

    /* item single click for dynamically added items */
    public void setSingleClickListener(ListView list){
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // set id and pos to handle data
                CheckBox checkBox = (CheckBox) v.findViewById(R.id.todo_checked);
                checkBox.setChecked(true);
                finishTodo(todoList.get(position).getTodoId(), position);
            }
        });
    }

    /* delete from database and adapter with alert dialog */
    public void finishTodo(final int id, final int pos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                todoManager.removeTodo(id);
                adapter.remove(adapter.getItem(pos));

                ListView todoListView = (ListView)getActivity().findViewById(R.id.todoListView);
                todoListView.setAdapter(adapter);

                String text = "Todo finished";
                Toast toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
                toast.show();
            }
        }, 1000);
    }

    /* add new to database by given data */
    private void add_todo(String title, String category, int state) {
        Todo todo = new Todo(title, category, state, 122544);
        todoManager.addTodo(todo);

        List<Todo> list = todoManager.getTodos();
        adapter.clear();
        adapter.addAll(list);
        adapter.notifyDataSetChanged();

        ListView todoListView = (ListView)getActivity().findViewById(R.id.todoListView);
        todoListView.setAdapter(adapter);

        setSingleClickListener(todoListView);

        String noEntries = "Todo added";
        Toast toast = Toast.makeText(getContext(), noEntries, Toast.LENGTH_LONG);
        toast.show();
    }

    /* filter the todos by the category */
    public void handle_category_filter(String category){
        List<Todo> list = todoManager.getTodosByCategory(category);
        adapter.clear();
        adapter.addAll(list);
        adapter.notifyDataSetChanged();

        ListView todoListView = (ListView)getActivity().findViewById(R.id.todoListView);
        todoListView.setAdapter(adapter);

        String text = "Category \"" + category + "\"";
        Toast toast = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
        toast.show();
    }
}
