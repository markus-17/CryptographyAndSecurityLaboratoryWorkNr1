import com.mariuspurici.cryptography_and_security.lab4.DigestValidator

class Lab4 extends munit.FunSuite:
  test("Digital Signatures Test 1") {
    val key: String = "test1"
    val message = "This is the same text"
    val sameMessage = "This is the same text"
    DigestValidator.store(key, message)
    val result = DigestValidator.check(key, sameMessage)
    assert(result)
  }

  test("Digital Signatures Test 2") {
    val key: String = "test2"
    val message = "This is the same text"
    val sameMessage = "This is not the same text"
    DigestValidator.store(key, message)
    val result = DigestValidator.check(key, sameMessage)
    assert(!result)
  }
end Lab4
