package com.github.alekseygett.newsapp.data.local

import androidx.room.*
import com.github.alekseygett.newsapp.data.model.ArticleEntity

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(item: ArticleEntity)

    @Query("SELECT * FROM ${ArticleEntity.TABLE_NAME}")
    suspend fun read(): List<ArticleEntity>

    @Update
    suspend fun update(item: ArticleEntity)

    @Delete
    suspend fun delete(entity: ArticleEntity)

}