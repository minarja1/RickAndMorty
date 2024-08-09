package cz.minarik.rickandmorty.ui.screens.characters.detail

import cz.minarik.rickandmorty.ui.core.model.UIEvent


/**
 * Events for [cz.minarik.rickandmorty.ui.screens.characters.detail.CharacterDetailScreen].
 */
sealed interface CharacterDetailScreenEvent : UIEvent {

    data object ExpandEpisodesClicked : CharacterDetailScreenEvent

}
