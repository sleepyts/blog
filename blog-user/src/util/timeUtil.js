export function convertToRelativeTime(date) {
    date = new Date(date);
    const currentTime = new Date();
    const duration = currentTime - date;

    const seconds = Math.floor(duration / 1000);
    const minutes = Math.floor(seconds / 60);
    const hours = Math.floor(minutes / 60);
    const days = Math.floor(hours / 24);

    if (days > 0) {
        if (days > 6) {
            return formatDate(date);
        }
        return days + "天前";
    } else if (hours > 0) {
        return hours + "小时前";
    } else if (minutes > 0) {
        return minutes + "分钟前";
    } else {
        return "刚刚";
    }
}

function formatDate(date) {
    const year = date.getFullYear();
    const month = ('0' + (date.getMonth() + 1)).slice(-2);
    const day = ('0' + date.getDate()).slice(-2);
    return `${year}-${month}-${day}`;
}