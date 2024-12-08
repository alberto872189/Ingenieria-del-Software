package es.unizar.eina.T202_camping.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import es.unizar.eina.T202_camping.database.CampingRepository;
import es.unizar.eina.T202_camping.database.Reserva;

public class ReservaViewModel extends AndroidViewModel {

    private CampingRepository mRepository;

    private final LiveData<List<Reserva>> mAllReservas;

    public ReservaViewModel(Application application) {
        super(application);
        mRepository = new CampingRepository(application);
        mAllReservas = mRepository.getAllReservas();
    }

    LiveData<List<Reserva>> getAllReservas() { return mAllReservas; }

    LiveData<List<Reserva>> getAllReservasName() {return mRepository.getAllReservasName();}

    LiveData<List<Reserva>> getAllReservasMovil() {return mRepository.getAllReservasMovil();}

    LiveData<List<Reserva>> getAllReservasFecha() {return mRepository.getAllReservasFecha();}

    public void insert(Reserva reserva) { mRepository.insert(reserva); }

    public void update(Reserva reserva) { mRepository.update(reserva); }
    public void delete(Reserva reserva) { mRepository.delete(reserva); }
}
