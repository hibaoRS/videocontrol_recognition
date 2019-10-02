package com.xqdd.app

import com.xqdd.view.MainView
import org.scijava.nativelib.NativeLoader
import tornadofx.App

class App : App(MainView::class) {
    companion object {
        init {
            NativeLoader.loadLibrary("native-lib")
            System.getProperties().forEach {
                println(it.key.toString() + ": " + it.value)
                println()
            }

        }

        @JvmStatic
        fun main(args: Array<String>) {
            launch(com.xqdd.app.App::class.java)
        }
    }


}