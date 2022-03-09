package com.raj.stocksapp.view

import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * Base activity
 * abstract base activity which can be inherited by all other activities
 * @constructor Create empty Base activity
 */
@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {
}