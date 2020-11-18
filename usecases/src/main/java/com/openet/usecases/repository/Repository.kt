package com.openet.usecases.repository

import com.openet.entities.*
import com.openet.usecases.CafApis
import com.openet.usecases.PreferencesHelper
import com.openet.usecases.cafApis
import io.reactivex.Single
import retrofit2.Call


val repository: Repository by lazy { RepositoryImplementer() }

interface Repository {
    fun login (email: String, password: String): Single<ResponseLogin>
    fun getTokenHeader(refreshToken: String): Call<LoginResponse>
    fun setLoggedInStatus(status:Boolean)
    fun getLoggedInStatus(): Boolean
    fun setFirstTimeStatus(status:Boolean)
    fun getFirstTimeStatus(): Boolean
    fun setToken(token: String)
    fun getToken():String
    fun setUserName(userName:String)
    fun setPassword(password: String)
    fun getUserName(): String
    fun getPassword(): String
    fun getRefreshToken(): String
    fun setRefreshToken(refreshToken: String)
    fun home():Single<ResponseHome>
    fun services(): Single<ResponseServices>
    fun categories(): Single<ResponseCategories>
    fun vendors(): Single<ResponseVendors>
    fun categoryProducts(categoryId: String, page:Int): Single<ResponseCategoryProducts>
    fun plantDetails(plantId: String): Single<ResponsePlantDetails>
    fun packageDetails(packageId: String): Single<ResponsePackageDetails>
    fun searchProducts(query: String, page: Int): Single<ResponseSearchProducts>
    fun getCartItems(): Single<ResponseCart>
    fun addItemToCart(productId: String, quantity: String): Single<ResponseAddToCart>
    fun addPackageToCart(packageId: String, quantity: String): Single<ResponseAddToCart>
    fun removePackageFromCart(packageId: String): Single<ResponseAddToCart>
    fun getFavoritesList(page: Int): Single<ResponseFavorites>
    fun register(name: String, email: String, password: String, passwordConfirmation: String, mobileNumber: String): Single<ResponseRegister>
    fun addToFavorites(productId: String): Single<ResponseFavoriteProduct>
    fun removeFromFavorites(productId: String): Single<ResponseFavoriteProduct>
    fun requestService(serviceId: String, note: String): Single<ResponseFavoriteProduct>
    fun getLanguage(): String
    fun setLanguage(language: String)
    fun saveOrder(
        comments: String,
        name: String,
        phone: String,
        email: String,
        address: String
    ): Single<ResponseSaveOrder>
    fun getAbout(): Single<ResponseAbout>
    fun getUserInfo(): Single<ResponseUserInfo>
    fun updateUserInfoUseCase(name: String, phone: String, country: String, city: String, address: String, ziip: String): Single<ResponseUserInfoUpdate>
    fun getCountries(): Single<ResponseCountries>
}

class RepositoryImplementer (
    private val server: CafApis = cafApis,
    private val preferencesHelper: PreferencesHelper = PreferencesHelper()

) : Repository
{
    override fun setRefreshToken(refreshToken: String) {
        preferencesHelper.refreshToken = refreshToken
    }

    override fun home(): Single<ResponseHome> = server.home()
    override fun services(): Single<ResponseServices> = server.services()
    override fun categories(): Single<ResponseCategories> = server.categories()
    override fun vendors(): Single<ResponseVendors> = server.vendors()

    override fun categoryProducts(categoryId: String, page: Int): Single<ResponseCategoryProducts> = server.categoryProducts(categoryId, page)
    override fun plantDetails(plantId: String): Single<ResponsePlantDetails> = server.plantDetails(plantId)
    override fun packageDetails(packageId: String): Single<ResponsePackageDetails> = server.packageDetails(packageId)
    override fun searchProducts(query: String, page: Int): Single<ResponseSearchProducts> = server.searchProducts(query, page)
    override fun getCartItems(): Single<ResponseCart> = server.getCartItems()
    override fun addItemToCart(productId: String, quantity: String): Single<ResponseAddToCart> = server.addItemToCart(productId, quantity)
    override fun addPackageToCart(packageId: String, quantity: String): Single<ResponseAddToCart> = server.addPackageToCart(packageId, quantity)
    override fun removePackageFromCart(packageId: String): Single<ResponseAddToCart> = server.removePackageFromCart(packageId)

    override fun getFavoritesList(page: Int): Single<ResponseFavorites> = server.userFavorites(page)
    override fun register(
        name: String,
        email: String,
        password: String,
        passwordConfirmation: String,
        mobileNumber: String
    ): Single<ResponseRegister> = server.register(name, email, password, passwordConfirmation, mobileNumber)

    override fun addToFavorites(productId: String): Single<ResponseFavoriteProduct> = server.addToFavorites(productId)
    override fun removeFromFavorites(productId: String): Single<ResponseFavoriteProduct> = server.removeFrom0Favorites(productId)
    override fun requestService(serviceId: String, note: String): Single<ResponseFavoriteProduct> = server.requestService(serviceId, note)
    override fun getLanguage(): String {
        return preferencesHelper.language
    }

    override fun setLanguage(language: String) {
        preferencesHelper.language= language
    }

    override fun saveOrder(
        comments: String,
        name: String,
        phone: String,
        email: String,
        address: String
    ): Single<ResponseSaveOrder> = server.saveOrder(comments, name, phone, email, address)

    override fun getAbout(): Single<ResponseAbout> = server.getAbout()
    override fun getUserInfo(): Single<ResponseUserInfo> = server.getUserInfo()
    override fun updateUserInfoUseCase(
        name: String,
        phone: String,
        country: String,
        city: String,
        address: String,
        ziip: String
    ) = server.updateUserInfo(name, phone, country, city, address, ziip)

    override fun getCountries(): Single<ResponseCountries> = server.getCountries()


    override fun getRefreshToken(): String {
        return preferencesHelper.refreshToken
    }


    override fun getTokenHeader(refreshToken: String): Call<LoginResponse> = server.getTokenInterceptor(RefreshTokenFields(refreshToken))
    override fun setUserName(userName: String) {
        preferencesHelper.username = userName
    }

    override fun setPassword(password: String) {
        preferencesHelper.password = password
    }

    override fun getUserName(): String {
        return preferencesHelper.username
    }

    override fun getPassword(): String {
        return preferencesHelper.password
    }

    override fun getLoggedInStatus(): Boolean {
        return preferencesHelper.isLoggedIn
    }

    override fun setFirstTimeStatus(status: Boolean) {
        preferencesHelper.isFirstTime= status
    }

    override fun getFirstTimeStatus(): Boolean = preferencesHelper.isFirstTime

    override fun setToken(token: String) {
        preferencesHelper.token = token
    }

    override fun getToken(): String {
        return preferencesHelper.token
    }

    override fun setLoggedInStatus(status: Boolean) {
        preferencesHelper.isLoggedIn = status
    }

    override fun login(email: String, password: String) = server.login(email, password)




}


