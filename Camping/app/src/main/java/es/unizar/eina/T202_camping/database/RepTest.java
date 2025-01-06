package es.unizar.eina.T202_camping.database;

import android.app.Application;

public class RepTest {

    private CampingRepository mRepository;

    public static void ejecutar(Application application) {
        CampingRepository mRepository = new CampingRepository(application);

        //Tests insert parcela

        //test 1: insert clases correctas
        Parcela p = new Parcela("parcela1", "hola", 1.0, 1);
        Long id = mRepository.insert(p);
        android.util.Log.d("Test 1", String.valueOf(id));

        //test 2: insert name null
        Parcela p2 = new Parcela(null, "hola", 1.0, 1);
        try {
            Long id2 = mRepository.insert(p2);
            android.util.Log.d("Test 2", String.valueOf(id2));
        }
        catch (Throwable t){
            android.util.Log.d("Test 2", t.toString());
        }
        //test 3: insert name ""
        Parcela p3 = new Parcela("", "hola", 1.0, 1);
        try {
            Long id3 = mRepository.insert(p3);
            android.util.Log.d("Test 3", String.valueOf(id3));
        }
        catch (Throwable t){
            android.util.Log.d("Test 3", t.toString());
        }

        //Tests insert reserva

        //test 1: insert clases correctas
        Reserva r = new Reserva("reserva1", "111111111", "03/10/2015", "04/10/2015", 1.0);
        Long id4 = mRepository.insert(r);
        android.util.Log.d("Test 4", String.valueOf(id4));

        //test 2: insert name null
        Reserva r2 = new Reserva(null, "111111111", "03/10/2015", "04/10/2015", 1.0);
        try {
            Long id5 = mRepository.insert(r2);
            android.util.Log.d("Test 5", String.valueOf(id5));
        }
        catch (Throwable t){
            android.util.Log.d("Test 5", t.toString());
        }
        //test 3: insert name ""
        Reserva r3 = new Reserva("", "111111111", "03/10/2015", "04/10/2015", 1.0);
        try {
            Long id6 = mRepository.insert(r3);
            android.util.Log.d("Test 6", String.valueOf(id6));
        }
        catch (Throwable t){
            android.util.Log.d("Test 6", t.toString());
        }

        //Tests insert parcela_reserva

        //test 1: insert clases correctas
        Parcela_Reserva pr = new Parcela_Reserva("parcela1", id4.intValue(), 1);
        Long id7 = mRepository.insert(pr);
        android.util.Log.d("Test 7", String.valueOf(id7));

        //test 2: insert name null
        Parcela_Reserva pr2 = new Parcela_Reserva(null, id4.intValue(), 1);
        try {
            Long id8 = mRepository.insert(pr2);
            android.util.Log.d("Test 8", String.valueOf(id8));
        }
        catch (Throwable t){
            android.util.Log.d("Test 8", t.toString());
        }
        //test 3: insert name ""
        Parcela_Reserva pr3 = new Parcela_Reserva("", id4.intValue(), 1);
        try {
            Long id9 = mRepository.insert(pr3);
            android.util.Log.d("Test 9", String.valueOf(id9));
        }
        catch (Throwable t){
            android.util.Log.d("Test 9", t.toString());
        }


        mRepository.delete(p);
        mRepository.delete(p2);
        mRepository.delete(p3);

        mRepository.delete(r);
        mRepository.delete(r2);
        mRepository.delete(r3);

        mRepository.delete(pr);
        mRepository.delete(pr2);
        mRepository.delete(pr3);


    }
}
