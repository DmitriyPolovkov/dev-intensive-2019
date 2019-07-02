package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String>? = if (fullName.isNullOrBlank()) listOf() else fullName.split(" ")
        return Pair(parts?.getOrNull(0), parts?.getOrNull(1))
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val alphabet = buildString {
            for (letter in 'а'..'я') {
                append(letter)
                append(letter.toUpperCase())
            }
            append(" ")
            append("ё")
            append("ё".toUpperCase())
        }
        return payload.replace(Regex("[$alphabet]")) {
            when (it.value) {
                " " -> divider
                "а" -> "a"
                "б" -> "b"
                "в" -> "v"
                "г" -> "g"
                "д" -> "d"
                "е" -> "e"
                "ё" -> "e"
                "ж" -> "zh"
                "з" -> "z"
                "и" -> "i"
                "й" -> "i"
                "к" -> "k"
                "л" -> "l"
                "м" -> "m"
                "н" -> "n"
                "о" -> "o"
                "п" -> "p"
                "р" -> "r"
                "с" -> "s"
                "т" -> "t"
                "у" -> "u"
                "ф" -> "f"
                "х" -> "h"
                "ц" -> "c"
                "ч" -> "ch"
                "ш" -> "sh"
                "щ" -> "sh'"
                "ъ" -> ""
                "ы" -> "i"
                "ь" -> ""
                "э" -> "e"
                "ю" -> "yu"
                "я" -> "ya"
                "А" -> "A"
                "Б" -> "B"
                "В" -> "V"
                "Г" -> "G"
                "Д" -> "D"
                "Е" -> "E"
                "Ё" -> "E"
                "Ж" -> "Zh"
                "З" -> "Z"
                "И" -> "I"
                "Й" -> "I"
                "К" -> "K"
                "Л" -> "L"
                "М" -> "M"
                "Н" -> "N"
                "О" -> "O"
                "П" -> "P"
                "Р" -> "R"
                "С" -> "S"
                "Т" -> "T"
                "У" -> "U"
                "Ф" -> "F"
                "Х" -> "H"
                "Ц" -> "C"
                "Ч" -> "Ch"
                "Ш" -> "Sh"
                "Щ" -> "Sh'"
                "Ъ" -> ""
                "Ы" -> "I"
                "Ь" -> ""
                "Э" -> "E"
                "Ю" -> "Yu"
                "Я" -> "Ya"
                else -> it.value
            }
        }
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        return when {
            !firstName.isNullOrBlank() && !lastName.isNullOrBlank() -> firstName[0].toString().toUpperCase().plus(
                lastName[0].toString().toUpperCase()
            )
            !firstName.isNullOrBlank() && lastName.isNullOrBlank() -> firstName[0].toString().toUpperCase()
            firstName.isNullOrBlank() && !lastName.isNullOrBlank() -> lastName[0].toString().toUpperCase()
            else -> null
        }
    }
}