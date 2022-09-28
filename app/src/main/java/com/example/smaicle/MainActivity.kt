package com.example.smaicle

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.smaicle.databinding.ActivityMainBinding
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.PictureResult
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.O)
    private val client = RetrofitService.getClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.cameraView.setLifecycleOwner(this)
        setContentView(binding.root)
        binding.cameraView.addCameraListener(object : CameraListener() {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onPictureTaken(result: PictureResult) {
                super.onPictureTaken(result)
                result.toBitmap(1000, 1000) {
                    enableSendingState()
//                        enableReceivedState()
//                        it?.let { bm ->
//                            binding.imgView.setImageBitmap(bm)
//                        }
                    it?.let { bitmap ->
                        val file = File(cacheDir, "hello" + ".jpg")
                        file.createNewFile()
                        Log.d("hello", file.toString())
                        val bos = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
                        val bitmapData = bos.toByteArray()

                        var fos: FileOutputStream? = null
                        try {
                            fos = FileOutputStream(file)
                        } catch (e: FileNotFoundException) {
                            e.printStackTrace()
                        }
                        try {
                            fos?.let { stream ->
                                stream.write(bitmapData)
                                stream.flush()
                                stream.close()
                            }
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                        val reqFile = RequestBody.create(MediaType.parse("image/*"), file)
                        val body = MultipartBody.Part.createFormData("file", file.name, reqFile)

                        lifecycleScope.launchWhenCreated {
                            val response = client.getLabelledImage(body)
                            Log.d("hello","hello")
                            Log.d("hello", response.body().toString())
                            response.body()?.string()?.let { json ->
                                val jsonObject = JSONObject(json)
                                val imageString = jsonObject["base64_image"].toString()
                                val recycling = jsonObject["segmented_objects"].toString()
                                Log.d("hello", recycling)
                                val bytes = Base64.decode(imageString, Base64.DEFAULT)
                                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                                binding.imgView.setImageBitmap(bmp)
                                enableReceivedState()
                            }
                        }


                    }
                }


            }
        })
        binding.btnCapture.setOnClickListener {
            binding.cameraView.takePicture()
        }
        enableCapturingState()
    }

    private fun enableCapturingState() {
        supportActionBar?.title = "Capture a Recyclable"
        binding.cameraView.visibility = View.VISIBLE
        binding.btnCapture.visibility = View.VISIBLE

        binding.imgView.visibility = View.GONE
        binding.btnRecycle.visibility = View.GONE
        binding.btnRecycleInstructions.visibility = View.GONE
        binding.btnUpcyclingIdeas.visibility = View.GONE
        binding.progressIndicator.visibility = View.GONE
    }

    private fun enableSendingState() {
        supportActionBar?.hide()
        binding.progressIndicator.visibility = View.VISIBLE

        binding.cameraView.visibility = View.GONE
        binding.btnCapture.visibility = View.GONE
        binding.imgView.visibility = View.GONE
        binding.btnRecycle.visibility = View.GONE
        binding.btnRecycleInstructions.visibility = View.GONE
        binding.btnUpcyclingIdeas.visibility = View.GONE
    }

    private fun enableReceivedState() {
        supportActionBar?.hide()
        binding.imgView.visibility = View.VISIBLE
        binding.btnRecycle.visibility = View.VISIBLE
        binding.btnRecycleInstructions.visibility = View.VISIBLE
        binding.btnUpcyclingIdeas.visibility = View.VISIBLE

        binding.cameraView.visibility = View.GONE
        binding.btnCapture.visibility = View.GONE
        binding.progressIndicator.visibility = View.GONE

    }


}