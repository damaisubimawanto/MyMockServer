package com.example.nostratest.presentation.detail_article

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.nostratest.R
import com.example.nostratest.databinding.FragmentDetailArticleBinding
import com.example.nostratest.util.DateUtil.formatTo
import com.example.nostratest.util.DateUtil.toDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailArticleFragment : Fragment() {
    private var _binding: FragmentDetailArticleBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController
    var datatitle: String? = null
    var dataauthor: String? = null
    var datapublishedAt: String? = null
    var dataurlImage: String? = null
    var datacontent: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            datatitle = it.getString("title")
            dataauthor = it.getString("author")
            datapublishedAt = it.getString("publishedAt")
            dataurlImage = it.getString("urlImage")
            datacontent = it.getString("content")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailArticleBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navController = Navigation.findNavController(view)

        datapublishedAt = datapublishedAt?.toDate()?.formatTo("dd MMM yyyy")

        init()
    }

    private fun init() = with(binding) {

        title = datatitle
        author = dataauthor
        publishedAt = datapublishedAt
        urlImage = dataurlImage
        content = datacontent
    }






    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            navController.popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}