package cz.minarik.rickandmorty.ui.screens.characters.detail

import cz.minarik.rickandmorty.ui.core.model.UIEvent


/**
 * Events for [cz.minarik.rickandmorty.ui.screens.characters.detail.CharacterDetailScreen].
 */
sealed interface CharacterDetailScreenEvent : UIEvent {

    /**
     * Event for when the user clicks on the expand episodes button.
     */
    data object ExpandEpisodesClicked : CharacterDetailScreenEvent

}
