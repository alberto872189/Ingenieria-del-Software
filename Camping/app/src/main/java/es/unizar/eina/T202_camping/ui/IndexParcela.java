package es.unizar.eina.T202_camping.ui;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import es.unizar.eina.T202_camping.R;
import es.unizar.eina.T202_camping.database.Parcela;

public class IndexParcela extends AppCompatActivity {
    Button mCreateParcelaButton;
    Button mVerParcelaButton;

    ParcelaListAdapter mAdapter;

    private ParcelaViewModel mParcelaViewModel;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcelaindex);
        mAdapter = new ParcelaListAdapter(new ParcelaListAdapter.ParcelaDiff());

        mCreateParcelaButton = findViewById(R.id.CrearParcelaButton);
        mVerParcelaButton = findViewById(R.id.VerParcelasButton);

        mCreateParcelaButton.setOnClickListener(view -> {
            createParcela();
        });

        mVerParcelaButton.setOnClickListener(view -> {
            Intent intent = new Intent(IndexParcela.this, Parcelapad.class);
            startActivity(intent);
        });

        mParcelaViewModel = new ViewModelProvider(this).get(ParcelaViewModel.class);

        mParcelaViewModel.getAllParcelas().observe(this, parcelas -> {
            // Update the cached copy of the parcelas in the adapter.
            mAdapter.submitList(parcelas);
        });
    }

    private void createParcela() {
        mStartCreateParcela.launch(new Intent(IndexParcela.this, ParcelaCreate.class));
    }

    ActivityResultLauncher<Intent> mStartCreateParcela = newActivityResultLauncher(new ExecuteActivityResult() {
        @Override
        public void process(Bundle extras, Parcela parcela) {
            mParcelaViewModel.insert(parcela);
        }
    });

    ActivityResultLauncher<Intent> newActivityResultLauncher(ExecuteActivityResult executable) {
        return registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
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
}
