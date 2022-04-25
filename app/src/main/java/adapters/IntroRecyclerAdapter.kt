package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sivva.daredevil.R
import models.IntroModel

class IntroRecyclerAdapter :
    RecyclerView.Adapter<IntroRecyclerAdapter.InstallmentViewHolder>() {

    private var records: List<IntroModel>

    init {
        records = listOf()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstallmentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.intro_layout, parent, false)
        return InstallmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: InstallmentViewHolder, position: Int) {
        holder.installmentTv.text =
            records[position].content
        holder.title.text =
            records[position].title
        holder.image.load(records[position].drawable)
    }

    override fun getItemCount(): Int {
        return records.size
    }

    fun updateList(records: List<IntroModel>) {
        this.records = records
        this.notifyDataSetChanged()
    }

    class InstallmentViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val installmentTv: TextView = v.findViewById(R.id.message)
        val title: TextView = v.findViewById(R.id.title)
        val image: ImageView = v.findViewById(R.id.image)
    }
}