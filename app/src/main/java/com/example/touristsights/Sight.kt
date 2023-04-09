package com.example.touristsights

import android.content.res.Resources
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import java.io.BufferedReader
import java.io.InputStreamReader

@kotlinx.serialization.Serializable
class Sight(
    val name: String,
    val imageName: String,
    val description: String,
    val kind: String
)

fun getSights(resources: Resources): List<Sight> {
    val assetManager = resources.assets
    val inputStream = assetManager.open("sights.json")
    val bufferedReader = BufferedReader(InputStreamReader(inputStream))
    val str: String = bufferedReader.readText()
    val obj = Json(JsonConfiguration.Stable).parse(Sight.serializer().list, str)
    return obj
}