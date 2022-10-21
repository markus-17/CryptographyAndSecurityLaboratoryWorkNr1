# Cryptography and Security Laboratory Work Nr.3

### Course: Cryptography & Security
### Author: Purici Marius

----

## Objectives:

1. Get familiar with the asymmetric cryptography algorithms.
2. Implement an asymmetric cipher.
3. Use test classes to showcase the execution of the algorithm.


## Implementation description

### RSA

**RSA (Rivest–Shamir–Adleman)** is a public-key crypto-system that is widely used for secure data transmission. It is also one of the oldest. The acronym **"RSA"** comes from the surnames of Ron Rivest, Adi Shamir and Leonard Adleman, who publicly described the algorithm in 1977. An equivalent system was developed secretly in 1973 at GCHQ (the British signals intelligence agency) by the English mathematician Clifford Cocks. That system was declassified in 1997.

In a public-key crypto-system, the encryption key is public and distinct from the decryption key, which is kept secret (private). An **RSA** user creates and publishes a public key based on two large prime numbers, along with an auxiliary value. The prime numbers are kept secret. Messages can be encrypted by anyone, via the public key, but can only be decoded by someone who knows the prime numbers.

The security of RSA relies on the practical difficulty of factoring the product of two large prime numbers, the **"factoring problem"**. **Breaking RSA encryption** is known as the **RSA problem**. Whether it is as difficult as the factoring problem is an open question. There are no published methods to defeat the system if a large enough key is used.

**RSA** is a relatively slow algorithm. Because of this, it is not commonly used to directly encrypt user data. More often, RSA is used to transmit shared keys for symmetric-key cryptography, which are then used for bulk encryption–decryption.

#### Key Generation

1. Choose two large prime numbers $p$ and $q$.
2. Compute $n = pq$.
3. Compute $\lambda\left(n\right)$, where $\lambda$ is **Carmichael's totient function**.
4. Choose an integer $e$ such that $1 < e < \lambda\left(n\right)$ and $\gcd\left(e, \lambda\left(n\right)\right) = 1$.
5. Determine $d$ as $d \equiv e − 1 \left(mod \lambda\left(n\right)\right)$.

#### Encryption

```scala
def encrypt(message: String, publicKey: (BigInt, BigInt)): IndexedSeq[BigInt] =
  val (e, n) = publicKey
  message.map(BigInt(_) modPow(e, n))
```

#### Decryption
```scala
def decrypt(message: IndexedSeq[BigInt], privateKey: (BigInt, BigInt)): String =
  val (d, n) = privateKey
  message.map(i => (i modPow(d, n)).toChar).mkString
```

## Conclusions / Screenshots / Results

**Public-key cryptography**, or **asymmetric cryptography**, the field of cryptographic systems that use pairs of related keys. Each key pair consists of a public key and a corresponding private key.Key pairs are generated with cryptographic algorithms based on mathematical problems termed one-way functions. Security of public-key cryptography depends on keeping the private key secret; the public key can be openly distributed without compromising security.

The most obvious application of a public key encryption system is for encrypting communication to provide **confidentiality** – a message that a sender encrypts using the recipient's public key which can be decrypted only by the recipient's paired private key.

It is necessary to use both types of encryption (symmetric and asymmetric) because asymmetric ciphers are computationally expensive, so these are usually used for the communication initiation and key exchange, or sometimes called handshake. The messages after that are encrypted with symmetric ciphers.
