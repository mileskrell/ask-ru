package tech.askru.questiondetails

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tech.askru.R
import tech.askru.api.GetAdviceByIdResponse

class AdviceListAdapter : RecyclerView.Adapter<AdviceListAdapter.AdviceCardViewHolder>() {

    private var items = listOf<GetAdviceByIdResponse>()

    fun loadNewData(newItems: List<GetAdviceByIdResponse>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdviceCardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.advice_card, parent, false)
        return AdviceCardViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: AdviceCardViewHolder, position: Int) {
        holder.adviceBodyTextView.text = items[position].advice!!.body
        holder.adviceUpvoteButton.setOnClickListener {
            castVote(items[position].advice!!._id, true, holder)
        }
        holder.adviceDownvoteButton.setOnClickListener {
            castVote(items[position].advice!!._id, false, holder)
        }
    }

    class AdviceCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val adviceBodyTextView: TextView = itemView.findViewById(R.id.advice_card_text_view_body)
        val adviceUpvoteButton: ImageView = itemView.findViewById(R.id.advice_card_button_upvote)
        val adviceDownvoteButton: ImageView = itemView.findViewById(R.id.advice_card_button_downvote)
    }

    private fun castVote(adviceId: String?, up: Boolean, holder: AdviceCardViewHolder) {
        if (up) {
            holder.adviceUpvoteButton.setColorFilter(Color.parseColor("#ED5564"))
            holder.adviceDownvoteButton.colorFilter = null
        } else {
            holder.adviceUpvoteButton.colorFilter = null
            holder.adviceDownvoteButton.setColorFilter(Color.parseColor("#6455ED"))
        }
    }
}
