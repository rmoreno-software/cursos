import 'package:flutter/material.dart';

class UIControlsScreen extends StatelessWidget {

  static const name = 'ui_controls_screen';
  
  const UIControlsScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('UI Controls'),
      ),
      body: _UIControlsView(),
    );
  }
}

class _UIControlsView extends StatefulWidget {

  const _UIControlsView();

  @override
  State<_UIControlsView> createState() => _UIControlsViewState();
}


enum Transportation { car, plane, boat, submarine }

class _UIControlsViewState extends State<_UIControlsView> {

  bool isDeveloper = true;
  Transportation selectedTransportation = Transportation.car;

  @override
  Widget build(BuildContext context) {
    return ListView(
      physics: ClampingScrollPhysics(),
      children: [

        SwitchListTile(
          title: const Text('Developer Mode'),
          subtitle: const Text('Controles adicionales'),
          value: isDeveloper,
          onChanged: (value) => setState(() {
            isDeveloper = !isDeveloper;
          })
        ),

        RadioListTile(
          title: const Text('By Car'),
          subtitle: const Text('Viajar con coche'),
          value: Transportation.car,
          groupValue: selectedTransportation,
          onChanged: (value) => setState(() {
            selectedTransportation = Transportation.car;
          })
        ),
        RadioListTile(
          title: const Text('By Boat'),
          subtitle: const Text('Viajar con barco'),
          value: Transportation.boat,
          groupValue: selectedTransportation,
          onChanged: (value) => setState(() {
            selectedTransportation = Transportation.boat;
          })
        ),
        RadioListTile(
          title: const Text('By Plane'),
          subtitle: const Text('Viajar con avion'),
          value: Transportation.plane,
          groupValue: selectedTransportation,
          onChanged: (value) => setState(() {
            selectedTransportation = Transportation.plane;
          })
        ),
        RadioListTile(
          title: const Text('By Submarine'),
          subtitle: const Text('Viajar con submarino'),
          value: Transportation.submarine,
          groupValue: selectedTransportation,
          onChanged: (value) => setState(() {
            selectedTransportation = Transportation.submarine;
          })
        )
      ],
    );
  }
}