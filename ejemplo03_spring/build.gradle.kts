plugins {
    id("java")
    id("application")
}

group = "com.distribuida"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/org.springframework/spring-core
    implementation("org.springframework:spring-core:6.1.8")

    implementation ("org.springframework:spring-context:6.1.8")

    // https://mvnrepository.com/artifact/org.springframework/spring-orm
    implementation("org.springframework:spring-orm:6.1.8")

    // https://mvnrepository.com/artifact/org.hibernate.orm/hibernate-core
    implementation("org.hibernate.orm:hibernate-core:6.5.2.Final")

// https://mvnrepository.com/artifact/com.h2database/h2
    implementation("com.h2database:h2:2.2.224")
// https://mvnrepository.com/artifact/com.sparkjava/spark-core
    implementation("com.sparkjava:spark-core:2.9.4")
// https://mvnrepository.com/artifact/com.google.code.gson/gson
    implementation("com.google.code.gson:gson:2.11.0")
    // https://mvnrepository.com/artifact/org.springframework.data/spring-data-jpa
    implementation("org.springframework.data:spring-data-jpa:3.3.0")

}

sourceSets {

    main {
        output.setResourcesDir(file("${buildDir}/classes/java/main"))
    }
}
tasks.jar {
    manifest {
        attributes(
                mapOf("Main-Class" to "com.distribuida.Main",
                        "Class-Path" to configurations.runtimeClasspath
                                .get()
                                .joinToString(separator = " ") { file ->
                                    "${file.name}"
                                })
        )
    }
}

tasks.test {
    useJUnitPlatform()
}