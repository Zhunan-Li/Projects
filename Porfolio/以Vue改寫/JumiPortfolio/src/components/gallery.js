import {galleryImages} from "@/Config";

function getImgRows(images, maxRowWidth) {
    const rows = [];

    let currentRow = [];
    let currentWidth = 0;
    for (let i = 0; i < images.length; i++) {
        const img = images[i];

        if (i === images.length - 1) {
            // 最後一張
            if (currentWidth + img.minWidth < maxRowWidth) {
                rows.push(currentRow.concat([img]));
            } else {
                rows.push(currentRow);
                rows.push([img]);
            }
            break;
        }
        // cw 500
        if (currentWidth + img.minWidth < maxRowWidth) {
            // 同行
            currentRow.push(img);
            currentWidth += img.minWidth;
        } else {
            // 換行
            if (currentRow.length === 0) {
                rows.push([img]);
                currentWidth = 0;
            } else {
                rows.push(currentRow);
                currentRow = [img];
                currentWidth = img.minWidth;
            }
        }
    }
    return rows;
}

function formatRows(rows, contentWidth) {
    const img = [];
    for (const currentRow of rows) {
        let currentTotalHeight = 0;
        for (const i of currentRow) {
            currentTotalHeight += i.imgHeight;
        }
        const averageHeight = currentTotalHeight / currentRow.length;
        for (const i of currentRow) {
            const heightDifference = Math.abs(i.imgHeight - averageHeight);
            const sizeRatio = i.imgWidth / i.imgHeight;
            i["width"] = i.imgHeight - averageHeight > 0 ? i.imgWidth - (sizeRatio * heightDifference) : (sizeRatio * heightDifference) + i.imgWidth;
            i["height"] = averageHeight;
        }

        let currentTotalWidth = currentRow.length - 1;
        for (const i of currentRow) {
            // console.log(i.width);
            currentTotalWidth += i.width;
        }
        const totalWidthDifferenceRatio = (contentWidth - currentRow.length - 1) / currentTotalWidth; // 當前行縮放比率
        // console.log(totalWidthDifferenceRatio);
        for (const i of currentRow) {
            // console.log("+==============================")
            // console.log(i.width)
            const originalWidth = i.width;
            const sizeRatio = i.imgHeight / i.imgWidth;
            if (currentTotalWidth - contentWidth > 0) {
                // 要縮小
                i.width = i.width * totalWidthDifferenceRatio;
                i.height = i.height - (originalWidth - i.width) * sizeRatio;
            } else {
                // 要放大
                i.width = i.width * totalWidthDifferenceRatio;
                i.height = i.height + (i.width - originalWidth) * sizeRatio;
            }
            img.push(i);
        }
    }

    return img;
}

function createGallery() {
    const content = document.querySelector("div#gallery-container");
    const contentWidth = content.getBoundingClientRect().width;
    // console.log(`Max Width = ${contentWidth}`);
    const result = [];
    for (const category of galleryImages) {
        const rows = getImgRows(category.images, contentWidth);
        // console.log(rows);
        result.push({
            name: category.name,
            images: formatRows(rows, contentWidth)
        });
    }
    return result;
}

export {createGallery};