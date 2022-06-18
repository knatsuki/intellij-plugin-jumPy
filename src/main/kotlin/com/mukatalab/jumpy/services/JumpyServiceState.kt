package com.mukatalab.jumpy.services

import com.mukatalab.jumpy.JumpyTestSrcRoot

/**
 * Note to self: Setter must be present for all properties in data class (i.e., no val properties)
 */
data class JumpyServiceState(var testSrcRoots: MutableList<JumpyTestSrcRoot> = mutableListOf())
