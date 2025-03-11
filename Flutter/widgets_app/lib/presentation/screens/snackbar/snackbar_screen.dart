import 'package:flutter/material.dart';

class SnackBarScreen extends StatelessWidget {
  static const name = 'snackbar_screen';

  const SnackBarScreen({super.key});

  void _showCustomSnackbar(BuildContext context) {

    ScaffoldMessenger.of(
      context,
    ).clearSnackBars();

    final snackbar = SnackBar(
      content: const Text('Hola Mundo'),
      action: SnackBarAction(label: 'Ok!', onPressed: () {}),
      duration: Duration(seconds: 2),
    );

    ScaffoldMessenger.of(
      context,
    ).showSnackBar(snackbar);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Snackbars y diálogos')),
      floatingActionButton: FloatingActionButton.extended(
        onPressed: () => _showCustomSnackbar(context),
        icon: Icon(Icons.remove_red_eye_outlined),
        label: const Text('Mostrar Snackbar'),
      ),
    );
  }
}
