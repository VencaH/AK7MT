package com.example.timetrack.api

import com.example.timetrack.domain.CommentDataDomain
import com.example.timetrack.domain.ListDataDomain
import com.example.timetrack.domain.ListDataNetwork
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
interface TrelloApi {

    companion object{
       const val BOARDS_ENDPOINT ="boards/{board}/cards?"
        const val LISTS_ENDPOINT = "lists/{id}"
        const val ADD_COMMENT_ENDPOINT = "cards/{id}/actions/comments"

    }

    @GET(BOARDS_ENDPOINT)
     suspend fun getTaskInfo (
        @Path("board") board: String,
        @Query("key") apikey: String,
        @Query("token") token: String,
    ): Response<List<TaskDataNetwork>>

    @GET(LISTS_ENDPOINT)
    suspend fun getListInfo (
        @Path("id") idlist: String,
        @Query("key") apikey: String,
        @Query("token") token: String,
    ): Response<ListDataNetwork>

@POST(ADD_COMMENT_ENDPOINT)
suspend fun postComment(
    @Path("id") idCard: String,
    @Query("text") comment: String,
    @Query("key") apikey: String,
    @Query("token") token: String,
    @Body body: Any = Object()
): Response<CommentDataDomain>




}