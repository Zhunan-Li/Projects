var SIEVE = {
    version: 20200828,
    rules: [{
        site: "reddit", srcPattern: /(.*)\/\/preview\.redd\.it\/(.*)\.(.*)/, replace: function (e) {
            return e.replace(/preview/, "i").replace(/\?(.*)/, "")
        }
    }, {
        site: "deviantart",
        srcPattern: /https:\/\/images\S+\.wixmp\.com\/f\/\S+\/v1\/fill\/\S+\?token\S+/,
        replace: function (e) {
            let t = new URL(e).searchParams.get("token");
            if (!t) return e;
            let r = UrlHelper.parseJwt(t);
            if (r.obj && r.obj[0] && r.obj[0][0] && r.obj[0][0].height) {
                let t = r.obj[0][0].height.replace("<=", ""), c = r.obj[0][0].width.replace("<=", "");
                return e.replace(/\/w_\d+,h_\d+,q_\d+/, `/w_${c},h_${t},q_100`)
            }
            return e
        }
    }, {
        site: "behance", srcPattern: /(.*)behance.net\/project_modules\/(.*)/, replace: function (e) {
            let t = "/source/";
            return e.replace("/max_1200/", t).replace("/2800_opt_1/", t).replace("/disp/", t).replace("/1400_opt_1/", t)
        }
    }, {
        site: "imgur", srcPattern: /(.*)i\.imgur\.com\/(.*)/, replace: function (e) {
            return e.replace(/\?(.*)/, "?maxwidth=99999")
        }
    }, {
        site: "twitter", srcPattern: /(.*)twimg.com(.*)name=(.*)/, replace: function (e) {
            return e.replace(/name=(.*)/, "name=orig")
        }
    }, {
        site: "meiye", srcPattern: "(.*)image.meiye.art/(.*)", replace: function (e) {
            return e.split("?imageMogr2")[0].split("?vframe")[0]
        }
    }, {
        site: "花瓣网 (新版)", srcPattern: "(.*)hbimg.huaban.com/(.*)", replace: function (e) {
            return e.replace(/_\/fw(.*)/, "").replace(/_sq\d+\/format(.*)/, "").split("/format/")[0].replace(/_sq235$/, "").replace(/_sq75$/, "").replace(/_sq(.*)$/, "").replace(/_fw[\d]+[w]*$/, "").split("_fw")[0].split("/fw/")[0]
        }
    }, {
        site: "花瓣网 (舊版)", srcPattern: "//hbimg[.]*/*", replace: function (e) {
            return e.split("/format/")[0].replace(/_sq235$/, "").replace(/_sq75$/, "").replace(/_fw[\d]+[w]*$/, "").split("_fw")[0].split("/fw/")[0]
        }
    }, {
        site: "大作", srcPattern: "(.*)bigurl(.*)", replace: function (e) {
            return e.replace("pc_236_webp_2x", "pc_680_webp").replace("pc_236_webp", "pc_680_webp")
        }
    }, {
        site: "Lapa.ninja", srcPattern: "(.*)cdn.lapaninja.com(.*)", replace: function (e) {
            return e.replace("-thumb.jpg", ".jpg")
        }
    }, {
        site: "Dribbble", srcPattern: "https?://cdn.dribbble.com/*", replace: function (e) {
            if (e.includes("userupload")) {
                return e.split("?")[0]
            }
            if (e.includes("screenshots") && e.includes("media")) {
                return e.split("?")[0]
            }
            return e.includes("screenshots") && e.includes(".gif") ? e.split("?")[0].replace("_4x", "") : e.includes("/videos/") ? e.replace("_large_preview", "") : -1 === e.indexOf("/attachments/") ? e.replace(/_1x/g, "").replace(/_teaser/g, "") : e
        }
    }, {
        site: "Pexels", srcPattern: "https?://images.pexels.com/*", replace: function (e) {
            return e.split("?")[0] + "?auto=compress"
        }
    }, {
        site: "新浪微博", srcPattern: /(.*)\/\/.*[.]sinaimg[.]cn\/([a-z]*)(\d+)\//, replace: function (e) {
            return e.includes(".mp4") ? e : e.replace(/(cn)\/([a-z]*)(\d+)\//, "cn/large/")
        }
    }, {
        site: "QQ相册/空间", srcPattern: "https?://.*[.]photo[.]store[.]qq[.]com/.*", replace: function (e) {
            return e.replace(/[/]m[/]/, "/o/").replace(/&w=[\d]+&h=[\d]+/, "")
        }
    }, {
        site: "QQ相册2", srcPattern: "https?://group[.]store[.]qq[.]com/.*", replace: function (e) {
            return e.replace(/[/]400$/, "/800").replace(/[/]200$/, "/800").replace(/[/]100$/, "/800")
        }
    }, {
        site: "百度贴吧", srcPattern: "https?://tiebapic[.]baidu[.]com/forum.*", replace: function (e) {
            var t = e.match(/^(http:\/\/tiebapic\.baidu\.com\/forum\/)ab(pic\/item\/[\w.]+)/i);
            if (/\/sys\/portrait/.test(e)) return e.replace(/\/sys\/portrait/, "/sys/portraitl");
            if (t) return t[1] + t[2];
            var r = e.match(/\/sign=\w+\/([\w.]+)$/);
            return r ? "http://tiebapic.baidu.com/forum/pic/item/" + r[1] : e
        }
    }, {
        site: "百度图趣", srcPattern: "https?://hiphotos[.]baidu[.]com/.*", replace: function (e) {
            return e.replace(/[/]abpic[/]/, "/pic/").replace(/[/]pin[/]w=[\d]+[/].+[/]/, "/pin/pic/item/")
        }
    }, {
        site: "豆瓣相册", srcPattern: /https?:\/\/img[\d]*\.doubanio\.com\/.*/, replace: function (e) {
            return e.replace(/[/]s[/]/, "/orginal/").replace(/[/]m[/]/, "/orginal/").replace(/[/]l[/]/, "/orginal/").replace(/[/]sqs[/]/, "/orginal/")
        }
    }, {
        site: "Flickr", srcPattern: ".*[.]staticflickr[.]com.*", replace: function (e) {
            return e.replace(/_[nms]\.jpg$/, "_b.jpg")
        }
    }, {
        site: "poco.cn", srcPattern: "http?://img[d].*pocoimg*", replace: function (e) {
            return e.replace(/_\d{3}.jpg$/, ".jpg")
        }
    }, {
        site: "蘑菇街", srcPattern: "https?://.*[.]mogucdn[.]com/.*.jpg", replace: function (e) {
            return e.replace(/_[\d]{3}x[\d]+.jpg$/, "_468x468.jpg").split(".jpg")[0] + ".jpg"
        }
    }, {
        site: "pinterest", srcPattern: "https?://.*[.]pinimg[.]com/.*.jpg", replaceAsync: async function (e) {
            return new Promise(t => {
                if (e.includes("75x75_RS")) return t(e);
                let r = e.replace(/[0-9]+x/g, "originals");
                if (!r.includes(".jpg") && !r.includes(".png")) return t(r);
                {
                    const s = new AbortController;
                    setTimeout(() => s.abort(), 1e3);
                    var c = fetch(r.replace(".jpg", ".png"), {method: "HEAD", signal: s.signal}),
                        a = fetch(r.replace(".png", ".jpg"), {method: "HEAD", signal: s.signal});
                    Promise.all([c, a]).then(r => 200 === r[0].status ? t(r[0].url) : 200 === r[1].status ? t(r[1].url) : t(e))
                }
            })
        }
    }, {
        site: "pixiv", srcPattern: /(.*)\/\/i\.pximg\.net\/(.*)/, replaceAsync: async function (e) {
            return new Promise(t => {
                let r = e;
                if (!(r = (r = (r = (r = r.replace(/c\/\d+x\d+_\d+(\_[A-z0-9]{2}){0,1}\//, "")).replace(/img-master/, "img-original")).replace(/custom-thumb/, "img-original")).replace(/_(?<=_p\d_)(.*)1200/, "")).includes(".jpg") && !r.includes(".png")) return t(r);
                {
                    const s = new AbortController;
                    setTimeout(() => {
                        s.abort();
                    }, 1e3);
                    var c = fetch(r.replace(".jpg", ".png"), {method: "HEAD", signal: s.signal, mode: "no-cors"}),
                        a = fetch(r.replace(".png", ".jpg"), {method: "HEAD", signal: s.signal, mode: "no-cors"});
                    Promise.all([c, a]).then(r => 200 === r[0].status ? t(r[0].url) : 200 === r[1].status ? t(r[1].url) : t(e))
                }
            })
        }
    }, {
        site: "1688", srcPattern: "https?://cbu01.alicdn.com/img/ibank/.*..*x.*.jpg", replace: function (e) {
            return e.replace(/\.\d+x.*\./, ".")
        }
    }, {
        site: "淘宝", srcPattern: /.(?:taobao|tb|ali)cdn(.+)_\d+x\d+.jpg(.*)/, replace: function (e) {
            return e.replace(/_\d+x\d+.jpg(_.webp)?/, "")
        }
    }, {
        site: "天猫", srcPattern: /.(?:taobao|tb|ali)cdn(.+)_\d+x\d+\S\d+.jpg(.*)/, replace: function (e) {
            return e.replace(/_\d+x\d+\S\d+.jpg(_.webp)?/, "")
        }
    }, {
        site: "Amazon",
        srcPattern: "https://(images-na.ssl-images|m.media)-amazon.com/images/(.*)",
        replace: function (e) {
            return e.replace(/\.\_\S+\./, ".")
        }
    }, {
        site: "京东", srcPattern: "https://.*.360buyimg.com.*", replace: function (e) {
            return e.replace(/\/n\d+\//, "/n0/").replace(/s\d+x\d+_?/, "").split("!cc")[0].split("!q")[0].replace(/.jpg.avif/, ".jpg")
        }
    }, {
        site: "officesnapshots wordpress",
        srcPattern: "https://officesnapshots.com/wp-content/uploads/(.*)",
        replace: function (e) {
            return e.replace(/-\d+x\d+-(.*)\./, ".").split("?w=")[0]
        }
    }, {
        site: "Houzz", srcPattern: "https://st[.]hzcdn[.]com/.*", replace: function (e) {
            return e.replace(/fimgs/, "simgs").replace(/_/, "_14-")
        }
    }, {
        site: "HouseBeautiful", srcPattern: "https://hips[.]hearstapps[.]com/.*", replace: function (e) {
            return e.replace(/[.]jpg{1,}/, ".jpg").split("&resize=")[0]
        }
    }, {
        site: "Officesnapshots", srcPattern: "https://officesnapshots[.]com/.*", replace: function (e) {
            return e.replace(/-\d{3,4}x\d{3,4}/, "")
        }
    }, {
        site: "Archilovers", srcPattern: "https?://img[.]archilovers[.]com/.*", replace: function (e) {
            return e.replace(/[a-z]_[0-9][0-9][0-9]_/, "").split("?w=")[0]
        }
    }, {
        site: "AD", srcPattern: "https://media[.]architecturaldigest[.]com/.*", replace: function (e) {
            return e.replace(/w_\d+/, "w_5000").replace(/,h_\d+/, "")
        }
    }, {
        site: "Archdaily 中文版", srcPattern: "https?://images[.]adsttc[.]com[.]qtlcn[.]com/.*", replace: function (e) {
            return e.replace(/thumb_jpg/, "large_jpg").replace("/medium_jpg/", "/large_jpg/").replace("/newsletter/", "/large_jpg/")
        }
    }, {
        site: "ArchDaily 国际版", srcPattern: "https?://images[.]adsttc[.]com/.*", replace: function (e) {
            return e.replace(/slideshow/, "large_jpg").replace(/thumb_jpg/, "large_jpg").replace("/medium_jpg/", "/large_jpg/").replace("/newsletter/", "/large_jpg/")
        }
    }, {
        site: "Dezeen", srcPattern: "https://static[.]dezeen[.]com/.*", replace: function (e) {
            return e.replace(/slideshow/, "large_jpg").replace(/thumb_jpg/, "large_jpg").replace(/-\d+x\d+.jpg/, ".jpg")
        }
    }, {
        site: "Archiproducts", srcPattern: "https://img[.]edilportale[.]com/.*", replace: function (e) {
            return e.replace(/-thumbs.*.[a-z]_/, "s/").replace(/news.*.[a-z]_/, "news/")
        }
    }, {
        site: "Wordpress 通用", srcPattern: "/wp-content/uploads/.*", replace: function (e) {
            return e.replace(/-\d+x\d+/, "").split("?w=")[0]
        }
    }, {
        site: "Squarespace 通用", srcPattern: "https://static[0-9][.]squarespace[.]com/.*", replace: function (e) {
            return e.replace(/format=[0-9]{3,4}w/, "format=3000w")
        }
    }, {
        site: "bilibili", srcPattern: "https://(.*).hdslb.com/(.*)@(.*).webp", replace: function (e) {
            return e.split("@")[0]
        }
    }, {
        site: "站酷", srcPattern: "https://img[.]zcool[.]cn/.*", replace: function (e) {
            return e.indexOf("?x-oss-process=") > -1 ? e.split("?x-oss-process=")[0] + "?x-oss-process=image/auto-orient,0/resize,m_lfit,w_3000" : e
        }
    }, {
        site: "如室", srcPattern: "res.rushi.net/(.*)", replace: function (e) {
            return e.includes("?x-oss-process=") ? e.split("?x-oss-process=")[0] : e
        }
    }, {
        site: "名师联智库", srcPattern: "imgbig.mslzk.com/(.*)", replace: function (e) {
            return e.includes("jpg?imageView") ? e.split("?")[0] : e
        }
    }, {
        site: "古田路9號", srcPattern: "https://cdn[.]gtn9[.]com/.*", replace: function (e) {
            return e.indexOf("?x-oss-process=") > -1 ? e.split("?x-oss-process=")[0] + "?x-oss-process=image/auto-orient,0/resize,m_lfit,w_3000" : e
        }
    }, {
        site: "Medium", srcPattern: "https://cdn-images-[0-9][.]medium[.]com/.*", replace: function (e) {
            return e.replace(/\/max\/\d{2,4}/, "")
        }
    }, {
        site: "Artstation", srcPattern: "https://cdn.*.artstation.com.*", replace: function (e) {
            return e.replace(/\d{14}\/small_square/, "large").replace(/\d{14}\/smaller_square/, "large").replace(/\d{14}\/micro_square/, "large").replace(/\/smaller_square/, "/large")
        }
    }, {
        site: "GameUI", srcPattern: "https://image.gameuiux.cn.*", replace: function (e) {
            return e.replace(/_list/, "_detail")
        }
    }, {
        site: "interiordesign", srcPattern: "https://d4qwptktddc5f[.]cloudfront[.]net/.*", replace: function (e) {
            return e.replace(/easy_thumbnails\/thumbs_/, "").replace(/[.]jpg.*/, ".jpg")
        }
    }, {
        site: "即刻", srcPattern: "https://cdn[.]ruguoapp[.]com/.*", replace: function (e) {
            return e.split("?imageMogr2")[0]
        }
    }]
};