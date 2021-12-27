import java.io.InputStreamReader
class StringBuilder extends UserTypeBuilder {

  override def typeName: String = {
    "String"
  }

  override def getTypeComparator: Comparator = (x: Any, y: Any) => {
    if (x.asInstanceOf[String] == y.asInstanceOf[String]) 0
    else if (x.asInstanceOf[String] > y.asInstanceOf[String]) 1
    else -1
  }

  override def create: Any = ???

  override def readValue(in: InputStreamReader): Any = ???

  override def parseValue(ss: String): String = {
    ss
  }
}