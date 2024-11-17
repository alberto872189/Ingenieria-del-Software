package es.unizar.eina.camping.database;


import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Clase que gestiona el acceso la fuente de datos.
 * Interacciona con la base de datos a través de las clases CampingRoomDatabase y ParcelaDao.
 */
public class CampingRepository {

    private final ParcelaDao mParcelaDao;
    private final ReservaDao mReservaDao;
    private final LiveData<List<Parcela>> mAllParcelas;
    private final LiveData<List<Reserva>> mAllRerervas;

    private final long TIMEOUT = 15000;

    /**
     * Constructor de CampingRepository utilizando el contexto de la aplicación para instanciar la base de datos.
     * Alternativamente, se podría estudiar la instanciación del repositorio con una referencia a la base de datos
     * siguiendo el ejemplo de
     * <a href="https://github.com/android/architecture-components-samples/blob/main/BasicSample/app/src/main/java/com/example/android/persistence/DataRepository.java">architecture-components-samples/.../persistence/DataRepository</a>
     */
    public CampingRepository(Application application) {
        CampingRoomDatabase db = CampingRoomDatabase.getDatabase(application);
        mParcelaDao = db.ParcelaDao();
        mAllParcelas = mParcelaDao.getOrderedParcelasName();
        mAllRerervas = mReservaDao.getOrderedReservasName();
    }

    /** Devuelve un objeto de tipo LiveData con todas las notas.
     * Room ejecuta todas las consultas en un hilo separado.
     * El objeto LiveData notifica a los observadores cuando los datos cambian.
     */
    public LiveData<List<Parcela>> getAllParcelas() {
        return mAllParcelas;
    }

    public LiveData<List<Parcela>> getAllParcelasPrecio() {
        return mParcelaDao.getOrderedParcelasPrecio();
    }

    public LiveData<List<Parcela>> getAllParcelasName() {
        return mParcelaDao.getOrderedParcelasName();
    }
    public LiveData<List<Parcela>> getAllParcelasOcupantes() {
        return mParcelaDao.getOrderedParcelasOcupantes();
    }

    public LiveData<List<Reserva>> getAllReservas() {
        return mAllReservas;
    }

    public LiveData<List<Reserva>> getAllReservasName() {
        return mReservaDao.getOrderedReservasName();
    }

    public LiveData<List<Reserva>> getAllReservasMovil() {
        return mReservaDao.getOrderedReservasMovil();
    }
    public LiveData<List<Reserva>> getAllReservasFecha() {
        return mReservaDao.getOrderedReservasFecha();
    }

    /** Inserta una nota nueva en la base de datos
     * @param parcela La nota consta de: un título (parcela.getTitle()) no nulo (parcela.getTitle()!=null) y no vacío
     *             (parcela.getTitle().length()>0); y un cuerpo (parcela.getBody()) no nulo.
     * @return Si la nota se ha insertado correctamente, devuelve el identificador de la nota que se ha creado. En caso
     *         contrario, devuelve -1 para indicar el fallo.
     */
    public long insert(Parcela parcela) {
        /* Para que la App funcione correctamente y no lance una excepción, la modificación de la
         * base de datos se debe lanzar en un hilo de ejecución separado
         * (databaseWriteExecutor.submit). Para poder sincronizar la recuperación del resultado
         * devuelto por la base de datos, se puede utilizar un Future.
         */
        Future<Long> future = CampingRoomDatabase.databaseWriteExecutor.submit(
                () -> mParcelaDao.insert(parcela));
        try {
            return future.get(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            Log.d("CampingRepository", ex.getClass().getSimpleName() + ex.getMessage());
            return -1;
        }
    }

    /** Actualiza una nota en la base de datos
     * @param parcela La nota que se desea actualizar y que consta de: un identificador (parcela.getId()) mayor que 0; un
     *             título (parcela.getTitle()) no nulo y no vacío; y un cuerpo (parcela.getBody()) no nulo.
     * @return Un valor entero con el número de filas modificadas: 1 si el identificador se corresponde con una nota
     *         previamente insertada; 0 si no existe previamente una nota con ese identificador, o hay algún problema
     *         con los atributos.
     */
    public int update(Parcela parcela) {
        Future<Integer> future = CampingRoomDatabase.databaseWriteExecutor.submit(
                () -> mParcelaDao.update(parcela));
        try {
            return future.get(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            Log.d("CampingRepository", ex.getClass().getSimpleName() + ex.getMessage());
            return -1;
        }
    }


    /** Elimina una nota en la base de datos.
     * @param parcela Objeto nota cuyo atributo identificador (parcela.getId()) contiene la clave primaria de la nota que se
     *             va a eliminar de la base de datos. Se debe cumplir: parcela.getId() > 0.
     * @return Un valor entero con el número de filas eliminadas: 1 si el identificador se corresponde con una nota
     *         previamente insertada; 0 si no existe previamente una nota con ese identificador o el identificador no es
     *         un valor aceptable.
     */
    public int delete(Parcela parcela) {
        Future<Integer> future = CampingRoomDatabase.databaseWriteExecutor.submit(
                () -> mParcelaDao.delete(parcela));
        try {
            return future.get(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            Log.d("CampingRepository", ex.getClass().getSimpleName() + ex.getMessage());
            return -1;
        }
    }

    public long insert(Reserva reserva) {
        /* Para que la App funcione correctamente y no lance una excepción, la modificación de la
         * base de datos se debe lanzar en un hilo de ejecución separado
         * (databaseWriteExecutor.submit). Para poder sincronizar la recuperación del resultado
         * devuelto por la base de datos, se puede utilizar un Future.
         */
        Future<Long> future = CampingRoomDatabase.databaseWriteExecutor.submit(
                () -> mReservaDao.insert(reserva));
        try {
            return future.get(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            Log.d("CampingRepository", ex.getClass().getSimpleName() + ex.getMessage());
            return -1;
        }
    }

    public int update(Reserva reserva) {
        Future<Integer> future = CampingRoomDatabase.databaseWriteExecutor.submit(
                () -> mReservaDao.update(reserva));
        try {
            return future.get(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            Log.d("CampingRepository", ex.getClass().getSimpleName() + ex.getMessage());
            return -1;
        }
    }

    public int delete(Reserva reserva) {
        Future<Integer> future = CampingRoomDatabase.databaseWriteExecutor.submit(
                () -> mReservaDao.delete(reserva));
        try {
            return future.get(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            Log.d("CampingRepository", ex.getClass().getSimpleName() + ex.getMessage());
            return -1;
        }
    }
}
