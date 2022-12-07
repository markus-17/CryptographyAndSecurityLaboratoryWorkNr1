# Cryptography and Security Laboratory Work Nr.5

### Course: Cryptography & Security
### Author: Purici Marius

----

## Objectives:

1. Take what I have at the moment from previous laboratory works and put it in a web service.
2. My services should have implemented basic authentication and MFA.
3. My web app needs to simulate user authorization.
4. As a service my application provides, I should use the classical ciphers. 


## Implementation description

### Secure Web Service

In my web service I implement several endpoints which make use of classical ciphers and return encrypted text. I used a library called **Zio Http**. Because I have almost no experience with this library I took some shortcuts and I used a custom authorization and authentication mechanism. As a second factor of authentication, I used **Google Authenticator**. For the authorization part I used **Attribute Based Access Control (ABAC)**. I used **postman** for testing purposes.

### Code Snippets

#### Make use of Google Authenticator

```java
public static String generateSecretKey() {
    SecureRandom random = new SecureRandom();
    byte[] bytes = new byte[20];
    random.nextBytes(bytes);
    Base32 base32 = new Base32();
    return base32.encodeToString(bytes);
}

public static String getTOTPCode(String secretKey) {
    Base32 base32 = new Base32();
    byte[] bytes = base32.decode(secretKey);
    String hexKey = Hex.encodeHexString(bytes);
    return TOTP.getOTP(hexKey);
}
```

#### Implementing Authentication

```scala
val secureApp = app @@ Middleware.customAuth(
  headers => {
    val userNameTuple = headers.header("X-UserName")
    val passwordTuple = headers.header("X-Password")
    val authCodeTuple = headers.header("X-AuthCode")
    println(s"$userNameTuple, $passwordTuple, $authCodeTuple")
    if(
      userNameTuple.isEmpty ||
        passwordTuple.isEmpty ||
        authCodeTuple.isEmpty
    ) {
      false
    } else {
      val userName = userNameTuple.get._2.toString
      val password = passwordTuple.get._2.toString
      val authCode = authCodeTuple.get._2.toString

      if(
        users.contains(userName) &&
          users(userName).password == password &&
          GoogleAuthenticator.getTOTPCode(users(userName).secretKey) == authCode
      ) {
        true
      } else {
        false
      }
    }
  },
  responseStatus = Status.Unauthorized
)
```

#### Implementing Authorization 

```scala
def checkPermissions(request: Request, algorithm: String): Boolean = {
  val headers = request.headers
  val userName = headers.header("X-UserName").get._2.toString

  if(users(userName).algorithms.contains(algorithm)) {
    true
  } else {
    false
  }
}
```

## Conclusions / Screenshots / Results

1. Attribute-based access control (ABAC) is an authorization model that evaluates attributes (or characteristics), rather than roles, to determine access. The purpose of ABAC is to protect objects such as data, network devices, and IT resources from unauthorized users and actions—those that don’t have “approved” characteristics as defined by an organization’s security policies.

2. My Application might be vulnerable to man in the middle attack if the communication isn't done using Http. 

3. Google Authenticator is a software-based authenticator by Google that implements two-step verification services using the Time-based One-time Password Algorithm and HMAC-based One-time Password algorithm, for authenticating users of software applications.

4. For this laboratory work I simulated the database by loading into memory user data from a resource file.

5. ZIO Http is a scala library for building http apps. It is powered by ZIO and netty and aims at being the defacto solution for writing, highly scalable and performant web applications using idiomatic scala.
