package cz.minarik.alzatest.ui.screens.products.list.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import cz.minarik.alzatest.R
import cz.minarik.alzatest.domain.model.Product
import cz.minarik.alzatest.ui.screens.products.list.fakeProducts
import cz.minarik.alzatest.ui.theme.cardViewOutlineColor

@Composable
fun ProductListItem(
    product: Product,
    onItemClick: (Product) -> Unit,
) {
    val roundedCornerShape = RoundedCornerShape(8.dp)
    Card(
        shape = roundedCornerShape,
        border = BorderStroke(1.dp, cardViewOutlineColor),
        modifier = Modifier
            .fillMaxWidth()
            .clip(roundedCornerShape)
            .padding(vertical = 8.dp)
            .clickable { onItemClick(product) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(
                    data = product.imageUrl,
                    builder = {
                        crossfade(true)
                    }
                ),
                contentDescription = stringResource(id = R.string.product_image),
                modifier = Modifier.size(128.dp),
            )
            Box(
                modifier = Modifier
                    .height(128.dp)
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 8.dp),
                    text = product.name ?: "",
                    style = MaterialTheme.typography.body1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomEnd),
                    style = MaterialTheme.typography.body2,
                    color = Color.Red,
                    text = product.price ?: ""
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProductListItemPreview() {
    ProductListItem(fakeProducts.first(), onItemClick = {})
}
