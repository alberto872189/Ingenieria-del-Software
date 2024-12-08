package es.unizar.eina.T202_camping.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import es.unizar.eina.T202_camping.R;

public class Index extends AppCompatActivity {

    Button mParcelaButton;
    Button mReservaButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campingindex);

        mParcelaButton = findViewById(R.id.parcelaButton);
        mReservaButton = findViewById(R.id.reservaButton);

        mParcelaButton.setOnClickListener(view -> {
            Intent intent = new Intent(Index.this, IndexParcela.class);
            startActivity(intent);
        });

        mReservaButton.setOnClickListener(view -> {
            Intent intent = new Intent(Index.this, IndexReserva.class);
            startActivity(intent);
        });
    }
}
