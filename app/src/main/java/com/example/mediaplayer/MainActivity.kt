package com.example.mediaplayer

import android.content.res.Configuration
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore.Audio.Playlists
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListenerAdapter
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private val playlist= listOf("El anhelo de mi vida","Ha oír la voz de su alabanza")
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter=PlaylistAdapter()
        findViewById<ListView>(R.id.listView).adapter=adapter

        mediaPlayer=MediaPlayer.create(this,R.raw.elanelodemivida)
        mediaPlayer=MediaPlayer.create(this,R.raw.hazoirlavozdesualabanza)

    }

    private inner class PlaylistAdapter:ArrayAdapter<String>(this,R.layout.list_item,playlist) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view=convertView?:LayoutInflater.from(context).inflate(R.layout.list_item,parent,false)

            val songName=playlist[position]
            val textViewSong:TextView=view.findViewById(R.id.textViewSong)
            val buttonPlayPause:ImageButton=view.findViewById(R.id.buttonPlayPause)

            textViewSong.text=songName

            buttonPlayPause.setOnClickListener {
                if(mediaPlayer.isPlaying) {
                    mediaPlayer.pause()
                    buttonPlayPause.setImageResource(android.R.drawable.ic_media_play)
                    Toast.makeText(context,"Pause",Toast.LENGTH_SHORT).show()
                }else{
                    val resourceId=when(songName){
                        "El anhelo de mi vida"->R.raw.elanelodemivida
                        "Haz oír la voz de su alabanza"->R.raw.hazoirlavozdesualabanza
                        else->0

                    }
                    if (resourceId!=0){
                        mediaPlayer.release()
                        mediaPlayer=MediaPlayer.create(context,resourceId)
                        mediaPlayer.start()
                        buttonPlayPause.setImageResource(android.R.drawable.ic_media_play)
                        Toast.makeText(context,"Playing",Toast.LENGTH_SHORT).show()
                    }
                }
            }
            return view
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}