package com.example.myapplication.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class ImageBitmapString {
   /* @TypeConverter
    fun BitMapToString(bitmap:Bitmap):String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        val temp = Base64.encodeToString(b, Base64.DEFAULT)
        if (temp == null)
        {
            return ""
        }
        else
            return temp
    }
    @TypeConverter
    fun StringToBitMap(encodedString:String):Bitmap {
        try {
            val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
            if (bitmap == null) {

            } else {
                return bitmap
            }
        }*/

        @TypeConverter
        fun  toBitMap(bytes:ByteArray):Bitmap{
            return BitmapFactory.decodeByteArray(bytes,0,bytes.size)
        }

        @TypeConverter
        fun  fromBitmap(bmp:Bitmap):ByteArray{
            val  outputStream = ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.PNG,100,outputStream)
            return  outputStream.toByteArray()
        }
}