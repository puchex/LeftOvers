package com.example.leftovers

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.leftovers.databinding.ActivityGiveBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.sql.Date
import java.sql.Time
import java.util.*


class GiveActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener{
    private lateinit var binding : ActivityGiveBinding
    private lateinit var map : GoogleMap
    private lateinit var mAuth: FirebaseAuth
    private  var database : DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGiveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            if(checkedId == R.id.different_location){
                mapFragment.view?.visibility = View.VISIBLE
            }
            else{
                mapFragment.isVisible
                mapFragment.view?.visibility = View.INVISIBLE
            }
        }
        binding.donateButton.setOnClickListener {
            addGiveAway()
        }
    }

    private  fun addGiveAway(){
        val uid = mAuth.currentUser?.uid.toString()
        val info = binding.infoText.editText?.text.toString()
        val _quantity = binding.quantity.editText?.text.toString()
        if(_quantity.isEmpty()){
            binding.quantity.editText?.error = "Quantity required!"
            return
        }
        val quantity = _quantity.toInt()
        val location = getLocation()
        val time = Calendar.getInstance().time
        val giveaway = GiveAway(uid,info,quantity,location, time)
        val ref = FirebaseDatabase.getInstance().getReference("GiveAways")

        ref.child("newId").get().addOnSuccessListener {
            val id = it.value.toString().toInt()
            ref.child("newId").setValue(id+1)
            ref.child(id.toString()).setValue(giveaway)
            Toast.makeText(applicationContext,"Wow! Your GiveAway ${id}is Live, Lets see who takes it",Toast.LENGTH_LONG).show()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getLocation(){
        
    }

    override fun onMapReady(p0: GoogleMap?) {
        map = p0!!
        val mdpk  = LatLng(17.385044, 78.486671)
        map.addCircle(CircleOptions().center(mdpk).radius(12.3))
        map.addMarker(MarkerOptions().position(mdpk).title("Mandapaka"))
        map.moveCamera(CameraUpdateFactory.newLatLng(mdpk))
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        TODO("Not yet implemented")
    }

}