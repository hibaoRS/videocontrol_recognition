package com.xqdd.view

import javafx.scene.control.Alert
import javafx.scene.control.Slider
import javafx.scene.control.ToggleButton
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.StackPane
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.scene.media.MediaView
import javafx.scene.paint.Color
import tornadofx.*
import kotlin.math.absoluteValue

class MainView : View("video test") {


    private var slider: Slider? = null
    private var isSeek = false
    private val width = 960.0
    private val height = 540.0

    private external fun from_cpp1(): String?
    private external fun from_cpp2(): String?

    companion object {
        var currButton: ToggleButton? = null
    }

    private val toggleGroup = ToggleGroup().apply {
        selectedToggleProperty().onChange {
            currButton = it as ToggleButton?
        }
    }

    private val player = MediaPlayer(Media("http://vfx.mtime.cn/Video/2019/03/14/mp4/190314223540373995.mp4")).apply {
        isAutoPlay = true
        isMute = true
        currentTimeProperty().onChange { duraction ->
            slider?.let { slider ->
                if (!slider.isValueChanging && !isSeek) {
                    duraction?.let {
                        slider.value = it.toSeconds().div(totalDuration.toSeconds()) * 100
                    }
                }
            }

        }
    }


    override val root = vbox {
        stackpane {
            this += MediaView(player).apply {
                fitWidth = this@MainView.width
                fitHeight = this@MainView.height
            }

            pane {
                prefWidth = this@MainView.width
                prefHeight = this@MainView.height
                val list = listOf(
                        DrawRectangle(Color.YELLOW),
                        DrawRectangle(Color.BLUE),
                        DrawRectangle(Color.GREEN))

                list.forEach {
                    this += it
                }

                var startX = 0.0
                var startY = 0.0

                setOnMousePressed {
                    startX = it.x
                    startY = it.y
                }

                setOnMouseDragged {
                    currButton?.let { currButton ->
                        val width = it.x - startX
                        val height = it.y - startY
                        val rect = list[currButton.indexInParent]
                        if (!rect.isVisible) {
                            rect.show()
                        }
                        if (width < 0) {
                            rect.x = it.x
                        } else {
                            rect.x = startX
                        }
                        if (height < 0) {
                            rect.y = it.y
                        } else {
                            rect.y = startY
                        }
                        rect.width = width.absoluteValue
                        rect.height = height.absoluteValue
                    }
                }

                setOnMouseReleased {
                    currButton?.let {
                        it.isSelected = false
                    }
                }
            }
        }
        slider = slider {
            setOnMouseReleased {
                if (it.target is StackPane) {
                    player.seek(player.totalDuration.multiply(value / 100))
                }
                isSeek = false

            }
            setOnMousePressed {
                isSeek = true
            }
        }

        hbox {
            togglebutton("rect1", toggleGroup, false)
            togglebutton("rect2", toggleGroup, false)
            togglebutton("rect3", toggleGroup, false)



            button("test") {
                action {
                    alert(Alert.AlertType.INFORMATION, from_cpp1() ?: "")
                }
            }
            button("what") {
                action {
                    alert(Alert.AlertType.INFORMATION, from_cpp2() ?: "")
                }
            }
        }

    }


}