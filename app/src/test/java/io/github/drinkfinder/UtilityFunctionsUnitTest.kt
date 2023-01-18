package io.github.drinkfinder
import org.junit.Assert.assertTrue
import org.junit.Test


class UtilityFunctionsUnitTest {
    @Test
    fun returnsCorrectName() {
        assertTrue(UtilityFunctions.getImageName("abilene")=="abilene")
    }
    @Test
    fun returnsCorrectWhenEmpty() {
        assertTrue(UtilityFunctions.getImageName("")=="")
    }
    @Test
    fun returnsCorrectWhenNumbers() {
        assertTrue(UtilityFunctions.getImageName("155 belmont")=="_55_belmont")
    }
    @Test
    fun returnsCorrectWhenSymbols() {
        assertTrue(UtilityFunctions.getImageName("50/50")=="_0_50")
    }
    @Test
    fun returnsCorrectWhenNonExistent() {
        assertTrue(UtilityFunctions.getImageName("smiesznee nazwy")=="smiesznee_nazwy")
    }
    @Test
    fun returnsCorrectConcat() {
        val result =  UtilityFunctions.concatNextItem("test", "Fajna rzec")
        assertTrue(result == "test\n- Fajna rzec")
    }
    @Test
    fun returnsCorrectConcatWhenToContactEmpty() {
        val result =  UtilityFunctions.concatNextItem("", "Fajna rzec")
        assertTrue(result == "- Fajna rzec")
    }
    @Test
    fun returnsCorrectConcatWhenItemEmpty() {
        val result =  UtilityFunctions.concatNextItem("fajnarzecz", "")
        assertTrue(result == "fajnarzecz")
    }
    @Test
    fun returnsCorrectConcatWhenBothEmpty() {
        val result =  UtilityFunctions.concatNextItem("", "")
        assertTrue(result == "")
    }
    @Test
    fun returnsCorrectInstructions() {
        val result =  UtilityFunctions.adjustInstruction("test")
        assertTrue(result == "test")
    }
    @Test
    fun returnsCorrectInstructionsWithDot() {
        val result =  UtilityFunctions.adjustInstruction("test. A tera nowe. ")
        assertTrue(result == "test\nA tera nowe\n")
    }
    @Test
    fun returnsCorrectInstructionsWithSemi() {
        val result =  UtilityFunctions.adjustInstruction("test: ")
        assertTrue(result == "test\n")
    }
    @Test
    fun returnsCorrectInstructionsWhenEmpty() {
        val result =  UtilityFunctions.adjustInstruction("")
        assertTrue(result == "")
    }


}

