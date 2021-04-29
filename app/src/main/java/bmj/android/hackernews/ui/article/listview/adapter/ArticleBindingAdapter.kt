package bmj.android.hackernews.ui.article.listview.adapter

import android.text.format.DateUtils.*
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.util.*

@BindingAdapter("textFromDate")
fun bindTextFromDate(view: TextView, date: Date) {
    view.text = getRelativeTimeSpanString(
        date.time,
        System.currentTimeMillis(),
        MINUTE_IN_MILLIS,
        FORMAT_ABBREV_ALL
    )
}