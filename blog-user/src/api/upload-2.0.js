
// 生成指定长度的随机字符串
function generateRandomString(length) {
    const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    return Array.from({ length }, () => characters[Math.floor(Math.random() * characters.length)]).join('');
}

// 计算 MD5 哈希
async function calculateMD5Hash(nonce, timestamp) {
    const secret = "fuck-your-mother-three-thousand-times-apes-not-kill-apes";
    const data = `nonce=${nonce}&timestamp=${timestamp}${secret}`;

    // 将字符串编码为 ArrayBuffer
    const encoder = new TextEncoder();
    const dataBuffer = encoder.encode(data);

    // 使用 Web Crypto API 计算 MD5 哈希
    const hashBuffer = await crypto.subtle.digest('MD5', dataBuffer);

    // 将 ArrayBuffer 转换为十六进制字符串
    const hashArray = Array.from(new Uint8Array(hashBuffer));
    const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');

    return hashHex;
}

export async function onRequestPost(form) {
    // 生成随机字符串和时间戳
    if(form.get('file') === undefined|| form.get('file') === null|| form.get('file') === '') return {
        "url": "error",
        code : 500,
    }
    const nonce = generateRandomString(8);
    const timestamp = String(Math.floor(new Date().getTime()));

    // 计算 MD5 哈希
    const acceptLocale = await calculateMD5Hash(nonce, timestamp);

    // console.log(acceptLocale);

    // 构建请求头
    const imgheaders = {
        "Accept": "application/json, text/javascript, */*; q=0.01",
        "Accept-Encoding": "gzip, deflate, br, zstd",
        "Accept-Language": "zh-CN,zh;q=0.9",
        "Accept-Locale": acceptLocale,
        "Cache-Control": "no-cache",
        "Pragma": "no-cache",
        "Sec-Fetch-Dest": "empty",
        "Sec-Fetch-Mode": "cors",
        "Sec-Fetch-Site": "cross-site",
        "Origin" : "https://image.tsukiyo.cn",
        "Referer": "https://image.tsukiyo.cn/tencent"

    };
    const formData = new FormData();
    formData.append('nonce', nonce);
    formData.append('timestamp', timestamp);
    formData.append('file', form.get('file'));

    const res_img = fetch('https://telegra.ph/' + url.pathname + url.search, {
        method: clonedRequest.method,
        headers: clonedRequest.headers,
        body: clonedRequest.body,
    });
    const options = {
        timeZone: 'Asia/Shanghai',
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour12: false,
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit'
    };
    const responseData = await res_img.json();
    try {
        if (responseData.code === 200) {
            const url = responseData.data;
            return {
                "url": url,
                code : 200,
            }

        } else {
            return{
                "url": "error",
                code : 500,
            }
        }

    } catch (e) {
        console.log(e);
    }

    return {
        "url": "error",
        code : 500,
    }


}



async function insertImageData(env, src, referer, ip, rating, time) {
    try {
        const instdata = await env.prepare(
            `INSERT INTO imginfo (url, referer, ip, rating, total, time)
             VALUES ('${src}', '${referer}', '${ip}', ${rating}, 1, '${time}')`
        ).run();
    } catch (error) {

    }
}


