import 'package:flutter/material.dart';

class CardsSceen extends StatelessWidget {

  static const String name = 'cards_screen';

  const CardsSceen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Cards Screen'),
      ),
      body: Placeholder(),
    );
  }
}