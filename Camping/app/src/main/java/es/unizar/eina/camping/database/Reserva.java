package es.unizar.eina.camping.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/** Clase anotada como entidad que representa una nota y que consta de título y cuerpo */
@Entity(tableName = "reserva")
public class Reserva {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "movil")
    private String movil;

    @ColumnInfo(name = "fechaEntrada")
    private String fechaEntrada;

    @ColumnInfo(name = "fechaSalida")
    private String fechaSalida;

    @ColumnInfo(name="precio")
    private Double precio;

    public Reserva(@NonNull String name, String movil, String fechaEntrada, String fechaSalida, Double precio) {
        this.name = name;
        this.movil = movil;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.precio = precio;
    }

    /** Devuelve el identificador de la reserva */
    public int getId(){
        return this.id;
    }

    /** Permite actualizar el identificador de la reserva */
    public void setId(int id) {
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {this.name = name;}

    public Double getPrecio() {return this.precio; }


    public String getMovil(){
        return this.movil;
    }

    public String getFechaEntrada() {return this.fechaEntrada; }

    public String getFechaSalida() {return this.fechaSalida; }


}
