package com.example.request_api_grupo_1_tarea_22;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.request_api_grupo_1_tarea_22.Interface.UsersApi;
import com.example.request_api_grupo_1_tarea_22.Modelo.Usuarios;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityPosts extends AppCompatActivity {
  private ArrayList<String> titulos = new ArrayList<>();
  private EditText idUsuarioEditText;
  private ArrayAdapter arrayAdapter;
  private ListView postslistView;
  private Button regresarButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_posts);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });
    obtenerPosts();

    arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titulos);
    postslistView = (ListView) findViewById(R.id.postslistView);
    postslistView.setAdapter(arrayAdapter);

    regresarButton = findViewById(R.id.regresarButton);

    regresarButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
      }
    });
  }

  private void obtenerPosts() {
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/").addConverterFactory(GsonConverterFactory.create()).build();

    UsersApi usersApi = retrofit.create(UsersApi.class);

    Call<List<Usuarios>> callListView = usersApi.getUsuarios();

    callListView.enqueue(new Callback<List<Usuarios>>() {
      @Override
      public void onResponse(Call<List<Usuarios>> call, Response<List<Usuarios>> response) {
        for (Usuarios usuarios : response.body()) {
          Log.i(usuarios.getTitle(), "onResponse");
          titulos.add(usuarios.getTitle());

          arrayAdapter.notifyDataSetChanged();
        }
      }

      @Override
      public void onFailure(Call<List<Usuarios>> call, Throwable t) {
        t.getMessage();
      }
    });
  }
}