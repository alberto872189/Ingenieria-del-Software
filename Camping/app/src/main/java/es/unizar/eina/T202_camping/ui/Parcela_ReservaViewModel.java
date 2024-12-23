package es.unizar.eina.T202_camping.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import es.unizar.eina.T202_camping.database.CampingRepository;
import es.unizar.eina.T202_camping.database.ParcelaWithReserva;
import es.unizar.eina.T202_camping.database.Parcela_Reserva;

public class Parcela_ReservaViewModel extends AndroidViewModel {

    private CampingRepository mRepository;

    private final LiveData<List<Parcela_Reserva>> mAllParcela_Reservas;

    public Parcela_ReservaViewModel(Application application) {
        super(application);
        mRepository = new CampingRepository(application);
        mAllParcela_Reservas = mRepository.getAllParcelaReserva();
    }

    LiveData<List<Parcela_Reserva>> getAllParcelaReserva() { return mAllParcela_Reservas; }
    List<ParcelaWithReserva> getParcelasForReserva(int id) { return mRepository.getParcelaForReserva(id);}

    public void insert(Parcela_Reserva pr) { mRepository.insert(pr); }
    public void update(Parcela_Reserva pr) { mRepository.update(pr); }
    public void delete(Parcela_Reserva pr) { mRepository.delete(pr); }
}
