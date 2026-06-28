package com.example.recipecomposeapp.ui.recipes.model

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.example.recipecomposeapp.data.model.IngredientDto
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class IngredientUiModel(
    val name: String,
    val quantity: String,
    val unitOfMeasure: String
) : Parcelable {

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(quantity)
        dest.writeString(unitOfMeasure)
    }
}

fun IngredientDto.toUiModel(): IngredientUiModel {
    return IngredientUiModel(
        name = description,
        quantity = quantity,
        unitOfMeasure = unitOfMeasure
    )
}