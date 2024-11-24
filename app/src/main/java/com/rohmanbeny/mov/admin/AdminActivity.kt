package com.rohmanbeny.mov.admin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rohmanbeny.mov.R
import com.rohmanbeny.mov.model.Film
import kotlinx.android.synthetic.main.activity_admin.*

class AdminActivity : AppCompatActivity() {

    private lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        mDatabase = FirebaseDatabase.getInstance().getReference("Movies")

        btn_add_movie.setOnClickListener {
            val title = et_movie_title.text.toString()
            val desc = et_movie_desc.text.toString()
            val director = et_movie_director.text.toString()
            val genre = et_movie_genre.text.toString()
            val poster = et_movie_poster.text.toString()
            val rating = et_movie_rating.text.toString()

            if (title.isEmpty() || desc.isEmpty() || director.isEmpty() || genre.isEmpty() || poster.isEmpty() || rating.isEmpty()) {
                Toast.makeText(this, "Isi semua data", Toast.LENGTH_LONG).show()
            } else {
                addMovieToDatabase(title, desc, director, genre, poster, rating)
            }
        }
    }

    private fun addMovieToDatabase(
        title: String,
        desc: String,
        director: String,
        genre: String,
        poster: String,
        rating: String
    ) {
        val movieId = mDatabase.push().key ?: return
        val movie = Film(desc, director, genre, title, poster, rating)

        mDatabase.child(movieId).setValue(movie).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Film berhasil ditambahkan", Toast.LENGTH_LONG).show()
                et_movie_title.text.clear()
                et_movie_desc.text.clear()
                et_movie_director.text.clear()
                et_movie_genre.text.clear()
                et_movie_poster.text.clear()
                et_movie_rating.text.clear()
            } else {
                Toast.makeText(this, "Gagal menambahkan film", Toast.LENGTH_LONG).show()
            }
        }
    }
}
