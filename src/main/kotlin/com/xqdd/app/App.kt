package com.xqdd.app

import com.xqdd.view.MainView
import tornadofx.App

class App : App(MainView::class) {
    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }
}