"use strict";
if (window.jQuery) {
    RuntimeHelper.init();
    var logger = new Logger(!0);
    window.jQuery(document).ready(function () {
        var e;
        chrome.runtime.sendMessage({message: "getAppInfo"});
        var t, a, o, n, i, r, s, l, d, c, g, u, p = [], f = 32750, m = "", h = !0, v = 1;
        (!!(u = navigator.userAgent.match(/Chrom(e|ium)\/([0-9]+)\./)) && parseInt(u[2], 10) || 0) >= 76 && (f = 65500);
        var w = {
                default: {
                    save: "Save",
                    cancel: "Cancel",
                    "crop-tips": "Select an area, hold Ctrl to select an element",
                    drag: "Drag and drop",
                    "drag-desc": "The image dropped here will be added into Eagle",
                    "drag-choose": "Choose folder...",
                    "updated-desc": "Eagle extension was updated in the background. You might not be able to use Eagle on this page before refreshing.",
                    "updated-button": "Refresh",
                    "not-opened-title": "Eagle is not open yet",
                    "not-opened-desc": "To save images, you must open Eagle app first.",
                    "not-opened-button": "Open Eagle",
                    "privacy-dialog-title": "Privacy Policy",
                    "privacy-dialog-desc": "The extension will not collect any personal data and information that is irrelevant to data/files that you proactively collected. By clicking 'Agree' button below, you acknowledge that you have read, understood, and agreed upon our <a style='color: inherit; text-decoration: underline;' href='https://cn.eagle.cool/eula' target='_blank'>EULA</a> and <a style='color: inherit; text-decoration: underline;' href='https://cn.eagle.cool/privacy' target='_blank'>Privacy Policy</a>.",
                    "privacy-agree-btn": "Agree",
                    "privacy-disagree-btn": "Disagree"
                },
                de: {
                    save: "Speichern",
                    cancel: "Abbrechen",
                    "crop-tips": "Wähle einen Bereich aus, halte Ctrl, um einen Element auszuwählen",
                    drag: "Drag and Drop",
                    "drag-desc": "Bilder, die hier abgelegt werden, werden zu Eagle hinzugefügt",
                    "drag-choose": "Ordner auswählen...",
                    "updated-desc": "Die Eagle-Erweiterung wurde im Hintergrund aktualisiert. Möglicherweise können Sie Eagle auf dieser Seite vor dem Aktualisieren nicht verwenden.",
                    "updated-button": "Aktualisieren",
                    "not-opened-title": "Eagle ist noch nicht geöffnet",
                    "not-opened-desc": "Um Bilder zu speichern, musst du zuerst die Eagle-App öffnen.",
                    "not-opened-button": "Eagle öffnen"
                },
                es: {
                    save: "Guardar",
                    cancel: "Cancelar",
                    "crop-tips": "Selecionar una zona y apretar Ctrl para optar un elemento",
                    drag: "arrastrar y colocar",
                    "drag-desc": "Colocar la imáge aquí para añadir a Eagle",
                    "drag-choose": "Seleccionar carpeta...",
                    "updated-desc": "La extensión Eagle se actualizó en segundo plano. Es posible que no pueda utilizar Eagle en esta página antes de actualizar.",
                    "updated-button": "Actualizar",
                    "not-opened-title": "Eagle no está encendido todavía",
                    "not-opened-desc": "Para guardar imágenes,  primero tienes que abrir Eagle.",
                    "not-opened-button": "Abrir Eagle"
                },
                ru: {
                    save: "Сохранить",
                    cancel: "Отменить",
                    "crop-tips": "Выберите область, удерживая клавишу Ctrl, чтобы выбрать элемент",
                    drag: "Перетащить и оставить",
                    "drag-desc": "Перетащите картинку сюда и быстро добавьте ее в Eagle.",
                    "drag-choose": "Выберите папку ...",
                    "updated-desc": "Расширение Eagle обновлено в фоновом режиме. Возможно, вы не сможете использовать Eagle на этой странице до обновления.",
                    "updated-button": "Обновить",
                    "not-opened-title": "Eagle еще не открыт.",
                    "not-opened-desc": "Откройте приложение Eagle, чтобы сохранить изображения.",
                    "not-opened-button": "Открыть Eagle"
                },
                "zh-TW": {
                    save: "儲存",
                    cancel: "取消",
                    "crop-tips": "框選截圖範圍，壓住 Ctrl 可選取元件",
                    drag: "拖放圖片到這裡",
                    "drag-desc": "將圖片拖放到這裡，迅速添加到 Eagle 裡。",
                    "drag-choose": "選擇資料夾...",
                    "updated-desc": "偵測到 Eagle 的擴充在背景自動更新，故您可能無法在已開啟的頁面繼續使用，煩請您重新整理頁面，即可以繼續使用。",
                    "updated-button": "重新整理",
                    "not-opened-title": "尚未開啟 Eagle",
                    "not-opened-desc": '需要開啟 Eagle 才能夠收藏圖片，如果已經開啟，還是無法順利使用，<a href="https://docs-tw.eagle.cool/article/1267-why-does-the-browser-extension-not-working" target="_blank" style="text-decoration: underline !important; color: #1E77F7 !important;">點擊這裡查看解決辦法</a>。',
                    "not-opened-button": "開啟 Eagle",
                    "privacy-dialog-title": "隱私權條款說明",
                    "privacy-dialog-desc": "我們重視您的隱私，Eagle 擴展不會收集與您主動保存數據無關的其它隱私數據，點擊下方「同意」按鈕表示您已經查閱讀並同意「<a style='color: inherit; text-decoration: underline;' href='https://tw.eagle.cool/eula' target='_blank'>用戶條款</a>」及「<a style='color: inherit; text-decoration: underline;' href='https://tw.eagle.cool/privacy' target='_blank'>隱私權政策</a>」。",
                    "privacy-agree-btn": "同意",
                    "privacy-disagree-btn": "不同意"
                },
                "zh-CN": {
                    save: "保存",
                    cancel: "取消",
                    "crop-tips": "框选截图区域，压住 Ctrl 可选择元件",
                    drag: "拖放图片到这里",
                    "drag-desc": "将图片拖放到这里，迅速添加到 Eagle 里。",
                    "drag-choose": "选择文件夹",
                    "updated-desc": "侦测到 Eagle 的扩展在后台自动更新，故您可能无法在已开启的页面继续使用，烦请您刷新页面，即可以继续使用。",
                    "updated-button": "刷新页面",
                    "not-opened-title": "尚未打开 Eagle",
                    "not-opened-desc": '需要打开 Eagle 应用程序才能使用扩展，若已经打开还是无法使用，<b>可能是代理工具造成的网络异常</b>，建议将代理工具关闭后再试试，<a href="https://docs-cn.eagle.cool/article/9-why-does-the-browser-extension-not-working" target="_blank" style="text-decoration: underline !important; color: #1E77F7 !important;">了解详情</a>。',
                    "not-opened-button": "打开 Eagle",
                    "privacy-dialog-title": "隐私权条款说明",
                    "privacy-dialog-desc": "我们重视您的隐私，Eagle 扩展不会收集与您主动保存数据无关的其它隐私数据，点击下方「同意」按钮表示您已经查阅读并同意「<a style='color: inherit; text-decoration: underline;' href='https://cn.eagle.cool/eula' target='_blank'>用户条款</a>」及「<a style='color: inherit; text-decoration: underline;' href='https://cn.eagle.cool/privacy' target='_blank'>隐私权政策</a>」。",
                    "privacy-agree-btn": "同意",
                    "privacy-disagree-btn": "不同意"
                },
                ja: {
                    save: "保存",
                    cancel: "キャンセル",
                    "crop-tips": "範囲選択，要素を選択する Ctrl キー",
                    drag: "ドラッグ&ドロップ",
                    "drag-desc": "画像 をドラッグして即 Eagle に保存",
                    "drag-choose": "フォルダを選択...",
                    "updated-desc": "Eagle拡張機能が更新されました。再度使用する前に、ページを更新する必要があります。",
                    "updated-button": "ページの更新",
                    "not-opened-title": "まだ Eagle を開いていない",
                    "not-opened-desc": "Eagle を開いてから画像を保存する、ボタンをタップして Eagle App を開く。",
                    "not-opened-button": "Eagle を開く",
                    "privacy-dialog-title": "プライバシーポリシー",
                    "privacy-dialog-desc": "Eagle拡張機能は、ユーサー自身で積極的収集したデータやファイルに関係ない個人情報やデータを収集しません。 下記の[同意する]ボタンをクリックすることで、「<a style='color: inherit; text-decoration: underline;' href='https://cn.eagle.cool/eula' target='_blank'>EULA</a>」と「<a style='color: inherit; text-decoration: underline;' href='https://cn.eagle.cool/privacy' target='_blank'>プライバシーポリシー</a>」を読み、理解し、同意したことになります。",
                    "privacy-agree-btn": "同意する",
                    "privacy-disagree-btn": "同意しない"
                },
                ko: {
                    save: "저장",
                    cancel: "취소",
                    "crop-tips": "범위설정, Ctrl 누르는 것으로 웹페이지 요소 선택",
                    drag: "여기로 이미지를 드래그하세요",
                    "drag-desc": "여기로 이미지를 드래그하세요, 빠르게 Eagle에 추가하세요",
                    "drag-choose": "폴더 선택…",
                    "updated-desc": "Eagle 확장은 백그라운드에서 업데이트되었습니다. 새로 고침하기 전에이 페이지에서 Eagle을 사용하지 못할 수도 있습니다.",
                    "updated-button": "페이지 새로 고침",
                    "not-opened-title": "Eagle이 아직 실행되지 않았습니다",
                    "not-opened-desc": "Eagle을 실행해서 이미지를 저장할 수 있습니다",
                    "not-opened-button": "Eagle 실행하기",
                    "privacy-dialog-title": "개인정보 처리 방침 안내",
                    "privacy-dialog-desc": "우리는 당신의 개인 정보를 중요시 여기며, Eagle 확장프로그램은 당신이 저장하는 자료 외에 다른 당신의 개인정보를 수집하지 않습니다. 「<a style='color: inherit; text-decoration: underline;' href='https://tw.eagle.cool/eula' target='_blank'>최종 사용자 라이센스 계약</a>」과「<a style='color: inherit; text-decoration: underline;' href='https://tw.eagle.cool/privacy' target='_blank'>개인 정보 처리 방침</a>」에 동의하신다면 아래의 동의 버튼을 눌러주세요.",
                    "privacy-agree-btn": "동의",
                    "privacy-disagree-btn": "동의하지 않음"
                }
            }, b = w.default, y = window.navigator.language.toLowerCase(),
            x = {zh: "zh-CN", es: "es", de: "de", ru: "ru", ja: "ja", ko: "ko", en: "en"}[y.split("-")[0]];

        function M() {
            chrome.storage.sync.get(["dragMode", "urlBlackList", "domainBlackList"], function (e) {
                void 0 === e.dragMode ? (v = 1, h = !0) : (v = e.dragMode, h = 0 !== e.dragMode);
                var t = e.urlBlackList || [], a = e.domainBlackList || [], o = location.href, n = new URL(o).hostname;
                a.indexOf(n) > -1 ? h = !1 : t.indexOf(o) > -1 && (h = !1), T()
            })
        }

        function A() {
            chrome.storage.sync.get(["shortcutsSettings"], function (e) {
                if (void 0 === e.shortcutsSettings) d = {
                    macOS: {
                        "url-save": "Alt + 0",
                        "batch-save": "Alt + 1",
                        "capture-area": "Alt + 2",
                        "capture-visible": "Alt + 3",
                        "capture-entire": "Alt + 4"
                    },
                    Windows: {
                        "url-save": "Alt + 0",
                        "batch-save": "Alt + 1",
                        "capture-area": "Alt + 2",
                        "capture-visible": "Alt + 3",
                        "capture-entire": "Alt + 4"
                    }
                }; else try {
                    d = JSON.parse(e.shortcutsSettings)
                } catch (e) {
                    d = {
                        macOS: {
                            "url-save": "Alt + 0",
                            "batch-save": "Alt + 1",
                            "capture-area": "Alt + 2",
                            "capture-visible": "Alt + 3",
                            "capture-entire": "Alt + 4"
                        },
                        Windows: {
                            "url-save": "Alt + 0",
                            "batch-save": "Alt + 1",
                            "capture-area": "Alt + 2",
                            "capture-visible": "Alt + 3",
                            "capture-entire": "Alt + 4"
                        }
                    }
                }
                var t, a, o, n, i;
                navigator.platform.toUpperCase().indexOf("MAC") >= 0 ? (t = d.macOS["url-save"], a = d.macOS["batch-save"], o = d.macOS["capture-area"], n = d.macOS["capture-visible"], i = d.macOS["capture-entire"]) : (t = d.Windows["url-save"], a = d.Windows["batch-save"], o = d.Windows["capture-area"], n = d.Windows["capture-visible"], i = d.Windows["capture-entire"]), Mousetrap.reset(), t && (t = t.replace(/\s/g, "").toLowerCase(), Mousetrap.bind([t], debounce(function (e) {
                    return te(() => {
                        e.preventDefault(), chrome.runtime.sendMessage({action: "save-url"})
                    }), !1
                }, 500, !0))), a && (a = a.replace(/\s/g, "").toLowerCase(), Mousetrap.bind([a], debounce(function (e) {
                    return te(() => {
                        e.preventDefault(), chrome.runtime.sendMessage({action: "batch-save"})
                    }), !1
                }, 500, !0))), o && (o = o.replace(/\s/g, "").toLowerCase(), Mousetrap.bind([o], debounce(function (e) {
                    return te(() => {
                        e.preventDefault(), I()
                    }), !1
                }, 500, !0))), n && n !== i && (n = n.replace(/\s/g, "").toLowerCase(), Mousetrap.bind([n], debounce(function (e) {
                    return te(() => {
                        e.preventDefault(), C(), chrome.runtime.sendMessage({
                            action: "capture-visible",
                            pixelRatio: window.devicePixelRatio
                        })
                    }), !1
                }, 500, !0))), i && (i = i.replace(/\s/g, "").toLowerCase(), Mousetrap.bind([i], debounce(function (e) {
                    return te(() => {
                        e.preventDefault(), C(), chrome.runtime.sendMessage({
                            action: "screencapture",
                            pixelRatio: window.devicePixelRatio
                        })
                    }), !1
                }, 500, !0))), Mousetrap.bind(["enter"], debounce(function (e) {
                    return $("#eagle-crop-save-button").length > 0 && $("#eagle-crop-save-button").trigger("click"), !1
                }, 500, !0)), Mousetrap.bind(["esc"], debounce(function (e) {
                    return C(), !1
                }, 500, !0)), Mousetrap.bind(["up"], function (e) {
                    if (z() && !e.metaKey && !e.ctrlKey) return e.preventDefault(), E(0, -1), !1
                }), Mousetrap.bind(["down"], function (e) {
                    if (z() && !e.metaKey && !e.ctrlKey) return e.preventDefault(), E(0, 1), !1
                }), Mousetrap.bind(["left"], function (e) {
                    if (z() && !e.metaKey && !e.ctrlKey) return e.preventDefault(), E(-1, 0), !1
                }), Mousetrap.bind(["right"], function (e) {
                    if (z() && !e.metaKey && !e.ctrlKey) return e.preventDefault(), E(1, 0), !1
                }), Mousetrap.bind(["shift+up"], function (e) {
                    if (z() && !e.metaKey && !e.ctrlKey) return e.preventDefault(), E(0, -10), !1
                }), Mousetrap.bind(["shift+down"], function (e) {
                    if (z() && !e.metaKey && !e.ctrlKey) return e.preventDefault(), E(0, 10), !1
                }), Mousetrap.bind(["shift+left"], function (e) {
                    if (z() && !e.metaKey && !e.ctrlKey) return e.preventDefault(), E(-10, 0), !1
                }), Mousetrap.bind(["shift+right"], function (e) {
                    if (z() && !e.metaKey && !e.ctrlKey) return e.preventDefault(), E(10, 0), !1
                }), Mousetrap.bind(["mod+up"], function (e) {
                    if (z()) return e.preventDefault(), S(0, -1), !1
                }), Mousetrap.bind(["mod+down"], function (e) {
                    if (z()) return e.preventDefault(), S(0, 1), !1
                }), Mousetrap.bind(["mod+left"], function (e) {
                    if (z()) return e.preventDefault(), S(-1, 0), !1
                }), Mousetrap.bind(["mod+right"], function (e) {
                    if (z()) return e.preventDefault(), S(1, 0), !1
                }), Mousetrap.bind(["mod+shift+up"], function (e) {
                    if (z()) return e.preventDefault(), S(0, -10), !1
                }), Mousetrap.bind(["mod+shift+down"], function (e) {
                    if (z()) return e.preventDefault(), S(0, 10), !1
                }), Mousetrap.bind(["mod+shift+left"], function (e) {
                    if (z()) return e.preventDefault(), S(-10, 0), !1
                }), Mousetrap.bind(["mod+shift+right"], function (e) {
                    if (z()) return e.preventDefault(), S(10, 0), !1
                }), Mousetrap.bind(["mod+a"], function (e) {
                    if ($("#eagle-resize-tool .resizers").length > 0) return e.preventDefault(), t = $("#eagle-resize-tool").find(".resizers"), window.innerWidth, window.innerHeight, a = document.body, o = document.documentElement, n = Math.max(a.scrollHeight, a.offsetHeight, o.clientHeight, o.scrollHeight, o.offsetHeight), i = Math.max(a.offsetWidth, o.clientWidth, o.scrollWidth, o.offsetWidth), t.find(".ui-resizable-handle").show(), t.css("height", n), t.css("width", i), t.css("left", "0px"), t.css("top", "0px"), $("body").trigger("update-crop-position"), !1;
                    var t, a, o, n, i
                })
            })
        }

        function C() {
            var e = $("#eagle-resize-tool");
            if (0 !== e.length) {
                $(window).off("resize.crop"), $("html").removeClass("eagle-cropper-opened");
                var t = e.find(".resizers"), a = ($("#eagle-crop-size"), e.find(".eagle-resizable-overlay")),
                    o = $("#eagle-crop-close-button");
                a.off("mousedown"), a.off("mousemove"), a.off("mouseup"), t.off("resizestart"), t.off("resize"), t.off("mousemove"), t.off("mouseover"), t.off("mouseleave"), t.off("resizestop"), o.off("click"), e.remove(), p.forEach(function (e) {
                    e.play()
                }), p = []
            }
        }

        function I() {
            if (document.documentElement.classList.remove("hide-eagle-cropper"), !($("#eagle-resize-tool").length > 0)) {
                var e,
                    t = $(`\n            <div id="eagle-resize-tool" class='eagle-resizable' eagle-scrolling-hidden>\n                <div id="eagle-crop-size" class="eagle-crop-size"></div>\n                <div class='resizers'>\n                    <div class='eagle-overlay t'></div>\n                    <div class='eagle-overlay r'></div>\n                    <div class='eagle-overlay b'></div>\n                    <div class='eagle-overlay l'></div>\n                    <div class="crop-buttons">\n                        <div id="eagle-crop-save-button" class="crop-button">${b.save}</div>\n                        <div id="eagle-crop-close-button" class="crop-button">${b.cancel}</div>\n                    </div>\n                </div>\n                <div class="scroll-helper top"></div>\n                <div class="scroll-helper bottom"></div>\n                <div class="eagle-crosshair-v"></div>\n                <div class="eagle-crosshair-h"></div>\n                <div id="eagle-cropper-inspector"></div>\n                <div class="eagle-resizable-overlay"></div>\n            </div>\n        `);
                $("html").addClass("eagle-cropper-opened");
                var a = function () {
                    var a,
                        o = [window.outerHeight, document.documentElement.clientHeight, document.body ? document.body.scrollHeight : 0, document.documentElement.scrollHeight, document.body ? document.body.offsetHeight : 0, document.documentElement.offsetHeight];
                    a = o, e = Math.max.apply(Math, a.filter(function (e) {
                        return e
                    })), t.height(e)
                }, o = throttle(function () {
                    var e = $("#eagle-resize-tool .resizers");
                    if (0 !== e.length) {
                        var a = $(window).scrollTop(), o = a, i = a + window.innerHeight, r = e.position(),
                            s = (r.top, r.top + n.height()), l = t.find(".crop-buttons");
                        n.height() > 0 ? s + 40 > i ? (l.css({left: `${n.offset().left + n.width() - l.width() - 5}px`}), l.removeClass("overlap-top").addClass("overlap-bottom")) : s < o ? (l.css({left: `${n.offset().left + n.width() - l.width() - 5}px`}), l.removeClass("overlap-bottom").addClass("overlap-top")) : (l.css({left: ""}), l.removeClass("overlap-top overlap-bottom")) : (l.removeClass("overlap-top overlap-bottom"), l.css({left: ""}))
                    }
                }, 50);
                $("body").off("update-crop-position").on("update-crop-position", function () {
                    o()
                }), a(), o(), $(window).on("resize.crop", throttle(function () {
                    t.height(document.scrollingElement.scrollHeight)
                }, 250)), window.jQuery("body").append(t);
                var n = t.find(".resizers"), i = $("#eagle-crop-size"), r = t.find(".eagle-resizable-overlay"),
                    s = $("#eagle-crop-save-button"), l = $("#eagle-crop-close-button"), d = t.find(".crop-buttons"),
                    c = t.find(".eagle-crosshair-v"), g = t.find(".eagle-crosshair-h");
                s.on("click", function (e) {
                    e.stopPropagation();
                    let t = document.querySelector("#eagle-resize-tool .resizers"), a = {
                        x: t.offsetLeft || 0,
                        y: t.offsetTop || 0,
                        width: t.clientWidth || 0,
                        height: t.clientHeight || 0
                    };
                    chrome.runtime.sendMessage({action: "screencapture", pixelRatio: window.devicePixelRatio, rect: a})
                }), l.on("click", function (e) {
                    e.stopPropagation(), C()
                }), n.draggable({
                    scroll: !0,
                    containment: "document"
                }), n.resizable({
                    handles: "n, e, s, w, ne, se, sw, nw",
                    maxHeight: f - 4096,
                    maxWidth: f - 4096,
                    containment: "document"
                });
                var u, m = $(".scroll-helper"), h = 20, v = $(document);
                n.on("resizestart", function () {
                    m.off("mouseenter").off("mouseleave").on("mouseenter", E).on("mouseleave", z)
                }), n.on("resizestop", function (e, t) {
                    m.off("mouseenter").off("mouseleave"), clearInterval(u)
                }), n.find(".ui-resizable-handle").hide();
                var w, y, x, M = !1, A = !1, I = {x: 0, y: 0};
                r.on("mousedown", function (e) {
                    e.stopPropagation(), n.addClass("pointer-ignore"), d.hide(), A = !0, I = {
                        x: e.offsetX,
                        y: e.offsetY
                    }
                }), $(window).off("scroll.cropping").on("scroll.cropping", throttle(function () {
                    o()
                }, 50, !0)), r.on("mousemove", function (e) {
                    if (!e.metaKey && !e.ctrlKey || e.shiftKey ? (y = void 0, j(void 0)) : (clearTimeout(w), w = setTimeout(function () {
                        j(y = B(e.pageX, e.pageY))
                    }, 20)), A) {
                        var t, a, o = I.x - e.offsetX, r = I.y - e.offsetY;
                        Math.abs(o) > 5 && Math.abs(r) > 5 && (M = !0, a = o > 0 ? I.x - o : I.x, t = r > 0 ? I.y - r : I.y, o = Math.min(Math.abs(o), f - 4096), r = Math.min(Math.abs(r), f - 4096), e.shiftKey && (o = r), n.css({
                            left: `${a}px`,
                            top: `${t}px`,
                            width: o,
                            height: r
                        }), n.find(".ui-resizable-handle").show(), i.show(), i.addClass("orientation-se"), i.css({
                            top: `${e.pageY}px`,
                            left: `${e.pageX}px`
                        }), i.html(`${Math.abs(parseInt(o))}×${Math.abs(parseInt(r))}`))
                    } else if (!M) {
                        i.show(), i.addClass("orientation-se"), i.css({top: `${e.pageY}px`, left: `${e.pageX}px`});
                        var s = b["crop-tips"];
                        navigator.platform.toUpperCase().indexOf("MAC") >= 0 && (s = s.replace("Ctrl", "⌘")), i.html(s)
                    }
                    c.css({left: e.clientX}), g.css({top: e.clientY})
                }), r.on("contextmenu", function (e) {
                    e.stopPropagation(), e.preventDefault(), C()
                }), r.on("mouseup", function (e) {
                    A = !1, n.removeClass("pointer-ignore"), i.hide(), d.show(), i.html("");
                    var t = B(e.pageX, e.pageY);
                    if (t && t === y) {
                        var r = $(t), s = r.offset(), l = r.outerWidth(), c = r.outerHeight();
                        return n.css({
                            top: s.top + "px",
                            left: s.left + "px",
                            width: Math.min(l, window.innerWidth),
                            height: c
                        }), n.find(".ui-resizable-handle").show(), o(), y = void 0, void j(void 0)
                    }
                    a(), o()
                }), n.on("mousemove", function (e, t) {
                    !e.metaKey && !e.ctrlKey || e.shiftKey ? (y = void 0, j(void 0), c.css({left: e.clientX}), g.css({top: e.clientY})) : (clearTimeout(w), w = setTimeout(function () {
                        j(y = B(e.pageX, e.pageY))
                    }, 20))
                }), n.on("mouseup", function (e, t) {
                    if (e.metaKey || e.ctrlKey) {
                        e.preventDefault();
                        var a = B(e.pageX, e.pageY);
                        if (a && a === y) {
                            var o = $(a), i = o.offset(), r = o.outerWidth(), s = o.outerHeight();
                            return n.css({
                                top: i.top + "px",
                                left: i.left + "px",
                                width: Math.min(r, window.innerWidth),
                                height: s
                            }), n.find(".ui-resizable-handle").show(), y = void 0, void j(void 0)
                        }
                    }
                }), n.on("mouseover", function (e, t) {
                    clearTimeout(x), x = setTimeout(function () {
                        c.hide(), g.hide()
                    }, 300)
                }), n.on("mouseleave", function (e, t) {
                    clearTimeout(x), c.show(), g.show()
                }), n.on("resizestart", function (e, t) {
                    e.stopPropagation(), i.show();
                    var a = e.originalEvent.target.classList.value;
                    i.removeClass("orientation-ne orientation-se orientation-nw orientation-sw orientation-n orientation-e orientation-s orientation-w"), a.indexOf("ui-resizable-ne") > -1 ? i.addClass("orientation-ne") : a.indexOf("ui-resizable-se") > -1 ? i.addClass("orientation-se") : a.indexOf("ui-resizable-sw") > -1 ? i.addClass("orientation-sw") : a.indexOf("ui-resizable-nw") > -1 ? i.addClass("orientation-nw") : a.indexOf("ui-resizable-w") > -1 ? i.addClass("orientation-w") : a.indexOf("ui-resizable-e") > -1 ? i.addClass("orientation-e") : a.indexOf("ui-resizable-s") > -1 ? i.addClass("orientation-s") : a.indexOf("ui-resizable-n") > -1 && i.addClass("orientation-n"), o()
                }), n.on("resize", function (e, t) {
                    var a = n.width(), r = n.height();
                    i.css({top: `${e.pageY}px`, left: `${e.pageX}px`}), i.html(`${parseInt(a)}×${parseInt(r)}`), o()
                }), n.on("resizestop", function (e, t) {
                    e.stopPropagation(), i.hide(), a(), o()
                }), n.on("drag", function (e, t) {
                    o()
                }), n.on("dragstop", function (e, t) {
                    a(), o()
                }), Array.from(document.querySelectorAll("video")).filter(function (e) {
                    return !e.paused
                }).forEach(function (e) {
                    p.push(e), e.pause()
                }), p = [...new Set(p)]
            }

            function z() {
                h = 20, clearInterval(u)
            }

            function E() {
                let e = h;
                $(this).hasClass("top") && (e = -h), u = setInterval(function () {
                    v.scrollTop(v.scrollTop() + e), (h += .8) > 60 && (h = 60)
                }, 20)
            }
        }

        function z() {
            return $("#eagle-resize-tool .resizers").width() > 0
        }

        function E(e, t) {
            if (z()) {
                var a = $("#eagle-resize-tool").find(".resizers"), o = a.position(), n = a.width(),
                    i = (a.height(), o.top), r = o.left, s = window.innerWidth;
                0 !== e && (e > 0 && r + e + n < s ? a.css("left", r + e) : e < 0 && r + e > 0 && a.css("left", r + e)), 0 !== t && (t > 0 ? a.css("top", i + t) : t < 0 && i + t > 0 && a.css("top", i + t))
            }
        }

        function S(e, t) {
            if (z()) {
                var a = $("#eagle-resize-tool").find(".resizers"), o = a.position(), n = a.outerWidth(),
                    i = a.outerHeight(), r = o.top, s = o.left, l = window.innerWidth;
                0 !== e && (e > 0 && s + e + n < l ? a.css("width", n + e) : e < 0 && s + e > 0 && a.css("width", n + e)), 0 !== t && (t > 0 ? a.css("height", i + t) : t < 0 && r + t > 0 && a.css("height", i + t))
            }
        }

        function j(e) {
            if (e) {
                var t = jQuery(e), a = t.offset(), o = t.outerWidth(), n = t.outerHeight();
                jQuery("#eagle-cropper-inspector").css({
                    top: parseInt(a.top) + "px",
                    left: parseInt(a.left) + "px",
                    width: parseInt(o),
                    height: parseInt(n)
                }), jQuery("#eagle-cropper-inspector").addClass("show")
            } else jQuery("#eagle-cropper-inspector").css({
                width: 0,
                height: 0
            }), jQuery("#eagle-cropper-inspector").removeClass("show")
        }

        function T() {
            c && (c.remove(), g.remove()), 1 === v ? (c = $(`\n                <div id="eagle-drop-area" class="eagle-drop-area">\n                    <div class="eagle-drop-area-content">\n                        <div class="eagle-left-panel">\n                            <div class="eagle-drop-area-item" folder-id="">\n                            <div>\n                                <div class="icon"><div class="fake-svg"></div></div>\n                                <div class="title">${b.drag}</div>\n                                <div class="description">${b["drag-desc"]}</div>\n                            </div>\n                            </div>\n                        </div>\n                        <div class="eagle-right-panel">\n                            <div class="eagle-hideable-area">\n                                <div id="eagle-drop-area-folders" class="eagle-scrolling"></div>\n                                <hr>\n                            </div>\n                            <div class="eagle-drop-area-item" folder-id="choose">\n                                <div class="title">${b["drag-choose"]}</div>\n                            </div>\n                        </div>\n                    </div>\n                </div>\n            `), g = $('<div id="eagle-drop-area-overlay"></div>')) : 2 === v && (c = $('<div id="eagle-drop-area" class="eagle-drop-bottom-area"><div class="eagle-drop-area-content"><div class="icon"><div class="fake-svg"></div></div><div class="title">' + b.drag + "</div></div></div></div>"), g = $('<div id="eagle-drop-area-overlay"></div>')), 1 === v ? $("body").on("dragleave", ".eagle-drop-area-item", function () {
                return $(this).removeClass("dragenter"), !1
            }).on("dragenter", ".eagle-drop-area-item", function () {
                return $(this).addClass("dragenter"), !1
            }).on("dragover", ".eagle-drop-area-item", function () {
                return !1
            }).on("drop", ".eagle-drop-area-item", function (e) {
                e.stopPropagation();
                var o = this || e.target, n = $(o).attr("folder-id"), i = $(o).attr("extend-tags"), r = !1, s = !1;
                "choose" === n ? r = !0 : n && (s = !0);
                var l = V(t);
                return l && (l = ee(l)), m = ExtractTextTool.extractSiteTags(), UrlHelper.toLargeUrlAsync(l).then(function (e) {
                    chrome.runtime.sendMessage({
                        title: F(t),
                        src: e,
                        url: location.href,
                        link: a,
                        folderID: n,
                        extendTags: i,
                        forceOpenCollectModal: r,
                        forceHideCollectModal: s,
                        metaTags: m,
                        metaAlt: t && t.alt,
                        metaTitle: F(t),
                        metaDescription: jQuery('meta[name="description"]').attr("content"),
                        metaKeywords: jQuery('meta[name="keywords"]').attr("content")
                    }, function (e) {
                    })
                }), $(o).removeClass("dragenter"), !1
            }) : 2 === v && c.on("click", function () {
                return g && g.removeClass("show"), c && c.removeClass("show dragover"), !1
            }).on("dragleave", function () {
                return c.removeClass("dragover"), !1
            }).on("dragover", function () {
                return c.addClass("dragover"), !1
            }).on("drop", function (e) {
                var o = V(t);
                return o && (o = ee(o)), m = ExtractTextTool.extractSiteTags(), UrlHelper.toLargeUrlAsync(o).then(function (e) {
                    chrome.runtime.sendMessage({
                        title: F(t),
                        src: e,
                        url: location.href,
                        link: a,
                        metaTags: m,
                        metaAlt: t && t.alt,
                        metaTitle: F(t),
                        metaDescription: jQuery('meta[name="description"]').attr("content"),
                        metaKeywords: jQuery('meta[name="keywords"]').attr("content")
                    }, function (e) {
                    })
                }), !1
            })
        }

        "zh-tw" === y ? b = w["zh-TW"] : w[x] && (b = w[x]), A(), M();
        var N = $(`\n        <div id="eagle-not-open-modal" style="z-index: 99999999 !important; position: fixed !important; top: 0; left: 0; right: 0; bottom: 0;">\n            <div style="overflow: hidden; z-index: 99999998 !important; position: relative !important; top: 50% !important; left: 50% !important; transform: translateX(-50%) translateY(-50%) !important; width: 280px !important; background-color: #fff !important; border-radius: 5px !important;">\n                <div class="close-eagle-not-open-modal" id="not-opened-modal-close-btn" style="cursor: pointer; position: absolute !important; top: 16px !important; right: 16px !important; width: 14px !important; height: 14px !important;"><img style="width: 10px; user-select: none; -webkit-user-drag: none;" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz48c3ZnIHdpZHRoPSIxNHB4IiBoZWlnaHQ9IjE0cHgiIHZpZXdCb3g9IjAgMCAxNCAxNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4gICAgICAgIDx0aXRsZT5tb2RhbC1jbG9zZTwvdGl0bGU+ICAgIDxkZXNjPkNyZWF0ZWQgd2l0aCBTa2V0Y2guPC9kZXNjPiAgICA8ZGVmcz48L2RlZnM+ICAgIDxnIGlkPSJQYWdlLTEiIHN0cm9rZT0ibm9uZSIgc3Ryb2tlLXdpZHRoPSIxIiBmaWxsPSJub25lIiBmaWxsLXJ1bGU9ImV2ZW5vZGQiIG9wYWNpdHk9IjAuNSI+ICAgICAgICA8ZyBpZD0i5aaC5p6c6L2v5Lu25rKh5omT5byA77yM5oiW6ICF5pyJ5byA5ZCv57+75aKZ5a+86Ie05peg5rOV5L2/55SoIiB0cmFuc2Zvcm09InRyYW5zbGF0ZSgtOTEyLjAwMDAwMCwgLTM3NS4wMDAwMDApIiBmaWxsPSIjMDAwMDAwIiBmaWxsLXJ1bGU9Im5vbnplcm8iPiAgICAgICAgICAgIDxwb2x5Z29uIGlkPSJtb2RhbC1jbG9zZSIgcG9pbnRzPSI5MjYgMzc2LjQxNSA5MjQuNTg1IDM3NSA5MTkgMzgwLjU4NSA5MTMuNDE1IDM3NSA5MTIgMzc2LjQxNSA5MTcuNTg1IDM4MiA5MTIgMzg3LjU4NSA5MTMuNDE1IDM4OSA5MTkgMzgzLjQxNSA5MjQuNTg1IDM4OSA5MjYgMzg3LjU4NSA5MjAuNDE1IDM4MiI+PC9wb2x5Z29uPiAgICAgICAgPC9nPiAgICA8L2c+PC9zdmc+"/></div>\n                <div style="user-select: none; -webkit-user-drag: none;">\n                    <div class="eagle-info-icon" style="margin: 20px auto 0;width: 72px;height: 72px;border-radius: 50%;background-color: rgba(0, 110, 255, 0.06);background-image: url(https://eagleapp.oss-cn-hongkong.aliyuncs.com/website/logo/Logo.svg);background-size: cover;"></div>\n                </div>\n                <div style="padding: 20px !important; text-align: center !important;">\n                    <div style="font-size: 16px !important; font-weight: 600 !important; color: #172b4d !important; margin-bottom: 12px !important;">${b["not-opened-title"]}</div>\n                    <div style="font-size: 13px !important;line-height: 22px !important;color: rgba(23, 43, 77, 0.7) !important;margin-bottom: 16px !important;">${b["not-opened-desc"]}</div>\n                    <a id="open-eagle" href="eagle://open" target="_blank" style="display: block !important;text-decoration: none !important;background-color: #1E77F7 !important;font-size: 13px !important;text-align: center !important;color: #fff !important;font-w: 400 !important;font-weight: 400 !important;: 40px !important;line-height: 32px !important;border-radius: 5px !important;">${b["not-opened-button"]}</a>\n                </div>\n            </div>\n            <div class="close-eagle-not-open-modal" id="not-opened-modal-overlay" style="cursor: pointer; z-index: 99999997 !important; position: absolute !important; top: 0; left: 0; right: 0; bottom: 0; background: rgba(0,0,0,0.4) !important;">\n            </div>\n        </div>\n    `),
            k = $(`\n        <div id="eagle-extension-updated-modal" style="z-index: 99999999 !important; position: fixed !important; top: 0; left: 0; right: 0; bottom: 0;">\n            <div style="overflow: hidden; z-index: 99999998 !important; position: relative !important; top: 50% !important; left: 50% !important; transform: translateX(-50%) translateY(-50%) !important; width: 280px !important; background-color: #fff !important; border-radius: 5px !important;">\n                <div class="close-extension-updated-modal" id="extension-updated-modal-close-btn" style="cursor: pointer; position: absolute !important; top: 16px !important; right: 16px !important; width: 14px !important; height: 14px !important;"><img style="width: 10px; user-select: none; -webkit-user-drag: none; margin: auto !important; border: none !important;" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz48c3ZnIHdpZHRoPSIxNHB4IiBoZWlnaHQ9IjE0cHgiIHZpZXdCb3g9IjAgMCAxNCAxNCIgdmVyc2lvbj0iMS4xIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIj4gICAgICAgIDx0aXRsZT5tb2RhbC1jbG9zZTwvdGl0bGU+ICAgIDxkZXNjPkNyZWF0ZWQgd2l0aCBTa2V0Y2guPC9kZXNjPiAgICA8ZGVmcz48L2RlZnM+ICAgIDxnIGlkPSJQYWdlLTEiIHN0cm9rZT0ibm9uZSIgc3Ryb2tlLXdpZHRoPSIxIiBmaWxsPSJub25lIiBmaWxsLXJ1bGU9ImV2ZW5vZGQiIG9wYWNpdHk9IjAuNSI+ICAgICAgICA8ZyBpZD0i5aaC5p6c6L2v5Lu25rKh5omT5byA77yM5oiW6ICF5pyJ5byA5ZCv57+75aKZ5a+86Ie05peg5rOV5L2/55SoIiB0cmFuc2Zvcm09InRyYW5zbGF0ZSgtOTEyLjAwMDAwMCwgLTM3NS4wMDAwMDApIiBmaWxsPSIjMDAwMDAwIiBmaWxsLXJ1bGU9Im5vbnplcm8iPiAgICAgICAgICAgIDxwb2x5Z29uIGlkPSJtb2RhbC1jbG9zZSIgcG9pbnRzPSI5MjYgMzc2LjQxNSA5MjQuNTg1IDM3NSA5MTkgMzgwLjU4NSA5MTMuNDE1IDM3NSA5MTIgMzc2LjQxNSA5MTcuNTg1IDM4MiA5MTIgMzg3LjU4NSA5MTMuNDE1IDM4OSA5MTkgMzgzLjQxNSA5MjQuNTg1IDM4OSA5MjYgMzg3LjU4NSA5MjAuNDE1IDM4MiI+PC9wb2x5Z29uPiAgICAgICAgPC9nPiAgICA8L2c+PC9zdmc+"/></div>\n                <div style="user-select: none; -webkit-user-drag: none;">\n                    <div class="eagle-info-icon" style="margin: 20px auto 0;width: 72px;height: 72px;border-radius: 50%;background-color: rgba(0, 110, 255, 0.06);background-image: url(https://eagleapp.oss-cn-hongkong.aliyuncs.com/website/logo/Logo.svg);background-size: cover;"></div>\n                </div>\n                <div style="padding: 20px !important; text-align: center !important;">\n                    <div style="font-size: 13px !important;line-height: 22px !important;color: #2E3033 !important;margin-bottom: 16px !important;">${b["updated-desc"]}</div>\n                    <a id="eagle-refresh-page" href="#" target="_blank" style="display: block !important;text-decoration: none !important;background-color: #1E77F7 !important;font-size: 13px !important;text-align: center !important;color: #fff !important;font-w: 400 !important;font-weight: 400 !important;: 40px !important;line-height: 32px !important;border-radius: 5px !important;">${b["updated-button"]}</a>\n                </div>\n            </div>\n            <div class="close-extension-updated-modal" id="extension-updated-modal-overlay" style="cursor: pointer; z-index: 99999997 !important; position: absolute !important; top: 0; left: 0; right: 0; bottom: 0; background: rgba(0,0,0,0.4) !important;">\n            </div>\n        </div>\n    `);
        window.jQuery("body").on("click", "#open-eagle", function (e) {
            e.preventDefault(), window.jQuery("#open-eagle-iframe").remove();
            var t = document.createElement("iframe");
            t.id = "open-eagle-iframe", t.src = "eagle://open", t.name = "frame", window.jQuery(t).css({
                h: 0,
                w: 0,
                opacity: 0
            }), window.jQuery("body").append(t), W(), setTimeout(function () {
                window.jQuery("#open-eagle-iframe").remove()
            }, 2e3)
        }), window.jQuery("body").on("click", "#eagle-refresh-page", function (e) {
            e.preventDefault(), location.reload()
        });
        var D, L = $("<div id='eagle-drag-images' style='position: fixed; top: -100000px;'></div>");

        function W() {
            N.remove()
        }

        function H() {
            window.jQuery("#eagle-not-open-modal").length <= 0 && window.jQuery("body").append(N), N.show()
        }

        function O() {
            window.jQuery("#eagle-extension-updated-modal").length <= 0 && window.jQuery("body").append(k), k.show()
        }

        window.jQuery("body").on("click", ".close-eagle-not-open-modal", function () {
            W()
        }), window.jQuery("body").on("click", ".close-extension-updated-modal", function () {
            k.remove()
        }), window.jQuery("body").on("mousemove", function (e) {
            o = e.pageX, n = e.pageY, i = e.clientX, r = e.clientY
        });
        var Q = 0, P = 0, Z = 0;

        function R(e) {
            if (2 === v) g.addClass("show"), c.addClass("show"); else {
                var t = s, a = l;
                if (0 === s && 0 === l) return;
                let n = c.outerWidth();
                var o = c.outerHeight();
                "Top" === e.orientation ? ((a = a - o - 80) < 20 && (a = 20), (t -= 100) < 20 && (t = 20), a + o > window.innerHeight && (a = window.innerHeight - o - 20), t + n > window.innerWidth && (t = window.innerWidth - n - 10), g.addClass("show"), c.addClass("show"), c.css({
                    left: t,
                    top: a
                })) : "Bottom" === e.orientation ? ((a += 80) < 20 && (a = 20), (t -= 100) < 20 && (t = 20), a + o > window.innerHeight && (a = window.innerHeight - o - 20), t + n > window.innerWidth && (t = window.innerWidth - n - 10), g.addClass("show"), c.addClass("show"), c.css({
                    left: t,
                    top: a
                })) : "Right" === e.orientation ? ((a -= 20) < 20 && (a = 20), (t += 80) < 20 && (t = 20), a + o > window.innerHeight && (a = window.innerHeight - o - 20), t + n > window.innerWidth && (t = window.innerWidth - n - 10), g.addClass("show"), c.addClass("show"), c.css({
                    left: t,
                    top: a
                })) : ((a -= 20) < 20 && (a = 20), (t = t - 80 - 200) < 20 && (t = 20), a + o > window.innerHeight && (a = window.innerHeight - o - 20), t + n > window.innerWidth && (t = window.innerWidth - n - 10), g.addClass("show"), c.addClass("show"), c.css({
                    top: a,
                    left: t
                })), $("#eagle-drop-area-folders").scrollTop(0)
            }
        }

        window.jQuery(document).on("dragstart", "img, a, video, audio, [eagle-src]", function (a) {
            if (!h) return;
            if ($("[contenteditable='true']").has(this).length > 0) return;
            if (!RuntimeHelper.isAvailable) return O(), void logger.info("[content] extension has updated, please reload");
            chrome.runtime.sendMessage({message: "getRecentFolders"}), window.jQuery("#eagle-drop-area").length <= 0 && (window.jQuery("body").append(c), window.jQuery("body").append(L), window.jQuery("body").append(g)), e && e >= 20200612 ? window.jQuery("#eagle-drop-area").removeClass("old-version") : window.jQuery("#eagle-drop-area").addClass("old-version"), t = void 0, K(a), X(a, !0);
            var o = V(t);
            if (!o) return;
            var n;
            logger.info(`[content] user drag ${n = t.tagName, "IMG" == n ? "image" : "VIDEO" == n ? "video" : "AUDIO" == n ? "audio" : "unknow"}, [${o}][${location.href}]`), chrome.runtime.sendMessage({
                action: "cache",
                src: ee(o)
            });
            var d = a.dataTransfer || a.originalEvent.dataTransfer;
            if (d && t && "IMG" === t.nodeName) {
                if (t.naturalWidth > 140) {
                    var u = 140 / t.width * t.height, p = document.createElement("canvas");
                    p.width = 140, p.height = u, p.getContext("2d").drawImage(t, 0, 0, 140, u), $("#eagle-drag-images").empty().append(p), d.setDragImage(p, 0, 0)
                }
            } else d && t && "VIDEO" === t.nodeName ? (d.setDragImage(t, 0, 0), t.removeAttribute("draggable")) : d && t && "AUDIO" === t.nodeName && (d.setDragImage(t, 0, 0), t.removeAttribute("draggable"), t.blur());
            s = a.clientX, l = a.clientY, D = function () {
                Math.abs(s - i) > Math.abs(l - r) && 1 === v ? s <= i ? (R({orientation: "Right"}), logger.info("[content] droparea show [right]")) : (R({orientation: "Left"}), logger.info("[content] droparea show [left]")) : l >= r ? (R({orientation: "Top"}), logger.info("[content] droparea show [top]")) : l < r && (R({orientation: "Bottom"}), logger.info("[content] droparea show [bottom]"))
            }
        }), window.jQuery(document).on("focus", "audio", function () {
            this.getAttribute("eagle-src-loading") || this.getAttribute("draggable") || (logger.info("[content] focus on audio, set draggable to true"), this.draggable = !0)
        }), window.jQuery(document).on("mousedown", "video, [eagle-src]", function () {
            this.getAttribute("eagle-src-loading") || this.getAttribute("draggable") || (logger.info("[content] mousedown on video set draggable to true"), this.draggable = !0)
        }), window.jQuery(document).on("mousedown", "svg", function (e) {
            logger.info("[content] mousedown on svg, set target"), X(e, !0)
        }), window.jQuery("body").on("drag", "img, a, video, audio, [eagle-src]", throttle(function (e) {
            h && (P === e.clientX && Z === e.clientY || (P = e.clientX, Z = e.clientY, ++Q > 8 && D && (D(), D = void 0)))
        }, 16));
        var G;

        function V(e) {
            for (; e;) {
                if (e.hasAttribute("eagle-src")) return e.getAttribute("eagle-src");
                if ("svg" === e.nodeName) {
                    let t = (new XMLSerializer).serializeToString(e);
                    return "data:image/svg+xml;base64," + btoa(t)
                }
                if ("CANVAS" === e.nodeName) return e.toDataURL();
                if (null != location.href.match("https://(.*).pinterest.(.*)")) {
                    let t;
                    if ("IMG" == e.nodeName ? t = e.getAttribute("src").split("/")[7] : "VIDEO" == e.nodeName && (t = e.getAttribute("poster").split("/")[7]), "undefined" != typeof VIDEO_URL_CACHE_MAP && VIDEO_URL_CACHE_MAP[t]) return VIDEO_URL_CACHE_MAP[t]
                }
                if (location.href.indexOf("dribbble.com") > -1 && "VIDEO" !== e.nodeName && null != e.closest("div") && e.closest("div").classList.contains("video")) return e.closest("div").querySelector("video").getAttribute("src");
                if (location.href.indexOf("vk.com") > -1 && "VIDEO" === e.nodeName && e.src) return t = window.jQuery(e).prop("currentSrc") || e.src;
                if ("IMG" == e.nodeName && (e.hasAttribute("src") && "" != e.getAttribute("src") || e.hasAttribute("srcset"))) {
                    if (e.src && !e.src.startsWith("blob:") || !e.src) {
                        if (e.srcset && "string" == typeof e.srcset) {
                            var t = q(e);
                            if (t) return t
                        }
                        return window.jQuery(e).prop("currentSrc") || e.src
                    }
                    if (e.src && e.src.startsWith("blob:")) {
                        let t = document.createElement("canvas"), a = t.getContext("2d");
                        return t.width = e.width, t.height = e.height, a.drawImage(e, 0, 0, e.width, e.height), t.toDataURL()
                    }
                }
                if ("VIDEO" == e.nodeName) {
                    let t;
                    if (e.src) t = window.jQuery(e).prop("currentSrc") || e.src; else {
                        let a = window.jQuery(e).find("source")[0];
                        if (t = a.src || e.src, a.type && "video/mp4" === a.type) return t
                    }
                    let a = t.toLowerCase();
                    return a.includes(".mp4") || a.includes(".webm") || a.includes("fbcdn.net") || a.includes("mime_type=video_mp4") ? t : void 0
                }
                if ("AUDIO" == e.nodeName && (e.currentSrc && !e.currentSrc.startsWith("blob:") || !e.currentSrc)) return e.currentSrc;
                var a = getComputedStyle(e).getPropertyValue("background-image");
                if ("none" !== a && -1 === a.indexOf("gradient") && -1 === a.indexOf("about:blank") && -1 === a.indexOf("overlay")) {
                    var o = a.match(/url\("?(.+?)"?\)/);
                    if (o && o[1]) return o[1]
                }
                e = e.parentElement
            }
        }

        function K(e) {
            var a = getComputedStyle(e.target), o = a.getPropertyValue("pointer-events"),
                n = a.getPropertyValue("background-image");
            if (n && e && "A" == e.target.nodeName && $(e.target).find("img").length > 1 && (n = "none"), e && "IMG" == e.target.nodeName || "none" != n && -1 === n.indexOf("gradient") && -1 === n.indexOf("about:blank") && -1 === n.indexOf("overlay")) {
                let a = getComputedStyle(e.target);
                "0" !== a.width && "0" !== a.height && "0" !== a.opacity && "none" !== a.display && "hidden" !== a.visibility && (t = e.target), t && t.naturalWidth * t.naturalHeight < 10 && (t = void 0), "none" === o && (t = void 0)
            }
            if (t || U(e.pageX, e.pageY), t || function (e, a) {
                for (var o = $("video"), n = 9999999, i = o.length - 1; i >= 0; i--) {
                    var r = _(o[i], {x: e, y: a});
                    -1 != r && (r < n && (t = o[i], n = r))
                }
            }(e.pageX, e.pageY), !t) for (var i = $("img"), r = 9999999, s = i.length - 1; s >= 0; s--) {
                if (!(i[s].naturalWidth <= 1 && i[s].naturalHeight <= 1)) -1 != (d = Y(i[s], e.target)) && d < r && (t = i[s], r = d)
            }
            if (!t) {
                r = 9999999;
                var l = $(":visible").filter(function () {
                    return "none" != getComputedStyle(this).getPropertyValue("background-image")
                });
                for (s = l.length - 1; s >= 0; s--) {
                    var d;
                    -1 != (d = Y(l[s], e.target)) && (d < r && (t = l[s], r = d))
                }
            }
        }

        function B(e, t) {
            for (var a, o = $(":visible").not("#eagle-resize-tool, #eagle-resize-tool *, .eagle-drop-area-overlay"), n = 9999999, i = o.length - 1; i >= 0; i--) {
                var r = getComputedStyle(o[i]);
                if ("0" !== r.width && "0" !== r.height && "0" !== r.opacity && "none" !== r.display && "hidden" !== r.visibility && "none" !== r.pointerEvents) {
                    var s = _(o[i], {x: e, y: t});
                    -1 != s && s < n && (a = o[i], n = s)
                }
            }
            return a
        }

        function U(e, a) {
            for (var o = $("img:visible, canvas:visible"), n = 9999999, i = o.length - 1; i >= 0; i--) {
                var r = getComputedStyle(o[i]);
                if ("0" !== r.width && "0" !== r.height && "0" !== r.opacity && "none" !== r.display && "hidden" !== r.visibility) if (!(o[i].naturalWidth <= 1 && o[i].naturalHeight <= 1)) -1 != (l = _(o[i], {
                    x: e,
                    y: a
                })) && l < n && (t = o[i], n = l)
            }
            if (!t) {
                n = 9999999;
                var s = $(":visible").filter(function () {
                    var e = getComputedStyle(this);
                    return "none" != e.getPropertyValue("background-image") && "none" != e.getPropertyValue("pointer-events")
                });
                for (i = s.length - 1; i >= 0; i--) {
                    var l;
                    -1 != (l = _(s[i], {x: e, y: a})) && (l < n && (t = s[i], n = l))
                }
            }
        }

        function X(e, o) {
            t = void 0, a = void 0;
            var n = e.target === window.document.documentElement, i = "mousedown" === e.type;
            i && e.which;
            if (i || !n) {
                if (e.target instanceof SVGElement) {
                    if (null != e.target.closest("SVG")) return void (t = e.target.closest("SVG"));
                    if ("svg" === e.target.tagName) return void (t = e.target)
                }
                if (e.target instanceof HTMLElement) {
                    if (e.target.hasAttribute("eagle-src")) return t = e.target, void (a = e.target.getAttribute("eagle-src"));
                    if ("IMG" == e.target.tagName && "PICTURE" === e.target.parentElement.tagName) {
                        let t = e.target.parentElement, a = getPictureSourceSrc(t);
                        "" != a && e.target.setAttribute("eagle-src", a)
                    }
                    if ("VIDEO" === e.target.tagName) return t = e.target, void (e.target && e.target.tagName && ("a" === e.target.tagName.toLowerCase() || e.target.parentNode && "a" === e.target.parentNode.tagName.toLowerCase()) && (a = e.target.href || e.target.parentNode.href));
                    if ("AUDIO" === e.target.tagName) return t = e.target, void (e.target && e.target.tagName && (e.target.currentSrc || e.target.children[0] && e.target.children[0].src) && (a = e.target.currentSrc || e.target.children[0].src));
                    if ("CANVAS" !== e.target.tagName) {
                        if ("IMG" === e.target.tagName ? t = e.target : o && K(e), t || (t = e.target), location.href.indexOf("huaban.com") > -1) {
                            var r = jQuery(".pin").has(t);
                            if (r.length > 0) {
                                (l = r.find("p.description").attr("data-formatted")) && l.length > 0 && (t.customTitle = l.trim())
                            } else {
                                var s = jQuery(".pin-view").has(t);
                                if (s.length > 0) (l = s.find(".description").text()) && l.length > 0 && (t.customTitle = l.trim())
                            }
                            t.title && t.alt && t.alt == t.title && t.alt.includes("[F]") ? t.customTitle = $("a span[title]").attr("title") : t.alt && !t.alt.includes("[F]") && (t.customTitle = t.alt)
                        } else if (location.href.indexOf("youtube.com") > -1) {
                            var l, d = jQuery(".ytd-rich-item-renderer").has(t);
                            if (d.length > 0) (l = d.find("#video-title-link").text()) && l.length > 0 && (t.customTitle = l.trim())
                        } else if (location.href.indexOf("behance.net") > -1) {
                            let e = document.querySelectorAll('figcaption[class^="Project-caption"] span[class^="Project-title"]');
                            e.length > 0 && (t.customTitle = e[0].innerText)
                        } else if (location.href.indexOf("instagram.com") > -1) {
                            let o = e.target.closest('a[href^="/p/"]'), n = e.target.closest("article");
                            if (o) {
                                let e = o.href, n = document.querySelectorAll("header h2");
                                if (n.length > 0) {
                                    let e = n[0].textContent;
                                    t.customTitle = `@${e}`, t.alt && (t.customTitle += ` ${t.alt}`)
                                }
                                a = e
                            } else if (n) {
                                let o = n.querySelectorAll("a"), i = o[0], r = o[o.length - 1];
                                if (o.length > 1) {
                                    let o = i.getAttribute("href").replaceAll("/", "");
                                    t.customTitle = `@${o}`, a = r.href, e.target.alt && (t.customTitle += ` ${e.target.alt}`)
                                }
                            }
                        } else if (location.href.indexOf("weibo.com") > -1) {
                            let t = e.target.closest("article");
                            if (t) {
                                let e = t.querySelectorAll("a");
                                if (e[2] && e[2].getAttribute("class").includes("time")) {
                                    let t = e[2];
                                    a = t.href
                                }
                            }
                        }
                        if (e.target && e.target.tagName && ("a" === e.target.tagName.toLowerCase() || e.target && e.target.parentNode && e.target.parentNode.tagName && "a" === e.target.parentNode.tagName.toLowerCase()) && (e.target.href && "nofollow" !== e.target.rel ? a = e.target.href : e.target.parentNode.href && "nofollow" !== e.target.parentNode.rel && (a = e.target.href)), !a) {
                            var c = jQuery("a").has(e.target);
                            if (c.length > 0) {
                                let e = c.attr("rel"), t = c.attr("target");
                                "nofollow" !== e && "noopener" !== e && "_blank" !== t && c.attr("href") && (a = ee(c.attr("href")))
                            }
                        }
                        /^(?:(?:https?|ftp):\/\/)?(?:(?!(?:10|127)(?:\.\d{1,3}){3})(?!(?:169\.254|192\.168)(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)(?:\.(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)*(?:\.(?:[a-z\u00a1-\uffff]{2,})))(?::\d{2,5})?(?:\/\S*)?$/i.test(a) || (a = void 0), a && -1 !== a.indexOf("www.taobao.com/view_image.php") && (a = void 0)
                    } else t = e.target
                }
            }
        }

        function Y(e, t) {
            var a = e.clientWidth, o = e.clientHeight, n = e.getBoundingClientRect().left,
                i = e.getBoundingClientRect().top, r = t.clientWidth, s = t.clientHeight,
                l = t.getBoundingClientRect().left, d = t.getBoundingClientRect().top;
            if (i + o < d || i > d + s || n + a < l || n > l + r) return -1;
            var c = parseInt(J(e));
            return c >= 0 ? Math.abs(n - l) + Math.abs(i - d) != 0 ? Math.abs(n - l) + Math.abs(i - d) - c : c : Math.abs(n - l) + Math.abs(i - d)
        }

        function _(e, t) {
            var a = e.clientWidth, o = e.clientHeight, n = $(e).offset().left, i = $(e).offset().top, r = 1, s = 1,
                l = t.x, d = t.y;
            if (i + o < d || i > d + s || n + a < l || n > l + r) return -1;
            var c = parseInt(J(e));
            return c >= 0 ? Math.sqrt(Math.pow(n - l, 2) + Math.pow(i - d, 2)) - 1e3 * c : Math.sqrt(Math.pow(n - l, 2) + Math.pow(i - d, 2))
        }

        function J(e) {
            if (e == document) return 0;
            var t = document.defaultView.getComputedStyle(e).getPropertyValue("z-index");
            return isNaN(t) ? J(e.parentNode) : t
        }

        function F(e) {
            var t = e && (e.getAttribute("eagle-title") || e.customTitle || e.title) || e && e.alt;
            try {
                t = t && t.trim();
                var a = V(e) || e.currentSrc || e.src || q(e);
                if (a = a.split("?")[0], !t && a && a.indexOf(".") > -1) try {
                    if ((t = decodeURIComponent(a).split("/").pop().replace(/\.[^.]*$/, "")) && null === t.match(/[^a-zA-Z0-9 -_]/g)) {
                        var o = t.split(/\d+/).length;
                        1 === t.split(" ").length && o > 2 && (t = document.title)
                    }
                    new RegExp("/[0-9a-f]{32}.|/[0-9a-f]{40}.", "g").test(a) && (t = document.title)
                } catch (e) {
                }
                return location.href === a && (t = new URL(a).pathname.split("/").pop()), ("图片" === t || t.indexOf("https://") > -1) && (t = void 0), t && t != parseInt(t) || (t = document.title), t
            } catch (e) {
                return t
            }
        }

        function q(e) {
            const t = /^-?\d+$/;
            let a = e.currentSrc || e.getAttribute("src");
            if (e.getAttribute("srcset") && e.currentSrc && -1 === e.currentSrc.indexOf("pximg")) {
                var o = e.getAttribute("srcset"), n = (i = o.split(/,+/).map(e => {
                    const a = {};
                    return e.trim().split(/\s+/).forEach((e, o) => {
                        if (0 === o) return void (a.url = e);
                        const n = e.slice(0, -1), i = e[e.length - 1], r = Number.parseInt(n, 10);
                        Number.parseFloat(n);
                        "w" === i && t.test(n) ? r > 0 && (a.width = r) : "x" === i && t.test(n) && r > 0 && (a.width = r)
                    }), a
                })).sort().filter((e, t) => JSON.stringify(e) !== JSON.stringify(i[t - 1]));
                return (n = n.sort(function (e, t) {
                    return e.width > t.width ? -1 : e.width < t.width ? 1 : 0
                })).length > 0 && n[0] && n[0].width > 0 && n[0].url ? n[0].url : a
            }
            return a;
            var i
        }

        function ee(e) {
            e && e.indexOf(" ") > -1 && (e = e.trim().split(" ")[0]);
            var t = document.createElement("a");
            return t.href = e, t.href
        }

        function te(e) {
            RuntimeHelper.isAvailable ? chrome.runtime.sendMessage({action: "ensure-eagle-open"}, function (t) {
                !0 !== t ? H() : e()
            }) : O()
        }

        window.jQuery("body").on("dragend", "img, a, video, audio, [eagle-src]", function (e) {
            clearTimeout(void 0), Q = 0, h && (s = l = 0, 2 === v ? (g && g.removeClass("show"), c && c.removeClass("show dragover")) : (g && g.removeClass("show"), c && c.removeClass("show dragover"), c && c.css({top: "-999999px"})))
        }), window.jQuery("body").on("mouseup", "img, a, video, audio, [eagle-src], canvas", function (e) {
            clearTimeout(void 0), 2 === v ? (g && g.removeClass("show"), c && c.removeClass("show dragover")) : (g && g.removeClass("show"), c && c.removeClass("show dragover"), c && c.css({top: "-999999px"}))
        }), window.jQuery("body").on("mouseup", function (e) {
            clearTimeout(void 0), 2 === v ? (g && g.removeClass("show"), c && c.removeClass("show dragover")) : (g && g.removeClass("show"), c && c.removeClass("show dragover"), c && c.css({top: "-999999px"}))
        }), window.addEventListener("mousedown", function (e) {
            if (X(e, e.altKey || 0 !== e.button), 2 === e.button) {
                if (e.altKey) {
                    if (!RuntimeHelper.isAvailable) return void O();
                    !function (e) {
                        try {
                            var t = e.clientX, a = e.clientY,
                                o = $(`<div style="top: ${a - 30}px; left: ${t - 30}px;" class="eagle-click-effect-circle"><div></div><div></div></div>`);
                            $("body").append(o), setTimeout(function () {
                                o.remove()
                            }, 1e3)
                        } catch (e) {
                        }
                    }(e)
                }
                var a = V(t);
                a ? (chrome.runtime.sendMessage({
                    action: "cache",
                    src: ee(a)
                }), t && t.hasAttribute("eagle-src-type") ? chrome.runtime.sendMessage({
                    message: "updateContextMenu",
                    type: t.getAttribute("eagle-src-type")
                }) : t && "VIDEO" === t.nodeName ? chrome.runtime.sendMessage({
                    message: "updateContextMenu",
                    type: "video"
                }) : chrome.runtime.sendMessage({
                    message: "updateContextMenu",
                    type: "image"
                })) : chrome.runtime.sendMessage({message: "updateContextMenu", type: "none"})
            }
        }, !1), window.jQuery("body").on("contextmenu", "*", function (e) {
            e.altKey && (logger.info("[content] contextmenu with altKey, called."), e.preventDefault(), e.stopPropagation()), m = ExtractTextTool.extractSiteTags(), clearTimeout(G), G = setTimeout(function () {
                if (e.altKey) {
                    X(e, !0);
                    var o = V(t);
                    if (!o) return;
                    o = ee(o), chrome.runtime.sendMessage({
                        title: F(t),
                        src: o,
                        link: a,
                        metaTags: m,
                        metaAlt: t && t.alt,
                        metaTitle: F(t),
                        metaDescription: jQuery('meta[name="description"]').attr("content"),
                        metaKeywords: jQuery('meta[name="keywords"]').attr("content")
                    }, function (e) {
                    })
                }
                X(e, !0)
            }, 150)
        }), chrome.runtime.onMessage.addListener(function (i, r, s) {
            if ("get-app-info" == i.action) i.data && i.data.buildVersion && (e = parseInt(i.data.buildVersion)), s(); else if ("get-recent-folder" == i.action) $("#eagle-drop-area").removeClass("old-version"), $("#eagle-drop-area-folders").empty(), i.data && i.data.length > 0 ? (i.data.length = 16, i.data.forEach(function (e) {
                var t = e.extendTags || [];
                $("#eagle-drop-area-folders").append(`\n                        <div class="eagle-drop-area-item" folder-id="${e.id}" extend-tags="${t.join()}">\n                            <div class="title">${e.name}</div>\n                        </div>\n                    `)
            })) : $(".eagle-hideable-area").hide(), s(); else if ("quick-save" == i.action) te(() => {
                if (o && n && (U(o, n), t)) {
                    var e = V(t);
                    e && (e = ee(e)), chrome.runtime.sendMessage({
                        title: F(t),
                        src: e,
                        metaTags: m,
                        metaAlt: t && t.alt,
                        metaTitle: F(t),
                        metaDescription: jQuery('meta[name="description"]').attr("content"),
                        metaKeywords: jQuery('meta[name="keywords"]').attr("content")
                    }, function (e) {
                    })
                }
            }), s(); else if ("open-not-opened-modal" == i.action) H(), s(); else if ("drag-mode-0" == i.action) h = !1, v = 0, s(); else if ("drag-mode-1" == i.action) h = !0, v = 1, T(), s(); else if ("drag-mode-2" == i.action) h = !0, v = 2, T(), s(); else if ("update-drag-mode" == i.action) M(), s(); else if ("open-crop-area" === i.action) I(), s(); else if ("close-crop-area" === i.action) C(), s(); else if ("shortcuts-change" === i.action) A(), s(); else if ("start-screencapture" === i.action) te(() => {
                chrome.runtime.sendMessage({action: "screencapture", pixelRatio: window.devicePixelRatio})
            }), s(); else if ("start-capture-visible" === i.action) te(() => {
                chrome.runtime.sendMessage({action: "capture-visible", pixelRatio: window.devicePixelRatio})
            }), s(); else if ("context-save" === i.action) {
                let e = V(t);
                if (void 0 === e) return;
                e = ee(e), UrlHelper.toLargeUrlAsync(e).then(function (e) {
                    chrome.runtime.sendMessage({
                        title: F(t),
                        src: e,
                        url: location.href,
                        link: a,
                        metaTags: ExtractTextTool.extractSiteTags(),
                        metaAlt: t && t.alt,
                        metaTitle: t && (t.customTitle || t.title) || document.title,
                        metaDescription: jQuery('meta[name="description"]').attr("content"),
                        metaKeywords: jQuery('meta[name="keywords"]').attr("content")
                    }, function (e) {
                    })
                })
            } else "hide-save-to-eagle-dialog" === i.action && $("#save-to-eagle-dialog").remove();
            return !0
        })
    })
}