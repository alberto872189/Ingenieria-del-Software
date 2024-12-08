package es.unizar.eina.T202_camping.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.unizar.eina.T202_camping.R;

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
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mNameText.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
                Toast.makeText(getApplicationContext(), R.string.empty_not_saved, Toast.LENGTH_LONG).show();
            } else {
                replyIntent.putExtra(ReservaCreate.RESERVA_NAME, mNameText.getText().toString());
                replyIntent.putExtra(ReservaCreate.RESERVA_PHONE, mMovilText.getText().toString());
                replyIntent.putExtra(ReservaCreate.RESERVA_ENTRADA, mEntradaText.getText().toString());
                replyIntent.putExtra(ReservaCreate.RESERVA_SALIDA, mSalidaText.getText().toString());
                if (mRowId!=null) {
                    replyIntent.putExtra(ReservaCreate.RESERVA_ID, mRowId.intValue());
                }
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });

        mCancelButton = findViewById(R.id.button_cancel);
        mCancelButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            setResult(RESULT_CANCELED, replyIntent);
            finish();
        });

        populateFields();

    }

    private void populateFields () {
        mRowId = null;
        Bundle extras = getIntent().getExtras();
        if (extras!=null) {
            mNameText.setText(extras.getString(ReservaCreate.RESERVA_NAME));
            mMovilText.setText(extras.getString(ReservaCreate.RESERVA_PHONE));
            mEntradaText.setText(extras.getString(ReservaCreate.RESERVA_ENTRADA));
            mSalidaText.setText(extras.getString(ReservaCreate.RESERVA_SALIDA));
            mRowId = extras.getInt(ReservaCreate.RESERVA_ID);
        }
    }

}
