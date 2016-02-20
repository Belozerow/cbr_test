package com.belozerov.cbrrate.api;

import com.belozerov.cbrrate.api.model.RateAnswer;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created: Belozerov
 * Date: 20.02.2016
 */
public interface CBRApi {
    @GET("scripts/XML_daily.asp")
    Observable<RateAnswer> getRate(@Query("date_req") String date);
}
