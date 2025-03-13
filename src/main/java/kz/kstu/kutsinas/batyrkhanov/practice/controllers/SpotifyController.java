package kz.kstu.kutsinas.batyrkhanov.practice.controllers;

import kz.kstu.kutsinas.batyrkhanov.practice.services.SpotifyService;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.net.URI;

@RestController
public class SpotifyController {
    private final SpotifyService spotifyService;
    @Autowired
    public SpotifyController(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }
    @GetMapping("/authorize")
    public URI authorize(){
        return spotifyService.getAuthorizationUri();
    }

    @GetMapping("/callback")
    public String callback(@RequestParam String code){
        try{
            spotifyService.authorize(code);
            return "Authorization successful!";
        }catch (IOException| SpotifyWebApiException| ParseException e){
            return "Authorization failed: " + e.getMessage();
        }
    }
}
