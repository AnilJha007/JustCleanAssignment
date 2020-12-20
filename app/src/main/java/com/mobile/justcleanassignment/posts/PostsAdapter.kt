package com.mobile.justcleanassignment.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.justcleanassignment.R
import com.mobile.justcleanassignment.service.modal.Post
import kotlinx.android.synthetic.main.item_post.view.*

class PostsAdapter(
    private val postItemClickListener: (Int) -> Unit
) : RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    private val postList = ArrayList<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    override fun getItemCount() = postList.size

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(post: Post) {
            with(itemView) {
                post.apply {
                    tv_title.text = title
                    tv_body.text = body
                }
                setOnClickListener {
                    postItemClickListener(post.id)
                }
            }
        }
    }

    fun updateData(posts: MutableList<Post>) {
        postList.apply {
            clear()
            addAll(posts)
        }
        notifyDataSetChanged()
    }
}