
import 'package:go_router/go_router.dart';
import 'package:widgets_app/presentation/screens/screens.dart';

final appRouter = GoRouter(
  routes: [
    GoRoute(
      name: HomeScreen.name,
      path: '/',
      builder: (context, state) => HomeScreen()
    ),
    GoRoute(
      name: ButtonScreen.name,
      path: '/buttons',
      builder: (context, state) => ButtonScreen()
    ),
    GoRoute(
      name: CardsSceen.name,
      path: '/cards',
      builder: (context, state) => CardsSceen(),
    ),
    GoRoute(
      name: ProgressScreen.name,
      path: '/progress',
      builder: (context, state) => ProgressScreen(),
    ),
    GoRoute(
      name: SnackBarScreen.name,
      path: '/snackbars',
      builder: (context, state) => SnackBarScreen(),
    ),
    GoRoute(
      name: AnimatedScreen.name,
      path: '/animated',
      builder: (context, state) => AnimatedScreen(),
    ),
    GoRoute(
      name: UIControlsScreen.name,
      path: '/ui-controls',
      builder: (context, state) => UIControlsScreen(),
    ),
    GoRoute(
      name: AppTutorialScreen.name,
      path: '/tutorial',
      builder: (context, state) => AppTutorialScreen(),
    ),
    GoRoute(
      name: InfiniteScrollScreen.name,
      path: '/infinite',
      builder: (context, state) => InfiniteScrollScreen(),
    ),
    GoRoute(
      path: '/counter',
      builder: (context, state) => CounterScreen(),
    )
  ]
);