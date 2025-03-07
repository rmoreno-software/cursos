import 'package:flutter/material.dart';

const colorList = <Color>[
  Colors.blue,
  Colors.teal,
  Colors.green,
  Colors.red,
  Colors.purple,
  Colors.deepPurple,
  Colors.orange,
  Colors.pink,
  Colors.pinkAccent,
];


class AppTheme {

  final int selectedColor;

  AppTheme({
    required this.selectedColor
  }): assert(selectedColor >= 0, 'Selected color must be grater than 0'),
      assert(selectedColor < colorList.length, 'Selected color must be lower than ${colorList.length}');

    ThemeData getTheme() => ThemeData(
      useMaterial3: true,
      colorSchemeSeed: colorList[ selectedColor ],
    );

}