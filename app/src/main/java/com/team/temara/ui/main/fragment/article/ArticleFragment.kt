package com.team.temara.ui.main.fragment.article

import ArticleAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.team.temara.databinding.ArticleFragmentBinding
import com.team.temara.data.remote.response.Result

class ArticleFragment : Fragment() {
    private var _binding: ArticleFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var articleAdapter: ArticleAdapter
    private val articleViewModel: ArticleFragmentViewModel by viewModels {
        ArticleFragmentViewModel.ArticleFragmentViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ArticleFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articleAdapter = ArticleAdapter(requireContext())
        binding.rvRecArticle.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = articleAdapter
        }

        articleViewModel.checkToken().observe(viewLifecycleOwner) { token ->
            if (token != "null") {
                val myToken = "Bearer $token"
                articleViewModel.getStory(myToken).observe(viewLifecycleOwner) {
                    when (it) {
                        is Result.Success -> {
                            val articleList = it.result
                            articleAdapter.setArticleList(articleList)
                        }

                        is Result.Error -> {

                        }

                        is Result.Loading -> {
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
