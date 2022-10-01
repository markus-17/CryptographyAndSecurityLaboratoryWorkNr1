package com.mariuspurici
package cryptography_and_security
package lab1
package implementations

class CaesarCipherWithPermutation(
    key: Int,
    permutationKey: String
) extends CaesarCipher(key) {
  private val alphabet: String = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

  private val permutationKeyTransformed: String =
    permutationKey.toUpperCase.toSeq
      .filter(char => 'A' <= char && char <= 'Z')
      .distinct
      .toString

  private val permutedAlphabet: String =
    permutationKeyTransformed + alphabet.filter(
      !permutationKeyTransformed.contains(_)
    )

  private val alphabetToPermutation: Map[Char, Char] =
    (alphabet zip permutedAlphabet).toMap

  private val permutationToAlphabet: Map[Char, Char] =
    (permutedAlphabet zip alphabet).toMap

  override protected def encryptLetter(char: Char): Char =
    alphabetToPermutation(super.encryptLetter(char))

  override protected def decryptLetter(char: Char): Char =
    super.decryptLetter(permutationToAlphabet(char))
}
