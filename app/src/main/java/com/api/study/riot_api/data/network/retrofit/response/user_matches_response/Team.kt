package com.api.study.riot_api.data.network.retrofit.response.user_matches_response

data class Team(
    val bans: List<Any>,
    val objectives: Objectives,
    val teamId: Int,
    val win: Boolean
)