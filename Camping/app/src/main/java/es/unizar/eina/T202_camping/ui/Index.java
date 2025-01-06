package es.unizar.eina.T202_camping.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import es.unizar.eina.T202_camping.R;

public class Index extends AppCompatActivity {

    Button mParcelaButton;
    Button mReservaButton;
    Button mPruebasButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campingindex);

        mParcelaButton = findViewById(R.id.respositoryButton);
        mReservaButton = findViewById(R.id.reservaButton);
        mPruebasButton = findViewById(R.id.pruebasButton);

        mParcelaButton.setOnClickListener(view -> {
            Intent intent = new Intent(Index.this, IndexParcela.class);
            startActivity(intent);
        });

        mReservaButton.setOnClickListener(view -> {
            Intent intent = new Intent(Index.this, IndexReserva.class);
            startActivity(intent);
        });

        mPruebasButton.setOnClickListener(view -> {
            Intent intent = new Intent(Index.this, UnitTests.class);
            startActivity(intent);
        });
    }
}
