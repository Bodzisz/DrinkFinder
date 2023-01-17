package io.github.drinkfinder
import org.junit.Test


class ExampleUnitTest {
    @Test
    fun returnsCorrectName() {
        assert(UtilityFunctions.getImageName("abilene")=="abilene")
    }
    @Test
    fun returnsCorrectWhenEmpty() {
        assert(UtilityFunctions.getImageName("")=="")
    }
    @Test
    fun returnsCorrectWhenNumbers() {
        assert(UtilityFunctions.getImageName("155 belmont")=="_55_belmont")
    }
    @Test
    fun returnsCorrectWhenSymbols() {
        assert(UtilityFunctions.getImageName("50/50")=="_0_50")
    }
    @Test
    fun returnsCorrectWhenNonExistent() {
        assert(UtilityFunctions.getImageName("smiesznee nazwy")=="smiesznee_nazwy")
    }
    @Test
    fun returnsCorrectConcat() {
        val result =  UtilityFunctions.concatNextItem("test", "Fajna rzec")
        assert(result == "test\n- Fajna rzec")
    }
    @Test
    fun returnsCorrectConcatWhenToContactEmpty() {
        val result =  UtilityFunctions.concatNextItem("", "Fajna rzec")
        assert(result == "- Fajna rzec")
    }
    @Test
    fun returnsCorrectConcatWhenItemEmpty() {
        val result =  UtilityFunctions.concatNextItem("fajnarzecz", "")
        assert(result == "fajnarzecz")
    }
    @Test
    fun returnsCorrectConcatWhenBothEmpty() {
        val result =  UtilityFunctions.concatNextItem("", "")
        assert(result == "")
    }
    @Test
    fun returnsCorrectInstructions() {
        val result =  UtilityFunctions.adjustInstruction("test")
        assert(result == "test")
    }
    @Test
    fun returnsCorrectInstructionsWithDot() {
        val result =  UtilityFunctions.adjustInstruction("test. A tera nowe. ")
        assert(result == "test\nA tera nowe\n")
    }
    @Test
    fun returnsCorrectInstructionsWithSemi() {
        val result =  UtilityFunctions.adjustInstruction("test: ")
        assert(result == "test\n")
    }
    @Test
    fun returnsCorrectInstructionsWhenEmpty() {
        val result =  UtilityFunctions.adjustInstruction("")
        assert(result == "")
    }


}

