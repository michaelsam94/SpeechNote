package com.example.speechnote.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.speechnote.databinding.ItemSessionBinding
import com.example.speechnote.data.model.Session

class SessionsAdapter (private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val sessions = mutableListOf<Session>()

    fun addSessionToList(sessions: List<Session>) {
        this.sessions.clear()
        this.sessions.addAll(sessions)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return SessionViewHolder(
            ItemSessionBinding.inflate(LayoutInflater.from(parent.context),parent,false),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SessionViewHolder -> {
                holder.bind(sessions[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return sessions.size
    }

    class SessionViewHolder(private val binding: ItemSessionBinding, private val interaction: Interaction?) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: Session) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            binding.sessionTitleTv.text = item.title
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Session)
    }
}
