package values

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

object BitmapHelper {
    fun vectorToBitmap(
        context: Context,
        @DrawableRes id: Int,
        @ColorInt color: Int
    ): BitmapDescriptor {

        // se identificar um drawable válido, devolve o vetor se não usa o pin default do google
        val vectorDrawable = ResourcesCompat.getDrawable(context.resources, id, null)
            ?: return BitmapDescriptorFactory.defaultMarker()

        // cria um bitmap
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        // Canvas lê as propriedades do bitmap
        val canvas = Canvas(bitmap)

        // definindo propriedades de posicionamento e tamanho do vetor
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)

        // definindo cor do vetor
        DrawableCompat.setTint(vectorDrawable, color)

        // desenhando o vetor no bitmap
        vectorDrawable.draw(canvas)

        // devolvendo o ícone para colocar no mapa
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}