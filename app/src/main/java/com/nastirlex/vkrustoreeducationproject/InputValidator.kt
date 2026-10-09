package com.nastirlex.vkrustoreeducationproject

internal object InputValidator {
    // Support ordinary numbers and formatting, but reject letters and dial commands.
    private val phonePattern = Regex("""\+?[0-9][0-9 ()-]*""")

    fun normalizePhone(input: String): String? {
        val phone = input.trim()
        if (!phonePattern.matches(phone)) return null
        val digits = phone.filter { it in '0'..'9' }
        if (digits.length !in 3..15) return null
        return if (phone.startsWith('+')) "+$digits" else digits
    }
}
