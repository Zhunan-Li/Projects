using System;
using System.Threading.Tasks;

namespace YoutubeDownloader {
    class Program {
        static async Task Main(string[] args) {
            {
                if (args.Length > 0) {
                    if (args[0].Length == 11) {
                        var checkResult = await Youtube.CheckVideoIdIsValid(args[0]);

                        if (!checkResult) return;
                        // input a video id
                        if (args.Length > 1 && !Youtube.GetFormat(args[1]).Equals("False")) {
                            Youtube.DownloadFromVideoId(args[0], Youtube.GetFormat(args[1]));
                        } else {
                            Console.WriteLine("Please input format (video / music)");
                            var format = Youtube.InputFormat();
                            Youtube.DownloadFromVideoId(args[0], format);
                        }

                        return;
                    }

                    var results = await Youtube.Search(string.Join(" ", args));
                    var result = Youtube.ChooseToDownload(results);
                    var lastIndex = args.Length - 1;
                    
                    if (!Youtube.GetFormat(args[lastIndex]).Equals("False")) {
                    
                        Console.WriteLine($"Ready to Download {result.GetTitle()}");
                        var format = Youtube.GetFormat(args[lastIndex]);
                        Youtube.DownloadFromVideoId(result.GetVideoId(), format);
                    } else {
                        
                        Console.WriteLine("Please input format (video / music)");
                        var format = Youtube.InputFormat();
                        Youtube.DownloadFromVideoId(result.GetVideoId(), format);
                    }
                } else {
                    Console.WriteLine("Please input a video id or keywords");
                    Console.WriteLine("ytdl [id] [format(music/video)]");
                    Console.WriteLine("ytdl [keywords]");
                }
                // if (args.Length != 0) {
                //     var youtube = new Youtube();
                //     if (args.Length > 0 && await youtube.CheckVideoIdIsValid(args[0])) {
                //         if (args.Length == 1) {
                //             string line;
                //             while (true) {
                //                 while ((line = Console.ReadLine()) != null) {
                //                     if ("Video".Contains(line.ToLower())) {
                //                         youtube.DownloadFromVideoId(args[0], "v");
                //                         break;
                //                     } else if ("Music".Contains(line.ToLower())) {
                //                         youtube.DownloadFromVideoId(args[0], "m");
                //                         break;
                //                     } else {
                //                         Console.WriteLine("Invalid file format.");
                //                     }
                //                 }
                //             }
                //         }
                //
                //         if (args.Length == 2) {
                //             youtube.DownloadFromVideoId(args[0], args[1]);
                //         }
                //     } else {
                //         var keyword = string.Join(" ", args);
                //         Console.WriteLine($"Searching for {keyword}... \n");
                //         youtube.Search(keyword);
                //     }
                // } else {
                //     Console.WriteLine("Please input a video id or a video title.");
                // }
            }
        }
    }
}