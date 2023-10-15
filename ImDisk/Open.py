import win32gui
import winxpgui
import win32con
import win32api


def main():
    im_disk = 0
    while im_disk == 0:
        im_disk = win32gui.FindWindow(None, "ImDisk")
    win32gui.SetWindowLong(im_disk, win32con.GWL_EXSTYLE,
                           win32gui.GetWindowLong(im_disk, win32con.GWL_EXSTYLE) | win32con.WS_EX_LAYERED)
    winxpgui.SetLayeredWindowAttributes(im_disk, win32api.RGB(0, 0, 0), 0, win32con.LWA_ALPHA)
    win32gui.PostMessage(im_disk, win32con.WM_KEYDOWN, win32con.VK_RETURN, 0)

    disk_menu = 0
    while disk_menu == 0:
        disk_menu = win32gui.FindWindow(None, "RamDisk (R:) - 內容")
    win32gui.PostMessage(disk_menu, win32con.WM_KEYDOWN, win32con.VK_RETURN, 0)
    win32gui.PostMessage(im_disk, win32con.WM_CLOSE, 0, 0)


if __name__ == "__main__":
    main()
