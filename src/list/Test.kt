package list

/**
 * Create by SunnyDay 2023/10/21 21:09:22
 **/
fun main() {
    // 1、字符串转集合
    val words = "The quick brown fox jumps over the lazy dog".split(" ")
// 2、集合转换为序列
    val wordsSequence = words.asSequence()

// 3、同样的操作，不过每个元素依次执行下列操作。
    val lengthsSequence = wordsSequence.filter {
        it.length > 3
    }
        .map {
            "单词：$it length:${it.length}"
        }
        .take(4)

    println("Lengths of first 4 words longer than 3 chars")
// 末端操作：获取结果。
    println(lengthsSequence.toList())
}