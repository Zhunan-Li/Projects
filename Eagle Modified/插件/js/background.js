"use strict";
const emojiRegex = /\uD83D\uDC69\u200D(?:\uD83C[\uDF3E\uDF73\uDF93\uDFA4\uDFA8\uDFEB\uDFED]|\uD83D[\uDCBB\uDCBC\uDD27\uDD2C\uDE80\uDE92]|\u2764\uFE0F\u200D(?:\uD83D\uDC8B\u200D(?:\uD83D[\uDC68\uDC69])|\uD83D[\uDC68\uDC69]))|\uD83C\uDFF3\uFE0F\u200D\uD83C\uDF08|\uD83D\uDC69\u200D(?:\uD83D\uDC69\u200D)?\uD83D\uDC67\u200D(?:\uD83D[\uDC66\uDC67])|\uD83D\uDC69\u200D(?:\uD83D\uDC69\u200D)?\uD83D\uDC66\u200D\uD83D\uDC66|\uD83D\uDC69(?:\uD83C[\uDFFB-\uDFFF])\u200D(?:\uD83C[\uDF3E\uDF73\uDF93\uDFA4\uDFA8\uDFEB\uDFED]|\uD83D[\uDCBB\uDCBC\uDD27\uDD2C\uDE80\uDE92])|\uD83D\uDC68(?:\u200D(?:\uD83C[\uDF3E\uDF73\uDF93\uDFA4\uDFA8\uDFEB\uDFED]|\uD83D[\uDCBB\uDCBC\uDD27\uDD2C\uDE80\uDE92]|(?:(?:\uD83D[\uDC68\uDC69])\u200D)?\uD83D\uDC67\u200D(?:\uD83D[\uDC66\uDC67])|(?:(?:\uD83D[\uDC68\uDC69])\u200D)?\uD83D\uDC66\u200D\uD83D\uDC66|\u2764\uFE0F\u200D(?:\uD83D\uDC8B\u200D)?\uD83D\uDC68)|(?:\uD83C[\uDFFB-\uDFFF])\u200D(?:\uD83C[\uDF3E\uDF73\uDF93\uDFA4\uDFA8\uDFEB\uDFED]|\uD83D[\uDCBB\uDCBC\uDD27\uDD2C\uDE80\uDE92]))|(?:\uD83D\uDC69\u200D[\u2695\u2696\u2708]|\uD83D\uDC41\uFE0F\u200D\uD83D\uDDE8|\uD83D\uDC69(?:\uD83C[\uDFFB-\uDFFF])\u200D[\u2695\u2696\u2708]|\uD83D\uDC68(?:(?:\uD83C[\uDFFB-\uDFFF])\u200D[\u2695\u2696\u2708]|\u200D[\u2695\u2696\u2708]))\uFE0F|(?:\uD83C[\uDFC3\uDFC4\uDFCA]|\uD83D[\uDC6E\uDC6F\uDC71\uDC73\uDC77\uDC81\uDC82\uDC86\uDC87\uDE45-\uDE47\uDE4B\uDE4D\uDE4E\uDEA3\uDEB4-\uDEB6]|\uD83E[\uDD26\uDD37-\uDD39\uDD3C-\uDD3E\uDDD6-\uDDDF])\u200D[\u2640\u2642]\uFE0F|(?:\uD83C[\uDFC3\uDFC4\uDFCA]|\uD83D[\uDC6E\uDC71\uDC73\uDC77\uDC81\uDC82\uDC86\uDC87\uDE45-\uDE47\uDE4B\uDE4D\uDE4E\uDEA3\uDEB4-\uDEB6]|\uD83E[\uDD26\uDD37-\uDD39\uDD3D\uDD3E\uDDD6-\uDDDD])(?:\uD83C[\uDFFB-\uDFFF])\u200D[\u2640\u2642]\uFE0F|(?:\u26F9|\uD83C[\uDFCB\uDFCC]|\uD83D\uDD75)(?:(?:\uD83C[\uDFFB-\uDFFF])\u200D[\u2640\u2642]|\uFE0F\u200D[\u2640\u2642])\uFE0F|(?:[\u261D\u270A-\u270D]|\uD83C[\uDF85\uDFC2\uDFC7]|\uD83D[\uDC42\uDC43\uDC46-\uDC50\uDC66\uDC67\uDC70\uDC72\uDC74-\uDC76\uDC78\uDC7C\uDC83\uDC85\uDCAA\uDD74\uDD7A\uDD90\uDD95\uDD96\uDE4C\uDE4F\uDEC0\uDECC]|\uD83E[\uDD18-\uDD1C\uDD1E\uDD1F\uDD30-\uDD36\uDDD1-\uDDD5])(?:\uD83C[\uDFFB-\uDFFF])|\uD83C\uDFF4\uDB40\uDC67\uDB40\uDC62(?:\uDB40\uDC77\uDB40\uDC6C\uDB40\uDC73|\uDB40\uDC73\uDB40\uDC63\uDB40\uDC74|\uDB40\uDC65\uDB40\uDC6E\uDB40\uDC67)\uDB40\uDC7F|\uD83C\uDDFF(?:\uD83C[\uDDE6\uDDF2\uDDFC])|\uD83C\uDDFE(?:\uD83C[\uDDEA\uDDF9])|\uD83C\uDDFD\uD83C\uDDF0|\uD83C\uDDFC(?:\uD83C[\uDDEB\uDDF8])|\uD83C\uDDFB(?:\uD83C[\uDDE6\uDDE8\uDDEA\uDDEC\uDDEE\uDDF3\uDDFA])|\uD83C\uDDFA(?:\uD83C[\uDDE6\uDDEC\uDDF2\uDDF3\uDDF8\uDDFE\uDDFF])|\uD83C\uDDF9(?:\uD83C[\uDDE6\uDDE8\uDDE9\uDDEB-\uDDED\uDDEF-\uDDF4\uDDF7\uDDF9\uDDFB\uDDFC\uDDFF])|\uD83C\uDDF8(?:\uD83C[\uDDE6-\uDDEA\uDDEC-\uDDF4\uDDF7-\uDDF9\uDDFB\uDDFD-\uDDFF])|\uD83C\uDDF7(?:\uD83C[\uDDEA\uDDF4\uDDF8\uDDFA\uDDFC])|\uD83C\uDDF6\uD83C\uDDE6|\uD83C\uDDF5(?:\uD83C[\uDDE6\uDDEA-\uDDED\uDDF0-\uDDF3\uDDF7-\uDDF9\uDDFC\uDDFE])|\uD83C\uDDF4\uD83C\uDDF2|\uD83C\uDDF3(?:\uD83C[\uDDE6\uDDE8\uDDEA-\uDDEC\uDDEE\uDDF1\uDDF4\uDDF5\uDDF7\uDDFA\uDDFF])|\uD83C\uDDF2(?:\uD83C[\uDDE6\uDDE8-\uDDED\uDDF0-\uDDFF])|\uD83C\uDDF1(?:\uD83C[\uDDE6-\uDDE8\uDDEE\uDDF0\uDDF7-\uDDFB\uDDFE])|\uD83C\uDDF0(?:\uD83C[\uDDEA\uDDEC-\uDDEE\uDDF2\uDDF3\uDDF5\uDDF7\uDDFC\uDDFE\uDDFF])|\uD83C\uDDEF(?:\uD83C[\uDDEA\uDDF2\uDDF4\uDDF5])|\uD83C\uDDEE(?:\uD83C[\uDDE8-\uDDEA\uDDF1-\uDDF4\uDDF6-\uDDF9])|\uD83C\uDDED(?:\uD83C[\uDDF0\uDDF2\uDDF3\uDDF7\uDDF9\uDDFA])|\uD83C\uDDEC(?:\uD83C[\uDDE6\uDDE7\uDDE9-\uDDEE\uDDF1-\uDDF3\uDDF5-\uDDFA\uDDFC\uDDFE])|\uD83C\uDDEB(?:\uD83C[\uDDEE-\uDDF0\uDDF2\uDDF4\uDDF7])|\uD83C\uDDEA(?:\uD83C[\uDDE6\uDDE8\uDDEA\uDDEC\uDDED\uDDF7-\uDDFA])|\uD83C\uDDE9(?:\uD83C[\uDDEA\uDDEC\uDDEF\uDDF0\uDDF2\uDDF4\uDDFF])|\uD83C\uDDE8(?:\uD83C[\uDDE6\uDDE8\uDDE9\uDDEB-\uDDEE\uDDF0-\uDDF5\uDDF7\uDDFA-\uDDFF])|\uD83C\uDDE7(?:\uD83C[\uDDE6\uDDE7\uDDE9-\uDDEF\uDDF1-\uDDF4\uDDF6-\uDDF9\uDDFB\uDDFC\uDDFE\uDDFF])|\uD83C\uDDE6(?:\uD83C[\uDDE8-\uDDEC\uDDEE\uDDF1\uDDF2\uDDF4\uDDF6-\uDDFA\uDDFC\uDDFD\uDDFF])|\uD83D\uDC68(?:\u200D(?:(?:(?:\uD83D[\uDC68\uDC69])\u200D)?\uD83D\uDC67|(?:(?:\uD83D[\uDC68\uDC69])\u200D)?\uD83D\uDC66)|\uD83C[\uDFFB-\uDFFF])|\uD83D\uDC69(?:\u200D(?:(?:\uD83D\uDC69\u200D)?\uD83D\uDC67|(?:\uD83D\uDC69\u200D)?\uD83D\uDC66)|\uD83C[\uDFFB-\uDFFF])|(?:\uD83C[\uDFC3\uDFC4\uDFCA]|\uD83D[\uDC6E\uDC71\uDC73\uDC77\uDC81\uDC82\uDC86\uDC87\uDE45-\uDE47\uDE4B\uDE4D\uDE4E\uDEA3\uDEB4-\uDEB6]|\uD83E[\uDD26\uDD37-\uDD39\uDD3D\uDD3E\uDDD6-\uDDDD])(?:\uD83C[\uDFFB-\uDFFF])|(?:\u26F9|\uD83C[\uDFCB\uDFCC]|\uD83D\uDD75)(?:\uD83C[\uDFFB-\uDFFF])|[#\*0-9]\uFE0F\u20E3|(?:[\u261D\u26F9\u270A-\u270D]|\uD83C[\uDF85\uDFC2-\uDFC4\uDFC7\uDFCA-\uDFCC]|\uD83D[\uDC42\uDC43\uDC46-\uDC50\uDC66-\uDC69\uDC6E\uDC70-\uDC78\uDC7C\uDC81-\uDC83\uDC85-\uDC87\uDCAA\uDD74\uDD75\uDD7A\uDD90\uDD95\uDD96\uDE45-\uDE47\uDE4B-\uDE4F\uDEA3\uDEB4-\uDEB6\uDEC0\uDECC]|\uD83E[\uDD18-\uDD1C\uDD1E\uDD1F\uDD26\uDD30-\uDD39\uDD3D\uDD3E\uDDD1-\uDDDD])(?:\uD83C[\uDFFB-\uDFFF])?|(?:[\u231A\u231B\u23E9-\u23EC\u23F0\u23F3\u25FD\u25FE\u2614\u2615\u2648-\u2653\u267F\u2693\u26A1\u26AA\u26AB\u26BD\u26BE\u26C4\u26C5\u26CE\u26D4\u26EA\u26F2\u26F3\u26F5\u26FA\u26FD\u2705\u270A\u270B\u2728\u274C\u274E\u2753-\u2755\u2757\u2795-\u2797\u27B0\u27BF\u2B1B\u2B1C\u2B50\u2B55]|\uD83C[\uDC04\uDCCF\uDD8E\uDD91-\uDD9A\uDDE6-\uDDFF\uDE01\uDE1A\uDE2F\uDE32-\uDE36\uDE38-\uDE3A\uDE50\uDE51\uDF00-\uDF20\uDF2D-\uDF35\uDF37-\uDF7C\uDF7E-\uDF93\uDFA0-\uDFCA\uDFCF-\uDFD3\uDFE0-\uDFF0\uDFF4\uDFF8-\uDFFF]|\uD83D[\uDC00-\uDC3E\uDC40\uDC42-\uDCFC\uDCFF-\uDD3D\uDD4B-\uDD4E\uDD50-\uDD67\uDD7A\uDD95\uDD96\uDDA4\uDDFB-\uDE4F\uDE80-\uDEC5\uDECC\uDED0-\uDED2\uDEEB\uDEEC\uDEF4-\uDEF8]|\uD83E[\uDD10-\uDD3A\uDD3C-\uDD3E\uDD40-\uDD45\uDD47-\uDD4C\uDD50-\uDD6B\uDD80-\uDD97\uDDC0\uDDD0-\uDDE6])|(?:[#\*0-9\xA9\xAE\u203C\u2049\u2122\u2139\u2194-\u2199\u21A9\u21AA\u231A\u231B\u2328\u23CF\u23E9-\u23F3\u23F8-\u23FA\u24C2\u25AA\u25AB\u25B6\u25C0\u25FB-\u25FE\u2600-\u2604\u260E\u2611\u2614\u2615\u2618\u261D\u2620\u2622\u2623\u2626\u262A\u262E\u262F\u2638-\u263A\u2640\u2642\u2648-\u2653\u2660\u2663\u2665\u2666\u2668\u267B\u267F\u2692-\u2697\u2699\u269B\u269C\u26A0\u26A1\u26AA\u26AB\u26B0\u26B1\u26BD\u26BE\u26C4\u26C5\u26C8\u26CE\u26CF\u26D1\u26D3\u26D4\u26E9\u26EA\u26F0-\u26F5\u26F7-\u26FA\u26FD\u2702\u2705\u2708-\u270D\u270F\u2712\u2714\u2716\u271D\u2721\u2728\u2733\u2734\u2744\u2747\u274C\u274E\u2753-\u2755\u2757\u2763\u2764\u2795-\u2797\u27A1\u27B0\u27BF\u2934\u2935\u2B05-\u2B07\u2B1B\u2B1C\u2B50\u2B55\u3030\u303D\u3297\u3299]|\uD83C[\uDC04\uDCCF\uDD70\uDD71\uDD7E\uDD7F\uDD8E\uDD91-\uDD9A\uDDE6-\uDDFF\uDE01\uDE02\uDE1A\uDE2F\uDE32-\uDE3A\uDE50\uDE51\uDF00-\uDF21\uDF24-\uDF93\uDF96\uDF97\uDF99-\uDF9B\uDF9E-\uDFF0\uDFF3-\uDFF5\uDFF7-\uDFFF]|\uD83D[\uDC00-\uDCFD\uDCFF-\uDD3D\uDD49-\uDD4E\uDD50-\uDD67\uDD6F\uDD70\uDD73-\uDD7A\uDD87\uDD8A-\uDD8D\uDD90\uDD95\uDD96\uDDA4\uDDA5\uDDA8\uDDB1\uDDB2\uDDBC\uDDC2-\uDDC4\uDDD1-\uDDD3\uDDDC-\uDDDE\uDDE1\uDDE3\uDDE8\uDDEF\uDDF3\uDDFA-\uDE4F\uDE80-\uDEC5\uDECB-\uDED2\uDEE0-\uDEE5\uDEE9\uDEEB\uDEEC\uDEF0\uDEF3-\uDEF8]|\uD83E[\uDD10-\uDD3A\uDD3C-\uDD3E\uDD40-\uDD45\uDD47-\uDD4C\uDD50-\uDD6B\uDD80-\uDD97\uDDC0\uDDD0-\uDDE6])\uFE0F/g;
var extensionVersion = chrome.runtime.getManifest().version, logger = new Logger;
logger.info("---------------------------------------------"), logger.info(` Extension Version: ${extensionVersion}`), logger.info(`         userAgent: ${navigator.userAgent}`), logger.info(`            Screen: ${screen.width} x ${screen.height}`), logger.info(`        pixelRatio: ${window.devicePixelRatio}`), logger.info(`         Languages: ${navigator.languages.join(", ")}`), logger.info("---------------------------------------------");
var currentTab, resultWindowId, isRedirect = !1, i18n = {
        default: {
            context: {
                image: "Save Image to Eagle (&Z)",
                video: "Save Video to Eagle (&Z)",
                audio: "Save Audio to Eagle (&Z)"
            }
        },
        de: {context: {image: "Bild in Eagle speichern (&Z)", video: "Video in Eagle speichern (&Z)"}},
        es: {context: {image: "Guardar imágen en Eagle (&Z)", video: "Guardar video en Eagle (&Z)"}},
        ru: {context: {image: "Сохранить изображение в Eagle (&Z)", video: "Сохранить видео в Eagle (&Z)"}},
        ko: {context: {image: "이미지를 Eagle에 저장 (&Z)", video: "비디오를 Eagle에 저장 (&Z)"}},
        ja: {context: {image: "画像を Eagle に保存 (&Z)", video: "映像を Eagle に保存 (&Z)"}},
        "zh-TW": {
            context: {
                image: "將圖片儲存至 Eagle (&Z)",
                video: "將影片儲存至 Eagle (&Z)",
                audio: "將音訊儲存至 Eagle (&Z)"
            }
        },
        "zh-CN": {
            context: {
                image: "将图片保存至 Eagle (&Z)",
                video: "将视频保存至 Eagle (&Z)",
                audio: "將音訊儲存至 Eagle (&Z)"
            }
        }
    }, i18n_words = i18n.default, languageMap = {zh: "zh-CN", es: "es", de: "de", ru: "ru", ja: "ja", ko: "ko", en: "en"},
    language = window.navigator.language.toLowerCase(), languageKey = language.split("-")[0],
    i18nKey = languageMap[languageKey];

function saveUrlToEagle(u, D) {
    chrome.tabs.query({active: !0, currentWindow: !0}, function (e) {
        var t = e[0];
        logger.info(`[background] saveUrl, url[${t.url}] base64[${D.length}]`);
        let a = {
            method: "POST",
            url: "http://localhost:41593",
            data: {
                version: extensionVersion,
                type: "save-url",
                title: t.title.replace(/[#$%^&*()<>:'"\/\\|?*]+/g, ""),
                url: t.url,
                width: parseInt(u.videoWidth),
                height: parseInt(u.videoHeight),
                medium: u.videoMedium,
                videoID: u.videoID,
                videoEmbed: u.videoEmbed,
                videoDuration: u.videoDuration,
                base64: D,
                favicon: u.favicon
            }
        }, o = window.jQuery.extend(!0, {}, a);
        delete o.data.base64, logger.info(`[background] ${JSON.stringify(o)}`), window.jQuery.ajax(a).always(function (u) {
            0 == u.status && (logger.error(`[background] saveUrl, url[${t.url}] fail, eagle not open.`), openEagle())
        })
    })
}

function saveToEagle(u) {
    let D = {
        method: "POST",
        url: "http://localhost:41593",
        timeout: 2e4,
        data: {
            version: extensionVersion,
            type: "image",
            title: u.title,
            url: u.url,
            src: u.src,
            folderID: u.folderID,
            extendTags: u.extendTags || "",
            metaAlt: u.metaAlt,
            metaTitle: u.metaTitle,
            metaDescription: u.metaDescription,
            metaKeywords: u.metaKeywords,
            metaTags: u.metaTags,
            forceOpenCollectModal: u.forceOpenCollectModal,
            forceHideCollectModal: u.forceHideCollectModal
        }
    }, e = window.jQuery.extend(!0, {}, D);
    delete e.data.src, logger.info(`[background] ${JSON.stringify(e)}`), window.jQuery.ajax(D).always(function (u) {
        0 == u.status && openEagle()
    })
}

"zh-tw" === language ? i18n_words = i18n["zh-TW"] : i18n[i18nKey] && (i18n_words = i18n[i18nKey]), chrome.contextMenus.create({
    contexts: ["all"],
    id: "background_img",
    title: i18n_words.context.image
});
var isBackground = !0, captureFormat = "png", useRetina = !1;

function updateConfig() {
    logger.info("[background] config init"), captureFormat = localStorage.getItem("captureFormat") || "png", chrome.storage.sync.get(["captureFormat"], function (u) {
        u && u.captureFormat && (captureFormat = u.captureFormat, logger.info(`[background] config captureFormat[${captureFormat}]`))
    }), useRetina = "true" === localStorage.getItem("useRetina"), chrome.storage.sync.get(["useRetina"], function (u) {
        useRetina = !0 === u.useRetina, logger.info(`[background] config useRetina[${useRetina}]`)
    }), chrome.storage.sync.get(["useDelay", "captureDelay"], function (u) {
        logger.info(`[background] config useDelay[${u.useDelay}]`), logger.info(`[background] config captureDelay[${u.captureDelay}]`)
    }), chrome.storage.sync.get(["dragMode"], function (u) {
        logger.info(`[background] config dragMode[${u.dragMode}]`)
    })
}

function getFilename(u) {
    var D = u.split("?")[0].split("#")[0];
    return "screencapture" + (D = D ? "-" + (D = D.replace(/^https?:\/\//, "").replace(/[^A-z0-9]+/g, "-").replace(/-+/g, "-").replace(/^[_\-]+/, "").replace(/[_\-]+$/, "")) : "") + "-" + Date.now() + ".png"
}

function finishCaptures(u, D) {
    var e = "";
    D && (e = " #" + D);
    let t = {
        type: "POST",
        url: "http://localhost:41593",
        data: {
            version: extensionVersion,
            type: "screen capture",
            title: currentTab.title.replace(/[#$%^&*()<>:'"\/\\|?*]+/g, "").substr(0, 65) + e,
            url: currentTab.url,
            base64: u
        }
    }, a = window.jQuery.extend(!0, {}, t);
    delete a.data.base64, logger.info(`[background] ${JSON.stringify(a)}`), chrome.tabs.query({
        active: !0,
        currentWindow: !0
    }, function (u) {
        window.jQuery.ajax(t).always(function (u) {
            0 == u.status ? openEagle() : chrome.tabs.sendMessage(currentTab.id, {action: "close-crop-area"}, function (u) {
            })
        })
    })
}

function errorHandler(u) {
}

function openWebCapture() {
    logger.info("[background] openWebCapture, called"), ensureEagleIsOpen(function () {
        let u = {
            method: "POST",
            url: "http://localhost:41593",
            data: {
                version: extensionVersion,
                type: "capture-website",
                title: currentTab.title.replace(/[#$%^&*()<>:'"\/\\|?*]+/g, ""),
                url: currentTab.url
            }
        }, D = window.jQuery.extend(!0, {}, u);
        logger.info(`[background] ${JSON.stringify(D)}`), chrome.tabs.query({active: !0}, function (D) {
            window.jQuery.ajax(u).always(function (u) {
                0 == u.status && openEagle()
            })
        })
    })
}

function openSceenshot() {
    logger.info("[background] openSceenshot, called");
    let u = {
        method: "POST",
        url: "http://localhost:41593",
        data: {
            version: extensionVersion,
            type: "screenshot",
            title: currentTab.title.replace(/[#$%^&*()<>:'"\/\\|?*]+/g, ""),
            url: currentTab.url
        }
    }, D = window.jQuery.extend(!0, {}, u);
    logger.info(`[background] ${JSON.stringify(D)}`), chrome.tabs.query({active: !0}, function (D) {
        window.jQuery.ajax(u).always(function (u) {
            0 == u.status && openEagle()
        })
    })
}

function toDataURL(u, D, e) {
    var t = new XMLHttpRequest, a = !1,
        o = u.toLowerCase().indexOf(".mp4") > -1 || u.toLowerCase().indexOf(".webm") > -1,
        n = u.toLowerCase().indexOf(".gif") > -1, r = 700;
    n && (r = 1200), e && (r = e), o ? D(void 0) : (t.onload = function () {
        clearTimeout(r);
        var u = new FileReader;
        u.onloadend = function () {
            a || (a = !0, u.result.indexOf("data:image") > -1 ? D(u.result) : D(void 0))
        }, u.readAsDataURL(t.response)
    }, t.open("GET", u), t.responseType = "blob", t.send(), r = setTimeout(function () {
        a || (a = !0, t.abort(), D(void 0))
    }, r))
}

function openEagle() {
    chrome.tabs.query({active: !0, currentWindow: !0}, function (u) {
        chrome.tabs.sendMessage(u[0].id, {action: "open-not-opened-modal"}, function (u) {
        })
    })
}

function executeScripts(u, D) {
    function e(u, D, e) {
        return function () {
            chrome.tabs.executeScript(u, D, e)
        }
    }

    for (var t = null, a = D.length - 1; a >= 0; --a) t = e(u, D[a], t);
    null !== t && t()
}

function ensureEagleIsOpen(u) {
    window.jQuery.ajax({
        version: extensionVersion,
        type: "GET",
        url: "http://localhost:41593",
        timeout: 500,
        success: function (u) {
        }
    }).always(function (D) {
        0 == D.status ? (logger.warn("[background] eagle is not open, prompt the user to open."), openEagle()) : u()
    })
}

function openLogger() {
    logger.info("[background] openLogger() called."), chrome.tabs.create({url: chrome.runtime.getURL("logger/logger.html")})
}

updateConfig(), chrome.contextMenus.onClicked.addListener(function (u, D) {
    chrome.tabs.sendMessage(D.id, {action: "context-save"}, function (u) {
    })
}), chrome.runtime.onMessage.addListener(function (u, D, e) {
    if ("capture" == u.msg) doCapture(u, D, e); else if ("getSource" == u.action) ensureEagleIsOpen(function () {
        chrome.tabs.query({active: !0, currentWindow: !0}, function (D) {
            var e = JSON.parse(u.source);
            e.forEach(function (u) {
                u.title || (u.title = D[0].title), u.title = u.title.replace(emojiRegex, "").replace(/[#$%^&*()<>:'"\/\\|?*]+/g, ""), u.src && u.src.indexOf(" ") > -1 && (u.src = u.src.trim().split(" ")[0])
            });
            var t = e.map(function (u) {
                return UrlHelper.toLargeUrlAsync(u.src)
            });
            Promise.all(t).then(u => {
                e.forEach(function (D, e) {
                    let t = u[e];
                    D.src !== t && (D.urlConverted = !0, D.src = t || D.src)
                }), logger.info(`[background] batch save[${D[0].url}] count[${e.length}]`);
                let t = {
                    method: "POST",
                    url: "http://localhost:41593",
                    data: {
                        version: extensionVersion,
                        type: "import-images",
                        title: D[0].title.replace(/[#$%^&*()<>:'"\/\\|?*]+/g, ""),
                        url: D[0].url,
                        images: JSON.stringify(e)
                    }
                }, a = window.jQuery.extend(!0, {}, t);
                delete a.data.images, logger.info(`[background] ${JSON.stringify(a)}`), window.jQuery.ajax(t).always(function (u) {
                    0 == u.status && (logger.info("[background] getSource, eagle is not open."), openEagle())
                })
            })
        })
    }), e(); else if ("saveURL" == u.action) {
        logger.info("[background] saveURL, called.");
        var t = u.result;
        t.videoThumb ? toDataURL(t.videoThumb, function (u) {
            saveUrlToEagle(t, u)
        }, 1e4) : chrome.tabs.query({active: !0, currentWindow: !0}, function (u) {
            var D = u[0], e = D.windowId;
            chrome.tabs.executeScript(D.id, {code: 'document.getElementsByTagName("html")[0].classList.add("eagle-cropper-opened")'}), setTimeout(function () {
                chrome.tabs.captureVisibleTab(e, {format: "jpeg", quality: 75}, function (u) {
                    var e = new Image;
                    e.onload = function () {
                        var u = document.createElement("canvas");
                        e.width > 720 ? (u.width = 720, u.height = parseInt(720 * e.height / e.width)) : (u.width = e.width, u.height = e.height), u.getContext("2d").drawImage(e, 0, 0, u.width, u.height);
                        var a = u.toDataURL();
                        logger.info(`saveURL, base64[${a.length}]`), saveUrlToEagle(t, a), chrome.tabs.executeScript(D.id, {code: 'document.getElementsByTagName("html")[0].classList.remove("eagle-cropper-opened")'})
                    }, e.src = u
                })
            }, 200)
        }), e()
    } else if ("cache" == u.action) UrlHelper.toLargeUrlAsync(u.src).then(function (u) {
        u && u.startsWith("data:image/png;base64") ? logger.info(`[background] cache called, prefetchUrl: base64[${u.length}]`) : logger.info(`[background] cache called, prefetchUrl: src[${u}]`), CacheHelper.prefetchUrl(u)
    }), e(); else if ("ensure-eagle-open" === u.action) window.jQuery.ajax({
        type: "GET",
        url: "http://localhost:41593",
        timeout: 500,
        success: function (u) {
        }
    }).fail(function () {
        logger.info("[background] ensure-eagle-open, fail")
    }).always(function (u) {
        0 == u.status ? (logger.info("[background] ensure-eagle-open, eagle not open"), e(!1)) : e(!0)
    }); else if ("getAppInfo" == u.message) fetch("http://localhost:41595/api/application/info", {redirect: "manual"}).then(function (u) {
        return "opaqueredirect" == u.type && (isRedirect = !0, logger.info("[background] getAppInfo, fail. localhost get redirect")), u.json()
    }).then(function (u) {
        u && u.data && chrome.tabs.query({active: !0, currentWindow: !0}, function (D) {
            chrome.tabs.sendMessage(D[0].id, {action: "get-app-info", data: u.data})
        })
    }).catch(function () {
        logger.error("[background] getAppInfo, error.")
    }), e(); else if ("getRecentFolders" == u.message) fetch("http://localhost:41595/api/folder/listRecent", {redirect: "manual"}).then(function (u) {
        return "opaqueredirect" == u.type && (isRedirect = !0, logger.info("[background] getRecentFolders, fail. localhost get redirect")), u.json()
    }).then(function (u) {
        u && u.data && chrome.tabs.query({active: !0, currentWindow: !0}, function (D) {
            chrome.tabs.sendMessage(D[0].id, {action: "get-recent-folder", data: u.data})
        })
    }).catch(function () {
        logger.info("[background] getRecentFolders, fail.")
    }), e(); else if ("updateContextMenu" == u.message) "video" === u.type ? chrome.contextMenus.update("background_img", {
        visible: !0,
        contexts: ["all"],
        title: i18n_words.context.video
    }) : "audio" === u.type ? chrome.contextMenus.update("background_img", {
        visible: !0,
        contexts: ["all"],
        title: i18n_words.context.audio
    }) : "image" === u.type ? chrome.contextMenus.update("background_img", {
        visible: !0,
        contexts: ["all"],
        title: i18n_words.context.image
    }) : chrome.contextMenus.update("background_img", {
        visible: !1,
        contexts: ["all"],
        title: i18n_words.context.image
    }), e(); else if ("screencapture" === u.action) {
        var a = u.pixelRatio || 1, o = u.rect;
        chrome.tabs.query({active: !0, currentWindow: !0}, function (D) {
            var e = D[0];
            currentTab = e;
            var t = getFilename(e.url);
            webCapture(e, t, finishCaptures, errorHandler, a), u.rect ? logger.info(`[background] capture area[${e.url}] x[${o.x}] y[${o.y}] w[${o.width}] h[${o.height}] pixelRatio[${a}]`) : logger.info(`[background] capture fullpage[${e.url}] w[${e.width}] h[${e.height}] pixelRatio[${a}]`)
        }), e()
    } else if ("capture-visible" === u.action) {
        a = u.pixelRatio || 1;
        chrome.tabs.query({active: !0, currentWindow: !0}, function (u) {
            var D = u[0];
            currentTab = D;
            u[0] && u[0].id || currentTab.id;
            var e = D.windowId, t = captureFormat || "png";
            chrome.tabs.executeScript(currentTab.id, {code: 'document.getElementsByTagName("html")[0].classList.add("eagle-cropper-opened")'}), setTimeout(function () {
                chrome.tabs.captureVisibleTab(e, {format: t, quality: 100}, function (u) {
                    if (chrome.tabs.executeScript(currentTab.id, {code: 'document.getElementsByTagName("html")[0].classList.remove("eagle-cropper-opened")'}), u) if (a > 1 && !useRetina) {
                        var e = new Image;
                        e.onload = function () {
                            var u, o = document.createElement("canvas");
                            o.width = e.width, o.height = e.height, o.getContext("2d").drawImage(e, 0, 0), o = resizeCanvas(o, a), u = "png" === t ? o.toDataURL() : o.toDataURL("image/jpeg", 1), logger.info(`[background] capture visible[${D.url}] w[${D.width}] h[${D.height}] format[${t}] base64[${u.length}]`), finishCaptures(u)
                        }, e.src = u
                    } else logger.info(`[background] capture visible[${D.url}] w[${D.width}] h[${D.height}] format[${t}] dataURI[${u.length}]`), finishCaptures(u)
                })
            }, 200)
        }), e()
    } else "batch-save" === u.action ? (chrome.tabs.executeScript(null, {file: "/js/utils/getPagesSource.js"}), e()) : "change-retina" === u.action ? (logger.info("background change-retina, called."), useRetina = "true" === u.useRetina || !0 === u.useRetina || !1, e()) : "change-capture-format" === u.action ? (logger.info("background change-capture-format, called."), captureFormat = u.captureFormat || "png", e()) : "update-config" === u.action ? (updateConfig(), e()) : "save-url" === u.action ? (logger.info("background save-url, called."), chrome.tabs.executeScript(null, {file: "/js/utils/saveURL.js"}), e()) : "save-board" === u.action ? (ensureEagleIsOpen(function () {
        logger.info("background save-board, called."), chrome.tabs.executeScript(null, {file: "/js/utils/saveBoard.js"})
    }), e()) : "log" === u.action ? "INFO" == u.level ? logger.info(u.descriptor, u.data) : "ERROR" == u.level ? logger.error(u.descriptor, u.data) : "CRITICAL" == u.level ? logger.critical(u.descriptor, u.data) : logger.warn(u.descriptor, u.data) : u.src && (chrome.tabs.query({
        active: !0,
        currentWindow: !0
    }, function (D) {

        if (u.link && u.link.includes("pixiv.net")) {
            const fetchPromise = fetch(u.link)
                .then(response => {
                    return response.text();
                }).then(html => {
                    var parser = new DOMParser();
                    var doc = parser.parseFromString(html, "text/html");
                    let a = doc.querySelector('meta#meta-preload-data').getAttribute("content");
                    let jdata = JSON.parse(a);
                    let imageID = u.link.split("/")[4];
                    let url = jdata["illust"][imageID]["urls"]["original"];

                    const urlToBase64 = (url) => fetch(url, {
                        headers: {
                            "referer": u.link
                        }
                    })
                        .then(response => response.blob())
                        .then(blob => new Promise((resolve, reject) => {
                            const reader = new FileReader()
                            reader.onloadend = () => resolve(reader.result)
                            reader.onerror = reject
                            reader.readAsDataURL(blob)
                        }));
                    for (let i = 0; i < jdata["illust"][imageID]["pageCount"]; i++) {
                        urlToBase64(url.replace("_p0", `_p${i}`))
                            .then(dataUrl => {
                                let a = {
                                    version: extensionVersion,
                                    type: "image",
                                    title: "",
                                    url: u.link || D[0].url,
                                    src: dataUrl,
                                    folderID: u.folderID,
                                    extendTags: u.extendTags || "",
                                    metaAlt: u.metaAlt,
                                    metaTitle: u.metaTitle,
                                    metaDescription: u.metaDescription,
                                    metaKeywords: u.metaKeywords,
                                    metaTags: u.metaTags
                                };
                                u.title && (a.title = u.title.replace(/[#$%^&*()<>:'"\/\\|?*]+/g, "")), u.forceOpenCollectModal && (a.forceOpenCollectModal = !0), u.forceHideCollectModal && (a.forceHideCollectModal = !0), null != t && null != t ? logger.info(`[background] save base64[${t.length}]`) : logger.info(`[background] save src [${e}]`), saveToEagle(a)
                            });
                    }
                });
        } else {
            console.log("DOWNLOAD ELSE")
            UrlHelper.toLargeUrlAsync(u.src).then(function (e) {
                // t => original
                // e => thumnbail
                e && CacheHelper.getBase64Url(e).then(function (t) {
                    let a = {
                        version: extensionVersion,
                        type: "image",
                        title: "",
                        url: u.link || D[0].url,
                        src: t || e,
                        folderID: u.folderID,
                        extendTags: u.extendTags || "",
                        metaAlt: u.metaAlt,
                        metaTitle: u.metaTitle,
                        metaDescription: u.metaDescription,
                        metaKeywords: u.metaKeywords,
                        metaTags: u.metaTags
                    };
                    u.title && (a.title = u.title.replace(/[#$%^&*()<>:'"\/\\|?*]+/g, "")), u.forceOpenCollectModal && (a.forceOpenCollectModal = !0), u.forceHideCollectModal && (a.forceHideCollectModal = !0), null != t && null != t ? logger.info(`[background] save base64[${t.length}]`) : logger.info(`[background] save src [${e}]`), saveToEagle(a)
                });
            });
        }
    }), e());
    return !0
}), chrome.runtime.onConnect.addListener(() => {
});