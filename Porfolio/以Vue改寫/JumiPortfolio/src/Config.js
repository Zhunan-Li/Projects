const portfolioWidth = "80vw";
const discordId = "patota_jumi";

/**
 * 圖片的設定在這
 * name: "分類的名稱"
 * images: [{
 *   file: '檔案位置',
 *   imgWidth: 圖片的原寬(不是縮圖)
 *   imgHeight: 圖片的原高(不是縮圖)
 *   minWidth: 圖片最小寬度 超過就會換行
 * }]
 */
const galleryImages = [
    {
        name: "LOWPOLY MODELS",
        images: [
            {
                file: '/portfolio/npc1.jpg',
                imgWidth: 1060,
                imgHeight: 1080,
                minWidth: 200
            },
            {
                file: '/portfolio/npc2.jpg',
                imgWidth: 1060,
                imgHeight: 1080,
                minWidth: 200
            },
            {
                file: '/portfolio/npc3.jpg',
                imgWidth: 1060,
                imgHeight: 1080,
                minWidth: 200
            },
            {
                file: '/portfolio/npc4.jpg',
                imgWidth: 1060,
                imgHeight: 1080,
                minWidth: 200
            },
            {
                file: '/portfolio/1.jpg',
                imgWidth: 3415,
                imgHeight: 1208,
                minWidth: 99999
            },
        ]
    },
    {
        name: "PIXEL ART",
        images: [
            {
                file: '/portfolio/7.gif',
                imgWidth: 683,
                imgHeight: 512,
                minWidth: 99999
            },
            {
                file: '/portfolio/8.png',
                imgWidth: 3415,
                imgHeight: 2560,
                minWidth: 500
            },
            {
                file: '/portfolio/panda.jpg',
                imgWidth: 1920,
                imgHeight: 1920,
                minWidth: 100
            }
        ]
    },
    {
        name: "OTHER STUFF",
        images: [
            {
                file: '/portfolio/14.jpg',
                imgWidth: 3415,
                imgHeight: 2110,
                minWidth: 1
            }
        ]
    }
]

export {portfolioWidth, galleryImages, discordId};