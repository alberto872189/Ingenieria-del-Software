package es.unizar.eina.T202_camping.ui;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import es.unizar.eina.T202_camping.database.Parcela;
import es.unizar.eina.T202_camping.database.Reserva;
import es.unizar.eina.T202_camping.database.Parcela_Reserva;
import es.unizar.eina.T202_camping.R;

public class UnitTests extends AppCompatActivity {
    Button mRepositoryButton;
    Button mVolumenButton;
    Button mSobrecargaButton;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);

        mRepositoryButton = findViewById(R.id.respositoryButton);
        mVolumenButton = findViewById(R.id.volumenButton);
        mSobrecargaButton = findViewById(R.id.sobrecargaButton);

        mRepositoryButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, RepTest.class);
            startActivity(intent);
        });

        mVolumenButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, VolTest.class);
            startActivity(intent);
        });

        mSobrecargaButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, SobTest.class);
            startActivity(intent);
        });
    }
}
