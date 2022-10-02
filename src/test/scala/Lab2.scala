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
}
