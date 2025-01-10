package es.unizar.eina.T202_camping.database;

import android.app.Application;

public class VolTest {

    private CampingRepository mRepository;

    public static void ejecutar(Application application) {

        CampingRepository mRepository = new CampingRepository(application);

        //TEST 1 - 0 Parcelas 0 Reservas
        //TEST 1.1 - Insert reserva
        Reserva r = new Reserva("reserva1", "111111111", "03/10/2015", "04/10/2015", 1.0);
        Long idInsert = mRepository.insert(r);
        r.setId(idInsert.intValue());
        android.util.Log.d("Test 1.1", String.valueOf(idInsert));

        mRepository.delete(r);

        //TEST 1.2 - Insert parcela
        Parcela p = new Parcela("parcela1", "hola", 1.0, 1);
        idInsert = mRepository.insert(p);
        r.setId(idInsert.intValue());
        android.util.Log.d("Test 1.2", String.valueOf(idInsert));


        //TEST 2 - 1 Parcela 0 Reservas
        //TEST 2.1 - Update parcela
        Parcela p2 = new Parcela("parcela1", "hola 2", 1.5, 2);
        int idUpdate = mRepository.update(p2);

        android.util.Log.d("Test 2.1", String.valueOf(idUpdate));

        //TEST 2.2 - Select parcela

        //TEST 2.3 - Delete parcela
        int idDelete = mRepository.delete(p2);
        android.util.Log.d("Test 2.3", String.valueOf(idDelete));

        mRepository.insert(p);

        //TEST 2.4 - Insert parcela
        p2 = new Parcela("parcela2", "hola 2", 1.5, 2);
        idInsert = mRepository.insert(p2);
        android.util.Log.d("Test 2.4", String.valueOf(idInsert));

        //TEST 3 - 2 Parcelas 0 Reservas
        //TEST 3.1 - Update parcela
        Parcela p3 = new Parcela("parcela1", "hola 3", 2.5, 3);
        idUpdate = mRepository.update(p3);

        android.util.Log.d("Test 3.1", String.valueOf(idUpdate));

        //TEST 3.2 - Select parcela

        //TEST 3.3 - Delete parcela
        idDelete = mRepository.delete(p3);
        android.util.Log.d("Test 3.3", String.valueOf(idDelete));

        mRepository.insert(p);

        //TEST 3.4 - Insert parcela
        p3 = new Parcela("parcela3", "hola 3", 2.5, 3);
        idInsert = mRepository.insert(p3);
        android.util.Log.d("Test 3.4", String.valueOf(idInsert));


        //TEST 4 - 50 Parcelas 0 Reservas
        for (int i = 3; i < 50; i++) {
            int nParcela = i+1;
            Parcela pAux = new Parcela("parcela" + nParcela, "hola " + nParcela, 5.5, 50);
            mRepository.insert(pAux);
        }
        //TEST 4.1 - Update parcela
        Parcela p10 = new Parcela("parcela10", "hola 10", 10.5, 10);
        idUpdate = mRepository.update(p10);

        android.util.Log.d("Test 4.1", String.valueOf(idUpdate));

        //TEST 4.2 - Select parcela

        //TEST 4.3 - Delete parcela
        idDelete = mRepository.delete(p10);
        android.util.Log.d("Test 4.3", String.valueOf(idDelete));

        mRepository.insert(p10);

        //TEST 4.4 - Insert parcela
        Parcela p51 = new Parcela("parcela51", "hola 51", 6.5, 50);
        idInsert = mRepository.insert(p51);
        android.util.Log.d("Test 4.4", String.valueOf(idInsert));

        //TEST 5 - 100 Parcelas 0 Reservas
        for (int i = 51; i < 100; i++) {
            int nParcela = i+1;
            Parcela pAux = new Parcela("parcela" + nParcela, "hola " + nParcela, 5.5, 50);
            mRepository.insert(pAux);
        }
        //TEST 5.1 - Update parcela
        Parcela p75 = new Parcela("parcela75", "hola 75", 7.5, 75);
        idUpdate = mRepository.update(p75);

        android.util.Log.d("Test 5.1", String.valueOf(idUpdate));

        //TEST 5.2 - Select parcela

        //TEST 5.3 - Delete parcela
        idDelete = mRepository.delete(p75);
        android.util.Log.d("Test 5.3", String.valueOf(idDelete));

        mRepository.insert(p75);


        //TEST 6 - 0 Parcelas 1 Reserva
        for (int i = 0; i < 100; i++) {
            int nParcela = i+1;
            Parcela pAux = new Parcela("parcela" + nParcela, "hola " + nParcela, 5.5, 50);
            mRepository.delete(pAux);
        }
        idInsert = mRepository.insert(r);
        r.setId(idInsert.intValue());
        android.util.Log.d("Test 1.1", String.valueOf(idInsert));
        //TEST 6.1 - Update reserva
        Reserva r2 = new Reserva("reserva1", "222222222", "04/10/2015", "05/10/2015", 1.5);
        idUpdate = mRepository.update(r2);

        android.util.Log.d("Test 6.1", String.valueOf(idUpdate));

        //TEST 6.2 - Select reserva

        //TEST 6.3 - Delete reserva
        idDelete = mRepository.delete(r2);
        android.util.Log.d("Test 6.3", String.valueOf(idDelete));

        idInsert = mRepository.insert(r);
        r.setId(idInsert.intValue());

        //TEST 6.4 - Insert reserva
        r2 = new Reserva("reserva2", "222222222", "04/10/2015", "05/10/2015", 1.5);
        idInsert = mRepository.insert(r2);
        r2.setId(idInsert.intValue());
        android.util.Log.d("Test 6.4", String.valueOf(idInsert));


        //TEST 7 - 0 Parcelas 2 Reservas
        //TEST 7.1 - Update reserva
        Reserva r3 = new Reserva("reserva1", "333333333", "06/10/2015", "07/10/2015", 3.5);
        idUpdate = mRepository.update(r3);

        android.util.Log.d("Test 7.1", String.valueOf(idUpdate));

        //TEST 7.2 - Select reserva

        //TEST 7.3 - Delete reserva
        idDelete = mRepository.delete(r2);
        android.util.Log.d("Test 6.3", String.valueOf(idDelete));

        idInsert = mRepository.insert(r);
        r.setId(idInsert.intValue());

        //TEST 7.4 - Insert reserva
        r3 = new Reserva("reserva3", "333333333", "06/10/2015", "07/10/2015", 3.5);
        idInsert = mRepository.insert(r3);
        r3.setId(idInsert.intValue());
        android.util.Log.d("Test 7.4", String.valueOf(idInsert));


        //TEST 8 - 0 Parcelas 5000 Reservas
        for (int i = 3; i < 5000; i++) {
            int nReserva = i+1;
            Reserva rAux = new Reserva("reserva" + nReserva, String.valueOf(nReserva), "04/10/2015", "05/10/2015", 100.5);
            mRepository.insert(rAux);
        }
        //TEST 8.1 - Update reserva
        Reserva r1500 = new Reserva("reserva1500", "150015001", "06/10/2015", "07/10/2015", 150.0);
        idUpdate = mRepository.update(r1500);

        android.util.Log.d("Test 8.1", String.valueOf(idUpdate));

        //TEST 8.2 - Select reserva

        //TEST 8.3 - Delete reserva
        idDelete = mRepository.delete(r1500);
        android.util.Log.d("Test 8.3", String.valueOf(idDelete));

        idInsert = mRepository.insert(r1500);
        r.setId(idInsert.intValue());

        //TEST 8.4 - Insert reserva
        Reserva r5001 = new Reserva("reserva5001", "500150015", "06/10/2015", "07/10/2015", 500.1);
        idInsert = mRepository.insert(r5001);
        r3.setId(idInsert.intValue());
        android.util.Log.d("Test 8.4", String.valueOf(idInsert));


        //TEST 9 - 0 Parcelas 10000 Reservas
        for (int i = 5001; i < 10000; i++) {
            int nReserva = i+1;
            Reserva rAux = new Reserva("reserva" + nReserva, String.valueOf(nReserva), "05/10/2015", "08/10/2015", 1000.5);
            mRepository.insert(rAux);
        }
        //TEST 9.1 - Update reserva
        Reserva r7500 = new Reserva("reserva7500", "750075007", "06/10/2015", "07/10/2015", 750.0);
        idUpdate = mRepository.update(r7500);

        android.util.Log.d("Test 9.1", String.valueOf(idUpdate));

        //TEST 9.2 - Select reserva

        //TEST 9.3 - Delete reserva
        idDelete = mRepository.delete(r7500);
        android.util.Log.d("Test 9.3", String.valueOf(idDelete));

        idInsert = mRepository.insert(r7500);
        r.setId(idInsert.intValue());



        //TEST 10 - 100 Parcelas 10000 Reservas
        for (int i = 0; i < 100; i++) {
            int nParcela = i+1;
            Parcela pAux = new Parcela("parcela" + nParcela, "hola " + nParcela, 5.5, 50);
            mRepository.insert(pAux);
        }
        //TEST 10.1 - Update reserva
        r7500 = new Reserva("reserva7500", "750075007", "08/10/2015", "09/10/2015", 750.0);
        idUpdate = mRepository.update(r7500);

        android.util.Log.d("Test 10.1", String.valueOf(idUpdate));

        //TEST 10.2 - Select reserva

        //TEST 10.3 - Delete reserva
        idDelete = mRepository.delete(r7500);
        android.util.Log.d("Test 10.3", String.valueOf(idDelete));

        idInsert = mRepository.insert(r7500);
        r.setId(idInsert.intValue());

        //TEST 10.4 - Update parcela
        p75 = new Parcela("parcela75", "hola 75", 75.0, 750);
        idUpdate = mRepository.update(p75);

        android.util.Log.d("Test 10.4", String.valueOf(idUpdate));

        //TEST 10.5 - Select parcela

        //TEST 10.6 - Delete parcela
        idDelete = mRepository.delete(p75);
        android.util.Log.d("Test 10.6", String.valueOf(idDelete));

        mRepository.insert(p75);

        //BORRADO
        for (int i = 0; i < 100; i++) {
            int nParcela = i+1;
            Parcela pAux = new Parcela("parcela" + nParcela, "hola " + nParcela, 5.5, 50);
            mRepository.delete(pAux);
        }

        for (int i = 0; i < 10000; i++) {
            int nReserva = i+1;
            Reserva rAux = new Reserva("reserva" + nReserva, String.valueOf(nReserva), "05/10/2015", "08/10/2015", 1000.5);
            mRepository.delete(rAux);
        }
    }
}
