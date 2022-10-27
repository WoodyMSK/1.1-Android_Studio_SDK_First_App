package ru.netology.nmedia.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity

@Dao
interface PostDao {
    @Query("SELECT * FROM PostEntity ORDER BY id DESC")
    fun get(): LiveData<List<PostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(post: PostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveNewEditedPost(post: PostEntity)

    @Query("""
        UPDATE PostEntity SET
        likes = likes + CASE WHEN liked THEN -1 ELSE 1 END,
        liked = CASE WHEN liked THEN 0 ELSE 1 END
        WHERE id = :id;
        """)
    fun likeById(id: Long)

    @Query("DELETE FROM PostEntity WHERE id = :id")
    fun removeById(id: Long)

}
