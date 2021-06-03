package com.example.instafire

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instafire.models.Post

class PostsAdapter(private val context: Context, private val dataset: List<Post>): RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // create props for any elements you 'd want to access later
        val postUsername: TextView = view.findViewById(R.id.post_username)
        val postTime: TextView = view.findViewById(R.id.post_time)
        val postImage: ImageView = view.findViewById(R.id.post_image)
        val postDescription: TextView = view.findViewById(R.id.post_description)

        init {
            // define any click listeners here for the ViewHolder's views.

        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_post, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = dataset[position]
        holder.postUsername.text = post.user?.username
        holder.postTime.text = DateUtils.getRelativeTimeSpanString(post.creation_time_ms)
        holder.postDescription.text = post.description
        // use Glide instead for smoother experience
        Glide.with(context).load(post.image_url).into(holder.postImage)
    }

    override fun getItemCount() = dataset.size
}