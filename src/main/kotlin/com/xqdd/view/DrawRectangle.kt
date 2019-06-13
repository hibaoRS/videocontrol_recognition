package com.xqdd.view

import com.xqdd.view.DrawRectangle.Border.*
import javafx.scene.Cursor
import javafx.scene.input.KeyCode
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import javafx.scene.shape.Rectangle
import tornadofx.*
import kotlin.math.absoluteValue

class DrawRectangle(stroke: Paint = Color.RED) : Rectangle() {


    private var rectStartX = 0.0
    private var rectStartY = 0.0
    private var startX = 0.0
    private var startY = 0.0
    private var currPosition: Border? = null


    init {
        contextmenu {
            item("删除").action {
                this@DrawRectangle.hide()
            }
        }
        fill = Color.TRANSPARENT
        strokeWidth = 3.0
        this.stroke = stroke

        //鼠标移动时设置鼠标指针类型
        setOnMouseMoved {
            if (currPosition == null) {
                cursor = cursor(it)
            }
        }
        setOnMouseReleased {
            currPosition = null
            cursor = cursor(it)
            requestFocus()
        }


        setOnMousePressed {
            rectStartX = x
            rectStartY = y
            startX = it.x
            startY = it.y
            currPosition = calPosition(it)

            parent?.let {
                if (it is Pane) {
                    it.children.remove(this)
                    it.children.add(this)
                }
            }
        }


        setOnKeyPressed {
            if (it.code == KeyCode.DELETE) {
                if (this.stroke == Color.RED) {
                    this.hide()
                }
            }
        }

        setOnMouseEntered {
            this.stroke = Color.RED
            requestFocus()
        }


        setOnMouseDragged {
            if (MainView.currButton == null) {
                when (currPosition) {
                    LEFT, LEFT_TOP, LEFT_BOTTOM -> {
                        val width = x - it.x + width
                        if (width >= 0) {
                            x = it.x
                            this.width = width
                        }
                    }
                    RIGHT, RIGHT_TOP, RIGHT_BOTTOM -> {
                        val width = it.x - x
                        if (width >= 0) {
                            this.width = width
                        }
                    }
                    else -> {
                    }
                }
                when (currPosition) {
                    TOP, LEFT_TOP, RIGHT_TOP -> {
                        val height = y - it.y + height
                        if (height >= 0) {
                            y = it.y
                            this.height = height
                        }
                    }
                    BOTTOM, LEFT_BOTTOM, RIGHT_BOTTOM -> {
                        val height = it.y - y
                        if (height >= 0) {
                            this.height = height
                        }
                    }
                    CENTER -> {
                        x = it.x - startX + rectStartX
                        y = it.y - startY + rectStartY
                    }
                    else -> {
                    }
                }
            } else {
                this.stroke = stroke
            }
        }

        setOnMouseExited {
            if (currPosition == null) {
                this.stroke = stroke
                cursor = Cursor.DEFAULT
            }
        }
    }

    private fun cursor(it: MouseEvent): Cursor? {
        return when (calPosition(it)) {
            LEFT_TOP, RIGHT_BOTTOM -> Cursor.NW_RESIZE
            LEFT_BOTTOM, RIGHT_TOP -> Cursor.NE_RESIZE
            LEFT, RIGHT -> Cursor.H_RESIZE
            BOTTOM, TOP -> Cursor.V_RESIZE
            CENTER -> Cursor.HAND
        }
    }

    //计算鼠标边界状态
    private fun calPosition(event: MouseEvent): Border {
        val detectSize = 5.0
        var position = CENTER
        if ((event.x - x).absoluteValue <= detectSize) {
            position = LEFT
        } else if ((event.x - x - width).absoluteValue <= detectSize) {
            position = RIGHT
        }
        if ((event.y - y).absoluteValue <= detectSize) {
            position = when (position) {
                LEFT -> LEFT_TOP
                RIGHT -> RIGHT_TOP
                else -> TOP
            }
        } else if ((event.y - y - height).absoluteValue <= detectSize) {
            position = when (position) {
                LEFT -> LEFT_BOTTOM
                RIGHT -> RIGHT_BOTTOM
                else -> BOTTOM
            }
        }
        return position
    }

    enum class Border {
        LEFT, RIGHT, TOP, BOTTOM, LEFT_TOP, LEFT_BOTTOM, RIGHT_TOP, RIGHT_BOTTOM, CENTER
    }

}