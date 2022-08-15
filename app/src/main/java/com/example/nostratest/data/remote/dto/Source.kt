package com.example.nostratest.data.remote.dto

import com.example.nostratest.domain.model.Source

data class Source(
    val id: String,
    val name: String
){
    fun toSource(): Source {
        return Source(
            id = id,
            name = name
        )
    }
}