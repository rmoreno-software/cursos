import 'package:cinemapedia/presentation/providers/providers.dart';
import 'package:cinemapedia/presentation/widgets/widgets.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

class HomeScreen extends StatelessWidget {
  static const name = 'home-screen';

  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(child: _HomeView()),
      bottomNavigationBar: CustomBottomNavigation(),
    );
  }
}

class _HomeView extends ConsumerStatefulWidget {
  const _HomeView();

  @override
  _HomeViewState createState() => _HomeViewState();
}

class _HomeViewState extends ConsumerState<_HomeView> {
  @override
  void initState() {
    super.initState();
    ref.read(nowPlayingMoviesProvider.notifier).loadNextPage();
    ref.read(popularMoviesProvider.notifier).loadNextPage();
  }

  @override
  Widget build(BuildContext context) {

    final nowPlayingMovies = ref.watch(nowPlayingMoviesProvider);
    final slideShowMovies = ref.watch(moviesSlideshowProvider);
    final popularMovies = ref.watch(popularMoviesProvider);

    return CustomScrollView(
      slivers: [
        const SliverAppBar(
          floating: true,
          flexibleSpace: FlexibleSpaceBar(
            title: CustomAppbar(),
          ),
        ),
        SliverList(
          delegate: SliverChildBuilderDelegate((context, index) {
            return Column(
              children: [
                //

                MoviesSlideshow(movies: slideShowMovies),

                MoviesHorizontalListview(
                  movies: nowPlayingMovies,
                  title: 'En cines',
                  subtitle: 'Miércoles 26',
                  loadNextPage:
                      () =>
                          ref
                              .read(nowPlayingMoviesProvider.notifier)
                              .loadNextPage(),
                ),

                MoviesHorizontalListview(
                  movies: nowPlayingMovies,
                  title: 'Próximamente',
                  subtitle: 'En este mes',
                  loadNextPage:
                      () =>
                          ref
                              .read(nowPlayingMoviesProvider.notifier)
                              .loadNextPage(),
                ),

                MoviesHorizontalListview(
                  movies: popularMovies,
                  title: 'Populares',
                  subtitle: 'En este mes',
                  loadNextPage:
                      () =>
                          ref
                              .read(popularMoviesProvider.notifier)
                              .loadNextPage(),
                ),

                MoviesHorizontalListview(
                  movies: nowPlayingMovies,
                  title: 'Mejor calificadas',
                  subtitle: 'Desde siempre',
                  loadNextPage:
                      () =>
                          ref
                              .read(nowPlayingMoviesProvider.notifier)
                              .loadNextPage(),
                ),

                SizedBox(height: 10),
              ],
            );
          }, childCount: 1),
        ),
      ],
    );
  }
}
