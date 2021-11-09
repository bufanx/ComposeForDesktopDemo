import java.util.HashMap
import java.lang.StringBuilder
import java.util.LinkedList

object MyUtils {
    const val FORMAT_MAX_LENGTH = 500 // 表达式最大长度限制
    const val RESULT_DECIMAL_MAX_LENGTH = 8 // 结果小数点最大长度限制
    val symLvMap: MutableMap<Char, Int> = HashMap() // 符号优先级map

    /**
     *
     * @Title: checkFormat
     * @Desc: 检查表达式格式是否正确
     *
     * @param: str表达式
     * @return true表达式正确，false表达式错误
     */
    fun checkFormat(str: String): Boolean {
        // 校验是否以“=”结尾
        if ('=' != str[str.length - 1]) {
            return false
        }
        // 校验开头是否为数字或者“(”
        if (!(isCharNum(str[0]) || str[0] == '(')) {
            return false
        }
        var c: Char
        // 校验
        for (i in 1 until str.length - 1) {
            c = str[i]
            if (!isCorrectChar(c)) { // 字符不合法
                return false
            }
            if (!isCharNum(c)) {
                if (c == '-' || c == '+' || c == '*' || c == '/') {
                    if (c == '-' && str[i - 1] == '(') { // 1*(-2+3)的情况
                        continue
                    }
                    if (!(isCharNum(str[i - 1]) || str[i - 1] == ')')) { // 若符号前一个不是数字或者“）”时
                        return false
                    }
                }
                if (c == '.') {
                    if (!isCharNum(str[i - 1]) || !isCharNum(str[i + 1])) { // 校验“.”的前后是否位数字
                        return false
                    }
                }
            }
        }
        return isBracketCouple(str) // 校验括号是否配对
    }

    /**
     *
     * @Title: change2StandardFormat
     * @Desc: 处理表达式格式为标准格式，如2(-1+2)(3+4)改为2*(0-1+2)*(3+4)
     *
     * @param str
     * @return 标准表达式
     */
    fun change2StandardFormat(str: String): String {
        val sb = StringBuilder()
        var c: Char
        for (i in 0 until str.length) {
            c = str[i]
            if (i != 0 && c == '(' && (isCharNum(str[i - 1]) || str[i - 1] == ')')) {
                sb.append("*(")
                continue
            }
            if (c == '-' && str[i - 1] == '(') {
                sb.append("0-")
                continue
            }
            sb.append(c)
        }
        return sb.toString()
    }

    /**
     *
     * @Title: isBracketCouple
     * @Desc: 校验括号是否配对
     * @param str
     * @return 参数
     */
    fun isBracketCouple(str: String): Boolean {
        val stack = LinkedList<Char>()
        for (c in str.toCharArray()) {
            if (c == '(') {
                stack.add(c)
            } else if (c == ')') {
                if (stack.isEmpty()) {
                    return false
                }
                stack.removeLast()
            }
        }
        return if (stack.isEmpty()) {
            true
        } else {
            false
        }
    }

    /**
     *
     * @Title: formatResult
     * @Desc: 处理计算结果的显示
     *
     * @param: str计算结果
     * @return 规范的计算结果
     */
    fun formatResult(str: String): String {
        val ss = str.split("\\.").toTypedArray()
        if (ss[1].toInt() == 0) {
            return ss[0] // 整数
        }
        val s1 = StringBuilder(ss[1]).reverse().toString()
        var start = 0
        for (i in 0 until s1.length) {
            if (s1[i] != '0') {
                start = i
                break
            }
        }
        return ss[0] + "." + StringBuilder(s1.substring(start, s1.length)).reverse()
    }

    /**
     *
     * @Title: isCorrectChar
     * @Desc: 校验字符是否合法
     *
     * @param c
     * @return 参数
     */
    fun isCorrectChar(c: Char): Boolean {
        return if ('0' <= c && c <= '9' || c == '-' || c == '+' || c == '*' || c == '/' || c == '(' || c == ')' || c == '.') {
            true
        } else false
    }

    /**
     *
     * @Title: isCharNum
     * @Desc: 判断是否为数字
     *
     * @param c
     * @return
     */
    fun isCharNum(c: Char): Boolean {
        return if (c >= '0' && c <= '9') {
            true
        } else false
    }

    /**
     *
     * @Title: plus
     * @Desc: 加
     *
     * @param num1
     * @param num2
     * @return 计算结果
     */
    fun plus(num1: Double, num2: Double): Double {
        return num1 + num2
    }

    /**
     *
     * @Title: reduce
     * @Desc: 减
     *
     * @param num1
     * @param num2
     * @return 计算结果
     */
    fun reduce(num1: Double, num2: Double): Double {
        return num1 - num2
    }

    /**
     *
     * @Title: multiply
     * @Desc: 乘
     *
     * @param num1
     * @param num2
     * @return 计算结果
     */
    fun multiply(num1: Double, num2: Double): Double {
        return num1 * num2
    }

    /**
     *
     * @Title: divide
     * @Desc: 除
     *
     * @param num1
     * @param num2
     * @return 计算结果
     */
    fun divide(num1: Double, num2: Double): Double {
        return num1 / num2
    }

    init {
        symLvMap['='] = 0
        symLvMap['-'] = 1
        symLvMap['+'] = 1
        symLvMap['*'] = 2
        symLvMap['/'] = 2
        symLvMap['('] = 3
        symLvMap[')'] = 1
        // symLvMap.put('^', 3);
        // symLvMap.put('%', 3);
    }
}