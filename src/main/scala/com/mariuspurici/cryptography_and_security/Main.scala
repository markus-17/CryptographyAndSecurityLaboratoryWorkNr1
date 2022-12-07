package com.mariuspurici.cryptography_and_security

import zhttp.http.*
import zhttp.service.Server
import zio.*

import com.mariuspurici.cryptography_and_security.google_authenticator.GoogleAuthenticator
import com.mariuspurici.cryptography_and_security.lab1.implementations.*

import scala.io.Source

object Main extends ZIOAppDefault {
  case class User(userName: String, password: String, secretKey: String, algorithms: List[String])

  val users = Source.fromResource("users.txt")
      .mkString
      .split("\n")
      .map(row => {
        val values = row.split(",")
        values(0) -> User(values(0), values(1), values(2), values.slice(3, values.length).toList)
      })
      .toMap

  def checkPermissions(request: Request, algorithm: String): Boolean = {
    val headers = request.headers
    val userName = headers.header("X-UserName").get._2.toString

    if(users(userName).algorithms.contains(algorithm)) {
      true
    } else {
      false
    }
  }

  val port: Int = 8080

  val app: Http[Any, Nothing, Request, Response] = Http.collect[Request] {
    case req @ Method.POST -> !! / "AffineCipher" => {
      if(checkPermissions(req, "AffineCipher")) {
        Response.text(AffineCipher(5, 8).encrypt(req.url.queryParams("q").mkString))
      } else {
        Response(status = Status.Forbidden)
      }
    }

    case req @ Method.POST -> !! / "CaesarCipher" => {
      if(checkPermissions(req, "CaesarCipher")) {
        Response.text(CaesarCipher(23).encrypt(req.url.queryParams("q").mkString))
      } else {
        Response(status = Status.Forbidden)
      }
    }

    case req@Method.POST -> !! / "VigenereCipher" => {
      if (checkPermissions(req, "VigenereCipher")) {
        Response.text(VigenereCipher("lemon").encrypt(req.url.queryParams("q").mkString))
      } else {
        Response(status = Status.Forbidden)
      }
    }

    case req@Method.POST -> !! / "PlayFairCipher" => {
      if (checkPermissions(req, "PlayFairCipher")) {
        Response.text(PlayFairCipher(key = "monarchy").encrypt(req.url.queryParams("q").mkString))
      } else {
        Response(status = Status.Forbidden)
      }
    }
  }

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

  val program: ZIO[Any, Throwable, ExitCode] = for {
    _ <- Console.printLine(s"Starting server on http://localhost:$port")
    _ <- Server.start(port, secureApp)
  } yield ExitCode.success

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] =
    program
}