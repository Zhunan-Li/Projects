var win=window;function getSiteInfo(){var e,t,o,r,i,a=ExtractTextTool.extractSiteTags(),n=document.title,m=jQuery('meta[name="description"]').attr("content"),u=jQuery('meta[name="keywords"]').attr("content"),c=parseYourubeVideoID(location.href),s=parseBiliBiliVideoID(location.href),d=parseVimeoVideoID(location.href),l=jQuery("meta[property='og:video:width']").attr("content"),p=jQuery("meta[property='og:video:height']").attr("content");c&&(t=c,i="youtube",e=`https://i.ytimg.com/vi/${c}/hqdefault.jpg`,(h=jQuery(".ytp-time-duration").text())&&hmsToSecondsOnly(h)>0&&(o=hmsToSecondsOnly(h)));s&&(t=s,i="bilibili",e=jQuery("meta[property='og:image']").attr("content"),l=jQuery("meta[property='og:width']").attr("content"),p=jQuery("meta[property='og:height']").attr("content"),r="https://"+$(jQuery("#link2").val()).attr("src"),(h=jQuery(".bpx-player-ctrl-time-duration").text())&&hmsToSecondsOnly(h)>0&&(o=hmsToSecondsOnly(h)));if(d){t=d,i="vimeo";let r=jQuery(".vp-preview");if(r.length>0){e=r.attr("data-thumb");let t=new URL(e);l=t.searchParams.get("mw"),p=t.searchParams.get("mh")}else e=jQuery("meta[property='og:image']").attr("content"),l=jQuery("meta[property='og:video:width']").attr("content"),p=jQuery("meta[property='og:video:height']").attr("content");var h;(h=jQuery(".vp-progress [aria-valuemax]").attr("aria-valuemax"))&&hmsToSecondsOnly(h)>0&&(o=hmsToSecondsOnly(h))}var y={metaTags:a,metaTitle:n,metaDescription:m,metaKeywords:u,videoID:t,videoWidth:l,videoHeight:p,videoMedium:i,videoThumb:e};o&&(y.videoDuration=o),r&&(y.videoEmbed=r),chrome.runtime.sendMessage({action:"saveURL",result:y})}function parseYourubeVideoID(e){var t=e.match(/(?:youtube(?:-nocookie)?\.com\/(?:[^\/]+\/.+\/|(?:v|e(?:mbed)?)\/|.*[?&]v=)|youtu\.be\/)([^"&?\/\s]{11})/);return!(!t||11!=t[1].length)&&t[1]}function parseBiliBiliVideoID(e){try{return e.split("bilibili.com/video/")[1].split("?")[0]}catch(e){return}}function parseVimeoVideoID(e){var t=e.match(/^.+vimeo.com\/(.*\/)?([^#\?]*)/);return t?t[2]||t[1]:null}function hmsToSecondsOnly(e){for(var t=e.split(":"),o=0,r=1;t.length>0;)o+=r*parseInt(t.pop(),10),r*=60;return o}function absolutePath(e){e&&e.indexOf(" ")>-1&&(e=e.trim().split(" ")[0]);var t=document.createElement("a");return t.href=e,t.href}getSiteInfo();