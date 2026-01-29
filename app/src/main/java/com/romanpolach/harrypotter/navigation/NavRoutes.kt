package com.romanpolach.harrypotter.navigation

import kotlinx.serialization.Serializable

/**
 * Navigation keys for Navigation 3.
 * These are type-safe data classes that represent destinations.
 * Simply add/remove them from the back stack to navigate!
 */

/**
 * Character list screen - the home screen
 */
@Serializable
data object CharacterList

/**
 * Character detail screen with character ID
 */
@Serializable
data class CharacterDetail(val characterId: String)
