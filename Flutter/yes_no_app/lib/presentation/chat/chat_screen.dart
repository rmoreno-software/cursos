import 'package:flutter/material.dart';

class ChatScreen extends StatelessWidget {
  const ChatScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: const Padding(
          padding: EdgeInsets.all(4.0),
          child: CircleAvatar(
            backgroundImage: NetworkImage(
              'https://upload.wikimedia.org/wikipedia/commons/thumb/e/eb/Ana_de_Armas_%2854462814884%29_%28cropped%29.jpg/250px-Ana_de_Armas_%2854462814884%29_%28cropped%29.jpg',
            ),
          ),
        ),
        title: const Text('Mi amor❤️'),
        centerTitle: false,
      ),
    );
  }
}
