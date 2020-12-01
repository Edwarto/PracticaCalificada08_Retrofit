package com.example.practicacalificada_retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PostTodo extends AppCompatActivity {

    EditText Id;
    EditText Title;
    EditText Completed;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_todo);

        activity = this;
        Id = (EditText) findViewById(R.id.edtId);
        Title = (EditText) findViewById(R.id.edtTitle);
        Completed = (EditText) findViewById(R.id.edtCompleted);


    }

    protected void PostToDos(){
        Retrofit retrofit = API.getRetrofitClient();
        com.example.practicacalificada_retrofit.TodoAPI api = retrofit.create(com.example.practicacalificada_retrofit.TodoAPI.class);

        String id_p = String.valueOf(Id.getText());
        String title_p = String.valueOf(Title.getText());
        Boolean completed = Boolean.valueOf(String.valueOf(Completed.getText()));
        Call<List<Todo>> listCall = api.postTodos(id_p,title_p,completed);

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

    public void post(View view) {
        PostToDos();
    }
}