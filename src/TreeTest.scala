import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class TreeTest {
  def getBuilderByName(name: String): UserTypeBuilder = {
    name match {
      case "Integer" =>
        return new IntegerBuilder
      case "String" =>
        return new StringBuilder
      case _ =>

    }
    null
  }

  def test_rand(n: Int): Unit = {
    val tree = new Tree
    val builder = getBuilderByName("Integer")
    //массив для ключей, которые присутствуют в дереве
    val m: ArrayBuffer[Int] = new ArrayBuffer[Int]()
    //установка первого случайного числа
    val R: Random = new Random
    //заполнение дерева и массива элементами со случайными ключами
    for (i <- 0 until n) {
      val newInt: Int = R.nextInt
      m += newInt
      tree.insert(newInt, builder.getTypeComparator.compare)
    }
    println("Размер дерева до теста:" + tree.size)
    //обнуление счётчиков трудоёмкости вставки, удаления и поиска
    var I: Double = 0
    var D: Double = 0
    var S: Double = 0
    //генерация потока операций, 10% - промахи операций
    for (i <- 0 until n / 2) {
      if (i % 10 == 0) { //10% промахов
        tree.counter = 0
        tree.shift(R.nextInt, builder.getTypeComparator)
        D += tree.counter
        tree.counter = 0
        tree.insert(m((Math.random * n).toInt), builder.getTypeComparator)
        I += tree.counter
        tree.counter = 0
        tree.search(R.nextInt, builder.getTypeComparator)
        S += tree.counter
      }
      else { //90% успешных операций
        val ind = (Math.random * n).toInt
        tree.counter = 0
        tree.shift(m(ind), builder.getTypeComparator)
        D += tree.counter
        val key = R.nextInt
        tree.counter = 0
        tree.insert(R.nextInt, builder.getTypeComparator)
        I += tree.counter
        tree.counter = 0
        m.remove(ind)
        m.insert(ind, key)
        tree.search((Math.random * n).toInt, builder.getTypeComparator)
        S += tree.counter
      } //конец теста
    }
    //вывод результатов:
    //вывод размера дерева после теста
    println("items count: " + tree.size)
    //теоретической оценки трудоёмкости операций BST
    println("2*n*log2(n) = " + 2 * n * (Math.log(n.toDouble) / Math.log(2.0)))
    //экспериментальной оценки трудоёмкости вставки
    println("Count insert: " + I + " " + I / (n / 2))
    //экспериментальной оценки трудоёмкости удаления
    println("Count delete: " + D + " " + D / (n / 2))
    //экспериментальной оценки трудоёмкости поиска
    println("Count search: " + S + " " + S / (n / 2))
  }

  def test_ord(n: Int): Unit = {
    val tree: Tree = new Tree
    val builder: UserTypeBuilder = getBuilderByName("Integer")
    //массив для ключей, которые присутствуют в дереве
    val m: ArrayBuffer[Integer] = new ArrayBuffer[Integer]
    val R: Random = new Random

    //заполнение дерева и массива элементами с возрастающими чётными ключами на интервале [0, 10000, 20000, ... ,10000*n]
    for (i <- 0 until n) {
      m += (i * 1000)
      tree.insert(m(i), builder.getTypeComparator)
    }
    //вывод размера дерева до теста
    println("Размер дерева до теста:" + tree.size)
    //обнуление счётчиков трудоёмкости вставки, удаления и поиска
    var I: Double = 0
    var D: Double = 0
    var S: Double = 0

    //генерация потока операций, 10% - промахи операций
    for (i <- 0 until n / 2) {
      if (i % 10 == 0) //10% промахов
      {
        var k: Int = R.nextInt() % (1000 * n)
        k = k + (k / 2) + 1 //случайный нечётный ключ
        tree.counter = 0
        tree.shift(k, builder.getTypeComparator)
        D += tree.counter
        tree.counter = 0
        tree.insert(m((Math.random() * n).toInt), builder.getTypeComparator)
        I += tree.counter
        tree.counter = 0
        k = R.nextInt() % (1000 * n)
        k = k + (k / 2) + 1 // случайный нечётный ключ
        tree.search(k, builder.getTypeComparator)
        S += tree.counter
      } else //90% успешных операций
      {
        val ind: Int = (Math.random() * n).toInt
        tree.counter = 0
        tree.shift(m(ind), builder.getTypeComparator)
        D += tree.counter
        tree.counter = 0
        var k: Int = R.nextInt() % (1000 * n)
        k = k + k / 2 + 1 // случайный чётный ключ
        tree.insert(k, builder.getTypeComparator)
        I += tree.counter
        tree.counter = 0
        m.remove(ind)
        m.insert(ind, k)
        tree.counter = 0
        tree.search(m((Math.random() % n).toInt), builder.getTypeComparator)
        S += tree.counter
      }
    }
    //вывод результатов:
    //вывод размера дерева после теста
    println("items count: " + tree.size())
    //теоретической оценки трудоёмкости операций BST
    println("2*n*log2(n) = " + 2 * n * (Math.log(n.toDouble) / Math.log(2.0)))
    //экспериментальной оценки трудоёмкости вставки
    println("Count insert: " + I + " " + I / (n / 2))
    //экспериментальной оценки трудоёмкости удаления
    println("Count delete: " + D + " " + D / (n / 2))
    //экспериментальной оценки трудоёмкости поиска
    println("Count search: " + S + " " + S / (n / 2))
  } //конец теста
}
