package com.mariuspurici.cryptography_and_security.lab4

import com.mariuspurici.cryptography_and_security.lab3.Rsa

import java.math.BigInteger
import java.security.MessageDigest
import scala.collection.mutable

object DigestValidator:
  private val inMemoryDatabase: mutable.Map[String, IndexedSeq[BigInt]] =
    mutable.Map[String, IndexedSeq[BigInt]]()
  private val seed: Int = 69_420
  private val asymmetricEncryptionCipher: Rsa = Rsa(seed)

  def textToDigest(text: String): String =
    String.format(
      "%064x",
      new BigInteger(
        1,
        MessageDigest
          .getInstance("SHA-256")
          .digest(text.getBytes("UTF-8"))
      )
    )

  def store(key: String, inputText: String): Unit =
    inMemoryDatabase(key) =
      asymmetricEncryptionCipher.encrypt(textToDigest(inputText))

  def check(key: String, testText: String): Boolean =
    asymmetricEncryptionCipher.decrypt(inMemoryDatabase(key)) == textToDigest(
      testText
    )

end DigestValidator
