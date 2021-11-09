import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowSize
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState


@ExperimentalComposeUiApi
@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {
    Window(
        title = "Compose for Desktop",
        state = rememberWindowState(size = WindowSize(500.dp, 350.dp))
    ) {
        MaterialTheme {
            var inputText = remember { mutableStateOf("") }
            var count = remember { mutableStateOf(0) }
            var resultText = remember { mutableStateOf("") }
            val bigNumberMultiply = BigNumberMultiply()
            Column(Modifier.fillMaxSize(), Arrangement.spacedBy(5.dp)) {
                Spacer(modifier = Modifier.height(50.dp))
                TextField(
                    value = inputText.value,
                    textStyle = TextStyle(color = Color.DarkGray),
                    onValueChange = {
                        inputText.value = it
                    },
                    modifier = Modifier.width(200.dp).align(Alignment.CenterHorizontally),
                    maxLines = 1,
//                label = {
//                    Text("请输入")
//                },
                    placeholder = {
                        Text("Input a number:")
                    },
//                    shape = RoundedCornerShape(16.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        val bigNumber = inputText.value
                        try {
                            bigNumberMultiply.setNum(bigNumber)
                            val resultList = bigNumberMultiply.factor()
                            var result = ""
                            for (i in resultList.size - 1 downTo 0){
                                result += resultList[i].toString()
                            }
                            resultText.value = result
                        }catch (e:Exception){
                            e.printStackTrace()
                        }
                    })
                )
                OutlinedTextField(
                    value = resultText.value,
                    textStyle = TextStyle(color = Color.DarkGray),
                    onValueChange = {
                        resultText.value = it
                    },
                    label = { Text("Result:")},
                    modifier = Modifier.width(200.dp).align(Alignment.CenterHorizontally),
                )
                Button(modifier = Modifier.width(120.dp).align(Alignment.CenterHorizontally),
                    onClick = {
                        val bigNumber = inputText.value
                        try {
                            bigNumberMultiply.setNum(bigNumber)
                            val resultList = bigNumberMultiply.factor()
                            var result = ""
                            for (i in resultList.size - 1 downTo 0){
                                result += resultList[i].toString()
                            }
                            resultText.value = result
                        }catch (e:Exception){
                            e.printStackTrace()
                        }
                    },
                ) {
                    Text("Begin")
                }
            }
//            Row(Modifier.fillMaxSize(), Arrangement.spacedBy(5.dp)) {
//                Button(modifier = Modifier.align(Alignment.CenterVertically),
//                    onClick = {
//                        count.value++
//                        Thread {
//                            while (true) {
//                                count.value++;
//                                Thread.sleep(1000L)
//                            }
//                        }.start()
//                    }) {
//                    Text(if (count.value == 0) "Hello World" else "Clicked ${count.value}!")
//                }
//            }
        }
    }
}
