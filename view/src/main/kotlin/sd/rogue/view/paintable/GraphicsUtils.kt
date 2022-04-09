package sd.rogue.view.paintable

import java.awt.Graphics
import java.awt.Image

fun Graphics.drawImageScaleFull(image: Image): Unit = with(clipBounds) {
    drawImage(image, x, y, width, height, null)
}

fun Graphics.drawImageScale(image: Image): Unit = with(clipBounds) {
    val w = image.getWidth(null).toDouble()
    val h = image.getHeight(null).toDouble()
    val scale = minOf(width / w, height / h)
    val paddingW = ((width - w * scale) / 2).toInt()
    val paddingH = ((height - h * scale) / 2).toInt()
    drawImage(image, x + paddingW, y + paddingH, width - paddingW, height - paddingH, null)
}

fun Graphics.drawImageCrop(image: Image): Unit = with(clipBounds) {
    drawImage(image, x, y, width, height, 0, 0, width, height, null)
}
