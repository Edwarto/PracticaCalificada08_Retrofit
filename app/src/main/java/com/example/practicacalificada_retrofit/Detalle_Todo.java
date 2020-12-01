package com.example.practicacalificada_retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Detalle_Todo extends AppCompatActivity {

    //Variables de objetos gráficos
    private TextView tvTitle;
    private TextView tvUserId;
    private TextView tvCompleted;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle__todo);

        activity = this;

        //Asignar variables a sus objetos
        tvTitle = (TextView) findViewById(R.id.tvTitle_D);
        tvUserId = (TextView) findViewById(R.id.tvUserId_D);
        tvCompleted = (TextView) findViewById(R.id.tvCompleted_D);


        loadToDos();
    }

    protected void loadToDos() {
        //Recuperar variable id del Main activity
        Bundle datos = this.getIntent().getExtras();
        int id_recuperada = datos.getInt("variable_integer");
        tvCompleted.setText(String.valueOf(id_recuperada));

        Retrofit retrofit = API.getRetrofitClient();
        com.example.practicacalificada_retrofit.TodoAPI api = retrofit.create(com.example.practicacalificada_retrofit.TodoAPI.class);

       Call<List<Todo>> listCall = api.getAllTodos();

        listCall.enqueue(new Callback<List<Todo>>() {
            @Override
            public void onResponse(Call<List<com.example.practicacalificada_retrofit.Todo>> call, Response<List<Todo>> response) {
                com.example.practicacalificada_retrofit.TodoList list = new com.example.practicacalificada_retrofit.TodoList(activity,response.body());
                tvTitle.setText(String.valueOf(response.body().get((id_recuperada-1)).getTitle()));
                tvUserId.setText(String.valueOf(response.body().get((id_recuperada-1)).getId()));
                //tvCompleted.setText("Hola");
                tvCompleted.setText(String.valueOf(response.body().get((id_recuperada-1)).getCompleted()));
            }

            @Override
            public void onFailure(Call<List<com.example.practicacalificada_retrofit.Todo>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Something went wrong"
                        ,Toast.LENGTH_SHORT).show();
                Log.d("RetrofitError",t.toString());
            }
        });


    }

    public void addTodo(View view) {
        Intent intent = new Intent (Detalle_Todo.this, PostTodo.class);
        startActivityForResult(intent, 0);
    }

    public void delete(View view) {
        deleteTodo();
    }

    protected void deleteTodo() {
        //Recuperar variable id del Main activity
        Bundle datos = this.getIntent().getExtras();
        int id_recuperada = datos.getInt("variable_integer");
        tvCompleted.setText(String.valueOf(id_recuperada));

        Retrofit retrofit = API.getRetrofitClient();
        com.example.practicacalificada_retrofit.TodoAPI api = retrofit.create(com.example.practicacalificada_retrofit.TodoAPI.class);

        Call<List<Todo>> listCall = api.deleteTodo(String.valueOf(id_recuperada));

        listCall.enqueue(new Callback<List<Todo>>() {
            @Override
            public void onResponse(Call<List<com.example.practicacalificada_retrofit.Todo>> call, Response<List<Todo>> response) {
                com.example.practicacalificada_retrofit.TodoList list = new com.example.practicacalificada_retrofit.TodoList(activity,response.body());
                if (response.body()!=null) {
                    Toast.makeText(getApplicationContext(),"Everything is okay"
                            ,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<com.example.practicacalificada_retrofit.Todo>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Something went wrong"
                        ,Toast.LENGTH_SHORT).show();
                Log.d("RetrofitError",t.toString());
            }
        });


    }
}