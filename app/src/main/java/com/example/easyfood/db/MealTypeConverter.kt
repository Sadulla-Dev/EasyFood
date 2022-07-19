package com.example.easyfood.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConverter {
    fun fromAnyString(attributes: Any?): String{

        @TypeConverter
        if (attributes == null){
            return ""
        }
        return attributes as String
    }

    @TypeConverter
    fun fromStringToAny(attributes: String?):Any{

        if (attributes == null){
            return ""
        }
        return attributes
    }
}