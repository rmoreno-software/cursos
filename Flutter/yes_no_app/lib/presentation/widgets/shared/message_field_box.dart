import 'package:flutter/material.dart';

class MessageFieldBox extends StatelessWidget {
  const MessageFieldBox({super.key});

  @override
  Widget build(BuildContext context) {
    final outlineInputBorder = UnderlineInputBorder(
      borderSide: const BorderSide(color: Colors.transparent),
      borderRadius: BorderRadius.circular(40),
    );

    final inputDecoration = InputDecoration(
      enabledBorder: outlineInputBorder,
      focusedBorder: outlineInputBorder,
      filled: true,
      suffixIcon: IconButton(
        icon: Icon(Icons.send_outlined),
        onPressed: () {
          // print('Valor de la caja de texto');
        },
      ),
    );

    return TextFormField(
      decoration: inputDecoration,
      onChanged: (value) {
        print('Value: $value');
      },
      onFieldSubmitted: (value) {
        print('Submited value $value');
      },
    );
  }
}
