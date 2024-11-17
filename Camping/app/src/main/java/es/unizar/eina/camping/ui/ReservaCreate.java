package es.unizar.eina.camping.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.unizar.eina.camping.R;

/** Pantalla utilizada para la creación o edición de una nota */
public class ReservaCreate extends AppCompatActivity {

    public static final String PARCELA_TITLE = "title";
    public static final String PARCELA_BODY = "body";
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
        setContentView(R.layout.activity_parcelacreate);

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
                replyIntent.putExtra(ReservaCreate.PARCELA_TITLE, mTitleText.getText().toString());
                replyIntent.putExtra(ReservaCreate.PARCELA_BODY, mBodyText.getText().toString());
                replyIntent.putExtra(ReservaCreate.PARCELA_OCUPANTES, mMaxOcupantesText.getText().toString());
                replyIntent.putExtra(ReservaCreate.PARCELA_PRECIO, mPriceText.getText().toString());
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
            mTitleText.setText(extras.getString(ReservaCreate.PARCELA_TITLE));
            mBodyText.setText(extras.getString(ReservaCreate.PARCELA_BODY));
            mMaxOcupantesText.setText(extras.getString(ReservaCreate.PARCELA_OCUPANTES));
            mPriceText.setText(extras.getString(ReservaCreate.PARCELA_PRECIO));
            //mRowId = extras.getInt(ParcelaEdit.PARCELA_OCUPANTES);
        }
    }

}
