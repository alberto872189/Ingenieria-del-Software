package es.unizar.eina.T202_camping.ui;

import static androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Map;
import java.util.Vector;

import es.unizar.eina.T202_camping.R;
import es.unizar.eina.T202_camping.database.Parcela;

/** Pantalla principal de la aplicación Parcelapad */
public class Parcelapad_reserva extends AppCompatActivity {
    private ParcelaViewModel mParcelaViewModel;

    static final int EDIT_ID = Menu.FIRST;


    RecyclerView mRecyclerView;

    Parcela_ReservaListAdapter mAdapter;

    Button mOrdenarPrecio;
    Button mOrdenarID;
    Button mOrdenarOcupantes;

    Button mSaveButton;
    Button mCancelButton;

    Vector<String> parcelasSeleccionadas;

    //Map<String, Integer> ocupantesEnParcelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcelapad_reserva);
        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new Parcela_ReservaListAdapter(new Parcela_ReservaListAdapter.ParcelaDiff());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mParcelaViewModel = new ViewModelProvider(this).get(ParcelaViewModel.class);

        parcelasSeleccionadas = new Vector<>();

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

        mSaveButton = findViewById(R.id.button_save);
        mSaveButton.setOnClickListener(view -> {
            createReserva();
        });

        mCancelButton = findViewById(R.id.button_cancel);
        mCancelButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            setResult(RESULT_CANCELED, replyIntent);
            finish();
        });

        // It doesn't affect if we comment the following instruction
        registerForContextMenu(mRecyclerView);

    }


    public boolean onContextItemSelected(MenuItem item) {
        item.setChecked(!item.isChecked());
        Parcela current = mAdapter.getCurrent();
        switch (item.getItemId()) {
            case EDIT_ID:
                if (!parcelasSeleccionadas.contains(current.getName())) {
                    parcelasSeleccionadas.add(current.getName());
                } else {
                    parcelasSeleccionadas.remove(current.getName());
                }
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void createReserva() {
        Intent intent = new Intent(this, ReservaOcupantes.class);
        intent.putExtra("parcelasSeleccionadas", parcelasSeleccionadas);
        Bundle extras = getIntent().getExtras();
        intent.putExtra(ReservaCreate.RESERVA_NAME, (String) extras.get(ReservaCreate.RESERVA_NAME));
        intent.putExtra(ReservaCreate.RESERVA_PHONE, (String) extras.get(ReservaCreate.RESERVA_PHONE));
        intent.putExtra(ReservaCreate.RESERVA_ENTRADA, (String) extras.get(ReservaCreate.RESERVA_ENTRADA));
        intent.putExtra(ReservaCreate.RESERVA_SALIDA, (String) extras.get(ReservaCreate.RESERVA_SALIDA));

        mStartCreateNote.launch(intent);
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
                        Intent replyIntent = new Intent();
                        setResult(RESULT_OK, replyIntent);
                        finish();
                    }
                });
    }
}