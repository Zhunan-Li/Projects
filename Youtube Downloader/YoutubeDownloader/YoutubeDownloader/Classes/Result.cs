namespace YoutubeDownloader.Classes {
    public class Result {
        private string _title;
        private string _videoId;

        public Result(string title, string videoId) {
            this._title = title;
            this._videoId = videoId;
        }

        public string GetTitle() {
            return this._title;
        }

        public string GetVideoId() {
            return this._videoId;
        }
    }
}