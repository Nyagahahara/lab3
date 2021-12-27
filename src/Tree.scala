class Tree () {

  class Node ( var data: Any, var count: Int, var left: Node, var right: Node, var parent: Node)

  var rootNode: Node = null
  var counter: Int = 0  //счетчик пройденных узлов

  def insert(value: Any, comparator: Comparator): Unit = {
    var newNode: Node = new Node(value, 0, null, null, null)
    if (rootNode == null)
      rootNode = newNode
    else {
      if (search(rootNode, value, comparator) == null){ //узла с таким значением не существует
        var currentNode: Node = rootNode
        var parentNode: Node = null
        while (true){
          parentNode = currentNode;
          if (newNode.data == currentNode.data){
            return
          } else {
            if (comparator.compare(newNode.data, currentNode.data) < 0) {
              counter = counter + 1
              val cur: Any = currentNode.data
              currentNode.data = newNode.data
              newNode.data = cur
            }
            currentNode.count = currentNode.count + 1
            if (currentNode.left == null || currentNode.right != null &&
              comparator.compare(currentNode.left.count, currentNode.right.count) < 0){
              currentNode = currentNode.left
              if (currentNode == null){
                parentNode.left = newNode
                newNode.parent = parentNode
                return
              }
            } else {
              currentNode = currentNode.right
              if (currentNode == null){
                parentNode.right = newNode
                newNode.parent = parentNode
                return
              }
            }
          }
        }
      }
    }
  } //end of insert

  def shift(startNode: Node, comparator: Comparator): Unit = {
    var currentNode: Node = startNode
    if (currentNode == null){
      return
    }
    var parentNode: Node = currentNode.parent
    while (true) {
      if (currentNode.left == null && currentNode.right == null) {
        if (parentNode.right == currentNode) {
          parentNode.right = null
          return
        } else {
          parentNode.left = null
          return
        }
      }

      if (currentNode.left == null || currentNode.right != null &&
        comparator.compare(currentNode.right.data, currentNode.left.data) < 0) {
        counter = counter + 1
        currentNode.data = currentNode.right.data
        parentNode = currentNode
        currentNode = currentNode.right
      } else {
        counter = counter + 1
        currentNode.data = currentNode.left.data
        parentNode = currentNode
        currentNode = currentNode.left
      }
    }
  } //end of shift

  def scan(p: Node, level: Int): Unit = {
    if (p == null) {
      return
    }
    scan(p.left, level + 1)
    val numbers = 0 to level - 1
    for (number <- numbers) {
      print("  ")
    }
    println(p.data)
    scan(p.right, level + 1)
  } //end of scan

  def search(p: Node, value: Any, comparator: Comparator): Node = {
    var res: Node = null
    if (null != p){
      counter = counter + 1
      if (p.data == value){
        res = p
      }
      //если в данной вершине значение больше, чем искомое, то среди его потомков меньшего значения уже не будет
      else if (comparator.compare(p.data, value) < 0) {
        res = search(p.right, value, comparator)
        if (null == res){
          res = search(p.left, value, comparator)
        }
      }
    }
    res
  } //end of search

  def search(value: Int, comparator: Comparator): Node = {
    search(rootNode, value, comparator)
  }

  def shift(value: Any, comparator: Comparator): Unit = {
    var p: Node = search(rootNode, value, comparator)
    if (p != null) {
      shift(p, comparator)
    }
  }

  def scan(): Unit = {
    scan(rootNode, 1)
  }

  def size(): Int = {
    size(rootNode)
  }

  def size(p: Node): Int = {
    p match {
      case null => 0
      case _ => 1 + size(p.left) + size(p.right)
    }
  }

}
