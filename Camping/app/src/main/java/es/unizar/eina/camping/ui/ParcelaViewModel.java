package es.unizar.eina.camping.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import es.unizar.eina.camping.database.CampingRepository;
import es.unizar.eina.camping.database.Parcela;

public class ParcelaViewModel extends AndroidViewModel {

    private CampingRepository mRepository;

    private final LiveData<List<Parcela>> mAllNotes;

    public ParcelaViewModel(Application application) {
        super(application);
        mRepository = new CampingRepository(application);
        mAllNotes = mRepository.getAllNotes();
    }

    LiveData<List<Parcela>> getAllNotes() { return mAllNotes; }

    public void insert(Parcela parcela) { mRepository.insert(parcela); }

    public void update(Parcela parcela) { mRepository.update(parcela); }
    public void delete(Parcela parcela) { mRepository.delete(parcela); }
}
