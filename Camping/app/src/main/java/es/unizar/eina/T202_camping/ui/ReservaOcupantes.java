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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import es.unizar.eina.T202_camping.R;
import es.unizar.eina.T202_camping.database.Parcela;
import es.unizar.eina.T202_camping.database.ParcelaWithReserva;
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

    private boolean comprobarSolape(Reserva r1, Reserva r2) {
        Date r1Entrada = new Date();
        Date r1Salida = new Date();
        Date r2Entrada = new Date();
        Date r2Salida = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            r1Entrada = formatter.parse(r1.getFechaEntrada());
            r1Salida = formatter.parse(r1.getFechaSalida());
            r2Entrada = formatter.parse(r2.getFechaEntrada());
            r2Salida = formatter.parse(r2.getFechaSalida());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return (r1Salida.before(r2Entrada) || r2Salida.after(r1Entrada));
    }

    private boolean solapadas (Parcela parcela, Reserva reserva) {
        List<Reserva> listaReservas = mReservaViewModel.getAllReservas().getValue();
        for (Reserva r : listaReservas) {
            if (comprobarSolape(r, reserva)) {
                List<ParcelaWithReserva>  parcelas = mParcelaReservaViewModel.getParcelasForReserva(reserva.getId()).getValue();
                for (ParcelaWithReserva pr : parcelas) {
                    for (Parcela p : pr.parcelas) {
                        if (p.getName().equals(parcela.getName())) return false;
                    }
                }
            }
        }

        return true;
    }

    private String comprobarValidezReserva(Reserva reserva, Vector<Parcela> vectorParcelas, Vector<Integer> ocupantesPorParcela) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaEntrada = new Date();
        Date fechaSalida = new Date();
        Date diaActual = Calendar.getInstance().getTime();
        try {
            android.util.Log.d("FECHA_ENTRADA", reserva.getFechaEntrada());
            android.util.Log.d("FECHA_SALIDA", reserva.getFechaSalida());
            fechaEntrada = formatter.parse(reserva.getFechaEntrada());
            fechaSalida = formatter.parse(reserva.getFechaSalida());
            android.util.Log.d("FECHA_ENTRADA", fechaEntrada.toString());
            android.util.Log.d("FECHA_SALIDA", fechaSalida.toString());
            android.util.Log.d("FECHA_ACTUAL", diaActual.toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        if (fechaEntrada.after(fechaSalida)) return "ERROR: Fecha de salida anterior a fecha entrada";
        if (fechaEntrada.before(diaActual) || fechaEntrada.equals(diaActual)) return "ERROR: Fecha de entrada anterior a fecha actual";

        int i = 0;
        for (Parcela parcela : vectorParcelas) {
            if (ocupantesPorParcela.get(i) > parcela.getOcupantes()) return "ERROR: Se supera la capacidad de la parcela \"" + parcela.getName() + "\"";
            if(solapadas(parcela, reserva)) return "ERROR: La reserva para la parcela \"" + parcela.getName() + "\" se solapa con otra reserva";
        }

        return "Reserva creada correctamente";
    }

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


            Vector<Integer> ocupantesPorParcela = new Vector<Integer>();
            for (EditText et : mEditTexts) {
                Integer ocupantes = Integer.valueOf(et.getText().toString());
                if (ocupantes != null) {
                    ocupantesPorParcela.add(ocupantes);
                }
            }

            ArrayList<String> seleccionadas = (ArrayList<String>)extras.get("parcelasSeleccionadas");

            Double SumaPrecio = 0.0;
            int i = 0;
            for (String parcela : seleccionadas) {
                SumaPrecio += (mParcelaViewModel.getParcela(parcela).getPrecio() * ocupantesPorParcela.get(i));
                i++;
            }

            Reserva reserva = new Reserva((String)extras.get(ReservaCreate.RESERVA_NAME),
                    (String)extras.get(ReservaCreate.RESERVA_PHONE),
                    (String)extras.get(ReservaCreate.RESERVA_ENTRADA),
                    (String)extras.get(ReservaCreate.RESERVA_SALIDA),
                    SumaPrecio);

            Vector<Parcela> vectorParcelas = new Vector<Parcela>();
            for (String parcela : seleccionadas) {
                vectorParcelas.add(mParcelaViewModel.getParcela(parcela));
            }

            String msg = comprobarValidezReserva(reserva, vectorParcelas, ocupantesPorParcela);
            if (msg.equals("Reserva creada correctamente")) {
                mReservaViewModel.insert(reserva);
                i = 0;
                for (String parcela : seleccionadas) {
                    Parcela_Reserva pr = new Parcela_Reserva(parcela, reserva.getId(), ocupantesPorParcela.get(i));
                    i++;
                    mParcelaReservaViewModel.insert(pr);
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                finish();
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
