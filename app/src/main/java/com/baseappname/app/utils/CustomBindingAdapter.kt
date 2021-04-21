package com.baseappname.app.utils

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import de.hdodenhof.circleimageview.CircleImageView
import java.util.concurrent.atomic.AtomicBoolean


/**
 * @AUTHOR Amandeep Singh
 * @date 06/04/2021
 * */

/*
*
app:image_path_error="@{@drawable/ic_default_user_placeholder}"
app:image_path_placeholder="@{@drawable/ic_loading}"
*
* */
@BindingAdapter(
    value = ["app:image_path", "app:image_path_error", "app:image_path_placeholder"],
    requireAll = true
)
fun setImage(
    civ: CircleImageView,
    imagePath: String?,
    imagePathError: Drawable,
    imagePathPlaceholder: Drawable
) {
    GlideApp.with(civ.context!!).load(imagePath)
        .placeholder(imagePathPlaceholder).error(imagePathError)
        .into(civ)
}


/*
app:image_url_error="@{@drawable/ic_default_place_holder_alert}"
app:image_url_placeholder="@{@drawable/ic_loading}"
*/
@BindingAdapter(
    value = ["app:image_url", "app:image_url_error", "app:image_url_placeholder"],
    requireAll = false
)
fun setNormalImage(
    iv: ImageView,
    imageUrl: String?,
    imageUrlError: Drawable,
    imageUrlPlaceholder: Drawable
) {
    GlideApp.with(iv.context!!).load(imageUrl)
        .placeholder(imageUrlPlaceholder).error(imageUrlError)
        .into(iv)
}


@BindingAdapter("onSingleClick")
fun View.setOnSingleClickListener(clickListener: View.OnClickListener?) {
    clickListener?.also {
        setOnClickListener(OnSingleClickListener(it))
    } ?: setOnClickListener(null)
}

class OnSingleClickListener(
    private val clickListener: View.OnClickListener,
    private val intervalMs: Long = 1000
) : View.OnClickListener {
    private var canClick = AtomicBoolean(true)

    override fun onClick(v: View?) {
        if (canClick.getAndSet(false)) {
            v?.run {
                postDelayed({
                    canClick.set(true)
                }, intervalMs)
                clickListener.onClick(v)
            }
        }
    }
}






