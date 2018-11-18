package com.example.hoale.tikitest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private var mAdapter: CategoryAdapter? = null
    private lateinit var categories: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        categories = savedInstanceState?.getStringArrayList("categories") ?: ArrayList()

        mAdapter = CategoryAdapter(categories)
        main_rvCategory?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        main_rvCategory?.adapter = mAdapter

        if (categories.isEmpty())
            loadCategory()

        main_btnReload?.setOnClickListener {
            loadCategory()
        }
        mAdapter?.setOnItemClickListener(object : CategoryAdapter.OnItemClicktener {
            override fun onItemClick(data: Any, position: Int) {
                showToast(data as? String)
            }

        })
    }

    private fun loadCategory() {
        Log.d("hoale", "load api")
        main_loading?.visibility = View.VISIBLE
        AppManager.getUrl("https://gist.githubusercontent.com/talenguyen/38b790795722e7d7b1b5db051c5786e5/raw/63380022f5f0c9a100f51a1e30887ca494c3326e/keywords.json",
            object : AppManager.IResponseResult {
                override fun onSuccess(strJson: String?) {
                    main_loading?.visibility = View.GONE
                    try {
                        val root = JSONArray(strJson)
                        val list = ArrayList<String>()
                        for (i in 0 until root.length()) {
                            list.add(root[i].toString())
                        }
                        categories = list
                        mAdapter?.setData(list)
                    } catch (e: Exception) {
                        main_loading?.visibility = View.GONE
                        showToast("Lỗi dữ liệu")
                    }

                }
            })
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putStringArrayList("categories", categories)
        super.onSaveInstanceState(outState)
    }
}
