package cz.minarik.rickandmorty.ui.core.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cz.minarik.rickandmorty.ui.core.model.ButtonVo
import cz.minarik.rickandmorty.ui.core.model.ErrorViewVo


@Composable
fun ErrorIndicator(
    errorViewVo: ErrorViewVo,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colors.background.copy(
                    alpha = if (errorViewVo.showOverlay) .9f else 0f
                )
            )
            .clickable(enabled = false) {},
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            text = errorViewVo.text,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.error,
            textAlign = TextAlign.Center,
        )
        errorViewVo.buttonVo?.let {
            Button(
                onClick = it.onClick,
                content = {
                    Text(
                        style = MaterialTheme.typography.body1,
                        text = it.text,
                        color = MaterialTheme.colors.onBackground
                    )
                }
            )
        }
    }
}

@Composable
@ComponentPreview
fun ErrorViewPreview() {
    PreviewSurface {
        ErrorIndicator(
            errorViewVo = ErrorViewVo(
                text = "Error",
                buttonVo = ButtonVo(
                    text = "Retry",
                    onClick = {}
                )
            )
        )
    }
}
