package andlima.example.challengelima.room

import andlima.example.challengelima.room.favtable.FavFilm
import andlima.example.challengelima.room.favtable.FavFilmDao
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [FavFilm::class],
    version = 1
)

abstract class FavoriteFilmDatabase : RoomDatabase() {
    abstract fun favFilmDao() : FavFilmDao

    companion object{
        private var INSTANCES : FavoriteFilmDatabase? = null
        fun getInstances(context: Context) : FavoriteFilmDatabase? {
            synchronized(FavoriteFilmDatabase::class){
                INSTANCES = Room.databaseBuilder(context.applicationContext,
                FavoriteFilmDatabase::class.java,"FavFilm.db")
                    .allowMainThreadQueries().build()
            }
            return INSTANCES
        }
    }
    fun destroyInstance(){
        INSTANCES = null
    }
}