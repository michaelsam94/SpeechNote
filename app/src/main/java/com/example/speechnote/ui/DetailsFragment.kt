package com.example.speechnote.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.speechnote.R
import com.example.speechnote.data.model.Session
import com.example.speechnote.databinding.FragmentDetailsBinding
import com.example.speechnote.utils.showMsg
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailsFragment: Fragment(R.layout.fragment_details) {

    private val TAG = "DetailsFragment"
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var mSpeechRecognizer: SpeechRecognizer
    private lateinit var mSpeechRecognizerIntent: Intent


    private val navArgs: DetailsFragmentArgs by navArgs()

    val viewModel: DetailsViewModel by viewModel()

    private val requestRecordPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            startSpeechRecognition()
        } else {
           showMsg("you should give record permission")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSpeechRecognizer()

        binding.startBtn.setOnClickListener {
            checkRecordPermission()
        }

        binding.sessionContentTv.text = navArgs.session.content

    }

    private fun checkRecordPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            startSpeechRecognition()
        } else {
            requestRecordPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO);
        }
    }


    private fun initSpeechRecognizer() {
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(requireContext())
        mSpeechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        mSpeechRecognizerIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar-EG")
        mSpeechRecognizerIntent.putExtra(
            RecognizerIntent.EXTRA_CALLING_PACKAGE,
            requireContext().packageName
        )
        mSpeechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle) {
                // Called when the recognizer is ready to start listening
                Log.d(TAG,"onReadyForSpeech")
            }

            override fun onBeginningOfSpeech() {
                // Called when the user starts speaking
                binding.sessionContentTv.text = "listneng...."
            }

            override fun onRmsChanged(rmsdB: Float) {
                // Called when the sound level of the user's speech changes
                Log.d(TAG,"onRmsChanged")
            }

            override fun onBufferReceived(p0: ByteArray?) {
                Log.d(TAG,"onBufferReceived")

            }

            override fun onEndOfSpeech() {
                // Called when the user stops speaking
                Log.d(TAG,"onEndOfSpeech")
            }

            override fun onError(error: Int) {
                // Called when an error occurs during recognition
                Log.d(TAG,"onError")
            }

            override fun onResults(results: Bundle) {
                // Called when recognition results are available
                Log.d(TAG,"onResults")

                val matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (matches != null && !matches.isEmpty()) {
                    val text = matches[0]
                    // Handle the recognized text
                    binding.sessionContentTv.text = text
                    val session = Session(navArgs.session.id,navArgs.session.title,text)
                    viewModel.updateSessionContent(session)
                }
            }

            override fun onPartialResults(partialResults: Bundle) {
                // Called when partial recognition results are available
                Log.d(TAG,"onPartialResults")
            }

            override fun onEvent(eventType: Int, params: Bundle) {
                // Called when a recognition event occurs
                Log.d(TAG,"onPartialResults")
            }
        })
    }

    private fun startSpeechRecognition() {
        mSpeechRecognizer.startListening(mSpeechRecognizerIntent)
    }

    private fun stopSpeechRecognition() {
        mSpeechRecognizer.stopListening()
    }





    override fun onDestroyView() {
        super.onDestroyView()
        stopSpeechRecognition()
    }


}