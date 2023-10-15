using System;
using System.Diagnostics;
using System.Linq;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using System.Windows.Input;
using Microsoft.Toolkit.Uwp.Notifications;

namespace InputLock.Properties
{
    public class KeyboardHook
    {
        private static IntPtr hookID = IntPtr.Zero;
        private const int WH_KEYBOARD_LL = 13;
        private const int WM_KEYDOWN = 0x0100;

        public delegate IntPtr LowLevelKeyboardProc(int nCode, IntPtr wParam, IntPtr lParam);

        public static void SetHook(LowLevelKeyboardProc proc)
        {
            using (Process process = Process.GetCurrentProcess())
            using (ProcessModule processModule = process.MainModule)
            {
                hookID = SetWindowsHookEx(WH_KEYBOARD_LL, proc, GetModuleHandle(processModule.ModuleName), 0);
            }
        }

        public static void UnHook()
        {
            UnhookWindowsHookEx(hookID);
        }

        public static IntPtr HookCallback(int nCode, IntPtr wParam, IntPtr lParam)
        {
            var key = (Keys) Marshal.ReadInt32(lParam);
            var action = (KeyboardState) wParam;

            if (action == KeyboardState.KeyUp || action == KeyboardState.SysKeyUp)
            {
                if (Program.KeyList.All(Program.lockKeys.Contains))
                {
                    ToastNotificationManagerCompat.History.RemoveGroup("InputLock");
                    if (Program._lock)
                    {
                        //unlock
                        Program._lock = false;
                            
                        new ToastContentBuilder()
                            .AddArgument("action", "viewConversation")
                            .AddArgument("conversation", 9813)
                            .AddText("Unlock")
                            .Show(toast =>
                            {
                                toast.Group = "InputLock";
                                toast.ExpirationTime = DateTime.Now.AddSeconds(1);
                            });
                    }
                    else
                    {
                        Program._lock = true;
                        
                        new ToastContentBuilder()
                            .AddArgument("action", "viewConversation")
                            .AddArgument("conversation", 9813)
                            .AddText("Lock")
                            .Show(toast =>
                            {
                                toast.Group = "InputLock";
                                toast.ExpirationTime = DateTime.Now.AddSeconds(1);
                            });
                    }
                    
                    Program.KeyList.Remove(key);
                    return new IntPtr(1);
                }

                Program.KeyList.Remove(key);

                if (!Program._lock)
                {
                    return CallNextHookEx(hookID, nCode, wParam, lParam);
                }
            }

            if (action == KeyboardState.KeyDown || action == KeyboardState.SysKeyDown)
            {
                if (!Program.KeyList.Contains(key))
                {
                    Program.KeyList.Add(key);
                }

                if (!Program._lock && !Program.KeyList.All(Program.lockKeys.Contains))
                {
                    return CallNextHookEx(hookID, nCode, wParam, lParam);
                }
            }

            return new IntPtr(1);
        }

        private enum KeyboardState
        {
            KeyDown = 0x0100,
            KeyUp = 0x0101,
            SysKeyDown = 0x0104,
            SysKeyUp = 0x0105
        }

        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        private static extern IntPtr SetWindowsHookEx(int idHook,
            LowLevelKeyboardProc lpfn, IntPtr hMod, uint dwThreadId);

        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        [return: MarshalAs(UnmanagedType.Bool)]
        private static extern bool UnhookWindowsHookEx(IntPtr hhk);

        [DllImport("user32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        private static extern IntPtr CallNextHookEx(IntPtr hhk, int nCode,
            IntPtr wParam, IntPtr lParam);

        [DllImport("kernel32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        private static extern IntPtr GetModuleHandle(string lpModuleName);
    }
}