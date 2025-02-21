void main() {
  print(greetEveryone());
  print('Suma: ${addTwoNumbers(10, 20)}');
  print(greetPerson(name: "Roger", message: "Bon dia"));
}

String greetEveryone() {
  return 'Hello everyone!';
}

/*int addTwoNumbers(int a, int b) {
  return a + b;
} */

int addTwoNumbers(int a, int b) => a + b;

int addTwoNumbersOptionals(int a, [int b = 0]) {
  // b ??= 0;

  return a + b;
}

String greetPerson({required String name, String message = 'Hola'}) {
  return '$message, $name';
}
