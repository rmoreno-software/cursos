import 'package:animate_do/animate_do.dart';
import 'package:cinemapedia/config/helpers/human_formats.dart';
import 'package:cinemapedia/domain/entities/movie.dart';
import 'package:flutter/material.dart';

class MoviesHorizontalListview extends StatelessWidget {

  final List<Movie> movies;
  final String? title;
  final String? subtitle;
  final VoidCallback? loadNextPage;
  
  const MoviesHorizontalListview({
    super.key,
    required this.movies,
    this.title,
    this.subtitle,
    this.loadNextPage
  });

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      height: 350,
      child: Column(
        children: [
          if( title != null || subtitle != null)
          _Title(title: title, subtitle: subtitle),
          Expanded(
            child: ListView.builder(
              scrollDirection: Axis.horizontal,
              itemCount: movies.length,
              physics: const BouncingScrollPhysics(),
              itemBuilder: (context, index) {
                return _Slide(movie: movies[index]);
              },
            )
          )
        ],
      ),
    );
  }
}

class _Title extends StatelessWidget {

  final String? title;
  final String? subtitle;

  const _Title({
    this.title,
    this.subtitle
  });

  @override
  Widget build(BuildContext context) {

    final titleStyle = Theme.of(context).textTheme.titleLarge;

    return Container(
      padding: const EdgeInsets.only(top: 10),
      margin: const EdgeInsets.symmetric(horizontal: 10),
      child: Row(
        children: [
          if( title != null)
          Text(title!, style: titleStyle),

          const Spacer(),

          if( subtitle != null)
          FilledButton.tonal(
            style: const ButtonStyle( visualDensity: VisualDensity.compact),
            child: Text(subtitle!),
            onPressed: () {},
          ),

        ]
      ),
    );
  }
}

class _Slide extends StatelessWidget {

  final Movie movie;
  
  const _Slide({required this.movie});

  @override
  Widget build(BuildContext context) {

    final textStyles = Theme.of(context).textTheme;

    return Container(
      margin: EdgeInsets.symmetric(horizontal: 8),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [

          //* IMAGEN
          SizedBox(
            width: 150,
            child: ClipRRect(
              borderRadius: BorderRadius.circular(20),
              child: Image.network(
                movie.posterPath,
                fit: BoxFit.cover,
                width: 150,
                loadingBuilder: (context, child, loadingProgress) {
                  if (loadingProgress != null) {
                    return Padding(
                      padding: const EdgeInsets.all(8.0),
                      child: Center(child: CircularProgressIndicator(strokeWidth: 2)),
                    );
                  }
                  return FadeIn(child: child);
                },
              ),
            ),
          ),

          SizedBox(height: 5),

          //* Title
          SizedBox(
            width: 150,
            child: Text(
                movie.title,
                maxLines: 2,
                style: textStyles.titleSmall,
            ),
          ),

          //* Ratings
          SizedBox(
            width: 150,
            child: Row(
              children: [
                Icon(Icons.star_half_outlined, color: Colors.yellow.shade800,),
                const SizedBox(width: 3),
                Text('${movie.voteAverage}', style: textStyles.bodyMedium?.copyWith(color: Colors.yellow.shade800),),
                const Spacer(),
                Text(HumanFormats.number(movie.popularity), style: textStyles.bodySmall,)
              ],
            ),
          ),

        ],
      ),
    );
  }
}