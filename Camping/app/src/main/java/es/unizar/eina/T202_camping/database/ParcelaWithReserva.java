package es.unizar.eina.T202_camping.database;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class ParcelaWithReserva {
    @Embedded
    Reserva reserva;

    @Relation(
            parentColumn = "id",
            entityColumn = "name",
            associateBy = @Junction(Parcela_Reserva.class)
    )

    public List<Parcela> parcelas;
}
