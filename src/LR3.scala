object LR3 extends App {
/*
  val test: TreeTest = new TreeTest
  test.test_ord(1000)
  test.test_rand(1000)
*/

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

  val mainFrame: MainFrame = new MainFrame(getBuilderByName("Integer"))
}
