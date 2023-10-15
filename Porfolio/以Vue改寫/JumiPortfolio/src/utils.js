import {ref} from "vue";

const currentPage = ref(getCurrentPage());
function getCurrentPage() {
    return window.location.hash;
}

function playScriptAnimation(element, steps, ms) {
    element.animate(steps, {
        duration: ms,
    })
}

export {getCurrentPage, playScriptAnimation, currentPage}