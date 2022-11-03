# Cryptography and Security Laboratory Work Nr.4

### Course: Cryptography & Security
### Author: Purici Marius

----

## Objectives:

1. Get familiar with the hashing techniques / algorithms.
2. Use an appropriate hashing algorithms and store the encrypted result in a local data store.
3. Use an asymmetric cipher to implement a digital signature process for a user message.


## Implementation description

### SHA 256

The **SHA (Secure Hash Algorithm)** is one of a number of **cryptographic hash functions**. A cryptographic hash is like a signature for a data set. If you would like to compare two sets of raw data (source of the file, text or similar) it is always better to hash it and compare SHA256 values. It is like the fingerprints of the data. Even if only one symbol is changed the algorithm will produce different hash value. SHA256 algorithm generates an almost-unique, **fixed size** 256-bit (32-byte) hash. Hash is so called a one way function. This makes it suitable for checking integrity of your data, challenge hash authentication, anti-tamper, digital signatures, blockchain.

With the newest hardware (CPU and GPU) improvements it is become possible to **decrypt SHA256** algorithm back. So it is no longer recommended to use it for password protection or other similar use cases. Some years ago you would protect your passwords from hackers by storing SHA256 encrypted password in your database. This is no longer a case.

SHA256 algorithm can be still used for making sure you acquired the same data as the original one. For example if you download something you can easily check if data has not changed due to network errors or malware injection. You can **compare hashes of your file and original one** which is usually provided in the website you are getting data or the file from.

SHA-256 is one of the successor hash functions to SHA-1,and is one of the strongest hash functions available.

### Code Snippets

#### Converting the text to a digest in Scala

```scala
import java.math.BigInteger
import java.security.MessageDigest

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
```

#### Storing the message

```scala
def store(key: String, inputText: String): Unit =
    inMemoryDatabase(key) =
        asymmetricEncryptionCipher.encrypt(textToDigest(inputText))
```

#### Comparing the new hash to the decrypted one 

```scala
def check(key: String, testText: String): Boolean =
    asymmetricEncryptionCipher.decrypt(inMemoryDatabase(key)) == textToDigest(
        testText
    )
```

## Conclusions / Screenshots / Results

1. A digital signature, **a type of electronic signature**, is a mathematical algorithm routinely used to validate the authenticity and integrity of a message. Digital signatures create a virtual fingerprint that is unique to a person or entity and are used to identify users and protect information in digital messages or documents. In emails, the email content itself becomes part of the digital signature. Digital signatures are significantly more secure than other forms of electronic signatures.

2. Digital signatures increase the transparency of online interactions and develop trust between customers, business partners, and vendors.

3. Digital signatures work by proving that a digital message or document was not modified—intentionally or unintentionally—from the time it was signed. Digital signatures do this by generating a unique hash of the message or document and encrypting it using the sender’s private key. The hash generated is unique to the message or document, and changing any part of it will completely change the hash.

4. Once completed, the message or digital document is digitally signed and sent to the recipient. The recipient then generates their own hash of the message or digital document and decrypts the sender’s hash (included in the original message) using the sender’s public key. The recipient compares the hash they generate against the sender’s decrypted hash; if they match, the message or digital document has not been modified and the sender is authenticated.

5. As paperless, online interactions are used more widely, digital signatures can help you secure and safeguard the integrity of your data. By understanding and using digital signatures, you can better protect your information, documents, and transactions.
