package ir.maziz.batman.services.image

import ir.maziz.batman.view.BatmanImageView

interface ImageLoadingService {
    fun load(imageView: BatmanImageView, imageUrl: String)
}