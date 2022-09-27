package com.mariuspurici
package cryptography_and_security
package implementations

import Array.ofDim
import java.lang.Math.floorMod

class PlayFairCipher(key: String) extends Cipher {
  val alphabet: String = "ABCDEFGHIKLMNOPQRSTUVWXYZ"

  val transformedKey: String =
    key.toUpperCase
      .filter(char => 'A' <= char && char <= 'Z')
      .replace('J', 'I')
      .distinct

  val transformedAlphabet: String =
    transformedKey + alphabet.filter(
      !transformedKey.contains(_)
    )

  val coordinateToChar: Map[(Int, Int), Char] =
    transformedAlphabet.zipWithIndex
      .map((char, i) => (i / 5, i % 5) -> char)
      .toMap

  val charToCoordinate: Map[Char, (Int, Int)] =
    transformedAlphabet.zipWithIndex
      .map((char, i) => char -> (i / 5, i % 5))
      .toMap

  def getBogusLetter(char: Char): Char = if (char != 'X') 'X' else 'Z'

  def messageToListOfPairs(message: String): List[String] = {
    var result = List[String]()

    message.foreach(char => {
      if (result.isEmpty || result.last.length == 2) {
        result = result.appended(s"$char")
      } else {
        val lastChar: Char = result.last(0)
        result = result.init
        if (lastChar != char) result = result.appended(s"$lastChar$char")
        else
          result = result
            .appended(s"$lastChar${getBogusLetter(lastChar)}")
            .appended(s"$char")
      }
    })

    if (result.nonEmpty && result.last.length == 1) {
      val lastChar: Char = result.last(0)
      result = result.init.appended(s"$lastChar${getBogusLetter(lastChar)}")
    }

    result
  }

  def encodePair(pair: String): String = {
    val a = charToCoordinate(pair(0))
    val b = charToCoordinate(pair(1))
    var aa: (Int, Int) = (-1, -1)
    var bb: (Int, Int) = (-1, -1)

    if (a(0) == b(0)) { // Same Row
      aa = (a(0), (a(1) + 1) % 5)
      bb = (b(0), (b(1) + 1) % 5)
    } else if (a(1) == b(1)) { // Same Column
      bb = ((b(0) + 1) % 5, a(1))
      aa = ((a(0) + 1) % 5, a(1))
    } else {
      aa = (a(0), b(1))
      bb = (b(0), a(1))
    }

    s"${coordinateToChar(aa)}${coordinateToChar(bb)}"
  }

  def decodePair(pair: String): String = {
    val a = charToCoordinate(pair(0))
    val b = charToCoordinate(pair(1))
    var aa: (Int, Int) = (-2, -2)
    var bb: (Int, Int) = (-2, -2)

    if (a(0) == b(0)) { // Same Row
      aa = (a(0), floorMod(a(1) - 1, 5))
      bb = (b(0), floorMod(b(1) - 1, 5))
    } else if (a(1) == b(1)) { // Same Column
      bb = (floorMod(b(0) - 1, 5), a(1))
      aa = (floorMod(a(0) - 1, 5), a(1))
    } else {
      aa = (a(0), b(1))
      bb = (b(0), a(1))
    }

    s"${coordinateToChar(aa)}${coordinateToChar(bb)}"
  }

  override def encrypt(message: String): String =
    messageToListOfPairs {
      message.toUpperCase
        .filter(char => 'A' <= char && char <= 'Z')
        .replace('J', 'I')
    }.map(encodePair).mkString

  override def decrypt(message: String): String =
    messageToListOfPairs(
      message.toUpperCase
        .filter(char => 'A' <= char && char <= 'Z')
        .replace('J', 'I')
    ).map(decodePair).mkString
}
