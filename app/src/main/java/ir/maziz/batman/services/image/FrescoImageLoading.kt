package ir.maziz.batman.services.image

import com.facebook.drawee.view.SimpleDraweeView
import ir.maziz.batman.view.BatmanImageView

class FrescoImageLoading : ImageLoadingService {
    override fun load(imageView: BatmanImageView, imageUrl: String) {
        if (imageView is SimpleDraweeView) {
            imageView.setImageURI(imageUrl)
        }
    }
}