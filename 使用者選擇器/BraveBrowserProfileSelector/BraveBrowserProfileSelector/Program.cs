using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using Newtonsoft.Json.Linq;

namespace BraveBrowserProfileSelector {
    internal class Program {
        public static void Main(string[] args) {
            var profiles = GetAllInstance();
            profiles.Add("匿名", "   ");
            var selector = new Dictionary<int, string>();
            var index = 0;
            selector.Add(index, "匿名");
            Console.WriteLine($"{index} | 匿名");
            foreach (var profileName in profiles.Keys) {
                index++;
                selector.Add(index, profileName);
                Console.WriteLine($"{index} | {profileName}");
            }

            string s;
            while (true) {
                if ((s = Console.ReadLine()) != null) {
                    if (selector[int.Parse(s.Split(' ')[0])] != null) {
                        var select = selector[int.Parse(s.Split(' ')[0])];
                        var selectedProfile = profiles[select];

                        var incognito = "";
                        if (s.Split(' ').Length > 1 && s.Split(' ')[1].ToLower().Equals("p")) {
                            incognito = "--incognito";
                        }

                        var brave = new Process {
                            StartInfo = {
                                FileName = @"C:\Program Files\BraveSoftware\Brave-Browser\Application\brave.exe",
                                Arguments = $"--profile-directory=\"{selectedProfile}\" {incognito}"
                            }
                        };
                        brave.Start();
                        break;
                    }
                }
            }
        }

        private static string GetProfileName(string dictionaryPath) {
            var txt = File.ReadAllText(dictionaryPath + "\\Preferences");
            var profileSection = JObject.Parse(txt).GetValue("profile");
            return profileSection["name"].ToString();
        }

        private static SortedDictionary<string, string> GetAllInstance() {
            var profiles = new SortedDictionary<string, string>();
            var directoryInfo =
                new DirectoryInfo(@"C:\Users\UserName\AppData\Local\BraveSoftware\Brave-Browser\User Data");
            foreach (var dic in directoryInfo.GetDirectories()) {
                if (dic.Name.Contains("Profile") || dic.Name.Equals("Default")) {
                    var profileName = GetProfileName(dic.FullName);
                    profiles.Add(profileName, dic.Name);
                }
            }

            return profiles;
        }
    }
}