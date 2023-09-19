tasks.named("compileJava") {
    doLast {
        // Purposely add slowdown to simulate compilation of large projects.
        Thread.sleep(2000)
    }
}

/*tasks.withType<Test>().configureEach {
    maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).coerceAtLeast(1)
}*/