package andlima.example.challengelima.fragment_main.view

import andlima.example.challengelima.MainActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import andlima.example.challengelima.R
import andlima.example.challengelima.adapter.AdapterFilmFavorite
import andlima.example.challengelima.datastore.UserManager
import andlima.example.challengelima.fragment_main.presenter.FilmFavoritePresenter
import andlima.example.challengelima.fragment_main.presenter.FilmFavoriteView
import andlima.example.challengelima.func.toast
import andlima.example.challengelima.room.FavoriteFilmDatabase
import andlima.example.challengelima.room.favtable.FavFilm
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.coroutines.DelicateCoroutinesApi


class FavoriteFragment : Fragment(), FilmFavoriteView {

    // Get data store
    lateinit var userManager: UserManager

    private lateinit var presenter: FilmFavoritePresenter

    // Get local room database
    private var mDb : FavoriteFilmDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    @DelicateCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get something from data store
        userManager = UserManager(requireContext())

        // Get room database instance
        mDb = FavoriteFilmDatabase.getInstances(requireContext())

//        GlobalScope.async {
//            val input = mDb?.favFilmDao()?.insertNewFavorite(
//                FavFilm(
//                null,
//                "TITLE EXAMPLE",
//                "No synopsis",
//                "01 January 1001",
//                "https://i1.sndcdn.com/avatars-32EHFzqYhcwAzmuk-mE2q0g-t500x500.jpg",
//                "ADMIN",
//                1000
//            ))
//
//            (requireContext() as MainActivity).runOnUiThread {
//                if (input != 0.toLong()) {
//                    snackbarLong(requireView(), "Initial input success")
//                } else {
//                    snackbarLong(requireView(), "Initial input failed")
//                }
//            }
//        }

        presenter = FilmFavoritePresenter(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.getUSerFavoriteFilm(requireContext(), this, (requireContext() as MainActivity))
    }

    override fun onSuccess(msg: String, favFilm: List<FavFilm>?) {
        nothing_handler.visibility = View.GONE
        loading_content.visibility = View.GONE
        no_favorite_handler.visibility = View.GONE

        rv_favorite_film_list.layoutManager = LinearLayoutManager(requireContext())
        rv_favorite_film_list.adapter = AdapterFilmFavorite(favFilm) {
            val selectedData = bundleOf("SELECTED_DATA" to it)
            Navigation.findNavController(requireView())
                .navigate(R.id.action_favoriteFragment_to_detailFragment, selectedData)
        }
    }

    override fun onError(msg: String) {
        toast(requireContext(), msg)
        nothing_handler.visibility = View.GONE
        loading_content.visibility = View.GONE
        no_favorite_handler.visibility = View.VISIBLE
    }
}