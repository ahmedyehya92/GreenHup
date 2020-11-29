package com.openet.usecases

import com.openet.entities.*
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import android.util.Log
import com.openet.usecases.usecases.TokenUseCase
import okhttp3.Interceptor
import retrofit2.Call
import java.io.IOException
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.*


private const val SERVER_BASE_URL = "https://api.greenhub.shop/api/"

private val retrofit: Retrofit by lazy {

    val httpClient = OkHttpClient.Builder()
    httpClient.addInterceptor(AnotherHeadersInterceptor()).
    addInterceptor(AuthorizationInterceptor())
    //httpClient.addInterceptor(LogJsonInterceptor())

    Retrofit.Builder()
        .baseUrl(SERVER_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(httpClient.build())
        .build()
}

val cafApis: CafApis by lazy {
    retrofit.create(CafApis::class.java)
}

interface CafApis {
    @POST("token/refresh")
    fun getTokenInterceptor(@Body refreshToken: RefreshTokenFields
    ): Call<LoginResponse>

    @POST("login")
    fun getTokenInterceptor(@Query("email") email: String,
                            @Query("password") passwowrd: String
    ): Call<ResponseLogin>

    @POST("login")
    fun login(
        @Query("email") email: String,
        @Query("password") passwowrd: String
    ): Single<ResponseLogin>


    @POST("register")
    fun register(@Query("name") name: String,
                 @Query("email") email: String,
                 @Query("password") password: String,
                 @Query("password_confirmation") passwordConfirmation: String,
                 @Query("phone") mobileNumber: String,
                 @Query("buyer") buyer: String="1"

    ): Single<ResponseRegister>

    @GET("home")
    fun home(@Query("lang") lang: String= Locale.getDefault().language): Single<ResponseHome>



    @GET("services")
    fun services(@Query("lang") lang: String= Locale.getDefault().language): Single<ResponseServices>

    @GET("categories")
    fun categories(@Query("lang") lang: String= Locale.getDefault().language): Single<ResponseCategories>

    @GET("vendors")
    fun vendors(@Query("lang") lang: String= Locale.getDefault().language): Single<ResponseVendors>

    @GET("categoryproducts/{categoryId}")
    fun categoryProducts(@Path("categoryId") categoryId: String,
                         @Query("p") page: Int,
                         @Query("lang") lang: String= Locale.getDefault().language
                         ): Single<ResponseCategoryProducts>

    @GET("userfavourites")
    fun userFavorites(
        @Query("p") page: Int,
        @Query("lang") lang: String= Locale.getDefault().language
    ): Single<ResponseFavorites>


    @GET("allproducts/{query}")
    fun searchProducts(@Path("query") query: String,
                         @Query("p") page: Int,
                       @Query("lang") lang: String= Locale.getDefault().language
    ): Single<ResponseSearchProducts>

    @GET("package_details/{packageId}")
    fun packageDetails(@Path("packageId") packageId: String,
                       @Query("lang") lang: String= Locale.getDefault().language
    ): Single<ResponsePackageDetails>

    @GET("product_details/{plant_id}")
    fun plantDetails(@Path("plant_id") plantId: String,
                     @Query("lang") lang: String= Locale.getDefault().language
    ): Single<ResponsePlantDetails>

    @GET("product_details_loggedin/{plant_id}")
    fun plantDetailsAuthorized(@Path("plant_id") plantId: String,
                     @Query("lang") lang: String= Locale.getDefault().language
    ): Single<ResponsePlantDetails>

    @GET("viewcart")
    fun getCartItems(@Query("lang") lang: String= Locale.getDefault().language): Single<ResponseCart>

    @POST("additemtocart")
    fun addItemToCart(
        @Query("product") productId: String,
        @Query("quantity") quantity: String
    ): Single<ResponseAddToCart>

    @POST("addpackagetocart")
    fun addPackageToCart(
        @Query("package") productId: String,
        @Query("quantity") quantity: String
    ): Single<ResponseAddToCart>

    @POST("removepackagefromcart")
    fun removePackageFromCart(
        @Query("package") productId: String
    ): Single<ResponseAddToCart>

    @POST("addtofavourites")
    fun addToFavorites(
        @Query("product") productId: String
    ): Single<ResponseFavoriteProduct>

    @POST("removefromfavourites")
    fun removeFrom0Favorites(
        @Query("product") productId: String
    ): Single<ResponseFavoriteProduct>

    @POST("removeitemfromcart")
    fun removeProductFromCart(
        @Query("product") productId: String
    ): Single<ResponseAddToCart>

    @POST("requestservice")
    fun requestService(
        @Query("service") serviceId: String,
        @Query("notes") note: String
    ): Single<ResponseFavoriteProduct>


    @POST("saveorder")
    fun saveOrder(
        @Query("comments") comments: String,
        @Query("name") name: String,
        @Query("phone") phone: String,
        @Query("email") email: String,
        @Query("address") address: String,
        @Query("paymethod") paymentMethod: String
    ): Single<ResponseSaveOrder>

    @GET("userinfo")
    fun getUserInfo(): Single<ResponseUserInfo>

    @POST("updateuser")
    fun updateUserInfo(
        @Query("name") name: String,
        @Query("phone") phone: String,
        @Query("country") country: String,
        @Query("city") city: String,
        @Query("address") address: String,
        @Query("zip") zip: String
    ): Single<ResponseUserInfoUpdate>

    @GET("greenhub")
    fun getAbout(@Query("lang") lang: String= Locale.getDefault().language): Single<ResponseAbout>

    @GET("countries")
    fun getCountries(): Single<ResponseCountries>



}


class AuthorizationInterceptor(private val tokenUseCase: TokenUseCase = TokenUseCase()) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var mainResponse = chain.proceed(chain.request())
        val mainRequest = chain.request()

        if (tokenUseCase.isLoggedIn) {
            // if response code is 401 or 403, 'mainRequest' has encountered authentication error
            if (mainResponse.code() == 401 || mainResponse.code() == 403) {
                //val authKey = getAuthorizationHeader(session.getEmail(), session.getPassword())
                // request to login API to get fresh token
                // synchronously calling login API




                val tokenResponse: retrofit2.Response<LoginResponse> = tokenUseCase.getTokenInterceptor(tokenUseCase.refreshToken).execute()

                if (tokenResponse.isSuccessful) {
                    // login request succeed, new token generated
                    val authorization = tokenResponse.body()
                    // save the new token
                    tokenUseCase.token = authorization!!.token
                    Log.i("", "interceptor done")
                    // retry the 'mainRequest' which encountered an authentication error
                    // add new token into 'mainRequest' header and request again

                    Log.i("", "token = ${tokenUseCase.token}")

                    val builder = mainRequest.newBuilder().header("Authorization", tokenUseCase.token)
                        .method(mainRequest.method(), mainRequest.body())


                    mainResponse = chain.proceed(builder.build())
                }



            }
        }

        return mainResponse
    }
/*
    companion object {

        /**
         * this method is API implemetation specific
         * might not work with other APIs
         */
        fun getAuthorizationHeader(email: String, password: String): String {
            val credential = "$email:$password"
            return "Basic " + Base64.encodeToString(credential.toByteArray(), Base64.DEFAULT)
        }
    }
    */
}

class AnotherHeadersInterceptor(private val tokenUseCase: TokenUseCase = TokenUseCase()) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        Log.i("Interceptor", "is logged In = ${tokenUseCase.isLoggedIn}")

        val original: Request = chain.request()

        val request: Request = original.newBuilder()
            .header("Accept", "application/json")
            .apply {
                if (tokenUseCase.isLoggedIn)
                    this.header("Authorization", tokenUseCase.token)
            }
            .method(original.method(), original.body())
            .build()

        return chain.proceed(request)

    }
}

