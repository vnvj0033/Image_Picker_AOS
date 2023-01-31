package com.yoosangyeop.imagepicker.feature.search.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.setPadding
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.yoosangyeop.imagepicker.feature.search.R
import com.yoosangyeop.imagepicker.feature.search.ui.view.PinchImageView

internal class PinChImageDialogFragment : DialogFragment {

    constructor() : super()
    constructor(url: String) : super() {
        this.url = url
    }

    private lateinit var pinchImageView: PinchImageView
    private lateinit var closeView: ImageView
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
        pinchImageView = PinchImageView(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        }

        val dp16 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics)
        closeView = ImageView(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                setPadding(dp16.toInt())
            }
            setImageResource(android.R.drawable.ic_menu_close_clear_cancel)
        }

        val layer = FrameLayout(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            addView(pinchImageView)
            addView(closeView)
        }
        return layer
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(pinchImageView)
            .load(url)
            .placeholder(R.drawable.loading_gif)
            .override(SIZE_ORIGINAL)
            .into(pinchImageView)

        closeView.setOnClickListener {
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(MATCH_PARENT, MATCH_PARENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}