package db

import android.content.Context
import android.content.Intent
import android.icu.text.CaseMap.Title
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.TwoLineListItem
import androidx.recyclerview.widget.RecyclerView
import com.example.mysqllite.EditActivity
import com.example.mysqllite.R

class RcAdapter(listMain: ArrayList<ListItem>, contextM: Context) : RecyclerView.Adapter<RcAdapter.MyHolder>() {
    var listArray = listMain
    var context = contextM

    class MyHolder(itemView: View, contextV: Context) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_Title)
        val context = contextV
        fun setData(item: ListItem){
            tvTitle.text = item.title
            itemView.setOnClickListener{
                val intent = Intent(context, EditActivity::class.java).apply {
                    putExtra(MyIntentContants.I_TITLE_KEY, item.title)
                    putExtra(MyIntentContants.I_DISC_KEY, item.disk)
                    putExtra(MyIntentContants.I_URI_KEY, item.uri)
                }
                context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyHolder(inflater.inflate(R.layout.rc_layout, parent, false), context)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.setData(listArray.get(position))
    }

    override fun getItemCount(): Int {
        return  listArray.size
    }

    fun updateAdapter(listItems: List<ListItem>){
        listArray.clear()
        listArray.addAll(listItems)
        notifyDataSetChanged()

    }

    fun removeItem(pos:Int, dbManager: MyDbManager){            //удаляем из адаптера
        dbManager.deleteFromDB(listArray[pos].id.toString())
        listArray.removeAt(pos)
        notifyItemRangeChanged(0, listArray.size)
        notifyItemRemoved(pos)
    }
}