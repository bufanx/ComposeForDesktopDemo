import MyUtils.formatResult
import MyUtils.checkFormat
import MyUtils.change2StandardFormat
import MyUtils.plus
import MyUtils.reduce
import MyUtils.multiply
import MyUtils.divide
import java.util.*
import kotlin.collections.ArrayList
import kotlin.jvm.JvmStatic

class Calculator {
    /**
     *
     * @Title: PrepareParam
     * @Desc: 准备计算的数据，符号
     *
     * @param str计算式
     * @return 计算结果
     */
    fun prepareParam(str: String?): Double? {
        // 空值校验
        var str = str
        if (null == str || "" == str) {
            return null
        }
        // 长度校验
        if (str.length > MyUtils.FORMAT_MAX_LENGTH) {
            println("表达式过长！")
            return null
        }
        // 预处理
        str = str.replace(" ".toRegex(), "") // 去空格
        if ('-' == str[0]) { // 开头为负数，如-1，改为0-1
            str = "0$str"
        }
        // 校验格式
        if (!checkFormat(str)) {
            println("表达式错误！")
            return null
        }
        // 处理表达式，改为标准表达式
        str = change2StandardFormat(str)
        // 拆分字符和数字
        val nums = str.split("[^.0-9]").toTypedArray()
        val numLst: List<Double> = ArrayList()
        for (i in nums.indices) {
            if ("" != nums[i]) {
                numLst.plus(nums[i].toDouble())
            }
        }
        val symStr = str.replace("[.0-9]".toRegex(), "")
        return doCalculate(symStr, numLst)
    }

    /**
     *
     * @Title: doCalculate
     * @Desc: 计算
     *
     * @param symStr符号串
     * @param numLst数字集合
     * @return 计算结果
     */
    fun doCalculate(symStr: String, numLst: List<Double?>): Double? {
        val symStack: LinkedList<Char> = LinkedList() // 符号栈
        val numStack: LinkedList<Double> = LinkedList() // 数字栈
        val result = 0.0
        var i = 0 // numLst的标志位
        var j = 0 // symStr的标志位
        var sym: Char // 符号
        var num1: Double
        var num2: Double // 符号前后两个数
        while (symStack.isEmpty() || !(symStack.getLast() === '=' && symStr[j] == '=')) { // 形如：
            // =8=
            // 则退出循环，结果为8
            if (symStack.isEmpty()) {
                symStack.add('=')
                numStack.add(numLst[i++]!!)
            }
            if (MyUtils.symLvMap[symStr[j]]!! > MyUtils.symLvMap[symStack.getLast()]!!) { // 比较符号优先级，若当前符号优先级大于前一个则压栈
                if (symStr[j] == '(') {
                    symStack.add(symStr[j++])
                    continue
                }
                numLst[i++]?.let { numStack.add(it) }
                symStack.add(symStr[j++])
            } else { // 当前符号优先级小于等于前一个 符号的优先级
                if (symStr[j] == ')' && symStack.getLast() === '(') { // 若（）之间没有符号，则“（”出栈
                    j++
                    symStack.removeLast()
                    continue
                }
                if (symStack.getLast() === '(') { // “（”直接压栈
                    numLst[i++]?.let { numStack.add(it) }
                    symStack.add(symStr[j++])
                    continue
                }
                num2 = numStack.removeLast()
                num1 = numStack.removeLast()
                sym = symStack.removeLast()
                when (sym) {
                    '+' -> numStack.add(plus(num1, num2))
                    '-' -> numStack.add(reduce(num1, num2))
                    '*' -> numStack.add(multiply(num1, num2))
                    '/' -> {
                        if (num2 == 0.0) { // 除数为0
                            println("存在除数为0")
                            return null
                        }
                        numStack.add(divide(num1, num2))
                    }
                }
            }
        }
        return numStack.removeLast()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val calc = Calculator()
            val s = Scanner(System.`in`)
            println("请输入需要计算式子(输入end退出计算器)：如-1.1+2*3(4*(-6+5))/(6(2+0.2)(7-3))= ")
            var result: Double?
            while (s.hasNextLine()) {
                val str: String = s.nextLine()
                if ("end" == str) {
                    println("谢谢使用!")
                    break
                }
                // 计算处理
                result = calc.prepareParam(str)
                if (result != null) {
                    // 处理结果并打印
                    println(
                        formatResult(String.format("%." + MyUtils.RESULT_DECIMAL_MAX_LENGTH + "f", result))
                    )
                }
            }
        }
    }
}