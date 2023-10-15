using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using AngleSharp;
using AngleSharp.Dom;
using Microsoft.VisualBasic.CompilerServices;
using Newtonsoft.Json.Linq;
using YoutubeDownloader.Classes;

namespace YoutubeDownloader {
    public class Youtube {
        private const string BasicYtUrl = "https://youtu.be/";
        private const string DefaultDownloadFolder = "Download Folder";
        private const string Ytdlp = "yt-dlp.exe";
        private const int FragmentCount = 8;

        public static async Task<bool> CheckVideoIdIsValid(string id) {
            var httpClient = new HttpClient();
            var task = httpClient.GetAsync($"{BasicYtUrl}{id}");
            task.Wait();
            var response = task.Result;
            if (response.StatusCode == HttpStatusCode.OK) {
                IBrowsingContext context = BrowsingContext.New(Configuration.Default);
                var document = await context.OpenAsync(res => res.Content(response.Content.ReadAsStreamAsync().Result));
                string title = document.QuerySelector("title").TextContent;
                if (!title.Equals(" - YouTube")) {
                    return true;
                }
            }

            return false;
        }

        public static string InputFormat() {
            while (true) {
                string input;
                if ((input = Console.ReadLine()) == null) continue;
                return GetFormat(input);
            }
        }

        public static string GetFormat(string input) {
            input = input.ToLower();
            if ("video".Contains(input)) {
                return "v";
            }

            if ("music".Contains(input)) {
                return "m";
            }

            return "False";
        }

        public static void DownloadFromVideoId(string id, string format) {
            var formatArg = $"--concurrent-fragments {FragmentCount} https://www.youtube.com/watch?v={id}";
            if (format.Equals("m")) {
                formatArg =
                    $"-f bestaudio[ext=webm] --extract-audio --audio-format m4a --embed-thumbnail --add-metadata --concurrent-fragments {FragmentCount} https://www.youtube.com/watch?v={id}";
            }

            Process process = new Process();
            process.StartInfo = new ProcessStartInfo {
                // WorkingDirectory = System.IO.Directory.GetCurrentDirectory(),

                WindowStyle = ProcessWindowStyle.Normal,
                // FileName = $"{DefaultDownloadFolder}/youtube-dl.exe",
                FileName = Ytdlp,
                RedirectStandardOutput = true,
                UseShellExecute = false,
                Arguments = formatArg
            };
            process.Start();
            while (!process.StandardOutput.EndOfStream) {
                Console.WriteLine(process.StandardOutput.ReadLine());
            }
        }

        public static async Task<Dictionary<int, Result>> Search(string keyword) {
            keyword = keyword.Replace(" ", "+");
            var httpClient = new HttpClient();
            var task = httpClient.GetAsync($"https://www.youtube.com/results?search_query={keyword}");
            task.Wait();
            var response = task.Result;
            if (response.StatusCode == HttpStatusCode.OK) {
                var context = BrowsingContext.New(Configuration.Default);
                var document = await context.OpenAsync(res => res.Content(response.Content.ReadAsStreamAsync().Result));
                var index = 0;
                var results = new Dictionary<int, Result>();

                var scripts = document.QuerySelectorAll($"script[nonce]");
                dynamic jsonData = null;
                foreach (var s in scripts) {
                    try {
                        var rawData = s.Html().Replace("var ytInitialData = ", "")[..^1];
                        jsonData = JValue.Parse(rawData);
                    } catch (Exception e) {
                        // ignored
                    }
                }

                foreach (var songData in jsonData["contents"]["twoColumnSearchResultsRenderer"]["primaryContents"]
                             ["sectionListRenderer"]["contents"][0]["itemSectionRenderer"]["contents"]) {
                    if (songData["videoRenderer"] == null) continue;
                    string title = songData["videoRenderer"]["title"]["runs"][0]["text"];
                    string url = songData["videoRenderer"]["videoId"];
                    var result = new Result(title, url);
                    results.Add(index++, result);
                }

                return results;
            }

            throw new InvalidCastException($"Error while getting search page. \nStatus Code {response.StatusCode}");
        }

        public static Result ChooseToDownload(Dictionary<int, Result> results) {
            Console.WriteLine("");
            foreach (var (i, result) in results) {
                Console.WriteLine($"{i} | {result.GetTitle()}");
            }

            Console.WriteLine("\nPlease enter the number of the video which you want to download.");
            Result r;
            while (true) {
                string input;
                int index;
                if ((input = Console.ReadLine()) == null) continue;
                try {
                    index = IntegerType.FromString(input);
                    r = results[index];
                    break;
                } catch (Exception) {
                    Console.WriteLine("Invalid index");
                }
            }

            return r;
        }
    }
}