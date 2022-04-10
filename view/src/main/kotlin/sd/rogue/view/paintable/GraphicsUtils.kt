package sd.rogue.view.paintable

import java.awt.Graphics
import java.awt.Image

/**
 * Scale image to full size of graphics bounds
 */
fun Graphics.drawImageScaleFull(image: Image): Unit = with(clipBounds) {
    drawImage(image, x, y, width, height, null)
}

/**
 * Scale image to maximal size without modifying image proportions and locate in the center of bounds.
 */
fun Graphics.drawImageScale(image: Image): Unit = with(clipBounds) {
    val w = image.getWidth(null).toDouble()
    val h = image.getHeight(null).toDouble()
    val scale = minOf(width / w, height / h)
    val paddingW = ((width - w * scale) / 2).toInt()
    val paddingH = ((height - h * scale) / 2).toInt()
    drawImage(image, x + paddingW, y + paddingH, width - paddingW, height - paddingH, null)
}

/**
 * Crop bounds from upper left corner without scaling.
 */
fun Graphics.drawImageCrop(image: Image): Unit = with(clipBounds) {
    drawImage(image, x, y, width, height, 0, 0, width, height, null)
}
