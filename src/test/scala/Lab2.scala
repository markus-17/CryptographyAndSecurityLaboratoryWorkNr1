import com.mariuspurici.cryptography_and_security.lab2._

class Lab2 extends munit.FunSuite {
  test("Des Encrypt Block") {
    val des = Des()
    val input = "123456ABCD132536"
    val output = des.encryptBlock(input)
    val expectedOutput = "C0B7A8D05F3A829C"
    assertEquals(output, expectedOutput)
  }

  test("Des Decrypt Block") {
    val des = Des()
    val input = "C0B7A8D05F3A829C"
    val output = des.decryptBlock(input)
    val expectedOutput = "123456ABCD132536"
    assertEquals(output, expectedOutput)
  }

  test("Des Encrypt then Decrypt") {
    val des = Des()
    val input = "Hi there, my name is John. I study Software Engineering. This is a test."
    val listOfEncryptedBlocks: List[String] = des.encryptMessage(input)
    val decryptedMessage = des.decryptMessage(listOfEncryptedBlocks)
    assertEquals(input, decryptedMessage)
  }

  test("Rc4 KeyStream from wikipedia 1") {
    val rc4 = Rc4("Key")
    val expectedKeyStream = "EB9F7781B734CA72A719"
    val keyStream = rc4.keyStream(10).map("%02X" format _).mkString
    assertEquals(expectedKeyStream, keyStream)
  }

  test("Rc4 KeyStream from wikipedia 2") {
    val rc4 = Rc4("Wiki")
    val expectedKeyStream = "6044DB6D41B7"
    val keyStream = rc4.keyStream(6).map("%02X" format _).mkString
    assertEquals(expectedKeyStream, keyStream)
  }

  test("Rc4 KeyStream from wikipedia 3") {
    val rc4 = Rc4("Secret")
    val expectedKeyStream = "04D46B053CA87B59"
    val keyStream = rc4.keyStream(8).map("%02X" format _).mkString
    assertEquals(expectedKeyStream, keyStream)
  }

  test("Rc4 Ciphertext from wikipedia 1") {
    val rc4 = Rc4("Key")
    val plainText = "Plaintext"
    val expectedCipherText = "BBF316E8D940AF0AD3"
    val cipherText = rc4.encryptMessage(plainText)
    assertEquals(cipherText, expectedCipherText)
  }

  test("Rc4 Ciphertext from wikipedia 2") {
    val rc4 = Rc4("Wiki")
    val plainText = "pedia"
    val expectedCipherText = "1021BF0420"
    val cipherText = rc4.encryptMessage(plainText)
    assertEquals(cipherText, expectedCipherText)
  }

  test("Rc4 Ciphertext from wikipedia 3") {
    val rc4 = Rc4("Secret")
    val plainText = "Attack at dawn"
    val expectedCipherText = "45A01F645FC35B383552544B9BF5"
    val cipherText = rc4.encryptMessage(plainText)
    assertEquals(cipherText, expectedCipherText)
  }

  test("Rc4 Decrypt Message from wikipedia 1") {
    val rc4 = Rc4("Key")
    val cipherText = "BBF316E8D940AF0AD3"
    val expectedPlainText = "Plaintext"
    val plainText = rc4.decryptMessage(cipherText)
    assertEquals(plainText, expectedPlainText)
  }

  test("Rc4 Decrypt Message from wikipedia 2") {
    val rc4 = Rc4("Wiki")
    val cipherText = "1021BF0420"
    val expectedPlainText = "pedia"
    val plainText = rc4.decryptMessage(cipherText)
    assertEquals(plainText, expectedPlainText)
  }

  test("Rc4 Decrypt Message from wikipedia 3") {
    val rc4 = Rc4("Secret")
    val cipherText = "45A01F645FC35B383552544B9BF5"
    val expectedPlainText = "Attack at dawn"
    val plainText = rc4.decryptMessage(cipherText)
    assertEquals(plainText, expectedPlainText)
  }

  test("Rc4 Encrypt then Decrypt") {
    val rc4 = Rc4("69_420")
    val input = "Hi there, my name is John. I study Software Engineering. This is a test."
    val cipherText = rc4.encryptMessage(input)
    val decryptedCipherText = rc4.decryptMessage(cipherText)
    assertEquals(input, decryptedCipherText)
  }
}
