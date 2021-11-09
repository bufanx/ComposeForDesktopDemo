import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.mouse.MouseScrollUnit
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.substring
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowSize
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.jetbrains.skija.paragraph.TextStyleAttribute


@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {
    val height = 680
    val width = 450
    var calculated = remember { mutableStateOf("") }
    var inputText = remember { mutableStateOf("0") }
    var refresh = remember { mutableStateOf(false) }
    Window(
        title = "Calculator",
        state = rememberWindowState(size = WindowSize(width.dp, height.dp))
    ) {
        MaterialTheme {
            Column(Modifier.fillMaxSize()) {
                Spacer(Modifier.height(10.dp))
                TextField(
                    value = inputText.value,
                    textStyle = TextStyle(color = Color.DarkGray,textAlign = TextAlign.Right,fontSize = 30.sp),
                    onValueChange = {
                        inputText.value = it
                    },
                    modifier = Modifier.height(70.dp).width((width - 10).dp),
                    maxLines = 1,
                    readOnly = true,
                    shape = RoundedCornerShape(5.dp),
                    label = {
                        Text(calculated.value,textAlign = TextAlign.End,color = Color.Black)
                    }
                )
                Spacer(Modifier.height(15.dp))
                Row(modifier = Modifier.height(100.dp).width(width.dp)){
                    Spacer(Modifier.width(5.dp))
                    makeButton("(",{
                        calculated.value += "(";
                        inputText.value = "("
                    })
                    makeButton(")",{
                        calculated.value += ")";
                        inputText.value = ")"
                    })
                    makeButton("C",{
                        calculated.value = calculated.value.substring(0, calculated.value.length - 1)
                        println(calculated)
                    })
                    makeButton("Back",{
                        inputText.value = ""
                        calculated.value = ""
                    })
                }
                Spacer(Modifier.height(10.dp))
                Row(modifier = Modifier.height(100.dp).width(width.dp)){
                    Spacer(Modifier.width(5.dp))
                    makeButton("7",textColor = Color.Black,color = ButtonDefaults.buttonColors(backgroundColor = Color.Gray), click = {
                        calculated.value += "7";
                        inputText.value = "7"
                    })
                    makeButton("8",textColor = Color.Black,color = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),click = {
                        calculated.value += "8";
                        inputText.value = "8"
                    })
                    makeButton("9",textColor = Color.Black,color = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),click = {
                        calculated.value += "9";
                        inputText.value = "9"
                    })
                    makeButton("➗",{
                        calculated.value += "/";
                        inputText.value = "/"
                    })
                }
                Canvas(modifier = Modifier.height(100.dp).width(width.dp)){
                    MouseScrollUnit.Line(11F)
                }
                Spacer(Modifier.height(10.dp))
                Row(modifier = Modifier.height(100.dp).width(width.dp)){
                    Spacer(Modifier.width(5.dp))
                    makeButton("4",textColor = Color.Black,color = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),click = {
                        calculated.value += "4";
                        inputText.value = "4"
                    })
                    makeButton("5",textColor = Color.Black,color = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),click = {
                        calculated.value += "5";
                        inputText.value = "5"
                    })
                    makeButton("6",textColor = Color.Black,color = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),click = {
                        calculated.value += "6";
                        inputText.value = "6"
                    })
                    makeButton("✖",{
                        calculated.value += "x";
                        inputText.value = "x"
                    })
                }
                Spacer(Modifier.height(10.dp))
                Row(modifier = Modifier.height(100.dp).width(width.dp)){
                    Spacer(Modifier.width(5.dp))
                    makeButton("1",textColor = Color.Black,color = ButtonDefaults.buttonColors(backgroundColor = Color.Gray), click = {
                        calculated.value += "1";
                        inputText.value = "1"
                    })
                    makeButton("2",textColor = Color.Black,color = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),click = {
                        calculated.value += "2";
                        inputText.value = "2"
                    })
                    makeButton("3",textColor = Color.Black,color = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),click = {
                        calculated.value += "3";
                        inputText.value = "3"
                    })
                    makeButton("➖",{
                        calculated.value += "-";
                        inputText.value = "-"
                    })
                }
                Spacer(Modifier.height(10.dp))
                Row(modifier = Modifier.height(100.dp).width(width.dp)){
                    Spacer(Modifier.width(5.dp))
                    makeButton("0",textColor = Color.Black,color = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),click = {
                        calculated.value += "0";
                        inputText.value = "0"
                    })
                    makeButton(".",textColor = Color.Black,color = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),click = {
                        calculated.value += ".";
                        inputText.value = "."
                    })
                    makeButton("＝",textColor = Color.Black,color = ButtonDefaults.buttonColors(backgroundColor = Color(0xff00ffff)), click = {
                        calculated.value += "="
                        inputText.value = FormulaUtil().caculate(calculated.value.replace("x","*")).toString()
                    })
                    makeButton("➕",{
                        calculated.value += "+";
                        inputText.value = "+"
                    })
                }
            }
        }
    }
}
@Composable
fun makeButton(text:String,
               click: () -> Unit = {},
               color: ButtonColors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
               textColor: Color = Color.Blue){
    Spacer(Modifier.width(5.dp))
    Button(modifier = Modifier.height(95.dp).width(95.dp),
        onClick = {
            click()
        },
        border = BorderStroke(2.dp,Color.Black),
        colors = color,
    ) {
        Text(text,fontSize = 25.sp,color = textColor)
    }
    Spacer(Modifier.width(5.dp))
}
