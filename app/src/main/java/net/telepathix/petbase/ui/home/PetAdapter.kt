package net.telepathix.petbase.ui.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.pet_list_item.view.*
import net.telepathix.petbase.model.Pet
import com.squareup.picasso.Picasso
import net.telepathix.petbase.R
import net.telepathix.petbase.ui.info.EXTRA_PET_INFO_URL
import net.telepathix.petbase.ui.info.EXTRA_PET_TITLE
import net.telepathix.petbase.ui.info.PetInfoActivity

const val targetDimension = 500

class PetAdapter(val context: Context) : RecyclerView.Adapter<PetViewHolder>() {

    init {
        setHasStableIds(true)
    }

    var items: List<Pet> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        return PetViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.pet_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val petInfo = items.get(position)
        Picasso.get().load(petInfo.imageUrl)
            .resize(targetDimension, targetDimension)
            .onlyScaleDown()
            .centerCrop()
            .error(R.drawable.error)
            .into(holder.petView.petImage)
        holder.petView.petTitle.text = petInfo.title
        holder.petView.setOnClickListener {
            val intent = Intent(context, PetInfoActivity::class.java)
            intent.putExtra(EXTRA_PET_TITLE, petInfo.title)
            intent.putExtra(EXTRA_PET_INFO_URL, petInfo.contentUrl)
            context.startActivity(intent)
        }
    }

    override fun getItemId(position: Int): Long {
        return items.get(position).hashCode().toLong()
    }

    override fun onViewRecycled(holder: PetViewHolder) {
        super.onViewRecycled(holder)
        holder.cleanup()
    }
}

class PetViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val petView = view

    fun cleanup() {
        Picasso.get().cancelRequest(petView.petImage)
        petView.petImage.setImageDrawable(null)
    }
}
