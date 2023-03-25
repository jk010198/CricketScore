package com.interactive.sportz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.interactive.sportz.databinding.ActivityMainBinding
import com.interactive.sportz.models.*
import com.interactive.sportz.viewmodel.MatchDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MatchDetailsViewModel
    var teamA: String = ""
    var teamB: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MatchDetailsViewModel::class.java)

        viewModel.mdata.observe(this, Observer {

            try {
                val gson = Gson()
                val jsonString = gson.toJson(it)
                val jsonObjectMain = JSONObject(jsonString)

                binding.tvTeamName.text = "Series :- ${
                    jsonObjectMain.getJSONObject("Matchdetail").getJSONObject("Series")
                        .optString("Name")
                }"

                val dateTimeVenue = "Date :- ${
                    jsonObjectMain.getJSONObject("Matchdetail").getJSONObject("Match").optString("Date")
                } " +
                        "Time :- ${
                            jsonObjectMain.getJSONObject("Matchdetail").getJSONObject("Match")
                                .optString("Time")
                        }, " +
                        "\n Venue :- ${
                            jsonObjectMain.getJSONObject("Matchdetail").getJSONObject("Venue")
                                .optString("Name")
                        }"
                binding.tvMatchDateTimeAndVenueDetails.text = dateTimeVenue


                val teamAway =
                    jsonObjectMain.getJSONObject("Matchdetail").optString("Team_Away").toString()
                val teamHome =
                    jsonObjectMain.getJSONObject("Matchdetail").optString("Team_Home").toString()

                teamA = jsonObjectMain.getJSONObject("Teams").getJSONObject(teamAway)
                    .optString("Name_Short")

                teamB = jsonObjectMain.getJSONObject("Teams").getJSONObject(teamHome)
                    .optString("Name_Short")
            } catch (e: Exception){

            }

        })

        binding.btnTeamPlayerDetails.setOnClickListener({
            startActivity(Intent(this, TeamPlayersDetails::class.java).putExtra("teamA", teamA)
                .putExtra("teamB", teamB))
        })

    }
}