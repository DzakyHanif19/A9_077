package com.example.ujianakhir.ui.viewmodel

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ujianakhir.BioskopApp
import com.example.ujianakhir.ui.viewmodel.film.FilmDetailViewModel
import com.example.ujianakhir.ui.viewmodel.film.FilmInsertViewModel
import com.example.ujianakhir.ui.viewmodel.penayangan.HomePenayanganViewModel
import com.example.ujianakhir.ui.viewmodel.penayangan.PenayanganDetailViewModel
import com.example.ujianakhir.ui.viewmodel.penayangan.PenayanganInsertViewModel
import com.example.ujianakhir.ui.viewmodel.penayangan.PenayanganUpdateViewModel
import com.example.ujianakhir.ui.viewmodel.studio.HomeStudioViewModel
import com.example.ujianakhir.ui.viewmodel.studio.StudioDetailViewModel
import com.example.ujianakhir.ui.viewmodel.studio.StudioInsertViewModel
import com.example.ujianakhir.ui.viewmodel.tiket.HomeTiketViewModel
import com.example.ujianakhir.ui.viewmodel.tiket.TiketDetailViewModel
import com.example.ujianakhir.ui.viewmodel.tiket.TiketInsertViewModel
import com.example.ujianakhir.ui.viewmodel.tiket.TiketUpdateViewModel
import com.example.ujianakhir.viewmodel.FilmUpdateViewModel
import com.example.ujianakhir.viewmodel.HomeFilmViewModel
import com.example.ujianakhir.viewmodel.StudioUpdateViewModel

object PenyediaViewModel {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    val Factory = viewModelFactory {
        initializer { HomeFilmViewModel(bioskopApp().container.filmRepo) }
        initializer { FilmDetailViewModel(createSavedStateHandle(),bioskopApp().container.filmRepo)}
        initializer { FilmUpdateViewModel(createSavedStateHandle(),bioskopApp().container.filmRepo) }
        initializer { FilmInsertViewModel(bioskopApp().container.filmRepo) }

        initializer { HomeStudioViewModel(bioskopApp().container.studioRepo) }
        initializer { StudioDetailViewModel(createSavedStateHandle(),bioskopApp().container.studioRepo) }
        initializer { StudioUpdateViewModel(createSavedStateHandle(),bioskopApp().container.studioRepo) }
        initializer { StudioInsertViewModel(bioskopApp().container.studioRepo) }

        initializer { HomePenayanganViewModel(bioskopApp().container.penayanganRepo) }
        initializer { PenayanganDetailViewModel(createSavedStateHandle(),bioskopApp().container.penayanganRepo) }
        initializer { PenayanganUpdateViewModel(createSavedStateHandle(),bioskopApp().container.penayanganRepo) }
        initializer { PenayanganInsertViewModel (bioskopApp().container.penayanganRepo) }

        initializer { HomeTiketViewModel(bioskopApp().container.tiketRepo) }
        initializer { TiketDetailViewModel(createSavedStateHandle(),bioskopApp().container.tiketRepo) }
        initializer { TiketUpdateViewModel(createSavedStateHandle(),bioskopApp().container.tiketRepo) }
        initializer { TiketInsertViewModel(bioskopApp().container.tiketRepo) }



    }
}

fun CreationExtras.bioskopApp(): BioskopApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BioskopApp)