import java.io.InputStreamReader
class IntegerBuilder extends UserTypeBuilder {
  override def typeName: String = {
    "Integer"
  }

  override def getTypeComparator: Comparator = (x: Any, y: Any) => {
    if (x.asInstanceOf[Int] == y.asInstanceOf[Int]) 0
    else if (x.asInstanceOf[Int] > y.asInstanceOf[Int]) 1
    else -1
  }

  override def create: Any = ???

  override def readValue(in: InputStreamReader): Any = ???

  override def parseValue(ss: String): Int = {
    ss.toInt
  }
}
