let prefetchCache = {}, releaseCacheTimeout = {};

class CacheHelper {
    static isImage(e) {
        let r = e.toLowerCase(), c = [".mp4", ".webm"];
        for (let e = 0; e < c.length; e++) {
            var a = c[e];
            if (r.includes(a)) return logger.debug("[background] cache isImage, not an image, don't download."), !1
        }
        return !0
    }

    static prefetchUrl(e) {
        void 0 !== e && this.isImage(e) && void 0 === prefetchCache[e] && (e.startsWith("data:image/") || (prefetchCache[e] = {
            count: 20,
            done: !1,
            progress: 0,
            base64: void 0,
            xhr: void 0
        }, clearTimeout(releaseCacheTimeout[e]), prefetchCache[e].xhr = this.progressFetch(e, function (r) {
            prefetchCache[e].progress = r
        }, function (r, c) {
            c && (prefetchCache[e].base64 = c, prefetchCache[e].done = !0), releaseCacheTimeout[e] = setTimeout(function () {
                prefetchCache[e] && (delete prefetchCache[e], logger.debug(`[background] cache 30s releaseCache, url: ${e}`))
            }, 3e4)
        })))
    }

    static progressFetch(e, r, c) {
        const a = new XMLHttpRequest;
        let o = !1, t = 0;
        return a.onload = (() => {
            logger.debug("[background] cache progressFetch, download completed.");
            let e = new FileReader;
            e.onloadend = function () {
                logger.debug("[background] cache progressFetch, saveing to cache..."), o || (o = !0, e.result.indexOf("data:image") > -1 ? (logger.debug(`[background] cache progressFetch, complateCallback(base64[${e.result.length}])`), c(void 0, e.result)) : (logger.debug("[background] cache progressFetch, complateCallback(undefined)"), c(void 0)))
            }, e.readAsDataURL(a.response)
        }), a.onerror = (() => {
            logger.debug("[background] cache download error")
        }), a.onabort = (() => {
            logger.debug("[background] cache download abort")
        }), a.onprogress = (c => {
            var o = a.getResponseHeader("Content-Type");
            if (!o.startsWith("image")) return logger.debug(`[background] cache onprogress, not an image: ${o}.`), void a.abort();
            if (c.total && c.total > 0) {
                let a = c.loaded / c.total * 100;
                if (a > t) {
                    prefetchCache[e].count += (a - t) / 15, logger.debug(`[background] prefetchCache[url].count: ${prefetchCache[e].count}`)
                }
                t = a, logger.debug(`[background] cache download precent: ${a}%`), r(a)
            }
        }), a.responseType = "blob", a.open("GET", e), a.send(), logger.debug("[background] cache prepareing..."), a
    }

    static getBase64Url(e) {
        return new Promise((r, c) => {
            let a, o = 0, t = c => {
                clearInterval(a), r(c), delete prefetchCache[e], c ? logger.debug(`[background] cache getBase64Url, download from base64(${c.length})`) : logger.debug(`[background] cache getBase64Url, download from url(${e})`)
            }, g = () => {
                prefetchCache[e] ? (logger.debug(`[background] the cache exist, url[${e}]`), !0 === prefetchCache[e].done && prefetchCache[e].base64 ? (logger.debug(`[background] use exist cache, url[${e}]`), t(prefetchCache[e].base64)) : prefetchCache[e].progress && prefetchCache[e].progress > 0 ? (logger.debug(`[background] cache is downloading and progress > 0, progress[${prefetchCache[e].progress}]`), clearInterval(a), a = setInterval(function () {
                    if (logger.debug(`[background] cache downloading, progress[${prefetchCache[e].progress}]`), !0 === prefetchCache[e].done && prefetchCache[e].base64) return logger.debug("[background] cache download completed"), t(prefetchCache[e].base64);
                    prefetchCache[e].count--, prefetchCache[e].count <= 0 && (logger.debug("[background] cache download timeout, cancel download..."), prefetchCache[e].xhr.abort(), t())
                }, 100)) : o <= 10 ? (o++, setTimeout(function () {
                    logger.debug(`[background] cache download is not start, retry... count[${o}]`), g()
                }, 50)) : (logger.debug("[background] cache download timeout, abort..."), prefetchCache[e].xhr.abort(), t())) : (logger.debug("[background] cache not exist"), t())
            };
            g()
        })
    }
}