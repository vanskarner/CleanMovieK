package com.vanskarner.cleanmoviek.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vanskarner.cleanmoviek.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
//    @Inject
//    lateinit var viewErrorFilter: ViewErrorFilter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}