package bmj.android.hackernews.ui.article.listview.adapter

import android.content.Context
import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

abstract class SwipeToDeleteCallback() : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        getDefaultUIUtil().onDraw(
            c,
            recyclerView,
            (viewHolder as ArticleListAdapter.ArticleViewHolder).binding.articleCard,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        getDefaultUIUtil().clearView((viewHolder as ArticleListAdapter.ArticleViewHolder).binding.articleCard)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (viewHolder != null) {
            getDefaultUIUtil().onSelected((viewHolder as ArticleListAdapter.ArticleViewHolder).binding.articleCard)
        }
    }

    override fun onChildDrawOver(
        c: Canvas, recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        getDefaultUIUtil().onDrawOver(
            c, recyclerView,
            (viewHolder as ArticleListAdapter.ArticleViewHolder).binding.articleCard, dX, dY,
            actionState, isCurrentlyActive
        )
    }
}