package com.andriod.lesson1

data class Record(val id: Int = ID++, var value: String) {
    companion object {
        private var ID = 0
    }
}
