# Cryptography and Security Laboratory Work Nr.2

### Course: Cryptography & Security
### Author: Purici Marius

----

## Objectives:

1. Get familiar with the symmetric cryptography, stream and block ciphers.
2. Implement an example of a stream cipher.
3. Implement an example of a block cipher.
4. Use Test Classes to showcase the execution of the algorithms.


## Implementation description

### RC4

In cryptography, RC4 (Rivest Cipher 4) is a stream cipher. While it is remarkable for its simplicity and speed in software, multiple vulnerabilities have been discovered in RC4, rendering it insecure. It is especially vulnerable when the beginning of the output `keyStream` is not discarded, or when nonrandom or related keys are used. Particularly problematic uses of RC4 have led to very insecure protocols such as **WEP**.

**RC4** generates a pseudorandom stream of bits. As with any stream cipher, these can be used for encryption by combining it with the plaintext using bitwise exclusive or; decryption is performed the same way (since exclusive or with given data is an involution). This is similar to the one-time pad, except that generated pseudorandom bits, rather than a prepared stream, are used.

To generate the **KeyStream**, the cipher makes use of a secret internal state which consists of two parts:

1. A permutation of all 256 possible bytes
2. Two 8-bit index-pointers (denoted `i` and `j`).

The permutation is initialized with a variable-length key, typically between 40 and 2048 bits, using the key-scheduling algorithm (KSA). Once this has been completed, the stream of bits is generated using the pseudo-random generation algorithm.

#### Implementation of Key-Scheduling algorithm

```scala
var j: Int = 0
(0 until n).foreach((i: Int) => {
    j = (j + s(i) + t(i)) % n
    val temp: Int = s(i)
    s(i) = s(j)
    s(j) = temp
})
```

#### Implementation of Pseudo-Random generation algorithm
```scala
def keyStream(size: Int): IndexedSeq[Int] = {
    var (i, j) = (0, 0)
    val tempS = s.map((i: Int) => i)
    
    (0 until size).map(_ => {
        i = (i + 1) % n
        j = (j + tempS(i)) % n
        val temp: Int = tempS(i)
        tempS(i) = tempS(j)
        tempS(j) = temp
        val t = (tempS(i) + tempS(j)) % n
        tempS(t)
    })
}
```

### DES

**Data encryption standard (DES)** has been found vulnerable to very powerful attacks and therefore, the popularity of DES has been found slightly on the decline. DES is a block cipher and encrypts data in blocks of size of 64 bits each, which means 64 bits of plain text go as the input to DES, which produces 64 bits of ciphertext.

We have mentioned that DES uses a 56-bit key. Actually, the initial key consists of 64 bits. However, before the DES process even starts, every 8th bit of the key is discarded to produce a 56-bit key. That is bit positions 8, 16, 24, 32, 40, 48, 56, and 64 are discarded.

Thus, the discarding of every 8th bit of the key produces a 56-bit key from the original 64-bit key. DES is based on the two fundamental attributes of cryptography: substitution (also called confusion) and transposition (also called diffusion). DES consists of 16 steps, each of which is called a round. Each round performs the steps of substitution and transposition. Let us now discuss the broad-level steps in DES.

* In the first step, the 64-bit plain text block is handed over to an initial Permutation (IP) function.
* The initial permutation is performed on plain text.
* Next, the initial permutation (IP) produces two halves of the permuted block; saying Left Plain Text (LPT) and Right Plain Text (RPT).
* Now each LPT and RPT go through 16 rounds of the encryption process.
* In the end, LPT and RPT are rejoined and a Final Permutation (FP) is performed on the combined block
* The result of this process produces 64-bit ciphertext.

## Conclusions / Screenshots / Results

Symmetric Cryptography deals with the encryption of plain text when having only one encryption key which needs to remain private. Based on the way the plain text is processed / encrypted there are 2 types of ciphers:

* Stream ciphers
  * The encryption is done one byte at a time.
  * Stream ciphers use confusion to hide the plain text.
  * Make use of substitution techniques to modify the plain text.
  * The implementation is fairly complex.
  * The execution is fast.
* Block ciphers
  * The encryption is done one block of plain text at a time.
  * Block ciphers use confusion and diffusion to hide the plain text.
  * Make use of transposition techniques to modify the plain text.
  * The implementation is simpler relative to the stream ciphers.
  * The execution is slow compared to the stream ciphers.

#### Some observations

* Even though a block cipher should be easier to implement than a stream cipher, for this particular laboratory work I found the `Rc4` algorithm to be much easier to implement than the `Des` algorithm.

* The common pieces of implementation that are shared between different symmetric ciphers are usually related to bit level operations and conversions, therefore I believe there is room for improvement in my implementations because it would be possible to make better use of bitwise operators and other out-of-the-box features that most programming languages provide.