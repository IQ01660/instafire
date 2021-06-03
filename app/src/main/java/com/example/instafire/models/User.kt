package com.example.instafire.models

/**
 * JSON will be serialized onto User objects
 * so there are some requirements for the data class:
 * 1. property names  match the JSON key values (they originate in the FireStore DB)
 * 2. the first field has to be "var" not "val"
 * 3. all properties should get a default value
 */
data class User(
    var username: String = "",
    var age: Int = 0
)