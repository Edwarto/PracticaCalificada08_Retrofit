package com.example.practicacalificada_retrofit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

  private ListView lvTodos;
  Activity activity;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    activity = this;
    lvTodos = (ListView)findViewById(R.id.lvTodos);
    loadToDos();
  }

  protected void loadToDos(){
    Retrofit retrofit = API.getRetrofitClient();
    com.example.practicacalificada_retrofit.TodoAPI api = retrofit.create(com.example.practicacalificada_retrofit.TodoAPI.class);

    Call<List<com.example.practicacalificada_retrofit.Todo>> listCall = api.getAllTodos();

    listCall.enqueue(new Callback<List<com.example.practicacalificada_retrofit.Todo>>() {
      @Override
      public void onResponse(Call<List<com.example.practicacalificada_retrofit.Todo>> call, Response<List<com.example.practicacalificada_retrofit.Todo>> response) {
        com.example.practicacalificada_retrofit.TodoList list = new com.example.practicacalificada_retrofit.TodoList(activity,response.body());
        lvTodos.setAdapter(list);
        lvTodos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(getApplicationContext(), response.body().get(i).getTitle()
                , Toast.LENGTH_SHORT).show();

            Intent intent = new Intent (MainActivity.this, Detalle_Todo.class);
            intent.putExtra("variable_integer", response.body().get(i).getId());
            startActivityForResult(intent, 0);
          }
        });
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