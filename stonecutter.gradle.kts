plugins {
    id("dev.kikugie.stonecutter")
}

stonecutter active "26.3-fabric"

stonecutter parameters {
    val (version, loader) = current.project.split('-', limit = 2)
    properties { tags(version, loader) }
    constants { match(loader, "fabric", "neoforge") }
}

tasks.register("buildAll") {
    group = "build"
    description = "Build and collect all eight Minecraft/loader variants."
    dependsOn(stonecutter.versions.map { ":${it.project}:buildAndCollect" })
}
