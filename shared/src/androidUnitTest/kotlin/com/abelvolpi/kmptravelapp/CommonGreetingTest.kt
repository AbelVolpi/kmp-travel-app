package com.abelvolpi.kmptravelapp

import org.junit.Assert.assertTrue
import org.junit.Test

class CommonGreetingTest {
    @Test
    fun testExample() {
        assertTrue("Check Android is mentioned", Greeting().greet().contains("Android"))
    }
}
