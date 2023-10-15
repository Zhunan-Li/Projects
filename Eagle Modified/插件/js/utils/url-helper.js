class UrlHelper {
    static smartToDataURL(e) {
        let t = this.testLargeUrl(e);
        return this.promiseToDataUrl(t)
    }

    static toDataURL(e, t, r = 300) {
        console.log("準備下載");
        let n, o = new AbortController, a = o.signal, l = !1;
        e.toLowerCase().indexOf(".mp4") > -1 || e.toLowerCase().indexOf(".webm") > -1 ? t(void 0) : (fetch(e, {
            cache: "force-cache",
            mode: "cors",
            signal: a
        }).then(function (e) {
            return e.ok ? e.blob() : Promise.reject(e)
        }).then(function (e) {
            console.log("建立FileReader");
            let r = new FileReader;
            r.onloadend = function () {
                clearTimeout(n), l || (l = !0, r.result.indexOf("data:image") > -1 ? (console.log("使用快取"), t(r.result)) : (console.log("不使用快取"), t(void 0)))
            }, r.readAsDataURL(e)
        }).catch(function (e) {
            clearTimeout(n), console.log("因錯誤取消下載"), t(void 0)
        }), n = setTimeout(function () {
            l || (l = !0, o.abort(), console.log("因超時取消下載"), t(void 0))
        }, r))
    }

    static toLargeUrl(e) {
        return e ? e.indexOf("data:image") > -1 ? e : this.testLargeUrl(e) : e
    }

    static async toLargeUrlAsync(e) {
        return new Promise(t => {
            var r = !1, n = function (e) {
                if (!r) return r = !0, t(e)
            };
            return e ? e.indexOf("data:image") > -1 ? n(e) : (this.testLargeUrlAsync(e).then(function (e) {
                return n(e)
            }), void setTimeout(function () {
                return n(e)
            }, 1e3)) : n(e)
        })
    }

    static testLargeUrl(e) {
        if (SIEVE && SIEVE.rules) {
            for (let t = 0; t < SIEVE.rules.length; t++) try {
                const r = SIEVE.rules[t];
                let n = r.srcPattern;
                r.replaceRule;
                if (RegExp(n).test(e)) return r.replace(e)
            } catch (t) {
                return e
            }
            return e
        }
    }

    static async testLargeUrlAsync(e) {
        return new Promise(t => {
            if (SIEVE && SIEVE.rules) {
                var r = !1;
                for (let n = 0; n < SIEVE.rules.length; n++) try {
                    const o = SIEVE.rules[n];
                    let a = o.srcPattern;
                    o.replaceRule;
                    if (RegExp(a).test(e)) {
                        if (r = !0, o.replace) return t(o.replace(e));
                        if (!o.replaceAsync) return t(e);
                        o.replaceAsync(e).then(function (e) {
                            return t(e)
                        });
                        break
                    }
                } catch (r) {
                    return t(e)
                }
                if (!r) return t(e)
            }
        })
    }

    static parseJwt(e) {
        var t = e.split(".")[1].replace(/-/g, "+").replace(/_/g, "/"),
            r = decodeURIComponent(atob(t).split("").map(function (e) {
                return "%" + ("00" + e.charCodeAt(0).toString(16)).slice(-2)
            }).join(""));
        return JSON.parse(r)
    }
}