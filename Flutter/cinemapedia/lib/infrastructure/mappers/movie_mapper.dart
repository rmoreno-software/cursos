import 'package:cinemapedia/domain/entities/movie.dart';
import 'package:cinemapedia/infrastructure/models/moviedb/movie_details_moviedb.dart';
import 'package:cinemapedia/infrastructure/models/moviedb/movie_moviedb.dart';

class MovieMapper {
  static Movie movieDBToEntity(MovieMovieDB moviedb) => Movie(
    adult: moviedb.adult,
    backdropPath:
        moviedb.backdropPath != ''
            ? 'https://image.tmdb.org/t/p/original${moviedb.backdropPath}'
            : 'https://m.media-amazon.com/images/I/61s8vyZLSzL._AC_UF894,1000_QL80_.jpg',
    genreIds: moviedb.genreIds.map((e) => e.toString()).toList(),
    id: moviedb.id,
    originalLanguage: moviedb.originalLanguage,
    originalTitle: moviedb.originalTitle,
    overview: moviedb.overview,
    popularity: moviedb.popularity,
    posterPath:
        moviedb.posterPath != ''
            ? 'https://image.tmdb.org/t/p/original${moviedb.posterPath}'
            : 'no-poster',
    releaseDate: moviedb.releaseDate != null
      ? moviedb.releaseDate!
      : DateTime.now(),
    title: moviedb.title,
    video: moviedb.video,
    voteAverage: moviedb.voteAverage,
    voteCount: moviedb.voteCount,
  );

  static Movie movieDetailsToEntity(MovieDetailsMoviedb moviedbDetails) => Movie(
    adult: moviedbDetails.adult,
    backdropPath:
        moviedbDetails.backdropPath != ''
            ? 'https://image.tmdb.org/t/p/original${moviedbDetails.backdropPath}'
            : 'https://m.media-amazon.com/images/I/61s8vyZLSzL._AC_UF894,1000_QL80_.jpg',
    genreIds: moviedbDetails.genres.map((e) => e.name).toList(),
    id: moviedbDetails.id,
    originalLanguage: moviedbDetails.originalLanguage,
    originalTitle: moviedbDetails.originalTitle,
    overview: moviedbDetails.overview,
    popularity: moviedbDetails.popularity,
    posterPath:
        moviedbDetails.posterPath != ''
            ? 'https://image.tmdb.org/t/p/original${moviedbDetails.posterPath}'
            : 'https://m.media-amazon.com/images/I/61s8vyZLSzL._AC_UF894,1000_QL80_.jpg',
    releaseDate: moviedbDetails.releaseDate,
    title: moviedbDetails.title,
    video: moviedbDetails.video,
    voteAverage: moviedbDetails.voteAverage,
    voteCount: moviedbDetails.voteCount,
  );
}
