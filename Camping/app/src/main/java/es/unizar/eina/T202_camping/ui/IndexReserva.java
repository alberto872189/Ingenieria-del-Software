package es.unizar.eina.T202_camping.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import es.unizar.eina.T202_camping.R;
import es.unizar.eina.T202_camping.database.Reserva;

public class IndexReserva extends AppCompatActivity {
    Button mCreateReservaButton;
    Button mVerReservaButton;

    private ReservaViewModel mReservaViewModel;
    ReservaListAdapter mAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservaindex);

        mAdapter = new ReservaListAdapter(new ReservaListAdapter.ReservaDiff());

        mCreateReservaButton = findViewById(R.id.CrearReservaButton);
        mVerReservaButton = findViewById(R.id.VerReservasButton);

        mCreateReservaButton.setOnClickListener(view -> {
            createReserva();
        });

        mVerReservaButton.setOnClickListener(view -> {
            Intent intent = new Intent(IndexReserva.this, Reservapad.class);
            startActivity(intent);
        });
        mReservaViewModel = new ViewModelProvider(this).get(ReservaViewModel.class);

        mReservaViewModel.getAllReservas().observe(this, reservas -> {
            // Update the cached copy of the parcelas in the adapter.
            mAdapter.submitList(reservas);
        });
    }

    private void createReserva() {
        mStartCreateReserva.launch(new Intent(this, ReservaCreate.class));
    }

    ActivityResultLauncher<Intent> mStartCreateReserva = newActivityResultLauncher(new ExecuteActivityResultReserva() {
        @Override
        public void process(Bundle extras, Reserva reserva) {
            mReservaViewModel.insert(reserva);
        }
    });

    ActivityResultLauncher<Intent> newActivityResultLauncher(ExecuteActivityResultReserva executable) {
        return registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    /*if (result.getResultCode() == RESULT_OK) {
                        Bundle extras = result.getData().getExtras();
                        Reserva reserva = new Reserva(extras.getString(ReservaEdit.RESERVA_NAME),
                                extras.getString(ReservaEdit.RESERVA_PHONE),
                                extras.getString(ReservaEdit.RESERVA_ENTRADA),
                                extras.getString(ReservaEdit.RESERVA_SALIDA),
                                1.0); //Rellenar con precio
                        executable.process(extras, reserva);
                    }*/
                });
    }
}
