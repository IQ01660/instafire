package com.example.instafire.models

/**
 * JSON will be serialized onto Post objects
 * so there are some requirements for the data class:
 * 1. property names  match the JSON key values (they originate in the FireStore DB)
 * 2. the first field has to be "var" not "val"
 * 3. all properties should get a default value
 */
data class Post(
    var description: String = "",
    var image_url: String = "",
    var creation_time_ms: Long = 0,
    var user: User? = null
)