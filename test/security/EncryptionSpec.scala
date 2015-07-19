package security

import org.specs2.mutable._

class EncryptionSpec extends Specification {

  "Encryption" should {

    val password = Encryption.encryptPassword("foobar")

    "check a password" in {

      Encryption.checkPassword("foobar", password) mustEqual true
      Encryption.checkPassword("invalid", password) mustEqual false
    }
  }
}
