package com.romanpolach.harrypotter.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for character data from the Harry Potter API.
 */
@Serializable
data class CharacterDto(
    val id: String,
    val name: String,
    @SerialName("alternate_names")
    val alternateNames: List<String> = emptyList(),
    val species: String = "",
    val gender: String = "",
    val house: String = "",
    val dateOfBirth: String? = null,
    val yearOfBirth: Int? = null,
    val wizard: Boolean = false,
    val ancestry: String = "",
    val eyeColour: String = "",
    val hairColour: String = "",
    val wand: WandDto? = null,
    val patronus: String = "",
    val hogwartsStudent: Boolean = false,
    val hogwartsStaff: Boolean = false,
    val actor: String = "",
    @SerialName("alternate_actors")
    val alternateActors: List<String> = emptyList(),
    val alive: Boolean = true,
    val image: String = ""
)

@Serializable
data class WandDto(
    val wood: String = "",
    val core: String = "",
    val length: Double? = null
)
