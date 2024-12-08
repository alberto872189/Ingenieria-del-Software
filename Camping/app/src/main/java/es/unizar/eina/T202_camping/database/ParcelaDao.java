package es.unizar.eina.T202_camping.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/** Definición de un Data Access Object para las notas */
@Dao
public interface ParcelaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Parcela parcela);

    @Update
    int update(Parcela parcela);

    @Delete
    int delete(Parcela parcela);

    @Query("DELETE FROM Parcela")
    void deleteAll();

    @Query("SELECT * FROM Parcela ORDER BY name ASC")
    LiveData<List<Parcela>> getOrderedParcelasName();


    @Query("SELECT * FROM Parcela ORDER BY precio ASC")
    LiveData<List<Parcela>> getOrderedParcelasPrecio();

    @Query("SELECT * FROM Parcela ORDER BY ocupantes ASC")
    LiveData<List<Parcela>> getOrderedParcelasOcupantes();
}

