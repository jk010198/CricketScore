package com.interactive.sportz.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.interactive.sportz.databinding.PlayerRowLayoutBinding
import com.interactive.sportz.models.TeamPlayerDetail


class TeamPlayerDataAdpter(
    private var playerList: ArrayList<TeamPlayerDetail>,
    private val context: Context,
) :
    RecyclerView.Adapter<TeamPlayerDataAdpter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            PlayerRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.apply {
            with(playerList[position]) {

                binding.tvPlayerName.text = "Player Name :- ${this.Name}"
                binding.tvPlayerBattingRuns.text = "Player Runs :- ${this.Batting.Runs}"
                binding.tvPlayerBowlingAverage.text = "Bowling Avg :- ${this.Bowling.Average}"

                if (this.Iskeeper) {
                    binding.tvPlayerName.text = "${binding.tvPlayerName.text} (Wicket keeper)"
                }

                if (this.Iscaptain) {
                    binding.tvPlayerName.text = "${binding.tvPlayerName.text} (Captain)"
                }

                binding.cardview.setOnClickListener({
                    Toast.makeText(context, "${this.Name}", Toast.LENGTH_LONG).show()
                    MaterialAlertDialogBuilder(context)
                        .setTitle("Players Batting And Bowling Style")
                        .setMessage("Batting Style\nStyle :- ${this.Batting.Style} \nStrike Rate :- ${this.Batting.Strikerate} \n\n" +
                                "Bowling Style\nStyle :- ${this.Bowling.Style} \nWickets :- ${this.Bowling.Wickets}")
                        /*.setPositiveButton("GOT IT"
                        ) { dialogInterface, i -> }
                        .setNegativeButton("CANCEL"
                        ) { dialogInterface, i -> }*/
                        .show()
                })

                /*val gson = Gson()
                val jsonString = gson.toJson(this)
                val jsonObjectMain = JSONObject(jsonString)

                val teamAway =
                    jsonObjectMain.getJSONObject("Matchdetail").optString("Team_Away").toString()
                val teamHome =
                    jsonObjectMain.getJSONObject("Matchdetail").optString("Team_Home").toString()

                val jsonBatsmanArray = jsonObjectMain.optJSONArray("Innings")

                for (i in 0 until jsonBatsmanArray.length()) {
                    val jsonObject = jsonBatsmanArray.getJSONObject(i)
                    val batingTeamNo = jsonObject.optString("Battingteam").toString()
                    Log.d("allData12", "batting Team no :- $batingTeamNo ")

                    val jsonBatsmanDataArray = jsonObject.optJSONArray("Batsmen")
                    for (i in 0 until jsonBatsmanDataArray.length()) {
                        val jsonDataObject = jsonBatsmanDataArray.getJSONObject(i)
                        val batsmanId = jsonDataObject.optString("Batsman").toString()
                        //Log.d("allData12", "batsman_id :- $batsmanId ")

                        val teamNo = if (batingTeamNo == teamAway) {
                            teamAway
                        } else {
                            teamHome
                        }

                        val jsonArrayTeamsData =
                            jsonObjectMain.getJSONObject("Teams").getJSONObject(teamNo)
                                .optJSONObject("Players")
                        Log.d("allData12",
                            "batsman_id_in_teams :- ${
                                jsonArrayTeamsData.getJSONObject(batsmanId).optString("Name_Full")
                            } ")
                        binding.tvPlayerName.text =
                            jsonArrayTeamsData.getJSONObject(batsmanId).optString("Name_Full")

                    }
                }*/

            }
        }

    }

    override fun getItemCount(): Int {
        return playerList.size
    }

    class MyViewHolder(val binding: PlayerRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setData(userList: ArrayList<TeamPlayerDetail>) {
        this.playerList = userList
        notifyDataSetChanged()
    }
}