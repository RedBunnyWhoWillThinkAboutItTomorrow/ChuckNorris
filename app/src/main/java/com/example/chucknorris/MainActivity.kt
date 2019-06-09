package com.example.chucknorris


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

class MainActivity : AppCompatActivity() {

   
    val URL = "https://api.icndb.com/jokes/random"
    var okHttpClient: OkHttpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Picasso.with(this).load("https://media.gettyimages.com/photos/actor-chuck-norris-poses-for-a-portrait-session-in-january-2003-in-picture-id83457444?s=612x612").into(imageView);

        pressBtn.setOnClickListener {
            loadRandomFact()
        }
    }

    private fun loadRandomFact() {
        runOnUiThread {
            progressBar.visibility = View.VISIBLE
        }

        val request: Request = Request.Builder().url(URL).build()
        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call?, e: IOException?) {
            }

            override fun onResponse(call: Call?, response: Response?) {
                val json = response?.body()?.string()
                // we get the joke from the Web Service
                val txt = (JSONObject(json).getJSONObject("value").get("joke")).toString()

                // we update the UI from the UI Thread
                runOnUiThread {
                    progressBar.visibility = View.GONE
                    // we use Html class to decode html entities
                    jokes.text = Html.fromHtml(txt)
                }
            }
        })

    }
}