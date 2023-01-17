package com.example.mysqllite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.helper.widget.Carousel.Adapter
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import db.MyDbManager
import db.RcAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val myDbManager = MyDbManager(this)
    val rcAdapter = RcAdapter(ArrayList(), this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun onClickNew(view: View) {
        val i = Intent(this, EditActivity::class.java)
        startActivity(i)
    }
    override fun onResume() {
        super.onResume()
        myDbManager.openDb()
        fillAdapter()
    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }

    fun init(){
        rcView.layoutManager = LinearLayoutManager(this)
        val swapHelper = getShwap()
        swapHelper.attachToRecyclerView(rcView)
        rcView.adapter = rcAdapter
    }

    fun fillAdapter(){
        val list = myDbManager.readDbData()
        rcAdapter.updateAdapter(list)
        if (list.size > 0){
            tvNoElements.visibility = View.GONE
        }else{
            tvNoElements.visibility = View.GONE
        }
    }

    private fun getShwap(): ItemTouchHelper{
        return (ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                rcAdapter.removeItem(viewHolder.adapterPosition, myDbManager)
            }

        }))
    }
}