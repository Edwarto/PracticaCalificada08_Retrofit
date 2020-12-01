package com.example.practicacalificada_retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TodoAPI {
  @GET("/todos")
  Call<List<com.example.practicacalificada_retrofit.Todo>> getAllTodos();

  @GET("/todos/{id}")
  Call<List<com.example.practicacalificada_retrofit.Todo>> getTodo(@Path("id") String id);

  @POST("/todos")
  Call<List<com.example.practicacalificada_retrofit.Todo>> postTodos(@Field("id") String id, @Field("title") String title,  @Field("completed") boolean completed);

  @PUT("/todos")
  Call<List<com.example.practicacalificada_retrofit.Todo>> putAllTodos();

  @PATCH
  Call<List<com.example.practicacalificada_retrofit.Todo>> patchAllTodos();

  @DELETE
  Call<List<com.example.practicacalificada_retrofit.Todo>> deleteTodo(@Path("id") String id);
}
