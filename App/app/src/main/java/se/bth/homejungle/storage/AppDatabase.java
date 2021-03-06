package se.bth.homejungle.storage;

import android.content.Context;
import android.net.Uri;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import se.bth.homejungle.storage.dao.CalendarManager;
import se.bth.homejungle.storage.dao.CategoryManager;
import se.bth.homejungle.storage.dao.FuturePlantManager;
import se.bth.homejungle.storage.dao.PlantManager;
import se.bth.homejungle.storage.dao.SpeciesManager;
import se.bth.homejungle.storage.entity.FuturePlant;
import se.bth.homejungle.storage.entity.Plant;
import se.bth.homejungle.storage.entity.Species;
import se.bth.homejungle.storage.entity.SpeciesCategory;

@Database(entities = {Plant.class, FuturePlant.class, Species.class, SpeciesCategory.class}, version = 10, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    /**
     * Singleton instance of the database
     */
    private static volatile AppDatabase INSTANCE;

    private static String PACKAGE_NAME;

    public abstract PlantManager getPlantManager();
    public abstract FuturePlantManager getFuturePlantManager();
    public abstract SpeciesManager getSpeciesManager();
    public abstract CategoryManager getCategoryManager();
    public abstract CalendarManager getCalendarManager();

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    RoomDatabase.Callback rdc = new RoomDatabase.Callback() {
                        public void onCreate(SupportSQLiteDatabase db) {
                            Executors.newSingleThreadScheduledExecutor().execute(() -> {
                                DatabaseInitializer initializer = new DatabaseInitializer(INSTANCE);
                                initializer.initializeDatabase();
                            });
                        }

                        public void onOpen(SupportSQLiteDatabase db) {
                            // do nothing
                        }
                    };

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "home_jungle_10")
                            .addCallback(rdc)
                            .fallbackToDestructiveMigration()
                            .build();

                    PACKAGE_NAME = context.getApplicationContext().getPackageName();

                    // dummy select statement to run 'onCreate' callback
                    // which is only executed before first read/write operation
                    INSTANCE.query("SELECT 1", null);
                }
            }
        }
        return INSTANCE;
    }

    public static Uri getUriForFileName(String fileName) {
        return Uri.parse("android.resource://" + PACKAGE_NAME + "/drawable/" + fileName);
    }
}
