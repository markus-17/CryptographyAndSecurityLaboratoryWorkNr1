package com.mariuspurici
package cryptography_and_security
package implementations

import java.lang.Math.floorMod

class CaesarCipher(key: Int) extends SubstitutionCipher(key: Int) {
  override protected def encryptLetter(char: Char): Char =
    (floorMod(char.toInt - 'A'.toInt + key, alphabetSize) + 'A'.toInt).toChar

  override protected def decryptLetter(char: Char): Char =
    (floorMod(char.toInt - 'A'.toInt - key, alphabetSize) + 'A'.toInt).toChar
}
