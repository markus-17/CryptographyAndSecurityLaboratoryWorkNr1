import com.mariuspurici.cryptography_and_security.lab3.Rsa

class Lab3 extends munit.FunSuite:
  test("Rsa Encrypt then Decrypt Message 1") {
    val rsa = Rsa(69_420)
    val input = "Hi there, this is a test!!!"
    val encryptedData = rsa.encrypt(input)
    val decryptedMessage = rsa.decrypt(encryptedData)
    assertEquals(input, decryptedMessage)
  }

  test("Rsa Encrypt then Decrypt Message 2") {
    val rsa = Rsa(17_171)
    val input = "Hello again, this is another test, RSA works!!!"
    val encryptedData = rsa.encrypt(input)
    val decryptedMessage = rsa.decrypt(encryptedData)
    assertEquals(input, decryptedMessage)
  }
end Lab3
