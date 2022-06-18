package com.mukatalab.jumpy

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith


internal class JumpyPathTest {

    @Test
    fun testGetPath() {
        val subject = JumpyPath(listOf("path", "to", "somewhere", "blue"))
        assertEquals(subject.path, "path/to/somewhere/blue")
    }

    @Test
    fun testMinus() {
        val subject = JumpyPath(listOf("path", "that", "matches", "and", "more")) - JumpyPath(
            listOf(
                "path",
                "that",
                "matches"
            )
        )

        assertEquals(subject.locations, listOf("and", "more"))
    }

    @Test
    fun testMinusRaisesIfPrefixDoesNotMatch() {
        assertFailsWith(Exception::class) {
            JumpyPath(listOf("path", "that", "matches", "and", "more")) - JumpyPath(
                listOf(
                    "path",
                    "that",
                    "does not match"
                )
            )
        }
    }

    @Test
    fun testAdd() {
        val subject = JumpyPath(listOf("a", "b", "c")) + JumpyPath(listOf("d", "e"))

        assertEquals(subject.locations, listOf("a", "b", "c", "d", "e"))
    }


    @Nested
    inner class ConstructFromPathStr {
        @Test
        fun testConstructFromPathStr() {
            assertEquals(JumpyPath("from/a/path/string").locations, listOf("from", "a", "path", "string"))

        }

        @Test
        fun testRemovesEmptyString() {
            assertEquals(JumpyPath("from/a//path/string").locations, listOf("from", "a", "path", "string"))
        }

        @Test
        fun testRemovesIfInitPy() {
            assertEquals(JumpyPath("from/a/path/__init__.py").locations, listOf("from", "a", "path"))
        }

        @Test
        fun testRemovesPyFileExtension() {
            assertEquals(
                JumpyPath("from/a/path/something_here.py").locations,
                listOf("from", "a", "path", "something_here")
            )
        }

        @Test
        fun testRemovesTestFilePrefix() {
            assertEquals(
                JumpyPath("from/a/path/test_something_here.py").locations,
                listOf("from", "a", "path", "something_here")
            )
        }

    }

}