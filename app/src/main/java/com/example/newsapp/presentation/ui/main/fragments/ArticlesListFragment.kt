package com.example.newsapp.presentation.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.newsapp.BaseApplication
import com.example.newsapp.domain.model.Article
import com.example.newsapp.presentation.ui.base.ArticleListViewModel
import com.example.newsapp.utils.DEFAULT_ARTICLE_IMAGE
import com.example.newsapp.utils.LoadingArticleListShimmer
import com.example.newsapp.utils.loadPicture
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ArticlesListFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel: ArticleListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                val articles = viewModel.articles.value
                Column() {
                    Column {
                        Column(
                            modifier = Modifier.padding(top = 5.dp, bottom = 1.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        )
                        {

                        }
                        Column(
                            modifier = Modifier.padding(10.dp).padding(top = 5.dp),
                        ) {
                            Row() {
                                //if list is empty make animation LoadingRecipeListShimmer(200)
                                if(articles.isEmpty()){
                                    LoadingArticleListShimmer(200)
                                }else{
                                    getComposeList(itemViewStates = articles, onSelectArticle = {
                                        val bundle = Bundle()
                                        bundle.putInt("articleId", it)
                                        //handle navigation
                                        findNavController().navigate(com.example.newsapp.R.id.action_articlesListFragment_to_articleContentFragment, bundle)
                                        Toast.makeText(
                                                application.applicationContext,
                                                "FragmenlistArticle",
                                                Toast.LENGTH_SHORT
                                        ).show()
                                    })
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalCoroutinesApi
@Composable
fun getComposeList(
        itemViewStates: List<Article>,
        onSelectArticle: (Int) -> Unit,
) {
    LazyColumn {
        itemsIndexed(
            items = itemViewStates
        ){index, item ->
            /*getListItem(itemViewState = item, onClick = { onSelectArticle})*/
            getListItem(itemViewState = item, onSelectArticle = { onSelectArticle }, onClick = { onSelectArticle })
        }
    }
}

@ExperimentalCoroutinesApi
@Composable
fun getListItem(
        itemViewState: Article,
        onSelectArticle: (Int) -> Unit,
        onClick: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp,
            )
            .height(280.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 8.dp,
        backgroundColor = Color.White
    ) {

        Column(
            modifier = Modifier.padding()
        ) {

            itemViewState.urlToImage?.let { url ->
                val image = loadPicture(
                    url = url,
                    defaultImage = DEFAULT_ARTICLE_IMAGE
                ).value
                image?.let { img ->
                    Image(
                        bitmap = img.asImageBitmap(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .preferredHeight(225.dp),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
            Spacer(modifier = Modifier.padding(1.dp))
            Column() {
                itemViewState.title?.let { Text(text = it) }
            }
        }
    }
}
