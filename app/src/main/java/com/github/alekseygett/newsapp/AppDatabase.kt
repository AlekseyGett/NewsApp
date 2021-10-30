package com.github.alekseygett.newsapp

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.alekseygett.newsapp.data.local.ArticleDao
import com.github.alekseygett.newsapp.data.model.ArticleEntity
import com.github.alekseygett.newsapp.utils.Converters

@Database(entities = [ArticleEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}