import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.input.mouse.MouseScrollEvent
import androidx.compose.ui.input.mouse.MouseScrollOrientation
import androidx.compose.ui.input.mouse.MouseScrollUnit
import androidx.compose.ui.input.mouse.mouseScrollFilter
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
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
import kotlinx.coroutines.launch
import org.jetbrains.skija.paragraph.TextStyleAttribute



@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {
    val height = 450
    val width = 600
    val m =
    Window(
        title = "Calculator",
        state = rememberWindowState(size = WindowSize(width.dp, height.dp)),
    ) {
        MaterialTheme {
//            Canvas(Modifier.fillMaxSize()){
//                drawRect(
//                    color = Color.Black,
//                    topLeft = Offset(size.width * 0.25f, size.height / 4),
//                    size = Size(600F,4F)
//                )
//
//            }
            Column(modifier = Modifier
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            println("press")
                            println(it.x)
                            println(it.y)
                        },
                        onTap = {
                            println("松开")
                            println(it.x)
                            println(it.y)
                        }
                    )
                }
                .background(Color.LightGray)) {
                ProgressLinearDemo()
                ProgressCircularDemo()
                //点击
//                detectTapGesturesTest()
                //拖动
//                detectDragGesturesTest()
            }
        }
    }
}
@Composable
fun ProgressLinearDemo() {
    val rememberProgress = remember { mutableStateOf(0.1f) }

    //添加动画
    val animatedProgress = animateFloatAsState(
        targetValue = rememberProgress.value,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(Modifier.height(30.dp))
        LinearProgressIndicator(
            progress = animatedProgress.value
        )
        Spacer(Modifier.height(30.dp))
        TextButton(onClick = {
            if (rememberProgress.value < 1f) rememberProgress.value += 0.1f
            else rememberProgress.value = 0f
        }) {
            Text(text = "增加进度")
        }
    }
}
@Composable
fun ProgressCircularDemo() {
    val rememberProgress = remember { mutableStateOf(0.1f) }

    //添加动画
    val animatedProgress = animateFloatAsState(
        targetValue = rememberProgress.value,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(Modifier.height(30.dp))

        CircularProgressIndicator(
            progress = animatedProgress.value
        )

        Spacer(Modifier.height(30.dp))
        TextButton(onClick = {
            if (rememberProgress.value < 1f) rememberProgress.value += 0.1f
            else rememberProgress.value = 0f
        }) {
            Text(text = "增加进度")
        }
    }
}
@Composable
fun detectTapGesturesTest(){
    val color = remember {
        mutableStateOf(Color.Gray)
    }
    Box(modifier = Modifier
        .pointerInput(Unit) {
            detectTapGestures(
                onDoubleTap = {
                    // 双击变成红色
                    color.value = Color.Red
                },
                onLongPress = {
                    // 长按变成蓝色
//                    color.value = Color.Blue
                },
                onPress = {
                    // 按下变成黄色
                    color.value = Color.Yellow
                    println("press")
                    println(it.x)
                    println(it.y)
                },
                onTap = {
                    // 点击时候变成黑色
                    color.value = Color.Black
                    println("松开")
                    println(it.x)
                    println(it.y)
                }
            )
        }
        .size(200.dp)
        .background(color.value)){
    }
}
@Composable
fun detectDragGesturesTest(){
    Box(modifier = Modifier.fillMaxSize()) {

        var offsetX = remember { mutableStateOf(0f) }
        var offsetY = remember { mutableStateOf(0f) }

        Box(
            Modifier
                .offset { IntOffset(offsetX.value.toInt(), offsetY.value.toInt()) }
                .background(Color.Blue)
                .size(50.dp)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = {
                            println("开始拖动===")
                            println("ox:" + offsetX.value)
                            println("oy:" + offsetY.value)
                        },
                        onDragEnd = {
                            println("结束===")
                            println("ox:" + offsetX.value)
                            println("oy:" + offsetY.value)
                        },
                        onDragCancel = {
                            println("取消===")
                        },
                        onDrag = { change, dragAmount ->
                            println("拖动中===")
                            change.consumeAllChanges()
                            offsetX.value += dragAmount.x
                            offsetY.value += dragAmount.y
                        }
                    )
                }
        )
    }
}



