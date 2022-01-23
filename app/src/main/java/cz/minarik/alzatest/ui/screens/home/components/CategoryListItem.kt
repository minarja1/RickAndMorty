package cz.minarik.alzatest.ui.screens.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import cz.minarik.alzatest.R
import cz.minarik.alzatest.domain.model.Category
import cz.minarik.alzatest.ui.theme.cardViewOutlineColor

@Composable
fun CategoryListItem(
    category: Category,
    onItemClick: (Category) -> Unit,
) {
    val roundedCornerShape = RoundedCornerShape(8.dp)
    Card(
        shape = roundedCornerShape,
        border = BorderStroke(1.dp, cardViewOutlineColor),
        modifier = Modifier
            .fillMaxWidth()
            .clip(roundedCornerShape)
            .padding(vertical = 8.dp)
            .clickable { onItemClick(category) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(category.imageUrl),
                contentDescription = stringResource(id = R.string.category_image),
                modifier = Modifier.size(48.dp)
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = category.name ?: "",
                style = MaterialTheme.typography.body1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
private fun CategoryListItemPreview() {
    CategoryListItem(
        category = Category(1, "Test", "https://cdn.britannica.com/77/170477-050-1C747EE3/Laptop-computer.jpg"),
        onItemClick = {})
}