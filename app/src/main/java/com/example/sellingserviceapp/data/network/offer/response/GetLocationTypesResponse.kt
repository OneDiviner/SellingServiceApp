package com.example.sellingserviceapp.data.network.offer.response

import com.example.sellingserviceapp.data.network.authorization.response.Response
import com.google.gson.annotations.SerializedName

data class LocationTypes(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("is_physical") val isPhysical: Boolean
)

data class GetLocationTypesResponse(
    @SerializedName("response") val response: Response,
    @SerializedName("location_types") val locationTypes: List<LocationTypes>
)