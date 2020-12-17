import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.google.protobuf.gradle.*

plugins {
    application
    id("com.google.protobuf") version "0.8.14"
    kotlin("jvm") version "1.4.10"
}



sourceSets.main {
    withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
        kotlin.srcDirs("build/generated/source/proto/main/grpc","build/generated/source/proto/main/grpckt", "build/generated/source/proto/main/java")
    }
}


protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.10.1"
    }

    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.32.1"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:0.1.5"
        }
    }

    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                // Apply the "grpc" plugin whose spec is defined above, without options.
                id("grpc")
                id("grpckt")
            }
        }
    }
}

group = "com.mutualmobile"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
    google()
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
    implementation("io.grpc:grpc-netty-shaded:1.34.1")
    implementation("io.grpc:grpc-protobuf:1.34.1")
    implementation("io.grpc:grpc-kotlin-stub:1.0.0")

    implementation("com.google.protobuf:protobuf-java:3.8.0")

    implementation("com.twilio.sdk:twilio:7.47.2")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "com.mutualmobile.whatsappclone.WhatsappcloneApplication"
    }

    // To add all of the dependencies
    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}

tasks.withType<Test> {
    useJUnitPlatform()
}