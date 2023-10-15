using System;
using System.Collections.Generic;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using InputLock.Properties;
using Microsoft.Toolkit.Uwp.Notifications;

//參考 https://stackoverflow.com/questions/4524608/how-can-i-disable-mouse-click-event-system-wide-using-c
//參考 https://stackoverflow.com/questions/604410/global-keyboard-capture-in-c-sharp-application

namespace InputLock
{
    internal class Program
    {
        public static Boolean _lock = false;
        public static List<Keys> KeyList = new List<Keys>();
        public static List<Keys> lockKeys = new List<Keys>();

        public static void Main(string[] args)
        {
            Console.Out.WriteLine("HOOK");
            SetDefaultKeys();
            ShowWindow(GetConsoleWindow(), Window_Hide);

            MouseHook.SetHook(MouseHook.HookCallback);
            KeyboardHook.SetHook(KeyboardHook.HookCallback);

            new ToastContentBuilder()
                .AddArgument("action", "viewConversation")
                .AddArgument("conversation", 9813)
                .AddText("HotKey : NumPad8")
                .Show(toast =>
                {
                    toast.Group = "InputLock";
                    toast.ExpirationTime = DateTime.Now.AddSeconds(1);
                });
            
            Application.Run();

            MouseHook.UnHook();
            KeyboardHook.UnHook();
        }

        private static void SetDefaultKeys()
        {
            lockKeys.Add(Keys.NumPad8);
        }

        [DllImport("kernel32.dll")]
        static extern IntPtr GetConsoleWindow();

        private const int Window_Visible = 5;
        private const int Window_Hide = 0;

        [DllImport("user32.dll")]
        static extern bool ShowWindow(IntPtr window, int nCmdShow);
    }
}