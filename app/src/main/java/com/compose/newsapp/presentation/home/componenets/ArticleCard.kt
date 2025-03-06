package com.compose.newsapp.presentation.home.componenets


import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.compose.newsapp.R
import com.compose.newsapp.domain.model.Article
import com.compose.newsapp.domain.model.Source
import com.compose.newsapp.presentation.dimens.Dimens.ArticleCardSize
import com.compose.newsapp.presentation.dimens.Dimens.ExtraSmallPadding
import com.compose.newsapp.presentation.dimens.Dimens.ExtraSmallPadding2
import com.compose.newsapp.presentation.dimens.Dimens.SmallIconSize
import com.compose.newsapp.ui.theme.NewsAppTheme
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ArticleCard(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: (() -> Unit)? = null,
) {

    val context = LocalContext.current
    Row(
        modifier = modifier.clickable { onClick?.invoke() },

        ) {


        if (article.urlToImage?.isEmpty() == true) {
            Image(
                modifier = Modifier
                    .size(ArticleCardSize)
                    .clip(MaterialTheme.shapes.medium),
                painter = painterResource(id = R.drawable.ic_no_be_image_news),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        } else {
            AsyncImage(
                modifier = Modifier
                    .size(ArticleCardSize)
                    .clip(MaterialTheme.shapes.medium),
                model = ImageRequest.Builder(context).data(article.urlToImage).build(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(horizontal = ExtraSmallPadding)
                .height(ArticleCardSize)
        ) {
            article.title?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium.copy(),
                    color = colorResource(id = R.color.text_title),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                article.source?.name?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = colorResource(id = R.color.body)
                    )
                }
                Spacer(modifier = Modifier.width(ExtraSmallPadding2))
                Icon(
                    painter = painterResource(id = R.drawable.ic_time),
                    contentDescription = null,
                    modifier = Modifier.size(SmallIconSize),
                    tint = colorResource(id = R.color.body)
                )
                Spacer(modifier = Modifier.width(ExtraSmallPadding))

                val publishAt = article.publishedAt
                val instant = Instant.parse(publishAt)

                // Convert to IST (Indian Standard Time)
                val istZone = ZoneId.of("Asia/Kolkata")
                val istDateTime = instant.atZone(istZone)

                // Format it to "yyyy-MM-dd HH:mm" (without seconds)
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                val formattedDate = istDateTime.format(formatter)
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.labelSmall,
                    color = colorResource(id = R.color.body)
                )

            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ArticleCardPreview() {
    NewsAppTheme(dynamicColor = false) {
        ArticleCard(
            article = Article(
                author = "",
                content = "",
                description = "",
                publishedAt = "2 hours",
                source = Source(id = "", name = "BBC"),
                title = "Her train broke down. Her phone died. And then she met her Saver in a",
                url = "",
                urlToImage = "https://ichef.bbci.co.uk/live-experience/cps/624/cpsprodpb/11787/production/_124395517_bbcbreakingnewsgraphic.jpg"
            )
        )
    }
}
