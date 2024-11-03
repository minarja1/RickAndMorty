package cz.minarik.rickandmorty.ui.core.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cz.minarik.rickandmorty.ui.core.model.ButtonVo
import cz.minarik.rickandmorty.ui.core.model.ErrorIndicatorVo
import cz.minarik.rickandmorty.ui.core.model.StringModel


@Composable
fun ErrorIndicator(
    errorIndicatorVo: ErrorIndicatorVo,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.background.copy(
                    alpha = if (errorIndicatorVo.showOverlay) .9f else 0f
                )
            )
            .clickable(enabled = false) {},
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GeneralText(
            modifier = Modifier.padding(bottom = 16.dp),
            text = errorIndicatorVo.text,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center,
        )
        errorIndicatorVo.buttonVo?.let {
            OutlinedButton(
                onClick = it.onClick,
                content = {
                    Text(
                        style = MaterialTheme.typography.bodyLarge,
                        text = it.text.getString(),
                        color = MaterialTheme.colorScheme.onBackground
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
            errorIndicatorVo = ErrorIndicatorVo(
                text = StringModel.String("Error"),
                buttonVo = ButtonVo(
                    text = StringModel.String("Retry"),
                    onClick = {}
                )
            )
        )
    }
}
