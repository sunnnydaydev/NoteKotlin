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

    val list = listOf(1, 3, 5)
    println(
        "result:${
            list.mapNotNull {
                if (it == 3) {
                    "null" //空值会被过滤掉
                } else {
                    it
                }
            }
        }"
    )

    list.map {

    }

    val colors = listOf("name", "age", "sex")
    val animals = listOf("Carry", "20", "boy")
    println(colors.zip(animals)) // colors zip animals

    // unzip:zip的反向操作，返回一个Pair对象pair的key value是两个集合。
    // Pair<List<T>, List<R>>
    val numberPairs = listOf("name" to "Carry", "age" to "20", "sex" to "boy")
    println(numberPairs.unzip())
    // ([name, age, sex], [Carry, 20, boy])

    val numbers = listOf("one", "one", "three", "four")
    println(numbers.associateWith { it.length })

    println(numbers.associateBy {
        it.first().uppercase()//取string的收个字符作为key
    })
    //{O=one, T=three, F=four}

    // 还可以分别变换key value
    println(numbers.associateBy(keySelector = {
        "${it.lowercase()}"
    }, valueTransform = {
        "${it.uppercase()}"
    }))
    //{one=ONE, three=THREE, four=FOUR}

    val names = listOf("Alice Adams", "Brian Brown", "Clara Campbell")
    println(names.associate {
        Pair(it.first(), it.length)
    })

    val numberSets = listOf(setOf(1, 2, 3), setOf(4, 5, 6), setOf(1, 2))
    println(numberSets.flatten())

    val allStudent = arrayListOf(
        Student(arrayListOf("普通班:张三", "普通班:李四")),
        Student(arrayListOf("优秀班:carry", "优秀班:kate"))
    )

    val flatMapResult = allStudent.flatMap {
        it.list
    }
    println(flatMapResult)

    val li = listOf("a", "b", "c", 'd', null, 1)
    println(li.filterIsInstance<String>())
    //[a, b, c]

    val partitionList = arrayListOf(1, 2, 3, 4, 5)
    val (keyList, valueList) = partitionList.partition {
        it % 2 == 0
    }
    println(keyList) // [2, 4]
    println(valueList)//   [1, 3, 5]

    val wordList = arrayListOf("my name is tom")
    println(wordList.any {
        it.contains("m")
    })

}

data class Student(
    val list: List<String> = arrayListOf()
)