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
public interface ReservaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Reserva reserva);

    @Update
    int update(Reserva reserva);

    @Delete
    int delete(Reserva reserva);

    @Query("DELETE FROM Reserva")
    void deleteAll();

    @Query("SELECT * FROM Reserva ORDER BY name ASC")
    LiveData<List<Reserva>> getOrderedReservasName();


    @Query("SELECT * FROM Reserva ORDER BY movil ASC")
    LiveData<List<Reserva>> getOrderedReservasMovil();

    @Query("SELECT * FROM Reserva ORDER BY fechaEntrada ASC")
    LiveData<List<Reserva>> getOrderedReservasFecha();
}

