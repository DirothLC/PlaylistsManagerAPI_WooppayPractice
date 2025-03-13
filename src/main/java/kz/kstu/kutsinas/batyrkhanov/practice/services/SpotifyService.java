package kz.kstu.kutsinas.batyrkhanov.practice.services;

import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.io.IOException;
import java.net.URI;

@Service
public class SpotifyService {
    private final SpotifyApi spotifyApi;

    public SpotifyService(SpotifyApi spotifyApi){
        this.spotifyApi = spotifyApi;
    }

    public URI getAuthorizationUri(){
        AuthorizationCodeUriRequest authorizationCodeUriRequest
                =spotifyApi.authorizationCodeUri()
                .scope("user-read-private,user-read-email")
                .build();
        return authorizationCodeUriRequest.execute();
    }

    public void authorize(String code) throws
            IOException, SpotifyWebApiException, ParseException {
        AuthorizationCodeCredentials authorizationCodeCredentials
                =spotifyApi.authorizationCode(code).build().execute();
        spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
        spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

    }
}
