import 'package:intl/intl.dart';

class HumanFormats {

  static String humanReadbleNumber( double number) {
    
    final String formattedNumber = NumberFormat.compactCurrency(
      decimalDigits: 0,
      symbol: ''
    ).format(number);
    return formattedNumber;
  }

}