package com.am_development.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginFields(
    val username: String,
    val password: String
)

data class LoginResponse(
    @field:SerializedName("token") val token:String
)

data class ResponseLogin(
    val user: User?,
    val access_token: String
)

data class User (
    val code: String?,
    val id: String?,
    val name: String?,
    val email: String?,
    val phone: String?,
    val usertype: String,
    val msg: String?
)


data class Response<T>(
    val message: String,
    val result: T,
    val status: Boolean,
    val status_code: Int
)


data class MPackage (
    @field:SerializedName("id") val id: String,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("price") val price: String,
    @field:SerializedName("image") val imageUrl: String,
    @field:SerializedName("products") val plantsList: MutableList<Plant>
): Serializable

data class Plant(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("position") val position: String,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("price") val price: String,
    @field:SerializedName("image") val imageUrl: String,
    @field:SerializedName("specs") val specifications: MutableList<Specification>?= null,
    @field:SerializedName("gallery") val gallery: MutableList<SliderItem>?= null,
    @field:SerializedName("reviews") val reviews: String?= null,
    @field:SerializedName("intro") val intro: String?= null,
    @field:SerializedName("addedtocart") val addedtocart: Boolean= false,
    @field:SerializedName("category") val categoryName: String?= null,
    @field:SerializedName("offer_start_timestamp") val offerStartAt: String?= null,
    @field:SerializedName("offer_end_timestamp") val offerEndAt: String? = null,
    @field:SerializedName("discount_percentage") val discountPercentage: String? =null,
    @field:SerializedName("details") val details: String?= null
    ): Serializable


data class Specification(
    @field:SerializedName("spec_name") val specName: String,
    @field:SerializedName("value") val value: String
)



data class Category(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("plants") var plantsList: MutableList<Plant>
)

data class CartItem(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("quantity") var quantity: Int,
    @field:SerializedName("price") val unitPrice: Float,
    @field:SerializedName("total_price") var totalPrice: Float,
    @field:SerializedName("image") val imageUrl: String,
    @field:SerializedName("max_quantity") var maxQuantity: Int
)

data class ResponseHome(
    val home: Home
)

data class Home(
    val slider: MutableList<SliderItem>,
    val featured: MutableList<Plant>,
    val packages: MutableList<MPackage>,
    val mainoffer: MutableList<Plant>

)



data class SliderItem(
    val id: String,
    val title: String,
    val imageUrl: String
)

data class ItemNovigtionMenu(
    var isSelected: Boolean,
    val title: String,
    val iconResourceId: Int? = null
)

data class ResponseServices(
    @field:SerializedName("services") val services: MutableList<ServiceItem>
)

data class ServiceItem(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("image") val imageUrl: String,
    @field:SerializedName("intro") val intro: String,
    @field:SerializedName("price") val price: String= "200"
)


data class Pagination(
    val current: Int,
    val currentItemCount: Int,
    val endPage: Int,
    val first: Int,
    val firstItemNumber: Int,
    val firstPageInRange: Int,
    val last: Int,
    val lastItemNumber: Int,
    val lastPageInRange: Int,
    val next: Int,
    val numItemsPerPage: Int,
    val pageCount: Int,
    val pageRange: Int,
    val pagesInRange: List<Int>,
    val startPage: Int,
    val totalCount: Int
)




data class RefreshTokenFields(
    val refresh_token: String
)



@Entity
data class SeatDB(
    @PrimaryKey(autoGenerate = true)
    val roomId: Int,

    @field:SerializedName("color")val color: String,
    @field:SerializedName("confirmed")val confirmed: Boolean,
    @field:SerializedName("reserved")val reserved: Boolean,
    @field:SerializedName("seatcode")val seatcode: String,
    @field:SerializedName("isseated")val seated: Boolean,
    @field:SerializedName("visibility")val visibility: Boolean
)









// loading handler constants
const val LOADING = 0
const val ITEM = 0
const val ERRORCONNECTION = 1
const val ERRORFROMSERVER= 2
const val ERRORUNKNOWN= 3
const val FINISH = 4
const val DONE = 5
const val EMPTY_LIST = 6



// for navigation
const val LOGIN_SCREEN = 100
const val EVENTS_SEARCH_SCREEN = 101
const val SEATS_SCREEN = 102


//for SharedPreference
const val PREF_KEY_LOGGEDIN = "PREF_KEY_LOGGEDIN"
const val PREF_KEY_TOKEN = "PREF_KEY_TOKEN"
const val PREF_KEY_REFRESH_TOKEN = "PREF_KEY_REFRESH_TOKEN"
const val PREF_KEY_USERNAME = "PREF_KEY_USERNAME"
const val PREF_KEY_PASSWORD = "PREF_KEY_PASSWORD"

const val KEY_SEARCH_QUERY= "KEY_SEARCH_QUERY"

const val REQUEST_FETCH_EVENTS = 1001
const val REQUEST_FETCH_EVENT_GUESTS = 1002

const val REQUEST_FETCH_SEATS = 1050

const val ACTION_SHOW_SEAT_LOCATION = "ACTION_SHOW_SEAT_LOCATION"

const val EXTRA_AUDIENCE = "EXTRA_AUDIENCE"