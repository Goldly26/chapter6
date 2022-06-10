package andlima.example.challengelima.fragment_main.presenter

import andlima.example.challengelima.datastore.UserManager
import andlima.example.challengelima.fragment_main.view.FavoriteFragment
import andlima.example.challengelima.room.FavoriteFilmDatabase
import android.app.Activity
import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FilmFavoritePresenter(val favFilmView: FilmFavoriteView){

    //get data store
    lateinit var userManager: UserManager

    //get local room db
    private var mDB : FavoriteFilmDatabase? = null

    fun getUserID(context: Context, lifecycleOwner: LifecycleOwner, onIdFound: (Int)->Unit) : Int {
        // get something from data store
        userManager = UserManager(context)

        var id = 0
        userManager.id.asLiveData().observe(lifecycleOwner,{
            id = it.toInt()
        })

        return id
    }

    fun getUSerFavoriteFilm(context: Context, lifecycleOwner: LifecycleOwner, activity: Activity){

        // get something from data store
        userManager = UserManager(context)

        // get roomdatabase instaces
        mDB = FavoriteFilmDatabase.getInstances(context)

        GlobalScope.launch {
            activity.runOnUiThread{
                userManager.id.asLiveData().observe(lifecycleOwner,{
                    userId ->
                    val listData = mDB?.favFilmDao()?.getUsersFavoriteFilm(userId.toInt())

                    if (listData != null ) {
                        if (listData.isNotEmpty()){
                            favFilmView.onSuccess("data show",listData)
                        } else {
                            favFilmView.onError("nothimg here")
                        }
                    } else {
                        favFilmView.onError("data null")
                    }
                })
            }
        }

    }
}