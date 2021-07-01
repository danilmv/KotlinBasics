package com.andriod.lesson1

import android.view.View

class Lambdas() {

    init {
        setListener1(View.OnClickListener { })
        setListener1(object : View.OnClickListener {
            override fun onClick(v: View?) {
            }
        })
        setListener1 { }

        setListener2(object : MyEventListener {
            override fun foo(v: View) {
            }
        })
//        setListener2{ } error

        setListener3(object : MySecondEventListener {
            override fun foo(v: View) {
            }
        })
        setListener3 { }

        setListener4 { }
        setListener4(object : () -> Unit {
            override fun invoke() {
            }
        })

        setListener5(object : (Int) -> Unit {
            override fun invoke(p1: Int) {
            }
        })
        setListener5 {it == 1}
        setListener5 {someNum -> }
    }


    interface MyEventListener {
        fun foo(v: View);
    }

    fun interface MySecondEventListener {
        fun foo(v: View);
    }

    private fun setListener1(l: View.OnClickListener) {}
    private fun setListener2(eventListener: MyEventListener) {}
    private fun setListener3(eventListener: MySecondEventListener) {}
    private fun setListener4(eventListener: () -> Unit) {}
    private fun setListener5(eventListener: (Int) -> Unit) {}
}