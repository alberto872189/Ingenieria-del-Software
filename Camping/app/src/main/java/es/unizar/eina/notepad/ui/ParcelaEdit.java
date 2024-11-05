package es.unizar.eina.notepad.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.unizar.eina.notepad.R;

/** Pantalla utilizada para la creación o edición de una nota */
public class ParcelaEdit extends AppCompatActivity {

    public static final String NOTE_TITLE = "title";
    public static final String NOTE_BODY = "body";
    public static final String NOTE_ID = "id";

    private EditText mTitleText;

    private EditText mBodyText;

    private Integer mRowId;

    Button mSaveButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noteedit);

        mTitleText = findViewById(R.id.title);
        mBodyText = findViewById(R.id.body);

        mSaveButton = findViewById(R.id.button_save);
        mSaveButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mTitleText.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
                Toast.makeText(getApplicationContext(), R.string.empty_not_saved, Toast.LENGTH_LONG).show();
            } else {
                replyIntent.putExtra(ParcelaEdit.NOTE_TITLE, mTitleText.getText().toString());
                replyIntent.putExtra(ParcelaEdit.NOTE_BODY, mBodyText.getText().toString());
                if (mRowId!=null) {
                    replyIntent.putExtra(ParcelaEdit.NOTE_ID, mRowId.intValue());
                }
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });

        populateFields();

    }

    private void populateFields () {
        mRowId = null;
        Bundle extras = getIntent().getExtras();
        if (extras!=null) {
            mTitleText.setText(extras.getString(ParcelaEdit.NOTE_TITLE));
            mBodyText.setText(extras.getString(ParcelaEdit.NOTE_BODY));
            mRowId = extras.getInt(ParcelaEdit.NOTE_ID);
        }
    }

}
