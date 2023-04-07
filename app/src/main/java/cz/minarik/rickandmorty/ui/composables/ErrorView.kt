package cz.minarik.rickandmorty.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.minarik.rickandmorty.R


@Composable
fun ErrorView(
    error: String,
    modifier: Modifier = Modifier,
    onTryAgainClicked: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            text = error,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.error,
            textAlign = TextAlign.Center,
        )
        Button(
            onClick = { onTryAgainClicked.invoke() }) {
            Text(
                style = MaterialTheme.typography.body1,
                text = stringResource(id = R.string.try_again),
                color = Color.White
            )
        }
    }
}

@Composable
@Preview
fun ErrorViewPreview() {
    ErrorView(error = "Error") {}
}
