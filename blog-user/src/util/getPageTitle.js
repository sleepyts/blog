import store from "@/store/index.js";


export default function getPageTitle(pageTitle){
    const title = store().state.siteInfo.webTitleSuffix;
    if (pageTitle) {
        if (title) {
            return `${pageTitle}${title}`
        }
        return pageTitle
    }
    return title
}

