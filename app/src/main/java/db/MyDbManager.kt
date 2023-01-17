package db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class MyDbManager(val context: Context) {
    val myDbHelper = DbHelper(context)
    var db: SQLiteDatabase? = null

    fun openDb(){
        db = myDbHelper.writableDatabase
    }

    fun insertDB(title: String, content: String, uri: String){
        val values = ContentValues().apply {
            put(MyDbNameClass.COLUMN_NAME_TITLE, title)
            put(MyDbNameClass.COLUMN_NAME_CONTENT, content)
            put(MyDbNameClass.COLUMN_NAME_IMG_URI, uri)
        }

        db?.insert(MyDbNameClass.TABLE_NAME, null, values)
    }

    fun deleteFromDB(id: String){

        val selection = BaseColumns._ID + "=$id"
        db?.delete(MyDbNameClass.TABLE_NAME, selection, null)
    }
    fun readDbData(): ArrayList<ListItem>{
        val dataList = ArrayList<ListItem>()
        val cursor = db?.query(MyDbNameClass.TABLE_NAME,
            null,null,null,null,null,null)

            while (cursor?.moveToNext()!!){
                val dataText = cursor.getString(cursor.getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_TITLE))
                val dataContent = cursor.getString(cursor.getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_CONTENT))
                val dataUri = cursor.getString(cursor.getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_IMG_URI))
                val dataId = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
                val item = ListItem()
                item.title = dataText
                item.disk = dataContent
                item.uri = dataUri
                item.id = dataId
                dataList.add(item)
            }
        cursor.close()
        return dataList
    }

    fun closeDb(){
        myDbHelper.close()
    }
}