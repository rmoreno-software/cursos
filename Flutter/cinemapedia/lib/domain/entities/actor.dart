class Actor {

  final int id;
  final String name;
  final String profilePath;
  final String? character;

  Actor({
    required this.id,
    required this.name,
    this.character,
    required this.profilePath
  });
}