import kotlin.jvm.JvmOverloads
import java.math.BigDecimal
import java.lang.StringBuffer
import java.lang.ArithmeticException
import java.util.*
import kotlin.jvm.JvmStatic

/**
 * 算式计算
 */
class FormulaUtil @JvmOverloads constructor(  // 进行除法出现无线循环小数时保留的精度
    private val scale: Int = 32
) {
    /** 数字栈：用于存储表达式中的各个数字  */
    private var numberStack: Stack<BigDecimal>? = null

    /** 符号栈：用于存储运算符和括号  */
    private var symbolStack: Stack<Char>? = null

    /**
     * 解析并计算四则运算表达式(含括号优先级)，返回计算结果
     *
     * @param numStr
     * 算术表达式(含括号)
     */
    fun caculate(numStr: String): BigDecimal? {
        var numStr = numStr
        numStr = removeStrSpace(numStr) // 去除空格
        // 如果算术表达式尾部没有‘=’号，则在尾部添加‘=’，表示结束符
        if (numStr.length > 1
            && "=" != numStr[numStr.length - 1].toString() + ""
        ) {
            numStr += "="
        }
        // 检查表达式是否合法
        if (!isStandard(numStr)) {
            System.err.println("错误：算术表达式有误！")
            return null
        }
        // 初始化栈
        if (numberStack == null) {
            numberStack = Stack()
        }
        numberStack!!.clear()
        if (symbolStack == null) {
            symbolStack = Stack()
        }
        symbolStack!!.clear()
        // 用于缓存数字，因为数字可能是多位的
        var temp = StringBuffer()
        // 从表达式的第一个字符开始处理
        for (i in 0 until numStr.length) {
            val ch = numStr[i] // 获取一个字符
            if (isNumber(ch)) { // 若当前字符是数字
                temp.append(ch) // 加入到数字缓存中
            } else { // 非数字的情况
                val tempStr = temp.toString() // 将数字缓存转为字符串
                if (!tempStr.isEmpty()) {
                    // long num = Long.parseLong(tempStr); // 将数字字符串转为长整型数
                    val num = BigDecimal(tempStr)
                    numberStack!!.push(num) // 将数字压栈
                    temp = StringBuffer() // 重置数字缓存
                }
                // 判断运算符的优先级，若当前优先级低于栈顶的优先级，则先把计算前面计算出来
                while (!comparePri(ch) && !symbolStack!!.empty()) {
                    val b = numberStack!!.pop() // 出栈，取出数字，后进先出
                    val a = numberStack!!.pop()
                    when (symbolStack!!.pop() as Char) {
                        '+' -> numberStack!!.push(a.add(b))
                        '-' -> numberStack!!.push(a.subtract(b))
                        '*' -> numberStack!!.push(a.multiply(b))
                        '/' -> try {
                            numberStack!!.push(a.divide(b))
                        } catch (e: ArithmeticException) {
                            // 进行除法出现无限循环小数时，就会抛异常，此处设置精度重新计算
                            numberStack!!.push(
                                a.divide(
                                    b, scale,
                                    BigDecimal.ROUND_HALF_EVEN
                                )
                            )
                        }
                        else -> {
                        }
                    }
                } // while循环结束
                if (ch != '=') {
                    symbolStack!!.push(ch) // 符号入栈
                    if (ch == ')') { // 去括号
                        symbolStack!!.pop()
                        symbolStack!!.pop()
                    }
                }
            }
        } // for循环结束
        return numberStack!!.pop() // 返回计算结果
    }

    /**
     * 去除字符串中的所有空格
     */
    private fun removeStrSpace(str: String?): String {
        return str?.replace(" ".toRegex(), "") ?: ""
    }

    /**
     * 检查算术表达式的基本合法性，符合返回true，否则false
     */
    private fun isStandard(numStr: String?): Boolean {
        if (numStr == null || numStr.isEmpty()) // 表达式不能为空
            return false
        val stack = Stack<Char>() // 用来保存括号，检查左右括号是否匹配
        var b = false // 用来标记'='符号是否存在多个
        for (element in numStr) {
            val n = element
            // 判断字符是否合法
            if (!(isNumber(n) || "(" == n.toString() + "" || ")" == n.toString() + "" || "+" == n.toString() + "" || "-" == n.toString() + "" || "*" == n.toString() + "" || "/" == n.toString() + "" || "=" == n
                    .toString() + "")
            ) {
                return false
            }
            // 将左括号压栈，用来给后面的右括号进行匹配
            if ("(" == n.toString() + "") {
                stack.push(n)
            }
            if (")" == n.toString() + "") { // 匹配括号
                if (stack.isEmpty() || "(" != stack.pop().toString()) // 括号是否匹配
                return false
            }
            // 检查是否有多个'='号
            if ("=" == n.toString() + "") {
                if (b) return false
                b = true
            }
        }
        // 可能会有缺少右括号的情况
        if (!stack.isEmpty()) return false
        // 检查'='号是否不在末尾
        return "=" == numStr[numStr.length - 1].toString() + ""
    }

    /**
     * 判断字符是否是0-9的数字
     */
    private fun isNumber(num: Char): Boolean {
        return num in '0'..'9' || num == '.'
    }

    /**
     * 比较优先级：如果当前运算符比栈顶元素运算符优先级高则返回true，否则返回false
     */
    private fun comparePri(symbol: Char): Boolean {
        if (symbolStack!!.empty()) { // 空栈返回ture
            return true
        }

        // 符号优先级说明（从高到低）:
        // 第1级: (
        // 第2级: * /
        // 第3级: + -
        // 第4级: )
        val top = symbolStack!!.peek() as Char // 查看堆栈顶部的对象，注意不是出栈
        if (top == '(') {
            return true
        }
        when (symbol) {
            '(' -> return true
            '*' -> {
                return top == '+' || top == '-'
            }
            '/' -> {
                return top == '+' || top == '-'
            }
            '+' -> return false
            '-' -> return false
            ')' -> return false
            '=' -> return false
            else -> {
            }
        }
        return true
    }

}