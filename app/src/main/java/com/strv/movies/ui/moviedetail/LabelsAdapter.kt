package com.strv.movies.ui.moviedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.strv.movies.databinding.MovieDetailLabelItemBinding

/*
    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recycler_labels"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>
 */

// recyclerLabels.layoutManager = LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)
// recyclerLabels.adapter = LabelsAdapter(viewState.movie?.genres?.map { it.name } ?: emptyList())

class LabelsAdapter(private val dataSet: List<String>) :
    RecyclerView.Adapter<LabelsAdapter.ViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(val viewBinding: MovieDetailLabelItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        return ViewHolder(MovieDetailLabelItemBinding.inflate(LayoutInflater.from(viewGroup.context)))
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.viewBinding.textTitle.text = dataSet[position]
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}