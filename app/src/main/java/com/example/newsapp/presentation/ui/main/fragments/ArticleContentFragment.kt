package com.example.newsapp.presentation.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.newsapp.BaseApplication
import com.example.newsapp.domain.model.Article
import com.example.newsapp.presentation.ui.base.ArticleViewModel
import com.example.newsapp.presentation.ui.main.events.ArticleEvent
import com.example.newsapp.utils.DEFAULT_ARTICLE_IMAGE
import com.example.newsapp.utils.IMAGE_HEIGHT
import com.example.newsapp.utils.loadPicture
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ArticleContentFragment : Fragment() {


    @Inject
    lateinit var application: BaseApplication

    private val viewModel: ArticleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("articleId")?.let { articleId ->
            viewModel.onTriggerEvent(ArticleEvent.GetArticleContentEvent(articleId))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                val article = viewModel.article.value
                Column() {
                    Text(text = "Empty Content")
                    article?.let {
                        ArticleView(
                            article = it,
                        )
                    }

                }
            }
        }
    }

}



@ExperimentalCoroutinesApi
@Composable
fun ArticleView(
    article: Article,
){

    ScrollableColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {


        article.urlToImage?.let { url ->
            val image = loadPicture(url = url, defaultImage = DEFAULT_ARTICLE_IMAGE).value
            image?.let { img ->
                Image(
                    bitmap = img.asImageBitmap(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .preferredHeight(IMAGE_HEIGHT.dp),
                    contentScale = ContentScale.Crop,
                )
            }

        }

    }

}