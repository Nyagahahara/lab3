

class MainFrame (builder: UserTypeBuilder) extends javax.swing.JFrame  {

  private var tree: Tree = new Tree()

  private val addButton: javax.swing.JButton = new javax.swing.JButton("Add")
  private val removeButton: javax.swing.JButton  = new javax.swing.JButton("Remove")
  private val clearButton: javax.swing.JButton  = new javax.swing.JButton("Clear")
  private val serializeButton: javax.swing.JButton  = new javax.swing.JButton("Serialize")
  private val deserializeButton: javax.swing.JButton  = new javax.swing.JButton("Deserialize")
  private val textArea1: javax.swing.JTextArea = new javax.swing.JTextArea()
  private val rootPanel: javax.swing.JTextArea = new javax.swing.JTextArea()

  addButton.addActionListener(new java.awt.event.ActionListener(){
    override def actionPerformed(e: java.awt.event.ActionEvent): Unit = {
      val result : String = javax.swing.JOptionPane.showInputDialog("Введите значение: ", 0)
      tree.insert(builder.parseValue(result), builder.getTypeComparator)
      refreshTreeView()
    }
  })

  removeButton.addActionListener(new java.awt.event.ActionListener(){
    override def actionPerformed(e: java.awt.event.ActionEvent): Unit = {
      val result : String = javax.swing.JOptionPane.showInputDialog("Введите значение: ", 0)
      tree.shift(builder.parseValue(result), builder.getTypeComparator)
      refreshTreeView()
    }
  })

  clearButton.addActionListener(new java.awt.event.ActionListener(){
    override def actionPerformed(e: java.awt.event.ActionEvent): Unit = {
      tree = new Tree()
      refreshTreeView()
    }
  })

  serializeButton.addActionListener(new java.awt.event.ActionListener(){
    override def actionPerformed(e: java.awt.event.ActionEvent): Unit = {
      try {
        val serFile: java.io.File = new java.io.File("C:\\Users\\panda\\Documents\\file.ser")
        var oos: java.io.ObjectOutputStream = new java.io.ObjectOutputStream(new java.io.FileOutputStream(serFile))
        oos.writeObject(tree)
        oos.flush()
        oos.close()
      } catch {
        case ex: java.io.IOException =>
          ex.printStackTrace()
      }
    }
  })

  deserializeButton.addActionListener(new java.awt.event.ActionListener(){
    override def actionPerformed(e: java.awt.event.ActionEvent): Unit = {
      try {
        val serFile: java.io.File = new java.io.File("C:\\Users\\panda\\Documents\\file.ser")
        var ois: java.io.ObjectInputStream = new java.io.ObjectInputStream(new java.io.FileInputStream(serFile))
        tree = ois.readObject.asInstanceOf[Tree]
        ois.close()
      } catch {
        case ex@(_: java.io.IOException | _: ClassNotFoundException) =>
          ex.printStackTrace()
      }
      refreshTreeView();
    }
  })

  def refreshTreeView() = {
    textArea1.selectAll()
    textArea1.replaceSelection("")
    tree.scan()
  }

  private class CustomOutputStream(var textArea: javax.swing.JTextArea) extends java.io.OutputStream {
    @throws[java.io.IOException]
    override def write(b: Int): Unit = {
      textArea.append(String.valueOf(b.toChar))
      textArea.setCaretPosition(textArea.getDocument.getLength)
      textArea.update(textArea.getGraphics)
    }
  }

  val printStream: java.io.PrintStream = new java.io.PrintStream(new CustomOutputStream(textArea1))
  System.setOut(printStream)
  System.setErr(printStream)

  val panel1: javax.swing.JPanel = new javax.swing.JPanel()
  val panel2: javax.swing.JPanel = new javax.swing.JPanel()

  panel1.setLayout(new java.awt.GridLayout(5, 1, 20, 20))
  panel1.add(addButton)
  panel1.add(removeButton)
  panel1.add(clearButton)
  panel1.add(serializeButton)
  panel1.add(deserializeButton)

  panel2.add(textArea1)
  panel1.setPreferredSize(new java.awt.Dimension(350, 550))
  panel1.setPreferredSize(new java.awt.Dimension(350, 550))

  rootPanel.setLayout(new java.awt.BorderLayout())
  rootPanel.add(panel1, java.awt.BorderLayout.EAST)
  rootPanel.add(panel2, java.awt.BorderLayout.WEST)

  this.setContentPane(rootPanel)
  this.setMinimumSize(new java.awt.Dimension(700, 550))
  this.setSize(new java.awt.Dimension(700, 550))
  this.setResizable(true)
  this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE)
  this.setVisible(true)

}
