package es.unizar.eina.T202_camping.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Vector;

import es.unizar.eina.T202_camping.R;
import es.unizar.eina.T202_camping.database.Parcela;

/** Pantalla utilizada para la creación o edición de una nota */
public class ReservaOcupantes extends AppCompatActivity {

    public static final String RESERVA_ID = "id";
    public static final String RESERVA_NAME = "name";
    public static final String RESERVA_PHONE = "phone";
    public static final String RESERVA_ENTRADA = "entrada";
    public static final String RESERVA_SALIDA = "salida";

    private Integer mRowId;

    Vector<TextView> mTextViews;
    Vector<EditText> mEditTexts;

    //private Integer mRowId;

    Button mSaveButton;
    Button mCancelButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservaocupantes);

        mTextViews = new Vector<>();
        mEditTexts = new Vector<>();

         Bundle extras = getIntent().getExtras();

        /*intent.putExtra(ReservaCreate.RESERVA_NAME ,(String)extras.get(ReservaCreate.RESERVA_NAME));
        intent.putExtra(ReservaCreate.RESERVA_PHONE ,(String)extras.get(ReservaCreate.RESERVA_PHONE));
        intent.putExtra(ReservaCreate.RESERVA_ENTRADA ,(String)extras.get(ReservaCreate.RESERVA_ENTRADA));
        intent.putExtra(ReservaCreate.RESERVA_SALIDA ,(String)extras.get(ReservaCreate.RESERVA_SALIDA));*/

        Vector<Parcela> parcelas = (Vector<Parcela>)extras.get("parcelasSeleccionadas");
        LinearLayout layout = (LinearLayout) findViewById(R.id.holder);
        for (Parcela parcela : parcelas) {
            TextView tv = new TextView(this);
            tv.setText(parcela.getName());
            EditText et = new EditText(this);
            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.addView(tv, lp1);
            layout.addView(et, lp2);
            mTextViews.add(tv);
            mEditTexts.add(et);
        }

        mSaveButton = findViewById(R.id.button_save);
        mSaveButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent(this, Parcelapad_reserva.class);
            replyIntent.putExtra(ReservaOcupantes.RESERVA_NAME, (String)extras.get(ReservaCreate.RESERVA_NAME));
            replyIntent.putExtra(ReservaOcupantes.RESERVA_PHONE, (String)extras.get(ReservaCreate.RESERVA_PHONE));
            replyIntent.putExtra(ReservaOcupantes.RESERVA_ENTRADA, (String)extras.get(ReservaCreate.RESERVA_ENTRADA));
            replyIntent.putExtra(ReservaOcupantes.RESERVA_SALIDA, (String)extras.get(ReservaCreate.RESERVA_SALIDA));

            replyIntent.putExtra(ReservaOcupantes.RESERVA_ID, String.valueOf(mRowId));
            startActivity(replyIntent);
            setResult(RESULT_OK, replyIntent);
            finish();
        });

        mCancelButton = findViewById(R.id.button_cancel);
        mCancelButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            setResult(RESULT_CANCELED, replyIntent);
            finish();
        });
    }
}
