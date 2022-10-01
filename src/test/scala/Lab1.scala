// For more information on writing tests, see
// https://scalameta.org/munit/docs/getting-started.html

import com.mariuspurici.cryptography_and_security.lab1.Cipher
import com.mariuspurici.cryptography_and_security.lab1.implementations.{
  PlayFairCipher,
  AffineCipher,
  CaesarCipher,
  CaesarCipherWithPermutation,
  VigenereCipher
}

class Lab1 extends munit.FunSuite {
  test("Play Fair Cipher Encryption Test Succeeded") {
    val cipher: Cipher = PlayFairCipher(key = "monarchy")
    val input = "instruments"
    val output = cipher.encrypt(input)
    val expectedOutput = "GATLMZCLRQXA"
    assertEquals(output, expectedOutput)
  }

  test("Play Fair Cipher Decryption Test Succeeded") {
    val cipher: Cipher = PlayFairCipher(key = "monarchy")
    val input = "gatlmzclrqxa"
    val output = cipher.decrypt(input)
    val expectedOutput = "INSTRUMENTSX"
    assertEquals(output, expectedOutput)
  }

  test("Affine Cipher Test Succeeded") {
    val cipher: Cipher = AffineCipher(5, 8)
    val input = "AFFINECIPHER"
    val output = cipher.encrypt(input)
    val expectedOutput = "IHHWVCSWFRCP"
    assertEquals(output, expectedOutput)
    assertEquals(cipher.decrypt(output), input)
  }

  test("Caesar Cipher Test Succeeded") {
    val cipher: Cipher = CaesarCipher(23)
    val input = "THE_QUICK_BROWN_FOX_JUMPS_OVER_THE_LAZY_DOG"
    val output = cipher.encrypt(input)
    val expectedOutput = "QEB_NRFZH_YOLTK_CLU_GRJMP_LSBO_QEB_IXWV_ALD"
    assertEquals(output, expectedOutput)
    assertEquals(cipher.decrypt(output), input)
  }

  test("Caesar Cipher With Permutation Test Succeeded") {
    val cipher: Cipher = CaesarCipherWithPermutation(23, "monarchy")
    val input = "THE_QUICK_BROWN_FOX_JUMPS_OVER_THE_LAZY_DOG"
    val output = cipher.encrypt(input)
    val expectedOutput = "LRO_IPCZY_XJFSE_NFT_HPDGK_FQOJ_LRO_BWVU_MFA"
    assertEquals(output, expectedOutput)
    assertEquals(cipher.decrypt(output), input)
  }

  test("Vigenere Cipher Test Succeeded") {
    val cipher: Cipher = VigenereCipher("lemon")
    val input = "ATTACKATDAWN"
    val output = cipher.encrypt(input)
    val expectedOutput = "LXFOPVEFRNHR"
    assertEquals(output, expectedOutput)
    assertEquals(cipher.decrypt(output), input)
  }
}
