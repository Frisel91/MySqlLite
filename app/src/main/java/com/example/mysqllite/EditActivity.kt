package com.example.mysqllite

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build.VERSION_CODES.M
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import db.MyDbManager
import db.MyIntentContants
import kotlinx.android.synthetic.main.edit_activity.*

class EditActivity : AppCompatActivity() {
    val imageRequestCode = 10
    var tempImageUri = "empty"
    val myDbManager = MyDbManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity)
        getMyIntent()
    }

    override fun onResume() {
        super.onResume()
        myDbManager.openDb()
    }
    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == imageRequestCode){
            my_image.setImageURI(data?.data)
            tempImageUri = data?.data.toString()
            contentResolver.takePersistableUriPermission(data?.data!!, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
    }
    }

    fun onClickAddImage(view: View) {
        myImageLayout.visibility = View.VISIBLE
        ic_add_image_btn.visibility = View.GONE
    }

    fun onClickDelit(view: View) {
        myImageLayout.visibility = View.GONE
        ic_add_image_btn.visibility = View.VISIBLE
    }

    fun onClickImage(view: View){
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        startActivityForResult(intent, imageRequestCode)
    }

    fun onClickSave(view: View){
        val myTitle = edTitle.text.toString()
        val myDisck = edDisck.text.toString()
        if (myTitle != "" && myDisck != ""){
            myDbManager.insertDB(myTitle, myDisck, tempImageUri)
        }
        edTitle.text.clear()
        edDisck.text.clear()
        finish()
    }

    fun getMyIntent(){
        val i = intent

        if (i != null){
            if(i.getStringExtra(MyIntentContants.I_TITLE_KEY) != null){
                ic_add_image_btn.visibility = View.GONE
                edTitle.setText(i.getStringExtra(MyIntentContants.I_TITLE_KEY))
                edDisck.setText(i.getStringExtra(MyIntentContants.I_DISC_KEY))
                if (i.getStringExtra(MyIntentContants.I_URI_KEY) != "empty"){

                    myImageLayout.visibility = View.VISIBLE

                    my_image.setImageURI(Uri.parse(i.getStringExtra(MyIntentContants.I_URI_KEY)))
                    imButtonDelet.visibility = View.GONE
                    imButtonEdit.visibility = View.GONE
                }
            }
        }
    }
}