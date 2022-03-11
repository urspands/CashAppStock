package com.raj.stocksapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raj.stocksapp.R
import com.raj.stocksapp.api.Stock
import com.raj.stocksapp.databinding.ItemStockBinding

/**
 * Stock list adapter
 * adapter to list porfolio stocks
 * @property _stocks
 * @constructor Create empty Stock list adapter
 */
class StockListAdapter(private val _stocks: ArrayList<Stock>) :
    RecyclerView.Adapter<StockListAdapter.StockViewHolder>() {

    class StockViewHolder(private val itemStockBinding: ItemStockBinding) :
        RecyclerView.ViewHolder(itemStockBinding.root) {
        fun bind(stock: Stock) {
            itemStockBinding.name.text = stock.name
            itemStockBinding.ticker.text = stock.ticker
            itemStockBinding.quantity.apply {

                text = itemStockBinding.root.context.getString(
                    R.string.stock_quantity,
                    stock.quantity?.toString() ?: "-"
                )
            }
            itemStockBinding.price.text = itemStockBinding.root.context.getString(
                R.string.stock_price,
                "${(stock.current_price_cents / 100)}.${(stock.current_price_cents % 100)}"
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        return StockViewHolder(
            ItemStockBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        holder.bind(_stocks[position])
    }

    override fun getItemCount(): Int {
        return _stocks.size
    }

}