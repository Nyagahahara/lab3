import java.io.InputStreamReader

trait UserTypeBuilder {
  def typeName: String // Имя типа
  def create: Any // Создает объект
  def readValue(in: InputStreamReader): Any // Создает и читает объект
  def parseValue(ss: String): Any // Создает и парсит одно
  def getTypeComparator: Comparator // Возвращает компаратор для сравнения
}