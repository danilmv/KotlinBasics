package com.andriod.lesson1

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar
import java.time.Duration
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = setRecyclerView()

        findViewById<Button>(R.id.button).setOnClickListener {
            Toast.makeText(this,
                "hello",
                Toast.LENGTH_SHORT).show()

            adapter.addRecord(Record(value = String.format("hello@ [%s]",
                Calendar.getInstance().time.toString())))
        }
    }

    private fun setRecyclerView(): RecordAdapter {
        val recycler: RecyclerView = findViewById(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this)

        val adapter = RecordAdapter()
        recycler.adapter = adapter

        return adapter
    }

    class RecordAdapter : RecyclerView.Adapter<RecordAdapter.ViewHolder>() {

        private val records = emptyList<Record>().toMutableList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.record_item, parent, false)) {
                Snackbar.make(parent,
                    String.format("record [%d] was deleted", records.removeAt(it).id),
                    LENGTH_SHORT).show()
                notifyItemRemoved(it)
            }
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(records[position])
        }

        override fun getItemCount(): Int {
            return records.size
        }

        fun addRecord(record: Record) {
            records.add(record)
            notifyItemInserted(records.size - 1)
        }

        inner class ViewHolder(itemView: View, private val eventListener: (Int) -> Unit) :
            RecyclerView.ViewHolder(itemView) {
            private val textViewValue = itemView.findViewById<TextView>(R.id.value)

            fun bind(record: Record) {
                textViewValue.text = String.format("%d: %s", record.id, record.value)
                itemView.setOnClickListener {
                    AlertDialog.Builder(itemView.context).apply {
                        setTitle(String.format("delete item [%d]?", record.id))
                        setMessage(String.format("value: [%s]", record.value))
                        setPositiveButton("Yep") { _, _ -> eventListener.invoke(adapterPosition) }
                        setNeutralButton("I don't know") { _, _ ->
                            if (Random.nextInt() % 2 == 0) eventListener.invoke(adapterPosition)
                            else Snackbar.make(itemView,
                                String.format("record [%d] survived", record.id),
                                LENGTH_SHORT).show()
                        }
                        show()
                    }
                }
            }
        }
    }
}