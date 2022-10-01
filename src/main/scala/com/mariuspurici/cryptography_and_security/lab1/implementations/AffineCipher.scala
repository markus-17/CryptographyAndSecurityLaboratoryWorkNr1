package com.mariuspurici.cryptography_and_security.lab1.implementations

import java.lang.Math.floorMod

class AffineCipher(a: Int, b: Int) extends SubstitutionCipher {
  private val alphabet: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
  private val substitutedAlphabet: String = alphabet.map { char =>
    (floorMod(
      a * (char.toInt - 'A'.toInt) + b,
      alphabetSize
    ) + 'A'.toInt).toChar
  }
  private val alphabetToSubstituted: Map[Char, Char] =
    (alphabet zip substitutedAlphabet).toMap
  private val substitutedToAlphabet: Map[Char, Char] =
    (substitutedAlphabet zip alphabet).toMap

  override protected def encryptLetter(char: Char): Char =
    alphabetToSubstituted(char)
  override protected def decryptLetter(char: Char): Char =
    substitutedToAlphabet(char)
}
