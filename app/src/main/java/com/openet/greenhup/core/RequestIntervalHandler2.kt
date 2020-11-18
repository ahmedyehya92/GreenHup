package com.openet.greenhup.core

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.openet.greenhup.R
import com.openet.greenhup.customviews.CustomeFontTextView
import com.openet.usecases.engine.toMutableLiveData

class RequestIntervalHandler2(
    val viewGroup: ViewGroup,
    val context: Context,
    val isLoutColored: Boolean = false

) {
    private var viewHolder: ViewHolder? = null

    val tryAgainTrigger: MutableLiveData<Int> = 0.toMutableLiveData()

    //abstract fun onTryAgainClickListener()


    init {
        /* tryAgainTrigger!!.value = 0
         val inflater = LayoutInflater.from(context)
         val relativeLayout =
             inflater.inflate(R.layout.view_request_interval_handler, viewGroup, false) as RelativeLayout

         viewGroup.addView(relativeLayout)

         viewHolder = ViewHolder(viewGroup)

         viewHolder!!.btnTryAgain!!.setOnClickListener { tryAgainTrigger.postValue(1) }

         viewHolder!!.louColored!!.isSoundEffectsEnabled = false

         viewHolder!!.louColored!!.setOnClickListener { } */
        setupView()
    }

    private fun setupView() {
        tryAgainTrigger.value = 0
        val inflater = LayoutInflater.from(context)
        val relativeLayout =
            inflater.inflate(
                R.layout.view_request_interval_handler2,
                viewGroup,
                false
            ) as RelativeLayout

        viewGroup.addView(relativeLayout)

        viewHolder = ViewHolder(viewGroup)

        /* viewHolder!!.loutMain.setOnClickListener {

         }*/

        viewHolder!!.btnTryAgain!!.setOnClickListener {
            tryAgainTrigger.postValue(1)
        }

        viewHolder?.btn_login?.setOnClickListener {
            tryAgainTrigger.postValue(2)
        }

        viewHolder!!.louColored!!.isSoundEffectsEnabled = false

        viewHolder!!.louColored!!.setOnClickListener { }
    }


    fun showLoadingView(loadingTitle: String? = null) {
        if (loadingTitle != null) {
            viewHolder!!.tvLoadingTitle!!.visibility = View.VISIBLE
            viewHolder!!.tvLoadingTitle!!.text = "Loading..."
        } else
            viewHolder!!.tvLoadingTitle!!.visibility = View.GONE

        if (viewHolder!!.loutMain!!.visibility == View.GONE)
            viewHolder!!.loutMain!!.visibility = View.VISIBLE
        viewHolder!!.loutErrorView!!.visibility = View.GONE
        viewHolder!!.loutEmptyView!!.visibility = View.GONE
        viewHolder!!.loutLoading!!.visibility = View.VISIBLE

        if (isLoutColored)
            viewHolder!!.louColored!!.visibility = View.VISIBLE
        else
            viewHolder!!.louColored!!.visibility = View.GONE

    }


    fun showDone(doneMessage: String) {

        /*    try {
                val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val r = RingtoneManager.getRingtone(CashSwapApp().applicationContext, notification)
                r.play()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            */

        val mediaPlayer: MediaPlayer = MediaPlayer.create(context, R.raw.notification_done)
        mediaPlayer.setVolume(50f, 50f)
        mediaPlayer.start()

        if (viewHolder!!.loutMain!!.visibility == View.GONE)
            viewHolder!!.loutMain!!.visibility = View.VISIBLE

        viewHolder!!.loutLoading!!.visibility = View.GONE


        if (doneMessage != null)
            viewHolder!!.tvDoneMessage!!.text = doneMessage



        viewHolder!!.loutDoneView!!.visibility = View.VISIBLE

        if (isLoutColored)
            viewHolder!!.louColored!!.visibility = View.VISIBLE
        else
            viewHolder!!.louColored!!.visibility = View.GONE

    }


    fun showErrorView(errorMessage: String?) {

        if (viewHolder!!.loutMain!!.visibility == View.GONE)
            viewHolder!!.loutMain!!.visibility = View.VISIBLE

        viewHolder!!.loutLoading!!.visibility = View.GONE

        if (errorMessage == null)
            viewHolder!!.tvErrorMessage!!.text = context.getString(R.string.unknown_error)
        else
            viewHolder!!.tvErrorMessage!!.text = errorMessage

        viewHolder!!.loutErrorView!!.visibility = View.VISIBLE

        if (isLoutColored)
            viewHolder!!.louColored!!.visibility = View.VISIBLE
        else
            viewHolder!!.louColored!!.visibility = View.GONE
    }

    fun showNotLoggedInView() {

        if (viewHolder!!.loutMain!!.visibility == View.GONE)
            viewHolder!!.loutMain!!.visibility = View.VISIBLE

        viewHolder!!.loutLoading!!.visibility = View.GONE


        viewHolder!!.lout_not_logged_in!!.visibility = View.VISIBLE

        if (isLoutColored)
            viewHolder!!.louColored!!.visibility = View.VISIBLE
        else
            viewHolder!!.louColored!!.visibility = View.GONE
    }

    fun hideNotLoggedInView()
    {
        viewHolder!!.lout_not_logged_in!!.visibility = View.GONE
    }


    fun setMessageErrorTextColor(@ColorRes colorResourseId: Int) {
        viewHolder!!.tvErrorMessage.setTextColor(ContextCompat.getColor(context, colorResourseId))
    }


    fun showEmptyView(emptyMessage: String?) {
        if (viewHolder!!.loutMain!!.visibility == View.GONE)
            viewHolder!!.loutMain!!.visibility = View.VISIBLE

        viewHolder!!.loutLoading!!.visibility = View.GONE

        if (emptyMessage == null)
            viewHolder!!.tvEmptyViewMessage!!.text = context.getString(R.string.empty_list)
        else
            viewHolder!!.tvEmptyViewMessage!!.text = emptyMessage

        viewHolder!!.loutEmptyView!!.visibility = View.VISIBLE

        /*   if (isLoutColored)
               viewHolder!!.louColored!!.visibility = View.VISIBLE */
        //   else
        viewHolder!!.louColored!!.visibility = View.GONE

        viewHolder!!.tvEmptyViewMessage.setTextColor(context.resources.getColor(R.color.gray))
        viewHolder!!.tvEmptyViewMessage.isAllCaps = true
    }


    fun showCustomStateView(message: String?, imgResourceId: Int?) {
        if (message != null)
            viewHolder!!.tvCustomStateMessage!!.text = message

        if (imgResourceId != null)
            viewHolder!!.imCustomState!!.setImageResource(imgResourceId)

        if (viewHolder!!.loutMain!!.visibility == View.GONE)
            viewHolder!!.loutMain!!.visibility = View.VISIBLE

        viewHolder!!.loutLoading!!.visibility = View.GONE
        viewHolder!!.loutCusomState!!.visibility = View.VISIBLE

    }


    fun finishLoading() {
        viewHolder!!.loutMain!!.visibility = View.GONE
    }


    inner class ViewHolder(view: View) {

        val loutMain by lazy { view.findViewById<RelativeLayout>(R.id.lout_main) }

        val lout_not_logged_in by lazy { view.findViewById<LinearLayout>(R.id.lout_not_logged_in) }

        val btn_login by lazy { view.findViewById<CustomeFontTextView>(R.id.btn_login) }

        val loutLoading by lazy { view.findViewById<LinearLayout>(R.id.lout_loading) }


        val tvLoadingTitle by lazy { view.findViewById<CustomeFontTextView>(R.id.tv_loading_title) }


        val loutErrorView by lazy { view.findViewById<LinearLayout>(R.id.lout_error_view) }


        val btnTryAgain by lazy { view.findViewById<LinearLayout>(R.id.btn_try_again) }


        val tvErrorMessage by lazy { view.findViewById<CustomeFontTextView>(R.id.tv_error_message) }


        val louColored by lazy { view.findViewById<RelativeLayout>(R.id.lout_colored) }


        val loutCusomState by lazy { view.findViewById<LinearLayout>(R.id.lout_custom_state_view) }


        val imCustomState by lazy { view.findViewById<ImageView>(R.id.im_custom_state_view) }


        val tvCustomStateMessage by lazy { view.findViewById<CustomeFontTextView>(R.id.tv_custom_state_message) }

        val loutDoneView by lazy { view.findViewById<LinearLayout>(R.id.lout_done_view) }

        val tvDoneMessage by lazy { view.findViewById<CustomeFontTextView>(R.id.tv_done_message) }


        val tvEmptyViewMessage by lazy { view.findViewById<CustomeFontTextView>(R.id.tv_empty_message) }

        val loutEmptyView by lazy { view.findViewById<LinearLayout>(R.id.lout_empty_view) }

    }


}
