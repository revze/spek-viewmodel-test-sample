package com.example.belajarspek.presentation

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.belajarspek.R
import com.example.belajarspek.domain.usecase.GetPostUseCaseImpl
import com.example.belajarspek.domain.usecase.GetProfileUseCaseImpl
import com.example.belajarspek.presentation.base.BaseViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModelFactory: BaseViewModelFactory<MainViewModel>
    private lateinit var viewModel: MainViewModel
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDI()
        initViewModel()
        initViewClickListener()
    }

    private fun initViewClickListener() {
        btn_profile.setOnClickListener {
            viewModel.getProfile()
        }
        btn_post.setOnClickListener {
            viewModel.getPost()
        }
    }

    private fun initDI() {
        val profileUseCase = GetProfileUseCaseImpl()
        val postUseCase = GetPostUseCaseImpl()
        val mainViewModel = MainViewModel(profileUseCase, postUseCase)
        viewModelFactory = BaseViewModelFactory {
            mainViewModel
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.isLoading.observe(this, Observer {
            if (it) {
                progressDialog = ProgressDialog(this).apply {
                    setMessage("Please wait")
                    setCancelable(false)
                    show()
                }
            } else {
                progressDialog.dismiss()
            }
        })
        viewModel.posts.observe(this, Observer {
            Toast.makeText(this, "Post count: ${it.size}", Toast.LENGTH_LONG).show()
        })
        viewModel.profileName.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }
}
