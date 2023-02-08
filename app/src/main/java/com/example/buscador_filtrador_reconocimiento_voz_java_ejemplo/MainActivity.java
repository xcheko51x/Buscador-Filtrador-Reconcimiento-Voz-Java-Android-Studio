package com.example.buscador_filtrador_reconocimiento_voz_java_ejemplo;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;

import com.example.buscador_filtrador_reconocimiento_voz_java_ejemplo.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    AdaptadorNombres adaptador;

    ArrayList<String> listaNombres = new ArrayList<>(Arrays.asList("Ana", "Sergio", "Luis", "Maria", "Antonio", "Laura"));

    private final ActivityResultLauncher<Intent> activityResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            ArrayList<String> data = result.getData() != null ? result.getData().getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) : null;
            if (data != null) {
                binding.etNombre.setText(data.get(0));
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.rvNombres.setLayoutManager(new LinearLayoutManager(this));
        adaptador = new AdaptadorNombres(listaNombres);
        binding.rvNombres.setAdapter(adaptador);

        binding.ibtnMicrofono.setOnClickListener(view -> {
            binding.etNombre.setText("");
            escucharVoz();
        });

        binding.etNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                filtrar(editable.toString());
            }
        });
    }

    private void escucharVoz() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        activityResult.launch(intent);
    }

    private void filtrar(String texto) {
        ArrayList<String> listaFiltrada = new ArrayList();
        for (String nombre : listaNombres) {
            if (nombre.toLowerCase().contains(texto.toLowerCase())) {
                listaFiltrada.add(nombre);
            }
        }
        adaptador.filtrar(listaFiltrada);
    }
}