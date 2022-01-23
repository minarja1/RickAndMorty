package cz.minarik.alzatest.ui.composables

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.minarik.alzatest.R


@Composable
fun ErrorView(error: String, fullScreen: Boolean, onTryAgainClicked: () -> Unit) {
    if (fullScreen) {
        ErrorViewFullscreen(error, onTryAgainClicked)
    } else {
        Toast.makeText(LocalContext.current, error, Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun ErrorViewFullscreen(error: String, onTryAgainClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
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
    ErrorView(error = "Error", true) {}
}