package com.strv.movies.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

interface Launcher {
    fun launch()
}

@Composable
fun rememberGalleryLauncher(
    onResult: (String) -> Unit
): Launcher {
    val context = LocalContext.current
    val galleryLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { tempFile ->
            CameraUtils.getPhotoPath(context, tempFile)?.let { onResult(it) }
        }

    }
    return object : Launcher {
        override fun launch() {
            galleryLauncher.launch("image/*")
        }
    }
}

@Composable
fun rememberCameraLauncher(
    onResult: (String) -> Unit
): Launcher {
    var tempFileUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val activityResultLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
            tempFileUri?.let { tempFile ->
                CameraUtils.getPhotoPath(context, tempFile)?.let { onResult(it) }
            }
        }
    return object : Launcher {
        override fun launch() {
            tempFileUri = CameraUtils.getTempFileUri(context)
            activityResultLauncher.launch(tempFileUri)
        }
    }
}

object CameraUtils {
    fun getTempFileUri(context: Context): Uri? {
        val tempFile = File.createTempFile("photo_temp", ".jpg", File(requireNotNull(context.externalCacheDir), "photo").apply {
            mkdirs()
        })
        return tempFile.let {  FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", it)}
    }

    fun getPhotoPath(context: Context, tempFileUri: Uri): String? =
        try {
            val inputStream = context.contentResolver.openInputStream(tempFileUri)
            val exifInputStream = context.contentResolver.openInputStream(tempFileUri)
            requireNotNull(inputStream) { "can't open inputStream for uri $tempFileUri" }
            requireNotNull(exifInputStream) { "can't open inputStream for uri $tempFileUri" }
            val bitmap = performRotation(inputStream, exifInputStream)
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val image = File(context.filesDir, "avatar_$timeStamp.jpg")
            //Finally writing the bitmap to the output stream that we opened
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, FileOutputStream(image))
            bitmap.recycle()
            image.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    private fun performRotation(inputStream: InputStream, exifInputStream: InputStream): Bitmap {
        val originalBitmap = BitmapFactory.decodeStream(inputStream)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) return originalBitmap
        val exif = ExifInterface(exifInputStream)

        val rotation = when (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
            ExifInterface.ORIENTATION_ROTATE_270 -> 270f
            ExifInterface.ORIENTATION_ROTATE_180 -> 180f
            ExifInterface.ORIENTATION_ROTATE_90 -> 90f
            else -> 0f
        }
        return if (rotation != 0f) {
            val matrix = Matrix()
            matrix.postRotate(rotation)
            Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmap.width, originalBitmap.height, matrix, true)
                .also { originalBitmap.recycle() }
        } else {
            originalBitmap
        }
    }
}
