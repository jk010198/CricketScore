package com.interactive.sportz

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.interactive.sportz.adapters.TeamPlayerDataAdpter
import com.interactive.sportz.databinding.ActivityTeamPlayersDetailsBinding
import com.interactive.sportz.models.Batting
import com.interactive.sportz.models.Bowling
import com.interactive.sportz.models.CricketModel
import com.interactive.sportz.models.TeamPlayerDetail
import com.interactive.sportz.viewmodel.MatchDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class TeamPlayersDetails : AppCompatActivity() {

    private lateinit var binding: ActivityTeamPlayersDetailsBinding
    lateinit var viewModel: MatchDetailsViewModel
    lateinit var arrayList: ArrayList<TeamPlayerDetail>
    lateinit var firstTeamArrayList: ArrayList<TeamPlayerDetail>
    lateinit var secondTeamArrayList: ArrayList<TeamPlayerDetail>
    var teamA: String = ""
    var teamB: String = ""
    var teamAway: String = ""
    var teamHome: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_team_players_details)
        arrayList = ArrayList<TeamPlayerDetail>()
        firstTeamArrayList = ArrayList<TeamPlayerDetail>()
        secondTeamArrayList = ArrayList<TeamPlayerDetail>()
        binding.tvNoData.visibility = View.GONE

        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(this@TeamPlayersDetails)
            setHasFixedSize(true)
        }

        teamA = intent.getStringExtra("teamA").toString()
        teamB = intent.getStringExtra("teamB").toString()

        viewModel = ViewModelProvider(this).get(MatchDetailsViewModel::class.java)

        viewModel.mdata.observe(this, Observer {
            arrayList.clear()
            var size = 1
            try {
                val gson = Gson()
                val jsonString = gson.toJson(it)
                val jsonObjectMain = JSONObject(jsonString)

                teamAway =
                    jsonObjectMain.getJSONObject("Matchdetail").optString("Team_Away").toString()
                teamHome =
                    jsonObjectMain.getJSONObject("Matchdetail").optString("Team_Home").toString()

                val jsonBatsmanArray = jsonObjectMain.optJSONArray("Innings")
                for (i in 0 until jsonBatsmanArray.length()) {
                    val jsonObject = jsonBatsmanArray.getJSONObject(i)
                    val batingTeamNo = jsonObject.optString("Battingteam").toString()
                    Log.d("allData12", "details batting Team no :- $batingTeamNo ")
                    val jsonBatsmanDataArray = jsonObject.optJSONArray("Batsmen")
                    for (i in 0 until jsonBatsmanDataArray.length()) {
                        val jsonDataObject = jsonBatsmanDataArray.getJSONObject(i)
                        val batsmanId = jsonDataObject.optString("Batsman").toString()

                        val teamNo = if (batingTeamNo == teamAway) {
                            teamAway
                        } else {
                            teamHome
                        }

                        val jsonArrayTeamsData =
                            jsonObjectMain.getJSONObject("Teams").getJSONObject(teamNo)
                                .optJSONObject("Players")

                        if (jsonArrayTeamsData != null) {
                            if (!jsonArrayTeamsData.getJSONObject(batsmanId).optString("Name_Full")
                                    .isNullOrEmpty()
                            ) {
                                Log.d("allData22", "details  batsman_id_in_teams :- ${
                                    jsonArrayTeamsData.getJSONObject(batsmanId)
                                        .optString("Name_Full")
                                } keeper ${
                                    jsonArrayTeamsData.getJSONObject(batsmanId)
                                        .optBoolean("Iskeeper")
                                }" + " captain  ${
                                    jsonArrayTeamsData.getJSONObject(batsmanId)
                                        .optBoolean("Iscaptain")
                                }")
                                size++
                                arrayList.add(TeamPlayerDetail(teamAway,
                                    teamHome,
                                    teamNo,
                                    jsonArrayTeamsData.getJSONObject(batsmanId)
                                        .optString("Name_Full"),
                                    jsonArrayTeamsData.getJSONObject(batsmanId)
                                        .optBoolean("Iskeeper"),
                                    jsonArrayTeamsData.getJSONObject(batsmanId)
                                        .optBoolean("Iscaptain"),
                                    Batting(jsonArrayTeamsData.getJSONObject(batsmanId)
                                        .getJSONObject("Batting").optString("Average"),
                                        jsonArrayTeamsData.getJSONObject(batsmanId)
                                            .getJSONObject("Batting").optString("Runs"),
                                        jsonArrayTeamsData.getJSONObject(batsmanId)
                                            .getJSONObject("Batting").optString("Strikerate"),
                                        jsonArrayTeamsData.getJSONObject(batsmanId)
                                            .getJSONObject("Batting").optString("Style")),
                                    Bowling(jsonArrayTeamsData.getJSONObject(batsmanId)
                                        .getJSONObject("Bowling").optString("Average"),
                                        jsonArrayTeamsData.getJSONObject(batsmanId)
                                            .getJSONObject("Bowling").optString("Economyrate"),
                                        jsonArrayTeamsData.getJSONObject(batsmanId)
                                            .getJSONObject("Bowling").optString("Style"),
                                        jsonArrayTeamsData.getJSONObject(batsmanId)
                                            .getJSONObject("Bowling").optString("Wickets"))))
                            } else {
                            }
                        } else {
                        }
                    }
                }

                binding.recyclerview.adapter = TeamPlayerDataAdpter(arrayList, this)

            } catch (e: Exception) {
                binding.tvNoData.visibility = View.VISIBLE
                Log.d("allData12", e.toString());
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menus, menu)
        menu?.apply {
            if (!teamA.isEmpty()) {
                findItem(R.id.teamFirst).setTitle(teamA)
            } else {
                findItem(R.id.allPlayers).setVisible(false)
                findItem(R.id.teamFirst).setVisible(false)
                findItem(R.id.teamSecond).setVisible(false)
            }
            if (!teamB.isEmpty()) {
                findItem(R.id.teamSecond).setTitle(teamB)
            }

        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.allPlayers -> {
                binding.recyclerview.adapter = TeamPlayerDataAdpter(arrayList, this)
            }
            R.id.teamFirst -> {
                arrayList.filterTo(firstTeamArrayList, { it.Team_No == teamAway })
                binding.recyclerview.adapter = TeamPlayerDataAdpter(firstTeamArrayList, this)

            }
            R.id.teamSecond -> {
                arrayList.filterTo(secondTeamArrayList, { it.Team_No == teamHome })
                binding.recyclerview.adapter = TeamPlayerDataAdpter(secondTeamArrayList, this)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}