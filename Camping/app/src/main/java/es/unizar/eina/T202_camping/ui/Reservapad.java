package es.unizar.eina.T202_camping.ui;

import static androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import es.unizar.eina.T202_camping.R;
import es.unizar.eina.T202_camping.database.ParcelaWithReserva;
import es.unizar.eina.T202_camping.database.Parcela_Reserva;
import es.unizar.eina.T202_camping.database.Reserva;
import es.unizar.eina.send.SendAbstraction;
import es.unizar.eina.send.SendAbstractionImpl;

/** Pantalla principal de la aplicación Parcelapad */
public class Reservapad extends AppCompatActivity {
    private ReservaViewModel mReservaViewModel;
    private Parcela_ReservaViewModel mParcelaReservaViewModel;

    static final int INSERT_ID = Menu.FIRST;
    static final int DELETE_ID = Menu.FIRST + 1;
    static final int EDIT_ID = Menu.FIRST + 2;
    static final int SEND_ID = Menu.FIRST + 3;

    RecyclerView mRecyclerView;

    ReservaListAdapter mAdapter;

    Button mOrdenarNombre;
    Button mOrdenarMovil;
    Button mOrdenarFecha;

    SendAbstraction send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservapad);
        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new ReservaListAdapter(new ReservaListAdapter.ReservaDiff());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mReservaViewModel = new ViewModelProvider(this).get(ReservaViewModel.class);
        mParcelaReservaViewModel = new ViewModelProvider(this).get(Parcela_ReservaViewModel.class);

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
                Collections.sort(reservas, (r1, r2) -> {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date r1Entrada = formatter.parse(r1.getFechaEntrada());
                        Date r2Entrada = formatter.parse(r2.getFechaEntrada());
                        return r1Entrada.compareTo(r2Entrada);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                });
                mAdapter.submitList(reservas);
            });

        });

        send = new SendAbstractionImpl(this, "Whatsapp");

        // It doesn't affect if we comment the following instruction
        registerForContextMenu(mRecyclerView);

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
                mParcelaReservaViewModel.getAllParcelaReserva().observe(this, parcela_reserva -> {
                    for(Parcela_Reserva i : parcela_reserva){
                        if(i.getReservaID() == current.getId()) {
                            mParcelaReservaViewModel.delete(i);
                        }
                    }
                });

                mReservaViewModel.delete(current);
                return true;
            case EDIT_ID:
                editReserva(current);
                return true;
            case SEND_ID:
                String mensaje = "*Datos reserva*" +"\n" +
                        "Nombre: " + current.getName() + "\n" +
                        "Móvil: " + current.getMovil() + "\n" +
                        "Fecha de entrada: " + current.getFechaEntrada() + "\n" +
                        "Fecha de salida: " + current.getFechaSalida() + "\n" +
                        "Precio total: " + current.getPrecio();
                send.send(current.getMovil(), mensaje);
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
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    /*if (result.getResultCode() == RESULT_OK) {
                        Intent replyIntent = new Intent();
                        setResult(RESULT_OK, replyIntent);
                        finish();
                    }*/
                });
    }

    private void editReserva(Reserva current) {
        Intent intent = new Intent(this, ReservaEdit.class);
        intent.putExtra(ReservaEdit.RESERVA_NAME, current.getName());
        intent.putExtra(ReservaEdit.RESERVA_PHONE, current.getMovil());
        intent.putExtra(ReservaEdit.RESERVA_ENTRADA, current.getFechaEntrada());
        intent.putExtra(ReservaEdit.RESERVA_SALIDA, current.getFechaSalida());
        intent.putExtra(ReservaEdit.RESERVA_ID, current.getId());
        intent.putExtra("1", current.getPrecio()); //Rellenar con precio
        mStartUpdateReserva.launch(intent);
    }

    ActivityResultLauncher<Intent> mStartUpdateReserva = newActivityResultLauncher(new ExecuteActivityResultReserva() {
        @Override
        public void process(Bundle extras, Reserva reserva) {
            int id = extras.getInt(ReservaEdit.RESERVA_ID);
            reserva.setId(id);
            mReservaViewModel.update(reserva);
        }
    });

}

interface ExecuteActivityResultReserva {
    void process(Bundle extras, Reserva reserva);
}