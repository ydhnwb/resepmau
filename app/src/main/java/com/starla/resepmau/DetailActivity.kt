package com.starla.resepmau

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.starla.resepmau.converter.WrappedResponse
import com.starla.resepmau.model.Recipe
import com.starla.resepmau.util.ApiUtil
import com.starla.resepmau.util.Utilities

import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.view.View


class DetailActivity : AppCompatActivity() {

    val postService = ApiUtil.getPostService()
    private var mRecipe = Recipe()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { finish() }
        fetchData()
        update()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()
        if (id == R.id.action_delete) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Delete")
            builder.setMessage("Are you sure to delete this item?")
            builder.setPositiveButton("HIT IT") { dialog, which ->
                delete()
            }
            builder.setNegativeButton("CANCEL", { dialog, which -> dialog.cancel() })
            builder.setCancelable(false)
            builder.show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun fetchData(){
        val id = intent.getIntExtra("ID", 0)
        if(id == 0){finish()}
        else{
            val data :  Call<WrappedResponse<Recipe>> = postService.find("Bearer ${Utilities.getToken(this@DetailActivity)}", id.toString())
            data.enqueue(object : Callback<WrappedResponse<Recipe>>{
                override fun onFailure(call: Call<WrappedResponse<Recipe>>, t: Throwable) {
                    Toast.makeText(this@DetailActivity, "Please check your internet", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<WrappedResponse<Recipe>>, response: Response<WrappedResponse<Recipe>>) {
                    if(response.isSuccessful){
                        val body : WrappedResponse<Recipe>? = response.body()
                        if(body?.status.equals("1")){
                            mRecipe = body!!.data
                            bindData(mRecipe)
                        }
                    }else{
                        Toast.makeText(this@DetailActivity, "Response is not successfull", Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }
    }

    private fun bindData(recipe: Recipe){
        et_title.setText(recipe.title)
        et_content.setText(recipe.content)
    }

    private fun update(){
        fab.setOnClickListener {
            val id = intent.getIntExtra("ID", 0)
            val t = et_title.text.toString().trim()
            val c = et_content.text.toString().trim()
            if(!t.isEmpty() && !c.isEmpty() && t.length > 6 && c.length > 20){
                loading.visibility = View.VISIBLE
                fab.isEnabled = false
                val task : Call<WrappedResponse<Recipe>> = postService.update("Bearer ${Utilities
                        .getToken(this@DetailActivity)}",id.toString() ,t,c)

                task.enqueue(object : Callback<WrappedResponse<Recipe>>{
                    override fun onFailure(call: Call<WrappedResponse<Recipe>>, t: Throwable) {
                        loading.visibility = View.INVISIBLE
                        fab.isEnabled = true
                    }

                    override fun onResponse(call: Call<WrappedResponse<Recipe>>, response: Response<WrappedResponse<Recipe>>) {
                        if(response.isSuccessful){
                            val body : WrappedResponse<Recipe>? = response.body()
                            if(body?.status.equals("1")){
                                loading.visibility = View.INVISIBLE
                                fab.isEnabled = true
                                Toast.makeText(this@DetailActivity, "Successfully updated", Toast.LENGTH_SHORT).show()
                                finish()
                            }else{
                                Toast.makeText(this@DetailActivity, "Failed to update", Toast.LENGTH_SHORT).show()
                            }
                            loading.visibility = View.INVISIBLE
                            fab.isEnabled = true
                        }
                    }
                })
            }else{
                loading.visibility = View.INVISIBLE
                fab.isEnabled = true
                Toast.makeText(this@DetailActivity, "Cannot be empty and must be 20 char long and 6 char long for title", Toast.LENGTH_SHORT).show() }
        }
    }

    private fun delete(){
        loading.visibility = View.VISIBLE
        val id = intent.getIntExtra("ID", 0)
        val task : Call<WrappedResponse<Recipe>> = postService.delete("Bearer ${Utilities.getToken(this@DetailActivity)}",
                id.toString())
        task.enqueue(object : Callback<WrappedResponse<Recipe>>{
            override fun onFailure(call: Call<WrappedResponse<Recipe>>, t: Throwable) {
                loading.visibility = View.INVISIBLE
            }
            override fun onResponse(call: Call<WrappedResponse<Recipe>>, response: Response<WrappedResponse<Recipe>>) {
                if(response.isSuccessful){
                    val body : WrappedResponse<Recipe>? = response.body()
                    if(body?.status.equals("1")){
                        loading.visibility = View.INVISIBLE
                        Toast.makeText(this@DetailActivity, "Successfully deleted", Toast.LENGTH_LONG).show()
                        finish()
                    }else{
                        Toast.makeText(this@DetailActivity, "Failed to delete", Toast.LENGTH_LONG).show()
                    }
                    loading.visibility = View.INVISIBLE
                }
            }
        })
    }

}
