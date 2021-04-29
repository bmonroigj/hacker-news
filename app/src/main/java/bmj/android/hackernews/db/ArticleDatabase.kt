package bmj.android.hackernews.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import bmj.android.hackernews.model.Article
import bmj.android.hackernews.model.DeletedArticle
import bmj.android.hackernews.util.DATABASE_NAME

/**
 * The Room database for this app
 */
@Database(
    entities = [Article::class, DeletedArticle::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun deletedArticleDao(): DeletedArticleDao

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: ArticleDatabase? = null

        fun getInstance(context: Context): ArticleDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): ArticleDatabase {
            return Room.databaseBuilder(context, ArticleDatabase::class.java, DATABASE_NAME).build()
        }
    }
}