package cz.minarik.alzatest.ui.screens.characters.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import cz.minarik.alzatest.R
import cz.minarik.alzatest.common.util.decodeSafely
import cz.minarik.alzatest.domain.model.CharacterDetail
import cz.minarik.alzatest.ui.composables.AlzaTopAppBar
import cz.minarik.alzatest.ui.composables.ErrorView
import cz.minarik.alzatest.ui.theme.AlzaTestTheme
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf


@Composable
fun CharacterDetailScreen(
    onBackClicked: () -> Unit,
    characterId: String?,
    characterName: String?
) {
    val viewModel = getViewModel<CharacterDetailScreenViewModel> {
        parametersOf(characterId)
    }
    AlzaTestTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                AlzaTopAppBar(onBackClicked = onBackClicked, text = characterName?.decodeSafely() ?: "")
            },
            content = { padding ->
                HandleState(
                    Modifier.padding(padding),
                    viewModel.state.collectAsState(initial = ProductDetailScreenState()),
                ) {
                    viewModel.getProductDetail()
                }
            }
        )
    }
}


@Composable
fun HandleState(
    modifier: Modifier,
    state: State<ProductDetailScreenState>,
    reload: () -> Unit
) {
    state.value.apply {
        Box(modifier = modifier.fillMaxSize()) {
            character?.let { CharacterDetail(character) }
            if (error.isNotBlank()) {
                ErrorView(modifier = Modifier.fillMaxSize(), error = error) {
                    reload.invoke()
                }
            }
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun CharacterDetail(character: CharacterDetail) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.height(164.dp),
            painter = rememberImagePainter(
                data = character.imageUrl ?: character.imageUrl,
            ),
            contentDescription = stringResource(id = R.string.character_image),
            contentScale = ContentScale.FillHeight
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            text = character.name ?: "",
            style = MaterialTheme.typography.h5,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            text = character.species ?: "",
            style = MaterialTheme.typography.body1,
        )
    }
}