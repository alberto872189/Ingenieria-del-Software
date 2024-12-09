package es.unizar.eina.T202_camping.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

/** Clase anotada como entidad que representa una nota y que consta de título y cuerpo */
@Entity(tableName = "parcela_reserva", primaryKeys = {"name", "id"})
public class Parcela_Reserva {
    @NonNull
    @ColumnInfo(name = "name")
    private String parcelaID;

    @NonNull
    @ColumnInfo(name = "id")
    private Integer reservaID;

    @ColumnInfo(name = "ocupantes")
    private Integer ocupantes;

    @ColumnInfo(name = "precio")
    private Double precio;

    public Parcela_Reserva(String parcelaID, Integer reservaID, Integer ocupantes, Double precio) {
        this.parcelaID = parcelaID;
        this.reservaID = reservaID;
        this.ocupantes = ocupantes;
        this.precio = precio;
    }

    public String getParcelaID(){
        return this.parcelaID;
    }

    public Integer getReservaID() {return this.reservaID; }

    public Integer getOcupantes() {return this.ocupantes; }

    public Double getPrecio() {return this.precio; }


}
