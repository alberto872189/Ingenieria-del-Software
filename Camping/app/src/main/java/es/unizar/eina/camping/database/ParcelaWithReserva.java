package es.unizar.eina.camping.database;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class ParcelaWithReserva {
    @Embedded
    Reserva reserva;

    @Relation(
            parentColumn = "reservaID",
            entityColumn = "parcelaID",
            associateBy = @Junction(Parcela_Reserva.class)
    )

    public List<Parcela> parcelas;
}
