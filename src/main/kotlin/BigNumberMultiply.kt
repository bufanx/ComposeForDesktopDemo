class BigNumberMultiply {
    /**
     * 大数阶乘
     */
    var num: String? = null
    fun BigNumberMultiply() {}
    fun BigNumberMultiply(num: String?) {
        this.num = num
    }

    fun multiply(a: List<Int>, b: Int): MutableList<Int> {
        val c: MutableList<Int> = ArrayList()
        var t = 0
        var i = 0
        while (i < a.size || t != 0) {
            if (i < a.size) t += a[i] * b
            c.add(t % 10)
            t /= 10
            i++
        }
        while (c.size > 1 && c[c.size - 1] == 0) c.removeAt(c.size - 1)
        return c
    }

    fun factor(): List<Int> {
        var result: MutableList<Int> = ArrayList()
        val A: MutableList<Int> = ArrayList()
        var temp: MutableList<Int> = ArrayList()
        temp.add(1)
        for (i in num!!.length - 1 downTo 0) {
            A.add(Integer.valueOf(num!![i].toInt()))
        }
        val number = Integer.valueOf(num)
        for (i in 1 until number) {
            result = multiply(temp, i + 1)
            temp = result
        }
        return result
    }

    @JvmName("setNum1")
    fun setNum(num: String?) {
        this.num = num
    }
}