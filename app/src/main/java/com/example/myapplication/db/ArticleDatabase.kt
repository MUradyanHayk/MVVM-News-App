package com.example.myapplication.db

import android.content.Context
import androidx.room.*
import com.example.myapplication.data.Article
import com.example.myapplication.utils.Constants

@Database(entities = [Article::class], version = Constants.DATABASE_VERSION)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao

    companion object {
        private var instance: ArticleDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context): ArticleDatabase {
            return Room.databaseBuilder(context.applicationContext, ArticleDatabase::class.java, Constants.DATABASE_NAME).build()
        }
    }
}