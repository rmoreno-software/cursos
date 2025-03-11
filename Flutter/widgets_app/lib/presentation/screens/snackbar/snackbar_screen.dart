import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

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

  void _openDialog(BuildContext context) {
    showDialog(
      context: context,
      barrierDismissible: false,
      builder: (context) => AlertDialog(
        title: const Text('¿Estás seguro?'),
        content: const Text('Incididunt dolor cupidatat est culpa aute incididunt Lorem dolore tempor esse non cillum duis. Nisi culpa velit non nostrud cupidatat aliqua ipsum pariatur enim non irure exercitation fugiat. Tempor consectetur aliqua sit fugiat laboris est sint. Excepteur exercitation aute est esse.'),
      actions: [
        TextButton(onPressed: () => context.pop(), child: const Text('Cancelar')),
        FilledButton(onPressed: () => context.pop(), child: const Text('Aceptar'))
      ],
      ),
    );
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
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            /*FilledButton.tonal(
              onPressed: (){
                showAboutDialog(context: context);
              },
              child: const Text('Licencias usadas')
            ),*/
            FilledButton.tonal(
              onPressed: () => _openDialog(context),
              child: const Text('Mostrar diálogo')
            )
          ]
        ),
      )
    );
  }
}
