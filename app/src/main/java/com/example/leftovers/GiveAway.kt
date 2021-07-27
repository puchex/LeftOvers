package com.example.leftovers

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import java.sql.Date
import java.sql.Time

class GiveAway (public val giver : String,
                public val info : String,
                public val quantity : Number,
                public val location : LatLng,
                public val startTime : java.util.Date
                )