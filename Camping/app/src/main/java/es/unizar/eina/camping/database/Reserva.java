package es.unizar.eina.camping.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/** Clase anotada como entidad que representa una nota y que consta de título y cuerpo */
@Entity(tableName = "reserva")
public class Reserva {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "movil")
    private String movil;

    @ColumnInfo(name = "fechaEntrada")
    private String fechaEntrada;

    @ColumnInfo(name = "fechaSalida")
    private String fechaSalida;

    @ColumnInfo(name = "parcelas")
    private String[] parcelas;

    @ColumnInfo(name="ocupantesPorParcela")
    private Integer[] ocupantesPorParcela;

    @ColumnInfo(name="precio")
    private Double precio;

    public Reserva(@NonNull String name, String movil, String fechaEntrada, String fechaSalida, String[] parcelas, Integer[] ocupantesPorParcela) {
        this.name = name;
        this.movil = movil;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.parcelas = parcelas;
        this.ocupantesPorParcela = ocupantesPorParcela;
    }

    /** Devuelve el título de la nota */
    public String getName(){
        return this.name;
    }

    public void setName(String name) {this.name = name;}

    public Double getPrecio() {return this.precio; }

    public Integer[] getOcupantesPorParcela() {return this.ocupantesPorParcela; }

    public String getMovil(){
        return this.movil;
    }

    public String getFechaEntrada() {return this.fechaEntrada; }

    public String getFechaSalida() {return this.fechaSalida; }

    public String[] getParcelas() {return this.parcelas; }

}
