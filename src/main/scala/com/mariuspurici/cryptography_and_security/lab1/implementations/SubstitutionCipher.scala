package com.mariuspurici
package cryptography_and_security
package lab1
package implementations

abstract class SubstitutionCipher extends Cipher {
  protected def encryptLetter(char: Char): Char
  protected def decryptLetter(char: Char): Char

  override def encrypt(message: String): String =
    message.toUpperCase
      .map {
        case char: Char if 'A' <= char && char <= 'Z' => encryptLetter(char)
        case char: Char                               => char
      }

  override def decrypt(message: String): String =
    message.toUpperCase
      .map {
        case char: Char if 'A' <= char && char <= 'Z' => decryptLetter(char)
        case char: Char                               => char
      }
}
