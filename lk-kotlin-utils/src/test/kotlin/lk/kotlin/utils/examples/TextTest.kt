package lk.kotlin.utils.examples

import lk.kotlin.utils.text.isEmail
import org.junit.Test

class TextTest {

    @Test
    fun testEmail() {
        listOf(
                "test@gmail.com",
                "somedude+3@test.nt",
                "troll_-_-_@wat.wat",
                "#!$%&'*+-/=?^_`{}|~@example.org"
        ).forEach {
            assert(it.isEmail(), { "$it should be an email" })
        }

        listOf(
                "just some text",
                "i have spaces @gmail.com",
                "double@@double.com",
                "@noname.com"
        ).forEach {
            assert(!it.isEmail(), { "$it should not be an email" })
        }

        //EDGE CASES!  Technically legal, but no.
        listOf(
                "ipv6@[IPv6:2001:DB8::1]",
                "\" \"@example.org",
                "dude@localdomain"
        ).forEach {
            assert(!it.isEmail(), { "$it is no longer an edge case!" })
        }
    }
}