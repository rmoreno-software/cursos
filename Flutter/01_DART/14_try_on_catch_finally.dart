void main() async {
  print('Inicio del programa');

  try {
    final value = await httpGet('https://roger.moreno.cat/software');
    print('Éxito $value');
  } on Exception catch(err){
    print('Tenemos una Exception: ${err}');
  } catch (err) {
    print('Ups, algo horrible pasó');
  } finally {
    print('Fin del try / catch');
  }
  print('Fin del programa');
}

Future<String> httpGet(String url) async {
  await Future.delayed(const Duration(seconds: 1));
  throw Exception('No hay parametros en el URL');
}
