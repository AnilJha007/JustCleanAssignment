package com.mobile.justcleanassignment.ui.postdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.justcleanassignment.R
import com.mobile.justcleanassignment.service.modal.Comment
import kotlinx.android.synthetic.main.item_comment.view.*

class CommentsAdapter : RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {

    private val commentList = ArrayList<Comment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return CommentsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.bind(commentList[position])
    }

    override fun getItemCount() = commentList.size

    inner class CommentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(comment: Comment) {
            with(itemView) {
                comment.apply {
                    tv_body.text = body
                    tv_email.text = email
                    tv_name.text = name
                }
            }
        }
    }

    fun updateData(comments: MutableList<Comment>) {
        commentList.apply {
            clear()
            addAll(comments)
        }
        notifyDataSetChanged()
    }
}