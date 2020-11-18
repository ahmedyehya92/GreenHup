package com.openet.entities

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

data class ResponseRegister(
    val user: User?
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
data class ResponseUserInfo(
    val userInfo: UserInfo
)

data class UserInfo(
    val name: String,
    val email: String,
    val phone: String,
    val type: String,
    val company_name: String,
    val country: String,
    val city: String,
    val address: String?,
    val zip: String
)

data class ResponseUserInfoUpdate(
    val user: UserResponseUserInfoUpdate
)

data class UserResponseUserInfoUpdate(
    val msg: String
)

data class Response<T>(
    val message: String,
    val result: T,
    val status: Boolean,
    val status_code: Int
)


data class ResponseSaveOrder(
    val response: ResponseSaveOrderEntry
)

data class ResponseSaveOrderEntry(
    val msg: String,
    val order_id: Int,
    val order_reference: String,
    val gtotal: Int
): Serializable


data class ResponseAbout(
    val response: ResponseAboutEnter
)

data class ResponseAboutEnter (
    val about: String,
    val terms: String,
    val privacy: String,
    val contact: Contact
)

data class Contact(
    val facebook: String,
    val twitter: String,
    val linkedin: String,
    val phone: String,
    val email: String,
    val address: String="Salmiya, Block No. 4, Street Salem Almubarak, Building 6214",
    val imageStaticMap:String="",
    val latitude: String= "37.7749",
    val longitude: String= "-122.4194"
)

data class MPackage (
    @field:SerializedName("id") val id: String,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("price") val price: String,
    @field:SerializedName("image") val imageUrl: String,
    @field:SerializedName("products") val plantsList: MutableList<Plant>,
    @field:SerializedName("details") val packageDetails: String,
    @field:SerializedName("seller") val sellerName: String,
    @field:SerializedName("addedtocart") val addedToCart: Boolean= false
): Serializable

data class ResponsePackageDetails(
    @field:SerializedName("package_details") val packageDetails: MPackage
)


data class ResponseCountries(
    @field:SerializedName("countries") val countries: MutableList<Country>
)

data class Country(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("name") val name: String
)


data class Plant(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("position") val position: String,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("frontprice") val price: String,
    @field:SerializedName("image") val imageUrl: String,
    @field:SerializedName("isliked") val isliked: Boolean,
    @field:SerializedName("specs") val specifications: MutableList<Specification>?= null,
    @field:SerializedName("gallery") val gallery: MutableList<SliderItem>?= null,
    @field:SerializedName("reviews") val reviews: String?= null,
    @field:SerializedName("intro") val intro: String?= null,
    @field:SerializedName("addedtocart") val addedtocart: Boolean= false,
    @field:SerializedName("category") val categoryName: String?= null,
    @field:SerializedName("offer_start_timestamp") val offerStartAt: String?= null,
    @field:SerializedName("offer_end_timestamp") val offerEndAt: String? = null,
    @field:SerializedName("discount_percentage") val discountPercentage: String? =null,
    @field:SerializedName("details") val details: String?= null,
    @field:SerializedName("sellername") val vendorName: String?= null,
    @field:SerializedName("specs_cats") val specsCats: MutableList<SpecsCatItem>?= null,
    @field:SerializedName("specslist") val specsList: MutableList<Specification>,
    @field:SerializedName("code") val code: String
    ): Serializable

data class SpecsCatItem(
    @field:SerializedName("id")val id: String,
    @field:SerializedName("price") val price: String,
    @field:SerializedName("offer_start_timestamp") val offerStartAt: String?= null,
    @field:SerializedName("offer_end_timestamp") val offerEndAt: String? = null,
    @field:SerializedName("discount_percentage") val discountPercentage: String? =null,
    @field:SerializedName("amountInCart") val amountInCart: Int= 0,
    @field:SerializedName("specifications") val specifications: MutableList<Specification>
    ): Serializable

data class Specification(
    @field:SerializedName("spec_id") val specId: Int,
    @field:SerializedName("spec_name") val specName: String,
    @field:SerializedName("value") val value: String
): Serializable

data class ResponseCategories(
    @field:SerializedName("categories") val categories: MutableList<Category>
)

data class ResponseVendors(
    @field:SerializedName("vendors") val categories: MutableList<Category>
)




data class Category(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("latest_products") var plantsList: MutableList<Plant>
)

data class ResponseSearchProducts(
    @field:SerializedName("products") val products: MutableList<Plant>
)


data class ResponseCategoryProducts(
    @field:SerializedName("categoryproducts") val categoryProducts: MutableList<Plant>
)

data class ResponseFavorites(
    @field:SerializedName("favourites") val favouritesList: MutableList<Plant>
)




data class ResponseCart(
    @field:SerializedName("cartitems") val cartItems: MutableList<CartItem>
)

data class ResponseAddToCart(
    val response: String
)

data class ResponseFavoriteProduct(
    val response: EnterResponseFavoriteProduct
)

data class EnterResponseFavoriteProduct(
    val msg: String
)

data class CartItem(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("quantity") var quantity: Int,
    @field:SerializedName("price") val unitPrice: Float,
    @field:SerializedName("type") val type: String,
    @field:SerializedName("total_price") var totalPrice: Float,
    @field:SerializedName("image") val imageUrl: String,
    @field:SerializedName("max_quantity") var maxQuantity: Int
)

data class ResponseHome(
    val home: Home
)

data class Home(
    val slider: MutableList<Plant>?,
    val featured: MutableList<Plant>?,
    val packages: MutableList<MPackage>?,
    val mainoffer: MutableList<Plant?>?,
    @field:SerializedName("categories") val categories: MutableList<Category>

)



data class SliderItem(
    val id: String,
    @field:SerializedName("image") val imageUrl: String
): Serializable

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
    @field:SerializedName("title") val name: String,
    @field:SerializedName("image") val imageUrl: String,
    @field:SerializedName("intro") val intro: String,
    @field:SerializedName("price") val price: String= "200"
): Serializable


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



data class ResponsePlantDetails(
    @field:SerializedName("details") val details: Plant
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
const val PREF_KEY_FIRST_TIME = "PREF_KEY_FIRST_TIME"

const val PREF_KEY_TOKEN = "PREF_KEY_TOKEN"
const val PREF_KEY_REFRESH_TOKEN = "PREF_KEY_REFRESH_TOKEN"
const val PREF_KEY_LANGUAGE = "PREF_KEY_LANGUAGE"
const val PREF_KEY_USERNAME = "PREF_KEY_USERNAME"
const val PREF_KEY_PASSWORD = "PREF_KEY_PASSWORD"

const val KEY_SEARCH_QUERY= "KEY_SEARCH_QUERY"
const val KEY_CATEGORY_ID= "KEY_CATEGORY_ID"
const val KEY_PLANT= "KEY_PLANT"
const val KEY_PACKAGE= "KEY_PACKAGE"
const val KEY_SERVICE="KEY_SERVICE"

const val KEY_RESPONSE_SAVE_ORDER= "KEY_RESPONSE_SAVE_ORDER"

const val REQUEST_FETCH_EVENTS = 1001
const val REQUEST_FETCH_EVENT_GUESTS = 1002

const val REQUEST_FETCH_SEATS = 1050

const val ACTION_SHOW_SEAT_LOCATION = "ACTION_SHOW_SEAT_LOCATION"

const val EXTRA_AUDIENCE = "EXTRA_AUDIENCE"