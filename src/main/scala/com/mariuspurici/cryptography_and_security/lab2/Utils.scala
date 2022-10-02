package com.mariuspurici.cryptography_and_security.lab2

object Utils {
  implicit class StringConversions(s: String) {
    def hexToBin: String =
      s.grouped(2)
        .map(Integer.parseInt(_, 16))
        .map("%8s" format _.toBinaryString)
        .map(_.replace(" ", "0"))
        .mkString

    def binToHex: String =
      s.grouped(8)
        .map(Integer.parseInt(_, 2))
        .map("%02X" format _)
        .mkString

    def binToDec: Int = Integer.parseInt(s, 2)
  }

  implicit class IntegerConversions(i: Int) {
    def decToBin: String = {
      val binaryString = i.toBinaryString
      if binaryString.length % 4 == 0 then binaryString
      else "0" * (4 - binaryString.length % 4) + binaryString
    }
  }
}
