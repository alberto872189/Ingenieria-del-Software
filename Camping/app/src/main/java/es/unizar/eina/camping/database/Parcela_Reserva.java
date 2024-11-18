package es.unizar.eina.camping.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/** Clase anotada como entidad que representa una nota y que consta de título y cuerpo */
@Entity(tableName = "parcela_reserva")
public class Parcela_Reserva {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "parcelaID")
    private String parcelaID;

    @ColumnInfo(name = "reservaID")
    private Integer reservaID;

    @ColumnInfo(name = "ocupantes")
    private Integer ocupantes;

    public Parcela_Reserva(@NonNull String parcelaID, Integer reservaID, Integer ocupantes) {
        this.parcelaID = parcelaID;
        this.reservaID = reservaID;
        this.ocupantes = ocupantes;
    }

    /** Devuelve el título de la nota */
    public String getParcelaID(){
        return this.parcelaID;
    }

    public Integer getReservaID() {return this.reservaID; }

    public Integer getOcupantes() {return this.ocupantes; }


}
