package andlima.example.challengelima.fragment_main.presenter

import andlima.example.challengelima.model.GetAllFilmResponseItem

interface FIlmDetailView {
    fun onProcessed(detail: GetAllFilmResponseItem)
    fun onError(msg: String)
}