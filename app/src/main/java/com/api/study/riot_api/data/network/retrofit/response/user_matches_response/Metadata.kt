package com.api.study.riot_api.data.network.retrofit.response.user_matches_response

data class Metadata(
    val dataVersion: String,
    val matchId: String,
    val participants: List<String>
)