package dev.kevinsalazar.tmdb.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import  dev.kevinsalazar.tmdb.screen.home.model.CategoryModel
import  dev.kevinsalazar.tmdb.ui.theme.TmdbTheme
import  dev.kevinsalazar.tmdb.utils.Constants.ALPHA_20
import  dev.kevinsalazar.tmdb.utils.Constants.ALPHA_60
import  dev.kevinsalazar.tmdb.utils.DisabledInteractionSource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import themoviedb.composeapp.generated.resources.Res
import themoviedb.composeapp.generated.resources.on_tv_category

@Composable
fun CategoryItem(
    category: CategoryModel,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    Tab(
        text = {
            Text(
                modifier = Modifier.padding(
                    top = 4.dp, bottom = 4.dp, start = 12.dp, end = 12.dp
                ),
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary
                else MaterialTheme.colorScheme.onSurface.copy(ALPHA_60),
                text = stringResource(category.categoryResourceId)
            )
        },
        selected = isSelected,
        interactionSource = DisabledInteractionSource(),
        modifier = Modifier
            .padding(8.dp)
            .clip(shape = RoundedCornerShape(24.dp))
            .border(
                border = BorderStroke(
                    1.dp,
                    if (isSelected) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurface.copy(ALPHA_20)
                ),
                shape = MaterialTheme.shapes.medium.copy(
                    all = CornerSize(24.dp)
                )
            )
            .zIndex(2f),
        onClick = onClick
    )
}

@Preview
@Composable
fun CategoryItemPreview() {
    TmdbTheme {
        CategoryItem(
            category = CategoryModel(
                categoryResourceId = Res.string.on_tv_category,
                category = ""
            ),
            isSelected = true,
            onClick = {}
        )
    }
}
