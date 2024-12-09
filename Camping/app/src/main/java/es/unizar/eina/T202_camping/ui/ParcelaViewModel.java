package es.unizar.eina.T202_camping.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import es.unizar.eina.T202_camping.database.CampingRepository;
import es.unizar.eina.T202_camping.database.Parcela;

public class ParcelaViewModel extends AndroidViewModel {

    private CampingRepository mRepository;

    private final LiveData<List<Parcela>> mAllParcelas;

    public ParcelaViewModel(Application application) {
        super(application);
        mRepository = new CampingRepository(application);
        mAllParcelas = mRepository.getAllParcelas();
    }

    LiveData<List<Parcela>> getAllParcelas() { return mAllParcelas; }

    LiveData<List<Parcela>> getAllParcelasName() {return mRepository.getAllParcelasName();}

    LiveData<List<Parcela>> getAllParcelasPrecio() {return mRepository.getAllParcelasPrecio();}

    LiveData<List<Parcela>> getAllParcelasOcupantes() {return mRepository.getAllParcelasOcupantes();}

    public void insert(Parcela parcela) { mRepository.insert(parcela); }

    public void update(Parcela parcela) { mRepository.update(parcela); }
    public void delete(Parcela parcela) { mRepository.delete(parcela); }

    public Parcela getParcela(String parcela) { return mRepository.getParcela(parcela); }
}
