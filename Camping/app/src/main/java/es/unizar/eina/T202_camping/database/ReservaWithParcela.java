package es.unizar.eina.T202_camping.database;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class ReservaWithParcela {
    @Embedded
    Parcela parcela;

    @Relation(
            parentColumn = "name",
            entityColumn = "id",
            associateBy = @Junction(Parcela_Reserva.class)
    )

    public List<Reserva> reservas;
}
