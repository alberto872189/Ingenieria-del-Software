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
import es.unizar.eina.camping.database.Parcela;
import es.unizar.eina.camping.database.Reserva;

/** Pantalla principal de la aplicación Parcelapad */
public class Reservapad extends AppCompatActivity {
    /*private ReservaViewModel mReservaViewModel;

    static final int INSERT_ID = Menu.FIRST;
    static final int DELETE_ID = Menu.FIRST + 1;
    static final int EDIT_ID = Menu.FIRST + 2;

    RecyclerView mRecyclerView;

    ReservaListAdapter mAdapter;

    Button mOrdenarPrecio;
    Button mOrdenarID;
    Button mOrdenarOcupantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcelapad);
        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new ReservaListAdapter(new ReservaListAdapter.ReservaDiff());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mReservaViewModel = new ViewModelProvider(this).get(ReservaViewModel.class);

        mReservaViewModel.getAllReservas().observe(this, reservas -> {
            // Update the cached copy of the parcelas in the adapter.
            mAdapter.submitList(reservas);
        });

        mOrdenarPrecio = findViewById(R.id.buttonPrecio);
        mOrdenarID = findViewById(R.id.buttonID);
        mOrdenarOcupantes = findViewById(R.id.buttonOcupantes);

        mOrdenarPrecio.setOnClickListener(view -> {

        });

        mOrdenarID.setOnClickListener(view -> {

        });

        mOrdenarOcupantes.setOnClickListener(view -> {

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
        mStartCreateNote.launch(new Intent(this, ReservaEdit.class));
    }

    ActivityResultLauncher<Intent> mStartCreateNote = newActivityResultLauncher(new ExecuteActivityResult() {
        @Override
        public void process(Bundle extras, Reserva reserva) {
            mReservaViewModel.insert(reserva);
        }
    });

    ActivityResultLauncher<Intent> newActivityResultLauncher(ExecuteActivityResult executable) {
        return registerForActivityResult(
                new StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Bundle extras = result.getData().getExtras();
                        Parcela parcela = new Parcela(extras.getString(ParcelaEdit.PARCELA_NAME),
                                extras.getString(ParcelaEdit.PARCELA_DESCRIPCION),
                                Double.valueOf(extras.getString(ParcelaEdit.PARCELA_PRECIO)),
                                Integer.valueOf(extras.getString(ParcelaEdit.PARCELA_OCUPANTES)));
                        executable.process(extras, parcela);
                    }
                });
    }

    private void editReserva(Reserva current) {
        Intent intent = new Intent(this, ParcelaEdit.class);
        intent.putExtra(ReservaEdit.PARCELA_NAME, current.getName());
        intent.putExtra(ReservaEdit.PARCELA_DESCRIPCION, current.getDescripcion());
        intent.putExtra(ReservaEdit.PARCELA_OCUPANTES, current.getOcupantes());
        intent.putExtra(ReservaEdit.PARCELA_PRECIO, current.getPrecio());
        mStartUpdateParcela.launch(intent);
    }

    ActivityResultLauncher<Intent> mStartUpdateParcela = newActivityResultLauncher(new ExecuteActivityResult() {
        @Override
        public void process(Bundle extras, Reserva reserva) {
            String name = ParcelaEdit.PARCELA_NAME;
            parcela.setName(name);
            mReservaViewModel.update(reserva);
        }
    });*/

}
/*
interface ExecuteActivityResultReserva {
    void process(Bundle extras, Reserva reserva);
}*/