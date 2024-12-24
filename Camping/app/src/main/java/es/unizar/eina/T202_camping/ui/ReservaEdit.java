package es.unizar.eina.T202_camping.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;

import es.unizar.eina.T202_camping.R;
import es.unizar.eina.T202_camping.database.Reserva;

/** Pantalla utilizada para la creación o edición de una nota */
public class ReservaEdit extends AppCompatActivity {

    public static final String RESERVA_ID = "id";
    public static final String RESERVA_NAME = "name";
    public static final String RESERVA_PHONE = "phone";
    public static final String RESERVA_ENTRADA = "entrada";
    public static final String RESERVA_SALIDA = "salida";

    private Integer mRowId;
    private EditText mNameText;

    private EditText mMovilText;

    private EditText mEntradaText;
    private EditText mSalidaText;

    //private Integer mRowId;

    Button mSaveButton;
    Button mCancelButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservaedit);

        mNameText = findViewById(R.id.name);
        mMovilText = findViewById(R.id.phone);
        mEntradaText = findViewById(R.id.entrada);
        mSalidaText = findViewById(R.id.salida);

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

        mEntradaText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpc = new DatePickerDialog(
                        ReservaEdit.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                mEntradaText.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                            }
                        },
                        year, month, day);
                dpc.show();
            }
        });

        mSalidaText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpc = new DatePickerDialog(
                        ReservaEdit.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                mSalidaText.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                            }
                        },
                        year, month, day);
                dpc.show();
            }
        });
        populateFields();

    }

    private void createReserva() {
        Intent replyIntent = new Intent(this, ReservaOcupantesEdit.class);
        if (TextUtils.isEmpty(mNameText.getText())) {
            setResult(RESULT_CANCELED, replyIntent);
            Toast.makeText(getApplicationContext(), R.string.empty_not_saved, Toast.LENGTH_LONG).show();
        } else {
            replyIntent.putExtra(ReservaEdit.RESERVA_NAME, mNameText.getText().toString());
            replyIntent.putExtra(ReservaEdit.RESERVA_PHONE, mMovilText.getText().toString());
            replyIntent.putExtra(ReservaEdit.RESERVA_ENTRADA, mEntradaText.getText().toString());
            replyIntent.putExtra(ReservaEdit.RESERVA_SALIDA, mSalidaText.getText().toString());
            if (mRowId != null) {
                replyIntent.putExtra(ReservaEdit.RESERVA_ID, String.valueOf(mRowId.intValue()));
            }
            mStartCreateReserva.launch(replyIntent);
        }

    }

    ActivityResultLauncher<Intent> mStartCreateReserva = newActivityResultLauncher(new ExecuteActivityResultReserva() {
        @Override
        public void process(Bundle extras, Reserva reserva) {
            //mReservaViewModel.insert(reserva);
        }
    });

    ActivityResultLauncher<Intent> newActivityResultLauncher(ExecuteActivityResultReserva executable) {
        return registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent replyIntent = new Intent();
                        setResult(RESULT_OK, replyIntent);
                        finish();
                    }
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
            //mReservaViewModel.update(reserva);
        }
    });

    interface ExecuteActivityResultReserva {
        void process(Bundle extras, Reserva reserva);
    }

    private void populateFields () {
        mRowId = null;
        Bundle extras = getIntent().getExtras();
        if (extras!=null) {
            mNameText.setText(extras.getString(ReservaEdit.RESERVA_NAME));
            mMovilText.setText(extras.getString(ReservaEdit.RESERVA_PHONE));
            mEntradaText.setText(extras.getString(ReservaEdit.RESERVA_ENTRADA));
            mSalidaText.setText(extras.getString(ReservaEdit.RESERVA_SALIDA));
            mRowId = extras.getInt(ReservaEdit.RESERVA_ID);
        }
    }

}
