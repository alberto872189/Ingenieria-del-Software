package es.unizar.eina.camping.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import es.unizar.eina.camping.R;

public class IndexReserva extends AppCompatActivity {
    Button mCreateReservaButton;
    Button mVerReservaButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservaindex);

        mCreateReservaButton = findViewById(R.id.CrearReservaButton);
        mVerReservaButton = findViewById(R.id.VerReservasButton);

        mCreateReservaButton.setOnClickListener(view -> {
            Intent intent = new Intent(IndexReserva.this, ReservaCreate.class); //CAMBIAR POR CREARRESERVA
            startActivity(intent);
        });

        mVerReservaButton.setOnClickListener(view -> {
            Intent intent = new Intent(IndexReserva.this, Parcelapad.class);
            startActivity(intent);
        });
    }
}
