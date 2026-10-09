package com.nastirlex.vkrustoreeducationproject

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class InputValidatorTest {
    @Test
    fun acceptsAndNormalizesFormattedNumbers() {
        assertEquals("+79991234567", InputValidator.normalizePhone(" +7 (999) 123-45-67 "))
        assertEquals("89991234567", InputValidator.normalizePhone("8 999 123 45 67"))
        assertEquals("112", InputValidator.normalizePhone("112"))
        assertEquals("123456789012345", InputValidator.normalizePhone("123456789012345"))
    }

    @Test
    fun rejectsEmptyAndIncorrectNumbers() {
        listOf("", "   ", "\n", "+", "12", "1234567890123456", "call 112",
            "++79991234567", "7+9991234567", "*100#", "123;456", "123,456",
            "１２３", "123\n456").forEach { phone ->
            assertNull("Should reject: $phone", InputValidator.normalizePhone(phone))
        }
    }
}
