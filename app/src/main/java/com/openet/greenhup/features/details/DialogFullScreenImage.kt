package com.openet.greenhup.features.details

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.openet.greenhup.R
import com.openet.greenhup.features.home.HomeFragment
import kotlinx.android.synthetic.main.dialog_full_screen_image.*


private const val ARG_PARAM1 = "param1"

class DialogFullScreenImage : DialogFragment()
{
    private var imageByteArray: ByteArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageByteArray = it.getByteArray(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_full_screen_image, container, false)
   //     dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    /*    dialog?.window!!.setGravity(Gravity.CENTER)
        //ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog?.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme)*/

        return view
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageByteArray?.let {
            val bmp: Bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
            im_full_screen_image.setImageBitmap(bmp)
        }


        btn_close.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param paramImageByteArray Parameter 1.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(paramImageByteArray: ByteArray) =
            DialogFullScreenImage().apply {
                arguments = Bundle().apply {
                    putByteArray(ARG_PARAM1, paramImageByteArray)

                }
            }
    }
}