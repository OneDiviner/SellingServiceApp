package com.example.sellingserviceapp.util.extension.imagePicker

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.activity.compose.ManagedActivityResultLauncher
import java.io.InputStream

class ImagePicker(
    private val context: Context,
    private val onImageSelected: (base64: String) -> Unit
) {
    fun pickImage(launcher: ManagedActivityResultLauncher<String, Uri?>) {
        launcher.launch("image/*")
    }

    private fun uriToBase64(context: Context, uri: Uri?): String? {
        return try {
            val inputStream: InputStream? = uri?.let { context.contentResolver.openInputStream(it) }
            val bytes = inputStream?.readBytes()
            inputStream?.close()
            bytes?.let { Base64.encodeToString(it, Base64.NO_WRAP) }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun handleUri(uri: Uri?) {
        uri?.let {
            val base64 = uriToBase64(context, it)
            base64?.let(onImageSelected)
        }
    }

    private fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
        return try {
            val input = context.contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun base64ToBitmap(base64Str: String): Bitmap? {
        return try {
            val bytes = Base64.decode(base64Str, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}