package com.mariuspurici
package cryptography_and_security
package lab3

import scala.util.Random
import scala.annotation.tailrec
import scala.io.{BufferedSource, Source}

class Rsa(seed: Int):
  private val random = Random(seed)

  private val i = random.nextInt(Rsa.primeNumbers.length)
  private val j: Int =
    @tailrec
    def chooseDifferentPrimeId(j: Int): Int =
      if j != i then j
      else chooseDifferentPrimeId(random.nextInt(Rsa.primeNumbers.length))

    chooseDifferentPrimeId(i)
  end j

  val p: BigInt = Rsa.primeNumbers(i)
  val q: BigInt = Rsa.primeNumbers(j)
  val n: BigInt = p * q
  val totient: BigInt = Rsa.lcm(p - 1, q - 1)
  val e: BigInt = Rsa.ePrimeNumbers(random.nextInt(Rsa.ePrimeNumbers.length))
  val d: BigInt = e modInverse totient

  val publicKey: (BigInt, BigInt) = (e, n)
  val privateKey: (BigInt, BigInt) = (d, n)

  def encrypt(message: String): IndexedSeq[BigInt] = Rsa.encrypt(message, publicKey)
  def decrypt(message: IndexedSeq[BigInt]): String = Rsa.decrypt(message, privateKey)
end Rsa

object Rsa:
  def lcm(a: BigInt, b: BigInt): BigInt = a * b / a.gcd(b)

  def readPrimeNumbers(path: String): Array[Int] =
    val resource: BufferedSource = Source.fromResource(path)
    val primeNumbers = resource.mkString.split(", ").map(_.toInt)
    resource.close()
    primeNumbers
  end readPrimeNumbers

  lazy val primeNumbers: Array[Int] = readPrimeNumbers("prime_numbers.txt")
  lazy val ePrimeNumbers: Array[Int] = readPrimeNumbers("e.txt")

  def encrypt(message: String, publicKey: (BigInt, BigInt)): IndexedSeq[BigInt] =
    val (e, n) = publicKey
    message.map(BigInt(_) modPow(e, n))

  def decrypt(message: IndexedSeq[BigInt], privateKey: (BigInt, BigInt)): String =
    val (d, n) = privateKey
    message.map(i => (i modPow(d, n)).toChar).mkString
end Rsa
