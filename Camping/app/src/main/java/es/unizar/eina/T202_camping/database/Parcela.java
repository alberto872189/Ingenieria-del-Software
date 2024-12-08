package es.unizar.eina.T202_camping.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/** Clase anotada como entidad que representa una nota y que consta de título y cuerpo */
@Entity(tableName = "parcela")
public class Parcela {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "descripcion")
    private String descripcion;

    @ColumnInfo(name = "precio")
    private Double precio;

    @ColumnInfo(name = "ocupantes")
    private Integer ocupantes;

    public Parcela(@NonNull String name, String descripcion, Double precio, Integer ocupantes) {
        this.name = name;
        this.descripcion = descripcion;
        this.precio = precio;
        this.ocupantes = ocupantes;
    }

    /** Devuelve el título de la nota */
    public String getName(){
        return this.name;
    }

    public void setName(String name) {this.name = name;}

    public Double getPrecio() {return this.precio; }

    public Integer getOcupantes() {return this.ocupantes; }

    /** Devuelve el cuerpo de la nota */
    public String getDescripcion(){
        return this.descripcion;
    }

}
