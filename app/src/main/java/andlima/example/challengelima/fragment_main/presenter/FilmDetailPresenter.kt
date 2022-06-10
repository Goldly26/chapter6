package andlima.example.challengelima.fragment_main.presenter

import andlima.example.challengelima.datastore.UserManager
import andlima.example.challengelima.fragment_main.view.DetailFragment
import andlima.example.challengelima.model.GetAllFilmResponseItem
import andlima.example.challengelima.room.FavoriteFilmDatabase

class FilmDetailPresenter(val detailView: FIlmDetailView, val detailFIlm: GetAllFilmResponseItem) {

    //data Store
    lateinit var userManager: UserManager

    //Local DB
    private var mDB: FavoriteFilmDatabase? = null

    fun getFilmDetail() {
        detailView.onProcessed(detailFIlm)
    }

}