package com.gradlelab

import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property

interface CoverageLockInExtension {

    /**
     * File in which coverage gains are saved.
     */
    val coverageFile : RegularFileProperty

    /**
     * Jacoco metric to use. eg. LINE.
     */
    val counter : Property<String>

    /**
     * Final coverage target, after which point gains will not be locked in.
     */
    val goal : Property<Float>

    /**
     * Coverage gains will only be locked in if this is true. Set only on CI machines.
     */
    val onCi : Property<Boolean>

    /**
     * Internally used to track current coverage from the file.
     */
    val internalCurrentCoverage : Property<Float>
}