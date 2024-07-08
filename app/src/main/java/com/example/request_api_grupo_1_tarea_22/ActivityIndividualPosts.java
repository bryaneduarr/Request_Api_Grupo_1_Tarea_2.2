package com.example.request_api_grupo_1_tarea_22;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityIndividualPosts extends AppCompatActivity {
  private ArrayList<String> titulos = new ArrayList<>();
  private EditText idUsuarioEditText;
  private ArrayAdapter arrayAdapter;
  private ListView postslistView;
  private Button regresarButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_individual_posts);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });
    idUsuarioEditText = (EditText) findViewById(R.id.idUsuarioEditText);

    idUsuarioEditText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        obtenerUsuario(s.toString());
      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });

    arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titulos);
    postslistView = (ListView) findViewById(R.id.postslistView);
    postslistView.setAdapter(arrayAdapter);
  }

  private void obtenerUsuario(String usuarioId) {
    if (titulos.size() > 0) {
      titulos.remove(0);
    }

    arrayAdapter.notifyDataSetChanged();

    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/").addConverterFactory(GsonConverterFactory.create()).build();

    UsersApi usersApi = retrofit.create(UsersApi.class);

    Call<Usuarios> callListView = usersApi.getUsuario(usuarioId);

    callListView.enqueue(new Callback<Usuarios>() {
      @Override
      public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {
        titulos.add(response.body().getTitle());

        arrayAdapter.notifyDataSetChanged();
      }

      @Override
      public void onFailure(Call<Usuarios> call, Throwable t) {

      }
    });

  }
}