plugins {
    id("java")
    id("io.quarkus") version "3.11.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()

}
val quarkusVersion="3.11.1"

dependencies {
    implementation(enforcedPlatform("io.quarkus.platform:quarkus-bom:${quarkusVersion}"))

    implementation("io.quarkus:quarkus-arc") //CDI
    implementation("io.quarkus:quarkus-resteasy-reactive") //REST
    implementation("io.quarkus:quarkus-resteasy-reactive-jackson") //JSON
    implementation("io.quarkus:quarkus-hibernate-orm-panache") //JPA-Hibernate
    //implementation("com.h2database:h2:2.2.224")
    implementation("org.postgresql:postgresql:42.7.3") //BD

}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}


