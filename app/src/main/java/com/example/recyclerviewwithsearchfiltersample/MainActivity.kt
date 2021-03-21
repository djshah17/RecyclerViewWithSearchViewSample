package com.example.recyclerviewwithsearchfiltersample

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewwithsearchfiltersample.adapter.EmployeesAdapter
import com.example.recyclerviewwithsearchfiltersample.models.Employee
import com.example.recyclerviewwithsearchfiltersample.utils.ApiClient
import com.example.recyclerviewwithsearchfiltersample.utils.Utility.hideProgressBar
import com.example.recyclerviewwithsearchfiltersample.utils.Utility.isInternetAvailable
import com.example.recyclerviewwithsearchfiltersample.utils.Utility.showProgressBar
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private var listEmployees: MutableList<Employee> = mutableListOf<Employee>()
    private var employeesAdapter: EmployeesAdapter? = null
    private var mSearchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listEmployees = mutableListOf()

        recycler_main.layoutManager = LinearLayoutManager(this@MainActivity)
        employeesAdapter = EmployeesAdapter(
            this,
            listEmployees
        )
        recycler_main.adapter = employeesAdapter

        if (isInternetAvailable()) {
            getEmployeesData()
        }

    }

    private fun getEmployeesData() {

        showProgressBar()

        ApiClient.apiService.getUsers().enqueue(object : Callback<MutableList<Employee>> {
            override fun onFailure(call: Call<MutableList<Employee>>, t: Throwable) {
                hideProgressBar()
            }

            override fun onResponse(
                call: Call<MutableList<Employee>>,
                response: Response<MutableList<Employee>>
            ) {
                hideProgressBar()
                val employeesResponse = response.body()
                listEmployees.clear()
                employeesResponse?.let { listEmployees.addAll(it) }
                employeesAdapter?.notifyDataSetChanged()
            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val mSearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        mSearchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        mSearchView?.setSearchableInfo(mSearchManager.getSearchableInfo(componentName))
        mSearchView?.maxWidth = Int.MAX_VALUE

        mSearchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                employeesAdapter?.filter?.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                employeesAdapter?.filter?.filter(query)
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        return if (id == R.id.action_search) {
            true
        } else super.onOptionsItemSelected(item)

    }

}
