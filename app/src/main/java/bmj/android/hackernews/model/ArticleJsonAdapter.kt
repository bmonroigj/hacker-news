package bmj.android.hackernews.model

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.internal.Util
import java.util.*

public class ArticleJsonAdapter(
    moshi: Moshi
) : JsonAdapter<Article>() {
    private val options: JsonReader.Options = JsonReader.Options.of(
        "created_at_i", "title", "url",
        "story_title", "story_url", "author", "created_at"
    )

    private val longAdapter: JsonAdapter<Long> = moshi.adapter(Long::class.java, emptySet(), "id")

    private val nullableStringAdapter: JsonAdapter<String?> = moshi.adapter(
        String::class.java,
        emptySet(), "title"
    )

    private val stringAdapter: JsonAdapter<String> = moshi.adapter(
        String::class.java, emptySet(),
        "author"
    )

    private val dateAdapter: JsonAdapter<Date> = moshi.adapter(
        Date::class.java, emptySet(),
        "date"
    )

    public override fun toString(): String = buildString(29) {
        append("GeneratedJsonAdapter(").append("Article").append(')')
    }

    public override fun fromJson(reader: JsonReader): Article {
        var id: Long? = null
        var title: String? = null
        var url: String? = null
        var storyTitle: String? = null
        var storyUrl: String? = null
        var author: String? = null
        var date: Date? = null
        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.selectName(options)) {
                0 -> id = longAdapter.fromJson(reader) ?: throw Util.unexpectedNull(
                    "id", "created_at_i",
                    reader
                )
                1 -> title = nullableStringAdapter.fromJson(reader)
                2 -> url = nullableStringAdapter.fromJson(reader)
                3 -> storyTitle = nullableStringAdapter.fromJson(reader)
                4 -> storyUrl = nullableStringAdapter.fromJson(reader)
                5 -> author = stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull(
                    "author",
                    "author", reader
                )
                6 -> date = dateAdapter.fromJson(reader) ?: throw Util.unexpectedNull(
                    "date",
                    "created_at", reader
                )
                -1 -> {
                    // Unknown name, skip it.
                    reader.skipName()
                    reader.skipValue()
                }
            }
        }
        reader.endObject()
        return Article(
            id = id ?: throw Util.missingProperty("id", "created_at_i", reader),
            title = storyTitle ?: title ?: "No title",
            url = storyUrl ?: url ?: "http://www.google.com",
            author = author ?: throw Util.missingProperty("author", "author", reader),
            date = date ?: throw Util.missingProperty("date", "created_at", reader)
        )
    }

    public override fun toJson(writer: JsonWriter, value_: Article?): Unit {
        if (value_ == null) {
            throw NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.")
        }
        writer.beginObject()
        writer.name("created_at_i")
        longAdapter.toJson(writer, value_.id)
        writer.name("title")
        nullableStringAdapter.toJson(writer, value_.title)
        writer.name("url")
        nullableStringAdapter.toJson(writer, value_.url)
        writer.name("story_title")
        nullableStringAdapter.toJson(writer, value_.title)
        writer.name("story_url")
        nullableStringAdapter.toJson(writer, value_.url)
        writer.name("author")
        stringAdapter.toJson(writer, value_.author)
        writer.name("created_at")
        dateAdapter.toJson(writer, value_.date)
        writer.endObject()
    }
}