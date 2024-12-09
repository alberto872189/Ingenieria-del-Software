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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import es.unizar.eina.T202_camping.R;
import es.unizar.eina.T202_camping.database.Parcela;
import es.unizar.eina.T202_camping.database.Reserva;
import es.unizar.eina.T202_camping.database.Parcela_Reserva;

/** Pantalla utilizada para la creación o edición de una nota */
public class ReservaOcupantes extends AppCompatActivity {

    private ReservaViewModel mReservaViewModel;
    private ParcelaViewModel mParcelaViewModel;
    private Parcela_ReservaViewModel mParcelaReservaViewModel;

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

        mReservaViewModel = new ViewModelProvider(this).get(ReservaViewModel.class);
        mParcelaViewModel = new ViewModelProvider(this).get(ParcelaViewModel.class);
        mParcelaReservaViewModel = new ViewModelProvider(this).get(Parcela_ReservaViewModel.class);

        mTextViews = new Vector<>();
        mEditTexts = new Vector<>();

         Bundle extras = getIntent().getExtras();

        /*intent.putExtra(ReservaCreate.RESERVA_NAME ,(String)extras.get(ReservaCreate.RESERVA_NAME));
        intent.putExtra(ReservaCreate.RESERVA_PHONE ,(String)extras.get(ReservaCreate.RESERVA_PHONE));
        intent.putExtra(ReservaCreate.RESERVA_ENTRADA ,(String)extras.get(ReservaCreate.RESERVA_ENTRADA));
        intent.putExtra(ReservaCreate.RESERVA_SALIDA ,(String)extras.get(ReservaCreate.RESERVA_SALIDA));*/

        ArrayList<String> parcelas = (ArrayList<String>)extras.get("parcelasSeleccionadas");
        LinearLayout layout = (LinearLayout) findViewById(R.id.holder);
        for (String parcela : parcelas) {
            android.util.Log.d("PARCELASEL", parcela);
            TextView tv = new TextView(this);
            tv.setText(parcela);
            tv.setTextSize(18);
            EditText et = new EditText(this);
            et.setTextSize(18);
            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.addView(tv, lp1);
            layout.addView(et, lp2);
            mTextViews.add(tv);
            mEditTexts.add(et);
        }

        mSaveButton = findViewById(R.id.button_save);
        mSaveButton.setOnClickListener(view -> {

            List<Parcela> ListaParcelas = mParcelaViewModel.getAllParcelas().getValue();




            Reserva reserva = new Reserva((String)extras.get(ReservaCreate.RESERVA_NAME),
                                        (String)extras.get(ReservaCreate.RESERVA_PHONE),
                                        (String)extras.get(ReservaCreate.RESERVA_ENTRADA),
                                        (String)extras.get(ReservaCreate.RESERVA_SALIDA),
                                        1.0);
            mReservaViewModel.insert(reserva);

            Vector<Integer> ocupantesPorParcela = new Vector<Integer>();
            for (EditText et : mEditTexts) {
                Integer ocupantes = Integer.valueOf(et.getText().toString());
                if (ocupantes != null) {
                    ocupantesPorParcela.add(ocupantes);
                }
            }
            ArrayList<String> seleccionadas = (ArrayList<String>)extras.get("parcelasSeleccionadas");
            int i = 0;
            for (String parcela : seleccionadas) {
                Parcela_Reserva pr = new Parcela_Reserva(parcela, reserva.getId(), ocupantesPorParcela.get(i));
                i++;
                mParcelaReservaViewModel.insert(pr);
            }

            Intent replyIntent = new Intent();
            /*int[] ocupantes = new int[mEditTexts.size()];
            int i = 0;
            for (EditText editText : mEditTexts){
                ocupantes[i] = Integer.valueOf(editText.getText().toString());
            }
            replyIntent.putExtra("ocupantes", ocupantes);*/
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
