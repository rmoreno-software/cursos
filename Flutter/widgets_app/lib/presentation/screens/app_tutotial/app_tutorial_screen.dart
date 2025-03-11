import 'package:flutter/material.dart';


class SlideInfo {
  final String title;
  final String caption;
  final String imageUrl;

  SlideInfo(this.title, this.caption, this.imageUrl);

}

final slides = <SlideInfo> [
  SlideInfo('Busca la comida', 'Consequat non occaecat minim nulla nisi ipsum nisi ex consequat proident pariatur aliquip in.', 'assets/images/1.png'),
  SlideInfo('Entrega rápida', 'Fugiat laboris nisi anim ex elit amet do enim aliquip culpa nisi.', 'assets/images/2.png'),
  SlideInfo('Disfruta la comida', 'Ipsum dolore laborum Lorem do proident ad dolore.', 'assets/images/3.png'),
];

class AppTutorialScreen extends StatelessWidget {

  static const name = 'tutorial_screen';
  
  const AppTutorialScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: PageView(
        physics: BouncingScrollPhysics(),
        children: slides.map(
          (slideData) => _Slide(
            title: slideData.title,
            caption: slideData.caption,
            imageUrl: slideData.imageUrl,
          )
        ).toList(),
      )
    );
  }
}

class _Slide extends StatelessWidget {
  
  final String title;
  final String caption;
  final String imageUrl;

  const _Slide({
    required this.title,
    required this.caption,
    required this.imageUrl
  });

  @override
  Widget build(BuildContext context) {
    return const Placeholder();
  }
}