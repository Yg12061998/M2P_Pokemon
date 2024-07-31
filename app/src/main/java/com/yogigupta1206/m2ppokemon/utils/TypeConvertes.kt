package com.yogigupta1206.m2ppokemon.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.yogigupta1206.m2ppokemon.domain.model.Ability
import com.yogigupta1206.m2ppokemon.domain.model.Attack
import com.yogigupta1206.m2ppokemon.domain.model.ImageUrls
import com.yogigupta1206.m2ppokemon.domain.model.Resistance
import com.yogigupta1206.m2ppokemon.domain.model.Weakness

class AttackConverter {
    @TypeConverter
    fun fromAttackList(attacks: List<Attack>): String = Gson().toJson(attacks)

    @TypeConverter
    fun toAttackList(attacksJson: String): List<Attack> = Gson().fromJson(attacksJson, Array<Attack>::class.java).toList()
}

class WeaknessConverter {
    @TypeConverter
    fun fromWeaknessList(weaknesses: List<Weakness>): String = Gson().toJson(weaknesses)

    @TypeConverter
    fun toWeaknessList(weaknesssJson: String): List<Weakness> = Gson().fromJson(weaknesssJson, Array<Weakness>::class.java).toList()
}

class ResistanceConverter {
    @TypeConverter
    fun fromResistanceList(resistances: List<Resistance>): String = Gson().toJson(resistances)

    @TypeConverter
    fun toResistanceList(resistancesJson: String): List<Resistance> = Gson().fromJson(resistancesJson, Array<Resistance>::class.java).toList()
}

class AbilityConverter {
    @TypeConverter
    fun fromAbilityList(abilities: List<Ability>): String = Gson().toJson(abilities)

    @TypeConverter
    fun toAbilityList(abilitiesJson: String): List<Ability> = Gson().fromJson(abilitiesJson, Array<Ability>::class.java).toList()
}

class StringConverter {
    @TypeConverter
    fun fromStringList(strings: List<String>): String = Gson().toJson(strings)

    @TypeConverter
    fun toStringList(stringsJson: String): List<String> = Gson().fromJson(stringsJson, Array<String>::class.java).toList()
}

class ImageUrlsConverter{
    @TypeConverter
    fun fromImageUrls(imageUrls:ImageUrls): String = Gson().toJson(imageUrls)

    @TypeConverter
    fun toImageUrls(imageUrlsJson: String): ImageUrls = Gson().fromJson(imageUrlsJson, ImageUrls::class.java)
}