package com.mariuspurici
package cryptography_and_security
package lab2

import scala.collection.mutable.ArrayBuffer

class Rc4(key: String) {
  private val n: Int = 256
  private val s: ArrayBuffer[Int] = ArrayBuffer.range(0, n)
  private val k: Vector[Int] = key.map(_.toInt).toVector
  private val t: Vector[Int] = (0 until n).map((i: Int) => k(i % k.length)).toVector

  {
    var j: Int = 0
    (0 until n).foreach((i: Int) => {
      j = (j + s(i) + t(i)) % n
      val temp: Int = s(i)
      s(i) = s(j)
      s(j) = temp
    })
  }

  def keyStream(size: Int): IndexedSeq[Int] = {
    var (i, j) = (0, 0)
    val tempS = s.map((i: Int) => i)

    (0 until size).map(_ => {
      i = (i + 1) % n
      j = (j + tempS(i)) % n
      val temp: Int = tempS(i)
      tempS(i) = tempS(j)
      tempS(j) = temp
      val t = (tempS(i) + tempS(j)) % n
      tempS(t)
    })
  }

  def encryptMessage(message: String): String =
    val asciiEncodedMessage = message.map(_.toInt)
    val stream = keyStream(asciiEncodedMessage.length)
    (asciiEncodedMessage zip stream).map(_ ^ _).map((i: Int) => "%02X" format i).mkString

  def decryptMessage(message: String): String =
    val decimalMessage = message.grouped(2).map(Integer.parseInt(_, 16)).toVector
    val stream = keyStream(decimalMessage.length)
    (decimalMessage zip stream).map(_ ^ _).map(_.toChar).mkString
}
