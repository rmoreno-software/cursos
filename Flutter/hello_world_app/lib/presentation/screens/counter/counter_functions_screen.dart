import 'package:flutter/material.dart';

class CounterFunctionsScreen extends StatefulWidget {
  const CounterFunctionsScreen({super.key});

  @override
  State<CounterFunctionsScreen> createState() => _CounterFunctionsScreenState();
}

class _CounterFunctionsScreenState extends State<CounterFunctionsScreen> {
  int clickCounter = 0;
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Center(child: const Text('Counter Functions')),
        actions: [
          IconButton(
            icon: const Icon(Icons.refresh_rounded),
            onPressed: () {
              setState(() {
                clickCounter = 0;
              });
            },
          ),
        ],
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(
              '$clickCounter',
              style: const TextStyle(
                fontSize: 160,
                fontWeight: FontWeight.w100,
              ),
            ),
            Text(
              'Click${clickCounter == 1 ? '' : 's'}',
              style: const TextStyle(fontSize: 25),
            ),
          ],
        ),
      ),
      floatingActionButton: Column(
        mainAxisAlignment: MainAxisAlignment.end,
        children: [
          CustomFloatingButton(
            icon: Icons.plus_one_outlined,
            onPressed: () {
              clickCounter++;
              setState(() {});
            },
          ),

          const SizedBox(height: 10),

          CustomFloatingButton(
            icon: Icons.refresh_rounded,
            onPressed: () {
              clickCounter = 0;
              setState(() {});
            },
          ),

          const SizedBox(height: 10),

          CustomFloatingButton(
            icon: Icons.exposure_minus_1_outlined,
            onPressed: () {
              if (clickCounter == 0) return;
              clickCounter--;
              setState(() {});
            },
          ),
        ],
      ),
    );
  }
}

class CustomFloatingButton extends StatelessWidget {
  final IconData icon;
  final VoidCallback? onPressed;

  const CustomFloatingButton({super.key, required this.icon, this.onPressed});

  @override
  Widget build(BuildContext context) {
    return FloatingActionButton(
      shape: const StadiumBorder(),
      onPressed: onPressed,
      child: Icon(icon),
    );
  }
}
