package com.example.speechnote.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.speechnote.R
import com.example.speechnote.data.model.Session
import com.example.speechnote.databinding.FragmentSessionsBinding
import com.example.speechnote.utils.Resource
import com.example.speechnote.utils.gone
import com.example.speechnote.utils.show
import com.example.speechnote.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class SessionFragment : Fragment(R.layout.fragment_sessions), SessionsAdapter.Interaction {

    private val viewModel: SessionViewModel by viewModel()
    
    private lateinit var binding: FragmentSessionsBinding

    private val sessionsAdapter by lazy { SessionsAdapter(this) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSessionsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeToSessionsLiveData()
        observeToLastInsertedSessionLiveData()

        viewModel.getSessions()
        binding.addSessionFab.setOnClickListener {
            val session = Session(title = "Session ${sessionsAdapter.itemCount + 1}", content = "")
            viewModel.addSession(session)
        }
    }

    private fun observeToLastInsertedSessionLiveData() {
        viewModel.getLastInsertedSessionLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    binding.progressBar.gone()
                    it.msg?.let { msg -> showToast(msg) }
                }
                is Resource.Loading -> binding.progressBar.show()
                is Resource.Success -> {
                    binding.progressBar.gone()
                    if (it.data != null) {
                        findNavController().navigate(
                            SessionFragmentDirections.actionSessionFragmentToDetailsFragment(
                                it.data
                            )
                        )
                    }
                }
            }
        }
    }

    private fun observeToSessionsLiveData() {
        viewModel.getSessionsLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    binding.progressBar.gone()
                    it.msg?.let { msg -> showToast(msg) }
                }
                is Resource.Loading -> binding.progressBar.show()
                is Resource.Success -> {
                    binding.progressBar.gone()
                    if (it.data != null) {
                       sessionsAdapter.addSessionToList(it.data)
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.sessionsRecycler.apply {
            adapter = sessionsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }



    override fun onItemSelected(position: Int, item: Session) {
        findNavController().navigate(SessionFragmentDirections.actionSessionFragmentToDetailsFragment(item))
    }


}