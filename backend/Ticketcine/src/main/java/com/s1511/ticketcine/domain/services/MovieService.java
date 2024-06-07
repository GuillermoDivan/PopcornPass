package com.s1511.ticketcine.domain.services;

import org.springframework.http.ResponseEntity;

import com.s1511.ticketcine.application.dto.movie.CreateDtoMovie;
import com.s1511.ticketcine.application.dto.movie.ReadDtoMovie;
import com.s1511.ticketcine.application.dto.movie.ReadMovieApiData;
import com.s1511.ticketcine.domain.entities.Movie;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MovieService {

    ResponseEntity<?> saveLastestMovies();
    ReadDtoMovie getMovieById(String id);
    List<ReadDtoMovie> getActiveMovieList();
    ReadDtoMovie getMovieByTitle(String title);
    List<ReadDtoMovie> findByReleaseDate(LocalDate time);
   // List<ReadDtoMovie> getMovieByGenre(String gendre);
    List<ReadDtoMovie> getMovieByAge(Boolean agePlus18);
    List<ReadDtoMovie> getMovieByThreeD(Boolean threeD);
    List<String> assignGenre(List<Integer> genre);
    List<Movie> findBySubtitleAndActive(Boolean subtitle);
    Double findAvgRateByMovieId(String movieId);
    String getRandomMovieId(Integer i);
    String getRandomMovieId2();//metodo para las primeras funciones
    void outdateMovie();
}
