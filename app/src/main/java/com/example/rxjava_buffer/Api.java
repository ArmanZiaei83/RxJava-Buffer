package com.example.rxjava_buffer;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface Api {

    @GET("comments")
    Observable<List<Comment>> getComments();

    @GET("posts")
    Observable<Post> getPost();
}
