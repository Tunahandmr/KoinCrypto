package com.tunahan.koincrypto.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tunahan.koincrypto.databinding.RecyclerRowBinding
import com.tunahan.koincrypto.model.Crypto

class CryptoAdapter(private val cryptoList:ArrayList<Crypto>, private val listener: Listener):
    RecyclerView.Adapter<CryptoAdapter.RowHolder>() {

    interface Listener {
        fun onItemClick(cryptoModel: Crypto)
    }
    private val colors: Array<String> = arrayOf("#13bd27","#29c1e1","#b129e1","#d3df13","#f6bd0c","#a1fb93","#0d9de3","#ffe48f")

    class RowHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val itemBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return cryptoList.count()
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {

        holder.itemView.setOnClickListener {
            listener.onItemClick(cryptoList.get(position))
        }
        holder.itemView.setBackgroundColor(Color.parseColor(colors[position % 8]))
        holder.binding.cryptoNameText.text = cryptoList.get(position).currency
        holder.binding.cryptoPriceText.text = cryptoList.get(position).price
    }
}