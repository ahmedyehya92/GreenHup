package com.am_development.greenhup.features.on_boarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.am_development.greenhup.R
import kotlinx.android.synthetic.main.fragment_on_boarding_slide_screen.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_TITLE = "param1"
private const val ARG_TEXT = "param2"
private const val ARG_IMAGERESID = "param3"

/**
 * A simple [Fragment] subclass.
 * Use the [OnBoardingSlideScreenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OnBoardingSlideScreenFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var paramTitle: String? = null
    private var paramText: String? = null
    private var paramImageResId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            paramTitle = it.getString(ARG_TITLE)
            paramText = it.getString(ARG_TEXT)
            paramImageResId = it.getInt(ARG_IMAGERESID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding_slide_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_title.text = paramTitle
        tv_text.text = paramText
        im_board.setImageResource(paramImageResId!!)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OnBoardingSlideScreenFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(paramtTitle: String, paramText: String, paramImgResId: Int) =
            OnBoardingSlideScreenFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, paramtTitle)
                    putString(ARG_TEXT, paramText)
                    putInt(ARG_IMAGERESID, paramImgResId)
                }
            }
    }
}
