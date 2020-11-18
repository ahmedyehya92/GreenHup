package com.openet.greenhup.features.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.openet.entities.MPackage

import com.openet.greenhup.R
import com.openet.greenhup.features.package_details.PackageDetailsActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_package_slide.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_IMAGERESID = "param3"

/**
 * A simple [Fragment] subclass.
 * Use the [PackageSlideFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PackageSlideFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var paramImageResId: MPackage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            paramImageResId = it.getSerializable(ARG_IMAGERESID) as MPackage
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_package_slide, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Glide.with(context)
            .load(paramImageResId!!.imageUrl)
            .into(im_slide)

        im_slide.setOnClickListener {
            val packageId= paramImageResId!!.id
            paramImageResId?.let { startActivity(PackageDetailsActivity.instantiateIntent(requireContext(),it)) }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PackageSlideFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(paramImgUrl: MPackage) =
            PackageSlideFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_IMAGERESID, paramImgUrl)
                }
            }
    }
}
