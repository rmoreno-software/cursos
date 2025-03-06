import 'package:toktik/domain/datasources/video_post_data_source.dart';
import 'package:toktik/domain/entities/video_post.dart';
import 'package:toktik/domain/repositories/video_post_repository.dart';

class VideoPostsRepositoryImpl implements VideoPostRepository {

  final VideoPostDataSource videosDatasource;

  VideoPostsRepositoryImpl({required this.videosDatasource});
  
  @override
  Future<List<VideoPost>> getFavouritesVideosByUser(String userId) {
    throw UnimplementedError();
  }

  @override
  Future<List<VideoPost>> getTrendingVideosByPage(int page) {
    return videosDatasource.getTrendingVideosByPage(page);
  }

}