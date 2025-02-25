void main() {
  final wolverin = Hero(name: 'Logan', power: 'Regeneración');

  print(wolverin);
}

class Hero {
  String name;
  String power;

  Hero({required this.name, required this.power}) {}

  /*Hero(String pName, String pPower)
    : name = pName,
      power = pPower; */

  @override
  String toString() {
    return '$name - $power';
  }
}
