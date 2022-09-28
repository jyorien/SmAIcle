package com.example.smaicle

import android.content.Intent
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
import org.json.JSONArray
import org.json.JSONObject
import java.io.*

const val UPCYCLING = "UPCYCLING"
const val RECYCLING = "RECYCLING"
const val EVENT = "EVENT"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var recyclingInstructions = arrayListOf<String>()
    private var upcyclingTips = hashMapOf<String, String>()
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
                            response.body()?.string()?.let { json ->
                                val jsonObject = JSONObject(json)
                                val imageString = jsonObject["base64_image"].toString()
                                val segmentedObjects = JSONObject(jsonObject["segmented_objects"].toString())
                                val objectKeys = segmentedObjects.keys()
                                objectKeys.forEach { key ->
                                    val obj = JSONObject(segmentedObjects[key].toString())
                                    val recycling = JSONArray(obj["recycling"].toString())
                                    val upcycling = JSONObject(JSONArray(obj["upcycling"].toString())[0].toString())
                                    val recyclingList = arrayListOf<String>()
                                    val upcyclingKeys = upcycling.keys()
                                    val upcyclingMap = hashMapOf<String, String>()
                                    upcyclingKeys.forEach {
                                        upcyclingMap[it] = upcycling[it].toString()
                                    }
                                    for (i in 0 until recycling.length()) {
                                        recyclingList.add(recycling[i].toString())
                                    }
                                    // recycling and upcycling data
//                                    ["Crush the bottle.","Remove the cap.","Squeeze out the air.","Put the bottle cap back on.","Throw it into the plastic bin."]
//                                    {Practical ways to upcycle bottles=https://www.treehugger.com/creative-ways-upcycle-your-plastic-bottles-4864134,
//                                    38 creative ideas with bottles=https://www.youtube.com/watch?v=xEAOvFG1AmM}
                                    Log.d("hello", recycling.toString())
                                    recyclingInstructions = recyclingList
                                    Log.d("hello", upcyclingMap.toString())
                                    upcyclingTips = upcyclingMap
                                }
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
        binding.btnRecycleInstructions.setOnClickListener {
            Intent(this, DetailActivity::class.java).also {
                it.putExtra(EVENT, "recycle")
                it.putStringArrayListExtra(RECYCLING, recyclingInstructions)
                startActivity(it)
                overridePendingTransition(R.anim.slide_up, R.anim.slide_down)
            }
        }
        binding.btnUpcyclingIdeas.setOnClickListener {
            Intent(this, DetailActivity::class.java).also {
                it.putExtra(EVENT, "upcycle")
                val b= Bundle()
                b.putSerializable(UPCYCLING, upcyclingTips)
                it.putExtra("bundle", b)
                startActivity(it)
                overridePendingTransition(R.anim.slide_up, R.anim.slide_down)
            }
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