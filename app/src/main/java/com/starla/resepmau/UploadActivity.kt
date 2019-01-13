package com.starla.resepmau

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.View
import android.widget.Toast
import com.starla.resepmau.converter.WrappedResponse
import com.starla.resepmau.model.Recipe
import com.starla.resepmau.service.PostService
import com.starla.resepmau.util.ApiUtil

import kotlinx.android.synthetic.main.activity_upload.*
import kotlinx.android.synthetic.main.content_upload.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UploadActivity : AppCompatActivity() {

    private var settings: SharedPreferences? = null
    private var postService = ApiUtil.getPostService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { finish() }
        initComp()
        upload()
    }

    private fun initComp(){
        settings = getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
    }

    private fun getToken() = settings?.getString("TOKEN", "UNDEFINED")

    private fun upload(){
        fab.setOnClickListener { view ->
            val t = et_title.text.toString().trim()
            val c = et_content.text.toString().trim()
            if(!t.isEmpty() && !c.isEmpty() && c.length > 20 && t.length > 6){
                loading.visibility = View.VISIBLE
                fab.isEnabled = false
                val post : Call<WrappedResponse<Recipe>> = postService.addNew("Bearer ${getToken()}", t, c)
                post.enqueue(object : Callback<WrappedResponse<Recipe>>{
                    override fun onFailure(call: Call<WrappedResponse<Recipe>>, t: Throwable) { Toast.makeText(this@UploadActivity, "Failure : ${t.message}", Toast.LENGTH_LONG).show() }
                    override fun onResponse(call: Call<WrappedResponse<Recipe>>, response: Response<WrappedResponse<Recipe>>) {
                        if(response.isSuccessful){
                            val body : WrappedResponse<Recipe>? = response.body()
                            if(body?.status.equals("1")){
                                loading.visibility = View.INVISIBLE
                                fab.isEnabled = true
                                Toast.makeText(this@UploadActivity, "Successfully added", Toast.LENGTH_SHORT).show()
                                finish()
                            }else{
                                Toast.makeText(this@UploadActivity, "Cannot add new post", Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            Toast.makeText(this@UploadActivity, "Response is not success", Toast.LENGTH_SHORT).show()
                        }
                    }
                })

            }else{
                Snackbar.make(view, "Please fill all forms and minimum character is 20 and 6 for title", Snackbar.LENGTH_LONG)
                        .setAction("OK", null).show()
            }
            loading.visibility = View.INVISIBLE
            fab.isEnabled = true
        }
    }
}
