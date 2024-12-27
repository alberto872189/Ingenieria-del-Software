package es.unizar.eina.T202_camping.ui;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import es.unizar.eina.T202_camping.database.Parcela;
import es.unizar.eina.T202_camping.R;
import es.unizar.eina.T202_camping.database.Parcela_Reserva;

import static androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;

import java.util.List;

/** Pantalla principal de la aplicación Parcelapad */
public class Parcelapad extends AppCompatActivity {
    private ParcelaViewModel mParcelaViewModel;
    private Parcela_ReservaViewModel mParcelaReservaViewModel;

    static final int INSERT_ID = Menu.FIRST;
    static final int DELETE_ID = Menu.FIRST + 1;
    static final int EDIT_ID = Menu.FIRST + 2;

    RecyclerView mRecyclerView;

    ParcelaListAdapter mAdapter;

    Button mOrdenarPrecio;
    Button mOrdenarID;
    Button mOrdenarOcupantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcelapad);
        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new ParcelaListAdapter(new ParcelaListAdapter.ParcelaDiff());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mParcelaViewModel = new ViewModelProvider(this).get(ParcelaViewModel.class);
        mParcelaReservaViewModel = new ViewModelProvider(this).get(Parcela_ReservaViewModel.class);

        mParcelaViewModel.getAllParcelas().observe(this, parcelas -> {
            // Update the cached copy of the parcelas in the adapter.
            mAdapter.submitList(parcelas);
        });


        mOrdenarPrecio = findViewById(R.id.buttonPrecio);
        mOrdenarID = findViewById(R.id.buttonID);
        mOrdenarOcupantes = findViewById(R.id.buttonOcupantes);

        mOrdenarPrecio.setOnClickListener(view -> {
            mParcelaViewModel.getAllParcelasPrecio().observe(this, parcelas -> {
                // Update the cached copy of the parcelas in the adapter.
                mAdapter.submitList(parcelas);
            });
            //DEBUG LOG
            /*LiveData<List<Parcela>> lista = mParcelaViewModel.getAllParcelas();

            if (lista.getValue() != null) {
                for (Parcela p : lista.getValue()) {
                    android.util.Log.d("Ocupantes ", p.getOcupantes().toString());
                    android.util.Log.d("Precio ", p.getPrecio().toString());
                }
            }
            else {
                android.util.Log.d("null", "lista null");
            }*/
        });

        mOrdenarID.setOnClickListener(view -> {
            mParcelaViewModel.getAllParcelasName().observe(this, parcelas -> {
                // Update the cached copy of the parcelas in the adapter.
                mAdapter.submitList(parcelas);
            });
        });

        mOrdenarOcupantes.setOnClickListener(view -> {
            mParcelaViewModel.getAllParcelasOcupantes().observe(this, parcelas -> {
                // Update the cached copy of the parcelas in the adapter.
                mAdapter.submitList(parcelas);
            });

        });

        // It doesn't affect if we comment the following instruction
        registerForContextMenu(mRecyclerView);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case INSERT_ID:
                createParcela();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

     public boolean onContextItemSelected(MenuItem item) {
        Parcela current = mAdapter.getCurrent();
        switch (item.getItemId()) {
            case DELETE_ID:
                Toast.makeText(
                        getApplicationContext(),
                        "Deleting " + current.getName(),
                        Toast.LENGTH_LONG).show();
                mParcelaReservaViewModel.getAllParcelaReserva().observe(this, parcela_reserva -> {
                    for(Parcela_Reserva i : parcela_reserva){
                        if(i.getParcelaID().equals(current.getName())) {
                            mParcelaReservaViewModel.delete(i);
                        }
                    }
                });
                mParcelaViewModel.delete(current);
                return true;
            case EDIT_ID:
                editParcela(current);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void createParcela() {
        mStartCreateNote.launch(new Intent(this, ParcelaEdit.class));
    }

    ActivityResultLauncher<Intent> mStartCreateNote = newActivityResultLauncher(new ExecuteActivityResult() {
        @Override
        public void process(Bundle extras, Parcela parcela) {
            mParcelaViewModel.insert(parcela);
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

    private void editParcela(Parcela current) {
        Intent intent = new Intent(this, ParcelaEdit.class);
        intent.putExtra(ParcelaEdit.PARCELA_NAME, current.getName());
        intent.putExtra(ParcelaEdit.PARCELA_DESCRIPCION, current.getDescripcion());
        intent.putExtra(ParcelaEdit.PARCELA_OCUPANTES, current.getOcupantes());
        intent.putExtra(ParcelaEdit.PARCELA_PRECIO, current.getPrecio());
        mStartUpdateParcela.launch(intent);
    }

    ActivityResultLauncher<Intent> mStartUpdateParcela = newActivityResultLauncher(new ExecuteActivityResult() {
        @Override
        public void process(Bundle extras, Parcela parcela) {
            String name = extras.getString(ParcelaEdit.PARCELA_NAME);
            parcela.setName(name);
            mParcelaViewModel.update(parcela);
        }
    });

}

interface ExecuteActivityResult {
    void process(Bundle extras, Parcela parcela);
}