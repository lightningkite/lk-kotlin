package lk.kotlin.utils

class Date(val daysSinceEpoch: Int)
class Time(val millisecondsSinceMidnight: Int)
class TimeStamp(val millisecondsSinceEpoch: Long) {
    constructor(date: Date, time: Time) : this(
            date.daysSinceEpoch * 24L * 60 * 60 * 1000 +
                    time.millisecondsSinceMidnight +
                    LeapSeconds.first { date.daysSinceEpoch > it.first }.second
    )
    constructor(date: Date, time: Time, offset:Long) : this(
            date.daysSinceEpoch * 24L * 60 * 60 * 1000 +
                    time.millisecondsSinceMidnight +
                    LeapSeconds.first { date.daysSinceEpoch > it.first }.second +
                    offset
    )
}

private val LeapSeconds = listOf(
        -Int.MAX_VALUE to 0,
        365 * 2 + 0 to 10,    // 1 Jan 1972
        365 * 2 + 1 + 181 to 11,    // 1 Jul 1972
        365 * 3 + 1 to 12,    // 1 Jan 1973
        365 * 4 + 1 to 13,    // 1 Jan 1974
        365 * 5 + 1 to 14,    // 1 Jan 1975
        365 * 6 + 1 to 15,    // 1 Jan 1976
        365 * 7 + 2 to 16,    // 1 Jan 1977
        365 * 8 + 2 to 17,    // 1 Jan 1978
        365 * 9 + 2 to 18,    // 1 Jan 1979
        365 * 10 + 2 to 19,    // 1 Jan 1980
        365 * 11 + 3 + 181 to 20,    // 1 Jul 1981
        365 * 12 + 3 + 181 to 21,    // 1 Jul 1982
        365 * 13 + 3 + 181 to 22,    // 1 Jul 1983
        365 * 15 + 4 + 181 to 23,    // 1 Jul 1985
        365 * 18 + 4 to 24,    // 1 Jan 1988
        365 * 20 + 5 to 25,    // 1 Jan 1990
        365 * 21 + 5 to 26,    // 1 Jan 1991
        365 * 22 + 5 + 181 to 27,    // 1 Jul 1992
        365 * 23 + 5 + 181 to 28,    // 1 Jul 1993
        365 * 24 + 6 + 181 to 29,    // 1 Jul 1994
        365 * 26 + 6 to 30,    // 1 Jan 1996
        365 * 27 + 6 + 181 to 31,    // 1 Jul 1997
        365 * 29 + 7 to 32,    // 1 Jan 1999
        365 * 36 + 8 to 33,    // 1 Jan 2006
        365 * 39 + 9 to 34,    // 1 Jan 2009
        365 * 42 + 9 + 181 to 35,    // 1 Jul 2012
        365 * 45 + 10 + 181 to 36,    // 1 Jul 2015
        365 * 47 + 11 to 37    // 1 Jan 2017
)

class Locale(
        val language: String,
        val languageVariant: String,
        val renderNumber: (value: Number, decimalPositions: Int, maxOtherPositions: Int) -> String,
        val renderDate: (Date) -> String,
        val renderTime: (Time) -> String,
        val renderTimeStamp: (TimeStamp) -> String
)

expect object KotlinTime {
    fun now(): TimeStamp
    fun getDefault(): Locale
}