package es.unizar.eina.camping.ui;

import static androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.unizar.eina.camping.R;
import es.unizar.eina.camping.database.Reserva;

/** Pantalla principal de la aplicación Parcelapad */
public class Reservapad extends AppCompatActivity {
    private ReservaViewModel mReservaViewModel;

    static final int INSERT_ID = Menu.FIRST;
    static final int DELETE_ID = Menu.FIRST + 1;
    static final int EDIT_ID = Menu.FIRST + 2;

    RecyclerView mRecyclerView;

    ReservaListAdapter mAdapter;

    Button mOrdenarNombre;
    Button mOrdenarMovil;
    Button mOrdenarFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservapad);
        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new ReservaListAdapter(new ReservaListAdapter.ReservaDiff());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mReservaViewModel = new ViewModelProvider(this).get(ReservaViewModel.class);

        mReservaViewModel.getAllReservas().observe(this, reservas -> {
            // Update the cached copy of the parcelas in the adapter.
            mAdapter.submitList(reservas);
        });

        mOrdenarNombre = findViewById(R.id.buttonNombre);
        mOrdenarMovil = findViewById(R.id.buttonMovil);
        mOrdenarFecha = findViewById(R.id.buttonFecha);

        mOrdenarNombre.setOnClickListener(view -> {
            mReservaViewModel.getAllReservasName().observe(this, reservas -> {
                // Update the cached copy of the parcelas in the adapter.
                mAdapter.submitList(reservas);
            });
        });

        mOrdenarMovil.setOnClickListener(view -> {
            mReservaViewModel.getAllReservasMovil().observe(this, reservas -> {
                // Update the cached copy of the parcelas in the adapter.
                mAdapter.submitList(reservas);
            });

        });

        mOrdenarFecha.setOnClickListener(view -> {
            mReservaViewModel.getAllReservasFecha().observe(this, reservas -> {
                // Update the cached copy of the parcelas in the adapter.
                mAdapter.submitList(reservas);
            });

        });

        // It doesn't affect if we comment the following instruction
        registerForContextMenu(mRecyclerView);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, INSERT_ID, Menu.NONE, R.string.add_note);
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case INSERT_ID:
                createReserva();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

     public boolean onContextItemSelected(MenuItem item) {
        Reserva current = mAdapter.getCurrent();
        switch (item.getItemId()) {
            case DELETE_ID:
                Toast.makeText(
                        getApplicationContext(),
                        "Deleting " + current.getName(),
                        Toast.LENGTH_LONG).show();
                mReservaViewModel.delete(current);
                return true;
            case EDIT_ID:
                editReserva(current);
                return true;
        }
        return super.onContextItemSelected(item);
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
                new StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Bundle extras = result.getData().getExtras();
                        Reserva reserva = new Reserva(extras.getString(ReservaEdit.RESERVA_NAME),
                                extras.getString(ReservaEdit.RESERVA_PHONE),
                                extras.getString(ReservaEdit.RESERVA_ENTRADA),
                                extras.getString(ReservaEdit.RESERVA_SALIDA),
                                1.0); //Rellenar con precio
                        executable.process(extras, reserva);
                    }
                });
    }

    private void editReserva(Reserva current) {
        Intent intent = new Intent(this, ReservaEdit.class);
        intent.putExtra(ReservaEdit.RESERVA_NAME, current.getName());
        intent.putExtra(ReservaEdit.RESERVA_PHONE, current.getMovil());
        intent.putExtra(ReservaEdit.RESERVA_ENTRADA, current.getFechaEntrada());
        intent.putExtra(ReservaEdit.RESERVA_SALIDA, current.getFechaSalida());
        intent.putExtra("1", current.getPrecio()); //Rellenar con precio
        mStartUpdateReserva.launch(intent);
    }

    ActivityResultLauncher<Intent> mStartUpdateReserva = newActivityResultLauncher(new ExecuteActivityResultReserva() {
        @Override
        public void process(Bundle extras, Reserva reserva) {
            String id = ReservaEdit.RESERVA_ID;
            reserva.setId(Integer.valueOf(id));
            mReservaViewModel.update(reserva);
        }
    });

}

interface ExecuteActivityResultReserva {
    void process(Bundle extras, Reserva reserva);
}