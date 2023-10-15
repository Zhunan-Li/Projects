const path = require('path');
const fs = require("fs");
const Jimp = require("jimp");
const portfolioFolder = path.join(__dirname, '../public/portfolio');

fs.readdir(portfolioFolder, function (err, files) {
    if (err) {
        return console.log('找不到資料夾' + err);
    }

    const tasks = [];
    for (const file of files) {
        if (file.includes("thumb")) continue;

        const imgPath = `${portfolioFolder}/${file}`;
        tasks.push(new Promise((resolve, reject) => {
            Jimp.read(imgPath, (err, img) => {
                if (err) reject(err);
                resolve({
                    file: `/portfolio/${file}`,
                    imgWidth: img.bitmap.width,
                    imgHeight: img.bitmap.height,
                    minWidth: "最小寬度"
                });
            })
        }));
    }
    Promise.all(tasks).then((value) => {
        const savedImg = [];
        // const data = fs.readFileSync(path.join(__dirname, "../src/components/Gallery.vue"));
        // for (const l of data.toString().split("\n")) {
        //     if (!l.includes("<GalleryImage")) continue;
        //     const imgUrl = l.split(":")[1];
        //     savedImg.push(imgUrl.substring(10, imgUrl.length - 2));
        // }
        // for (const v of value) {
        //     if (savedImg.includes(v.file)) continue;
        //     console.log(`<GalleryImage :img-url="'${v.file}'" :img-width="'${v.imgWidth}'" :img-height="'${v.imgHeight}'" :min-width="'${v.minWidth}'"></GalleryImage>`)
        // }
        console.log(value);
    });
})
;