// See LICENSE for license details.

package vizualizer

import java.io.{File, PrintWriter}

case class MuxNode(name: String) {
  def select: String = s"$name:select"
  def in1: String = s"$name:in1"
  def in2: String = s"$name:in2"
  def out: String = s"$name:out"
  def render: String ={
    s"""
       |$name [label=<
       |<TABLE BORDER="0" CELLBORDER="1" CELLSPACING="0" CELLPADDING="4">
       |  <TR>
       |    <TD PORT="in1">a</TD>
       |    <TD ROWSPAN="2" PORT="select">a?</TD>
       |    <TD ROWSPAN="2" PORT="out">out</TD>
       |  </TR>
       |  <TR>
       |    <TD PORT="in2">b</TD>
       |  </TR>
       |</TABLE>>];
       """.stripMargin
  }

}

import sys.process._

object MuxNode {
  def main(args: Array[String]): Unit = {
    val mux1 = MuxNode("struct1")
    val mux2 = MuxNode("struct2")
    val writer = new PrintWriter(new File("mux1.dot"))
    writer.println(s"digraph structs {\nnode [shape=plaintext]")
    writer.write(mux1.render)
    writer.write(mux2.render)

    writer.println(s"FOX -> ${mux1.select};")
    writer.println(s"DOG -> ${mux1.in1};")
    writer.println(s"CAT -> ${mux1.in2};")
    writer.println(s"\n${mux1.out} -> ${mux2.in1}")
    writer.println(s"}")

    writer.close()

    "dot -Tpng -O mux1.dot".!!
    "open mux1.dot.png".!!
  }
}