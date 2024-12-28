package com.example.week1

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class allListDB(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${TABLE_NAME}"

    override fun onCreate(db: SQLiteDatabase) {
        val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${TABLE_NAME} (" +
                    //       "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${TEAM} TEXT," +
                    "${STADIUM} TEXT)"
        db.execSQL(SQL_CREATE_ENTRIES)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun insertTeam(name: String, stadium: String): Long {
        val db = writableDatabase
        val values = ContentValues()
        values.put(TEAM, name)
        values.put(STADIUM, stadium)
        return db.insert(TABLE_NAME, null, values)
    }

    fun getAllTeams(): List<Team> {
        val userList = mutableListOf<Team>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT DISTINCT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val user = Team(
                    name = cursor.getString(cursor.getColumnIndexOrThrow(TEAM)),
                    stadium = cursor.getString(cursor.getColumnIndexOrThrow(STADIUM))
                )
                userList.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return userList
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "FeedReader.db"
        const val TABLE_NAME = "entry"
        const val TEAM = "team"
        const val STADIUM = "stadium"
    }
}