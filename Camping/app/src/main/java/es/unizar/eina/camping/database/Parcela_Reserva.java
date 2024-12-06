package es.unizar.eina.camping.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Junction;
import androidx.room.PrimaryKey;

/** Clase anotada como entidad que representa una nota y que consta de título y cuerpo */
@Entity(tableName = "parcela_reserva", primaryKeys = {"parcelaID", "reservaID"})
public class Parcela_Reserva {
    @NonNull
    @ColumnInfo(name = "parcelaID")
    private String parcelaID;

    @NonNull
    @ColumnInfo(name = "reservaID")
    private Integer reservaID;

    @ColumnInfo(name = "ocupantes")
    private Integer ocupantes;

    public Parcela_Reserva(String parcelaID, Integer reservaID, Integer ocupantes) {
        this.parcelaID = parcelaID;
        this.reservaID = reservaID;
        this.ocupantes = ocupantes;
    }

    public String getParcelaID(){
        return this.parcelaID;
    }

    public Integer getReservaID() {return this.reservaID; }

    public Integer getOcupantes() {return this.ocupantes; }


}
