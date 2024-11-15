package es.unizar.eina.camping.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import es.unizar.eina.camping.R;

public class IndexParcela extends AppCompatActivity {
    Button mCreateParcelaButton;
    Button mVerParcelaButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcelaindex);

        mCreateParcelaButton = findViewById(R.id.CrearParcelaButton);
        mCreateParcelaButton = findViewById(R.id.VerParcelasButton);

        mCreateParcelaButton.setOnClickListener(view -> {
            Intent intent = new Intent(IndexParcela.this, Parcelapad.class); //CAMBIAR POR CREARPARCELA
            startActivity(intent);
        });

        mVerParcelaButton.setOnClickListener(view -> {
            Intent intent = new Intent(IndexParcela.this, Parcelapad.class);
            startActivity(intent);
        });
    }
}
