package com.example.texttospeech

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import android.media.AudioManager
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var etTTS: EditText
    private lateinit var btnExecute: Button
    private lateinit var btnGahyun: Button
    private lateinit var btnYeobo: Button
    private lateinit var btnMacaron: Button
    private lateinit var btnJadu: Button
    private lateinit var volumeSeekBar: SeekBar
    private var tts: TextToSpeech? = null
    private lateinit var audioManager: AudioManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etTTS = findViewById(R.id.editText)
        btnExecute = findViewById(R.id.button01)
        btnGahyun = findViewById(R.id.button_gahyun)
        btnYeobo = findViewById(R.id.button_yeobo)
        btnMacaron = findViewById(R.id.button_macaron)
        btnJadu = findViewById(R.id.button_jadu)
        volumeSeekBar = findViewById(R.id.volumeSeekBar)

        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        // Initialize volumeSeekBar
        volumeSeekBar.max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        volumeSeekBar.progress = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

        volumeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        tts = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = tts?.setLanguage(Locale.KOREAN)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    // 언어가 지원되지 않거나 데이터가 누락된 경우 처리
                    // 에러 메시지 등을 표시할 수 있습니다.
                }
            }
        }

        btnExecute.setOnClickListener {
            val text = etTTS.text.toString()
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts1")
        }

        btnGahyun.setOnClickListener {
            tts?.speak("가현아", TextToSpeech.QUEUE_FLUSH, null, "tts2")
        }

        btnYeobo.setOnClickListener {
            tts?.speak("여보", TextToSpeech.QUEUE_FLUSH, null, "tts3")
        }

        btnMacaron.setOnClickListener {
            tts?.speak("마카롱", TextToSpeech.QUEUE_FLUSH, null, "tts4")
        }

        // 새로운 버튼 클릭 리스너 추가
        btnJadu.setOnClickListener {
            tts?.speak("자두", TextToSpeech.QUEUE_FLUSH, null, "tts5")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tts?.stop()
        tts?.shutdown()
    }
}
