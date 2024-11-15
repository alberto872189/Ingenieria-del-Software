package es.unizar.eina.camping.ui;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import es.unizar.eina.camping.database.Parcela;
import es.unizar.eina.camping.R;

import static androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;

/** Pantalla principal de la aplicación Parcelapad */
public class Parcelapad extends AppCompatActivity {
    private ParcelaViewModel mParcelaViewModel;

    static final int INSERT_ID = Menu.FIRST;
    static final int DELETE_ID = Menu.FIRST + 1;
    static final int EDIT_ID = Menu.FIRST + 2;

    RecyclerView mRecyclerView;

    ParcelaListAdapter mAdapter;

    FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcelapad);
        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new ParcelaListAdapter(new ParcelaListAdapter.NoteDiff());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mParcelaViewModel = new ViewModelProvider(this).get(ParcelaViewModel.class);

        mParcelaViewModel.getAllParcelas().observe(this, notes -> {
            // Update the cached copy of the notes in the adapter.
            mAdapter.submitList(notes);
        });

        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(view -> createParcela());

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
                        "Deleting " + current.getTitle(),
                        Toast.LENGTH_LONG).show();
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
                        Parcela parcela = new Parcela(extras.getString(ParcelaEdit.NOTE_TITLE),
                                extras.getString(ParcelaEdit.NOTE_BODY));
                        executable.process(extras, parcela);
                    }
                });
    }

    private void editParcela(Parcela current) {
        Intent intent = new Intent(this, ParcelaEdit.class);
        intent.putExtra(ParcelaEdit.NOTE_TITLE, current.getTitle());
        intent.putExtra(ParcelaEdit.NOTE_BODY, current.getBody());
        intent.putExtra(ParcelaEdit.NOTE_ID, current.getId());
        mStartUpdateNote.launch(intent);
    }

    ActivityResultLauncher<Intent> mStartUpdateNote = newActivityResultLauncher(new ExecuteActivityResult() {
        @Override
        public void process(Bundle extras, Parcela parcela) {
            int id = extras.getInt(ParcelaEdit.NOTE_ID);
            parcela.setId(id);
            mParcelaViewModel.update(parcela);
        }
    });

}

interface ExecuteActivityResult {
    void process(Bundle extras, Parcela parcela);
}