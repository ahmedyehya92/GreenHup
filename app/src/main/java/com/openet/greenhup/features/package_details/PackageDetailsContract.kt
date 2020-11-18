package com.openet.greenhup.features.package_details

import com.openet.entities.MPackage
import com.openet.greenhup.core.LoadingHandler

interface PackageDetailsView: LoadingHandler{
    fun addPackageDetails(packageDetails: MPackage)
    fun changeAddToCartState(added: Boolean)
}

interface PackageDetailsPresenter{
    fun getPackageDetails(packageId: String)
    fun changeCartStae(packageId: String, added: Boolean, quantity: String)
}