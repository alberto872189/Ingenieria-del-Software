package es.unizar.eina.camping.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Parcela.class, Reserva.class, Parcela_Reserva.class}, version = 1, exportSchema = false)
public abstract class CampingRoomDatabase extends RoomDatabase {

    public abstract ParcelaDao ParcelaDao();
    public abstract ReservaDao ReservaDao();
    public abstract ParcelaReservaDao ParcelaReservaDao();

    private static volatile CampingRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static CampingRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CampingRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    CampingRoomDatabase.class, "camping_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more notes, just add them.
                /*ParcelaDao dao = INSTANCE.ParcelaDao();
                ReservaDao dao2 = INSTANCE.ReservaDao();
                dao.deleteAll();
                dao2.deleteAll();

                Parcela parcela = new Parcela("Parcela 1's title", "Parcela 1's body", 2.5, 5);
                dao.insert(parcela);
                parcela = new Parcela("Parcela 2's title", "Parcela 2's body", 3.5, 5);
                dao.insert(parcela);*/
            });
        }
    };

}
