import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.sharp.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.drawscope.DrawContext
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawScopeMarker
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.input.mouse.MouseScrollEvent
import androidx.compose.ui.input.mouse.MouseScrollOrientation
import androidx.compose.ui.input.mouse.MouseScrollUnit
import androidx.compose.ui.input.mouse.mouseScrollFilter
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.substring
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowSize
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.skija.paragraph.TextStyleAttribute
import java.awt.Graphics2D
import java.io.File
import javax.swing.Painter
import kotlin.math.abs



@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {
    // 窗口大小
    val height = 650
    val width = 800
    // 图形类型
    var type = "square"
    // 图形列表
    val shape = mutableListOf(MyShape(0f,0f,0f,0f,Color.Black,type))
    // 是否开始绘图
    var start = false
    val triangle = remember { imageFormFile(File("C:\\Users\\bufanx\\IdeaProjects\\compose_test\\src\\main\\resources\\trangel.png")) }
    val circle = remember { imageFormFile(File("C:\\Users\\bufanx\\IdeaProjects\\compose_test\\src\\main\\resources\\circle.png")) }
    val square = remember { imageFormFile(File("C:\\Users\\bufanx\\IdeaProjects\\compose_test\\src\\main\\resources\\square.png")) }
    val reset = remember { imageFormFile(File("C:\\Users\\bufanx\\IdeaProjects\\compose_test\\src\\main\\resources\\reset.png")) }
    val clear = remember { imageFormFile(File("C:\\Users\\bufanx\\IdeaProjects\\compose_test\\src\\main\\resources\\clear.png")) }
    val line = remember { imageFormFile(File("C:\\Users\\bufanx\\IdeaProjects\\compose_test\\src\\main\\resources\\line.png")) }
    val roundRec = remember { imageFormFile(File("C:\\Users\\bufanx\\IdeaProjects\\compose_test\\src\\main\\resources\\roundRec.png")) }
    shape.plus(MyShape(0f,0f,0f,0f, Color.Black, type))
    // 存储图形
    val myShape = MyShape(0f,0f,0f,0f, Color.Black, type)
    // 预览图形
    val shaping = MyShape(0f,0f,0f,0f, Color.Black, type)
        Window(
            title = "Paint",
            state = rememberWindowState(size = WindowSize(width.dp, height.dp)),
        ) {
            MaterialTheme {
                Column {
                    var change = false
                    val padding = remember { mutableStateOf(0.dp) }
                    val color = remember { mutableStateOf(Color.Black) }
                    Row(Modifier.background(Color.White).fillMaxWidth()) {
                        Button(modifier = Modifier.height(20.dp).width(20.dp),
                            onClick = { color.value = Color.Red },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)){}
                        Button(modifier = Modifier.height(20.dp).width(20.dp),
                            onClick = { color.value = Color.Blue },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)){}
                        Button(modifier = Modifier.height(20.dp).width(20.dp),
                            onClick = { color.value = Color.Green },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)){}
                        Button(modifier = Modifier.height(20.dp).width(20.dp),
                            onClick = { color.value = Color.Yellow },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow)){}
                        Button(modifier = Modifier.height(20.dp).width(20.dp),
                            onClick = { color.value = Color.White },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)){}
                        Button(modifier = Modifier.height(20.dp).width(20.dp),
                            onClick = { color.value = Color.Black },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)){}
                        Button(modifier = Modifier.height(20.dp).width(20.dp),
                            onClick = { color.value = Color.DarkGray},
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray)){}
                        Button(modifier = Modifier.height(20.dp).width(20.dp),
                            onClick = { color.value = Color.Magenta},
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Magenta)){}
                        Button(modifier = Modifier.height(20.dp).width(20.dp),
                            onClick = { color.value = Color.Cyan},
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Cyan)){}
                    }
                    Row(Modifier.background(Color.White).fillMaxWidth()) {
                        Spacer(Modifier.width(10.dp))
                        IconButton(
                            modifier = Modifier.height(30.dp).width(30.dp),
                            onClick = {
                                type = "square"
                            },
                            content = {
                                Icon(square,
                                    contentDescription = null,
                                    modifier = Modifier.height(20.dp).width(20.dp))
                            }
                        )
                        IconButton(
                            modifier = Modifier.height(30.dp).width(30.dp),
                            onClick = {
                                type = "circle"
                            },
                            content = {
                                Icon(circle,
                                    contentDescription = null,
                                    modifier = Modifier.height(20.dp).width(20.dp))
                            }
                        )
                        IconButton(
                            modifier = Modifier.height(30.dp).width(30.dp),
                            onClick = {
                                type = "triangle"
                            },
                            content = {
                                Icon(triangle,
                                    contentDescription = null,
                                    modifier = Modifier.height(20.dp).width(20.dp))
                            }
                        )
                        IconButton(
                            modifier = Modifier.height(30.dp).width(30.dp),
                            onClick = {
                                type = "line"
                            },
                            content = {
                                Icon(line,
                                    contentDescription = null,
                                    modifier = Modifier.height(20.dp).width(20.dp))
                            }
                        )
                        IconButton(
                            modifier = Modifier.height(30.dp).width(30.dp),
                            onClick = {
                                type = "roundRec"
                            },
                            content = {
                                Icon(roundRec,
                                    contentDescription = null,
                                    modifier = Modifier.height(20.dp).width(20.dp))
                            }
                        )
                        IconButton(
                            modifier = Modifier.height(30.dp).width(30.dp),
                            onClick = {
                                if (shape.size > 0)
                                    shape.removeLast()
                                change = !change
                                if (change) padding.value = 0.00002.dp
                                else padding.value = 0.0001.dp
                            },
                            content = {
                                Icon(reset,
                                    contentDescription = null,
                                    modifier = Modifier.height(20.dp).width(20.dp))
                            }
                        )
                        IconButton(
                            modifier = Modifier.height(30.dp).width(30.dp),
                            onClick = {
                                shaping.x = 0f
                                shaping.y = 0f
                                shaping.width = 0f
                                shaping.height = 0f
                                if (shape.size > 0)
                                    shape.removeAll(shape)
                                change = !change
                                if (change) padding.value = 0.00002.dp
                                else padding.value = 0.0001.dp
                            },
                            content = {
                                Icon(clear,
                                    contentDescription = null,
                                    modifier = Modifier.height(20.dp).width(20.dp))
                            }
                        )
                    }
                    Canvas(modifier = Modifier
                            // 鼠标拖动监听
                        .pointerMoveFilter(
                            onMove = {
                                if(start){
                                    val width = abs(shaping.x - it.x)
                                    val height = abs(shaping.y - it.y)
                                    if(type == "line"){
                                        shaping.width = it.x
                                        shaping.height = it.y
                                    }else{
                                        shaping.height = height
                                        shaping.width = width
                                    }
                                    change = !change
                                    if (change) padding.value = 0.00002.dp
                                    else padding.value = 0.0001.dp
                                }
                                false
                            },
                            onExit = {
                                shaping.width = 0f
                                shaping.height = 0f
                                shaping.x = 0f
                                shaping.y = 0f
                                start = false
                                false
                            }
                        )
                            // 鼠标点击监听
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    println("press")
                                    start = true
                                    myShape.x = it.x
                                    myShape.y = it.y
                                    shaping.x = it.x
                                    shaping.y = it.y
                                    shaping.type = type
                                    shaping.color = color.value
                                    println(it.x)
                                    println(it.y)
                                },
                                onTap = {
                                    println("release")
                                    start = false
                                    val width = abs(it.x - myShape.x)
                                    val height = abs(it.y - myShape.y)
                                    if(type == "line"){
                                        myShape.width = it.x
                                        myShape.height = it.y
                                    }else{
                                        myShape.height = height
                                        myShape.width = width
                                    }
                                    shaping.x = 0f
                                    shaping.y = 0f
                                    shaping.width = 0f
                                    shaping.height = 0f
                                    shape.add(MyShape(myShape.x, myShape.y, myShape.width, myShape.height, color.value, type))
                                    change = !change
                                    if (change) padding.value = 0.00002.dp
                                    else padding.value = 0.0001.dp
                                }
                            )

                        }
                        .background(Color.LightGray)
                        .fillMaxSize()
                        .padding(padding.value)) {
                        when(shaping.type){
                            "square" -> {
                                drawRect(shaping.color,
                                    topLeft = Offset(shaping.x, shaping.y),
                                    size = Size(shaping.width, shaping.height),
                                    style = Stroke())
                            }
                            "circle" -> {
                                drawOval(
                                    shaping.color,
                                    size = Size(shaping.width,shaping.height),
                                    topLeft = Offset(shaping.x,shaping.y),
                                    style = Stroke())
                            }
                            "triangle" -> {
                                drawLine(shaping.color,
                                    start = Offset(shaping.x, shaping.y + shaping.height),
                                    end = Offset(shaping.x + shaping.width, shaping.y + shaping.height))
                                drawLine(shaping.color,
                                    start = Offset(shaping.x, shaping.y + shaping.height),
                                    end = Offset(shaping.x + shaping.width / 2, shaping.y))
                                drawLine(shaping.color,
                                    start = Offset(shaping.x + shaping.width / 2, shaping.y),
                                    end = Offset(shaping.x + shaping.width, shaping.y + shaping.height))
                            }
                            "line" -> {
                                drawLine(shaping.color,
                                start = Offset(shaping.x, shaping.y),
                                end = Offset(shaping.width, shaping.height))
                            }
                            "roundRec" -> {
                                drawRoundRect(shaping.color,
                                    topLeft = Offset(shaping.x, shaping.y),
                                    size = Size(shaping.width, shaping.height),
                                    style = Stroke(),
                                    cornerRadius = CornerRadius(10f,10f))
                            }
                            else -> {
                            }
                        }
                        shape.forEach {
                            when(it.type){
                                "square" -> {
                                    drawRect(it.color,
                                        topLeft = Offset(it.x, it.y),
                                        size = Size(it.width, it.height),
                                        style = Stroke())
                                }
                                "circle" -> {
                                    drawOval(
                                        it.color,
                                        size = Size(it.width,it.height),
                                        topLeft = Offset(it.x,it.y),
                                        style = Stroke())
                                }
                                "triangle" -> {
                                    drawLine(it.color,
                                        start = Offset(it.x, it.y + it.height),
                                        end = Offset(it.x + it.width, it.y + it.height))
                                    drawLine(it.color,
                                        start = Offset(it.x, it.y + it.height),
                                        end = Offset(it.x + it.width / 2, it.y))
                                    drawLine(it.color,
                                        start = Offset(it.x + it.width / 2, it.y),
                                        end = Offset(it.x + it.width, it.y + it.height))
                                }
                                "line" -> {
                                    drawLine(it.color,
                                        start = Offset(it.x, it.y),
                                        end = Offset(it.width, it.height))
                                }
                                "roundRec" -> {
                                    drawRoundRect(it.color,
                                        topLeft = Offset(it.x, it.y),
                                        size = Size(it.width, it.height),
                                        style = Stroke(),
                                        cornerRadius = CornerRadius(10f,10f))
                                }
                                else -> {
                                }
                            }
                        }
                    }
                }
            }
        }
}


@Composable
fun DrawColorRing(context: DrawScope) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        var radius = 300.dp
        var ringWidth = 30.dp
        Canvas(modifier = Modifier.size(radius)) {
            context.drawCircle( // 画圆
                brush = Brush.sweepGradient(listOf(Color.Red, Color.Green, Color.Red), Offset(radius.toPx() / 2f, radius.toPx() / 2f)),
                radius = radius.toPx() / 2f,
                style = Stroke(
                    width = ringWidth.toPx()
                )
            )
        }
    }
}

// 图形类
class MyShape(var x: Float, var y : Float, var width : Float, var height : Float, var color : Color, var type : String)

// 加载图片
fun imageFormFile(file: File): ImageBitmap {
    return org.jetbrains.skija.Image.makeFromEncoded(file.readBytes()).asImageBitmap()
}



