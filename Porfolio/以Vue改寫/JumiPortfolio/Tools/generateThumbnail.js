const path = require('path');
const fs = require("fs");
const Jimp = require('jimp');
const portfolioFolder = path.join(__dirname, '../public/portfolio');

fs.readdir(portfolioFolder, function (err, files) {
    if (err) {
        return console.log('找不到資料夾' + err);
    }
    files.forEach(function (file) {
        if (file.includes("thumb")) return;
        const imgPath = `${portfolioFolder}\\${file}`;
        Jimp.read(imgPath, (err, img) => {
            if (err) throw err;
            img.resize(20, 20);
            img.write(`${imgPath}_thumb.jpg`);
        });
        console.log(`已產生 ${file} 的縮圖`);
    });
});