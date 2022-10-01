package com.mariuspurici
package cryptography_and_security
package lab1
package implementations

import java.lang.Math.floorMod

class VigenereCipher(key: String) extends Cipher {
  val transformedKey: String =
    key.toUpperCase
      .filter(char => 'A' <= char && char <= 'Z')
      .distinct

  val transformedKeyLength: Int = transformedKey.length

  override def encrypt(message: String): String =
    message.toUpperCase.zipWithIndex.map {
      case (char, i) if 'A' <= char && char <= 'Z' =>
        (floorMod(
          (char.toInt - 'A'.toInt) + (transformedKey(
            i % transformedKeyLength
          ).toInt - 'A'.toInt),
          alphabetSize
        ) + 'A'.toInt).toChar
      case (char, _) => char
    }.mkString

  override def decrypt(message: String): String =
    message.toUpperCase.zipWithIndex.map {
      case (char, i) if 'A' <= char && char <= 'Z' =>
        (floorMod(
          (char.toInt - 'A'.toInt) - (transformedKey(
            i % transformedKeyLength
          ).toInt - 'A'.toInt),
          alphabetSize
        ) + 'A'.toInt).toChar
      case (char, _) => char
    }.mkString
}
