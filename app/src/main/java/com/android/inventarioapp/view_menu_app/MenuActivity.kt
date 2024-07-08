package com.android.inventarioapp.view_menu_app

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.android.inventarioapp.MainActivity2
import com.android.inventarioapp.R
import com.android.inventarioapp.view_add_sales.AddSalesActivity
import com.android.inventarioapp.view_add_shirt.AddShirtActivity
import com.android.inventarioapp.view_delete_shirt.DeleteShirtActivity
import com.android.inventarioapp.view_sales.ViewSalesActivity
import com.android.inventarioapp.view_add_data.AddDataActivity
import com.android.inventarioapp.view_search_shirts.SearchShirtActivity

class MenuActivity : AppCompatActivity() {
    private lateinit var btnAddShirt: CardView
    private lateinit var btnViewShirt: CardView
    private lateinit var btnRemoveShirt: CardView
    private lateinit var btnViewSale: CardView
    private lateinit var btnAddSale: CardView
    private lateinit var btnAddExtraData: CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        initComponents()
        initListeners()
        requestPermission()
    }

    private fun initComponents() {
        btnAddShirt = findViewById(R.id.btnAddShirt)
        btnViewShirt = findViewById(R.id.btnViewShirt)
        btnRemoveShirt = findViewById(R.id.btnDeleteShirt)
        btnViewSale = findViewById(R.id.btnViewSales)
        btnAddSale = findViewById(R.id.btnAddSales)
        btnAddExtraData = findViewById(R.id.btnAddExtraData)
    }

    private fun initListeners() {
        btnAddShirt.setOnClickListener { navigateToAddShirtActivity() }
        btnViewShirt.setOnClickListener { navigateToViewShirtActivity() }
        btnRemoveShirt.setOnClickListener { navigateToDeleteActivity() }
        btnAddSale.setOnClickListener { navigateToAddSalesActivity() }
        btnViewSale.setOnClickListener { navigateToViewSalesActivity() }
        btnAddExtraData.setOnClickListener { navigateToAddExtraData() }
    }

    private fun navigateToAddShirtActivity() {
        val intent = Intent(this, AddShirtActivity::class.java)
        startActivity(intent)

    }

    private fun navigateToViewShirtActivity() {
        val intent = Intent(this, SearchShirtActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToDeleteActivity() {
        val intent = Intent(this, DeleteShirtActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToAddSalesActivity() {
        val intent = Intent(this, AddSalesActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToViewSalesActivity() {
        val intent = Intent(this, ViewSalesActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToAddExtraData() {
        val intent = Intent(this, AddDataActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToExit() {
        finish()
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                val result = isGranted
            }
        }

    private fun requestPermissionHandler(permission: String) {
        when {
            ContextCompat.checkSelfPermission(this, permission )== PackageManager.PERMISSION_GRANTED -> {}

            shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
            }
            else -> {
                requestPermissionLauncher.launch(
                    permission
                )
            }
        }
    }

    private fun requestPermission() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            android.Manifest.permission.READ_MEDIA_IMAGES
        else
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        requestPermissionHandler(permission)
    }
}