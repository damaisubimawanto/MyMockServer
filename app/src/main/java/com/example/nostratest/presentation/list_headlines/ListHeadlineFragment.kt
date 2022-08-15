package com.example.nostratest.presentation.list_headlines

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nostratest.R
import com.example.nostratest.databinding.FragmentListHeadlineBinding
import com.example.nostratest.presentation.list_headlines.adapter.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListHeadlineFragment : Fragment() {

    private val TAG = "LISTHEADLINEFRAGMENT"
    private var _binding: FragmentListHeadlineBinding? = null
    private val binding get() = _binding!!
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var navController: NavController
    private val viewModel: NewsViewModel by viewModels()
    private var pageToLoad = 1
    private var totalResult = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListHeadlineBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        initRv()
        handleLoadMore()
        handleItemClick()
        viewModel.getNews(1)
        uiUpdate()
    }

    private fun initRv(){
        newsAdapter = NewsAdapter()
        binding.apply {
            rvNews.setHasFixedSize(true)
            rvNews.layoutManager = LinearLayoutManager(requireContext())
            val divider = DividerItemDecoration(requireContext(), (rvNews.layoutManager as LinearLayoutManager).orientation)
            rvNews.addItemDecoration(divider)
            rvNews.adapter = newsAdapter
        }
    }

    private fun handleItemClick(){
        newsAdapter.onClickItem = { article ->
            val bundle = bundleOf(
                "title" to article.title,
                "author" to article.author,
                "publishedAt" to article.publishedAt,
                "urlImage" to article.urlToImage,
                "content" to article.content
            )
            navController.navigate(R.id.action_listHeadlineFragment_to_detailArticleFragment, bundle)
        }
    }

    private fun handleLoadMore(){
        newsAdapter.onLoadMore = {
            val totalPage = calculateTotalPage()
            pageToLoad ++
            if(pageToLoad <= totalPage){
                viewModel.getNews(pageToLoad)
            } else {
                Toast.makeText(requireContext(), getString(R.string.no_data_to_load), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uiUpdate(){
        binding.pbHeadline.visibility = View.VISIBLE
        lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                when(uiState){
                    is NewsViewModel.UiState.Loading -> {
                        binding.pbHeadline.visibility = View.VISIBLE
                    }

                    is NewsViewModel.UiState.Error -> {
                        val error = uiState.message
                        showErrorDialog(error)
                        binding.pbHeadline.visibility = View.GONE
                    }

                    is NewsViewModel.UiState.Success -> {
                        binding.pbHeadline.visibility = View.GONE
                        val list = uiState.data?.articles ?: emptyList()
                        totalResult = uiState.data?.totalResults!!
                        val listSize = newsAdapter.itemCount

                        if (listSize >= 5){
                            newsAdapter.updateData(list)
                        }  else  {
                            newsAdapter.setData(list)
                        }
                    }
                }
            }
        }
    }

    private fun calculateTotalPage(): Int{
        val x:Double = totalResult.toDouble() / 5
        val totalPage = Math.ceil(x)
        return totalPage.toInt()
    }

    private fun showErrorDialog(errorMessage: String){
        val alertDialogBuilder = AlertDialog.Builder(requireContext())

        val layoutDialog = LayoutInflater.from(requireContext()).inflate(R.layout.check_internet_dialog, null)
        alertDialogBuilder.setView(layoutDialog)

        val btn = layoutDialog.findViewById<Button>(R.id.btn_retry)
        val text = layoutDialog.findViewById<TextView>(R.id.errorMessage)

        text.text = errorMessage

        val dialog = alertDialogBuilder.create()
        dialog.show()
        dialog.setCancelable(false)
        dialog.window!!.setGravity(Gravity.CENTER)

        btn.setOnClickListener {
            pageToLoad = 1
            newsAdapter.clearData()
            dialog.dismiss()
            viewModel.getNews(1)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onStop() {
        super.onStop()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}