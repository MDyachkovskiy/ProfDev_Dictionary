package gb.com.view.search

import gb.com.model.data.wordDefinition.WordDTO

interface OnFavoriteChanged {
    fun onFavoriteChanged(wordDTO: WordDTO)
}