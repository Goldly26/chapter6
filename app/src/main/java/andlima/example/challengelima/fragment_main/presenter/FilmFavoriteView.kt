package andlima.example.challengelima.fragment_main.presenter

import andlima.example.challengelima.room.favtable.FavFilm

interface FilmFavoriteView {
    fun onSuccess(msg: String, favFilm: List<FavFilm>?)
    fun onError(msg : String)
}