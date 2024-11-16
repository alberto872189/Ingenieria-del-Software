package es.unizar.eina.camping.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.unizar.eina.camping.R;

/** Pantalla utilizada para la creación o edición de una nota */
public class ParcelaEdit extends AppCompatActivity {

    public static final String PARCELA_NAME = "name";
    public static final String PARCELA_DESCRIPCION = "descripcion";
    public static final String PARCELA_OCUPANTES = "ocupantes";
    public static final String PARCELA_PRECIO = "precio";

    private EditText mTitleText;

    private EditText mBodyText;

    private EditText mMaxOcupantesText;
    private EditText mPriceText;

    //private Integer mRowId;

    Button mSaveButton;
    Button mCancelButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcelaedit);

        mTitleText = findViewById(R.id.title);
        mBodyText = findViewById(R.id.body);
        mMaxOcupantesText = findViewById(R.id.n_ocupantes);
        mPriceText = findViewById(R.id.precio);

        mSaveButton = findViewById(R.id.button_save);
        mSaveButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mTitleText.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
                Toast.makeText(getApplicationContext(), R.string.empty_not_saved, Toast.LENGTH_LONG).show();
            } else {
                replyIntent.putExtra(ParcelaEdit.PARCELA_NAME, mTitleText.getText().toString());
                replyIntent.putExtra(ParcelaEdit.PARCELA_DESCRIPCION, mBodyText.getText().toString());
                replyIntent.putExtra(ParcelaEdit.PARCELA_OCUPANTES, mMaxOcupantesText.getText().toString());
                replyIntent.putExtra(ParcelaEdit.PARCELA_PRECIO, mPriceText.getText().toString());
               /* if (mRowId!=null) {
                    replyIntent.putExtra(ParcelaEdit.PARCELA_OCUPANTES, mRowId.intValue());
                }*/
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
        //mRowId = null;
        Bundle extras = getIntent().getExtras();
        if (extras!=null) {
            mTitleText.setText(extras.getString(ParcelaEdit.PARCELA_NAME));
            mBodyText.setText(extras.getString(ParcelaEdit.PARCELA_DESCRIPCION));
            mMaxOcupantesText.setText(extras.getString(ParcelaEdit.PARCELA_OCUPANTES));
            mPriceText.setText(extras.getString(ParcelaEdit.PARCELA_PRECIO));
            //mRowId = extras.getInt(ParcelaEdit.PARCELA_OCUPANTES);
        }
    }

}
