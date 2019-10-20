package tech.askru.main.pages.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tech.askru.R
import tech.askru.api.Question

class SearchResultsAdapter : RecyclerView.Adapter<SearchResultsAdapter.SearchResultViewHolder>() {

    private var items = listOf<Question>()

    fun loadNewData(newItems: List<Question>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_result, parent, false)
        return SearchResultViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.questionTitleTextView.text = items[position].title
        holder.questionBodyTextView.text = items[position].body
    }

    class SearchResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionTitleTextView: TextView = itemView.findViewById(R.id.text_view_result_title)
        val questionBodyTextView: TextView = itemView.findViewById(R.id.text_view_result_body)
    }
}
