package com.yoosangyeop.imagepicker.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.yoosangyeop.imagepicker.ui.view.PinchImageView

class PinChImageDialogFragment : DialogFragment {

    constructor()
    constructor(url: String) {
        this.url = url
    }

    private lateinit var pinchImageView: PinchImageView
    private var url = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        pinchImageView = PinchImageView(requireContext())
        return LinearLayout(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            addView(pinchImageView)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(pinchImageView)
            .load(url)
            .override(SIZE_ORIGINAL)
            .into(pinchImageView)
    }
}