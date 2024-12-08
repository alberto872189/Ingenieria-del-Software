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
public interface ParcelaReservaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Parcela_Reserva parcela_reserva);

    @Update
    int update(Parcela_Reserva parcela_reserva);

    @Delete
    int delete(Parcela_Reserva parcela_reserva);

    @Query("DELETE FROM Parcela_Reserva")
    void deleteAll();

    @Query("SELECT * FROM Parcela_Reserva ORDER BY parcelaID")
    LiveData<List<Parcela_Reserva>> getOrderedParcelaReserva();

    /*@Transaction
    @Query("SELECT * FROM Parcela_Reserva")
    List <ParcelaWithReserva> getParcelaWithReserva();*/

}

