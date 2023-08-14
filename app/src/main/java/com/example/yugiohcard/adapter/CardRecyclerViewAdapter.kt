package com.example.yugiohcard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yugiohcard.R
import com.example.yugiohcard.databinding.ItemCardListBinding
import com.example.yugiohcard.domain.entitiy.Card


class CardRecyclerViewAdapter : RecyclerView.Adapter<CardRecyclerViewAdapter.ViewHolder>(),
    Filterable {

    private lateinit var filteredItems: List<Card>

    private val differCallback = object : DiffUtil.ItemCallback<Card>() {
        override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean =
            oldItem == newItem
    }

    private val cardsDiffer = AsyncListDiffer(this, differCallback)

    fun submitList(cards: List<Card>) =
        cardsDiffer.submitList(cards)

    fun setFilteredItems() {
        filteredItems = cardsDiffer.currentList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemCardListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        val card = cardsDiffer.currentList[position]
        Glide.with(binding.imgViewCard).load(card.imageUrl)
            .placeholder(R.drawable.img_placeholder).into(binding.imgViewCard)
        binding.txtViewDesc.text = card.desc
        binding.txtViewName.text = card.name
        when (card.attribute) {
            "DARK" -> binding.imgViewAttribute.setImageResource(R.drawable.ic_attribute_dark)
            "DIVINE" -> binding.imgViewAttribute.setImageResource(R.drawable.ic_attribute_divine)
            "EARTH" -> binding.imgViewAttribute.setImageResource(R.drawable.ic_attribute_earth)
            "FIRE" -> binding.imgViewAttribute.setImageResource(R.drawable.ic_attribute_fire)
            "LIGHT" -> binding.imgViewAttribute.setImageResource(R.drawable.ic_attribute_light)
            "WATER" -> binding.imgViewAttribute.setImageResource(R.drawable.ic_attribute_water)
            "WIND" -> binding.imgViewAttribute.setImageResource(R.drawable.ic_attribute_wind)
            else -> binding.imgViewAttribute.setImageResource(R.drawable.ic_attribute_default)
        }
        binding.txtViewAttribute.text = card.attribute
        ("LEVEL " + card.level.toString()).also { binding.txtViewLevel.text = it }
        ("ATK " + card.attack.toString()).also { binding.txtViewAttack.text = it }
        ("DEF " + card.defence.toString()).also { binding.txtViewDefence.text = it }
    }

    override fun getItemCount(): Int =
        cardsDiffer.currentList.size

    inner class ViewHolder(val binding: ItemCardListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getFilter(): Filter =
        object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults =
                FilterResults().apply {
                    values = constraint?.let {
                        filteredItems.filter {
                            it.name.contains(constraint, true)
                        }
                    }
                }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                results?.let { if (it.values != null) submitList(it.values as List<Card>) }
            }
        }
}