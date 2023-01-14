package db

import android.os.Parcel
import android.os.Parcelable
import android.provider.BaseColumns


// создание базы данных
object MyDbNameClass : BaseColumns{
    const val TABLE_NAME = "my_Db"
    const val COLUMN_NAME_TITLE = "title"
    const val COLUMN_NAME_CONTENT = "content"
    const val COLUMN_NAME_IMG_URI = "uri"

    const val DATABASE_VERSION = 2                    //версия бд
    const val DATABASE_NAME = "FeedReader.db"         //название бд

        // создание таблицы
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME(" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "$COLUMN_NAME_TITLE TEXT," +
            "$COLUMN_NAME_CONTENT TEXT,"+
            "$COLUMN_NAME_IMG_URI TEXT)"
                // удаление таблицы
    const val DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}