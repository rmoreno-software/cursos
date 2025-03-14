import 'package:flutter/material.dart';

class CounterScreen extends StatelessWidget {
 
  final int valor = 0;

  const CounterScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Counter Screen'),
      ),
      body: Center(child: Text('Valor: $valor', style: Theme.of(context).textTheme.titleLarge)),
      floatingActionButton: FloatingActionButton(
        onPressed: () {},
        child: Icon(Icons.add)
      ),
    );
  }
}