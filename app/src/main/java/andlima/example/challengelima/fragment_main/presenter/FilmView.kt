package andlima.example.challengelima.fragment_main.presenter

import andlima.example.challengelima.model.GetAllFilmResponseItem

interface FilmView {
    fun onSuccess(msg: String, film: List<GetAllFilmResponseItem>)
    fun onError(msg: String)
}