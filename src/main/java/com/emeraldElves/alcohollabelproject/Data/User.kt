package com.emeraldElves.alcohollabelproject.Data

data class User(val name: String, val password: String, val type: UserType){

    var id: Long = -1

    companion object {
        val DB_TABLE = "users"
        val DB_NAME = "name"
        val DB_PASSWORD = "password"
        val DB_USER_TYPE = "user_type"
        val DB_ID = "id"
    }
}