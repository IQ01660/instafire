package com.example.instafire

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.instafire.databinding.ActivityPostsBinding
import com.example.instafire.models.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PostsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostsBinding

    // the Firestore db field
    private lateinit var db: FirebaseFirestore

    // the posts data and RecyclerView stuff
    private lateinit var posts: MutableList<Post>
    private lateinit var adapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initializing the Firestore db
        db = Firebase.firestore
        // Initializing an empty mutable list of posts
        posts = mutableListOf()

        // Initializing the adapter
        adapter = PostsAdapter(this, posts)

        // connecting the adapter to the RecyclerView in PostsActivity
        binding.rvPosts.adapter = this.adapter

        displayPosts()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_posts, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.isEnabled = false
        // if user selected profile menu option
        // then navigate to Profile screen
        if (item.itemId == R.id.menu_profile) {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        item.isEnabled = true
        return super.onOptionsItemSelected(item)
    }

    private fun displayPosts() {
        val postsReference = db.collection("posts")
            .limit(20)
            .orderBy("creation_time_ms", Query.Direction.DESCENDING)


        postsReference
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w("POSTS", "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val postList = snapshot.toObjects(Post::class.java)

                    // let adapter know that we updated the posts
                    posts.clear()
                    posts.addAll(postList)
                    adapter.notifyDataSetChanged()

                    // logcat debugging
                    for (post in postList) {
                        Log.d("POSTS", "Document $post")
                    }
                } else {
                    Log.d("POSTS", "Current data: null")
                }


            }
    }
}